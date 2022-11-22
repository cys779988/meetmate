package com.spring.redis;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RedisTest {

	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	Environment env;
	
	private static String KEY = "4a35g4ds3af43sa2f4테스트키";
	
	@Test
	void hashOperationsTest() {
		String value = "value";
		
		HashOperations<String, String, String> map = redisTemplate.opsForHash();
		
		map.put(KEY, KEY, value);
		String result = map.get(KEY, KEY);
		
		assertThat(result, is(value));
		
		redisTemplate.delete(KEY);
	}

	@Test
	public void redisPubSubTest() {
		String channel = "foo";
		String message = "foo/bar";
		redisTemplate.convertAndSend(channel, message);
	}
	
	@Test
	void listOperationsTest() {
		ListOperations<String, Object> chatList = redisTemplate.opsForList();
		chatList.rightPush(KEY, "768768");
		chatList.rightPush(KEY, "12321");
		chatList.rightPush(KEY, "2132131");
		chatList.rightPush(KEY, "213213");
		chatList.rightPush(KEY, "31231");
		
		int size = chatList.range(KEY, 0, -1).size();
		assertThat(size, is(5));
		
		redisTemplate.delete(KEY);
	}
}
