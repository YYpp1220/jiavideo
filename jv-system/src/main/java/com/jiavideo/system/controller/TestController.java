package com.jiavideo.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 * @author MyMrDiao
 * @date 2020/09/26
 */
@RestController
public class TestController {
    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("success");
    }
}