package com.techstart.bi.reportsservice.freemarker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.techstart.common.utils.date.DateFormat;
import freemarker.cache.StringTemplateLoader;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.techstart.common.utils.file.WriteToFile.writeToFile;

@Component
public class TemplateManager {

    private static final String TEMPLATE_DIRECTORY = System.getProperty("user.dir")+ File.separator +"freemarker/";
    private static final String OUTPUT_DIRECTORY = System.getProperty("user.dir")+ File.separator +"OUTPUT"+ File.separator+"freemarker"+ File.separator;


    private Logger LOG= LoggerFactory.getLogger(TemplateManager.class);
    private Configuration freemarkerConfig;

    public TemplateManager() {
        freemarkerConfig = new Configuration(Configuration.VERSION_2_3_23);
        freemarkerConfig.setTagSyntax(Configuration.ANGLE_BRACKET_TAG_SYNTAX);
        freemarkerConfig.setDefaultEncoding("UTF-8");
        freemarkerConfig.setNumberFormat("computer");
        freemarkerConfig.setObjectWrapper(new BeansWrapperBuilder(Configuration.VERSION_2_3_23).build());
        freemarkerConfig.setTemplateLoader(new StringTemplateLoader());
    }

    private Template loadTemplate(String templateName, String templatePath) {
        try {
            String templateContent = new String(Files.readAllBytes(Paths.get(templatePath)));
            ((StringTemplateLoader) freemarkerConfig.getTemplateLoader()).putTemplate(templateName, templateContent);
            return freemarkerConfig.getTemplate(templateName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String processTemplate(String templateName, String data) {
        Template template = loadTemplate(templateName, TEMPLATE_DIRECTORY + templateName + ".ftl");
        try (StringWriter writer = new StringWriter()) {
            Map map = new JsonMapper().readValue(data,new TypeReference<HashMap>(){});
            template.process(map, writer);
            final String htmlstr = writer.toString();
            write(templateName,htmlstr);
            return htmlstr;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void write(String template,String data){
        final String fileName = OUTPUT_DIRECTORY + template + DateFormat.get_yyyyMMddHHmmss(new Date()) + ".html";
        writeToFile(fileName,data);
    }


}
