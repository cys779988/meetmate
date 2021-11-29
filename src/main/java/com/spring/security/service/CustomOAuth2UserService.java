package com.spring.security.service;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.spring.security.model.OAuthAttributes;
import com.spring.security.model.SessionUser;
import com.spring.security.model.UserEntity;
import com.spring.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	private final UserRepository userRepository;
	private final HttpSession httpSession;

	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		// 현재 로그인 진행중인 서비스 구분
		
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
				.getUserNameAttributeName();
		// OAuth2 로그인 진행시 Key가 되는 필드값
		// Google의 경우 기본적으로 코드를 지원하지만("sub"), Naver, Kakao 등은 지원하지 않음
		// 이후 Naver로그인과 Google로그인을 동시지원할 때 사용
		
		OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
		// OAuth2User는 OAuth2UserService 로 만들어진 OAuth2User 객체를 참조하는 변수
		// OAuthAttributes attributes는 OAuth2UserService를 통해 가져온 OAuth2User 클래스의 attribute를 담을 클래스
		
		UserEntity user = saveOrUpdate(attributes);
		httpSession.setAttribute("user", new SessionUser(user));
		// User 클래스를 사용하면 안돼서 세션에 사용자정보를 저장하기위한 DTO 클래스 SessionUser를 생성
		// 직렬화된 클래스를 넣어야하는데 Entity 클래스는 다른 Entity 클래스와 관계(@OneToMany, @ManyToMany 등)가 형성될 수 있기 때문에
		// 직렬화 대상에 자식들까지 포함되니 성능 이슈, 부수 효과가 발생할 확률이 높음
		// 직렬화 기능을 가진 DTO를 하나 추가로 만드는 것이 이후 운영 및 유지보수 좋음
		
		return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
				attributes.getAttributes(), attributes.getNameAttributeKey());
	}

	private UserEntity saveOrUpdate(OAuthAttributes attributes) {
		UserEntity user = userRepository.findById(attributes.getEmail())
				.map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
				.orElse(attributes.toEntity());

		return userRepository.save(user);
	}
}
