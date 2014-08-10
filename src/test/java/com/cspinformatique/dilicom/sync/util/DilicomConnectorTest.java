package com.cspinformatique.dilicom.sync.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("com.cspinformatique.dilicom.sync.util")
@ContextConfiguration(classes=DilicomConnectorTest.class)
@PropertySource("classpath:config/persistence/elasticsearch.properties")
public class DilicomConnectorTest {
	@Autowired private DilicomConnector dilicomConnector;
	
	@Test
	public void dilicomConnectorTest(){
//		this.dilicomConnector.initializeSearch(false);
//		
//		Assert.assertEquals(this.dilicomConnector.searchReferences(1, true, 10).size(), 10);
	}
}