package com.jiavideo.system.controller;

import com.jiavideo.common.constants.Constants;
import com.jiavideo.common.excepton.JvException;
import com.jiavideo.common.pojo.PageResult;
import com.jiavideo.system.server.UserServer;
import com.jiavideo.user.dto.LoginUserDTO;
import com.jiavideo.user.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * 登录控制器
 *
 * @author MyMrDiao
 * @date 2020/12/20
 */
@Slf4j
@RestController
public class LoginController {
    @Autowired
    private UserServer userServer;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public static final String BUSINESS_NAME = "登录";

    /**
     * 登录
     *
     * @param userDTO 用户dto
     * @param request 请求
     * @return {@link ResponseEntity<Object>}
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        userDTO.setPassword(DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes()));
        PageResult<Object> pageResult = new PageResult<>();

        // 根据验证码token去获取缓存中的验证码，比较和用户输入的是否一致(失败，spring cloud分布式sessionId不一致问题未解决！)
        // String imageCode = (String) request.getSession().getAttribute(userDTO.getImageCodeToken());
        // 从redis取出验证码校验
        String imageCode = redisTemplate.opsForValue().get(userDTO.getImageCodeToken());
        if (StringUtils.isEmpty(imageCode)) {
            log.info("用户登录失败,验证码已过期！");
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("验证码已过期！");
        }

        if (!imageCode.toLowerCase().equals(userDTO.getImageCode().toLowerCase())) {
            log.info("用户登录失败。验证码不正确！");
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("验证码不正确！");
        } else {
            // 验证码通过后，移除验证码
            // request.getSession().removeAttribute(userDTO.getImageCodeToken());
            redisTemplate.delete(userDTO.getImageCodeToken());
        }

        LoginUserDTO loginUser = null;
        try {
            loginUser = userServer.login(userDTO);
        } catch (JvException e) {
            log.error("异常信息为：{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(e.getMessage());
        }
        request.getSession().setAttribute(Constants.LOGIN_USER, loginUser);
        pageResult.setGeneralClass(Collections.singletonList(loginUser));
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 注销
     *
     * @param request 请求
     * @return {@link ResponseEntity<Object>}
     */
    @GetMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        PageResult<Object> pageResult = new PageResult<>();
        request.getSession().removeAttribute(Constants.LOGIN_USER);
        return ResponseEntity.ok(pageResult);
    }
}
