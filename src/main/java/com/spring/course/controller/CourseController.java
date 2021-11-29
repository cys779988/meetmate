package com.spring.course.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.board.model.BoardDto;
import com.spring.common.util.AppUtil;
import com.spring.course.model.CourseDto;
import com.spring.course.service.CourseService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/course/*")
public class CourseController {

	private CourseService courseService;
	
	
	@GetMapping("/")
	public String listView(Model model) {
		model.addAttribute("category", courseService.getCategories());
		return "course/list";
	}

	@GetMapping("/add")
	public String addView(Model model) {
		model.addAttribute("category", courseService.getCategories());
		return "course/regist";
	}

	@GetMapping("/{no}")
	public String detailView(@PathVariable("no") Long no, Model model) {
		CourseDto courseDto = courseService.getCourse(no);
		if(courseDto.getRegistrant().equals(AppUtil.getUser())){
			model.addAttribute("owner", true);
		}
		model.addAttribute("courseDto", courseService.getCourse(no));
		return "course/detail";
	}

	@GetMapping("/edit/{no}")
	public String editView(@PathVariable("no") Long no, Model model) {
		model.addAttribute("category", courseService.getCategories());
		model.addAttribute("courseDto", courseService.getCourse(no));
		return "course/update";
	}
}
