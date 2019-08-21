package tohb.pool.connection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DBConnectionImp implements DBConnectionIn {

    protected DatabaseMetaData metaData = null;
    protected Connection connection = null;

    //    private DBConnection(){}
    public DBConnectionImp() {
        createConnection();
        try {
            metaData = connection.getMetaData();
            System.out.println(metaData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected abstract void createConnection();

    protected abstract void createConnection(String url, String user, String pwd);

    @Override
    public Connection getConnection() {
        return connection;
    }


    @Override
    public Statement createStatement() {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getTableSet(String dbName) {
        List<String> list = new ArrayList<String>();
        ResultSet tableSet = null;
        try {
            tableSet = metaData.getTables(dbName, dbName, "%", new String[]{"TABLE"});
            while (tableSet.next()) {
                list.add(tableSet.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Map<String, String> getColumnSet(String dbName, String tableName) {

        Map<String, String> map = new HashMap<>();
        ResultSet columnSet = null;
        try {
            columnSet = metaData.getColumns(dbName, dbName, tableName, "%");
            while (columnSet.next()) {
                map.put(columnSet.getString("COLUMN_NAME"), columnSet.getString("TYPE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return map;
    }

    @Override
    public List<String> getColumnList(String dbName, String tableName) {

        List<String> list = new ArrayList<>();
        ResultSet columnSet = null;
        try {
            columnSet = metaData.getColumns(dbName, dbName, tableName, "%");
            while (columnSet.next()) {
                list.add(columnSet.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
