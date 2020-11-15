package com.jiavideo.upload.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * spring mvc配置
 *
 * @author MyMrDiao
 * @date 2020/11/07
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
    @Value("${jv.file.path}")
    private String IMAGE_UPLOAD_ADDRESS;

    /**
     * 添加资源处理程序
     *
     * @param registry 注册表
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/u/**").addResourceLocations("file:" + IMAGE_UPLOAD_ADDRESS);
    }
}
