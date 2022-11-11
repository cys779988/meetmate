package com.spring.course.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.common.model.GridForm;
import com.spring.common.util.AppUtil;
import com.spring.course.model.CourseDto;
import com.spring.course.service.CourseService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/course/*")
public class CourseRestController {
	
	CourseService courseService;

	@GetMapping(value = "/")
	public ResponseEntity<?> getCourses(Pageable page, @RequestParam(value = "search", required = false) Long searchParam) {
		GridForm coursesGridForm = courseService.getCoursesGridForm(page, searchParam);
		return ResponseEntity.ok(coursesGridForm);
	}
	
	@GetMapping(value = "/{no}")
	public ResponseEntity<?> getCourse(@PathVariable("no") Long no) {
		CourseDto result = courseService.getCourse(no);
		return ResponseEntity.ok(result);
	}
	
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addCourse(@RequestBody @Valid CourseDto param) {
		param.setRegistrant(AppUtil.getUser());
		courseService.addCourse(param);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping(value = "/{no}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> editCourse(@RequestBody @Valid CourseDto param, @PathVariable("no") Long no, BindingResult bindingResult) {
		param.setId(no);
		param.setRegistrant(AppUtil.getUser());
		courseService.updateCourse(param);
		return ResponseEntity.ok(null);
	}

	@DeleteMapping(value = "/{no}")
	public ResponseEntity<?> deleteCourse(@PathVariable("no") Long no) {
		courseService.deleteCourse(no);
		return ResponseEntity.ok(null);
	}
}
