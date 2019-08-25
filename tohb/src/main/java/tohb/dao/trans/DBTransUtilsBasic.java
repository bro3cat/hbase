package tohb.dao.trans;

import tohb.pool.connection.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class DBTransUtilsBasic implements DBTransUtils {

    private DBConnection conn;

    public DBTransUtilsBasic(DBConnection dbConnection) {
        conn = dbConnection;
    }

    @Override
    public ResultSet scan(String dbName, String tableName, int batchStart, int batchStop) throws SQLException {
        String sql = "select * from " + dbName + "." + tableName + "  limit " + batchStart + " ," + batchStop;
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

    @Override
    public List<String> getColumnList(String dbName, String tableName) {
        return conn.getColumnList(dbName, tableName);
    }

    @Override
    public int getTableSize(String dbName, String tableName) throws SQLException {
        String sql = "select count(*) from " + dbName + "." + tableName;
        //Log.say(sql);
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while (rs.next())
            return (rs.getInt(1));

        return 0;
    }

    @Override
    public String getPrimaryName(String dbName, String tableName) throws SQLException {
        String sql = "select table_schema, table_name,column_name from  INFORMATION_SCHEMA.KEY_COLUMN_USAGE  t where t.table_schema='" + dbName + "'";
        //Log.say(sql);
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while (rs.next()) {
            if (tableName.equals(rs.getString(2)))
                return rs.getString(3);
        }
        return "";
    }
}
