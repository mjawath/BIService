package com.techstart.common.utils.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WriteToFile {
    private static Logger LOG= LoggerFactory.getLogger(WriteToFile.class);
    /**
     * should be a util
     * @param fileName
     * @param data
     */
    public static void writeToFile(String fileName,String data){
//        Files.write(Paths.get(OUTPUT_DIRECTORY+template+ LocalDateTime.now().toString()),data);
        Path path = Paths.get(fileName);
        byte[] strToBytes = data.getBytes();
        try {
            Files.write(path, strToBytes);
        } catch (Exception e) {
            LOG.error("error while writing file",e);
            e.printStackTrace();
        }
    }
}
