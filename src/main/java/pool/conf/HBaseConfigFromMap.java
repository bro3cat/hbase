package pool.conf;

import java.io.File;
import java.util.Map;

public class HBaseConfigFromMap extends HConfiguration {


    @Override
    protected void setConfigurationFromMap(Map<String, String> configMap) {
        for (Map.Entry<String, String> entry : configMap.entrySet()) {
            setConf(entry.getKey(), entry.getValue());
        }
    }

    public HBaseConfigFromMap(Map<String, String> map) {
        setConfigurationFromMap(map);
    }

    private HBaseConfigFromMap() {

    }

    ;


    @Override
    protected void setConfigurationFromPropertyFile(File propertyFile) {

    }

    @Override
    protected void setConfigurationFromSources() {

    }

}
