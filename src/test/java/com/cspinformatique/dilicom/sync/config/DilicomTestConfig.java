package com.cspinformatique.dilicom.sync.config;

import javax.annotation.Resource;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import com.cspinformatique.dilicom.sync.util.DilicomConnector;

@Configuration
@ComponentScan("com.cspinformatique.dilicom.sync.util.DilicomConnector")
public class DilicomTestConfig {
	
}
