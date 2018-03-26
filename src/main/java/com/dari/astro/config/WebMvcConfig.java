package com.dari.astro.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@ComponentScan({"com.dari.astro.controller"})
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport{
	
	@Override
	 protected void addViewControllers(ViewControllerRegistry registry) {
	  registry.addViewController("/NGCHome.htm").setViewName("NGC");
	 }

	 @Override
	 protected void configureViewResolvers(ViewResolverRegistry registry) {
	  registry.jsp("/WEB-INF/jsp/", ".jsp");
	 }	

}
