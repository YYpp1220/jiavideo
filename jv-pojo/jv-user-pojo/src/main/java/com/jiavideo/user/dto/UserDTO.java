package com.jiavideo.user.dto;

import lombok.Data;

/**
 * 用户dto
 *
 * @author MyMrDiao
 * @date 2020/12/19
 */
@Data
public class UserDTO {

    /**
     * id
     */
    private String id;

    /**
     * 登陆名
     */
    private String loginName;

    /**
     * 昵称
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 图片的代码
     */
    private String imageCode;

    /**
     * 图像代码标记
     */
    private String imageCodeToken;
}