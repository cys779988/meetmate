package com.spring.log.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LogType {
	
	PASSWORD("비밀번호 초기화"), LOGIN("로그인");
	
	private final String name;
}
