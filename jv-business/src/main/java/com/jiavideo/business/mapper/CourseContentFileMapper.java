package com.jiavideo.business.mapper;

import com.jiavideo.business.entity.CourseContentFile;
import com.jiavideo.business.entity.CourseContentFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 课程内容文件映射器
 *
 * @author MyMrDiao
 * @date 2020/11/21
 */
public interface CourseContentFileMapper {
    /**
     * 计算实例
     *
     * @param example 例子
     * @return long
     */
    long countByExample(CourseContentFileExample example);

    /**
     * 删除实例
     *
     * @param example 例子
     * @return int
     */
    int deleteByExample(CourseContentFileExample example);

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
    int insert(CourseContentFile record);

    /**
     * 插入选择性
     *
     * @param record 记录
     * @return int
     */
    int insertSelective(CourseContentFile record);

    /**
     * 选择的例子
     *
     * @param example 例子
     * @return {@link List<CourseContentFile>}
     */
    List<CourseContentFile> selectByExample(CourseContentFileExample example);

    /**
     * 选择通过主键
     *
     * @param id id
     * @return {@link CourseContentFile}
     */
    CourseContentFile selectByPrimaryKey(String id);

    /**
     * 更新的例子有选择性
     *
     * @param record  记录
     * @param example 例子
     * @return int
     */
    int updateByExampleSelective(@Param("record") CourseContentFile record, @Param("example") CourseContentFileExample example);

    /**
     * 更新的例子
     *
     * @param record  记录
     * @param example 例子
     * @return int
     */
    int updateByExample(@Param("record") CourseContentFile record, @Param("example") CourseContentFileExample example);

    /**
     * 更新主键的选择性
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKeySelective(CourseContentFile record);

    /**
     * 更新的主键
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKey(CourseContentFile record);
}