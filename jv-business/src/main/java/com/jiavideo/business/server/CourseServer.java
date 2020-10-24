package com.jiavideo.business.server;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiavideo.business.dto.CourseDTO;
import com.jiavideo.business.entity.Course;
import com.jiavideo.business.entity.CourseExample;
import com.jiavideo.business.mapper.CourseMapper;
import com.jiavideo.common.pojo.PageResult;
import com.jiavideo.common.utils.UUIDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

/**
 * 课程服务器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CourseServer {
    @Autowired(required = false)
    private CourseMapper courseMapper;

    @Autowired
    private CourseCategoryServer courseCategoryServer;

    /**
     * 查询所有
     * @return {@link List<CourseDTO>}
     * @param page
     * @param pageSize
     */
    public PageResult<CourseDTO> queryAll(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        CourseExample example = new CourseExample();
        example.setOrderByClause("sort asc");
        List<Course> courseList = courseMapper.selectByExample(example);
        List<CourseDTO> courseDTOList = courseList.stream().map(course -> JSONUtil.toBean(JSONUtil.toJsonStr(course), CourseDTO.class)).collect(Collectors.toList());
        PageInfo<Course> coursePageInfo = new PageInfo<>(courseList);
        PageResult<CourseDTO> courseDTOPageResult = new PageResult<>(coursePageInfo.getTotal(), coursePageInfo.getPages(), courseDTOList);
        return courseDTOPageResult;
    }

    /**
     * 保存
     * @param courseDTO 章dto
     */
    public void save(CourseDTO courseDTO){
        Course course = JSONUtil.toBean(JSONUtil.toJsonStr(courseDTO), Course.class);
        if (StringUtils.isEmpty(courseDTO.getId())) {
            this.insert(course);
        }else {
            this.update(course);
        }

        // 批量保存课程分类
        courseCategoryServer.saveBatch(course.getId(), courseDTO.getCategorys());
    }

    /**
     * 更新
     *
     * @param course 章
     */
    private void update(Course course) {
        course.setUpdatedAt(new Date());
        courseMapper.updateByPrimaryKey(course);
    }

    /**
     * 插入
     *
     * @param course 章
     */
    private void insert(Course course){
        course.setCreatedAt(new Date());
        course.setUpdatedAt(new Date());
        course.setId(UUIDUtil.getShortUuid());
        courseMapper.insert(course);
    }

    /**
     * 删除通过id
     *
     * @param courseId 章id
     */
    public void deleteById(String courseId) {
        courseMapper.deleteByPrimaryKey(courseId);
    }

    /**
     * 更新时间
     *
     * @param courseId 进程id
     */
    public void updateTime(String courseId) {
        courseMapper.updateTime(courseId);
    }
}