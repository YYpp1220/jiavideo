package com.jiavideo.common.enumerate;

import lombok.Getter;

/**
 * 课程状态枚举
 *
 * @author MyMrDiao
 * @date 2020/10/11
 */
@Getter
public enum CourseStatusEnum {

    /**
     * 发布
     */
    PUBLISH("P", "发布"),
    /**
     * 草案
     */
    DRAFT("D", "草稿");

    private String code;

    private String desc;

    CourseStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
