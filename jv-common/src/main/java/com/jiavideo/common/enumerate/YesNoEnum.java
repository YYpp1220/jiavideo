package com.jiavideo.common.enumerate;

import lombok.Getter;

/**
 * 部分电荷枚举
 *
 * @author MyMrDiao
 * @date 2020/10/11
 */
@Getter
public enum YesNoEnum {
    /**
     * 负责枚举
     */
    YES("1", "是"),
    /**
     * 免费的枚举
     */
    NO("0", "否");

    private String code;

    private String desc;

    YesNoEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
