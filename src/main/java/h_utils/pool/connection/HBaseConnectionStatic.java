package h_utils.pool.connection;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import h_utils.pool.conf.HBaseConfigNaive;
import h_utils.pool.conf.HConfiguration;

import java.io.IOException;

public class HBaseConnectionStatic {


    private static HConfiguration configuration = new HBaseConfigNaive("hmaster");

    public static Connection createConnection() {
        try {
            return ConnectionFactory.createConnection(configuration.getConfiguration());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
