package com.jiavideo.upload.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.AppendObjectRequest;
import com.aliyun.oss.model.AppendObjectResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetMezzanineInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.jiavideo.common.enumerate.FileUseEnum;
import com.jiavideo.common.pojo.PageResult;
import com.jiavideo.common.utils.Base64ToMultipartFile;
import com.jiavideo.common.utils.UUIDUtil;
import com.jiavideo.common.utils.VodUtil;
import com.jiavideo.file.dto.FileDTO;
import com.jiavideo.upload.server.FileServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
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
public class VodController {
    public static final String BUSINESS_NAME = "Vod文件上传";

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
    @PostMapping("/vod")
    public ResponseEntity<PageResult> fileUpload(@RequestBody FileDTO fileDTO) throws Exception {
        log.info("文件上传开始");
        String shardBase64 = fileDTO.getShard();
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardBase64);

        // 保存文件到本地
        FileUseEnum useEnum = FileUseEnum.getByCode(fileDTO.getUse());
        assert useEnum != null;
        String dir = useEnum.name().toLowerCase();
        String path = new StringBuffer(dir)
                .append("/")
                .append(fileDTO.getKey())
                .append(".")
                .append(fileDTO.getSuffix()).toString();

        String vod = "";
        String fileUrl = "";

        try {
            // 初始化vod客户端并获取上传地址和凭证
            DefaultAcsClient vodClient = VodUtil.initVodClient(accessKeyId, accessKeySecret);
            CreateUploadVideoResponse createUploadVideoResponse = VodUtil.createUploadVideo(vodClient, path);
            // 执行成功返回videoId，uploadAddress和uploadAuth
            vod = createUploadVideoResponse.getVideoId();
            JSONObject uploadAuth = JSONObject.parseObject(Base64.decodeBase64(createUploadVideoResponse.getUploadAuth()), JSONObject.class);
            JSONObject uploadAddress = JSONObject.parseObject(Base64.decodeBase64(createUploadVideoResponse.getUploadAddress()), JSONObject.class);
            // 使用uploadAuth和uploadAddress初始化oss客户端
            OSSClient ossClient = VodUtil.initOssClient(uploadAuth, uploadAddress);
            // 上传文件，注意是同步上传会阻塞等待，耗时与文件大小和网络上行带宽有关
            assert shard != null;
            VodUtil.uploadLocalFile(ossClient, uploadAddress, shard.getInputStream());

            log.info("上传视频成功，vod {}", vod);
            GetMezzanineInfoResponse response = VodUtil.getMezzanineInfo(vodClient, vod);
            log.info("获取视频信息，response {}", JSON.toJSONString(response));
            fileUrl = response.getMezzanine().getFileURL();

            // 关闭ossClient
            ossClient.shutdown();
        } catch (Exception e) {
            log.error("上传视频失败，ErrorMessage: {} {}", e.getLocalizedMessage(), e);
        }

        log.info("保存文件记录开始！");
        fileDTO.setPath(path);
        fileDTO.setVod(vod);
        fileServer.save(fileDTO);

        PageResult<Object> pageResult = new PageResult<>();
        fileDTO.setPath(fileUrl);
        pageResult.setGeneralClass(Collections.singletonList(fileDTO));

        return ResponseEntity.ok(pageResult);
    }

    @GetMapping("/getAuth/{vod}")
    public ResponseEntity<PageResult> getAuth(@PathVariable String vod) {
        log.info("获取播放授权开始！");
        PageResult<String> pageResult = new PageResult<>();
        DefaultAcsClient vodClient = VodUtil.initVodClient(accessKeyId, accessKeySecret);
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            response = VodUtil.getVideoPlayAuth(vodClient, vod);
            log.info("授权码 = {}", response.getPlayAuth());
            pageResult.setGeneralClass(Collections.singletonList(response.getPlayAuth()));
            // videoMeta信息
            log.info("VideoMeta = {}", JSON.toJSONString(response.getVideoMeta()));
        } catch (Exception e) {
            System.out.println("ErrorMessage = " + e.getLocalizedMessage());
        }
        log.info("获取播放授权结束");
        return ResponseEntity.ok(pageResult);
    }
}