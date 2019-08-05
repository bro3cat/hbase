package com.per.hu.pool.connection.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import com.per.hu.pool.configuration.hbase.HBaseSimpleConfiguration;

class HBaseConnectionNeverClosed {

	// private List<Connection> connectionList;

	private Connection connection;

	private static Configuration configuration;
	static {
		configuration = HBaseSimpleConfiguration.defaultConfigurate;
	}

	/********************************************************************************************************************************/
	public static void main(String[] args) {
		Connection conn = new HBaseConnectionNeverClosed().getConnection();
		System.out.println(conn);
		// new HBaseConnection().getConnection();
	}

	/********************************************************************************************************************************/
	/**
	 * 初始化一个connection，当“当前”连接不存在或被关闭时，调用该方法
	 */
	private void initConnection() {
		try {
			if (null == connection || connection.isClosed()) {
				// 获取连接对象
				connection = ConnectionFactory.createConnection(configuration);
				if (null == connection)
					System.out.println(getClass() + " Failed to connect!!!");
			}
		} catch (IOException e) {
			System.out.println(getClass() + " Exception to connect!!!");
			e.printStackTrace();
		}
	}

	protected synchronized Connection getConnection() {
		if (null == connection || connection.isClosed())
			initConnection();
		return connection;
	}
	// /********************************************************************************************************************************/
	// public Connection getExtraConnection() {
	// Connection extraConnection = null;
	// try {
	// // 获取连接对象
	// extraConnection = ConnectionFactory.createConnection(configuration);
	// // connection.getAdmin();
	// } catch (IOException e) {
	// System.out.println("failed to connect!!!");
	// e.printStackTrace();
	// }
	// return extraConnection;
	// }

	// /********************************************************************************************************************************/
	// public Connection getOtherConnection() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// /********************************************************************************************************************************/


}
