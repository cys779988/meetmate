package com.spring.code.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommonCodeVO {
	private Long id;
	private String name;
	
	@Builder
	public CommonCodeVO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}