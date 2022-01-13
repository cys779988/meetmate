package com.spring.board;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.board.controller.BoardController;
import com.spring.board.controller.BoardRestController;
import com.spring.board.model.BoardDto;
import com.spring.board.service.BoardService;
import com.spring.security.config.SecurityConfig;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {BoardRestController.class, BoardController.class}, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
@AutoConfigureMockMvc
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
		BoardDto boardDto = BoardDto.builder()
				.title("추가된 제목")
				.content("추가된 내용")
				.registrant("cys779988@naver.com")
				.build();
		
		/*
		 * Map<String, String> convertMap = objectMapper.convertValue(boardDto,
		 * Map.class);
		 * 
		 * MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		 * 
		 * param.setAll(convertMap);
		 */
		
		mockMvc.perform(post("/api/board/").with(csrf())
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(boardDto)))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Test
	public void editBoard() throws Exception{
		BoardDto boardDto = BoardDto.builder()
				.title("수정된 제목")
				.content("수정된 내용")
				.registrant("cys779988@naver.com")
				.build();
		
		mockMvc.perform(put("/api/board/1").with(csrf())
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(boardDto)))
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
