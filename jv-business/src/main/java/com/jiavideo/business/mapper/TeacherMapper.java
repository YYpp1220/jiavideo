package com.jiavideo.business.mapper;

import com.jiavideo.business.entity.Teacher;
import com.jiavideo.business.entity.TeacherExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 老师映射器
 *
 * @author MyMrDiao
 * @date 2020/10/31
 */
public interface TeacherMapper {
    /**
     * 计算实例
     *
     * @param example 例子
     * @return long
     */
    long countByExample(TeacherExample example);

    /**
     * 删除实例
     *
     * @param example 例子
     * @return int
     */
    int deleteByExample(TeacherExample example);

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
    int insert(Teacher record);

    /**
     * 插入选择性
     *
     * @param record 记录
     * @return int
     */
    int insertSelective(Teacher record);

    /**
     * 选择的例子
     *
     * @param example 例子
     * @return {@link List<Teacher>}
     */
    List<Teacher> selectByExample(TeacherExample example);

    /**
     * 选择通过主键
     *
     * @param id id
     * @return {@link Teacher}
     */
    Teacher selectByPrimaryKey(String id);

    /**
     * 更新的例子有选择性
     *
     * @param record  记录
     * @param example 例子
     * @return int
     */
    int updateByExampleSelective(@Param("record") Teacher record, @Param("example") TeacherExample example);

    /**
     * 更新的例子
     *
     * @param record  记录
     * @param example 例子
     * @return int
     */
    int updateByExample(@Param("record") Teacher record, @Param("example") TeacherExample example);

    /**
     * 更新主键的选择性
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKeySelective(Teacher record);

    /**
     * 更新的主键
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKey(Teacher record);
}