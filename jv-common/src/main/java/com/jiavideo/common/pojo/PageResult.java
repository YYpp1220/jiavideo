package com.jiavideo.common.pojo;

import lombok.Data;

import java.util.List;

/**
 * 页面的结果
 * @author MyMrDiao
 * @date 2020/10/02
 */
@Data
public class PageResult<T> {
    /**
     * 总计
     */
    private Long total;
    /**
     * 总页
     */
    private Integer totalPage;
    /**
     * 通用类
     */
    private List<T> generalClass;

    public PageResult() {
    }

    public PageResult(List<T> generalClass) {
        this.generalClass = generalClass;
    }

    public PageResult(Long total, List<T> generalClass) {
        this.total = total;
        this.generalClass = generalClass;
    }

    public PageResult(Long total, Integer totalPage, List<T> generalClass) {
        this.total = total;
        this.totalPage = totalPage;
        this.generalClass = generalClass;
    }
}
