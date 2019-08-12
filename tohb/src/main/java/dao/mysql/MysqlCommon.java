package dao.mysql;

import dao.DBCommonIn;
import org.junit.Test;
import pool.connection.DBConnectionImp;
import pool.connection.DBConnectionIn;
import pool.connection.MysqlConnection;

import java.sql.*;
import java.util.List;

/**
 * mysql数据库的实现
 */
public abstract class MysqlCommon implements DBCommonIn {

    /**
     * tableName作爲本类的内置属性，表示要对tableName表进行操作
     */
    protected String tableName;
    /**
     * connection为内置对象，为数据库连接，
     * 默认MysqlCommon包含<一个表,一个数据库连接>
     */
    protected DBConnectionIn connection;

    /**
     * 构造函数用来初始化表名tableName和内部连接connection
     * 提供默认的配置的connection
     *
     * @param tableName
     */
    public MysqlCommon(String tableName) {
        this.tableName = tableName;
        this.connection = new MysqlConnection();
    }

    /**
     * 构造函数用来初始化表名tableName和内部连接connection
     *
     * @param tableName    设定需要管理的表的名字
     * @param dbConnection 自己创建的connection
     */
    public MysqlCommon(String tableName, DBConnectionIn dbConnection) {
        this.tableName = tableName;
        this.connection = dbConnection;
    }


    @Override
    public void insert(List<String> list) {
        String sql = "INSERT INTO " + tableName + " VALUES (";
        for (String key : list)
            sql += "'" + key + "' ,";
        sql = sql.substring(0, sql.length() - 1) + ")";
        System.out.println(sql);
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        return sql;
    }

    @Override
    public void insertMany(List<List<String>> list) {
        int length = list.get(0).size();
        int index = 0;
        String sql = "INSERT INTO " + tableName + " values (";
        for (index = 0; index < length; ++index)
            sql += "?,";
        sql = sql.substring(0, sql.length() - 1) + ")";
        System.out.println(sql);
        try {
            connection.getConnection().setAutoCommit(false);
            PreparedStatement pstmt = connection.prepareStatement(sql);
            int list_index = 0;

            long t1 = System.currentTimeMillis();
            long t2 = System.currentTimeMillis();

            for (List<String> inList : list) {
                list_index++;
                int pstmt_index = 1;
                for (String value : inList) {
                    pstmt.setString(pstmt_index++, value);
                }
                pstmt.addBatch();
                if (list_index > 5000) {
                    System.out.println("excuting-----------------------");
                    long t3 = System.currentTimeMillis();
                    pstmt.executeBatch();
                    connection.getConnection().commit();
                    pstmt.clearBatch();
                    list_index = 0;
                    long t4 = System.currentTimeMillis();
                    System.out.println((t4 - t3));

                }
            }
            pstmt.executeBatch();
            t2 = System.currentTimeMillis();
            System.out.println("总体上" + (t2 - t1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ResultSet read(String key) {
        return null;
    }

    @Override
    public ResultSet readTable() {
        return null;
    }

    @Test
    public void test1() {
//        Connection conn = connection;
        PreparedStatement stmt = null;
        long t1 = System.currentTimeMillis();
        try {
            stmt = connection.prepareStatement("insert into test1 values(?,?,?)");
            for (int i = 0; i < 10000; i++) {
                stmt.setString(1, i + "");
                stmt.setString(2, i + "");
                stmt.setString(3, i + "");
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            JDBCUtil.release(null, stmt, conn);
        }
        long t2 = System.currentTimeMillis();
        System.out.println((t2 - t1));
    }

}
