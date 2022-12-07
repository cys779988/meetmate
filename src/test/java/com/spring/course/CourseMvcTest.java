package com.spring.course;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;

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
import com.spring.code.service.CommonCodeService;
import com.spring.course.controller.CourseController;
import com.spring.course.controller.CourseRestController;
import com.spring.course.model.CourseDto;
import com.spring.course.service.CourseService;
import com.spring.security.config.SecurityConfig;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {CourseRestController.class, CourseController.class}, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
@AutoConfigureMockMvc
@WithMockUser(roles = "GUEST")
class CourseMvcTest {
	@Autowired
	ObjectMapper objectMapper;
	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @MockBean
    private CommonCodeService commonCodeService;
	
	@Test
	public void courseListTest() throws Exception{
		mockMvc.perform(get("/course"))
		.andExpect(status().isOk())
		.andExpect(view().name("course/list"))
		.andDo(print());
	}
	
    @Test
    public void getCourses() throws Exception {
    	MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
    	param.add("page", "1");
    	param.add("perPage", "10");
    	
        mockMvc.perform(get("/api/course")
        			.params(param)
	        		.contentType(MediaType.APPLICATION_JSON_VALUE)
	        		.accept(MediaType.APPLICATION_JSON_VALUE))
		        .andDo(print())
				.andExpect(status().isOk());
    }
    
	@Test
	public void addCourse() throws Exception{
		CourseDto courseDto = CourseDto.builder()
						.title("테스트제목")
						.registrant("admin")
						.content("테스트내용")
						.category(1L)
						.applyStartDate(LocalDate.now())
						.applyEndDate(LocalDate.now())
						.startDate(LocalDate.now())
						.endDate(LocalDate.now())
						.divclsNo(5)
						.maxNum(20)
						.curNum(0)
						.node(null)
						.edge(null)
						.build();
		
		mockMvc.perform(post("/api/course").with(csrf())
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(courseDto)))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Test
	public void expect4xxErrorAddCourse() throws Exception{
		CourseDto courseDto = CourseDto.builder()
				.title("테스트제목")
				.registrant("admin")
				.category(1L)
				.divclsNo(5)
				.maxNum(20)
				.curNum(0)
				.node(null)
				.edge(null)
				.build();
		
		mockMvc.perform(post("/api/course").with(csrf())
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(courseDto)))
			.andDo(print())
			.andExpect(status().is4xxClientError())
			.andReturn();
	}
	
	@Test
	public void editCourse() throws Exception{
		CourseDto courseDto = CourseDto.builder()
				.title("테스트제목")
				.registrant("admin")
				.content("테스트내용")
				.category(1L)
				.applyStartDate(LocalDate.now())
				.applyEndDate(LocalDate.now())
				.startDate(LocalDate.now())
				.endDate(LocalDate.now())
				.divclsNo(5)
				.maxNum(20)
				.curNum(0)
				.node(null)
				.edge(null)
				.build();
		
		mockMvc.perform(put("/api/course/1").with(csrf())
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(courseDto)))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void expect4xxErrorEditCourse() throws Exception{
		CourseDto courseDto = CourseDto.builder()
				.title("테스트제목")
				.registrant("admin")
				.content("")
				.category(1L)
				.divclsNo(5)
				.maxNum(20)
				.curNum(0)
				.node(null)
				.edge(null)
				.build();
		
		mockMvc.perform(put("/api/course/1").with(csrf())
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(courseDto)))
			.andDo(print())
			.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void deleteBoard() throws Exception{
		mockMvc.perform(delete("/api/course/103").with(csrf())
					.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
	}

}
