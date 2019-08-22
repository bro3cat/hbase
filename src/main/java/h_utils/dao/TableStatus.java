package h_utils.dao;

import h_utils.pool.connection.HBaseConnectionStatic;
import h_utils.utils.Log;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

/**
 * 用与查看表的状态，例如：
 * 表是否存在
 * 列族是否存在
 */
public class TableStatus {

    private static Admin admin;

    static {
        admin = HBaseConnectionStatic.get_NOTCLOSABLE_Connection().getAdmin();
    }

    /**
     * 判断HBase表tableName是否存在
     *
     * @param tableName
     * @return
     */
    public static boolean ifTableExist(String tableName) {
        boolean exist = false;
        try {
            Log.say2("TableStatus.Table->" + tableName + " Exists:", "Checking");
            exist = admin.tableExists(TableName.valueOf(tableName));
            if(exist)Log.say2("TableStatus.Table->" + tableName + " Exists:", exist);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exist;
    }

    /**
     * 判断是否在table中存在某个family
     *
     * @param table
     * @param familyName
     * @return
     */
    public static boolean ifColumnExist(Table table, String familyName) {
        boolean exist = false;
        try {
            exist = table.getDescriptor().hasColumnFamily(Log.s2b(familyName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exist;
    }

    /**
     * 关闭表
     *
     * @param table
     */
    public static void closeTable(Table table) {
        try {
            table.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
