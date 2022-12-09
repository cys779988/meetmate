package com.spring.group.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.common.util.AppUtil;
import com.spring.course.service.CourseService;
import com.spring.group.service.GroupMngService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/group*")
public class GroupMngController {

	private GroupMngService groupMngService;
	
	private CourseService courseService;
	
	@GetMapping
	public String myGroupMngView(Model model) {
		model.addAttribute("course", courseService.getCourseByUser(AppUtil.getUser()));
		return "group/list";
	}
	
	@GetMapping("/apply")
	public String appplyGroupView(Model model) {
		model.addAttribute("course", groupMngService.getApplyCourseByUser(AppUtil.getUser()));
		return "group/applyList";
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
