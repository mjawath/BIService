package com.techstart.bi.reportsservice.poi;

import com.techstart.bi.reportsservice.freemarker.TemplateManager;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.nio.file.Files.copy;

/**
 * Credits
 * https://mkyong.com/java/apache-poi-reading-and-writing-excel-file-in-java/
 */

public class ExcelManager {

    private static final String DIR_NAME = System.getProperty("user.dir")+ File.separator +"OUTPUT"+ File.separator+"excel"+ File.separator;

    private static final Logger LOG= LoggerFactory.getLogger(ExcelManager.class);

    public static void processTemplate(String exelTemplate, List<List> data, HttpServletResponse response) throws IOException {
        //copy the file
        Path originalPath = Paths.get(DIR_NAME +exelTemplate);
        Path copied = Paths.get(DIR_NAME +exelTemplate.substring(0,exelTemplate.indexOf("."))+
                new Date().getTime()+exelTemplate.substring(exelTemplate.indexOf("."),exelTemplate.length()));
        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
        FileInputStream inputStream = new FileInputStream(copied.toFile());

        Workbook workbook = WorkbookFactory.create(inputStream);

        Sheet sheet = workbook.getSheet("data-source");

        int rowNum = 0;// this can be set to start from 1 considering header !!

        for (List datatype : data) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : datatype) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }
        XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

        try {
            FileOutputStream outputStream = new FileOutputStream(copied.toFile());
            workbook.write(outputStream);
            workbook.close();

            if(response!=null) {
                response.setContentType("application/excel");
                response.setHeader("Content-Disposition",
                        String.format("attachment; filename=\"" + "report.xlsx"));
                //set a link for last generated file in the header ?
                response.setHeader("last-generated-url", copied.toUri().toString());
                OutputStream out = response.getOutputStream();
                FileInputStream newInputStream = new FileInputStream(copied.toFile());
                out.write(newInputStream.readAllBytes());
                LOG.info("Excel file is successfuly generated {}",copied.toUri().toString());
            }
            } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


