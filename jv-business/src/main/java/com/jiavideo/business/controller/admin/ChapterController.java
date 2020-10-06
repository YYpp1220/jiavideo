package com.jiavideo.business.controller.admin;

import cn.hutool.json.JSONUtil;
import com.jiavideo.business.dto.ChapterDTO;
import com.jiavideo.business.server.ChapterServer;
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
@RequestMapping("/admin/chapter")
public class ChapterController {
    @Autowired
    private ChapterServer chapterServer;

    public static final String BUSINESS_NAME = "视频名称";

    /**
     * 查询所有
     *
     * @return {@link ResponseEntity<List<ChapterDTO>>}
     */
    @GetMapping("/queryAll")
    public ResponseEntity<PageResult<ChapterDTO>> queryAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageResult<ChapterDTO> chapterDTOList = chapterServer.queryAll(page, pageSize);
        return ResponseEntity.ok(chapterDTOList);
    }

    /**
     * 保存
     *
     * @param chapterDTO 章dto
     * @return {@link ResponseEntity<Void>}
     */
    @PostMapping("/save")
    public ResponseEntity<Object> saveBusiness(@RequestBody @Valid ChapterDTO chapterDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorStrArr = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            log.error(errorStrArr.get(0));
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body("请求参数异常！");
        }
        chapterServer.save(chapterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除通过id
     *
     * @param chapterId 章id
     * @return {@link ResponseEntity<Void>}
     */
    @DeleteMapping("/delete/{chapterId}")
    public ResponseEntity<Void> deleteById(@PathVariable String chapterId) {
        chapterServer.deleteById(chapterId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
