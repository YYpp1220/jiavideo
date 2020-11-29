package com.jiavideo.business.dto;

import lombok.Data;

/**
 * 课程内容文件dto
 *
 * @author MyMrDiao
 * @date 2020/11/21
 */
@Data
public class CourseContentFileDTO {

    /**
     * id
     */
    private String id;

    /**
     * 课程id
     */
    private String courseId;

    /**
     * 地址
     */
    private String url;

    /**
     * 文件名
     */
    private String name;

    /**
     * 大小|字节b
     */
    private Integer size;

}