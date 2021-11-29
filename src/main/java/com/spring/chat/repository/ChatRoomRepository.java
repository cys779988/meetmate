package com.spring.chat.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.spring.chat.model.ChatRoom;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {
	
	// 채팅방(topic)에 발행되는 메시지를 처리할 Listener
	//private final RedisMessageListenerContainer redisMessageListener;
	
	// 구독 처리 서비스
	//private final RedisSubscriber redisSubscriber;
	
	// Redis
	private static final String CHAT_ROOMS = "CHAT_ROOM";
	private final RedisTemplate<String, Object> redisTemplate;
	private HashOperations<String, String, ChatRoom> opsHashChatRoom;
	//private Map<String, ChannelTopic> topics;
	
	
	@PostConstruct
	private void init() {
		opsHashChatRoom = redisTemplate.opsForHash();
		//topics = new HashMap<>();
	}
	
	public List<ChatRoom> findAllRoom() {
		return opsHashChatRoom.values(CHAT_ROOMS);
	}
	
	public ChatRoom findRoomById(String id) {
		return opsHashChatRoom.get(CHAT_ROOMS, id);
	}
	
	public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
	
	/*
	public void enterChatRoom(String roomId) {
		ChannelTopic topic = topics.get(roomId);
		if(topic == null) {
			topic = new ChannelTopic(roomId);
			redisMessageListener.addMessageListener(redisSubscriber, topic);
			topics.put(roomId, topic);
		}
	}
	*/
	
	/*
	public ChannelTopic getTopic(String roomId) {
		return topics.get(roomId);
	}
	 */
}
