package com.example.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.exceptions.NoCourseAvailableException;
import com.example.exceptions.NoSuchCourseFoundException;
import com.example.exceptions.NoSuchStudentFoundException;
import com.example.model.dto.CourseDto;
import com.example.model.dto.StudentDto;
import com.example.model.entity.Course;
import com.example.model.entity.Student;
import com.example.repository.CourseRepository;
import com.example.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	private StudentRepository studentRepository;

	private CourseRepository courseRepository;

	private ModelMapper modelMapper;

	private Map<String, String> propertyMap = Map.of("first_name", "firstName", "last_name", "lastName", "middle_name",
			"middleName");

	public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository,
			ModelMapper modelMapper) {
		super();
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public void register(StudentDto dto) {
		Student student = modelMapper.map(dto, Student.class);

		studentRepository.save(student);
	}

	@Override
	public StudentDto getByStudentId(String studentId) {
		return checkIfStudentExist(studentId);
	}

	private StudentDto checkIfStudentExist(String studentId) {
		Optional<Student> optional = studentRepository.findByStudentId(studentId);

		if (optional.isEmpty()) {
			throw new NoSuchStudentFoundException(studentId);
		}

		return modelMapper.map(optional.get(), StudentDto.class);
	}

	private CourseDto checkIfCourseExist(String courseId) {
		Optional<Course> optional = courseRepository.findByCourseId(courseId);

		if (optional.isEmpty()) {
			throw new NoSuchCourseFoundException("" + courseId);
		}

		return modelMapper.map(optional.get(), CourseDto.class);
	}

	@Override
	public Page<StudentDto> getAllStudents(int pageNum, int pageSize, String sortOptions, String search) {

		Sort sort = prepareSortByFields(sortOptions);

		Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

		Page<Student> studentPage = studentRepository.getAllStudentsByPageAndSearch(pageable, search);

		java.lang.reflect.Type typeToken = new TypeToken<List<StudentDto>>() {
		}.getType();

		List<StudentDto> listDto = modelMapper.map(studentPage.getContent(), typeToken);

		return new PageImpl<>(listDto, pageable, studentPage.getTotalElements());
	}

	private Sort prepareSortByFields(String sortOptions) {
		Sort sort = Sort.unsorted();

		String[] sortFields = sortOptions.split(",");

		for (String sortField : sortFields) {
			String actualSortField = sortField.replace("-", "");

			if (sortField.startsWith("-")) {
				sort = sort.and(Sort.by(propertyMap.get(actualSortField)).descending());
			} else {
				sort = sort.and(Sort.by(propertyMap.get(actualSortField)).ascending());
			}
		}

		return sort;
	}

	@Override
	public void enrollForACourse(String studentId, String courseId) {
		StudentDto studentDto = checkIfStudentExist(studentId);

		Student student = modelMapper.map(studentDto, Student.class);

		CourseDto courseDto = checkIfCourseExist(courseId);

		Course course = modelMapper.map(courseDto, Course.class);

		student.getCoursesTaken().add(course);

		studentRepository.save(student);

	}

	@Override
	public List<CourseDto> getEnrolledCoursesForAStudent(String studentId, int pageNum, int pageSize,
			String sortOptions, String search) {
		StudentDto dto = checkIfStudentExist(studentId);

		if (dto.getCoursesTaken().isEmpty()) {
			throw new NoCourseAvailableException("You don't have any enrolled course");
		}
		
		Sort sort = prepareSortByFields(sortOptions);
		
		Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
		
		Page<Course> coursePage = null;

		return dto.getCoursesTaken();
	}
}
