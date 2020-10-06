package com.jiavideo.business;

import com.jiavideo.common.config.LogAspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 企业业务应用程序启动类
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.jiavideo.business.mapper"})
@ImportAutoConfiguration(LogAspect.class)
public class JvBusinessApplication {

    public static void main(String[] args){
        // 运行spring应用
        SpringApplication.run(JvBusinessApplication.class, args);
    }
}