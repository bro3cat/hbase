package h_utils.pool.connection;

import h_utils.config.StaticConfiguration;
import h_utils.utils.Log;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import h_utils.pool.conf.HBaseConfigNaive;
import h_utils.pool.conf.HConfiguration;

import java.io.IOException;

/**
 * 包含不允许被关闭的，可以通过get_NOTCLOSABLE_Connection（）方法获取的NOTCLOSABLE_hConnection
 */
public class HBaseConnectionStatic {


    private static final HConfiguration configuration;

    private static final HBaseConnection NOTCLOSABLE_hConnection;

    static {
        configuration = new HBaseConfigNaive(StaticConfiguration.hmaster);
        Log.say2("HBaseConfigNaive", "OK");
        NOTCLOSABLE_hConnection = new HBaseConnectionBasic(configuration);
        Log.say2("NOTCLOSABLE_hConnection", "OK");
    }

    public static Connection createConnection() {
        try {
            return ConnectionFactory.createConnection(configuration.getConfiguration());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HBaseConnection get_NOTCLOSABLE_Connection() {
        Log.say2("get_NOTCLOSABLE_Connection", "OK");
        return NOTCLOSABLE_hConnection;
    }
}
