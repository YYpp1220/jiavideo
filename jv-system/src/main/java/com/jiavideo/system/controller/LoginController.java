package com.jiavideo.system.controller;

import com.jiavideo.common.excepton.JvException;
import com.jiavideo.common.pojo.PageResult;
import com.jiavideo.system.server.UserServer;
import com.jiavideo.user.dto.LoginUserDTO;
import com.jiavideo.user.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    public static final String BUSINESS_NAME = "登录";

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserDTO userDTO) {
        userDTO.setPassword(DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes()));
        PageResult<Object> pageResult = new PageResult<>();
        LoginUserDTO loginUser = null;
        try {
            loginUser = userServer.login(userDTO);
        } catch (JvException e) {
            log.error("异常信息为：{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(e.getMessage());
        }
        pageResult.setGeneralClass(Collections.singletonList(loginUser));
        return ResponseEntity.ok(pageResult);
    }
}
