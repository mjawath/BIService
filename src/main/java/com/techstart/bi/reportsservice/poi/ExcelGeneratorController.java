package com.techstart.bi.reportsservice.poi;


import com.techstart.bi.reportsservice.freemarker.TemplateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
@RestController
@RequestMapping(value = "/excel")
public class ExcelGeneratorController {

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @CrossOrigin
    public void generateExcel(HttpServletResponse response, @RequestParam String template, @RequestBody String dataStr){
        try {


            Object[][] data = {
                    {"Datatype", "Type", "Size(in bytes)"},
                    {"int", "Primitive", 2},
                    {"float", "Primitive", 4},
                    {"double", "Primitive", 8},
                    {"char", "Primitive", 1},
                    {"String", "Non-Primitive", "No fixed size"}
            };
            ExcelManager.processTemplate(template,data,response);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
