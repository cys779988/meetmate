package com.spring.course.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.course.model.GroupDto;
import com.spring.course.service.CourseService;
import com.spring.course.service.GroupMngService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/group/*")
@SuppressWarnings("rawtypes")
public class GroupMngRestController {

	CourseService courseService;
	
	GroupMngService groupMngService;
	
	ObjectMapper objectMapper;
	
	@PostMapping("/apply/{no}")
	public ResponseEntity applyGroup(@PathVariable("no") Long no) {
		groupMngService.apply(no);
		return ResponseEntity.ok("");
	}
	
	@GetMapping("/{no}")
	public ResponseEntity getGroupUsers(@PathVariable("no") Long no) {
		return ResponseEntity.ok(groupMngService.getUsersInGroupById(no));
	}

	@PostMapping("/autoDivide")
	public ResponseEntity autoDivide(@RequestBody Map<String, Object> param) {
		groupMngService.autoDivide(param);
		return ResponseEntity.ok("");
	}

	@PostMapping("/resetDivide/{no}")
	public ResponseEntity resetDivide(@PathVariable("no") Long no) {
		groupMngService.resetDivide(no);
		return ResponseEntity.ok("");
	}
	
	@PostMapping("/updateDivide")
	public ResponseEntity updateDivide(@RequestBody List<GroupDto> groupDto) {
		groupMngService.updateDivide(groupDto);
		return ResponseEntity.ok("");
	}

}
