package com.jiavideo.common.config;

import com.jiavideo.common.excepton.JvException;
import com.jiavideo.common.pojo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 统一异常拦截器
 *
 * @author MyMrDiao
 * @date 2020/10/03
 */
@ControllerAdvice
public class JvExceptionController {

    /**
     * 表示当前处理器，只处理LyException异常
     * @param e JvException对象
     * @return 异常状态码
     */
    @ExceptionHandler(JvException.class)
    public ResponseEntity<ExceptionResult> handlerJvException(JvException e){
        return ResponseEntity.status(e.getStatus()).body(new ExceptionResult(e));
    }
}
