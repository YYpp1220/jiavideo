package com.jiavideo.business.controller.admin;

import cn.hutool.json.JSONUtil;
import com.jiavideo.business.dto.CourseCategoryDTO;
import com.jiavideo.business.dto.CourseDTO;
import com.jiavideo.business.server.CourseCategoryServer;
import com.jiavideo.business.server.CourseServer;
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
 * 当然控制器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Slf4j
@RestController
@RequestMapping("/admin/course")
public class CourseController {
    @Autowired
    private CourseServer courseServer;

    @Autowired
    private CourseCategoryServer courseCategoryServer;

    public static final String BUSINESS_NAME = "课程";

    /**
     * 查询所有
     *
     * @return {@link ResponseEntity<List<CourseDTO>>}
     */
    @GetMapping("/queryAll")
    public ResponseEntity<PageResult<CourseDTO>> queryAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageResult<CourseDTO> courseDTOList = courseServer.queryAll(page, pageSize);
        return ResponseEntity.ok(courseDTOList);
    }

    /**
     * 保存
     *
     * @param courseDTO 章dto
     * @return {@link ResponseEntity<Void>}
     */
    @PostMapping("/save")
    public ResponseEntity<Object> saveBusiness(@RequestBody @Valid CourseDTO courseDTO, BindingResult result) {
        if (JvException.paramVerificationEx(result)) {
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body("请求参数异常！");
        }
        courseServer.save(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除通过id
     *
     * @param courseId 章id
     * @return {@link ResponseEntity<Void>}
     */
    @DeleteMapping("/delete/{courseId}")
    public ResponseEntity<Void> deleteById(@PathVariable String courseId) {
        courseServer.deleteById(courseId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 类别列表
     *
     * @param courseId 进程id
     * @return {@link ResponseEntity<PageResult<CourseCategoryDTO>>}
     */
    @PostMapping("/categoryList/{courseId}")
    public ResponseEntity<PageResult<CourseCategoryDTO>> categoryList(@PathVariable("courseId") String courseId) {
        PageResult<CourseCategoryDTO> pageResult = courseCategoryServer.listByCourse(courseId);
        return ResponseEntity.ok(pageResult);
    }
}