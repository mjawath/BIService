package com.techstart.bi.reportsservice.poi;


import com.techstart.bi.reportsservice.freemarker.TemplateManager;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jfree.data.io.CSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/excel")
public class ExcelGeneratorController {

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @CrossOrigin
    public void generateExcel(HttpServletResponse response, @RequestParam String template, @RequestBody String dataStr){
        try {

            CSVParser parser = CSVParser.parse(dataStr, CSVFormat.DEFAULT);
            Iterable<CSVRecord> records =  parser.parse(dataStr, CSVFormat.DEFAULT);

            long rn = parser.getRecordNumber();
//            Object[][] data = new Object[(int) rn][];
            List<List> lists = new ArrayList<>();
            for (CSVRecord record : records) {
                int cur=0;
                List l = new ArrayList();
                while (cur<record.size()){
                    l.add(record.get(cur++));
                }
                lists.add(l);
            }
            records.

            Object[][] data = {
                    {"Datatype", "Type", "Size(in bytes)"},
                    {"int", "Primitive", 2},
                    {"float", "Primitive", 4},
                    {"double", "Primitive", 8},
                    {"char", "Primitive", 1},
                    {"String", "Non-Primitive", "No fixed size"}
            };
            Object[][] data = lists.
            ExcelManager.processTemplate(template,data,response);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
