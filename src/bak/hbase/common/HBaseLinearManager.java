package com.per.hu.hbase.common;

import org.apache.hadoop.hbase.client.Connection;

import com.per.hu.pool.dao.hbase.HBaseDaoModel;

/**
 * 线性处理事物的manager，用于被外部实现的虚类
 * 
 * @author wyd
 *
 */
public abstract class HBaseLinearManager {

	private HBaseDaoModel daoModel = null;
	
	private Connection connection = null;
	
//	private
	
//	daomode
	public void test(){
//		daoModel.createHBaseTable(tableName, families);
	}
	
//	public void setHBaseConnection()
	public abstract void connectHBase();
	
	public abstract void hBaseJob();

}
