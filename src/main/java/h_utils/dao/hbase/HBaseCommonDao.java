package h_utils.dao.hbase;

import h_utils.utils.Log;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import h_utils.pool.connection.HConnection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HBaseCommonDao implements HBaseCommonDaoIn {

    /**
     * 不可实例化
     */
    private HBaseCommonDao() {
    }

    /**
     * 用于对表进行操作的Admin内置对象
     */
    private Admin admin;

    /**
     * 用于HBase表连接的封装对象
     */
    private HConnection connection;

    private Table table;

    /**
     * 用来更换正在操作的表
     * 用来设置要进行操作的表
     *
     * @param tableName
     */
    @Override
    public void setTable(String tableName) {
        if (!isExistTable(tableName)) {
            Log.say(tableName + "不存在，创建");
            createTable(tableName);
        }
        table = connection.getTable((tableName));
    }

    @Override
    public Table currentTable() {
        return table;
    }

    public HBaseCommonDao(HConnection hConnection, String tableName) {
        connection = hConnection;
        admin = hConnection.getAdmin();
        setTable(tableName);
    }

    @Override
    public void scanTable(Table table, Scan scan) throws IOException {
        ResultScanner scanner = table.getScanner(scan);
        for (Result rs : scanner) {
            String rowKey = Bytes.toString(rs.getRow());
            if (Integer.parseInt(rowKey.substring(12, 16)) > 2017) {
                System.out.println("row key :" + rowKey);
                Cell[] cells = rs.rawCells();
                for (Cell cell : cells) {
                    System.out.println(
                            Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength()) + "::"
                                    + Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
                                    cell.getQualifierLength())
                                    + "::" + Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
                                    cell.getValueLength()));
                }
                System.out.println("-----------------------------------------");
            }
        }
    }

    @Override
    public Cell getResult(Table table, String rowKey) throws IOException {
        Get get = new Get(s2b(rowKey));
        Result result = table.get(get);

        Cell[] cells = result.rawCells();
        int i = 0;
        for (Cell cell : cells) {
            i++;
            System.out.println(i + Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset()));
            System.out.println(i + Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength()) + "   family");
            System.out.println(i + Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength()) + "   qualifier");
            System.out.println(i + Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()) + "   value");
            return cell;
        }
        return null;
    }

    @Override
    public void insertByList(Table table, String rowKey, String family, String qualifier, String value) throws IOException {
        Put put = new Put(s2b(rowKey));
        put.addColumn(s2b(family), s2b(qualifier), s2b(value));
        table.put(put);
    }

    @Override
    public void insertByPut(Table table, Put put) throws IOException {
        table.put(put);
    }

    @Override
    public void insertManyByList(Table table, List<Map<String, String>> list) throws IOException {
        List<Put> puts = new ArrayList<Put>();
        if (null != list) {
            for (Map<String, String> map : list) {
                Put put = new Put(s2b(map.get("rowKey")));
                put.addColumn(s2b(map.get("family")), s2b(map.get("qualifier")), s2b(map.get("value")));
                puts.add(put);
            }
        }
        table.put(puts);
    }

    @Override
    public void insertManyByPut(Table table, List<Put> puts) throws IOException {
        table.put(puts);
    }

    @Override
    public void addColumnFamily(String tableName, String family) throws IOException {
        ColumnFamilyDescriptor fa = ColumnFamilyDescriptorBuilder.newBuilder(s2b(family)).build();
        admin.addColumnFamily(TableName.valueOf(tableName), fa);
    }

    @Override
    public boolean ifExistColumnFamily(Table table, String family) throws IOException {
        TableDescriptor tableDescriptor = table.getDescriptor();
        ColumnFamilyDescriptor descriptor = tableDescriptor.getColumnFamily(Bytes.toBytes(family));
        return descriptor == null ? false : true;
    }

    @Override
    public boolean isExistTable(String tableName) {
        try {
            return admin.tableExists(TableName.valueOf(tableName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void closeTable(Table table) throws IOException {
        table.close();
    }

    @Override
    public void createTable(String tableName) {
        ColumnFamilyDescriptor familyDescriptor = ColumnFamilyDescriptorBuilder.newBuilder(Log.s2b("test")).build();
        TableDescriptor descriptor = TableDescriptorBuilder.
                newBuilder(TableName.valueOf(tableName)).
                setColumnFamily(familyDescriptor).build();  //getDescriptor(TableName.valueOf(tableName));
        try {
            admin.createTable(descriptor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 字符串转为byte[]类型，主要用于行列的存储等
     *
     * @param str
     * @return
     */
    protected static byte[] s2b(String str) {
        return Bytes.toBytes(str);
    }

    protected static String b2s(byte[] bytes) {
        return Bytes.toString(bytes);
    }

}
