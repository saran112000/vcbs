package com.bookings.vcbs.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@ComponentScan(basePackages = "com.bookings.*")
public class WebMvcConfigruation {

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/css/**")
	            .addResourceLocations("classpath:/static/css/");
	}
	 
}
