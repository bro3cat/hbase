package poll.admin;

import org.apache.hadoop.hbase.client.Admin;
import poll.connection.HConnection;

/**
 * 获取HBaseConnection 的admin
 */
public abstract class HAdmin {

    protected Admin admin;

    /**
     * 創建一admin
     * @param hConnection
     */
    protected abstract void createAdmin(HConnection hConnection);

    public HAdmin(HConnection hConnection) {
        createAdmin(hConnection);
    }

    public Admin getAdmin() {
        return admin;
    }

}
