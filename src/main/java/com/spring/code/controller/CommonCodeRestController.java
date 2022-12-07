package com.spring.code.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.code.model.CodeRequest;
import com.spring.code.model.CodeType;
import com.spring.code.model.CommonCodeVO;
import com.spring.code.service.CommonCodeService;
import com.spring.common.model.GridForm;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api/code*")
public class CommonCodeRestController {
	private final CommonCodeService commonCodeService;
	
	@GetMapping(value = "/grid")
	public ResponseEntity<?> getCodesGridForm(Pageable page, @RequestParam(value = "search", required = false) String searchParam) {
		GridForm codesGridForm = commonCodeService.getCodesGridForm(page, CodeType.valueOf(searchParam));
		return ResponseEntity.ok(codesGridForm);
	}
	
	@GetMapping
	public ResponseEntity<?> getCodes(CodeType codeType) {
		Map<String, String> codes = commonCodeService.getCodes(codeType);
		List<CommonCodeVO> codeList = codes.entrySet().stream().map(i -> CommonCodeVO.builder().id(i.getKey()).name(i.getValue()).build()).collect(Collectors.toList());
		return ResponseEntity.ok(codeList);
	}

	@PostMapping
	public ResponseEntity<?> addCodes(@RequestBody CodeRequest request) {
		commonCodeService.addCodes(request);
		return ResponseEntity.ok("");
	}

	@DeleteMapping
	public ResponseEntity<?> deleteCodes(@RequestBody CodeRequest request) {
		commonCodeService.deleteCodes(request);
		return ResponseEntity.ok("");
	}
}