package com.spring.security.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomLogoutHandler implements LogoutHandler{
	
	private String sessionAttrToDeleteOnLoginFail = HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		logout(request);
	}

	private void logout(HttpServletRequest request) {
		log.debug("Interactive login attempt was unsuccessful.");
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(this.sessionAttrToDeleteOnLoginFail);
		}
	}
}
