package com.spring.security.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class UserContext extends User{
	private static final long serialVersionUID = -666580827443131046L;
	
	private final UserEntity userEntity;
	
	public UserContext(UserEntity userEntity, Collection<? extends GrantedAuthority> authorities) {
		super(userEntity.getName(), userEntity.getPassword(), authorities);
		this.userEntity = userEntity;
	}
	
	public UserEntity getUser() {
		return userEntity;
	}
}
