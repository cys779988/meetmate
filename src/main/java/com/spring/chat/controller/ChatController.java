package com.spring.chat.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.chat.model.ChatMessage;
import com.spring.chat.service.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ChatController {
	
	// private final RedisPublisher redisPublisher;
	// private final ChatRoomRepository chatRoomRepository;
	private final RedisTemplate<String, Object> redisTemplate;
	private final JwtTokenProvider jwtTokenProvider;
	private final ChannelTopic channelTopic;
	
	@MessageMapping("/chat/message")
	public void message(ChatMessage message, @Header("token") String token) {
		String nickname = jwtTokenProvider.getUserNameFromJwt(token);
		
		message.setSender(nickname);
		
		if(ChatMessage.MessageType.ENTER.equals(message.getType())) {
			//chatRoomRepository.enterChatRoom(message.getRoomId());
			message.setSender("[알림]");
			message.setMessage(nickname + "님이 입장하셨습니다.");
		}
		//redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
		redisTemplate.convertAndSend(channelTopic.getTopic(), message);
	}
}
