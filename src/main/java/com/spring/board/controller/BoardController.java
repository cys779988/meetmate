package com.spring.board.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.board.model.BoardDto;
import com.spring.board.service.BoardService;
import com.spring.common.config.PerLogging;
import com.spring.common.util.AppUtil;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/board*")
public class BoardController {

	private BoardService boardService;
	
	@PerLogging
	@GetMapping
	public String listView() {
		return "board/list";
	}

	@GetMapping("/add")
	public String addView() {
		return "board/regist";
	}

	@GetMapping("/{no}")
	public String detailView(@PathVariable("no") Long no, Model model) {
		BoardDto board = boardService.getBoard(no);
		if(board.getRegistrant().equals(AppUtil.getUser())){
			model.addAttribute("owner", true);
		}
		model.addAttribute("board", board);
		return "board/detail";
	}
	
	@GetMapping("/edit/{no}")
	public String editView(@PathVariable("no") Long no, Model model) {
		BoardDto board = boardService.getBoard(no);
		model.addAttribute("board", board);
		return "board/update";
	}
}
