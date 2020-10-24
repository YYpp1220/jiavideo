package com.jiavideo.business.dto;

import lombok.Data;

/**
 * 课程类别dto
 *
 * @author MyMrDiao
 * @date 2020/10/24
 */
@Data
public class CourseCategoryDTO {

    /**
     * id
     */
    private String id;

    /**
     * 课程|course.id
     */
    private String courseId;

    /**
     * 分类|course.id
     */
    private String categoryId;

}