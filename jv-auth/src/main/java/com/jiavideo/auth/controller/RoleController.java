package com.jiavideo.auth.controller;

import cn.hutool.json.JSONUtil;
import com.jiavideo.auth.dto.RoleDTO;
import com.jiavideo.auth.server.RoleServer;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * c控制器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Slf4j
@RestController
public class RoleController {
    @Autowired
    private RoleServer roleServer;

    public static final String BUSINESS_NAME = "角色";

    /**
     * 查询所有
     *
     * @return {@link ResponseEntity<List<RoleDTO>>}
     */
    @GetMapping("/role/queryAll")
    public ResponseEntity<PageResult<RoleDTO>> queryAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageResult<RoleDTO> roleDTOList = roleServer.queryAll(page, pageSize);
        return ResponseEntity.ok(roleDTOList);
    }

    /**
     * 保存
     *
     * @param roleDTO 角色dto
     * @return {@link ResponseEntity<Void>}
     */
    @PostMapping("/role/save")
    public ResponseEntity<Object> saveBusiness(@RequestBody @Valid RoleDTO roleDTO, BindingResult result) {
        if (JvException.paramVerificationEx(result)) {
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body("请求参数异常！");
        }
        roleServer.save(roleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除通过id
     *
     * @param roleId 角色id
     * @return {@link ResponseEntity<Void>}
     */
    @DeleteMapping("/role/delete/{roleId}")
    public ResponseEntity<Void> deleteById(@PathVariable String roleId) {
        roleServer.deleteById(roleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 保存资源
     *
     * @param roleDTO 角色dto
     * @return {@link ResponseEntity<PageResult>}
     */
    @PostMapping("/role/saveResource")
    public ResponseEntity<PageResult> saveResource(@RequestBody RoleDTO roleDTO) {
        log.info("保存角色资源关联开始");
        PageResult<Object> result = new PageResult<>();
        roleServer.saveResource(roleDTO);
        result.setGeneralClass(Collections.singletonList(roleDTO));
        return ResponseEntity.ok(result);
    }

    /**
     * 加载已关联的资源
     *
     * @param roleId 角色id
     * @return {@link ResponseEntity<PageResult>}
     */
    @GetMapping("/role/listResource/{roleId}")
    public ResponseEntity<PageResult> listResource(@PathVariable String roleId) {
        log.info("加载资源开始");
        PageResult<Object> result = new PageResult<>();
        List<String> resourceIds = roleServer.listResource(roleId);
        result.setGeneralClass(Collections.singletonList(resourceIds));
        return ResponseEntity.ok(result);
    }

    /**
     * 保存用户
     *
     * @param roleDTO 角色dto
     * @return {@link ResponseEntity<PageResult>}
     */
    @PostMapping("/role/saveUser")
    public ResponseEntity<PageResult> saveUser(@RequestBody RoleDTO roleDTO) {
        log.info("保存角色用户关联开始");
        PageResult<Object> result = new PageResult<>();
        roleServer.saveUser(roleDTO);
        result.setGeneralClass(Collections.singletonList(roleDTO));
        return ResponseEntity.ok(result);
    }

    /**
     * 加载用户列表
     *
     * @param roleId 角色id
     * @return {@link ResponseEntity<PageResult>}
     */
    @GetMapping("/role/listUser/{roleId}")
    public ResponseEntity<PageResult> listUser(@PathVariable String roleId) {
        log.info("加载用户开始");
        PageResult<Object> result = new PageResult<>();
        List<String> userIds = roleServer.listUser(roleId);
        result.setGeneralClass(Collections.singletonList(userIds));
        return ResponseEntity.ok(result);
    }
}