package com.jiavideo.upload.controller;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetMezzanineInfoResponse;
import com.jiavideo.common.enumerate.FileUseEnum;
import com.jiavideo.common.pojo.PageResult;
import com.jiavideo.common.utils.Base64ToMultipartFile;
import com.jiavideo.common.utils.UUIDUtil;
import com.jiavideo.common.utils.VodUtil;
import com.jiavideo.file.dto.FileDTO;
import com.jiavideo.upload.server.FileServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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

    @Value("${vod.accessKeyId}")
    private String accessKeyId;

    @Value("${vod.accessKeySecret}")
    private String accessKeySecret;

    @Autowired
    private FileServer fileServer;

    /**
     * 上传图片
     *
     * @param fileDTO 文件dto
     * @return {@link ResponseEntity<PageResult>}
     * @throws IOException ioException
     */
    @PostMapping("/image")
    public ResponseEntity<PageResult> uploadImage(@RequestBody FileDTO fileDTO) throws Exception {
        log.info("文件上传开始");
        String shardBase64 = fileDTO.getShard();
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardBase64);

        // 保存文件到本地
        FileUseEnum useEnum = FileUseEnum.getByCode(fileDTO.getUse());
        assert useEnum != null;
        String dir = useEnum.name().toLowerCase();
        // String path = dir + File.separator + fileDTO.getKey() + "." + fileDTO.getSuffix() + "." + fileDTO.getShardIndex();
        String path = new StringBuffer(dir)
                .append(File.separator)
                .append(fileDTO.getKey())
                .append(".")
                .append(fileDTO.getSuffix()).toString();
        String localPath = new StringBuffer(path)
                .append(".")
                .append(fileDTO.getShardIndex()).toString();
        File shardUpload = new File(IMAGE_UPLOAD_ADDRESS + dir);
        if (!shardUpload.exists()) {
            //noinspection ResultOfMethodCallIgnored
            shardUpload.mkdirs();
        }
        String shardPath = IMAGE_UPLOAD_ADDRESS + localPath;
        File dest = new File(shardPath);
        assert shard != null;
        shard.transferTo(dest);
        log.info(shardUpload.getAbsolutePath());

        log.info("保存文件记录开始！");
        fileDTO.setPath(path);
        fileServer.save(fileDTO);

        PageResult<Object> pageResult = new PageResult<>();
        fileDTO.setPath(IMAGE_UPLOAD_DOMAIN + path);
        pageResult.setGeneralClass(Collections.singletonList(fileDTO));

        if (fileDTO.getShardTotal().equals(fileDTO.getShardIndex())) {
            this.merge(fileDTO);
        }

        return ResponseEntity.ok(pageResult);
    }

    private void merge(FileDTO fileDTO) throws Exception {
        log.info("文件合并开始");
        String path = fileDTO.getPath();
        path = path.replace(IMAGE_UPLOAD_DOMAIN, "");
        Integer shardTotal = fileDTO.getShardTotal();
        File newFile = new File(IMAGE_UPLOAD_ADDRESS + path);
        // 文件追加写入
        FileOutputStream outputStream = new FileOutputStream(newFile, true);
        // 分片文件
        FileInputStream inputStream = null;
        byte[] byt = new byte[10 * 1024 * 1024];
        int len;

        try {
            for (int i = 0; i < shardTotal; i++) {
                // 读取第i个分片
                inputStream = new FileInputStream(new File(IMAGE_UPLOAD_ADDRESS + path + "." + (i + 1)));
                while ((len = inputStream.read(byt)) != -1) {
                    outputStream.write(byt, 0, len);
                }
            }
        } catch (IOException e) {
            log.error("分片合并异常！", e);
        } finally {
            try {
                if (StringUtils.isEmpty(inputStream)) {
                    assert inputStream != null;
                    inputStream.close();
                }
                outputStream.close();
                log.info("IO流关闭");
            } catch (IOException e) {
                log.error("IO流关闭异常", e);
            }
        }
        log.info("分片合并结束");

        System.gc();
        Thread.sleep(200);

        // 删除分片
        log.info("删除分片开始");
        for (int i = 0; i < shardTotal; i++) {
            String filePath = IMAGE_UPLOAD_ADDRESS + path + "." + (i + 1);
            File file = new File(filePath);
            boolean result = file.delete();
            log.info("删除 {}， {}", filePath, result ? "成功" : "失败");
        }
        log.info("删除分片结束");
    }

    @GetMapping("/check/{key}")
    public ResponseEntity<PageResult> check(@PathVariable String key) throws ClientException {
        log.info("检查上传分片开始：{}", key);
        PageResult<FileDTO> result = new PageResult<>();
        FileDTO fileDTO = fileServer.findByKey(key);
        if (!StringUtils.isEmpty(fileDTO.getId())) {
            if (StringUtils.isEmpty(fileDTO.getVod())) {
                fileDTO.setPath(IMAGE_UPLOAD_DOMAIN + fileDTO.getPath());
            } else {
                DefaultAcsClient vodClient = VodUtil.initVodClient(accessKeyId, accessKeySecret);
                GetMezzanineInfoResponse response = VodUtil.getMezzanineInfo(vodClient, fileDTO.getVod());
                log.info("获取视频信息，response：{}", JSON.toJSONString(response));
                String fileUrl = response.getMezzanine().getFileURL();
                fileDTO.setPath(fileUrl);
            }
        }
        result.setGeneralClass(Collections.singletonList(fileDTO));
        return ResponseEntity.ok(result);
    }
}