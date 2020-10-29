package com.jiavideo.business.mapper;

import com.jiavideo.business.dto.SortDTO;
import com.jiavideo.business.entity.Course;
import com.jiavideo.business.entity.CourseExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 当然映射器
 *
 * @author MyMrDiao
 * @date 2020/10/11
 */
public interface CourseMapper {
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
    int insert(Course record);

    /**
     * 插入选择性
     *
     * @param record 记录
     * @return int
     */
    int insertSelective(Course record);

    /**
     * 选择的例子
     *
     * @param example 例子
     * @return {@link List<Course>}
     */
    List<Course> selectByExample(CourseExample example);

    /**
     * 选择通过主键
     *
     * @param id id
     * @return {@link Course}
     */
    Course selectByPrimaryKey(String id);

    /**
     * 更新主键的选择性
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKeySelective(Course record);

    /**
     * 更新的主键
     *
     * @param record 记录
     * @return int
     */
    int updateByPrimaryKey(Course record);

    /**
     * 更新时间
     *
     * @param courseId 进程id
     * @return int
     */
    int updateTime(@Param("courseId") String courseId);

    /**
     * 更新排序
     *
     * @param sortDTO 那种dto
     */
    void updateSort(SortDTO sortDTO);

    /**
     * 推动各种
     *
     * @param sortDTO 那种dto
     */
    void moveSortsForward(SortDTO sortDTO);

    /**
     * 向后移动类型
     *
     * @param sortDTO 那种dto
     */
    void moveSortsBackward(SortDTO sortDTO);
}