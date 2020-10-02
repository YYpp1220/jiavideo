package com.jiavideo.common.enumerate;

import lombok.Getter;

/**
 * 异常枚举
 *
 * @author MyMrDiao
 * @date 2020/10/03
 */
@Getter
public enum ExceptionEnum {
    /**
     * 无效的文件类型
     */
    INVALID_FILE_TYPE(400, "无效的文件类型！"),
    /**
     * 无效的参数错误
     */
    INVALID_PARAM_ERROR(400, "无效的请求参数！"),
    /**
     * 无效的电话号码
     */
    INVALID_PHONE_NUMBER(400, "无效的手机号码"),
    /**
     * 无效的验证代码
     */
    INVALID_VERIFY_CODE(400, "验证码错误！"),
    /**
     * 无效的用户名密码
     */
    INVALID_USERNAME_PASSWORD(400, "无效的用户名和密码！"),
    /**
     * 无效的服务器id的秘密
     */
    INVALID_SERVER_ID_SECRET(400, "无效的服务id和密钥！"),
    /**
     * 无效的通知参数
     */
    INVALID_NOTIFY_PARAM(400, "回调参数有误！"),
    /**
     * 无效的通知标志
     */
    INVALID_NOTIFY_SIGN(400, "回调签名有误！"),

    /**
     * 类没有找到
     */
    CATEGORY_NOT_FOUND(404, "视频分类不存在！"),
    /**
     * 没有找到视频
     */
    VIDEO_NOT_FOUND(404, "品牌不存在！"),
    /**
     * 协调视频没有找到
     */
    ASSORT_VIDEO_NOT_FOUND(404, "规格不存在！"),
    /**
     * 产品没有找到
     */
    GOODS_NOT_FOUND(404, "商品不存在！"),
    /**
     * 车没有找到
     */
    CARTS_NOT_FOUND(404, "购物车不存在！"),
    /**
     * 应用程序没有找到
     */
    APPLICATION_NOT_FOUND(404, "应用不存在！"),
    /**
     * 订单没有找到
     */
    ORDER_NOT_FOUND(404, "订单不存在！"),
    /**
     * 订单细节未找到
     */
    ORDER_DETAIL_NOT_FOUND(404, "订单数据不存在！"),

    /**
     * 数据传输错误
     */
    DATA_TRANSFER_ERROR(500, "数据转换异常！"),
    /**
     * 插入操作失败
     */
    INSERT_OPERATION_FAIL(500, "新增操作失败！"),
    /**
     * 更新操作失败
     */
    UPDATE_OPERATION_FAIL(500, "更新操作失败！"),
    /**
     * 删除操作失败
     */
    DELETE_OPERATION_FAIL(500, "删除操作失败！"),
    /**
     * 文件上传错误
     */
    FILE_UPLOAD_ERROR(500, "文件上传失败！"),
    /**
     * 目录作家错误
     */
    DIRECTORY_WRITER_ERROR(500, "目录写入失败！"),
    /**
     * 文件作者错误
     */
    FILE_WRITER_ERROR(500, "文件写入失败！"),
    /**
     * 发送信息错误
     */
    SEND_MESSAGE_ERROR(500, "短信发送失败！"),
    /**
     * 无效的订单状态
     */
    INVALID_ORDER_STATUS(500, "订单状态不正确！"),
    /**
     * 库存不够的错误
     */
    STOCK_NOT_ENOUGH_ERROR(500, "库存不足！"),

    /**
     * 未经授权的
     */
    UNAUTHORIZED(401, "登录失效或未登录！");

    /**
     * 状态
     */
    private int status;
    /**
     * 消息
     */
    private String message;

    ExceptionEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
