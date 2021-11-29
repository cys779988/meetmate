package com.spring.course;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.course.controller.CourseRestController;
import com.spring.course.model.CourseDto;
import com.spring.security.config.SecurityConfig;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CourseRestController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
class CourseRestTest {
	@Autowired
	ObjectMapper objectMapper;
	
    @Autowired
    private MockMvc mockMvc;
	
    @Test
    @WithMockUser(roles = "ADMIN")
    public void getCourses() throws Exception {
        mockMvc.perform(get("/api/course/get")
        		.contentType(MediaType.APPLICATION_JSON_VALUE))
        .andDo(print())
		.andExpect(status().isOk());
    }
    
	@Test
	@WithMockUser(roles = "GUEST")
	public void addCourse() throws Exception{
		List<Object> node = new ArrayList<>();
		List<Object> edge = new ArrayList<>();
		CourseDto dto = CourseDto.builder()
				.title("테스트제목")
				.content("테스트내용")
				.category(1L)
				.divclsNo(5)
				.maxNum(20)
				.curNum(0)
				.node(node)
				.edge(edge)
				.build();

		mockMvc.perform(post("/api/course/add")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(dto)))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Test
	@WithMockUser(roles = "GUEST")
	public void editCourse() throws Exception{
		List<Object> node = new ArrayList<>();
		List<Object> edge = new ArrayList<>();
		CourseDto dto = CourseDto.builder()
				.title("테스트제목")
				.content("테스트내용")
				.category(1L)
				.divclsNo(5)
				.maxNum(20)
				.curNum(0)
				.node(node)
				.edge(edge)
				.build();
		
		mockMvc.perform(put("/api/course/4")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(dto)))
				.andDo(print())
				.andExpect(status().isOk());
	}


	@Test
	@WithMockUser(roles = "GUEST")
	public void deleteCourse() throws Exception{
		mockMvc.perform(delete("/api/course/103")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
	}

}
