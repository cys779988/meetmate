package com.spring.chat.model;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoom implements Serializable {
	
	private static final long serialVersionUID = -9205652047035521508L;
	
	private String roomId;
	private String name;
	
	public static ChatRoom create(String name) {
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.roomId = UUID.randomUUID().toString();
		chatRoom.name = name;
		return chatRoom;
	}
	
	/*
	 * pub/sub 방식을 이용하면 구독자 관리가 알아서 되므로 웹소켓 세션 관리가 필요없어짐
	 * 발송의 구현도 알아서 해결됨
	 * 클라이언트에게 메시지를 발송하는 구현이 필요 없어짐
	private Set<WebSocketSession> sessions = new HashSet<>();

	
	@Builder
	public ChatRoom(String roomId, String name) {
		this.roomId = roomId;
		this.name = name;
	}
	
	public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
		if(chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
			sessions.add(session);
			chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
		}
		sendMessage(chatMessage, chatService);
	}
	
	public <T> void sendMessage(T message, ChatService chatService) {
		sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
	}
	*/
}
