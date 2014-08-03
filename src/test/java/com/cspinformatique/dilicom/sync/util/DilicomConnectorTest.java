package com.cspinformatique.dilicom.sync.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=DilicomConnectorTest.class)
@ComponentScan("com.cspinformatique.dilicom.sync.util")
public class DilicomConnectorTest {
	@Autowired private DilicomConnector dilicomConnector;
	
	@Test
	public void dilicomConnectorTest(){
		this.dilicomConnector.loginToDilicom();
		
		String result = this.dilicomConnector.searchNewReferences(1);
		
		System.out.println(result);
		
		Assert.assertTrue(result.contains("Liste des articles"));
	}
}
