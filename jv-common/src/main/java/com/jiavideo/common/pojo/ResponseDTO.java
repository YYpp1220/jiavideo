package com.jiavideo.common.pojo;

import lombok.Data;

/**
 * 响应dto
 *
 * @author MyMrDiao
 * @date 2020/10/04
 */
@Data
public class ResponseDTO<T> {
    /**
     * 业务上的成功或失败
     */
    private boolean success;

    /**
     * 返回码
     */
    private String code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回泛型数据，自定义类型
     */
    private T content;
}
