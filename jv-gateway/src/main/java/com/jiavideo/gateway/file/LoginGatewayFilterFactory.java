package com.jiavideo.gateway.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * 登录网关过滤工厂
 *
 * @author MyMrDiao
 * @date 2020/12/26
 */
@Component
public class LoginGatewayFilterFactory extends AbstractGatewayFilterFactory {
    @Autowired
    private LoginGatewayFilter loginGatewayFilter;

    @Override
    public GatewayFilter apply(Object config) {
        return loginGatewayFilter;
    }
}
