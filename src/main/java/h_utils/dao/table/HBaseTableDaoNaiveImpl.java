package h_utils.dao.table;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import h_utils.pool.conf.HBaseConfigNaive;
import h_utils.pool.connection.HBaseConnectionBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static h_utils.config.StaticConfiguration.hmaster;

public class HBaseTableDaoNaiveImpl extends HBaseTableDao {
    public HBaseTableDaoNaiveImpl(String tableName) {
        super(tableName);
    }

    @Override
    public void setAdmin() {
        admin = new HBaseConnectionBase(new HBaseConfigNaive(hmaster)).getAdmin();
    }

    @Override
    public void scanTable(Scan scan) throws IOException {
        Table table = this.table;
        ResultScanner scanner = table.getScanner(scan);
        for (Result rs : scanner) {
            String rowKey = Bytes.toString(rs.getRow());
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

    @Override
    public Cell getResult(String rowKey) throws IOException {
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
//            return cell;
        }
        return null;
    }

    @Override
    public void insertByList(String rowKey, String family, String qualifier, String value) throws IOException {
        Put put = new Put(s2b(rowKey));
        put.addColumn(s2b(family), s2b(qualifier), s2b(value));
        table.put(put);
    }

    @Override
    public void insertByPut(Put put) throws IOException {
        table.put(put);
    }

    @Override
    public void insertManyByList(List<Map<String, String>> list) throws IOException {
        List<Put> puts = new ArrayList<Put>();
//        int i = 0;
        for (Map<String, String> map : list) {
//            if (i++ % 500000 == 0) {
//                Log.say("putting*********************");
//                table.put(puts);
//                puts.clear();
//            }
            Put put = new Put(s2b(map.get("rowKey")));
            put.addColumn(s2b(map.get("family")), s2b(map.get("qualifier")), s2b(map.get("value")));
            puts.add(put);

        }
//        Log.say("putsize-\t" + puts.size());
        table.put(puts);
//        table.close();
//        table.
    }

    @Override
    public void insertManyByPut(List<Put> puts) throws IOException {
        table.put(puts);
    }

    @Override
    public void addColumnFamily(String family) {
        try {
            ColumnFamilyDescriptor fa = ColumnFamilyDescriptorBuilder.newBuilder(s2b(family)).build();
            admin.addColumnFamily(_tableName, fa);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
