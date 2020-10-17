package com.jiavideo.common.enumerate;

import lombok.Getter;

/**
 * 课程收费枚举
 *
 * @author MyMrDiao
 * @date 2020/10/11
 */
@Getter
public enum CourseChargeEnum {

    /**
     * 负责
     */
    CHARGE("C", "收费"),
    /**
     * 免费的
     */
    FREE("F", "免费");

    private String code;

    private String desc;

    CourseChargeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
