package com.per.hu.pool.configuration.hbase;

import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

import com.per.hu.pool.configuration.HBaseConfigurationModel;
import com.per.hu.tools.Log;
import com.per.hu.tools.Property;

/**
 * 简单的HBase配置文件，如果需要自定义配置，可以实现方法<br>
 * public Configuration getOtherConfiguration()<br>
 * 的复写。<br>
 * 如果需要简单的配置，可以查看各方法的区别
 * 
 * @author wyd
 *
 */
public class HBaseSimpleConfiguration implements HBaseConfigurationModel {

	public static Configuration defaultConfigurate = null;

	/**
	 * 如果设置了configMap则表示从map中加载配置，否则，从默认的文件中加载配置
	 */
	private Map<String, String> configurationMap = null;

	public void setConfigurationMap(Map<String, String> map) {
		configurationMap = map;
	}

	static {
		defaultConfigurate = new HBaseSimpleConfiguration().getHBaseZookeeperSimpleQuorumConfiguration();
	}

	/*********************************************************************************************
	 * 一个简单的对zookeeper的设置，通常情况下，需要复杂设置可以拷贝服务器的hbase-site.xml文件过来
	 * 
	 * @return
	 */
	private Configuration getHBaseZookeeperSimpleQuorumConfiguration() {
		Log.lineStart(this);
		Log.date("开始加载配置");
		Log.date("加载简单配置--HbaseConnectionNeveClosed");
		Configuration config = HBaseConfiguration.create();
		config.set("hbase.zookeeper.quorum", Property.getProperty("hbase.zookeeper.quorum"));
		Log.noDate("hbase.zookeeper.quorum" + " : " + Property.getProperty("hbase.zookeeper.quorum"));
		Log.date("加载完毕");
		Log.lineEnd(this);
		return config;
	}

	@Override
	public Configuration getConfiguration() {

		return getHBaseZookeeperSimpleQuorumConfiguration();
	}

	/*********************************************************************************************
	 * 
	 * @return
	 */
	// @Override
	// public Configuration getOtherConfiguration(Map<String, String> configMap)
	// {
	//
	// Log.lineStart(this);
	// Log.date("开始加载配置");
	// Log.date("加载MAP类型配置");
	// Configuration config = HBaseConfiguration.create();
	// for (Map.Entry<String, String> entry : configMap.entrySet()) {
	// config.set(entry.getKey(), entry.getValue());
	// Log.noDateDouble(entry.getKey(), entry.getValue());
	// }
	// Log.date("加载完毕");
	// Log.lineEnd(this);
	// return config;
	// }

	@Override
	public Configuration getOtherConfiguration() {
		Log.lineStart(this);
		Log.date("额外的配置信息:<xml文件>或<Map>");
		Configuration conf = HBaseConfiguration.create();
		if (null == this.configurationMap) {
			Log.date("启用xml文件配置HBase连接");
			Log.noDate("加载hbase-site.xml");
			conf.addResource("hbase-site.xml");
			Log.noDate("加载core-site.xml");
			conf.addResource("core-site.xml");
			Log.noDate("加载yarn-site.xml");
			conf.addResource("yarn-site.xml");
			Log.noDate("加载hdfs-site.xml");
			conf.addResource("hdfs-site.xml");
			return conf;
		} else {
			Log.date("启用Map配置文件，配置信息如下：");
			for (Map.Entry<String, String> entry : configurationMap.entrySet()) {
				conf.set(entry.getKey(), entry.getValue());
				Log.noDateDouble(entry.getKey(), entry.getValue());
			}
		}
		Log.lineEnd(this);
		return conf;
	}

}
