package com.zoom.risk.platform.ctr.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileLoader {
    private static final Logger logger = LogManager.getLogger(FileLoader.class);
    private static final String FILE_PATH = "/conf/ls.stv";

    public static final String loadFileContent(){
        InputStream inputStream = FileLoader.class.getClassLoader().getResourceAsStream(FILE_PATH);
        BufferedReader reader = new BufferedReader( new InputStreamReader(inputStream));
        String content = null;
        try {
            content = reader.readLine();
            reader.close();
        }catch (Exception e){
            logger.error("",e);
        }
        return content;
    }
}
