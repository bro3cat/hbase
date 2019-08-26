package sqldbutils.pool.connection.config;

import sqldbutils.pool.connection.DBConnection;
import sqldbutils.pool.connection.MysqlConnection;
import utils.SqlProperty;

import java.io.File;

public class DefaultConfigDBConnection {
    private static DBConnection dbConnection = null;
    private static SqlProperty property;

    static {
        File file1 = new File(Config.config1);
        File file2 = new File(Config.config2);
        if (file1.exists()) property = new SqlProperty(Config.config1);
        else if (file2.exists()) property = new SqlProperty(Config.config2);
        else property = null;
        dbConnection = new MysqlConnection(property.url(), property.user(), property.password());
    }

    public static DBConnection defaultConfigDBConnection() {
        return dbConnection;
    }

}
