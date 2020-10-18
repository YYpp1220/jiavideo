package com.jiavideo.business.dto;

import lombok.Data;

/**
 * 类别dto
 *
 * @author MyMrDiao
 * @date 2020/10/18
 */
@Data
public class CategoryDTO {

    /**
     * id
     */
    private String id;

    /**
     * 父id
     */
    private String parent;

    /**
     * 名称
     */
    private String name;

    /**
     * 顺序
     */
    private Integer sort;

}