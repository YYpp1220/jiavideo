package com.jiavideo.business.server;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiavideo.business.dto.TeacherDTO;
import com.jiavideo.business.entity.Teacher;
import com.jiavideo.business.entity.TeacherExample;
import com.jiavideo.business.mapper.TeacherMapper;
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
 * 老师服务器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TeacherServer {
    @Autowired(required = false)
    private TeacherMapper teacherMapper;

    /**
     * 查询所有 不分页
     * @return {@link List<TeacherDTO>}
     */
    public PageResult<TeacherDTO> queryAll() {
        TeacherExample example = new TeacherExample();
        List<Teacher> teacherList = teacherMapper.selectByExample(example);
        List<TeacherDTO> teacherDTOList = teacherList.stream().map(teacher -> JSONUtil.toBean(JSONUtil.toJsonStr(teacher), TeacherDTO.class)).collect(Collectors.toList());
        PageResult<TeacherDTO> pageResult = new PageResult<>();
        pageResult.setGeneralClass(teacherDTOList);
        return pageResult;
    }

    /**
     * 查询所有
     * @return {@link List<TeacherDTO>}
     * @param page
     * @param pageSize
     */
    public PageResult<TeacherDTO> queryAllVice(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        TeacherExample example = new TeacherExample();
        List<Teacher> teacherList = teacherMapper.selectByExample(example);
        List<TeacherDTO> teacherDTOList = teacherList.stream().map(teacher -> JSONUtil.toBean(JSONUtil.toJsonStr(teacher), TeacherDTO.class)).collect(Collectors.toList());
        PageInfo<Teacher> teacherPageInfo = new PageInfo<>(teacherList);
        PageResult<TeacherDTO> teacherDTOPageResult = new PageResult<>(teacherPageInfo.getTotal(), teacherPageInfo.getPages(), teacherDTOList);
        return teacherDTOPageResult;
    }

    /**
     * 保存
     *
     * @param teacherDTO 章dto
     */
    public void save(TeacherDTO teacherDTO){
        Teacher teacher = JSONUtil.toBean(JSONUtil.toJsonStr(teacherDTO), Teacher.class);
        if (StringUtils.isEmpty(teacherDTO.getId())) {
            this.insert(teacher);
        }else {
            this.update(teacher);
        }
    }

    /**
     * 更新
     *
     * @param teacher 章
     */
    private void update(Teacher teacher) {
        teacherMapper.updateByPrimaryKey(teacher);
    }

    /**
     * 插入
     *
     * @param teacher 章
     */
    private void insert(Teacher teacher){
        teacher.setId(UUIDUtil.getShortUuid());
        teacherMapper.insert(teacher);
    }

    /**
     * 删除通过id
     *
     * @param teacherId 章id
     */
    public void deleteById(String teacherId) {
        teacherMapper.deleteByPrimaryKey(teacherId);
    }
}