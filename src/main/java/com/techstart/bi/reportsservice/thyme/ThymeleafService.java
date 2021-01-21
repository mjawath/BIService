package com.techstart.bi.reportsservice.thyme;

import com.techstart.common.utils.date.DateFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.io.File;
import java.util.Date;

import static com.techstart.common.utils.file.WriteToFile.writeToFile;

@Component
public class ThymeleafService {

    private static final String TEMPLATE_DIRECTORY = System.getProperty("user.dir")+ File.separator +"thyme/";
    private static final String OUTPUT_DIRECTORY = System.getProperty("user.dir")+ File.separator +"OUTPUT"+ File.separator+"thyme"+ File.separator;

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(thymeleafTemplateResolver());
        return templateEngine;
    }

    @Bean
    public ITemplateResolver thymeleafTemplateResolver() {
        FileTemplateResolver templateResolver
          = new FileTemplateResolver();
        templateResolver.setPrefix(TEMPLATE_DIRECTORY);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        return templateResolver;
    }
    public String process(String template, Context ctx){
        String html = templateEngine().process(template,ctx);
        write(template,html);
        return html;
    }

    public void write(String template,String data){
        final String fileName = OUTPUT_DIRECTORY + template + DateFormat.get_yyyyMMddHHmmss(new Date()) + ".html";
        writeToFile(fileName,data);
    }
}