package h_utils.pool.deprecated_admin_;

import h_utils.pool.connection.HConnection;

public class HBaseAdmin extends HAdmin {
    public HBaseAdmin(HConnection hConnection) {
        super(hConnection);
    }


    @Override
    protected void createAdmin(HConnection hConnection) {
        this.admin = hConnection.getAdmin();
        this.connection = hConnection.getConnection();
    }
}
