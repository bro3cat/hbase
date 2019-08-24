package tohb.pool.connection;

import tohb.common.SqlProperty;
import h_utils.config.StaticConfiguration;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DBConnectionBasicMysql extends DBConnectionBasic {

    private SqlProperty property;//= new Property(StaticConfiguration.mysql_property);

    /**
     * test
     */

    DatabaseMetaData metaData = null;


    public DBConnectionBasicMysql() {
        super();
        try {
            metaData = connection.getMetaData();
//            System.out.println(metaData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DBConnectionBasicMysql connection = new DBConnectionBasicMysql();
        try {
            ResultSet tableSet = connection.metaData.getTables("test", "test", "test1", new String[]{"TABLE"});

            ResultSet colSet = connection.metaData.getColumns("%", "casia", "test", "%");
            while (colSet.next()) {
                String colName = colSet.getString("COLUMN_NAME");
                String colType = colSet.getString("TYPE_NAME");
//                System.out.println(colName + "->:" + colType);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        List list = connection.getTableSet("test");
        for (Object s : list) System.out.println(s);
        Map<String, String> map = connection.getColumnSet("test", "test1");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.printf("%5s : %s\n", entry.getKey(), entry.getValue());
        }
    }


    @Override
    protected void createConnection() {


        property = new SqlProperty(StaticConfiguration.mysql_property);
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


//    @Override
//    public ResultSet getColumnSet(String tableName) {
//        return null;
//    }
}
