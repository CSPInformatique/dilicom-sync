package com.cspinformatique.dilicom.sync.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.cspinformatique.dilicom.sync.entity.Publisher;
import com.cspinformatique.dilicom.sync.entity.ReferenceNotification;

@Configuration
@PropertySource("classpath:config/persistence/redis.properties")
public class RedisConfig {
	@Autowired
	private Environment environment;

	public @Bean JedisConnectionFactory jedisConnectionFactory() {
		String hostname = environment.getProperty("redis.hostname");
		Integer port = environment.getProperty("redis.port", Integer.class);
		String password = environment.getProperty("redis.password");

		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();

		if (hostname != null)
			jedisConnectionFactory.setHostName(hostname);
		if (port != null)
			jedisConnectionFactory.setPort(port);
		if (password != null)
			jedisConnectionFactory.setPassword(password);

		jedisConnectionFactory.setUsePool(true);

		return jedisConnectionFactory;
	}

	public @Bean RedisTemplate<String, Publisher> publisherRedisTemplate() {
		RedisTemplate<String, Publisher> redisTemplate = new RedisTemplate<String, Publisher>();

		redisTemplate.setConnectionFactory(jedisConnectionFactory());

		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate
				.setDefaultSerializer(new Jackson2JsonRedisSerializer<Publisher>(
						Publisher.class));

		return redisTemplate;
	}

	public @Bean RedisTemplate<String, ReferenceNotification> referenceNotificationRedisTemplate() {
		RedisTemplate<String, ReferenceNotification> redisTemplate = new RedisTemplate<String, ReferenceNotification>();

		redisTemplate.setConnectionFactory(jedisConnectionFactory());

		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate
				.setDefaultSerializer(new Jackson2JsonRedisSerializer<ReferenceNotification>(
						ReferenceNotification.class));

		return redisTemplate;
	}
}