package com.techstart.bi.reportsservice.jasper;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.sql.SQLException;
import java.util.Map;


public class JasperUtils {

    private static final Logger logger = LoggerFactory.getLogger(JasperUtils.class);


    public static void exportAndWriteToOutPutStream(String report, Map<String, Object> parameters,
                                                    String payload,OutputStream out){
        try {
            String fileName = getFileName(report, "jasper");
            logger.info("trying to  read file jasper "+fileName);
            File jrxmlFile = new File(fileName);
            if(!jrxmlFile.exists()){
                JasperUtils.compileReport(report);
            }
            JasperPrint jasperPrint = exportToPdf(report,parameters,payload);//userService.exportPdfFile();
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);

        }catch (Exception e){
            logger.error(report+ " failed to compile ",e);
            throw  new RuntimeException(e);
        }
    }

    public static JasperPrint exportToPdf(String report, Map<String, Object> parameters,
                                          String payload) throws IOException, JRException, SQLException {


        report = getFileName(report,"jasper");
//        StringBuilder readLine = getStringBuilder();
        boolean exists = new File(report).exists();
        if(!exists) throw new RuntimeException("file not found"+report);
        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(payload.getBytes());

        JsonDataSource ds = new JsonDataSource(jsonDataStream);
//		ds.subDataSource();


        JasperReport jr =(JasperReport) JRLoader.loadObject(
                new File(report));
        String dir = getDir();
        parameters.put("SUBREPORT_DIR",dir);
        JasperPrint print = JasperFillManager.fillReport(jr
                , parameters, ds);
        return print;
    }

    private StringBuilder getStringBuilder() throws IOException {
        InputStream inputStream = new ClassPathResource("classpath:test.json").getInputStream();
//		InputStream in;
        StringBuilder readLine= new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String s = br.readLine();
        while ( s != null) {

            System.out.println(readLine);

            readLine.append(s);
            s=br.readLine();
        }
        return readLine;
    }

    public static JasperReport compileReport(String report) {
        try {

            File file = new File("reports"+ File.separator + report + ".jrxml");
            if(!file.exists()){
                String message = file.getPath() + " file not found";
                logger.error(message);
                throw  new RuntimeException(message);
            }
            String path = file.getAbsolutePath();
            JasperReport jasperReport2 = JasperCompileManager.compileReport(path);
            JRSaver.saveObject(jasperReport2, new File( getFileName(report,"jasper")));
            return jasperReport2;
        } catch (Exception e) {
            logger.error(" Exception while compiling the report "+report,e);
            throw new RuntimeException(" Exception while compiling the report "+report,e);
        }
        }

    private static String getDir() {
        File dir = new File("reports" );
        if(!dir.exists()){
            String message = dir.getPath() + " dir not found";
            logger.error(message);
            throw  new RuntimeException(message);
        }
        return dir.getAbsolutePath()+ File.separator;
    }

    private static String getFileName(String report,String ext) {
        String filesep = "reports" + File.separator;
        return  report.contains("."+ext)?filesep + report: filesep+report+"."+ext;
    }

}
