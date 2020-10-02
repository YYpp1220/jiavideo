package com.jiavideo.business.controller.admin;

import com.jiavideo.business.dto.ChapterDTO;
import com.jiavideo.business.entity.Chapter;
import com.jiavideo.business.server.ChapterServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 章控制器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {
    @Autowired
    private ChapterServer chapterServer;

    /**
     * 查询所有
     *
     * @return {@link ResponseEntity<List<ChapterDTO>>}
     */
    @GetMapping("/queryAll")
    public ResponseEntity<List<ChapterDTO>> queryAll(){
        List<ChapterDTO> chapterDTOList = chapterServer.queryAll();
        return ResponseEntity.ok(chapterDTOList);
    }
}
