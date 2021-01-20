package com.techstart.bi.reportsservice.freemarker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import freemarker.cache.StringTemplateLoader;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
public class TemplateManager {

    private Configuration freemarkerConfig;

    private static final String TEMPLATE_DIRECTORY = System.getProperty("user.dir")+ File.separator +"freemarker/";

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
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
