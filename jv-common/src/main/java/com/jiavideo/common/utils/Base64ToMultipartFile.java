package com.jiavideo.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;

/**
 * base64多部分文件
 *
 * @author MyMrDiao
 * @date 2020/12/05
 */
public class Base64ToMultipartFile implements MultipartFile {

    private final byte[] imgContent;
    private final String header;

    /**
     * base64多部分文件
     *
     * @param imgContent img内容
     * @param header     头
     */
    private Base64ToMultipartFile(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header.split(";")[0];
    }

    /**
     * 得到的名字
     *
     * @return {@link String}
     */
    @Override
    public String getName() {
        // TODO - implementation depends on your requirements
        return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
    }

    /**
     * 得到原始文件名
     *
     * @return {@link String}
     */
    @Override
    public String getOriginalFilename() {
        // TODO - implementation depends on your requirements
        return System.currentTimeMillis() + (int) Math.random() * 10000 + "." + header.split("/")[1];
    }

    /**
     * 让内容类型
     *
     * @return {@link String}
     */
    @Override
    public String getContentType() {
        // TODO - implementation depends on your requirements
        return header.split(":")[1];
    }

    /**
     * 是空的
     *
     * @return boolean
     */
    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    /**
     * 得到大小
     *
     * @return long
     */
    @Override
    public long getSize() {
        return imgContent.length;
    }

    /**
     * 得到字节
     *
     * @return {@link byte[]}* @throws IOException ioException
     */
    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    /**
     * 获取输入流
     *
     * @return {@link InputStream}* @throws IOException ioException
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    /**
     * 转移到
     *
     * @param dest 桌子
     * @throws IOException           ioException
     * @throws IllegalStateException 非法状态异常
     */
    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(imgContent);
    }

    /**
     * base64,多部分
     *
     * @param base64 base64
     * @return {@link MultipartFile}
     */
    public static MultipartFile base64ToMultipart(String base64) {
        try {
            String[] baseStrs = base64.split(",");

            Base64.Decoder decoder = Base64.getDecoder();
            byte[] b = new byte[0];
            b = decoder.decode(baseStrs[1]);

            for(int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }

            return new Base64ToMultipartFile(b, baseStrs[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}