package com.jiavideo.auth.entity;

import lombok.Data;

/**
 * 角色
 *
 * @author MyMrDiao
 * @date 2020/12/27
 */
@Data
public class Role {
    /**
     * id
     */
    private String id;

    /**
     * 的名字
     */
    private String name;

    /**
     * desc
     */
    private String desc;
}