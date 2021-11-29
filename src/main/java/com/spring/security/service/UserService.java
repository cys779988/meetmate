package com.spring.security.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.spring.common.exception.BusinessException;
import com.spring.common.model.ErrorCode;
import com.spring.security.model.Role;
import com.spring.security.model.UserContext;
import com.spring.security.model.UserEntity;
import com.spring.security.model.UserDto;
import com.spring.security.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{
	UserRepository userRepository;
	
	public Map<String, String> validateHandling(Errors errors) {
		Map<String, String> validatorResult = new HashMap<>();

		for (FieldError error : errors.getFieldErrors()) {
			String validKeyName = String.format("valid_%s", error.getField());
			validatorResult.put(validKeyName, error.getDefaultMessage());
		}

		return validatorResult;
	}

	public boolean findByEmail(String email) {
		Optional<UserEntity> entity = userRepository.findById(email);
		return entity.isPresent();
	}
	
    @Transactional
    public String joinUser(UserDto userDto) {
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
    	userDto.setRole(Role.GUEST);
        return userRepository.save(userDto.toEntity()).getEmail();
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Optional<UserEntity> userEntityWrapper = userRepository.findById(userEmail);
        
        if(!userEntityWrapper.isPresent()) {
        	throw new UsernameNotFoundException("UsernameNotFoundException");
        }
        
        UserEntity userEntity = userEntityWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userEntity.getRoleKey()));
        
        UserContext userContext = new UserContext(userEntity, authorities);
        
        return userContext;
    }

	public void resetPassword(UserDto userDto) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Optional<UserEntity> entityWrapper = userRepository.findById(userDto.getEmail());
		if(entityWrapper.isPresent()) {
			UserEntity userEntity = entityWrapper.get();
			userEntity.setPassword(passwordEncoder.encode("1111"));
			userRepository.save(userEntity);
		} else {
			throw new BusinessException(ErrorCode.NOTFOUND_EMAIL);
		}
	}
}
