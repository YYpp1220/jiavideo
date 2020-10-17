package com.jiavideo.business.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 当然dto
 *
 * @author MyMrDiao
 * @date 2020/10/11
 */
@Data
public class CourseDTO {

    /**
     * id
     */
    private String id;

    /**
     * 名称
     */
    @NotNull(message = "名称不能为空")
    @Length(max = 50, message = "长度不能超过50字符")
    private String name;

    /**
     * 概述
     */
    @NotNull(message = "概述不能为空")
    @Length(max = 2000, message = "概述最大长度为2000字符")
    private String summary;

    /**
     * 时长|单位秒
     */
    @NotNull(message = "时长不能为空")
    private Integer time;

    /**
     * 价格（元）
     */
    @NotNull(message = "价格不能为空")
    private BigDecimal price;

    /**
     * 封面
     */
    @Length(max = 100, message = "图片最大为100个字符")
    private String image;

    /**
     * 级别|枚举[CourseLevelEnum]：ONE("1", "初级"),TWO("2", "中级"),THREE("3", "高级")
     */
    @NotNull(message = "级别不能为空")
    private String level;

    /**
     * 收费|枚举[CourseChargeEnum]：CHARGE("C", "收费"),FREE("F", "免费")
     */
    @NotNull(message = "是否收费不能为空")
    private String charge;

    /**
     * 状态|枚举[CourseStatusEnum]：PUBLISH("P", "发布"),DRAFT("D", "草稿")
     */
    @NotNull(message = "状态不能为空")
    private String status;

    /**
     * 报名数
     */
    private Integer enroll;

    /**
     * 顺序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdAt;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedAt;

    /**
     * 讲师|teacher.id
     */
    private String teacherId;

}