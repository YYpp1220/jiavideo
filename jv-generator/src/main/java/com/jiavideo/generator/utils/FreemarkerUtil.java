package com.jiavideo.generator.utils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * freemarker工具类
 *
 * @author MyMrDiao
 * @date 2020/10/07
 */
public class FreemarkerUtil {

    /**
     * ftl路径
     */
    private static final String FTL_PATH = "jv-generator\\src\\main\\java\\com\\jiavideo\\generator\\ftl\\";

    /**
     * 模板对象，可使用缓存里的模板
     */
    private static Template temp;

    /**
     * 初始化配置
     *
     * @param tempName 临时的名字
     * @throws IOException ioException
     */
    public static void initConfig(String tempName) throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setDirectoryForTemplateLoading(new File(FTL_PATH));
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_30));
        temp = cfg.getTemplate(tempName);
    }

    /**
     * 生成模板对应java类
     *
     * @param fileName 文件名称
     * @throws IOException       ioException
     * @throws TemplateException 模板异常
     */
    public static void generate(String fileName, Map<String, Object> map) throws IOException, TemplateException {
        FileWriter fw = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        temp.process(map, bw);
        bw.flush();
        fw.close();
    }
}
