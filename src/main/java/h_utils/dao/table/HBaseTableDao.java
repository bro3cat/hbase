package h_utils.dao.table;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import h_utils.pool.conf.HBaseConfigNaive;
import h_utils.pool.connection.HBaseConnectionBase;
import h_utils.pool.connection.HConnection;
import h_utils.utils.Log;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static h_utils.config.StaticConfiguration.hmaster;

public abstract class HBaseTableDao implements HBaseTableDaoIn {


    protected TableName _tableName;
    /**
     * admin用来管理表操作，用connection.getAdmin()来实现，这里用子类来实现，由不同的connection和configuration进行实现
     */
    protected Admin admin;
    protected Table table;
    protected HConnection connection;

    private HBaseTableDao() {
    }

    public HBaseTableDao(String tableName) {
        connection = new HBaseConnectionBase(new HBaseConfigNaive(hmaster));
        admin = connection.getAdmin();
        _tableName = TableName.valueOf(tableName);
        Log.say("---" + tableName + " is set");
        Log.say("---Already config HMaster: " + hmaster + admin);
        try {
            System.out.print(tableName);
            System.out.print(this._tableName);
            this.table = admin.getConnection().getTable(this._tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void setAdmin();

    @Override
    public abstract void scanTable(Scan scan) throws IOException;

    @Override
    public abstract Cell getResult(String rowKey) throws IOException;

    @Override
    public abstract void insertByList(String rowKey, String family, String qualifier, String value) throws IOException;

    @Override
    public abstract void insertManyByList(List<Map<String, String>> list) throws IOException;

    @Override
    public abstract void insertByPut(Put put) throws IOException;


    @Override
    public abstract void insertManyByPut(List<Put> puts) throws IOException;


    @Override
    public void addColumnFamily(String family) throws IOException {
        TableDescriptor descriptor = table.getDescriptor();
        ColumnFamilyDescriptor fa = ColumnFamilyDescriptorBuilder.newBuilder(s2b(family)).build();
        admin.addColumnFamily(_tableName, fa);
    }

    ;


    @Override
    public void closeTable(Table table) throws IOException {
        table.close();
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
