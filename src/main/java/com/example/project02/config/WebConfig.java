package com.example.project02.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/upload/**") // 클라이언트에서 접근하는 URL 패턴
				.addResourceLocations("file:/D:/Benkyou/Weavus/upload/"); // 실제 파일이 저장되는 위치
	}
}
