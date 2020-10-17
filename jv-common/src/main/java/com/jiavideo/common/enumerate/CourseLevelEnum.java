package com.jiavideo.common.enumerate;

import lombok.Getter;

/**
 * 课程级别枚举
 *
 * @author MyMrDiao
 * @date 2020/10/11
 */
@Getter
public enum CourseLevelEnum {

    /**
     * 一个
     */
    ONE("1", "初级"),
    /**
     * 两个
     */
    TWO("2", "中级"),
    /**
     * 三个
     */
    THREE("3", "高级");

    private String code;

    private String desc;

    CourseLevelEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
