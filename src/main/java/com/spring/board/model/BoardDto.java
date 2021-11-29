package com.spring.board.model;

import lombok.*;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.spring.security.model.UserEntity;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id;
    
    private String registrant;
    
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
    
    private LocalDateTime createdDate;
    
    public BoardEntity toEntity(){
        BoardEntity boardEntity = BoardEntity.builder()
                .id(id)
                .registrant((UserEntity.builder().email(registrant)).build())
                .title(title)
                .content(content)
                .build();
        return boardEntity;
    }

    @Builder
    public BoardDto(Long id, String registrant, String title, String content, LocalDateTime createdDate) {
        this.id = id;
        this.registrant = registrant;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }
}