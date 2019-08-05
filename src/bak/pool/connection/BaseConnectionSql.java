package com.per.hu.pool.connection;

import java.sql.Connection;

/**
 * 基础SQL类数据库连接
 * @author wyd
 *
 */
public interface BaseConnectionSql {
	/**
	 * 获取SQL数据库连接
	 * 
	 * @return
	 */
	public Connection getConnection();

	/**
	 * 获取额外的SQL数据库连接，用于临时增加连接，该链接最终需要被释放
	 * 
	 * @return
	 */
	public Connection getExtraConnection();
}
