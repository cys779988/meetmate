package com.spring.redis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RedisTest {

	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	Environment env;
	
	@Test
	void test() {
		String key = "key";
		String value = "value";
		
		redisTemplate.opsForValue().set(key, value);
		
		assertThat(redisTemplate.opsForValue().get(key)).isEqualTo("value");
		
		redisTemplate.delete(key);
		
		
	}

	@Test
	public void redisPubSub_test() {
		String message = "foo/bar";
		String channel = env.getProperty("spring.redis.channel");
		redisTemplate.convertAndSend(channel, message);
	}
}
