package tohb.dao.mysql;

import tohb.dao.Deprecated_DBDao;
import tohb.pool.connection.DBConnection;

import java.sql.*;

/**
 * mysql数据库的实现
 */
public class Deprecated_MysqlDao extends Deprecated_DBDao {


    /**
     * 构造函数用来初始化表名tableName和内部连接connection
     * 提供默认的配置的connection
     *
     * @param tableName
     */
    public Deprecated_MysqlDao(String tableName) {
        super(tableName);
    }

    /**
     * 构造函数用来初始化表名tableName和内部连接connection
     *
     * @param tableName    设定需要管理的表的名字
     * @param dbConnection 自己创建的connection
     */
    public Deprecated_MysqlDao(String tableName, DBConnection dbConnection) {
        super(tableName, dbConnection);
    }


    @Override
    public ResultSet read(String key) {
        String sql = "select * from " + tableName + "where key = " + key;
        return null;
    }

    @Override
    public ResultSet readTable() {
        ResultSet rs;

        return null;
    }


}
