package com.spring.course.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Assignment {
	Y("assignment"), N("unassignment");
	
	private String value;
}
