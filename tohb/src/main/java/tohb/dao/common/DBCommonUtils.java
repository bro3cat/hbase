package tohb.dao.common;

import tohb.pool.connection.DBConnection;
import tohb.pool.connection.DBConnectionCreate;

import java.util.ArrayList;
import java.util.List;

public class DBCommonUtils {

    //    public
    public static List<DBConnection> getConnectionList() {
        DBConnection conn = DBConnectionCreate.createConfigDatabaseConnection();
        List<DBConnection> list = new ArrayList<>();

//        String sql = "SELECT "
        return null;
    }

    public static void main(String[] args) {
        getConnectionList();
    }
}
