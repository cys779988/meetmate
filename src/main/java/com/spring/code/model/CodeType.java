package com.spring.code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeType {
	COURSE_CATEGORY("COURSE_CATEGORY", "과정 카테고리");
	
	private String key;
	private String value;
}
