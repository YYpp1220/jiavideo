package com.jiavideo.gateway.file;

import cn.hutool.json.JSONUtil;
import com.jiavideo.user.dto.LoginUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashSet;

/**
 * 登录网关过滤
 *
 * @author MyMrDiao
 * @date 2020/12/26
 */
@Slf4j
@Component
public class LoginGatewayFilter implements GatewayFilter, Ordered {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        // 请求地址中包含/system/user/的。是登录请求和获取验证码，不需要拦截
        if (path.contains("/system/user/login") || path.contains("/system/user/logout") || path.contains("/system/user/kaptcha/imageCode")) {
            log.info("不需要控台登录验证：{}", path);
            return chain.filter(exchange);
        }
        // 获取header的token参数
        String token = exchange.getRequest().getHeaders().getFirst("token");
        log.info("控台登录验证开始，token：{}", token);
        if (StringUtils.isEmpty(token) || token.isEmpty()) {
            log.info("token为空，请求被拦截，token：{}", token);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        String loginUser = redisTemplate.opsForValue().get(token);
        if (StringUtils.isEmpty(loginUser)) {
            log.warn("token无效，请求被拦截！");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        LoginUserDTO loginUserDTO = JSONUtil.toBean(loginUser, LoginUserDTO.class);
        log.info("已登录，{}", loginUserDTO.getName());

        // 增加权限校验，gateway里没有loginUserDto，所以全部用json操作（但是我有啊）
        log.info("接口权限校验，请求地址：{}", path);
        boolean exist = false;
        HashSet<String> requests = loginUserDTO.getRequests();
        for (String request : requests) {
            if (path.contains(request.substring(1, request.length() - 1))) {
                exist = true;
                break;
            }
        }
        if (exist) {
            log.info("权限校验通过！");
        } else {
            log.warn("权限校验未通过！");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
