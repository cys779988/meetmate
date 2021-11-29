package com.spring.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.board.controller.BoardRestController;
import com.spring.board.model.BoardDto;
import com.spring.board.repository.BoardRepository;
import com.spring.board.service.BoardService;
import com.spring.security.config.SecurityConfig;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BoardRestController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
class BoardRestTest {
	@Autowired
	ObjectMapper objectMapper;
	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;
	
    @Test
    @WithMockUser(roles = "GUEST")
    public void getBoards() throws Exception {
    	Map<String, Integer> param = new HashMap<>();
    	param.put("page", 1);
    	param.put("perPage", 10);
        mockMvc.perform(get("/api/board/get")
	        		.contentType(MediaType.APPLICATION_JSON_VALUE)
	        		.accept(MediaType.APPLICATION_JSON_VALUE)
	        		.content(objectMapper.writeValueAsString(param)))
		        .andDo(print())
				.andExpect(status().isOk());
    }
    
	@Test
	@WithMockUser(roles = "GUEST")
	public void addBoard() throws Exception{
		BoardDto boardDto = BoardDto.builder()
				.title("추가된 제목")
				.content("추가된 내용")
				.registrant("추가된 작성자")
				.build();

		mockMvc.perform(post("/api/board/add")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(boardDto)))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Test
	@WithMockUser(roles = "GUEST")
	public void editBoard() throws Exception{
		BoardDto boardDto = BoardDto.builder()
				.title("수정된 제목")
				.content("수정된 내용")
				.registrant("수정된 작성자")
				.build();
		
		mockMvc.perform(put("/api/board/4")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(boardDto)))
				.andDo(print())
				.andExpect(status().isOk());
	}


	@Test
	@WithMockUser(roles = "GUEST")
	public void deleteBoard() throws Exception{
		mockMvc.perform(delete("/api/board/103")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
	}

}
