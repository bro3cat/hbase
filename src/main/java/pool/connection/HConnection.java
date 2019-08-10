package pool.connection;

import pool.conf.HConfiguration;
import org.apache.hadoop.hbase.client.Connection;

import java.io.IOException;

/**
 * 获取HBaseConnection的基本类
 */
public abstract class HConnection {

    /**
     * 返回的connection
     */
    protected Connection connection;


    public Connection getConnection() {
        return connection;
    }


    protected HConfiguration configuration;


    public HConnection(HConfiguration hConfiguration) {
        configuration = hConfiguration;
        connection = createConnection();
    }

    public void close() {
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
