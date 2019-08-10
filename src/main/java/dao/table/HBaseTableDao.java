package dao.table;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import pool.admin.HBaseAdmin;
import pool.conf.HBaseConfigNaive;
import pool.connection.HBaseConnectionBase;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static common.StaticConfiguration.hmaster;

public abstract class HBaseTableDao implements HBaseTableDaoIn {


    protected TableName tableName;
    protected HBaseAdmin hBaseAdmin;
    protected Table table;

    private HBaseTableDao() {
    }

    public HBaseTableDao(String tableName) {
        this.tableName = TableName.valueOf(tableName);
        System.out.println("---" + tableName + " is set");
        this.hBaseAdmin = new HBaseAdmin(new HBaseConnectionBase(new HBaseConfigNaive(hmaster)));
        System.out.println("---Already config HMaster: " + hmaster+hBaseAdmin.getAdmin());
        try {
            System.out.print(tableName);
            System.out.print(this.tableName);
            this.table = hBaseAdmin.getConnection().getTable(this.tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public abstract void scanTable(Scan scan) throws IOException;

    @Override
    public abstract Cell getResult(String rowKey) throws IOException;

    @Override
    public abstract void insert(String rowKey, String family, String qualifier, String value) throws IOException;

    @Override
    public abstract void insertMany(List<Map<String, String>> list) throws IOException;
}
