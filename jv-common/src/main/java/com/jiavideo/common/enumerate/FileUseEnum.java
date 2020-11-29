package com.jiavideo.common.enumerate;

/**
 * 文件使用枚举
 *
 * @author MyMrDiao
 * @date 2020/11/16
 */
public enum FileUseEnum {

    /**
     * 课程
     */
    COURSE("C", "课程"),

    /**
     * 老师
     */
    TEACHER("T", "讲师"),


    /**
     * 视频
     */
    VIDEO("V", "视频");

    private String code;

    private String desc;

    FileUseEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static FileUseEnum getByCode(String code){
        for(FileUseEnum e: FileUseEnum.values()){
            if(code.equals(e.getCode())){
                return e;
            }
        }
        return  null;
    }
}
