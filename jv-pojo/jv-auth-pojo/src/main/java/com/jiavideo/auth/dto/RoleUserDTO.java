package com.jiavideo.auth.dto;

import lombok.Data;

@Data
public class RoleUserDTO {

    /**
     * id
     */
    private String id;

    /**
     * 角色|id
     */
    private String roleId;

    /**
     * 用户|id
     */
    private String userId;

}