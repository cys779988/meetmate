package com.spring.group;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

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
import com.spring.course.controller.GroupMngController;
import com.spring.course.controller.GroupMngRestController;
import com.spring.course.model.GroupDto;
import com.spring.course.service.CourseService;
import com.spring.course.service.GroupMngService;
import com.spring.security.config.SecurityConfig;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {GroupMngRestController.class, GroupMngController.class}, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
@AutoConfigureMockMvc
@WithMockUser(roles = "GUEST")
class GroupMvcTest {
	@Autowired
	ObjectMapper objectMapper;
	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;
    
    @MockBean
    private GroupMngService groupMngService;

	@Test
	public void groupMngViewTest() throws Exception{
		mockMvc.perform(get("/group"))
		.andExpect(status().isOk())
		.andExpect(view().name("group/list"))
		.andDo(print());
	}
	
	@Test
	public void basicFormViewTest() throws Exception{
		mockMvc.perform(get("/group/basicForm/1"))
		.andExpect(status().isOk())
		.andExpect(view().name("group/basicForm"))
		.andDo(print());
	}
	
	@Test
	public void divFormViewTest() throws Exception{
		mockMvc.perform(get("/group/divForm/1"))
		.andExpect(status().isOk())
		.andExpect(view().name("group/divForm"))
		.andDo(print());
	}
	
    @Test
    public void getGroupUsers() throws Exception {
        mockMvc.perform(get("/api/group/1")
	        		.contentType(MediaType.APPLICATION_JSON_VALUE)
	        		.accept(MediaType.APPLICATION_JSON_VALUE))
		        .andDo(print())
				.andExpect(status().isOk());
    }
    
	@Test
	public void applyGroup() throws Exception{
		mockMvc.perform(post("/api/group/apply/1").with(csrf())
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Test
	public void autoDivide() throws Exception{
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		
		mockMvc.perform(post("/api/group/autoDivide").with(csrf())
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(param)))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	public void resetDivide() throws Exception{
		mockMvc.perform(post("/api/group/resetDivide/1").with(csrf())
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isOk());
	}

	
	@Test
	public void updateDivide() throws Exception{
		List<GroupDto> groupList = Arrays.asList(GroupDto.builder()
								.courseId(1L)
								.memberId("user1")
								.divNo(1L).build()
					, GroupDto.builder()
					.courseId(1L)
					.memberId("user2")
					.divNo(2L).build());
		
		mockMvc.perform(post("/api/group/updateDivide").with(csrf())
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(groupList)))
				.andDo(print())
				.andExpect(status().isOk());
	}

}
