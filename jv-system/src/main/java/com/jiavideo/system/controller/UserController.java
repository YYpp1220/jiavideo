package com.jiavideo.system.controller;

import cn.hutool.json.JSONUtil;
import com.jiavideo.user.dto.UserDTO;
import com.jiavideo.system.server.UserServer;
import com.jiavideo.common.excepton.JvException;
import com.jiavideo.common.pojo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 章控制器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserServer userServer;

    public static final String BUSINESS_NAME = "用户";

    /**
     * 查询所有
     *
     * @return {@link ResponseEntity<List<UserDTO>>}
     */
    @GetMapping("/queryAll")
    public ResponseEntity<PageResult<UserDTO>> queryAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageResult<UserDTO> userDTOList = userServer.queryAll(page, pageSize);
        return ResponseEntity.ok(userDTOList);
    }

    /**
     * 保存
     *
     * @param userDTO 章dto
     * @return {@link ResponseEntity<Void>}
     */
    @PostMapping("/save")
    public ResponseEntity<Object> saveBusiness(@RequestBody @Valid UserDTO userDTO, BindingResult result) {
        if (JvException.paramVerificationEx(result)) {
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body("请求参数异常！");
        }
        try {
            userServer.save(userDTO);
        } catch (JvException e) {
            log.error("异常信息为：{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除通过id
     *
     * @param userId 章id
     * @return {@link ResponseEntity<Void>}
     */
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable String userId) {
        userServer.deleteById(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}