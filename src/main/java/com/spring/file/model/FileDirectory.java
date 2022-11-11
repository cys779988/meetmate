package com.spring.file.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileDirectory {
	BOARD("board/"), COURSE("course/");
	
	private final String dirName;
}
