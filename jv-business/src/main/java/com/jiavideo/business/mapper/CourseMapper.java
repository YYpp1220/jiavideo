package com.jiavideo.business.mapper;

import com.jiavideo.business.entity.Course;
import com.jiavideo.business.entity.CourseExample;
import java.util.List;

/**
 * 当然映射器
 *
 * @author MyMrDiao
 * @date 2020/10/11
 */
public interface CourseMapper {
    int deleteByPrimaryKey(String id);

    int insert(Course record);

    int insertSelective(Course record);

    List<Course> selectByExample(CourseExample example);

    Course selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);
}