package dao;

import entity.DBTable;

import java.sql.ResultSet;

public interface DBCommonDaoIn {

    /**
     *
     * @param table 所在的catalog/schema/tableName和基本表结构
     */
    public void createTable(DBTable table);

    /**
     * 删除表
     * @param tableName tableName
     */
    public void delTable(String tableName);

    /**
     * 按照batch大小读取
     * @param tableName
     * @param batch
     * @return
     */
    public ResultSet scan(String tableName, int batch);

}
