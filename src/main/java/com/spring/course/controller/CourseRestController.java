package com.spring.course.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.common.model.PageVO;
import com.spring.common.util.GridUtil;
import com.spring.course.model.CourseDto;
import com.spring.course.service.CourseService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/course/*")
@SuppressWarnings("rawtypes")
public class CourseRestController {
	
	CourseService courseService;

	@GetMapping(value = "/")
	public ResponseEntity getCourses(@RequestParam(value = "page") Integer page, @RequestParam(value = "perPage") Integer perPage,
			@RequestParam(value = "search", required = false) Long searchParam) {
		Map<String, Object> pagination = new HashMap<>();
		pagination.put("page", page);
		PageVO pageVO = PageVO.builder().page(page).perPage(perPage).build();
		
		List<CourseDto> contentsList =  courseService.getCourses(pageVO, searchParam);
		Long totalCount = courseService.getCourseCount();
		
		pagination.put("totalCount", totalCount);
		GridUtil gridUtil = new GridUtil(contentsList, pagination);
		
		return ResponseEntity.ok(gridUtil.getData());
	}
	
	@GetMapping(value = "/{no}")
	public ResponseEntity getCourse(@PathVariable("no") Long no) {
		CourseDto result =  courseService.getCourse(no);
		return ResponseEntity.ok(result);
	}
	
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addCourse(@RequestBody @Valid CourseDto param, Errors errors) {
		List<Map<String, String>> errorList = new ArrayList<>();

		if (errors.hasErrors()) {
			for (FieldError value : errors.getFieldErrors()) {
				Map<String, String> map = new HashMap<>();
				map.put(value.getField(), value.getDefaultMessage());
				errorList.add(map);
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorList);
		}
		courseService.addCourse(param);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping(value = "/{no}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity editCourse(@RequestBody @Valid CourseDto param, @PathVariable("no") Long no, Errors errors) {
		List<Map<String, String>> errorList = new ArrayList<>();

		if (errors.hasErrors()) {
			for (FieldError value : errors.getFieldErrors()) {
				Map<String, String> map = new HashMap<>();
				map.put(value.getField(), value.getDefaultMessage());
				errorList.add(map);
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorList);
		}
		param.setId(no);
		courseService.updateCourse(param);
		return ResponseEntity.ok(null);
	}

	@DeleteMapping(value = "/{no}")
	public ResponseEntity deleteCourse(@PathVariable("no") Long no) {
		courseService.deleteCourse(no);
		return ResponseEntity.ok(null);
	}
}
