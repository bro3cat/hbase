package h_utils.dao;

import h_utils.config.StaticConfiguration;
import h_utils.pool.connection.HBaseConnection;
import h_utils.utils.Log;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableCommonUtilsBasic implements TableCommonUtils {

    /**
     * 不可实例化
     */
    private TableCommonUtilsBasic() {
    }

    /**
     * 用于对表进行操作的Admin内置对象
     */
    private Admin admin;

    /**
     * 用于HBase表连接的封装对象
     */
    private HBaseConnection hConnection;

    private Table table;


    /**
     * HBase数据表基基本操作
     *
     * @param hConnection HConnection对象
     * @param tableName   要操作的表
     */
    public TableCommonUtilsBasic(HBaseConnection hConnection, String tableName) {
        this.hConnection = hConnection;
        Log.say2("TableCommonUtilsBasic.[hConnection]", "set OK");
        admin = hConnection.getAdmin();
        setTable(tableName);
    }

    @Override
    public void setTable(String tableName) {
        Log.say2("TableCommonUtilsBasic.[Table]->" + tableName, "setting");
        if (!TableStatus.ifTableExist(tableName)) {
            Log.say2("TableCommonUtilsBasic.[Table]->" + tableName, "不存在，创建");
            TableModifier.createTable(tableName, StaticConfiguration.static_list);
        }
        Log.say2("TableCommonUtilsBasic.[Table]->" + tableName, "OK");
        table = hConnection.getTable((tableName));
    }

    @Override
    public Table currentTable() {
        return table;
    }

    @Override
    public void scanTable(Table table, Scan scan) throws IOException {
        ResultScanner results = table.getScanner(scan);
        for (Result result : results) {
            String rowKey = Bytes.toString(result.getRow());
            Log.say2(table.getName() + ".rowKey", rowKey);

            Cell[] cells = result.rawCells();
            for (Cell cell : cells) {
                Log.say2(Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength()) + "->" + Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
                        cell.getQualifierLength()), Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
                        cell.getValueLength()));
            }
            System.out.println("-----------------------------------------");
        }
    }

    @Override
    public Cell getResult(Table table, String rowKey) throws IOException {
        Get get = new Get(Log.s2b(rowKey));
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
        Put put = new Put(Log.s2b(rowKey));
        put.addColumn(Log.s2b(family), Log.s2b(qualifier), Log.s2b(value));
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
                Put put = new Put(Log.s2b(map.get("rowKey")));
                put.addColumn(Log.s2b(map.get("family")), Log.s2b(map.get("qualifier")), Log.s2b(map.get("value")));
                puts.add(put);
            }
        }
        table.put(puts);
    }

    @Override
    public void insertManyByPut(Table table, List<Put> puts) throws IOException {
        table.put(puts);
    }
}
