package com.per.hu.pool.connection;

import org.apache.hadoop.hbase.client.Connection;

/**
 * 基础HBase数据库连接
 * 
 * @author wyd
 *
 */
public interface HBaseConnectionModel {

	/**
	 * 获取HBase数据库连接
	 * 
	 * @return
	 */
	public Connection getConnection();

	/**
	 * 获取额外的HBase数据库连接，用于临时增加连接，该链接最终需要被释放
	 * 
	 * @return
	 */
	public Connection getExtraConnection();
	// public java.sql.Connection getjConnection();

	/**
	 * 如果需要连接其他HBase数据库，可定制新的连接
	 * 
	 * @return
	 */
	public Connection getOtherConnection();

	// public Connection getOtherExtra

}
