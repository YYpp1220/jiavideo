package com.jiavideo.business.entity;

import lombok.Data;

/**
 * 课程内容文件
 *
 * @author MyMrDiao
 * @date 2020/11/21
 */
@Data
public class CourseContentFile {
    /**
     * id
     */
    private String id;

    /**
     * 课程id
     */
    private String courseId;

    /**
     * url
     */
    private String url;

    /**
     * 的名字
     */
    private String name;

    /**
     * 大小
     */
    private Integer size;
}