package com.jiavideo.upload;

import com.jiavideo.common.config.LogAspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 合资公司上传application
 *
 * @author MyMrDiao
 * @date 2020/11/01
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.jiavideo.upload.mapper"})
@ImportAutoConfiguration(LogAspect.class)
public class JvUploadApplication {

    public static void main(String[] args){
        // 运行spring应用
        SpringApplication.run(JvUploadApplication.class, args);
    }
}