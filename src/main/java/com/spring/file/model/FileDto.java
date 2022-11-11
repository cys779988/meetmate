package com.spring.file.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class FileDto {

	private Long id;
	
	private String filePath;
	
	private Long fileSize;
	
	private String saveFileName;
	
	private String originalFileName;
	
	@Builder
	public FileDto(Long id, String filePath, Long fileSize, String saveFileName, String originalFileName) {
		this.id = id;
		this.filePath = filePath;
		this.fileSize = fileSize;
		this.saveFileName = saveFileName;
		this.originalFileName = originalFileName;
	}
}
