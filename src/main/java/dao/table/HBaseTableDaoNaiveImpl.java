package dao.table;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HBaseTableDaoNaiveImpl extends HBaseTableDao {
    public HBaseTableDaoNaiveImpl(String tableName) {
        super(tableName);
    }

    @Override
    public void scanTable(Scan scan) throws IOException {
        Table table = this.table;
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
    public void insert(String rowKey, String family, String qualifier, String value) throws IOException {
        Put put = new Put(s2b(rowKey));
        put.addColumn(s2b(family), s2b(qualifier), s2b(value));
        table.put(put);
    }

    @Override
    public void insertMany(List<Map<String, String>> list) throws IOException {
        List<Put> puts = new ArrayList<Put>();
        if (null != list) {
            for (Map<String, String> map : list) {
                Put put = new Put(s2b(map.get("rowKey")));
                put.addColumn(s2b(map.get("family")), s2b(map.get("qualifier")), s2b(map.get("value")));
                puts.add(put);
            }
        }
        table.put(puts);
        table.close();
    }

    /**
     * 字符串转为byte[]类型，主要用于行列的存储等
     *
     * @param str
     * @return
     */
    private static byte[] s2b(String str) {
        return Bytes.toBytes(str);
    }

    private static String b2s(byte[] bytes) {
        return Bytes.toString(bytes);
    }
}
