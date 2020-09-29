package com.jiavideo.business.controller;

import com.jiavideo.business.entity.Chapter;
import com.jiavideo.business.server.ChapterServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 章控制器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@RestController
public class ChapterController {
    @Autowired
    private ChapterServer chapterServer;

    @GetMapping("/queryById")
    public ResponseEntity<Chapter> queryById(){
        Chapter chapter = chapterServer.queryById();
        return ResponseEntity.ok(chapter);
    }
}
