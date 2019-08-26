package h_utils.dao;

import h_utils.config.StaticConfiguration;
import h_utils.pool.connection.HBaseConnection;
import h_utils.utils.Log;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TableCommonManager {
    /**
     * 用于对表进行操作的Admin内置对象
     */
    private Admin admin;

    /**
     * 用于HBase表连接的封装对象
     */
    private HBaseConnection hConnection;

    /**
     * Manager进行管理的Table
     */
    private Table table;

    /**
     * HBaseTable操作集合
     */
    private TableCommonUtils utils;//

    /**
     * 内置HBaseTable操作集dao，对Table进行管理
     * 其中dao所需的Connection及Admin由HConnection生成
     * tableName为要进行管理的表的名字
     *
     * @param hConnection
     * @param tableName
     */
    public TableCommonManager(HBaseConnection hConnection, String tableName) {

        //内置hConnection
        this.hConnection = hConnection;
        Log.say2("TableCommonManager.[HBaseConnection]", "set OK");
        //内置Admin
        admin = hConnection.getAdmin();
        Log.say2("TableCommonManager.[Admin]", "set OK");
        //内置Table
        setTable(tableName);
        Log.say2("TableCommonManager.[Table]", "set OK");
        //内置HBaseTable操作集
        utils = new TableCommonUtilsBasic();
        Log.say2("TableCommonManager.[TableCommonUtils]", "set OK");
    }

    /**
     * 设置内置Table，如不存在，创建
     * 如创建Table，则内置默认的data字段为familyName
     *
     * @param tableName
     */
    public void setTable(String tableName) {
        Log.say2("TableCommonUtilsBasic.[Table]->" + tableName, "setting");
        if (!TableStatus.ifTableExist(tableName)) {
            Log.say2("TableCommonUtilsBasic.[Table]->" + tableName, "不存在，创建");
            TableModifier.createTable(tableName, StaticConfiguration.static_list);
        }
        Log.say2("TableCommonUtilsBasic.[Table]->" + tableName, "OK");
        table = hConnection.getTable((tableName));
    }

    /**
     * 返回当前manager管理的Table
     *
     * @return
     */
    public Table currentTable() {
        return table;
    }

    /**
     * 插入一条数据，指定rowKey，family，qualifier，和value
     *
     * @param rowKey    HTable主键
     * @param family    FamilyName
     * @param qualifier Family:qualifier
     * @param value     Family:qualifier:value
     * @throws IOException
     */
    public void insertByList(String rowKey, String family, String qualifier, String value) throws IOException {
        utils.insertByList(table, rowKey, family, qualifier, value);
    }

    /**
     * 插入一条数据，使用put插入，put需要提前构建
     *
     * @param put
     * @throws IOException
     */
    public void insertByPut(Put put) throws IOException {
        utils.insertByPut(table, put);
    }

    /**
     * 插入多条数据
     *
     * @param list
     * @throws IOException
     */
    public void insertManyByList(List<Map<String, String>> list) throws IOException {
        utils.insertManyByList(table, list);
    }

    /**
     * 插入多条数据
     *
     * @param puts 为List<Put>
     * @throws IOException
     */
    public void insertManyByPut(List<Put> puts) throws IOException {
        utils.insertManyByPut(table, puts);
    }
}
