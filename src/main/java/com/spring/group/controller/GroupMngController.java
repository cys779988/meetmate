package com.spring.group.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.course.service.CourseService;
import com.spring.group.service.GroupMngService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/group/*")
public class GroupMngController {

	private GroupMngService groupMngService;
	
	private CourseService courseService;
	
	@GetMapping("/")
	public String groupMngView(Model model) {
		model.addAttribute("course", courseService.getCourseByUser());
		return "group/list";
	}
	
	@GetMapping("/basicForm/{id}")
	public String basicFormView(@PathVariable("id") Long id, Model model) {
		model.addAttribute("id", id);
		return "group/basicForm";
	}
	
	@GetMapping("/divForm/{id}")
	public String divFormView(@PathVariable("id") Long id, Model model) {
		model.addAttribute("id", id);
		return "group/divForm";
	}
}
