package com.spring.security.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.spring.common.model.BaseTimeEntity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto extends BaseTimeEntity{
	
	@NotBlank(message = "이름을 입력해주세요.")
	private String name;
	
	@NotBlank(message = "이메일을 입력해주세요.")
	@Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;
	
	@NotBlank(message = "비밀번호를 입력해주세요.")
	//@Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    transient private String password;
	
    private String picture;
    private Role role;

    public UserEntity toEntity() {
    	return UserEntity.builder()
    			.name(name)
    			.password(password)
    			.email(email)
    			.picture(picture)
    			.role(role)
    			.build();
    }
    
    @Builder
    public UserDto(String name, String password, String email, String picture, Role role) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }
}