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
import com.spring.board.service.BoardService;
import com.spring.common.util.AppUtil;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/board/*")
@SuppressWarnings("rawtypes")
public class BoardRestController {
	
	BoardService boardService;
	
	@GetMapping(value="/get", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getBoards(@RequestParam(value="page", required = false) Integer page, 
			@RequestParam(value="keyword", required = false) String keyword) {
		List<BoardDto> boardList = boardService.getBoardList(page, keyword);
		
		Long totalCount = boardService.getBoardCount(keyword);
		
		Map<String, Object> pagination = new HashMap<>();
		pagination.put("page", page);
		pagination.put("totalCount", totalCount);
		AppUtil appUtil = new AppUtil(boardList, pagination);
		
		return ResponseEntity.ok(appUtil.getData());
	}
	
	/*
	 * @GetMapping(value="/get", produces = MediaType.APPLICATION_JSON_VALUE) public
	 * ResponseEntity getBoards(@RequestParam(value="page", required = false)
	 * Integer page,
	 * 
	 * @RequestParam(value="keyword", required = false) String keyword) {
	 * List<BoardDto> boardList = boardService.getBoardList(page, keyword);
	 * Integer[] pageList = boardService.getPageList(page, keyword); Map<String,
	 * Object> response = new HashMap<>(); response.put("boardList", boardList);
	 * response.put("pageList", pageList); return ResponseEntity.ok(response); }
	 */
	
	@GetMapping(value = "/{no}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BoardDto> get(@PathVariable("no") Long no) {
		return ResponseEntity.ok(boardService.getPost(no));
	}
	
	@PostMapping(value="/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity add(@RequestBody @Valid BoardDto param, Errors errors) {
		List<Map<String, String>> errorList = new ArrayList<>();
		
		if(errors.hasErrors()) {
			for (FieldError value : errors.getFieldErrors()) {
				Map<String, String> map = new HashMap<>();
				System.out.println(value.getDefaultMessage());
				map.put(value.getField(), value.getDefaultMessage());
				errorList.add(map);
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorList);
		}
		boardService.add(param);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping(value = "/{no}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity edit(@RequestBody @Valid BoardDto param, @PathVariable("no") Long no) {
		
		param.setId(no);
		boardService.add(param);
		return ResponseEntity.ok(null);
	}
	
	@DeleteMapping(value = "/{no}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity delete(@PathVariable("no") Long no) {
		boardService.delete(no);
		return ResponseEntity.ok(null);
	}
}
