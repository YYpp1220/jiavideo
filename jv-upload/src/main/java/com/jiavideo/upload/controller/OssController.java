package com.jiavideo.upload.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.AppendObjectRequest;
import com.aliyun.oss.model.AppendObjectResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.jiavideo.common.enumerate.FileUseEnum;
import com.jiavideo.common.pojo.PageResult;
import com.jiavideo.common.utils.Base64ToMultipartFile;
import com.jiavideo.common.utils.UUIDUtil;
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
public class OssController {
    public static final String BUSINESS_NAME = "Oss文件上传";

    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.bucket}")
    private String bucket;

    @Value("${oss.ossDomain}")
    private String ossDomain;

    @Autowired
    private FileServer fileServer;

    /**
     * 上传图片
     *
     * @param fileDTO 文件dto
     * @return {@link ResponseEntity<PageResult>}
     * @throws IOException ioException
     */
    @PostMapping("/ossAppend")
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

        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        ObjectMetadata meta = new ObjectMetadata();
        // 指定上传的内容类型
        meta.setContentType("text/plain");

        // 通过AppendObjectRequest设置多个参数
        assert shard != null;
        AppendObjectRequest appendObjectRequest = new AppendObjectRequest(bucket, path, new ByteArrayInputStream(shard.getBytes()), meta);

        // 通过AppendObjectRequest设置单个参数。
        // 设置存储空间名称。
        //appendObjectRequest.setBucketName("<yourBucketName>");
        // 设置文件名称。
        //appendObjectRequest.setKey("<yourObjectName>");
        // 设置待追加的内容。有两种可选类型：InputStream类型和File类型。这里为InputStream类型。
        //appendObjectRequest.setInputStream(new ByteArrayInputStream(content1.getBytes()));
        // 设置待追加的内容。有两种可选类型：InputStream类型和File类型。这里为File类型。
        //appendObjectRequest.setFile(new File("<yourLocalFile>"));
        // 指定文件的元信息，第一次追加时有效。
        //appendObjectRequest.setMetadata(meta);

        // 第一次追加，设置文件的追加位置
        appendObjectRequest.setPosition((long) ((fileDTO.getShardIndex() - 1) * fileDTO.getShardSize()));
        AppendObjectResult appendObjectResult = ossClient.appendObject(appendObjectRequest);
        // 文件的64位CRC值，此值根据ECMA-182标准计算得出
        System.out.println(appendObjectResult.getObjectCRC());
        System.out.println(JSONObject.toJSONString(appendObjectResult));

        // 第二次追加。
        // nextPosition指明下一次请求中应当提供的Position，即文件当前的长度。
        // appendObjectRequest.setPosition(appendObjectResult.getNextPosition());
        // appendObjectRequest.setInputStream(new ByteArrayInputStream(content2.getBytes()));
        // appendObjectResult = ossClient.appendObject(appendObjectRequest);

        // 第三次追加。
        // appendObjectRequest.setPosition(appendObjectResult.getNextPosition());
        // appendObjectRequest.setInputStream(new ByteArrayInputStream(content3.getBytes()));
        // appendObjectResult = ossClient.appendObject(appendObjectRequest);

        ossClient.shutdown();

        log.info("保存文件记录开始！");
        fileDTO.setPath(path);
        fileServer.save(fileDTO);

        PageResult<Object> pageResult = new PageResult<>();
        fileDTO.setPath(ossDomain + path);
        pageResult.setGeneralClass(Collections.singletonList(fileDTO));

        return ResponseEntity.ok(pageResult);
    }

    @PostMapping("/ossSimple")
    public ResponseEntity<PageResult> fileUpload(@RequestParam MultipartFile file, @RequestParam String use) throws IOException {
        log.info("上传文件开始");
        FileUseEnum useEnum = FileUseEnum.getByCode(use);
        String key = UUIDUtil.getShortUuid();
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        assert useEnum != null;
        String dir = useEnum.name().toLowerCase();
        String path = dir + "/" + key + "." + suffix;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
//        String content = "Hello OSS";
        // <yourObjectName>表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, path, new ByteArrayInputStream(file.getBytes()));

        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);

        // 上传字符串。
        ossClient.putObject(putObjectRequest);

//        LOG.info("保存文件记录开始");
//        fileDto.setPath(path);
//        fileService.save(fileDto);

        PageResult<Object> result = new PageResult<>();
        FileDTO fileDto = new FileDTO();
        fileDto.setPath(ossDomain + path);
        result.setGeneralClass(Collections.singletonList(fileDto));
        return ResponseEntity.ok(result);
    }
}