package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.model.Course;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class FilterCourseRepositoryImpl implements FilterCourseRepository {
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public Page<Course> getAllCourses(Pageable pageable, String search) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Course> query = builder.createQuery(Course.class);
		
		Root<Course> root = query.from(Course.class);
		
		// Sort
		List<Order> orderList = new ArrayList<>();
		
		pageable.getSort().stream().forEach(order -> {
			if (order.isAscending()) {
				orderList.add(builder.asc(root.get(order.getProperty())));
			}
			else {
				orderList.add(builder.desc(root.get(order.getProperty())));
			}
		});
		
		query.orderBy(orderList);
		
		// Search
		
		//check if value is not empty String
		if (!"".equals(search)) {
			addFilterByFitlerField(search, builder, query, root);
		}
		
		TypedQuery<Course> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult((int)pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());
		
		List<Course> courses = typedQuery.getResultList();
		
		long totalRows = generateTotalRows(pageable, search);
		
		log.info("TOTAL ROWS: {}", totalRows);
		
		Page<Course> page = new PageImpl<>(courses, pageable, totalRows);
		
		return page;
	}

	private void addFilterByFitlerField(String search, CriteriaBuilder builder, CriteriaQuery<?> query,
			Root<Course> root) {
		
		Predicate predicate1 = builder.like(root.get("courseName"), "%" + search + "%");
		Predicate predicate2 = builder.like(root.get("courseCode"), "%" + search + "%");
		Predicate predicate3 = builder.like(root.get("instructor").get("fullName"), "%" + search + "%");
		
		Predicate allPredicates = builder.or(predicate1, predicate2, predicate3);
		query.where(allPredicates);
	}
	
	private long generateTotalRows(Pageable pageable, String search) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		
		Root<Course> root = query.from(Course.class);
		
		addFilterByFitlerField(search, builder, query, root);
		
		query.select(builder.count(root));
		
		TypedQuery<Long> typedQuery = entityManager.createQuery(query);
		long totalRows = typedQuery.getSingleResult();
		
		return totalRows;
	}

}
