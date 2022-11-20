package com.spring.code;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.spring.code.model.CodeType;
import com.spring.code.model.CommonCodeEntity;
import com.spring.code.repository.CommonCodeRepository;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CodeDataJpaTest {

	@Autowired
	private CommonCodeRepository commonCodeRepository;

	@BeforeEach
	public void init() {
		List<CommonCodeEntity> codeList = Arrays.asList(
				CommonCodeEntity.builder().name("sample1").type(CodeType.COURSE_CATEGORY).build()
				, CommonCodeEntity.builder().name("sample2").type(CodeType.COURSE_CATEGORY).build()
				, CommonCodeEntity.builder().name("sample3").type(CodeType.COURSE_CATEGORY).build()
				);
		
		commonCodeRepository.saveAll(codeList);
	}

	@Test
	@DisplayName("공통코드 페이징 조회")
	public void getPageCodes() {
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.DEFAULT_DIRECTION, "name");
		Page<CommonCodeEntity> codeEntityList = commonCodeRepository.findByType(pageRequest, CodeType.COURSE_CATEGORY);
		
		assertThat(codeEntityList, notNullValue());
	}
	
	@Test
	@DisplayName("공통코드 조회")
	public void getCodes() {
		List<CommonCodeEntity> codeEntityList = commonCodeRepository.findByType(CodeType.COURSE_CATEGORY);
		
		assertThat(codeEntityList, notNullValue());
	}
	
	@Test
	@DisplayName("공통코드 삭제")
	public void deleteCodes() {
		
		CommonCodeEntity codeEntity = commonCodeRepository.save(CommonCodeEntity.builder().name("sample").type(CodeType.COURSE_CATEGORY).build());

		long beforeCount = commonCodeRepository.count();
		
		commonCodeRepository.deleteAll(Arrays.asList(codeEntity));
		
		long afterCount = commonCodeRepository.count(); 
		
		assertThat(beforeCount - afterCount, is(1L));
	}
	
	
	
}
