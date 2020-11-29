package com.jiavideo.${module}.controller.admin;

import cn.hutool.json.JSONUtil;
import com.jiavideo.${module}.dto.${Entity}DTO;
import com.jiavideo.${module}.server.${Entity}Server;
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
@RequestMapping("${entity}")
public class ${Entity}Controller {
    @Autowired
    private ${Entity}Server ${entity}Server;

    public static final String BUSINESS_NAME = "${tableName}";

    /**
     * 查询所有
     *
     * @return {@link ResponseEntity<List<${Entity}DTO>>}
     */
    @GetMapping("/queryAll")
    public ResponseEntity<PageResult<${Entity}DTO>> queryAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageResult<${Entity}DTO> ${entity}DTOList = ${entity}Server.queryAll(page, pageSize);
        return ResponseEntity.ok(${entity}DTOList);
    }

    /**
     * 保存
     *
     * @param ${entity}DTO 章dto
     * @return {@link ResponseEntity<Void>}
     */
    @PostMapping("/save")
    public ResponseEntity<Object> saveBusiness(@RequestBody @Valid ${Entity}DTO ${entity}DTO, BindingResult result) {
        if (JvException.paramVerificationEx(result)) {
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body("请求参数异常！");
        }
        ${entity}Server.save(${entity}DTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除通过id
     *
     * @param ${entity}Id 章id
     * @return {@link ResponseEntity<Void>}
     */
    @DeleteMapping("/delete/{${entity}Id}")
    public ResponseEntity<Void> deleteById(@PathVariable String ${entity}Id) {
        ${entity}Server.deleteById(${entity}Id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}