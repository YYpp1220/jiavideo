package com.jiavideo.auth.entity;

import lombok.Data;

/**
 * 资源
 *
 * @author MyMrDiao
 * @date 2020/12/27
 */
@Data
public class Resource {
    /**
     * id
     */
    private String id;

    /**
     * 的名字
     */
    private String name;

    /**
     * 页面
     */
    private String page;

    /**
     * 请求
     */
    private String request;

    /**
     * 父
     */
    private String parent;
}