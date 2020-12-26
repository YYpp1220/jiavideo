package com.jiavideo.system;

import com.jiavideo.common.config.KaptchaConfig;
import com.jiavideo.common.config.LogAspect;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 企业系统应用启动类
 * @author Lenovo
 * @date 2020/09/26
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.jiavideo.system.mapper")
@ImportAutoConfiguration(classes = {LogAspect.class, KaptchaConfig.class})
public class JvSystemApplication {

    /*private static final Logger LOG = LoggerFactory.getLogger(JvSystemApplication.class);*/

    public static void main(String[] args){
        // 运行spring应用
        SpringApplication.run(JvSystemApplication.class, args);
       /* LOG.info("启动成功");*/
    }
}