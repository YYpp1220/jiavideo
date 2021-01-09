package com.jiavideo.auth.dto;

import lombok.Data;

import java.util.List;

/**
 * 角色dto
 *
 * @author MyMrDiao
 * @date 2020/12/27
 */
@Data
public class RoleDTO {

    /**
     * id
     */
    private String id;

    /**
     * 角色
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    /**
     * 资源id
     */
    private List<String> resourceIds;

    /**
     * 用户id
     */
    private List<String> userIds;
}