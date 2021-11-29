package com.spring.security.model;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.spring.common.model.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_users")
public class UserEntity extends BaseTimeEntity implements Serializable {
	
	private static final long serialVersionUID = -6230866903313613836L;

    @Column(nullable = false)
    private String name;

    @Id
    @Column(nullable = false)
    private String email;

    @Column
    private String password;
    
    @Column
    private String picture;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public UserEntity(String name, String email, String password, String picture, Role role) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public UserEntity update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}