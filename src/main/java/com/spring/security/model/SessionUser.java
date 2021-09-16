package com.spring.security.model;

import java.io.Serializable;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(UserEntity user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}