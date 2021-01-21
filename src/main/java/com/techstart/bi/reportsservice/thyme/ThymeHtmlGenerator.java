package com.techstart.bi.reportsservice.thyme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.util.Map;

@RestController
@RequestMapping("/tm")
public class ThymeHtmlGenerator {

    @Autowired
    private ThymeleafService tmpRes;

    @RequestMapping(value = "/export", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String generateFreemarkerTemplate(@RequestParam String template,@RequestBody Map data){
        try {

            Context ctx = new Context();
            ctx.setVariables(data);
            String html = tmpRes.process(template,ctx);
            return html;
        }catch (Exception e){
         e.printStackTrace();
         throw new RuntimeException(e);
        }
    }

}
