package com.jiavideo.business.controller.admin;

import cn.hutool.json.JSONUtil;
import com.jiavideo.business.dto.CourseContentFileDTO;
import com.jiavideo.business.server.CourseContentFileServer;
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
 * 课程内容文件控制器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Slf4j
@RestController
@RequestMapping("/admin/courseContentFile")
public class CourseContentFileController {
    @Autowired
    private CourseContentFileServer courseContentFileServer;

    public static final String BUSINESS_NAME = "课程内容文件";

    /**
     * 查询所有
     *
     * @return {@link ResponseEntity<List<CourseContentFileDTO>>}
     */
    @GetMapping("/queryAll")
    public ResponseEntity<PageResult<CourseContentFileDTO>> queryAll(@RequestParam(value = "courseId", required = false) String courseId) {
        PageResult<CourseContentFileDTO> courseContentFileDTOList = courseContentFileServer.queryAll(courseId);
        return ResponseEntity.ok(courseContentFileDTOList);
    }

    /**
     * 保存
     *
     * @param courseContentFileDTO 课程内容文件dto
     * @return {@link ResponseEntity<Void>}
     */
    @PostMapping("/save")
    public ResponseEntity<Object> saveBusiness(@RequestBody @Valid CourseContentFileDTO courseContentFileDTO, BindingResult result) {
        if (JvException.paramVerificationEx(result)) {
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body("请求参数异常！");
        }
        courseContentFileServer.save(courseContentFileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除通过id
     *
     * @param courseContentFileId 课程内容文件id
     * @return {@link ResponseEntity<Void>}
     */
    @DeleteMapping("/delete/{courseContentFileId}")
    public ResponseEntity<Void> deleteById(@PathVariable String courseContentFileId) {
        courseContentFileServer.deleteById(courseContentFileId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}