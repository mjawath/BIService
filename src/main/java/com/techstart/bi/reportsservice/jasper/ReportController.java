package com.techstart.bi.reportsservice.jasper;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin
public class ReportController {

    @Autowired
    private ResourceLoader resourceLoader;

    @RequestMapping(value = "/export", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public void export(HttpServletRequest request, HttpServletResponse response, @RequestBody String payload)  {
        try {
            String report = request.getParameter("report");
            if(StringUtils.isEmpty(report)) throw new RuntimeException("no report found for "+report);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition",
                    String.format("attachment; filename=\""+report+".pdf\""));
            OutputStream out = response.getOutputStream();
            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String,Object> stmap= new HashMap<>();
            parameterMap.entrySet().forEach(e ->{
                stmap.put(e.getKey(),e.getValue());
            });
            JasperUtils.exportAndWriteToOutPutStream(report, stmap,payload,out);
           response.flushBuffer();

        }catch (Exception e){
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }


}
