package com.jiavideo.auth.controller;

import cn.hutool.json.JSONUtil;
import com.jiavideo.auth.dto.RoleResourceDTO;
import com.jiavideo.auth.server.RoleResourceServer;
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
 * 角色资源控制器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Slf4j
@RestController
public class RoleResourceController {
    @Autowired
    private RoleResourceServer roleResourceServer;

    public static final String BUSINESS_NAME = "角色资源关联";

    /**
     * 查询所有
     *
     * @return {@link ResponseEntity<List<RoleResourceDTO>>}
     */
    @GetMapping("/roleResource/queryAll")
    public ResponseEntity<PageResult<RoleResourceDTO>> queryAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageResult<RoleResourceDTO> roleResourceDTOList = roleResourceServer.queryAll(page, pageSize);
        return ResponseEntity.ok(roleResourceDTOList);
    }

    /**
     * 保存
     *
     * @param roleResourceDTO 角色资源dto
     * @return {@link ResponseEntity<Void>}
     */
    @PostMapping("/roleResource/save")
    public ResponseEntity<Object> saveBusiness(@RequestBody @Valid RoleResourceDTO roleResourceDTO, BindingResult result) {
        if (JvException.paramVerificationEx(result)) {
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body("请求参数异常！");
        }
        roleResourceServer.save(roleResourceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除通过id
     *
     * @param roleResourceId 角色资源id
     * @return {@link ResponseEntity<Void>}
     */
    @DeleteMapping("/roleResource/delete/{roleResourceId}")
    public ResponseEntity<Void> deleteById(@PathVariable String roleResourceId) {
        roleResourceServer.deleteById(roleResourceId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}