package com.techstart.bi.reportsservice.jasper;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/rsjs")
public class ReportsController {

    @RequestMapping(path ={"","/"})
    public void getPrintReport(String id){


    }

}
