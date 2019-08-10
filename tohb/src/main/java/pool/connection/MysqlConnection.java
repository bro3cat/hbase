package pool.connection;

import common.Property;
import common.StaticConfiguration;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection extends DBConnection {

    private Property property;//= new Property(StaticConfiguration.mysql_property);

    @Override
    protected void createConnection() {
        property = new Property(StaticConfiguration.mysql_property);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://"
                            + property.getLoadProperty("db.host")
                            + ":"
                            + property.getLoadProperty("db.port")
                            + "/"
                            + property.getLoadProperty("db.name")
                            + "?useSSL=false&serverTimezone=UTC",
                    property.getLoadProperty("db.user"),
                    property.getLoadProperty("db.password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new MysqlConnection().createConnection();
    }
}
