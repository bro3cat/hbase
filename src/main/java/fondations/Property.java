package fondations;

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

    private static Properties properties = new Properties();

    private Properties loadProperties = new Properties();

    static {
        try {
            properties.load(new FileInputStream(new File(StaticConfiguration.configPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Property(File propertyFile) {
        try {
            loadProperties.load(new FileInputStream(new File(StaticConfiguration.configPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private static void loadFile(File propertyFile){
//
//    }

    /**
     * 获取某个属性
     *
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}