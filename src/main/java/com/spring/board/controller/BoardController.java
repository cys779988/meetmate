package com.spring.board.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.board.model.BoardDto;
import com.spring.board.model.CourseDto;
import com.spring.board.service.BoardService;
import com.spring.common.config.PerLogging;
import com.spring.common.util.AppUtil;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/board/*")
public class BoardController {

	private BoardService boardService;
	
	@PerLogging
	@RequestMapping("/")
	public String list() {
		/*
		List<BoardDto> boardList = boardService.getBoardList(pageNum, keyword);
		Integer[] pageList = boardService.getPageList(pageNum, keyword);
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("boardList", boardList);
		model.addAttribute("pageList", pageList);
		*/
		return "board/list";
	}
	
	@RequestMapping("/courseList")
	public String courseList() {
		return "board/courseList";
	}
	
	@RequestMapping("map")
	public String map() {
		return "board/courseList";
	}

	@GetMapping("/add")
	public String addView() {
		return "board/write";
	}
	
	@PostMapping("/add")
	public String add(BoardDto boardDto) {
		boardService.add(boardDto);
		return "redirect:/";
	}
	
	@GetMapping("/post/{no}")
	public String detail(@PathVariable("no") Long no, Model model) {
		BoardDto boardDTO = boardService.getPost(no);
		
		model.addAttribute("boardDto", boardDTO);
		return "board/detail";
	}
	
	@GetMapping("/post/edit/{no}")
	public String edit(@PathVariable("no") Long no, Model model) {
		BoardDto boardDTO = boardService.getPost(no);
		
		model.addAttribute("boardDto", boardDTO);
		return "board/update";
	}
	
	@PutMapping("/post/edit/{no}")
	public String update(BoardDto boardDto) {
		boardService.add(boardDto);
		
		return "redirect:/";
	}
	
	@DeleteMapping("/post/{no}")
	public String delete(@PathVariable("no") Long no) {
		boardService.delete(no);
		
		return "redirect:/";
	}
}
