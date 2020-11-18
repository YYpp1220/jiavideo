package com.jiavideo.generator.server;

import com.jiavideo.common.pojo.Field;
import com.jiavideo.common.utils.DdUtil;
import com.jiavideo.generator.utils.FreemarkerUtil;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 代码生成服务器
 *
 * @author MyMrDiao
 * @date 2020/10/07
 */
public class GeneratorServer {
    private static final String PREFIX_MODULE = "jv-";
    private static final String MODULE = "upload";
    private static final String MODULE_NAME = "file";
    private static final String TO_DTO_PATH = "jv-pojo\\jv-file-pojo\\src\\main\\java\\com\\jiavideo\\file\\dto\\";
    private static final String TO_SERVER_PATH = PREFIX_MODULE + MODULE +"\\src\\main\\java\\com\\jiavideo\\" + MODULE + "\\server\\";
    private static final String TO_CONTROLLER_PATH = PREFIX_MODULE + MODULE + "\\src\\main\\java\\com\\jiavideo\\" + MODULE + "\\controller\\";
    private static final String TO_GENERATOR_PATH = "jv-upload\\src\\main\\resources\\generator\\generatorConfig.xml";

    public static void main(String[] args) throws Exception {
        //只生成配置文件中的第一个table节点
        File file = new File(TO_GENERATOR_PATH);
        SAXReader reader = new SAXReader();
        //读取xml文件到Document中
        Document doc = reader.read(file);
        //获取xml文件的根节点
        Element rootElement = doc.getRootElement();
        //读取context节点
        Element contextElement = rootElement.element("context");
        //定义一个Element用于遍历
        Element tableElement;
        //读取第一个table的节点
        tableElement = contextElement.elementIterator("table").next();
        String Entity = tableElement.attributeValue("domainObjectName");
        String tableName = tableElement.attributeValue("tableName");
        String tableNameCn = DdUtil.getTableComment(tableName);
        String entity = Entity.substring(0, 1).toLowerCase() + Entity.substring(1);
        System.out.println("表：" + tableElement.attributeValue("tableName"));
        System.out.println("Entity：" + tableElement.attributeValue("domainObjectName"));
        List<Field> fieldList = DdUtil.getColumnByTableName(tableName);
        Set<String> typeSet = DdUtil.getJavaTypes(fieldList);
        Map<String, Object> map = new HashMap<>(16);
        map.put("Entity", Entity);
        map.put("entity", entity);
        map.put("tableName", tableNameCn);
        map.put("moduleName", MODULE_NAME);
        map.put("module", MODULE);
        map.put("fieldList", fieldList);
        map.put("typeSet", typeSet);

        //生成dto
        FreemarkerUtil.initConfig("dto.ftl");
        FreemarkerUtil.generate(TO_DTO_PATH + Entity + "DTO.java", map);

        //生成service
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generate(TO_SERVER_PATH + Entity + "Server.java", map);

        //生成controller
        FreemarkerUtil.initConfig("controller.ftl");
        FreemarkerUtil.generate(TO_CONTROLLER_PATH + Entity + "Controller.java", map);
    }
}
