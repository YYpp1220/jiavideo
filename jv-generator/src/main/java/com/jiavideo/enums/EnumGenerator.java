package com.jiavideo.enums;

import com.jiavideo.common.enumerate.SectionChargeEnum;
import com.jiavideo.common.enumerate.YesNoEnum;
import com.jiavideo.common.utils.ToEnumUtil;

/**
 * enum发电机
 *
 * @author MyMrDiao
 * @date 2020/10/11
 */
public class EnumGenerator {
    private static final String ENUM_PATH_JS = "admin\\public\\static\\js\\enums.js";

    public static void main(String[] args) {
        StringBuffer bufferObject = new StringBuffer();
        StringBuffer bufferArray = new StringBuffer();
        long begin = System.currentTimeMillis();
        try {
            ToEnumUtil.toJson(SectionChargeEnum.class, bufferObject, bufferArray);
            ToEnumUtil.toJson(YesNoEnum.class, bufferObject, bufferArray);
            StringBuffer buffer = bufferObject.append("\r\n").append(bufferArray);
            ToEnumUtil.writeJs(buffer, ENUM_PATH_JS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - begin) + "毫秒");
    }
}
