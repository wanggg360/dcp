package com.ht.lc.dcp.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ht.lc.dcp.common.interceptor.InterfaceLogInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(new InterfaceLogInterceptor()).addPathPatterns("/**");
	}
	
}
