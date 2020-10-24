package com.jiavideo.business.mapper;

import com.jiavideo.business.entity.CourseCategory;
import com.jiavideo.business.entity.CourseCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 课程类别映射器
 *
 * @author MyMrDiao
 * @date 2020/10/24
 */
public interface CourseCategoryMapper {
    /**
     * 计算实例
     *
     * @param example 例子
     * @return long
     */
    long countByExample(CourseCategoryExample example);

    /**
     * 删除实例
     *
     * @param example 例子
     * @return int
     */
    int deleteByExample(CourseCategoryExample example);

    /**
     * 按主键删除
     *
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
    int insert(CourseCategory record);

    /**
     * 插入选择性
     *
     * @param record 记录
     * @return int
     */
    int insertSelective(CourseCategory record);

    /**
     * 选择的例子
     *
     * @param example 例子
     * @return {@link List<CourseCategory>}
     */
    List<CourseCategory> selectByExample(CourseCategoryExample example);

    /**
     * 选择通过主键
     *
     * @param id id
     * @return {@link CourseCategory}
     */
    CourseCategory selectByPrimaryKey(String id);

    /**
     * 更新的例子有选择性
     *
     * @param record  记录
     * @param example 例子
     * @return int
     */
    int updateByExampleSelective(@Param("record") CourseCategory record, @Param("example") CourseCategoryExample example);

    /**
     * 更新的例子
     *
     * @param record  记录
     * @param example 例子
     * @return int
     */
    int updateByExample(@Param("record") CourseCategory record, @Param("example") CourseCategoryExample example);

    /**
     * 更新主键的选择性
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKeySelective(CourseCategory record);

    /**
     * 更新的主键
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKey(CourseCategory record);
}