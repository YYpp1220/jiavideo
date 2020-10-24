package com.jiavideo.business.server;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiavideo.business.dto.CategoryDTO;
import com.jiavideo.business.dto.CourseCategoryDTO;
import com.jiavideo.business.entity.CourseCategory;
import com.jiavideo.business.entity.CourseCategoryExample;
import com.jiavideo.business.mapper.CourseCategoryMapper;
import com.jiavideo.common.enumerate.ExceptionEnum;
import com.jiavideo.common.excepton.JvException;
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

/**
 * 课程类别服务器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CourseCategoryServer {
    @Autowired(required = false)
    private CourseCategoryMapper courseCategoryMapper;

    /**
     * 查询所有
     * @return {@link List<CourseCategoryDTO>}
     * @param page
     * @param pageSize
     */
    public PageResult<CourseCategoryDTO> queryAll(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        CourseCategoryExample example = new CourseCategoryExample();
        List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(example);
        List<CourseCategoryDTO> courseCategoryDTOList = courseCategoryList.stream().map(courseCategory -> JSONUtil.toBean(JSONUtil.toJsonStr(courseCategory), CourseCategoryDTO.class)).collect(Collectors.toList());
        PageInfo<CourseCategory> courseCategoryPageInfo = new PageInfo<>(courseCategoryList);
        PageResult<CourseCategoryDTO> courseCategoryDTOPageResult = new PageResult<>(courseCategoryPageInfo.getTotal(), courseCategoryPageInfo.getPages(), courseCategoryDTOList);
        return courseCategoryDTOPageResult;
    }

    /**
     * 保存
     *
     * @param courseCategoryDTO 章dto
     */
    public void save(CourseCategoryDTO courseCategoryDTO){
        CourseCategory courseCategory = JSONUtil.toBean(JSONUtil.toJsonStr(courseCategoryDTO), CourseCategory.class);
        if (StringUtils.isEmpty(courseCategoryDTO.getId())) {
            this.insert(courseCategory);
        }else {
            this.update(courseCategory);
        }
    }

    /**
     * 更新
     *
     * @param courseCategory 章
     */
    private void update(CourseCategory courseCategory) {
        courseCategoryMapper.updateByPrimaryKey(courseCategory);
    }

    /**
     * 插入
     *
     * @param courseCategory 章
     */
    private void insert(CourseCategory courseCategory){
        courseCategory.setId(UUIDUtil.getShortUuid());
        courseCategoryMapper.insert(courseCategory);
    }

    /**
     * 删除通过id
     *
     * @param courseCategoryId 章id
     */
    public void deleteById(String courseCategoryId) {
        courseCategoryMapper.deleteByPrimaryKey(courseCategoryId);
    }

    /**
     * 保存课程分类，先删除当前保存分类在保存
     *
     * @param courseId        进程id
     * @param categoryDTOList 类别dtolist
     */
    public void saveBatch(String courseId, List<CategoryDTO> categoryDTOList) {
        CourseCategoryExample example = new CourseCategoryExample();
        CourseCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        courseCategoryMapper.deleteByExample(example);
        categoryDTOList.forEach(categoryDTO -> {
            CourseCategory courseCategory = new CourseCategory();
            courseCategory.setId(UUIDUtil.getShortUuid());
            courseCategory.setCourseId(courseId);
            courseCategory.setCategoryId(categoryDTO.getId());
            courseCategoryMapper.insert(courseCategory);
        });
    }

    /**
     * 查找课程下的所有分类
     *
     * @param courseId 进程id
     * @return {@link List<CourseCategoryDTO>}
     */
    public PageResult<CourseCategoryDTO> listByCourse(String courseId) {
        CourseCategoryExample example = new CourseCategoryExample();
        CourseCategoryExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isEmpty(courseId)) {
            throw new JvException(ExceptionEnum.INVALID_PARAM_ERROR);
        }
        criteria.andCourseIdEqualTo(courseId);
        List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(example);
        List<CourseCategoryDTO> courseCategoryDTOS = courseCategoryList.stream().map(courseCategory -> JSONUtil.toBean(JSONUtil.toJsonStr(courseCategory), CourseCategoryDTO.class)).collect(Collectors.toList());
        PageResult<CourseCategoryDTO> pageResult = new PageResult<>();
        pageResult.setGeneralClass(courseCategoryDTOS);
        return pageResult;
    }
}