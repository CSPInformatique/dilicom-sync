package com.cspinformatique.dilicom.sync.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import com.cspinformatique.dilicom.sync.elasticsearch.TransportClientUtil;

@Configuration
@PropertySource("classpath:config/persistence/elasticsearch.properties")
@ComponentScan({ "com.cspinformatique.dilicom.sync.util.DilicomConnector",
		"com.cspinformatique.dilicom.sync.config.ElasticsearchConfig" })
public class DilicomTestConfig {
	@Resource
	private Environment env;

	@Bean
	public ElasticsearchTemplate elasticsearchTemplate() {
		return new ElasticsearchTemplate(
				TransportClientUtil.buildTransportClient(env));
	}
}
