package com.jiavideo.business.entity;

import lombok.Data;

/**
 * 老师
 *
 * @author MyMrDiao
 * @date 2020/10/31
 */
@Data
public class Teacher {
    private String id;

    private String name;

    private String nickname;

    private String image;

    private String position;

    private String motto;

    private String intro;
}