package com.jiavideo.business.dto;

import lombok.Data;

/**
 * 那种dto
 *
 * @author MyMrDiao
 * @date 2020/10/28
 */
@Data
public class SortDTO {
    /**
     * id
     */
    private String id;

    /**
     * 旧的那种
     */
    private Integer oldSort;

    /**
     * 新型
     */
    private Integer newSort;
}
