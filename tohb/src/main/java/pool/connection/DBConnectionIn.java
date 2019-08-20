package pool.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public interface DBConnectionIn {

    public Connection getConnection();

    public Statement createStatement();

    public PreparedStatement prepareStatement(String sql);

    public void closeConnection();

    public List<String> getTableSet(String dbName);

    public Map<String, String> getColumnSet(String dbName, String tableName);
}
