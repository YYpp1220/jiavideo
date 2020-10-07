package com.jiavideo.business.dto;

import lombok.Data;

import java.util.Date;

/**
 * 小节实体类
 *
 * @author MyMrDiao
 * @date 2020/10/06
 */
@Data
public class SectionDTO {
    private String id;

    private String title;

    private String courseId;

    private String chapterId;

    private String video;

    private Integer time;

    private String charge;

    private Integer sort;

    private Date createdAt;

    private Date updatedAt;

    private String vod;
}