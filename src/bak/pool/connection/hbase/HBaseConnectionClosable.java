package com.per.hu.pool.connection.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import com.per.hu.pool.configuration.HBaseConfigurationModel;
import com.per.hu.pool.configuration.hbase.HBaseSimpleConfiguration;

class HBaseConnectionClosable {

	private static Configuration configuration;
	static {
		configuration = HBaseSimpleConfiguration.defaultConfigurate;
	}

	protected static Connection getExtraConnection() {
		Connection extraConnection = null;
		try {
			// 获取连接对象
			extraConnection = ConnectionFactory.createConnection(configuration);
			// connection.getAdmin();
		} catch (IOException e) {
			System.out.println("failed to connect!!!");
			e.printStackTrace();
		}
		return extraConnection;
	}

	/**
	 * 获取额外的连接对象，区别于neverColsed <br>
	 * 从HBase的配置接口中，读取配置信息，然后生成HBaseConnection<br>
	 * 如果配置接口中存在配置Map则用Map中的内容初始化connection，否则，读取xml文件中的配置信息<br>
	 * 例如：简单的配置信息可实例化HBaseSimpleConfiguration，考虑是否调用setConfigurationMap方法
	 * 
	 * @param configurationModel
	 *            HBase配置接口
	 * @return
	 */
	protected static Connection getExtraConnection(HBaseConfigurationModel configurationModel) {
		Connection extraConnection = null;
		try {
			Configuration configuration = configurationModel.getOtherConfiguration();
			// 获取连接对象
			extraConnection = ConnectionFactory.createConnection(configuration);
		} catch (IOException e) {
			System.out.println("failed to connect!!!");
			e.printStackTrace();
		}
		return extraConnection;
	}
}
