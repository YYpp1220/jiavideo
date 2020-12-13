package com.jiavideo.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.InputStream;

/**
 * 视频点播跑龙套
 *
 * @author MyMrDiao
 * @date 2020/12/13
 */
public class VodUtil {
    /**
     * 使用AK初始化VOD客户端
     *
     * @param accessKeyId     访问密钥id
     * @param accessKeySecret 访问密钥的秘密
     * @return {@link DefaultAcsClient}
     */
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) {
        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        return new DefaultAcsClient(profile);
    }

    /**
     * 获取视频上传地址和凭证
     *
     * @param vodClient 视频点播客户
     * @param fileName  文件名称
     * @return {@link CreateUploadVideoResponse}* @throws ClientException 客户端异常
     */
    public static CreateUploadVideoResponse createUploadVideo(DefaultAcsClient vodClient, String fileName) throws ClientException {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setFileName(fileName);
        request.setTitle(fileName);
        request.setCateId(1000220944L);
        request.setTemplateGroupId("7368e606cc64a394007f926eb3dd8a0e");
        request.setSysReadTimeout(1000);
        request.setSysConnectTimeout(1000);
        return vodClient.getAcsResponse(request);
    }

    /**
     * 使用上传地址和凭证初始化oss客户端（注意需要先Base64解码并JSON Decode再传入）
     *
     * @param uploadAuth    上传身份验证
     * @param uploadAddress 上传地址
     * @return {@link OSSClient}
     */
    public static OSSClient initOssClient(JSONObject uploadAuth, JSONObject uploadAddress) {
        String endpoint = uploadAddress.getString("Endpoint");
        String accessKeyId = uploadAuth.getString("AccessKeyId");
        String accessKeySecret = uploadAuth.getString("AccessKeySecret");
        String securityToken = uploadAuth.getString("SecurityToken");
        return new OSSClient(endpoint, accessKeyId, accessKeySecret, securityToken);
    }

    /**
     * 简单上传
     *
     * @param ossClient     开源软件客户端
     * @param uploadAddress 上传地址
     * @param inputStream   输入流
     */
    public static void uploadLocalFile(OSSClient ossClient, JSONObject uploadAddress, InputStream inputStream) {
        String bucket = uploadAddress.getString("Bucket");
        String fileName = uploadAddress.getString("FileName");

        // 单文件上传
        ossClient.putObject(bucket, fileName, inputStream);

        /* 视频点播不支持追加上传
        // 追加上传
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType("text/plain");
        AppendObjectRequest request = new AppendObjectRequest(bucketName, objectName, file, meta);
        request.setPosition(0L);
        ossClient.appendObject(request);*/
    }

    /**
     * 上传本地文件
     *
     * @param ossClient     开源软件客户端
     * @param uploadAddress 上传地址
     * @param localFile     本地文件
     */
    public static void uploadLocalFile(OSSClient ossClient, JSONObject uploadAddress, String localFile) {
        String bucketName = uploadAddress.getString("Bucket");
        String objectName = uploadAddress.getString("FileName");
        File file = new File(localFile);

        // 单文件上传
        ossClient.putObject(bucketName, objectName, file);

        /* 视频点播不支持追加上传
        // 追加上传
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType("text/plain");
        AppendObjectRequest request = new AppendObjectRequest(bucketName, objectName, file, meta);
        request.setPosition(0L);
        ossClient.appendObject(request);*/
    }

    /**
     * 刷新上传凭证
     *
     * @param vodClient 视频点播客户
     * @return {@link RefreshUploadVideoResponse}* @throws ClientException 客户端异常
     */
    public static RefreshUploadVideoResponse refreshUploadVideo(DefaultAcsClient vodClient) throws ClientException {
        RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
        request.setAcceptFormat(FormatType.JSON);
        request.setVideoId("VideoId");
        // 设置请求超时时间
        request.setSysReadTimeout(1000);
        request.setSysConnectTimeout(1000);
        return vodClient.getAcsResponse(request);
    }

    /**
     * 获取源文件信息
     *
     * @param client  客户端
     * @param videoId 视频id
     * @return {@link GetMezzanineInfoResponse}* @throws ClientException 客户端异常
     */
    public static GetMezzanineInfoResponse getMezzanineInfo(DefaultAcsClient client, String videoId) throws ClientException {
        GetMezzanineInfoRequest request = new GetMezzanineInfoRequest();
        request.setVideoId(videoId);
        // 源片下载地址过期时间
        request.setAuthTimeout(3600L);
        return client.getAcsResponse(request);
    }

    /**
     * 获取播放凭证函数
     *
     * @param client  客户端
     * @param videoId 视频id
     * @return {@link GetVideoPlayAuthResponse}* @throws ClientException 客户端异常
     */
    public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client, String videoId) throws ClientException {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }

    public static void main(String[] args) {
        // accessKeyId
        String accessKeyId = "LTAI4FzA5JSEvZ8ab4XzECM6";
        // accessKeySecret
        String accessKeySecret = "FeMz61eE0FjyiuOGVZEX07EjOgFWWH";
        // 需要上传到vod的本地视频文件的完整路径，需要包含文件扩展名
        String localFile = "G:\\项目资源\\Spring Cloud+Vue在线视频\\代码\\course-online_20200414\\admin\\public\\static\\image\\小节视频\\test.mp4";
        try {
            // 初始化vod客户端并获取上传地址和凭证
            DefaultAcsClient vodClient = initVodClient(accessKeyId, accessKeySecret);
            String fileName = "test.mp4";
            CreateUploadVideoResponse createUploadVideoResponse = createUploadVideo(vodClient, fileName);
            // 执行成功会返回videoId，uploadAddress和uploadAuth
            String videoId = createUploadVideoResponse.getVideoId();
            JSONObject uploadAuth = JSONObject.parseObject(Base64.decodeBase64(createUploadVideoResponse.getUploadAuth()), JSONObject.class);
            JSONObject uploadAddress = JSONObject.parseObject(Base64.decodeBase64(createUploadVideoResponse.getUploadAddress()), JSONObject.class);
            // 使用uploadAuth和uploadAddress初始化oss客户端
            OSSClient ossClient = initOssClient(uploadAuth, uploadAddress);
            // 上传文件，注意是同步上传会阻塞等待，耗时与视频大小和网络上行带宽有关
            uploadLocalFile(ossClient, uploadAddress, localFile);
            System.out.println("上传视频成功，VideoId：" + videoId);

            GetMezzanineInfoResponse response = new GetMezzanineInfoResponse();
            response = getMezzanineInfo(vodClient, videoId);
            System.out.println("获取视频信息， response：" + JSON.toJSONString(response));
        } catch (Exception e) {
            System.out.println("上传视频失败，ErrorMessage：" + e.getLocalizedMessage());
        }
    }
}
