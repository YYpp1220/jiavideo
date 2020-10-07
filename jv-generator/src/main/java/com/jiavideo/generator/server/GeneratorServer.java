package com.jiavideo.generator.server;

import com.jiavideo.generator.utils.FreemarkerUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成服务器
 *
 * @author MyMrDiao
 * @date 2020/10/07
 */
public class GeneratorServer {
    private static final String TO_SERVER_PATH = "jv-business\\src\\main\\java\\com\\jiavideo\\business\\server\\";
    private static final String TO_CONTROLLER_PATH = "jv-business\\src\\main\\java\\com\\jiavideo\\business\\controller\\admin\\";

    public static void main(String[] args) throws IOException, TemplateException {
        String Entity = "Section";
        String entity = "section";
        Map<String, Object> map = new HashMap<>(16);
        map.put("Entity", Entity);
        map.put("entity", entity);

        //生成service
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generate(TO_SERVER_PATH + Entity + "Server.java", map);

        //生成controller
        FreemarkerUtil.initConfig("controller.ftl");
        FreemarkerUtil.generate(TO_CONTROLLER_PATH + Entity + "Controller.java", map);
    }
}
