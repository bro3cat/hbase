package pool.conf;

import java.io.File;
import java.util.Map;

public class HBaseConfigFromResources extends HConfiguration {
    @Override
    protected void setConfigurationFromPropertyFile(File propertyFile) {
    }

    @Override
    public void setConfigurationFromSources() {

    }

    @Override
    protected void setConfigurationFromMap(Map<String, String> configMap) {
    }
}
