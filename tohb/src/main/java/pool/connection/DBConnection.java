package pool.connection;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DBConnection {

    protected Connection connection = null;

    public DBConnection() {
        createConnection();
    }

    public Connection getConnection() {
        return connection;
    }



    protected abstract void createConnection();


    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
