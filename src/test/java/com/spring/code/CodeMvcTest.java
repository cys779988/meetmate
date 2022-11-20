package com.spring.code;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.code.controller.CommonCodeController;
import com.spring.code.controller.CommonCodeRestController;
import com.spring.code.model.CodeRequest;
import com.spring.code.model.CodeType;
import com.spring.code.service.CommonCodeService;
import com.spring.security.config.SecurityConfig;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {CommonCodeRestController.class, CommonCodeController.class}, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WithMockUser(roles = "ADMIN")
class CodeMvcTest {
	@Autowired
	ObjectMapper objectMapper;
	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommonCodeService commonCodeService;
    
    @Test
    public void getCodesGridForm() throws Exception {
    	MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
    	param.add("page", "1");
    	param.add("perPage", "10");
    	param.add("search", CodeType.COURSE_CATEGORY.getKey());
    	
    	mockMvc.perform(get("/admin/api/code/grid")
    			.params(param)
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
    			.accept(MediaType.APPLICATION_JSON_VALUE))
    	.andDo(print())
    	.andExpect(status().isOk());
    }
    
    @Test
    public void getCodes() throws Exception {
    	MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
    	param.add("search", CodeType.COURSE_CATEGORY.getKey());
    	
        mockMvc.perform(get("/admin/api/code")
        			.params(param)
	        		.contentType(MediaType.APPLICATION_JSON_VALUE)
	        		.accept(MediaType.APPLICATION_JSON_VALUE))
		        .andDo(print())
				.andExpect(status().isOk());
    }
    
	@Test
	public void addCodes() throws Exception{
		CodeRequest codeRequest = new CodeRequest();
		
		mockMvc.perform(post("/admin/api/code").with(csrf())
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(codeRequest)))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Test
	public void deleteCodes() throws Exception {
		CodeRequest codeRequest = new CodeRequest();
		
		mockMvc.perform(delete("/admin/api/code").with(csrf())
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(codeRequest)))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}
}
