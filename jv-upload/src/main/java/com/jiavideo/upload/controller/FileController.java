package com.jiavideo.upload.controller;

import cn.hutool.json.JSONUtil;
import com.jiavideo.file.dto.FileDTO;
import com.jiavideo.upload.server.FileServer;
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
 * 文件控制器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileServer fileServer;

    public static final String BUSINESS_NAME = "文件";

    /**
     * 查询所有
     *
     * @return {@link ResponseEntity<List<FileDTO>>}
     */
    @GetMapping("/queryAll")
    public ResponseEntity<PageResult<FileDTO>> queryAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageResult<FileDTO> fileDTOList = fileServer.queryAll(page, pageSize);
        return ResponseEntity.ok(fileDTOList);
    }

    /**
     * 保存
     *
     * @param fileDTO 文件dto
     * @return {@link ResponseEntity<Void>}
     */
    @PostMapping("/save")
    public ResponseEntity<Object> saveBusiness(@RequestBody @Valid FileDTO fileDTO, BindingResult result) {
        if (JvException.paramVerificationEx(result)) {
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body("请求参数异常！");
        }
        fileServer.save(fileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除通过id
     *
     * @param fileId 文件id
     * @return {@link ResponseEntity<Void>}
     */
    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<Void> deleteById(@PathVariable String fileId) {
        fileServer.deleteById(fileId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}