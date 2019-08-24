package h_utils.pool.connection;

import h_utils.pool.conf.HConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;


public class HBaseConnectionBase extends HConnection {

    /**
     * 可以生成新的数据库Connection
     *
     * @param hConfiguration
     */
    public HBaseConnectionBase(HConfiguration hConfiguration) {
        super(hConfiguration);
    }

    @Override
    protected Connection createConnection() {
        Connection conn = null;
        try {
            conn = ConnectionFactory.createConnection(configuration.getConfiguration());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
