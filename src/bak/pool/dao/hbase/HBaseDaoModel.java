package com.per.hu.pool.dao.hbase;

import java.util.List;

import org.apache.hadoop.hbase.client.Connection;

import com.per.hu.pool.connection.HBaseConnectionModel;

public interface HBaseDaoModel {

	/**
	 * 初始化到HBase数据库的连接
	 */
	public void inquireConnection(Connection hBaseConnection);
	
	public void createHBaseTable(String tableName, List<String> families);
	
//	public
	
}
