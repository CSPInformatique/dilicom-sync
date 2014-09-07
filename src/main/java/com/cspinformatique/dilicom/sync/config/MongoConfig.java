package com.cspinformatique.dilicom.sync.config;

import java.net.UnknownHostException;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

@Configuration
@PropertySource("classpath:config/persistence/mongo.properties")
@EnableMongoRepositories(basePackages = "com.cspinformatique.dilicom.sync.repository.mongo")
public class MongoConfig {
	@Resource
	private Environment env;

	public @Bean Mongo mongo() {
		try {
			return new MongoClient(
					new ServerAddress(
							env.getRequiredProperty("dilicom.sync.mongo.host"),
							env.getRequiredProperty("dilicom.sync.mongo.port",
									Integer.class))
					);
		} catch (UnknownHostException unknownHostEx) {
			throw new RuntimeException(unknownHostEx);
		}
	}

	public @Bean MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongo(),
				env.getRequiredProperty("dilicom.sync.mongo.database"));
	}
}
