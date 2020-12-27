package com.jiavideo.auth;

import com.jiavideo.common.config.LogAspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 企业身份验证应用程序
 *
 * @author MyMrDiao
 * @date 2020/12/27
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.jiavideo.auth.mapper")
@ImportAutoConfiguration(LogAspect.class)
public class JvAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(JvAuthApplication.class, args);
    }
}
