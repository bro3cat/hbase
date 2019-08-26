package sqldbutils.pool.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DBConnection的实现模板，内置了Connection，
 * 内置protected abstract方法为loadClassAndCreate(url,user,password)生成Connection
 */
public abstract class DBConnectionImpl implements DBConnection {

    /**
     * 内置的Connection对象
     */
    protected Connection _connection = null;

    public DBConnectionImpl(String url, String user, String password) {
        //初始化时，实例化内置对象
        _connection = loadClassAndCreate(url, user, password);
    }

    @Override
    public Connection get_connection() {
        return _connection;
    }


    /**
     * 加载驱动，并生成Connection
     *
     * @param url
     * @param user
     * @param password
     */
    protected abstract Connection loadClassAndCreate(String url, String user, String password);


    @Override
    public PreparedStatement prepareStatement(String sql) {
        try {
            return _connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void closeConnection() {

        try {
            if (null != _connection && !_connection.isClosed())
                _connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Statement createStatement() {
        try {
            return _connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
