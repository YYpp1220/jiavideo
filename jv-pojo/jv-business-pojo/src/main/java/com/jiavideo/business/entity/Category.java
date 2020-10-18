package com.jiavideo.business.entity;

import lombok.Data;

/**
 * 类别
 *
 * @author MyMrDiao
 * @date 2020/10/18
 */
@Data
public class Category {
    /**
     * id
     */
    private String id;

    /**
     * 父
     */
    private String parent;

    /**
     * 的名字
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;
}