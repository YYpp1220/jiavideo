package com.jiavideo.common.utils;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对enum跑龙套
 *
 * @author MyMrDiao
 * @date 2020/10/11
 */
public class ToEnumUtil {
    public static void toJson(Class clazz, StringBuffer bufferObject, StringBuffer bufferArray) throws Exception {
        String key = toUnderline(clazz.getSimpleName());
        toJson(clazz, key, bufferObject, bufferArray);
    }

    private static void toJson(Class clazz, String key, StringBuffer bufferObject, StringBuffer bufferArray) throws Exception {
        Object[] enumObj = clazz.getEnumConstants();
        Method name = clazz.getMethod("name");
        Method getDesc = clazz.getMethod("getDesc");
        Method getCode = clazz.getMethod("getCode");
        //生成对象
        bufferObject.append(key).append("={");
        for (int i = 0; i < enumObj.length; i++) {
            Object obj = enumObj[i];
            if (getCode == null) {
                bufferObject.append(name.invoke(obj)).append(":{key:\"").append(name.invoke(obj)).append("\", value:\"").append(getDesc.invoke(obj)).append("\"}");
            } else {
                bufferObject.append(name.invoke(obj)).append(":{key:\"").append(getCode.invoke(obj)).append("\", value:\"").append(getDesc.invoke(obj)).append("\"}");
            }
            if (i < enumObj.length - 1) {
                bufferObject.append(",");
            }
        }
        bufferObject.append("};\r\n");

        // 生成数组
        bufferArray.append(key).append("_ARRAY=[");
        for (int i = 0; i < enumObj.length; i++) {
            Object obj = enumObj[i];
            if (getCode == null) {
                bufferArray.append("{key:\"").append(name.invoke(obj)).append("\", value:\"").append(getDesc.invoke(obj)).append("\"}");
            } else {
                bufferArray.append("{key:\"").append(getCode.invoke(obj)).append("\", value:\"").append(getDesc.invoke(obj)).append("\"}");
            }
            if (i < enumObj.length - 1) {
                bufferArray.append(",");
            }
        }
        bufferArray.append("];\r\n");
    }

    /**
     * 写文件
     * @param stringBuffer
     */
    public static void writeJs(StringBuffer stringBuffer, String path) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path);
            OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
            System.out.println(path);
            osw.write(stringBuffer.toString());
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 驼峰转大写下划线，并去掉_ENUM
     * 如：SectionChargeEnum 变成 SECTION_CHARGE
     * @param str 需要转换字符串
     * @return 返回结果
     */
    private static String toUnderline(String str){
        String result = underline(str).toString();
        return result.substring(1, result.length()).toUpperCase().replace("_ENUM", "");
    }

    /**
     * 驼峰转下划线，第一位是下划线
     * 如：SectionChargeEnum 变成 _section_charge_enum
     * @param str 传入需要转换的字符串
     * @return 返回结果
     */
    private static StringBuffer underline(String str){
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if (matcher.find()) {
            sb = new StringBuffer();
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
            matcher.appendTail(sb);
        }else {
            return sb;
        }
        return underline(sb.toString());
    }
}
