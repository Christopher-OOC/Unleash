package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.example.model.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class FilterStudentRepositoryImpl implements FilterStudentRepository {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public Page<Student> getAllStudentsByPageAndSearch(Pageable pageable, String search) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Student> query = builder.createQuery(Student.class);
		
		Root<Student> root = query.from(Student.class);
		
		// Create WHERE CLAUSE
		
		createWhereClause(builder, query, root, search);
		
		//CREATE ORDER BY CLAUSE
		Sort sort = pageable.getSort();
		
		List<Order> listOrder = new ArrayList<>();
		
		sort.stream().forEach(order -> {
			if (order.isAscending()) {
				listOrder.add(builder.asc(root.get(order.getProperty())));
			}
			else {				
				listOrder.add(builder.desc(root.get(order.getProperty())));
			}
		});
		
		query.orderBy(listOrder);
		
		TypedQuery<Student> typedQuery = entityManager.createQuery(query);
		
		// CREATE PAGINATION CLAUSE
		typedQuery.setFirstResult((int)pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());
		
		List<Student> resultList = typedQuery.getResultList();
		
		long totalElements = getTotalElements(search);
							
		return new PageImpl<>(resultList, pageable, totalElements);
	}
	
	private void createWhereClause(CriteriaBuilder builder, CriteriaQuery<?> query, Root<Student> root,
			String search) {
		
		if (!"".equals(search)) {
		
			Predicate predicate1 = builder.like(root.get("lastName"), "%" +search + "%");
			Predicate predicate2 = builder.like(root.get("firstName"), "%" +search + "%");
			Predicate predicate3 = builder.like(root.get("middleName"), "%" +search + "%");
			
			Predicate allPredicates = builder.or(predicate1, predicate2, predicate3);
			
			query.where(allPredicates);
		}
		
	}

	private Long getTotalElements(String search) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		
		Root<Student> root = query.from(Student.class);
		
		createWhereClause(builder, query, root, search);
		
		query.select(builder.count(root));
		
		TypedQuery<Long> typedQuery = entityManager.createQuery(query);
		
		long totalElements = typedQuery.getSingleResult();
		
		return totalElements;
	}

}
