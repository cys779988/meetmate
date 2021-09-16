package com.spring;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.spring.board.controller.BoardRestController;
import com.spring.board.service.BoardService;
import com.spring.security.config.SecurityConfig;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BoardRestController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
class ControllerTest {
	
	    @Autowired
	    private MockMvc mockMvc;

	    @MockBean
	    private BoardService boardService;

	    @Test
	    @WithMockUser(roles = "GUEST")
	    public void getNewsTest() throws Exception {
	        mockMvc.perform(get("/api/board/get"))
	        .andExpect(status().isOk())
	        .andDo(print());
	    }

}
