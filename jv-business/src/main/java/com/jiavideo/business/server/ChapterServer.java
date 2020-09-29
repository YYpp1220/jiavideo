package com.jiavideo.business.server;

import cn.hutool.json.JSONUtil;
import com.jiavideo.business.dto.ChapterDTO;
import com.jiavideo.business.entity.Chapter;
import com.jiavideo.business.entity.ChapterExample;
import com.jiavideo.business.mapper.ChapterMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     */
    public List<ChapterDTO> queryAll() {
        ChapterExample example = new ChapterExample();
        List<Chapter> chapterList = chapterMapper.selectByExample(example);
        List<ChapterDTO> chapterDTOList = chapterList.stream().map(chapter -> JSONUtil.toBean(JSONUtil.toJsonStr(chapter), ChapterDTO.class)).collect(Collectors.toList());
        return chapterDTOList;
    }
}
