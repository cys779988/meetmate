package com.spring.board.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class BoardRequest {
	
	private Long boardId;
	
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
	
	private List<MultipartFile> files = new ArrayList<>();
	
	private List<Long> deleteFiles = new ArrayList<>();
}
