package com.jiavideo.common.pojo;

import com.jiavideo.common.excepton.JvException;
import lombok.Getter;
import org.joda.time.DateTime;

/**
 * 异常结果
 * @author MyMrDiao
 * @date 2020/10/03
 */
@Getter
public class ExceptionResult {
    /**
     * 状态码
     */
    private Integer status;

    /**
     * 异常消息
     */
    private String message;

    /**
     * 时间戳
     */
    private String timestamp;

    public ExceptionResult(JvException e){
        this.status = e.getStatus();
        this.message = e.getMessage();
        this.timestamp = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
    }
}
