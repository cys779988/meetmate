package com.spring.redis.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.spring.redis.service.RedisSubscriber;

import lombok.RequiredArgsConstructor;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

@RequiredArgsConstructor
@Configuration
@EnableRedisRepositories
public class RedisConfig {
	@Value("${spring.redis.cache.host}")
	private String redisCacheHost;
	
	@Value("${spring.redis.cache.port}")
	private int redisCachePort;

	@Value("${spring.redis.session.host}")
	private String redisSessionHost;
	
	@Value("${spring.redis.session.port}")
	private int redisSessionPort;
	
	@Bean
	public ConfigureRedisAction configureRedisAction() {
		return ConfigureRedisAction.NO_OP;
	}

	@Bean(name = "redisCacheConnectionFactory")
	public RedisConnectionFactory redisCacheConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(redisCacheHost);
		redisStandaloneConfiguration.setPort(redisCachePort);
		
		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
		return lettuceConnectionFactory;
	}
	
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(redisSessionHost);
		redisStandaloneConfiguration.setPort(redisSessionPort);
		
		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
		return lettuceConnectionFactory;
	}
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		
		//redisTemplate.setDefaultSerializer(new StringRedisSerializer());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		//redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		//redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		//redisTemplate.setHashValueSerializer(new GenericToStringSerializer<>(Object.class));
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		
		redisTemplate.setConnectionFactory(redisCacheConnectionFactory());
		
		return redisTemplate;
	}
	
	@Bean
	public MessageListenerAdapter listenerAdapter(RedisSubscriber subscriber) {
		return new MessageListenerAdapter(subscriber, "sendMessage");
	}
	
	@Bean
	public ChannelTopic topic() {
		return new ChannelTopic("chatroom");
	}

	@Bean
	@Primary
	public RedisMessageListenerContainer redisMessageListener(@Qualifier("redisCacheConnectionFactory") RedisConnectionFactory connectionFactory,
														MessageListenerAdapter listenerAdapter,
														ChannelTopic channelTopic) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, channelTopic);
		return container;
	}
	
}
