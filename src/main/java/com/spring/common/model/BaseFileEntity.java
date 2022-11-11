package com.spring.common.model;

import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
public abstract class BaseFileEntity extends BaseTimeEntity{
	
	private String filePath;
	
	private Long fileSize;
	
	private String saveFileName;
	
	private String originalFileName;
}
