package com.spring.chat.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.chat.model.ChatMessage;
import com.spring.chat.model.ChatMessageFormatter;
import com.spring.chat.repository.ChatRoomRepository;
import com.spring.chat.service.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ChatController {
	
	private final ChatRoomRepository chatRoomRepository;
	private final RedisTemplate<String, Object> redisTemplate;
	private final JwtTokenProvider jwtTokenProvider;
	private final ChannelTopic channelTopic;
	
	@MessageMapping("/chat/message")
	public void message(ChatMessage message, @Header("token") String token) {
		String nickname = jwtTokenProvider.getUserNameFromJwt(token);
		
		ChatMessage formatMessage = ChatMessageFormatter.messageFormat(message, nickname);

		chatRoomRepository.addChatMessage(formatMessage);

		redisTemplate.convertAndSend(channelTopic.getTopic(), formatMessage);
	}
}
