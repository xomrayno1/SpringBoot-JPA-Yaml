package com.tampro.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tampro.entity.Student;
import com.tampro.exception.ResourceNotFoundException;
import com.tampro.repository.StudentRepository;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
	@Autowired
	StudentRepository studentRepo;
	
	@GetMapping
	public ResponseEntity<List<Student>> getAll(){
		List<Student> list = studentRepo.findAll();
		if(list.isEmpty()) {
			return new ResponseEntity<List<Student>>(HttpStatus.CONFLICT);
		}else {
			return new ResponseEntity<List<Student>>(list,HttpStatus.OK);
		}
	}
	@GetMapping("/{id}")
	public ResponseEntity<Student> getById(@PathVariable("id") long id){
		Student student  = studentRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("student not found exception with id :"+id));
		return new ResponseEntity<Student>(student,HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<Object> createStudent(@RequestBody Student studentRequest){
		Student student = studentRepo.save(studentRequest);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
							.buildAndExpand(student.getId()).toUri();
		return ResponseEntity.created(location).body(student);
	}
	@PutMapping 
	public ResponseEntity<Student> updateStudent(@RequestBody Student studentRequest){
		Student student = studentRepo.findById(studentRequest.getId())
				.orElseThrow(()-> new ResourceNotFoundException("student not found exception with : "+studentRequest.getId()));
		if(studentRequest.getCodeStudent() != null) {
			student.setCodeStudent(studentRequest.getCodeStudent());
		}
		if(studentRequest.getFirstName() != null) {
			student.setFirstName(studentRequest.getFirstName());
		}
		if(studentRequest.getLastName() != null) {
			student.setLastName(studentRequest.getLastName());
		}
		student = studentRepo.save(student);
		return ResponseEntity.ok(student);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable("id") long id){
		Student student  = studentRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("student not found exception with id :"+id));
		studentRepo.delete(student);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
