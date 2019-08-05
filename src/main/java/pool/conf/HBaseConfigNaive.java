package pool.conf;

import java.io.File;
import java.util.Map;

public class HBaseConfigNaive extends HConfiguration {

    public HBaseConfigNaive(String quorum) {
        setConf("hbase.zookeeper.quorum", quorum);
    }

    @Override
    protected void setConfigurationFromPropertyFile(File propertyFile) {

    }

    @Override
    protected void setConfigurationFromSources() {

    }

    @Override
    protected void setConfigurationFromMap(Map<String, String> configMap) {

    }
}
