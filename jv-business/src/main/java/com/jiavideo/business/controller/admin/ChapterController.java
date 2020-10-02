package com.jiavideo.business.controller.admin;

import cn.hutool.json.JSONUtil;
import com.jiavideo.business.dto.ChapterDTO;
import com.jiavideo.business.server.ChapterServer;
import com.jiavideo.common.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @GetMapping("/queryAll")
    public ResponseEntity<PageResult<ChapterDTO>> queryAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        PageResult<ChapterDTO> chapterDTOList = chapterServer.queryAll(page, pageSize);
        return ResponseEntity.ok(chapterDTOList);
    }

    /**
     * 保存
     * @param chapterDTO 章dto
     * @return {@link ResponseEntity<Void>}
     */
    @PostMapping("/save")
    public ResponseEntity<Void> saveBusiness(@RequestBody ChapterDTO chapterDTO){
        chapterServer.save(chapterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
