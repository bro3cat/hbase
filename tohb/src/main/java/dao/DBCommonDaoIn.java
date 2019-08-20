package dao;

import java.sql.ResultSet;
import java.util.List;

/**
 * 数据库通用接口，因为目前只包含从数据库到HBase数据库的数据转移，所以事实上因该以读数据为主，insert和insertMany主要是为了测试用
 */
public interface DBCommonDaoIn {

    /**
     * 想数据库中插入一条数据
     *
     * @param list
     */
    public void insert(List<String> list);

    /**
     * 插入多条数据
     *
     * @param list
     */
    public void insertMany(List<List<String>> list,int batchSize);

    /**
     * 读取一条数据
     *
     * @param key
     * @return
     */
    public ResultSet read(String key);

    /**
     * 读取全部数据
     *
     * @return
     */
    public ResultSet readTable();

//    public ResultSet readTable(List<String> columns, List<String> criteria);
    public long test(List<List<String>> list,int batchSize);
}
