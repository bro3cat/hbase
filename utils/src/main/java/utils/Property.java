package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Properties类的简单封装
 *
 * @author wyd
 */
public class Property {

    private Properties loadProperties = new Properties();


    public Property(String propertyFilePath) {
        try {
            loadProperties.load(new FileInputStream(new File(propertyFilePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取某个属性
     *
     * @param key
     * @return
     */
    public String getProperty(String key) {
        return loadProperties.getProperty(key);
    }

}