/*
// sockjs 설정

package com.spring.chat.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.chat.model.ChatMessage;
import com.spring.chat.model.ChatRoom;
import com.spring.chat.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {
	private final ObjectMapper objectMapper;
	private final ChatService chatService;
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// TODO Auto-generated method stub
		String payload = message.getPayload();
		log.info("payload {}", payload);
		
		ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
		ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
		room.handleActions(session, chatMessage, chatService);
	}
}
*/