/*
 * package com.spring.security.config;
 * 
 * import java.util.Collection;
 * 
 * 
 * import org.springframework.security.authentication.AuthenticationProvider;
 * import org.springframework.security.authentication.BadCredentialsException;
 * import org.springframework.security.authentication.
 * UsernamePasswordAuthenticationToken; import
 * org.springframework.security.core.Authentication; import
 * org.springframework.security.core.AuthenticationException; import
 * org.springframework.security.core.GrantedAuthority; import
 * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 * 
 * import com.spring.security.service.UserService;
 * 
 * import lombok.RequiredArgsConstructor;
 * 
 * @RequiredArgsConstructor public class CustomAuthenticationProvider implements
 * AuthenticationProvider {
 * 
 * private final UserService userService;
 * 
 * @Override public Authentication authenticate(Authentication authentication)
 * throws AuthenticationException { Collection<? extends GrantedAuthority>
 * authorities;
 * 
 * String id = authentication.getName(); String password = (String)
 * authentication.getCredentials();
 * 
 * UserInformation user = (UserInformation)
 * userService.loadUserByUsername(id);// ID 확인 user.getUsername(); authorities =
 * user.getAuthorities();
 * 
 * System.out.println("AUTHORIES :: " + authorities);
 * 
 * BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
 * 
 * if(!bCryptPasswordEncoder.matches(password, user.getPassword())) // PW 확인
 * throw new BadCredentialsException("wrongPassword"); // PW 틀림
 * 
 * return new UsernamePasswordAuthenticationToken(user, user.getPassword(),
 * authorities); // 인증에 성공하면 만들어지는 Authentication 객체 // 인증에 성공한 인증객체는
 * SecurityContext에 저장. 이 인증객체를 요청 전반에 걸쳐 사용하기 위해 SecurityContextHolder에 담음 //
 * Spring Security에서는 ThreadLocal을 사용하여 요청 전반에 걸쳐 인증객체를 전달 // Thread가 수행하는 호출
 * 스택이 어디에 있던 수행하는 작업이 Thread 본인이라면 어디에서든 접근할 수 있다. //
 * SecurityContextHolder.getContext().getAuthentication()만 호출한다면 인증된 사용자의 정보를 얻을
 * 수 있다. // SecurityContextHolder는 또 내부에 SecurityContextHolderStrategy라는 필드를 갖는데
 * 실제로 홀더 저장에 대한 책임을 수행함
 * 
 * }
 * 
 * @Override public boolean supports(Class<?> authentication) { return
 * authentication.equals(UsernamePasswordAuthenticationToken.class); } }
 */