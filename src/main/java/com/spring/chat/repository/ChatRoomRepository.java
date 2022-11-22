package com.spring.chat.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.spring.chat.model.ChatMessage;
import com.spring.chat.model.ChatRoom;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {
	
	// 채팅방(topic)에 발행되는 메시지를 처리할 Listener
	//private final RedisMessageListenerContainer redisMessageListener;
	
	// 구독 처리 서비스
	//private final RedisSubscriber redisSubscriber;
	
	private static final String CHAT_ROOMS = "CHAT_ROOM";
	private final RedisTemplate<String, Object> redisTemplate;
	private HashOperations<String, String, ChatRoom> chatRooms;
	private ListOperations<String, Object> chatMessageList;
	
	@PostConstruct
	private void init() {
		chatRooms = redisTemplate.opsForHash();
		chatMessageList = redisTemplate.opsForList();
	}
	
	public List<ChatRoom> findAllRoom() {
		return chatRooms.values(CHAT_ROOMS);
	}
	
	public ChatRoom findRoomById(String id) {
		return chatRooms.get(CHAT_ROOMS, id);
	}
	
	public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        String roomId = chatRoom.getRoomId();
        
        chatRooms.put(CHAT_ROOMS, roomId, chatRoom);
        chatMessageList.rightPush(roomId, ChatMessage.initMessage(roomId));
        
        return chatRoom;
    }
	
	public void addChatMessage(ChatMessage chatMessage) {
		chatMessageList.rightPush(chatMessage.getRoomId(), chatMessage);
	}

	public List<?> getChatMessages(String roomId) {
		return chatMessageList.range(roomId, 0, -1);
	}
}
