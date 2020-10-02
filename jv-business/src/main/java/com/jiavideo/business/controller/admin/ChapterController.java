package com.jiavideo.business.controller.admin;

import com.jiavideo.business.dto.ChapterDTO;
import com.jiavideo.business.entity.Chapter;
import com.jiavideo.business.server.ChapterServer;
import com.jiavideo.common.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/queryAll")
    public ResponseEntity<PageResult<ChapterDTO>> queryAll(@RequestParam(defaultValue = "1", required = false) Integer page,
                                                           @RequestParam(defaultValue = "10", required = false) Integer pageSize){
        PageResult<ChapterDTO> chapterDTOList = chapterServer.queryAll(page, pageSize);
        return ResponseEntity.ok(chapterDTOList);
    }
}
