package sqldbutils.pool.connection;

import utils.SqlProperty;

public class DBConnectionManager {


    public static DBConnection loadConfigConnectionFromPropertyFile(String propertyFilePath) {
        DBConnection dbConnection = null;
        SqlProperty property = new SqlProperty(propertyFilePath);

        dbConnection = new MysqlConnection(property.url(), property.user(), property.password());

        return dbConnection;
    }
}
