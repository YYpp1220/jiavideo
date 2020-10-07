package com.jiavideo.business.controller.admin;

import cn.hutool.json.JSONUtil;
import com.jiavideo.business.dto.SectionDTO;
import com.jiavideo.business.server.SectionServer;
import com.jiavideo.common.excepton.JvException;
import com.jiavideo.common.pojo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部分控制器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Slf4j
@RestController
@RequestMapping("/admin/section")
public class SectionController {
    @Autowired
    private SectionServer sectionServer;

    public static final String BUSINESS_NAME = "小节";

    /**
     * 查询所有
     *
     * @return {@link ResponseEntity<List<SectionDTO>>}
     */
    @GetMapping("/queryAll")
    public ResponseEntity<PageResult<SectionDTO>> queryAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageResult<SectionDTO> sectionDTOList = sectionServer.queryAll(page, pageSize);
        return ResponseEntity.ok(sectionDTOList);
    }

    /**
     * 保存
     *
     * @param sectionDTO 章dto
     * @return {@link ResponseEntity<Void>}
     */
    @PostMapping("/save")
    public ResponseEntity<Object> saveBusiness(@RequestBody @Valid SectionDTO sectionDTO, BindingResult result) {
        sectionServer.save(sectionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除通过id
     *
     * @param sectionId 章id
     * @return {@link ResponseEntity<Void>}
     */
    @DeleteMapping("/delete/{sectionId}")
    public ResponseEntity<Void> deleteById(@PathVariable String sectionId) {
        sectionServer.deleteById(sectionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}