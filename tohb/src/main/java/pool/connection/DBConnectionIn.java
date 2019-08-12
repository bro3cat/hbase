package pool.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public interface DBConnectionIn {

    public Connection getConnection();

    public Statement createStatement();

    public PreparedStatement prepareStatement(String sql);

    public void closeConnection();
}
