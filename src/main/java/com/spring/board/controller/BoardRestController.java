package com.spring.board.controller;


import java.io.IOException;
import java.net.URLEncoder;

import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.board.model.BoardRequest;
import com.spring.board.model.ReplyDto;
import com.spring.board.service.BoardService;
import com.spring.common.exception.BusinessException;
import com.spring.common.model.ErrorCode;
import com.spring.common.model.GridForm;
import com.spring.file.model.FileDto;
import com.spring.file.util.FileHandler;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/board*")
@SuppressWarnings("rawtypes")
public class BoardRestController {

	BoardService boardService;

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getBoards(Pageable page, @RequestParam(value = "search", required = false) String searchParam) {
		GridForm boardsGridForm = boardService.getBoardsGridForm(page, searchParam);
		return ResponseEntity.ok(boardsGridForm);
	}

	@GetMapping(value = "/reply/{no}")
	public ResponseEntity getReplys(@PathVariable("no") Long no) {
		return ResponseEntity.ok(boardService.getReplys(no));
	}

	@PostMapping
	public ResponseEntity addBoard(@Valid BoardRequest boardRequest) {
		boardService.addBoard(boardRequest);
		return ResponseEntity.ok(null);
	}
	
	@PostMapping(value = "/reply", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addReply(@RequestBody @Valid ReplyDto param) {
		boardService.addReply(param);
		return ResponseEntity.ok(null);
	}

	@PostMapping(value = "/{no}")
	public ResponseEntity editBoard(@Valid BoardRequest boardRequest, @PathVariable("no") Long no) {
		boardRequest.setBoardId(no);
		boardService.addBoard(boardRequest);
		return ResponseEntity.ok(null);
	}

	@DeleteMapping(value = "/{no}")
	public ResponseEntity deleteBoard(@PathVariable("no") Long no) {
		boardService.deleteBoard(no);
		return ResponseEntity.ok(null);
	}
	
	@GetMapping(value = "/download/{no}")
	public ResponseEntity downloadFile(@PathVariable("no") Long no) {
		FileDto file = boardService.getBoardFile(no);
		Resource resource = FileHandler.loadFileAsResource(file);
		
		try {
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentDisposition(ContentDisposition.attachment()
																.filename(URLEncoder.encode(file.getOriginalFileName(), "UTF-8"))
																.build());
			httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			httpHeaders.setContentLength(resource.getFile().length());
			
			return ResponseEntity.ok()
								.headers(httpHeaders)
								.body(resource);
		} catch (IOException e) {
			throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}
}
