package sqldbutils.pool.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 实现Mysql数据库获取连接的类
 */
public class MysqlConnection extends DBConnectionImpl {
    public MysqlConnection(String url, String user, String password) {
        super(url, user, password);
    }

    @Override
    protected Connection loadClassAndCreate(String url, String user, String password) {
        Connection connection = null;
        try {
            //Mysql驱动，8.*
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
