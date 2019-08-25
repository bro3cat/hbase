package tohb.pool.connection;

import h_utils.config.Property;
import h_utils.config.StaticConfiguration;
import tohb.common.SqlProperty;
import tohb.config.Config;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionCreate {


    /**
     * 直接获得默认的DBConnection
     *
     * @param dbType
     * @return
     */
    public static DBConnection create(String dbType) {
        switch (dbType) {
            case "mysql":
                return new MysqlConnection();
        }

        return null;

    }


    public static DBConnection create(String dbType, String url, String user, String pwd) {
        switch (dbType) {
            case "mysql":
                return new MysqlConnection();
        }

        return null;
    }

//    public static DBConnection


    /**
     * 根据地址返得到DBConnection
     *
     * @param path
     * @return
     */
    public static DBConnection createConfigDatabaseConnection(final String path) {

        return new DBConnectionImpl() {

            SqlProperty property = new SqlProperty(path);

            @Override
            protected void createConnection() {
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
        };

    }

    /**
     * 返回在config中配置的两个配置文件中，获取的存储db的db的配置
     * （哪个配置存在返回哪个，主要因为第一个配置是测试时用到）
     *
     * @return
     */
    public static DBConnection createConfigDatabaseConnection() {

        return new DBConnectionImpl() {

            SqlProperty property;

            @Override
            protected void createConnection() {

                File file1 = new File(Config.config1);
                File file2 = new File(Config.config2);
                if (file1.exists()) property = new SqlProperty(Config.config1);
                else if (file2.exists()) property = new SqlProperty(Config.config2);
                else property = null;

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
        };

    }

//    public static DBConnection create(String dbType, )
}
