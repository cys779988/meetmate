package com.spring.board.controller;


import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
import com.spring.common.model.ErrorCode;
import com.spring.common.model.ErrorResponse;
import com.spring.common.model.GridForm;
import com.spring.common.util.AppUtil;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/board/*")
@SuppressWarnings("rawtypes")
public class BoardRestController {

	BoardService boardService;

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getBoards(Pageable page, @RequestParam(value = "search", required = false) String searchParam) {
		GridForm boardsGridForm = boardService.getBoardsGridForm(page, searchParam);
		return ResponseEntity.ok(boardsGridForm);
	}

	@GetMapping(value = "/reply/{no}")
	public ResponseEntity getReplys(@PathVariable("no") Long no) {
		return ResponseEntity.ok(boardService.getReplys(no));
	}

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addBoard(@RequestBody @Valid BoardDto param, BindingResult bindingResult) {
		if (bindingResult.hasFieldErrors()) {
			return new ResponseEntity<>(ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, bindingResult), HttpStatus.BAD_REQUEST);
		}
		
		param.setRegistrant(AppUtil.getUser());
		boardService.addBoard(param);
		return ResponseEntity.ok(null);
	}
	
	@PostMapping(value = "/reply", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addReply(@RequestBody @Valid ReplyDto param, BindingResult bindingResult) {
		if (bindingResult.hasFieldErrors()) {
			return new ResponseEntity<>(ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, bindingResult), HttpStatus.BAD_REQUEST);
		}
		
		param.setRegistrant(AppUtil.getUser());
		boardService.addReply(param);
		return ResponseEntity.ok(null);
	}

	@PutMapping(value = "/{no}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity editBoard(@RequestBody @Valid BoardDto param, @PathVariable("no") Long no, BindingResult bindingResult) {
		if (bindingResult.hasFieldErrors()) {
			return new ResponseEntity<>(ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, bindingResult), HttpStatus.BAD_REQUEST);
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
