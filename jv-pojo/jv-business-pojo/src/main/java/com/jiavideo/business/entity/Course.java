package com.jiavideo.business.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 课程
 *
 * @author MyMrDiao
 * @date 2020/10/11
 */
@Data
public class Course {
    /**
     * id
     */
    private String id;

    /**
     * 的名字
     */
    private String name;

    /**
     * 总结
     */
    private String summary;

    /**
     * 时间
     */
    private Integer time;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 图像
     */
    private String image;

    /**
     * 水平
     */
    private String level;

    /**
     * 负责
     */
    private String charge;

    /**
     * 状态
     */
    private String status;

    /**
     * 招收
     */
    private Integer enroll;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 在创建
     */
    private Date createdAt;

    /**
     * 更新在
     */
    private Date updatedAt;

    /**
     * 老师id
     */
    private String teacherId;
}