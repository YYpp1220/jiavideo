package com.jiavideo.common.excepton;

import com.jiavideo.common.enumerate.ExceptionEnum;
import lombok.Data;
import lombok.Getter;

/**
 * +视频公共异常
 *
 * @author MyMrDiao
 * @date 2020/10/03
 */
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
}
