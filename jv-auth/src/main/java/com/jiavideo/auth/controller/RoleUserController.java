package com.jiavideo.auth.controller;

import cn.hutool.json.JSONUtil;
import com.jiavideo.auth.dto.RoleUserDTO;
import com.jiavideo.auth.server.RoleUserServer;
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
 * 用户角色控制器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Slf4j
@RestController
public class RoleUserController {
    @Autowired
    private RoleUserServer roleUserServer;

    public static final String BUSINESS_NAME = "角色用户关联";

    /**
     * 查询所有
     *
     * @return {@link ResponseEntity<List<RoleUserDTO>>}
     */
    @GetMapping("/roleUser/queryAll")
    public ResponseEntity<PageResult<RoleUserDTO>> queryAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageResult<RoleUserDTO> roleUserDTOList = roleUserServer.queryAll(page, pageSize);
        return ResponseEntity.ok(roleUserDTOList);
    }

    /**
     * 保存
     *
     * @param roleUserDTO 角色用户dto
     * @return {@link ResponseEntity<Void>}
     */
    @PostMapping("/roleUser/save")
    public ResponseEntity<Object> saveBusiness(@RequestBody @Valid RoleUserDTO roleUserDTO, BindingResult result) {
        if (JvException.paramVerificationEx(result)) {
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body("请求参数异常！");
        }
        roleUserServer.save(roleUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除通过id
     *
     * @param roleUserId 角色用户id
     * @return {@link ResponseEntity<Void>}
     */
    @DeleteMapping("/roleUser/delete/{roleUserId}")
    public ResponseEntity<Void> deleteById(@PathVariable String roleUserId) {
        roleUserServer.deleteById(roleUserId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}