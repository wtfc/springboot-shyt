package com.jc.foundation.ueditor;

import java.io.InputStream;
import java.util.Properties;

public class FtpPropertiesReader {
	
	
    public FtpPropertiesReader() {
    }
    private static Properties _properties = null;
    static{
    	try {
    		
    		InputStream ins = FtpPropertiesReader.class.getResourceAsStream("/ftp.properties");
            _properties = new Properties();
            _properties.load(ins);
          
		} catch (Exception e) {
			
		}
    }
    public static String getProperty(String key) {
        return _properties.getProperty(key);
    }
    public static void main(String args[]){
    	System.out.println(FtpPropertiesReader.getProperty("ftpHost"));
    }
}