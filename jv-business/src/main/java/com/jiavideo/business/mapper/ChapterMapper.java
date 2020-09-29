package com.jiavideo.business.mapper;

import com.jiavideo.business.entity.Chapter;
import com.jiavideo.business.entity.ChapterExample;
import java.util.List;

/**
 * 章映射器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
public interface ChapterMapper {
    /**
     * 按主键删除
     * @param id id
     * @return int
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入
     *
     * @param record 记录
     * @return int
     */
    int insert(Chapter record);

    /**
     * 插入选择性
     *
     * @param record 记录
     * @return int
     */
    int insertSelective(Chapter record);

    /**
     * 选择的例子
     *
     * @param example 例子
     * @return {@link List<Chapter>}
     */
    List<Chapter> selectByExample(ChapterExample example);

    /**
     * 选择通过主键
     *
     * @param id id
     * @return {@link Chapter}
     */
    Chapter selectByPrimaryKey(String id);

    /**
     * 更新主键的选择性
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKeySelective(Chapter record);

    /**
     * 更新的主键
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKey(Chapter record);
}