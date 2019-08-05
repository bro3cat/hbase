package test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import poll.admin.HBaseAdmin;
import poll.conf.HBaseConfigNaive;
import poll.connection.HBaseConnectionBase;
import poll.connection.HConnection;

import java.io.IOException;
import java.util.ArrayList;

public class Test extends Configuration {
    public static void main(String[] args) {
        System.out.println("zaochenhao");
        HConnection conn = new HBaseConnectionBase(new HBaseConfigNaive("nm"));
        new Test().test(conn);

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