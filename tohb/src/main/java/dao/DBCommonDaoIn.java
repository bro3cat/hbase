package dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DBCommonDaoIn {

    /**
     *
     * @param table 所在的catalog/schema/tableName和基本表结构
     */
    //public void createTable(DBTable table);

    /**
     * 删除表
     * @param tableName tableName
     */
    //public void delTable(String tableName);

    /**
     * 按照batch大小决定每次读取的条数
     *
     * @param tableName
     * @param batch
     * @return
     */
    public ResultSet scan(String tableName, int batch) throws SQLException;


    /**
     * 获取数据库下所有的表名
     *
     * @param dbName [schema.]database
     * @return List{name1, name2, ***}
     */
    public List<String> getTableSet(String dbName);

    /**
     * 返回数据库下表的列名
     *
     * @param dbName    [schema.]database
     * @param tableName
     * @return 以column, columnType为对的Map
     */
    public Map<String, String> getColumnSet(String dbName, String tableName);


}
