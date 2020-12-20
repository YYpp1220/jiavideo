package com.jiavideo.user.entity;

import lombok.Data;

/**
 * 用户
 *
 * @author MyMrDiao
 * @date 2020/12/19
 */
@Data
public class User {
    private String id;

    private String loginName;

    private String name;

    private String password;
}