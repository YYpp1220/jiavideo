package com.jiavideo.business.controller.admin;

import cn.hutool.json.JSONUtil;
import com.jiavideo.business.dto.TeacherDTO;
import com.jiavideo.business.server.TeacherServer;
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
 * 老师控制器
 *
 * @author MyMrDiao
 * @date 2020/09/28
 */
@Slf4j
@RestController
@RequestMapping("/admin/teacher")
public class TeacherController {
    @Autowired
    private TeacherServer teacherServer;

    public static final String BUSINESS_NAME = "讲师";

    /**
     * 查询所有不分页
     *
     * @return {@link ResponseEntity<List<TeacherDTO>>}
     */
    @GetMapping("/queryAll")
    public ResponseEntity<PageResult<TeacherDTO>> queryAll() {
        PageResult<TeacherDTO> teacherDTOList = teacherServer.queryAll();
        return ResponseEntity.ok(teacherDTOList);
    }

    /**
     * 查询所有
     *
     * @return {@link ResponseEntity<List<TeacherDTO>>}
     */
    @GetMapping("/queryAllVice")
    public ResponseEntity<PageResult<TeacherDTO>> queryAllVice(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageResult<TeacherDTO> teacherDTOList = teacherServer.queryAllVice(page, pageSize);
        return ResponseEntity.ok(teacherDTOList);
    }

    /**
     * 保存
     *
     * @param teacherDTO 章dto
     * @return {@link ResponseEntity<Void>}
     */
    @PostMapping("/save")
    public ResponseEntity<Object> saveBusiness(@RequestBody @Valid TeacherDTO teacherDTO, BindingResult result) {
        if (JvException.paramVerificationEx(result)) {
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body("请求参数异常！");
        }
        teacherServer.save(teacherDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除通过id
     *
     * @param teacherId 章id
     * @return {@link ResponseEntity<Void>}
     */
    @DeleteMapping("/delete/{teacherId}")
    public ResponseEntity<Void> deleteById(@PathVariable String teacherId) {
        teacherServer.deleteById(teacherId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}