package com.techstart.bi.reportsservice.freemarker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ftl")
public class FreeMarkerHtmlGenerator{

    @Autowired
    private TemplateManager tplManager;

    @RequestMapping(value = "/export", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String generateFreemarkerTemplate(@RequestParam String template,@RequestBody String data){
        try {
            String html = tplManager.processTemplate(template,data);


            return html;
        }catch (Exception e){
         e.printStackTrace();
         throw new RuntimeException(e);
        }
    }

}
