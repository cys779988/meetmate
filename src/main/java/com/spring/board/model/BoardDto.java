package com.spring.board.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import com.spring.file.model.FileDto;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id;
    
    private String registrant;
    
    private String title;
    
    private String content;
    
    private List<FileDto> fileList;
    
    private LocalDateTime createdDate;
    
    @Builder
    public BoardDto(Long id, String registrant, String title, String content, List<FileDto> fileList, LocalDateTime createdDate) {
        this.id = id;
        this.registrant = registrant;
        this.title = title;
        this.content = content;
        this.fileList = fileList;
        this.createdDate = createdDate;
    }
}