package com.cspinformatique.dilicom.sync.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/css/**").addResourceLocations("css/**");
	    registry.addResourceHandler("/js/**").addResourceLocations("js/**");
	    registry.addResourceHandler("/libs/**").addResourceLocations("libs/**");
	}
	
	public @Bean MessageSource messageSource(){
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		
		messageSource.setBasename("translations/Messages");
		
		return messageSource;
	}
}
