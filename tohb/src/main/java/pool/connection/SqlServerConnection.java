package pool.connection;

import java.sql.ResultSet;
import java.util.Map;

public class SqlServerConnection extends DBConnectionImp {
    @Override
    protected void createConnection() {

    }

    @Override
    protected void createConnection(String url, String user, String pwd) {


    }

    @Override
    public Map<String, String> getColumnSet(String dbName, String tableName) {
        return null;
    }
}
