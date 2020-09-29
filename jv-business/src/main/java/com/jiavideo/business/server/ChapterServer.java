package com.jiavideo.business.server;

import com.jiavideo.business.entity.Chapter;
import com.jiavideo.business.mapper.ChapterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Chapter queryById() {
        return chapterMapper.selectByPrimaryKey("1");
    }
}
