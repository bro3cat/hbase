package dao;

import pool.connection.DBConnectionIn;
import pool.connection.MysqlConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * mysql数据库的实现
 */
public class DBCommonDao implements DBCommonDaoIn {
    protected Statement stmt = null;
    protected PreparedStatement pstmt = null;

    public void closeStmt() {
        try {
            if (null != stmt)
                if (!stmt.isClosed())
                    stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void closePstmt() {
        try {
            if (null != pstmt)
                if (!pstmt.isClosed())
                    pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
    public DBCommonDao(String tableName) {
        this.tableName = tableName;
        this.connection = new MysqlConnection();
        stmt = connection.createStatement();
    }

    /**
     * 构造函数用来初始化表名tableName和内部连接connection
     *
     * @param tableName    设定需要管理的表的名字
     * @param dbConnection 自己创建的connection
     */
    public DBCommonDao(String tableName, DBConnectionIn dbConnection) {
        this.tableName = tableName;
        this.connection = dbConnection;
        stmt = connection.createStatement();
    }


    @Override
    public void insert(List<String> list) {
        String sql = "INSERT INTO " + tableName + " VALUES (";
        for (String key : list)
            sql += "'" + key + "' ,";
        sql = sql.substring(0, sql.length() - 1) + ")";
        System.out.println(sql);
        try {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertMany(List<List<String>> list, int batchSize) {
        int length = list.get(0).size();
        int index = 0;
        String sql = "INSERT INTO " + tableName + " values (";
        for (index = 0; index < length; ++index)
            sql += "?,";
        sql = sql.substring(0, sql.length() - 1) + ")";
        System.out.println(sql);
        try {
            closePstmt();
            connection.getConnection().setAutoCommit(false);
            pstmt = connection.prepareStatement(sql);
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
                if (list_index > batchSize) {
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
            connection.getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public long test(List<List<String>> list, int batchSize) {
        String del = "truncate table " + tableName;
        String sql = "INSERT INTo " + tableName + " values ";
        int test = 100;
        try {
            stmt.execute(del);
            int length = list.get(0).size();
            int index = 0;
            List temp = null;

            long t0 = System.currentTimeMillis();


            for (int i = 0; i < list.size(); i++) {
                long t1 = System.currentTimeMillis();
                temp = list.get(i);
                sql += "('" + temp.get(0) + "','" + temp.get(1) + "','" + temp.get(2) + "'),";
                if (i == 1) System.out.println(sql);
                if (i % batchSize == 0) {

                    sql = sql.substring(0, sql.length() - 1) + "";
                    stmt.execute(sql);
                    sql = "INSERT INTO " + tableName + " values ";
                    long t2 = System.currentTimeMillis();
//                    System.out.println(i + "\t_单步：" + (t2 - t1));
                }
            }
            sql = sql.substring(0, sql.length() - 1) + "";
            stmt.execute(sql);
            long t3 = System.currentTimeMillis();
            System.out.println("总体上" + (t3 - t0));
            return t3 - t0;
        } catch (SQLException e) {
            System.out.println(sql);
            e.printStackTrace();
        }

/**


 for (index = 0; index < length; ++index)
 sql += "?,";
 sql = sql.substring(0, sql.length() - 1) + ")";
 System.out.println(sql);
 try {
 closePstmt();
 connection.getConnection().setAutoCommit(false);
 pstmt = connection.prepareStatement(sql);
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
 if (list_index > batchSize) {
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
 connection.getConnection().setAutoCommit(true);
 } catch (SQLException e) {
 e.printStackTrace();
 }
 **/
        return 0;
    }

    @Override
    public ResultSet read(String key) {
        return null;
    }

    ;

    @Override
    public ResultSet readTable() {
        return null;
    }

    ;


}
