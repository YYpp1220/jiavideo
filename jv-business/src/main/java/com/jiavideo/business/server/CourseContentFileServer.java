package com.jiavideo.business.server;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiavideo.business.dto.CourseContentFileDTO;
import com.jiavideo.business.entity.CourseContentFile;
import com.jiavideo.business.entity.CourseContentFileExample;
import com.jiavideo.business.mapper.CourseContentFileMapper;
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
 * 课程内容文件服务器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CourseContentFileServer {
    @Autowired(required = false)
    private CourseContentFileMapper courseContentFileMapper;

    /**
     * 查询所有
     * @return {@link List<CourseContentFileDTO>}
     */
    public PageResult<CourseContentFileDTO> queryAll(String courseId) {
        CourseContentFileExample example = new CourseContentFileExample();
        CourseContentFileExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        List<CourseContentFile> courseContentFileList = courseContentFileMapper.selectByExample(example);
        List<CourseContentFileDTO> courseContentFileDTOList = courseContentFileList.stream().map(courseContentFile -> JSONUtil.toBean(JSONUtil.toJsonStr(courseContentFile), CourseContentFileDTO.class)).collect(Collectors.toList());
        return new PageResult<>(courseContentFileDTOList);
    }

    /**
     * 保存
     * @param courseContentFileDTO 课程内容文件dto
     */
    public void save(CourseContentFileDTO courseContentFileDTO){
        CourseContentFile courseContentFile = JSONUtil.toBean(JSONUtil.toJsonStr(courseContentFileDTO), CourseContentFile.class);
        if (StringUtils.isEmpty(courseContentFileDTO.getId())) {
            this.insert(courseContentFile);
        }else {
            this.update(courseContentFile);
        }
    }

    /**
     * 更新
     *
     * @param courseContentFile 课程内容文件
     */
    private void update(CourseContentFile courseContentFile) {
        courseContentFileMapper.updateByPrimaryKey(courseContentFile);
    }

    /**
     * 插入
     *
     * @param courseContentFile 课程内容文件
     */
    private void insert(CourseContentFile courseContentFile){
        courseContentFile.setId(UUIDUtil.getShortUuid());
        courseContentFileMapper.insert(courseContentFile);
    }

    /**
     * 删除通过id
     *
     * @param courseContentFileId 课程内容文件id
     */
    public void deleteById(String courseContentFileId) {
        courseContentFileMapper.deleteByPrimaryKey(courseContentFileId);
    }
}