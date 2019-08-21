package h_utils.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import h_utils.pool.deprecated_admin_.HBaseAdmin;
import h_utils.pool.conf.HBaseConfigNaive;
import h_utils.pool.connection.HBaseConnectionBase;
import h_utils.pool.connection.HConnection;

import java.io.IOException;
import java.util.ArrayList;

public class Test extends Configuration {
    public static void main(String[] args) {
        System.out.println("zaochenhao");
        HConnection conn = new HBaseConnectionBase(new HBaseConfigNaive("nm"));
        new Test().test(conn);


        HConnection conn2 = new HBaseConnectionBase(new HBaseConfigNaive("10.0.112.216"));
        System.out.println(conn2);

//        HBaseConfigFromPropertyFile conf = new HBaseConfigFromPropertyFile(new File(""));
//        conf = new HBaseConfigFromPropertyFile(new File(""));

//        HConfigurationModel model = new HBaseConfigFromResources();
//        Connection conn =
    }

    public void test(HConnection conn) {
//        Configuration conf = new HBaseConfigNaive("nm").getConfiguration();
        System.out.println(new HBaseAdmin(conn));
        Admin admin = new HBaseAdmin(conn).getAdmin();
        ArrayList<String> names = new ArrayList<String>();
        try {
            TableName[] tableNames = admin.listTableNames();
            for (TableName tableName : tableNames) {
                names.add(tableName.toString());
                System.out.println(tableName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}