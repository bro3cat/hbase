package com.per.hu.pool.dao.hbase.impl;

import java.util.List;

import org.apache.hadoop.hbase.client.Connection;

import com.per.hu.pool.connection.HBaseConnectionModel;
import com.per.hu.pool.dao.hbase.HBaseDaoModel;

public abstract class HBaseDao implements HBaseDaoModel {

	private HBaseConnectionModel connectionModel = null;

	private Connection connection = null;

	public HBaseDao() {
		inquireConnection(createHBaseConnectionModel());
	}

	@Override
	public void inquireConnection(Connection hBaseConnection) {
		this.connection = hBaseConnection;
	}

	@Override
	public void createHBaseTable(String tableName, List<String> families) {
		// TODO Auto-generated method stub

	}

	protected abstract Connection createHBaseConnectionModel();

}
