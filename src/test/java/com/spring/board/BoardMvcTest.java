package com.spring.board;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.board.controller.BoardController;
import com.spring.board.controller.BoardRestController;
import com.spring.board.service.BoardService;
import com.spring.security.config.SecurityConfig;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {BoardRestController.class, BoardController.class}, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WithMockUser(roles = "GUEST")
class BoardMvcTest {
	@Autowired
	ObjectMapper objectMapper;
	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;
	
	@Test
	public void boardListTest() throws Exception{
		mockMvc.perform(get("/board/"))
		.andExpect(status().isOk())
		.andExpect(view().name("board/list"))
		.andDo(print());
	}
	
    @Test
    public void getBoards() throws Exception {
    	MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
    	param.add("page", "1");
    	param.add("perPage", "10");
    	
        mockMvc.perform(get("/api/board/")
        			.params(param)
	        		.contentType(MediaType.APPLICATION_JSON_VALUE)
	        		.accept(MediaType.APPLICATION_JSON_VALUE))
		        .andDo(print())
				.andExpect(status().isOk());
    }
    
	@Test
	public void addBoard() throws Exception{
		MockMultipartFile file = new MockMultipartFile("files", "testFile.png", "image/png", "file".getBytes());
		
		mockMvc.perform(multipart("/api/board/")
					.file(file)
					.part(new MockPart("title", "추가된 제목".getBytes()))
					.part(new MockPart("content", "추가된 내용".getBytes()))
					.with(csrf()))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Test
	public void expect4xxErrorAddBoard() throws Exception{
		MockMultipartFile file = new MockMultipartFile("files", "testFile.png", "image/png", "file".getBytes());
		
		mockMvc.perform(multipart("/api/board/")
				.file(file)
				.part(new MockPart("content", "추가된 내용".getBytes()))
				.with(csrf()))
			.andDo(print())
			.andExpect(status().is4xxClientError())
			.andReturn();
	}
	
	@Test
	public void editBoard() throws Exception{
		MockMultipartFile file = new MockMultipartFile("files", "testFile.png", "image/png", "file".getBytes());
		mockMvc.perform(multipart("/api/board/1")
				.file(file)
				.part(new MockPart("deleteFiles", "1".getBytes()))
				.part(new MockPart("deleteFiles", "2".getBytes()))
				.part(new MockPart("title", "추가된 제목".getBytes()))
				.part(new MockPart("content", "추가된 내용".getBytes()))
				.with(csrf()))
			.andDo(print())
			.andExpect(status().isOk());
	}


	@Test
	public void deleteBoard() throws Exception{
		mockMvc.perform(delete("/api/board/103").with(csrf())
					.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
	}

}
