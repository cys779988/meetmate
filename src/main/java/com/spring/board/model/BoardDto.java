package com.spring.board.model;

import lombok.*;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id;
    
    @NotBlank(message = "작성자는 필수값입니다.")
    private String writer;
    
    @NotBlank(message = "제목은 필수값입니다.")
    private String title;
    
    @NotBlank(message = "내용은 필수값입니다.")
    private String content;
    
    private LocalDateTime createdDate;
    
    private LocalDateTime modifiedDate;

    public BoardEntity toEntity(){
        BoardEntity boardEntity = BoardEntity.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .build();
        return boardEntity;
    }

    @Builder
    public BoardDto(Long id, String title, String content, String writer, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}