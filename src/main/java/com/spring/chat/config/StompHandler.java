package com.spring.chat.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.spring.chat.service.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor{
	
	private final JwtTokenProvider jwtTokenProvider;
	
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		// TODO Auto-generated method stub
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		// websocket 연결시 Header의 jwt token 검증
		if(StompCommand.CONNECT == accessor.getCommand()) {
			jwtTokenProvider.validateToken(accessor.getFirstNativeHeader("token"));
		}
		return message;
	}
}
