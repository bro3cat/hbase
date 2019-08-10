package pool.admin;

import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import pool.connection.HConnection;

/**
 * 获取HBaseConnection 的admin
 */
public abstract class HAdmin {

    protected Admin admin;

    protected Connection connection;

    /**
     * 創建一admin
     *
     * @param hConnection
     */
    protected abstract void createAdmin(HConnection hConnection);

    public HAdmin(HConnection hConnection) {
        createAdmin(hConnection);
    }

    public Connection getConnection() {
        return connection;
    }

    public Admin getAdmin() {
        return admin;
    }

}
