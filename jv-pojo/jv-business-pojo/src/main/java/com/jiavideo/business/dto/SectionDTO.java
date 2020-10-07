package com.jiavideo.business.dto;

import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 部分dto
 *
 * @author MyMrDiao
 * @date 2020/10/07
 */
@Data
public class SectionDTO {

    /**
     * id
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 课程|course.id
     */
    private String courseId;

    /**
     * 大章|chapter.id
     */
    private String chapterId;

    /**
     * 视频
     */
    private String video;

    /**
     * 时长|单位秒
     */
    private Integer time;

    /**
     * 收费|C 收费；F 免费
     */
    private String charge;

    /**
     * 顺序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdAt;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedAt;

    /**
     * vod|阿里云vod
     */
    private String vod;

}