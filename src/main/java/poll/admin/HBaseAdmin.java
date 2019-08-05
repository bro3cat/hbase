package poll.admin;

import poll.connection.HConnection;

import java.io.IOException;

public class HBaseAdmin extends HAdmin {
    public HBaseAdmin(HConnection hConnection) {
        super(hConnection);
    }

    @Override
    protected void createAdmin(HConnection hConnection) {
        try {
            this.admin = hConnection.getConnection().getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
