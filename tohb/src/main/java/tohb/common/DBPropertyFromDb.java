package tohb.common;

import tohb.pool.connection.DBConnection;
import tohb.pool.connection.DBConnectionImpl;
import tohb.pool.connection.MysqlConnection;

public class DBPropertyFromDb {
    private static String mysql_url;
    private static String sqlServer_url;
    private static String user;
    private static String pwd;

    private static String host;
    private static String port;

    private static DBConnection dbConnection;

//    private static

    static {
//        dbConnection = MysqlConnection();
    }

}
