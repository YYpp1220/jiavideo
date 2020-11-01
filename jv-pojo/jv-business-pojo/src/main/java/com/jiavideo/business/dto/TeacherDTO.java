package com.jiavideo.business.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 老师dto
 *
 * @author MyMrDiao
 * @date 2020/10/31
 */
@Data
public class TeacherDTO {

    /**
     * id
     */
    private String id;

    /**
     * 姓名
     */
    @NotNull(message = "姓名不能为空！")
    @Length(max = 50, message = "姓名最多50个字符")
    private String name;

    /**
     * 昵称
     */
    @Length(max = 50, message = "昵称最多50个字符")
    private String nickname;

    /**
     * 头像
     */
    @Length(max = 100, message = "头像链接长度最多100字符")
    private String image;

    /**
     * 职位
     */
    @Length(max = 50, message = "职位最多50字符")
    private String position;

    /**
     * 座右铭
     */
    @Length(max = 50, message = "座右铭最多50字符")
    private String motto;

    /**
     * 简介
     */
    @Length(max = 500, message = "简介最多500字符")
    private String intro;

}