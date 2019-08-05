package poll.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

public class HBaseConfigFromPropertyFile extends HConfiguration {


    @Override
    protected void setConfigurationFromPropertyFile(File propertyFile) {
        Properties loadProperties = new Properties();
        try {
            loadProperties.load(new FileInputStream(propertyFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Enumeration enu = loadProperties.propertyNames();
        while (enu.hasMoreElements()) {
            String key = (String) enu.nextElement();
            setConf(key, loadProperties.getProperty(key));
        }
    }

    public HBaseConfigFromPropertyFile(File file) {
        setConfigurationFromPropertyFile(file);
    }

    private HBaseConfigFromPropertyFile() {
    }

    @Override
    protected void setConfigurationFromSources() {

    }

    @Override
    protected void setConfigurationFromMap(Map<String, String> configMap) {

    }
}
