package com.jiavideo.common.excepton;

import com.jiavideo.common.enumerate.ExceptionEnum;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

/**
 * +视频公共异常
 *
 * @author MyMrDiao
 * @date 2020/10/03
 */
@Slf4j
@Getter
public class JvException extends RuntimeException {

    /**
     * 状态码
     */
    private Integer status;

    public JvException(Integer status, String message){
        super(message);
        this.status = status;
    }

    public JvException(ExceptionEnum ee) {
        super(ee.getMessage());
        this.status = ee.getStatus();
    }

    public static boolean paramVerificationEx(BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorStrArr = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            log.error(errorStrArr.get(0));
            return true;
        }
        return false;
    }
}
