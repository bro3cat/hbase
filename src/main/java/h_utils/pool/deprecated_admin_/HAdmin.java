package h_utils.pool.deprecated_admin_;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import h_utils.pool.connection.HConnection;

import java.io.IOException;

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


    public void disableTable(TableName tableName) {
        try {
            if (admin.isTableAvailable(tableName))
                admin.disableTable(tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ableTable(TableName tableName){
        try {
            if(admin.isTableDisabled(tableName))
                admin.enableTable(tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public Admin getAdmin() {
        return admin;
    }

}
