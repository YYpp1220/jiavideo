package com.jiavideo.common.enumerate;

import lombok.Getter;

/**
 * 部分电荷枚举
 *
 * @author MyMrDiao
 * @date 2020/10/11
 */
@Getter
public enum SectionChargeEnum {
    /**
     * 负责枚举
     */
    CHARGE_ENUM("C", "收费"),
    /**
     * 免费的枚举
     */
    FREE_ENUM("F", "免费");

    private String code;

    private String desc;

    SectionChargeEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
