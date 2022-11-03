package com.spring.common.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class GridForm {
	private boolean result;
	
	private GridResponse data;
}
