package dao.table;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Scan;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface HBaseTableDaoIn {

    public void scanTable(Scan scan) throws IOException;

    public Cell getResult(String rowKey) throws IOException;

    public void insert(String rowKey, String family, String qualifier, String value) throws IOException;

    public void insertMany(List<Map<String, String>> list) throws IOException;

//    public void exportTable(String tableName, String )
}
