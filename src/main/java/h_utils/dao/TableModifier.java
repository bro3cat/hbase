package h_utils.dao;

import h_utils.pool.connection.HBaseConnectionStatic;
import h_utils.utils.Log;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 修改HBase表的接口，包含：
 * 创建表
 * 删除表
 * 增加列族
 */
public class TableModifier {

    private static Admin admin = HBaseConnectionStatic.get_NOTCLOSABLE_Connection().getAdmin();

    /**
     * 增加一个列
     *
     * @param family
     * @throws IOException
     */
    public static void addColumnFamily(Table table, String family) {
        if (TableStatus.ifTableExist(table.getName().toString()))
            if (!TableStatus.ifColumnExist(table, family)) {
                ColumnFamilyDescriptor fa = ColumnFamilyDescriptorBuilder.newBuilder(Log.s2b(family)).build();
                try {
                    admin.addColumnFamily(table.getName(), fa);
                    Log.say2("---Added Family:", family);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    /**
     * 创建HBase表
     *
     * @param tableName
     * @param familyNames
     */
    public static void createTable(String tableName, Collection<String> familyNames) {
        List<ColumnFamilyDescriptor> descriptorList = new ArrayList<ColumnFamilyDescriptor>();
        for (String familyName : familyNames) {
            ColumnFamilyDescriptor temp = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(familyName)).build();
            descriptorList.add(temp);
        }
        TableDescriptor descriptor = TableDescriptorBuilder.
                newBuilder(TableName.valueOf(tableName)).
                setColumnFamilies(descriptorList).build();  //getDescriptor(TableName.valueOf(tableName));
        try {
            admin.createTable(descriptor);
            Log.say2("---Created Table:", tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除一个表
     *
     * @param tableName
     */
    public static void dropTable(String tableName) {
        try {
            Log.say2("---Disable Table:", tableName);
            admin.disableTable(TableName.valueOf(tableName));
            Log.say2("---Delete Table:", tableName);
            admin.deleteTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
