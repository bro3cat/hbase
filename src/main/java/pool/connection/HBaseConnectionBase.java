package pool.connection;

import pool.conf.HConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

public class HBaseConnectionBase extends HConnection {

    public HBaseConnectionBase(HConfiguration hConfiguration) {
        super(hConfiguration);
    }

    @Override
    protected Connection createConnection() {
        Connection conn = null;
        try {
            conn = ConnectionFactory.createConnection(configuration.getConfiguration());
            System.out.println("if");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
