package pool.connection;

import common.Property;
import common.SqlProperty;
import common.StaticConfiguration;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection extends DBConnectionImp {

    private SqlProperty property;//= new Property(StaticConfiguration.mysql_property);


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
}
