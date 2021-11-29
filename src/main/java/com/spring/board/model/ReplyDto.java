package com.spring.board.model;

import lombok.*;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.spring.security.model.UserEntity;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReplyDto {
	private Long id;
	
	private Long boardId;
	
	private String registrant;
	
    @NotBlank(message = "댓글을 입력해주세요.")
	private String content;

	private LocalDateTime createdDate;
	
    public ReplyEntity toEntity(){
    	ReplyEntity replyEntity = ReplyEntity.builder()
                .id(id)
                .board((BoardEntity.builder().id(boardId)).build())
                .registrant((UserEntity.builder().email(registrant)).build())
                .content(content)
                .build();
        return replyEntity;
    }

    @Builder
    public ReplyDto(Long id, Long boardId, String registrant, String content, LocalDateTime createdDate) {
        this.id = id;
        this.registrant = registrant;
        this.boardId = boardId;
        this.content = content;
        this.createdDate = createdDate;
    }
}