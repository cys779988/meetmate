package com.spring.chat.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.spring.chat.model.ChatMessage.MessageType.*;

public final class ChatMessageFormatter {
	private ChatMessageFormatter() {}
	
	public static ChatMessage messageFormat(ChatMessage message, String nickname) {
		
		message.setSender(nickname);
		message.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		
		checkAndConvertMessageType(message, nickname);
		
		return message;
	}
	
	private static void checkAndConvertMessageType(ChatMessage message, String nickname) {
		if(ENTER.equals(message.getType())) {
			message.setSender("[알림]");
			message.setMessage(nickname + "님이 입장하셨습니다.");
		} else {
			message.setType(TALK);
		}
	}
}
