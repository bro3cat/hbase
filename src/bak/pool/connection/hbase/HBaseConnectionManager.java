package com.per.hu.pool.connection.hbase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.hbase.client.Connection;

import com.per.hu.pool.configuration.HBaseConfigurationModel;
import com.per.hu.pool.configuration.hbase.HBaseSimpleConfiguration;
import com.per.hu.tools.Log;

public class HBaseConnectionManager {

	private HBaseConnectionManager() {
	}

	/**
	 * 一个永远存在的connection
	 */
	private static final HBaseConnectionNeverClosed neverCloseConnection = new HBaseConnectionNeverClosed();

	/**
	 * 一个永远存在的connection
	 * 
	 * @return
	 */
	public static Connection createHBaseNeverClosedSimpleConnection() {
		return neverCloseConnection.getConnection();
	}

	public static Connection createExtraConnection(Map<String, String> configurationMap) {

		// 创建配置
		HBaseConfigurationModel configurationModel = new HBaseSimpleConfiguration();
		// 应用配置configurationMap
		configurationModel.setConfigurationMap(configurationMap);
		// 利用Map创建closable connection
		Connection connection = HBaseConnectionClosable.getExtraConnection(configurationModel);
		// configurationMap.put(connection.toString(), connection);
		return connection;
	}

	/**
	 * 关闭一条数据库连接
	 *
	 * @param connection
	 */
	public static void close(Connection connection) {
		Log.date("开始关闭connection，这是一条HBaseConnectionClosable");
		if (!connection.isClosed() && null != connection) {
			Log.date("尝试关闭HBaseConnectionClosable");
			try {
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Log.date("已经关闭HBaseConnectionClosable");
		} else {
			Log.date("空的或已经关闭的HBaseConnectionClosable");
		}
	}

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		map.put("hbase.zookeeper.quorum", "tm1");
		Connection conn = createExtraConnection(map);
		System.out.println(conn);
		close(conn);
	}

}
