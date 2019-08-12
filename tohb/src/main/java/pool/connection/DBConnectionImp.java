package pool.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DBConnectionImp implements DBConnectionIn {

    protected Connection connection = null;

    //    private DBConnection(){}
    public DBConnectionImp() {
        createConnection();
    }

    protected abstract void createConnection();

    protected abstract void createConnection(String url, String user, String pwd);

    @Override
    public Connection getConnection() {
        return connection;
    }


    @Override
    public Statement createStatement() {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
