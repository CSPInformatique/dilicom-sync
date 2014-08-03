package com.cspinformatique.dilicom.sync.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import com.cspinformatique.dilicom.sync.elasticsearch.TransportClientUtil;

@Configuration
@EnableElasticsearchRepositories
@PropertySource("classpath:config/persistence/elasticsearch.properties")
public class ElasticsearchConfig {
	@Resource
	private Environment env;
	
	@Bean
    public ElasticsearchOperations elasticsearchTemplate() {
		return new ElasticsearchTemplate(TransportClientUtil.buildTransportClient(env));
    }
}
