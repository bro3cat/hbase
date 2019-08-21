package h_utils.pool.conf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

public class HBaseConfig extends HConfiguration {

    private Configuration conf = HBaseConfiguration.create();


    @Override
    public void setConfigurationFromPropertyFile(File propertyFile) {
        Properties loadProperties = new Properties();
        try {
            loadProperties.load(new FileInputStream(propertyFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Enumeration enu = loadProperties.propertyNames();
        while (enu.hasMoreElements()) {
            String key = (String) enu.nextElement();
            conf.set(key, loadProperties.getProperty(key));
        }

    }


    @Override
    public void setConfigurationFromSources() {
    }

    @Override
    public void setConfigurationFromMap(Map<String, String> configMap) {
        for (Map.Entry<String, String> entry : configMap.entrySet()) {
            conf.set(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Configuration getConfiguration() {
        return conf;
    }


}
