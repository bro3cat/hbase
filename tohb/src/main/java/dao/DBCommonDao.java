package dao;

import pool.connection.DBConnectionIn;
import pool.connection.MysqlConnection;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DBCommonDao implements DBCommonDaoIn {

    private DBConnectionIn conn;
    private DatabaseMetaData metaData;

    public DBCommonDao() {
        conn = new MysqlConnection();
        metaData = getMetaData();
    }


    /**
     * 获取表的MetaData
     *
     * @return
     */
    private DatabaseMetaData getMetaData() {
        try {
            return conn.getConnection().getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResultSet scan(String tableName, int batch) throws SQLException {
        String sql = "select * from " + tableName;
        return conn.createStatement().executeQuery(sql);
    }

    @Override
    public List<String> getTableSet(String dbName) {
        return conn.getTableSet(dbName);
    }

    @Override
    public Map<String, String> getColumnSet(String dbName, String tableName) {
        return conn.getColumnSet(dbName, tableName);
    }
}
