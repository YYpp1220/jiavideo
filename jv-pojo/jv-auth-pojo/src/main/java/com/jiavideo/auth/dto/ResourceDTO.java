package com.jiavideo.auth.dto;

import lombok.Data;

import java.util.List;

/**
 * 资源dto
 *
 * @author MyMrDiao
 * @date 2020/12/27
 */
@Data
public class ResourceDTO {

    /**
     * id
     */
    private String id;

    /**
     * 名称|菜单或按钮
     */
    private String name;

    /**
     * 页面|路由
     */
    private String page;

    /**
     * 请求|接口
     */
    private String request;

    /**
     * 父id
     */
    private String parent;

    /**
     * 孩子们
     */
    private List<ResourceDTO> children;
}