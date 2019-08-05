package com.per.hu.test;

import org.apache.hadoop.hbase.client.Connection;

import com.per.hu.pool.connection.hbase.HBaseConnectionManager;
import com.per.hu.pool.dao.hbase.impl.HBaseDao;

public class Test extends HBaseDao{

	public static void main(String[] args) {
//		HBaseDaoModel baseDaoModel = new HBaseDao();
		new Test();
	}

	@Override
	public Connection createHBaseConnectionModel() {
		// TODO Auto-generated method stub
		return HBaseConnectionManager.createHBaseNeverClosedSimpleConnection() ;
	}
}
