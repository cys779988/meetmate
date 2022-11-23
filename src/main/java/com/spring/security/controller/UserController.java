package com.spring.security.controller;

import java.util.Map;

import javax.servlet.http.HttpSessionListener;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.common.config.LoginUser;
import com.spring.log.model.LogType;
import com.spring.log.service.PerLogging;
import com.spring.security.model.SessionUser;
import com.spring.security.model.UserDto;
import com.spring.security.service.UserService;

import lombok.AllArgsConstructor;



@Controller
@AllArgsConstructor
public class UserController implements HttpSessionListener{
	private UserService userService;
	
	@GetMapping("/user/signup")
	public String signupView(Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("userDto", userDto);
		return "/user/signup";
	}

	@GetMapping("/user/password")
	public String passwordView(Model model) {
		return "/user/password";
	}

	@PerLogging(LogType.PASSWORD)
	@PostMapping("/user/password")
	public String resetPassword(UserDto userDto) {
		userService.resetPassword(userDto);
		return "/user/login";
	}

	@PostMapping("/user/signup")
	public String signup(@Valid UserDto userDto, Errors errors, Model model) {
		if(errors.hasErrors()) {
			model.addAttribute("userDto", userDto);
			
			Map<String, String> validatorResult = userService.validateHandling(errors);
			for (String key : validatorResult.keySet()) {
				model.addAttribute(key, validatorResult.get(key));
			}
			return "/user/signup";
		} else if(userService.findByEmail(userDto.getEmail())) {
			model.addAttribute("valid_email", "해당 이메일 계정이 존재합니다.");
			return "/user/signup";
		} else {
			userService.joinUser(userDto);
		}
		
		return "redirect:/user/login";
	}

	@GetMapping("/user/login")
	public String loginView() {
		return "/user/login";
	}

	@GetMapping("/user/info")
	public String userInfoView(@LoginUser SessionUser user, Model model) {
		SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user", user);
		return "/user/myinfo";
	}

	@GetMapping("/admin")
	public String adminView() {
		return "/admin";
	}
	
	@GetMapping("/denied")
	public String accessDenied(@RequestParam(value = "exception", required = false) String exception, Model model) {
		model.addAttribute("exception", exception);
		return "/errors/403";
	}
}
