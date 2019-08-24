package h_utils.pool.connection;

import h_utils.pool.conf.HConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

/**
 * 获取HBaseConnection的基本类
 */
public abstract class HConnection {

    /**
     * 返回的connection
     */
    protected Connection connection;

    //HBase数据库配置
    protected HConfiguration configuration;

    /**
     * 获取HBase的Table表
     *
     * @param tableName Table's Name
     * @return Table
     */
    public Table getTable(String tableName) {
        try {
            return connection.getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取HBase连接Connection
     *
     * @return
     */
    public Connection getConnection() {
        return connection;
    }


    /**
     * 通过HConfiguration生成关闭HBase数据库连接数据库连接
     *
     * @param hConfiguration
     */
    public HConnection(HConfiguration hConfiguration) {
        configuration = hConfiguration;
        connection = createConnection();
    }

    /**
     * 关闭HBase数据库连接
     */
    public void close() {
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取HBase数据库Admin
     *
     * @return
     */
    public Admin getAdmin() {
        try {
            return connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HConnection() {
    }

    /**
     * 用于被实现的，创建connection的方法，所有继承了HConnection的类都需要实现该方法，并返回一个HBase Connection
     *
     * @return 返回一个HBase Connection
     */
    protected abstract Connection createConnection();

}
