package pool.conf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;

import java.io.File;
import java.util.Map;

/**
 * 配置文件模板接口，目前《2019年8月5日》只用来配置hBase<br>
 * 有:<br>
 * HBaseConfigFromMap
 * HBaseConfigFromPropertyFile
 * HBaseConfigFromResources<br>
 * 等实现<br>
 * 另外有HBaseConfigNaive类，用于只设置zookeeper的quorum
 */
public abstract class HConfiguration {


    private Configuration conf = HBaseConfiguration.create();

    protected void setConf(String key, String value) {
        conf.set(key, value);
    }

    /**
     * 从单独的配置文件中获取目标数据库的配置文件
     *
     * @param propertyFile
     */
    protected abstract void setConfigurationFromPropertyFile(File propertyFile);


    /**
     * 从sources文件中获取配置文件，如core-site/hbase-site/hdfs-site/yarn-site.xml
     */
    protected abstract void setConfigurationFromSources();

    /**
     * 从map中获取配置文件，如"hbase.zookeeper.quorum","hbasemaster"
     *
     * @param configMap
     */
    protected abstract void setConfigurationFromMap(Map<String, String> configMap);


    public Configuration getConfiguration() {
        return conf;
    }
}
