package com.example.dogwalk.photo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.dir}")
    private String fileDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /images/ 로 시작하는 주소로 요청이 오면, 실제 C:/dogwalk_images/ 폴더 안의 파일로 연결해줍니다.
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + fileDir);
    }
}