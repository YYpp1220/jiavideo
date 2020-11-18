package com.jiavideo.file.entity;

import lombok.Data;

import java.util.Date;

/**
 * 文件
 *
 * @author MyMrDiao
 * @date 2020/11/16
 */
@Data
public class File {
    /**
     * id
     */
    private String id;

    /**
     * 路径
     */
    private String path;

    /**
     * 的名字
     */
    private String name;

    /**
     * 后缀
     */
    private String suffix;

    /**
     * 大小
     */
    private Integer size;

    /**
     * 使用
     */
    private String use;

    /**
     * 在创建
     */
    private Date createdAt;

    /**
     * 更新在
     */
    private Date updatedAt;

    /**
     * 碎片索引
     */
    private Integer shardIndex;

    /**
     * 碎片的大小
     */
    private Integer shardSize;

    /**
     * 碎片总
     */
    private Integer shardTotal;

    /**
     * 关键
     */
    private String key;

    /**
     * 视频点播
     */
    private String vod;
}