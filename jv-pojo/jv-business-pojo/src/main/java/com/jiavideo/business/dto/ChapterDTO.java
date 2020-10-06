package com.jiavideo.business.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 章
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Data
public class ChapterDTO {
    private String id;

    @NotNull(message = "视频Id不能为空")
    @Length(max = 8, min = 1, message = "视频Id长度为一到八个字符之间")
    private String courseId;

    @NotNull(message = "视频名称不能为空")
    @Length(max = 15, message = "视频名称不能超过15个字符")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9\\*]*$", message = "用户昵称限制：最多20字符，包含文字、字母和数字")
    private String name;
}