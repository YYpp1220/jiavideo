package com.jiavideo.auth.entity;

import lombok.Data;

@Data
public class RoleResource {
    /**
     * id
     */
    private String id;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 资源id
     */
    private String resourceId;
}