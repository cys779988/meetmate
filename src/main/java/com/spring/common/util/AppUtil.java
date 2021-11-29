package com.spring.common.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import com.spring.security.model.UserEntity;


public class AppUtil{
	public static String getUser() {
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof DefaultOAuth2User) {
			DefaultOAuth2User userInfo = (DefaultOAuth2User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return userInfo.getAttribute("email");
		} else {
			UserEntity userInfo = (UserEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return userInfo.getEmail();
		}
	}
}
