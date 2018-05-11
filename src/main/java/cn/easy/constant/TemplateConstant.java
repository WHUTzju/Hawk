package cn.easy.constant;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangrui on 2018/5/10.
 */
@Component

public class TemplateConstant {
    private Logger logger = Logger.getLogger(TemplateConstant.class);

    private static String BASE_PATH = "/template";
    private static String MAIL_TEMPLATE_PATH = "mailMsg.ftl";
    private Configuration config;

    @PostConstruct
    private void init() {
        this.config = new Configuration(Configuration.VERSION_2_3_23);
        config.setClassForTemplateLoading(this.getClass(), BASE_PATH);
        config.setDefaultEncoding("UTF-8");
    }

    public String createMailText(Map<String, Object> model) throws IOException {
        Template emailTemplate = config.getTemplate(MAIL_TEMPLATE_PATH);
        StringWriter out = new StringWriter();
        try {
            emailTemplate.process(model, out);
        } catch (TemplateException e) {
            logger.error("生成邮件模版错误!");
            e.printStackTrace();
        }
        return out.toString();
    }



    public static void main(String[] args) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("verifyCode", "123456");
        Configuration config = new Configuration(Configuration.VERSION_2_3_23);
        config.setClassLoaderForTemplateLoading(ClassLoader.getSystemClassLoader(), BASE_PATH);
        config.setDefaultEncoding("UTF-8");
        Template emailTemplate = config.getTemplate(MAIL_TEMPLATE_PATH);
        StringWriter out = new StringWriter();
        try {
            emailTemplate.process(map, out);
        } catch (TemplateException e) {

            e.printStackTrace();
        }
        System.out.println(out.toString());
    }
}
