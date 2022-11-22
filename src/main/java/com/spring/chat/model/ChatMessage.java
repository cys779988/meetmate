package com.spring.chat.model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ChatMessage {
	
	public enum MessageType {
		ENTER, TALK
	}
	private MessageType type;
	private String roomId;
	private String sender;
	private String message;
	private String createdDate;
	
	public static ChatMessage initMessage(String roomId) {
		return ChatMessage.builder()
					.roomId(roomId)
					.sender("운영자")
					.message("채팅방이 개설되었습니다.")
					.createdDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
					.build();
	}
	
	@Builder
	public ChatMessage(MessageType type, String roomId, String sender, String message, String createdDate) {
		this.type = type;
		this.roomId = roomId;
		this.sender = sender;
		this.message = message;
		this.createdDate = createdDate;
	}
}
