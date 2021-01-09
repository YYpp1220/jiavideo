package com.jiavideo.user.dto;

import com.jiavideo.auth.dto.ResourceDTO;
import lombok.Data;

import java.util.HashSet;
import java.util.List;

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

    /**
     * 所有资源，用于前端界面控制
     */
    private List<ResourceDTO> resources;

    /**
     * 所有资源中的请求，用于后端接口拦截
     */
    private HashSet<String> requests;
}