package com.spring.file.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileExtnFilter {
	EXE("exe"), SH("sh"), JS("js"), ALZ("alz");
	
	private final String extnName;
}
