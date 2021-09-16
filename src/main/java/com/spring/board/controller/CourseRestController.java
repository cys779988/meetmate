package com.spring.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.board.model.CourseDto;
import com.spring.board.service.CourseService;
import com.spring.common.util.AppUtil;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/course/*")
@SuppressWarnings("rawtypes")
public class CourseRestController {
	CourseService courseService;

	@GetMapping(value = "/get")
	public ResponseEntity getData(@RequestParam(value="page", required = false) Integer page, 
			@RequestParam(value="perPage", required = false) Integer perPage) {
		List<CourseDto> contentsList =  courseService.getCourseList(page, perPage);
		
		Long totalCount = courseService.getCourseCount();
		Map<String, Object> pagination = new HashMap<>();
		pagination.put("page", page);
		pagination.put("totalCount", totalCount);
		AppUtil appUtil = new AppUtil(contentsList, pagination);
		
		return ResponseEntity.ok(appUtil.getData());
	}
	
	@PostMapping(value = "/post", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity postData(@RequestBody List<CourseDto> param) {
		courseService.add(param);
		Map<String, Object> result = new HashMap<>();
		result.put("result", "true");
		
		return ResponseEntity.ok(result);
	}

	@PutMapping(value = "/put", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity putData(@RequestBody Map<String, List<CourseDto>> param) {
		courseService.add(param.get("updatedRows"));
		Map<String, Object> result = new HashMap<>();
		result.put("result", "true");
		return ResponseEntity.ok(result);
	}
	
	@PostMapping(value = "/delete")
	public ResponseEntity deleteData(@RequestBody Map<String, List<CourseDto>> param) {
		courseService.delete(param.get("deletedRows"));
		Map<String, Object> result = new HashMap<>();
		result.put("result", "true");
		return ResponseEntity.ok(result);
	}
}
