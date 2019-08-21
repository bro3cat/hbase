package h_utils.dao.hbase;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * HBase通用接口
 */
public interface HBaseCommonDaoIn {


    /**
     * 扫描整个表
     *
     * @param scan 构建的扫描条件
     * @throws IOException
     */
    public void scanTable(Table table, Scan scan) throws IOException;

    /**
     * 查询一条主键对应的内容
     *
     * @param rowKey 主键
     * @return 一个主键对应的Cell
     * @throws IOException
     */
    public Cell getResult(Table table, String rowKey) throws IOException;

    /**
     * 更容易组织形式的插入方式，将Put构建放入方法内部
     *
     * @param rowKey    主键
     * @param family    columnFamilyName
     * @param qualifier column
     * @param value     值
     * @throws IOException
     */
    public void insertByList(Table table, String rowKey, String family, String qualifier, String value) throws IOException;

    /**
     * 外部直接传入put，略直接的做法
     *
     * @param put Put
     * @throws IOException
     */
    public void insertByPut(Table table, Put put) throws IOException;

    /**
     * 参考insertByList，批量插入
     *
     * @param list
     * @throws IOException
     */
    public void insertManyByList(Table table, List<Map<String, String>> list) throws IOException;

    /**
     * 参考insertByPut，批量信息插入
     *
     * @param puts
     * @throws IOException
     */
    public void insertManyByPut(Table table, List<Put> puts) throws IOException;

    /**
     * 初步设想，一个数据库对应一个hBase表，一个表对应一个columnFamily<br>
     * 用于增加columnFamily
     *
     * @param family
     */
    public void addColumnFamily(TableName tableName, String family) throws IOException;

    public void closeTable(Table table) throws IOException;

}