package com.jiavideo.auth.dto;

import lombok.Data;

@Data
public class RoleResourceDTO {

    /**
     * id
     */
    private String id;

    /**
     * 角色|id
     */
    private String roleId;

    /**
     * 资源|id
     */
    private String resourceId;

}