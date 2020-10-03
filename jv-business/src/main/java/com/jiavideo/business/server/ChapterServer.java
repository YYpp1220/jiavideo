package com.jiavideo.business.server;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiavideo.business.dto.ChapterDTO;
import com.jiavideo.business.entity.Chapter;
import com.jiavideo.business.entity.ChapterExample;
import com.jiavideo.business.mapper.ChapterMapper;
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
 * 章服务器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChapterServer {
    @Autowired(required = false)
    private ChapterMapper chapterMapper;

    /**
     * 查询所有
     * @return {@link List<ChapterDTO>}
     * @param page
     * @param pageSize
     */
    public PageResult<ChapterDTO> queryAll(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        ChapterExample example = new ChapterExample();
        List<Chapter> chapterList = chapterMapper.selectByExample(example);
        List<ChapterDTO> chapterDTOList = chapterList.stream().map(chapter -> JSONUtil.toBean(JSONUtil.toJsonStr(chapter), ChapterDTO.class)).collect(Collectors.toList());
        PageInfo<Chapter> chapterPageInfo = new PageInfo<>(chapterList);
        PageResult<ChapterDTO> chapterDTOPageResult = new PageResult<>(chapterPageInfo.getTotal(), chapterPageInfo.getPages(), chapterDTOList);
        return chapterDTOPageResult;
    }

    /**
     * 保存
     * @param chapterDTO 章dto
     */
    public void save(ChapterDTO chapterDTO){
        Chapter chapter = JSONUtil.toBean(JSONUtil.toJsonStr(chapterDTO), Chapter.class);
        if (StringUtils.isEmpty(chapterDTO.getId())) {
            this.insert(chapter);
        }else {
            this.update(chapter);
        }
    }

    /**
     * 更新
     *
     * @param chapter 章
     */
    private void update(Chapter chapter) {
        chapterMapper.updateByPrimaryKey(chapter);
    }

    /**
     * 插入
     *
     * @param chapter 章
     */
    private void insert(Chapter chapter){
        chapter.setId(UUIDUtil.getShortUuid());
        chapterMapper.insert(chapter);
    }

    /**
     * 删除通过id
     *
     * @param chapterId 章id
     */
    public void deleteById(String chapterId) {
        chapterMapper.deleteByPrimaryKey(chapterId);
    }
}
