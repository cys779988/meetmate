package com.spring.common.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import com.spring.security.model.UserEntity;


public class AppUtil{
	public static String getUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof DefaultOAuth2User) {
			DefaultOAuth2User userInfo = (DefaultOAuth2User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return userInfo.getAttribute("email");
		} else if(principal instanceof UserEntity) {
			UserEntity userInfo = (UserEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return userInfo.getEmail();
		} else {
			User userInfo = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return userInfo.getUsername();
		}
	}
}
