package com.spring.code.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class CommonCodeVO {
	private String id;
	private String name;
	
	@Builder
	public CommonCodeVO(String id, String name) {
		this.id = id;
		this.name = name;
	}
}