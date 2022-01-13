package com.spring.board.controller;

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

import com.spring.board.model.BoardDto;
import com.spring.board.model.ReplyDto;
import com.spring.board.service.BoardService;
import com.spring.common.model.PageVO;
import com.spring.common.util.AppUtil;
import com.spring.common.util.GridUtil;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/board/*")
@SuppressWarnings("rawtypes")
public class BoardRestController {

	BoardService boardService;

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getBoards(@RequestParam(value = "page") Integer page, @RequestParam(value = "perPage") Integer perPage,
			@RequestParam(value = "search", required = false) String searchParam) {
		Map<String, Object> pagination = new HashMap<>();
		pagination.put("page", page);
		PageVO pageVO = PageVO.builder().page(page).perPage(perPage).build();
		
		List<BoardDto> boardList = boardService.getBoards(pageVO, searchParam);
		Long totalCount = boardService.getBoardCount(searchParam);

		pagination.put("totalCount", totalCount);
		GridUtil gridUtil = new GridUtil(boardList, pagination);

		return ResponseEntity.ok(gridUtil.getData());
	}

	/*
	@GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getBoards(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "keyword", required = false) String keyword) {
		List<BoardDto> boardList = boardService.getBoardList(page, keyword);
		Integer[] pageList = boardService.getPageList(page, keyword);
		Map<String, Object> response = new HashMap<>();
		response.put("boardList", boardList);
		response.put("pageList", pageList);
		return ResponseEntity.ok(response);
	}
	*/
	
	@GetMapping(value = "/reply/{no}")
	public ResponseEntity getReplys(@PathVariable("no") Long no) {
		return ResponseEntity.ok(boardService.getReplys(no));
	}

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addBoard(@RequestBody @Valid BoardDto param, Errors errors) {
		List<Map<String, String>> errorList = new ArrayList<>();

		if (errors.hasErrors()) {
			for (FieldError value : errors.getFieldErrors()) {
				Map<String, String> map = new HashMap<>();
				map.put(value.getField(), value.getDefaultMessage());
				errorList.add(map);
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorList);
		}
		param.setRegistrant(AppUtil.getUser());
		boardService.addBoard(param);
		return ResponseEntity.ok(null);
	}
	
	@PostMapping(value = "/reply", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addReply(@RequestBody @Valid ReplyDto param, Errors errors) {
		List<Map<String, String>> errorList = new ArrayList<>();
		
		if (errors.hasErrors()) {
			for (FieldError value : errors.getFieldErrors()) {
				Map<String, String> map = new HashMap<>();
				map.put(value.getField(), value.getDefaultMessage());
				errorList.add(map);
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorList);
		}
		param.setRegistrant(AppUtil.getUser());
		boardService.addReply(param);
		return ResponseEntity.ok(null);
	}

	@PutMapping(value = "/{no}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity editBoard(@RequestBody @Valid BoardDto param, @PathVariable("no") Long no, Errors errors) {
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
		boardService.addBoard(param);
		return ResponseEntity.ok(null);
	}

	@DeleteMapping(value = "/{no}")
	public ResponseEntity deleteBoard(@PathVariable("no") Long no) {
		boardService.deleteBoard(no);
		return ResponseEntity.ok(null);
	}
}
