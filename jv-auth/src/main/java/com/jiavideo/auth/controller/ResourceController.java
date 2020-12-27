package com.jiavideo.auth.controller;

import cn.hutool.json.JSONUtil;
import com.jiavideo.auth.dto.ResourceDTO;
import com.jiavideo.auth.server.ResourceServer;
import com.jiavideo.common.excepton.JvException;
import com.jiavideo.common.pojo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
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
public class ResourceController {
    @Autowired
    private ResourceServer resourceServer;

    public static final String BUSINESS_NAME = "资源";

    /**
     * 查询所有
     *
     * @return {@link ResponseEntity<List<ResourceDTO>>}
     */
    @GetMapping("/resource/queryAll")
    public ResponseEntity<PageResult<ResourceDTO>> queryAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageResult<ResourceDTO> resourceDTOList = resourceServer.queryAll(page, pageSize);
        return ResponseEntity.ok(resourceDTOList);
    }

    /**
     * 保存
     *
     * @param jsonStr 资源树
     * @return {@link ResponseEntity<Void>}
     */
    @PostMapping("/resource/save")
    public ResponseEntity<Object> saveBusiness(@RequestBody @Valid String jsonStr) {
        if (StringUtils.isEmpty(jsonStr)) {
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body("请求参数异常！");
        }
        resourceServer.saveJson(jsonStr);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除通过id
     *
     * @param resourceId 章id
     * @return {@link ResponseEntity<Void>}
     */
    @DeleteMapping("/resource/delete/{resourceId}")
    public ResponseEntity<Void> deleteById(@PathVariable String resourceId) {
        resourceServer.deleteById(resourceId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 加载树
     *
     * @return {@link ResponseEntity<PageResult>}
     */
    @GetMapping("/resource/loadTree")
    public ResponseEntity<PageResult> loadTree() {
        PageResult<Object> result = new PageResult<>();
        List<ResourceDTO> dtoList = resourceServer.loadTree();
        result.setGeneralClass(Collections.singletonList(dtoList));
        return ResponseEntity.ok(result);
    }
}