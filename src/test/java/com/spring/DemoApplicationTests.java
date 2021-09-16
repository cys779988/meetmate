package com.spring;


import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.spring.security.model.UserDto;
import com.spring.security.model.UserEntity;
import com.spring.security.repository.UserRepository;
import com.spring.security.service.UserService;


@SpringBootTest
class DemoApplicationTests {

	@Autowired
	UserService service;

	@Autowired
	UserRepository repository;

	@Test
	@Disabled
	void contextLoads() {
		UserDto dto = UserDto.builder()
					.email("test@example.com")
					.password("1111")
					.name("홍길동")
					.build();
		service.joinUser(dto);
		UserEntity entity = dto.toEntity();
		System.out.println(entity.toString());
	}

	
	@Test 
	@Disabled
	void test() { 
		List<?> list = new ArrayList<>(); 
		list = Stream.of(1,3,5,7,9)
					.collect(Collectors.toList());
		OptionalInt min = IntStream.of(1,3,5,7,9).min();
		int max = IntStream.of(1,5,7).max().orElse(0);
		IntStream.of(1,3,5,7,9)
				.average()
				.ifPresent(System.out::println);
	}
	
	@Test
	void fnTest() {
		Function<String, Integer> fn = str -> Integer.parseInt(str);
		Integer result = fn.apply("10");
	}
}
