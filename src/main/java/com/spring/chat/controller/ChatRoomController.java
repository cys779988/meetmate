package com.spring.chat.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.chat.model.ChatRoom;
import com.spring.chat.model.LoginInfo;
import com.spring.chat.repository.ChatRoomRepository;
import com.spring.chat.service.JwtTokenProvider;
import com.spring.common.util.AppUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

	private final ChatRoomRepository chatRoomRepository;
	private final JwtTokenProvider jwtTokenProvider;
	
	@GetMapping("/room")
	public String rooms(Model model) {
		return "/chat/room";
	}
	
	@GetMapping("/rooms")
	@ResponseBody
	public List<ChatRoom> room() {
		return chatRoomRepository.findAllRoom();
	}
	
	@PostMapping("/room")
	@ResponseBody
	public ChatRoom createRoom(@RequestParam String name) {
		return chatRoomRepository.createChatRoom(name);
	}
	
	@GetMapping("/room/enter/{roomId}")
	public String roomDetail(Model model, @PathVariable String roomId) {
		model.addAttribute("roomId", roomId);
		model.addAttribute("user", AppUtil.getUser());
		model.addAttribute("chatMessage", chatRoomRepository.getChatMessages(roomId));
		return "/chat/roomdetail";
	}
	
	@GetMapping("/room/{roomId}")
	@ResponseBody
	public ChatRoom roomInfo(@PathVariable String roomId) {
		return chatRoomRepository.findRoomById(roomId);
	}
	
	@GetMapping("/user")
	@ResponseBody
	public LoginInfo getUserInfo() {
		String name =  AppUtil.getUser();
		return LoginInfo.builder().name(name).token(jwtTokenProvider.generateToken(name)).build();
	}
}
