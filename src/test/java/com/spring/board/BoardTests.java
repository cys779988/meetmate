package com.spring.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.board.controller.BoardController;
import com.spring.board.model.BoardDto;


//@SpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureMockMvc
class BoardTests {

	@Autowired
	BoardController boardController;
	
	MockMvc mockMvc;
	
	@BeforeEach
	public void setUp() throws Exception{
		mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
	}
	
	@AfterEach
	public void end() throws Exception{
	}
	
	@Test
	public void boardListTest() throws Exception{
		System.out.println("board테스트");
		mockMvc.perform(get("/board/"))
		.andExpect(status().isOk())
		.andExpect(view().name("board/list"))
		.andDo(print());
	}
	
	@Test
	public void boardSaveTest() throws Exception{
		BoardDto boardDto = BoardDto.builder()
		        .id(Long.parseUnsignedLong("1279"))
		        .writer("홍길동")
		        .title("제목테스트")
		        .content("내용테스트")
		        .build();
		
		ObjectMapper objectMapper = new ObjectMapper();
		MultiValueMap<String, String> queryParam = new LinkedMultiValueMap<>();
		
		Map<String, String> map = objectMapper.convertValue(boardDto, new TypeReference<Map<String, String>>() {});
		
		queryParam.setAll(map);
		
		mockMvc.perform(post("/board/add")
				.params(queryParam))
		.andExpect(redirectedUrl("/"))
		.andDo(print());
	}
	

}
