package com.spring.code.controller;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.code.model.CodeType;
import com.spring.code.service.CommonCodeService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/code*")
public class CommonCodeController {
	CommonCodeService commonCodeService;
	
	@GetMapping
	public String listView(Model model) {
		model.addAttribute("code", Stream.of(CodeType.values()).collect(Collectors.toList()));
		return "code/list";
	}
}
