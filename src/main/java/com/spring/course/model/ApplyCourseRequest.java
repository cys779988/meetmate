package com.spring.course.model;

import org.springframework.data.domain.Pageable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplyCourseRequest {
	private String userId;
	private Long category;
	private Pageable page;
	
	@Builder
	public ApplyCourseRequest(String userId, Long category, Pageable page) {
		super();
		this.userId = userId;
		this.category = category;
		this.page = page;
	}
	
	
}
