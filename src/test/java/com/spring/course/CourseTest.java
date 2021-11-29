package com.spring.course;

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
import com.spring.course.controller.CourseController;
import com.spring.course.model.CourseDto;


//@SpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureMockMvc
class CourseTest {

	@Autowired
	CourseController courseController;
	
	MockMvc mockMvc;
	
	@BeforeEach
	public void setUp() throws Exception{
		mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
	}
	
	@AfterEach
	public void end() throws Exception{
	}
	
	@Test
	public void boardListTest() throws Exception{
		mockMvc.perform(get("/course/"))
		.andExpect(status().isOk())
		.andExpect(view().name("board/list"))
		.andDo(print());
	}
	
	@Test
	public void boardSaveTest() throws Exception{
		CourseDto dto = CourseDto.builder()
				.title("테스트제목")
				.content("테스트내용")
				.category(1L)
				.divclsNo(5)
				.maxNum(20)
				.curNum(0)
				.build();
		
		ObjectMapper objectMapper = new ObjectMapper();
		MultiValueMap<String, String> queryParam = new LinkedMultiValueMap<>();
		
		Map<String, String> map = objectMapper.convertValue(dto, new TypeReference<Map<String, String>>() {});
		
		queryParam.setAll(map);
		
		mockMvc.perform(post("/course/add")
				.params(queryParam))
		.andExpect(redirectedUrl("/"))
		.andDo(print());
	}
	

}
