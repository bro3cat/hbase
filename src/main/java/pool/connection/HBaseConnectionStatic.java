package pool.connection;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import pool.conf.HBaseConfigNaive;
import pool.conf.HConfiguration;

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
