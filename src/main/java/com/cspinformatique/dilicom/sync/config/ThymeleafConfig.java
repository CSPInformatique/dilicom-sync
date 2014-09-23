package com.cspinformatique.dilicom.sync.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
@PropertySource("classpath:config/gui/thymeleaf.properties")
public class ThymeleafConfig {
	@Resource Environment env;
	
	public @Bean TemplateResolver defaultTemplateResolver(){
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		
		templateResolver.setPrefix(env.getRequiredProperty("thymeleaf.prefix"));
		templateResolver.setSuffix(env.getRequiredProperty("thymeleaf.suffix"));
		templateResolver.setTemplateMode(env.getRequiredProperty("thymeleaf.templateMode"));
		templateResolver.setCacheable(false);
		
		return templateResolver;
	}
	
	public @Bean SpringTemplateEngine templateEngine(){
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		
		templateEngine.setTemplateResolver(defaultTemplateResolver());
		
		return templateEngine;
	}
	
	public @Bean ViewResolver thymeleafViewResolver(){
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		
		thymeleafViewResolver.setTemplateEngine(templateEngine());
		
		return thymeleafViewResolver;
	}
}
