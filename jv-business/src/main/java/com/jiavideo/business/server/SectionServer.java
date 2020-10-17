package com.jiavideo.business.server;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiavideo.business.dto.SectionDTO;
import com.jiavideo.business.entity.Section;
import com.jiavideo.business.entity.SectionExample;
import com.jiavideo.business.mapper.SectionMapper;
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
 * 章服务器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SectionServer {
    @Autowired(required = false)
    private SectionMapper sectionMapper;

    @Autowired
    private CourseServer courseServer;

    /**
     * 查询所有
     *
     * @param page      页面
     * @param pageSize  页面大小
     * @param courseId  进程id
     * @param chapterId 章id
     * @return {@link PageResult<SectionDTO>}
     */
    public PageResult<SectionDTO> queryAll(Integer page, Integer pageSize, String courseId, String chapterId) {
        PageHelper.startPage(page, pageSize);
        SectionExample example = new SectionExample();
        example.setOrderByClause("sort asc");
        SectionExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(chapterId) || !StringUtils.isEmpty(courseId)) {
            criteria.andCourseIdEqualTo(courseId);
            criteria.andChapterIdEqualTo(chapterId);
        }
        List<Section> sectionList = sectionMapper.selectByExample(example);
        List<SectionDTO> sectionDTOList = sectionList.stream().map(section -> JSONUtil.toBean(JSONUtil.toJsonStr(section), SectionDTO.class)).collect(Collectors.toList());
        PageInfo<Section> sectionPageInfo = new PageInfo<>(sectionList);
        PageResult<SectionDTO> sectionDTOPageResult = new PageResult<>(sectionPageInfo.getTotal(), sectionPageInfo.getPages(), sectionDTOList);
        return sectionDTOPageResult;
    }

    /**
     * 保存
     *
     * @param sectionDTO 章dto
     */
    public void save(SectionDTO sectionDTO){
        Section section = JSONUtil.toBean(JSONUtil.toJsonStr(sectionDTO), Section.class);
        if (StringUtils.isEmpty(sectionDTO.getId())) {
            this.insert(section);
        }else {
            this.update(section);
        }
        courseServer.updateTime(sectionDTO.getCourseId());
    }

    /**
     * 更新
     *
     * @param section 章
     */
    private void update(Section section) {
        section.setUpdatedAt(new Date());
        sectionMapper.updateByPrimaryKey(section);
    }

    /**
     * 插入
     *
     * @param section 章
     */
    private void insert(Section section){
        section.setCreatedAt(new Date());
        section.setUpdatedAt(new Date());
        section.setId(UUIDUtil.getShortUuid());
        sectionMapper.insert(section);
    }

    /**
     * 删除通过id
     *
     * @param sectionId 章id
     */
    public void deleteById(String sectionId) {
        sectionMapper.deleteByPrimaryKey(sectionId);
    }
}