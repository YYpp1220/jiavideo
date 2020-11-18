package com.jiavideo.upload.controller;

import com.jiavideo.common.enumerate.FileUseEnum;
import com.jiavideo.common.pojo.PageResult;
import com.jiavideo.common.utils.UUIDUtil;
import com.jiavideo.file.dto.FileDTO;
import com.jiavideo.upload.server.FileServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${jv.file.path}")
    private String IMAGE_UPLOAD_ADDRESS;

    @Value("${jv.file.domain}")
    private String IMAGE_UPLOAD_DOMAIN;

    @Autowired
    private FileServer fileServer;

    /**
     * 上传图片
     *
     * @param file 文件
     * @return {@link ResponseEntity<PageResult>}
     */
    @PostMapping("/image")
    public ResponseEntity<PageResult> uploadImage(@RequestParam MultipartFile file, String use) throws IOException {
        log.info("文件上传开始 {}", file);
        log.info(file.getOriginalFilename());
        log.info(String.valueOf(file.getSize()));

        // 保存文件到本地
        String fileName = file.getOriginalFilename();
        String key = UUIDUtil.getShortUuid();
        FileUseEnum useEnum = FileUseEnum.getByCode(use);
        assert useEnum != null;
        String dir = useEnum.name().toLowerCase();
        assert fileName != null;
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String path = dir + File.separator + key + "." + suffix;
        File fileUpload = new File(IMAGE_UPLOAD_ADDRESS + dir);
        if (!fileUpload.exists()) {
            //noinspection ResultOfMethodCallIgnored
            fileUpload.mkdirs();
        }
        String filePath = IMAGE_UPLOAD_ADDRESS + path;
        File dest = new File(filePath);
        file.transferTo(dest);
        log.info(fileUpload.getAbsolutePath());

        log.info("保存文件记录开始！");
        FileDTO fileDTO = new FileDTO();
        fileDTO.setPath(path);
        fileDTO.setName(fileName);
        fileDTO.setSize(Math.toIntExact(file.getSize()));
        fileDTO.setSuffix(suffix);
        fileDTO.setUse(use);
        fileServer.save(fileDTO);

        PageResult<Object> pageResult = new PageResult<>();
        pageResult.setGeneralClass(Collections.singletonList(IMAGE_UPLOAD_DOMAIN + path));
        return ResponseEntity.ok(pageResult);
    }
}