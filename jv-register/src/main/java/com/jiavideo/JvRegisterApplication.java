package com.jiavideo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 启动类
 * @author Lenovo
 */
@SpringBootApplication
@EnableEurekaServer
public class JvRegisterApplication {

    /*private static final Logger LOG = LoggerFactory.getLogger(JvRegisterApplication.class);*/

    public static void main(String[] args){
        // 运行spring应用
        SpringApplication.run(JvRegisterApplication.class, args);
        /*LOG.info("启动成功");*/
    }
}