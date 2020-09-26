package com.jiavideo.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 * 企业门户应用程序
 *
 * @author MyMrDiao
 * @date 2020/09/26
 */
@SpringBootApplication
@EnableEurekaClient
public class JvGatewayApplication {

    public static void main(String[] args){
        // 运行spring应用
        SpringApplication.run(JvGatewayApplication.class, args);
    }
}