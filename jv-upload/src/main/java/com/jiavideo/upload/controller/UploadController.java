package com.jiavideo.upload.controller;

import com.jiavideo.common.pojo.PageResult;
import com.jiavideo.common.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

/**
 * 上传控制器
 *
 * @author MyMrDiao
 * @date 2020/11/02
 */
@Slf4j
@RestController
public class UploadController {
    public static final String BUSINESS_NAME = "文件上传";

    private static final String IMAGE_UPLOAD_ADDRESS = "F:\\video\\upload\\image\\";

    /**
     * 上传图片
     *
     * @param file 文件
     * @return {@link ResponseEntity<PageResult>}
     */
    @PostMapping("/image")
    public ResponseEntity<PageResult> uploadImage(@RequestParam MultipartFile file) throws IOException {
        log.info("文件上传开始 {}", file);
        log.info(file.getOriginalFilename());
        log.info(String.valueOf(file.getSize()));
        // 保存文件到本地

        String fileName = file.getOriginalFilename();
        String key = UUIDUtil.getShortUuid();
        File fileUpload = new File(IMAGE_UPLOAD_ADDRESS + key + "-" + fileName);
        if (!fileUpload.exists()) {
            //noinspection ResultOfMethodCallIgnored
            fileUpload.mkdirs();
        }
        file.transferTo(fileUpload);
        log.info(fileUpload.getAbsolutePath());

        PageResult<Object> pageResult = new PageResult<>();
        pageResult.setGeneralClass(Collections.singletonList(fileUpload.getAbsolutePath()));
        return ResponseEntity.ok(pageResult);
    }
}
