package com.jiavideo.user.dto;

import lombok.Data;

/**
 * 用户dto
 *
 * @author MyMrDiao
 * @date 2020/12/19
 */
@Data
public class LoginUserDTO {

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
     * 令牌
     */
    private String token;
}