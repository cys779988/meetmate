package com.spring.code;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.*;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.spring.code.model.CodeType;
import com.spring.code.model.CommonCodeEntity;
import com.spring.code.repository.CommonCodeRepository;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@Transactional
public class CodeDataJpaTest {

	@Autowired
	private CommonCodeRepository commonCodeRepository;


	@Test
	@DisplayName("공통코드 조회")
	public void addCode() {

		CommonCodeEntity codeEntity = commonCodeRepository.save(CommonCodeEntity.builder().name("스터디").type(CodeType.COURSE_CATEGORY).build());
		
		assertThat(codeEntity, notNullValue());
	}
}
