package com.spring.code.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.code.model.CodeRequest;
import com.spring.code.model.CodeType;
import com.spring.code.model.CommonCodeEntity;
import com.spring.code.repository.CommonCodeRepository;
import com.spring.common.model.GridForm;
import com.spring.common.util.AppUtil;
import com.spring.common.util.GridUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommonCodeService {
	
	private final CommonCodeRepository commonCodeRepository;
	
	public GridForm getCodesGridForm(Pageable page, CodeType codeType) {
		Page<CommonCodeEntity> pageCommonCode = commonCodeRepository.findByType(PageRequest.of(page.getPageNumber()
																							, page.getPageSize()
																							, page.getSort())
																					, codeType);
		
        return GridUtil.of(page.getPageNumber(), pageCommonCode.getTotalElements(), pageCommonCode.getContent());
	}

	@Transactional
	@CacheEvict(value = "commonCodeStore", allEntries = true)
	public List<CommonCodeEntity> addCodes(CodeRequest request) {
		String email = AppUtil.getUser();
		request.getRows().forEach(i -> i.setRegistrant(email));;
		return commonCodeRepository.saveAll(request.getRows());
	}

	@Transactional
	@CacheEvict(value = "commonCodeStore", allEntries = true)
	public void deleteCodes(CodeRequest request) {
		commonCodeRepository.deleteAll(request.getRows());
	}
	
	@Cacheable(value = "commonCodeStore", key = "#codeType")
	public Map<String, String> getCodes(CodeType codeType) {
		List<CommonCodeEntity> codeEntityList = commonCodeRepository.findByType(codeType);
		return codeEntityList.stream().collect(Collectors.toMap(i-> i.getId().toString(), CommonCodeEntity::getName));
	}
}
