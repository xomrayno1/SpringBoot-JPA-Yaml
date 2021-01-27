package com.tampro.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tampro.entity.Student;

public interface StudentRepository extends CrudRepository<Student, Long>{
	
	@Override
	List<Student> findAll();
}
