package com.spring.security.model;

import com.spring.common.model.BaseTimeEntity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto extends BaseTimeEntity{
	
	private String name;
    private String email;
    transient private String password;
    private String picture;
    private Role role;

	/*
	 * private Long id;
	 * 
	 * @NotBlank(message = "닉네임은 필수 입력 값입니다.") private String nickname;
	 * 
	 * @NotBlank(message = "이메일은 필수 입력 값입니다.")
	 * 
	 * @Email(message = "이메일 형식에 맞지 않습니다.") private String email;
	 * 
	 * @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	 * 
	 * @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message
	 * = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.") private
	 * String password;
	 */

    
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