package com.per.hu.pool.configuration;

import java.util.Map;

import org.apache.hadoop.conf.Configuration;

/**
 * 简单的HBase配置文件类
 *
 * @author wyd
 */
public interface HBaseConfigurationModel {

    /**
     * 从本地配置文件中获取HBase的配置，通常为简单的zookeeper.quorum设置，<br>
     * 如果需要复杂配置，可以调用getOtherConfiguration(Map<String, String> configMap)
     */
    public Configuration getConfiguration();

    /**
     * 更对的针对HBase进行的配置，将配置放进configMap中
     *
     * @param configMap 放置HBase配置
     * @return
     */
//	public Configuration getOtherConfiguration(Map<String, String> configMap);
    public void setConfigurationFromMap(Map<String, String> map);

    public void setConfigurationFromProperty(File propertyFile);

    public void setConfigurationFromSources();

    /**
     * 从文件中获取HBase数据库配置，包括<br>
     * core-site.xml<br>
     * hdfs-site.xml<br>
     * hbase-site.xml<br>
     * yarn-site.xml<br>
     * 通常如果用来连接，只需要hbase-site.xml
     *
     * @return
     */
    public Configuration getOtherConfiguration();
}
