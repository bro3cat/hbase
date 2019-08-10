package pool.admin;

import pool.connection.HConnection;

import java.io.IOException;

public class HBaseAdmin extends HAdmin {
    public HBaseAdmin(HConnection hConnection) {
        super(hConnection);
    }

    @Override
    protected void createAdmin(HConnection hConnection) {
        try {
            this.admin = hConnection.getConnection().getAdmin();
            this.connection = hConnection.getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
