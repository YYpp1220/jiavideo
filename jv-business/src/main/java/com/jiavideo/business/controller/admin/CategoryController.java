package com.jiavideo.business.controller.admin;

import cn.hutool.json.JSONUtil;
import com.jiavideo.business.dto.CategoryDTO;
import com.jiavideo.business.server.CategoryServer;
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
 * 类别控制器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Slf4j
@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryServer categoryServer;

    public static final String BUSINESS_NAME = "分类";

    /**
     * 查询所有
     *
     * @return {@link ResponseEntity<List<CategoryDTO>>}
     */
    @GetMapping("/queryAll")
    public ResponseEntity<PageResult<CategoryDTO>> queryAll() {
        PageResult<CategoryDTO> categoryDTOList = categoryServer.queryAll();
        return ResponseEntity.ok(categoryDTOList);
    }

    /**
     * 查询所有
     *
     * @return {@link ResponseEntity<List<CategoryDTO>>}
     */
    @GetMapping("/queryAllVice")
    public ResponseEntity<PageResult<CategoryDTO>> queryAllVice(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageResult<CategoryDTO> categoryDTOList = categoryServer.queryAllVice(page, pageSize);
        return ResponseEntity.ok(categoryDTOList);
    }

    /**
     * 保存
     *
     * @param categoryDTO 章dto
     * @return {@link ResponseEntity<Void>}
     */
    @PostMapping("/save")
    public ResponseEntity<Object> saveBusiness(@RequestBody @Valid CategoryDTO categoryDTO, BindingResult result) {
        if (JvException.paramVerificationEx(result)) {
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body("请求参数异常！");
        }
        categoryServer.save(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除通过id
     *
     * @param categoryId 章id
     * @return {@link ResponseEntity<Void>}
     */
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<Void> deleteById(@PathVariable String categoryId) {
        categoryServer.deleteById(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}