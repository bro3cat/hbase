package tohb.pool.connection;

import h_utils.config.StaticConfiguration;
import tohb.common.SqlProperty;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection extends DBConnectionImpl {

    private SqlProperty property;

    /**
     * test
     */

    DatabaseMetaData metaData = null;


    public MysqlConnection() {
        super();

    }


    @Override
    protected void createConnection() {


        property = new SqlProperty(StaticConfiguration.mysql_property);
        createConnection(property.url(), property.user(), property.password());

    }

    @Override
    protected void createConnection(String url, String user, String pwd) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println(url);
            connection = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


//    @Override
//    public ResultSet getColumnSet(String tableName) {
//        return null;
//    }
}
