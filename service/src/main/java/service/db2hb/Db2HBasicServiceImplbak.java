package service.db2hb;

import h_utils.dao.*;
import h_utils.pool.connection.HBaseConnection;
import h_utils.pool.connection.HBaseConnectionStatic;
import h_utils.utils.Log;
import org.apache.hadoop.hbase.client.Put;
import tohb.dao.trans.DBTransUtils;
import tohb.dao.trans.DBTransUtilsBasic;
import tohb.pool.connection.DBConnection;
import tohb.pool.connection.DBConnectionCreate;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Db2HBasicServiceImplbak implements DB2HTimelyService {

    private String tableName = "";
    private String dbName = "";
    private String who = "";
    private String year_month_day_hour_minute_second = "";
    private long seconds = 0;
    protected DBTransUtils dbUtils;
    protected TableCommonUtils hBaseCommonDao;
    private TableCommonManager manager;

    protected void init(TableCommonUtils hBaseDao, DBTransUtils dbCommonDao) {
        this.hBaseCommonDao = hBaseDao;
        this.dbUtils = dbCommonDao;
    }

    public Db2HBasicServiceImplbak() {
    }

    /**
     * 从数据库（表）到HBase表的数据发送
     *
     * @param hBaseCommonDao hBase数据库的基本操作
     * @param dbCommonDao    关系数据库的通用操作
     * @param dbName         如果针对库去读，则不必设置数据库名
     * @param tableName      关系数据库表名，如果针对库区读，则不需要设置表名
     */
    @Deprecated
    public Db2HBasicServiceImplbak(TableCommonUtils hBaseCommonDao, DBTransUtils dbCommonDao, String dbName, String tableName) {
        //4个必须参数：
        //TableCommonUtils hbaseCommonDao
        //DBCommonDaoIn dbCommonDao
        //String dbName
        //String tableName
        init(hBaseCommonDao, dbCommonDao);
        if (tableName != null) this.tableName = tableName;
        if (dbName != null) this.dbName = dbName;
    }

    /**
     * 从数据库（表）到HBase表的数据发送service
     *
     * @param hTableManager 管理HTable的manager
     * @param dbTransUtils  数据库操作utils
     * @param dbName        数据库名
     * @param tableName     表名，如为空，则对数据库进行操作
     */
    public Db2HBasicServiceImplbak(TableCommonManager hTableManager, DBTransUtils dbTransUtils, String dbName, String tableName) {
        manager = hTableManager;
        dbUtils = dbTransUtils;
        if (tableName != null) this.tableName = tableName;
        if (dbName != null) this.dbName = dbName;
    }


    @Override
    public void setWho(String who) {
        this.who = who;
    }

    /**
     * 默认service
     */
    @Override
    public void defaultService() {
        fromDB2HBase(dbName, tableName);
    }

    /**
     * 用户service
     */
    @Override
    public abstract void userService();


    /**
     * 设置tableName时，导出该表，设置tableName为null时，导出整个数据库
     *
     * @param dbName
     * @param tableName null或String.
     *                  为null时，导出数据库
     *                  为String时，导出该表
     */
    private void fromDB2HBase(String dbName, String tableName) {
        try {
            //不设置tableName，直接导出数据库
            //不設置tableName，执行dB2Hbase
            if (null == tableName || "".equals(tableName)) {
                Log.say("读取数据库" + dbName + "中的全部表");
                dB2HBase(dbName);
            } else {
                Log.say("读取数据库" + dbName + "." + tableName + "一张表");
                //设置了table Name，执行dBTable2HBase
                dBTable2HBase(dbName, tableName);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 将db数据库中所有表，导入到HBase表
     *
     * @param dbName 被导出数据库
     * @throws IOException
     * @throws SQLException
     */
    private void dB2HBase(String dbName) throws IOException, SQLException {
        //获取一个数据库中所有表
        List<String> tableNames = dbUtils.getTableSet(dbName);
        //遍历，如果HBase中不存在该columnName则以表名新建
        for (String tableName : tableNames)
//            if (!TableStatus.ifColumnExist(hBaseCommonDao.currentTable(), tableName)) {
            if (!TableStatus.ifColumnExist(manager.currentTable(), tableName)) {
                Log.say2(tableName + " as Family", "not Exist");
                TableModifier.addColumnFamily(manager.currentTable(), tableName);
            }
        //遍历，调用DBTable2HBase
        for (String tableName : tableNames) {
            dBTable2HBase(dbName, tableName);
        }


    }

    /**
     * 本方法用于将DB数据库表中的信息导入到一个特定的HBase表中，
     * 读取过程由DBCommonDao完成
     * 存入过程由HBaseDao完成
     * 废弃：因为所有的字段都放在一个key中，如果历史数据发生改变，则无法维护
     *
     * @param dbName    被导出的表所在DB数据库
     * @param tableName 被导出的数据库表
     */
    @Deprecated
    private void dBTable2HBase_allRow2Key(String dbName, String tableName) throws SQLException, IOException {
        int stop = dbUtils.getTableSize(dbName, tableName);
        int batch = 10000;
        int start = 0;
        /***
         * 分为两种情况，事实上主要是第一种情况，即不设置tableName的情况，则只是从某个库导到某个hBase表
         **/
        /**
         * 此时针对整个数据库进行数据传送（数据库-->hBase表）
         */
        //undo
        //获取操作表的数据条数，主要用于分页查询，否则一次过千万可能会挤爆
        int length = dbUtils.getColumnSet(dbName, tableName).size();
        //方式start点超过总体的数据长度stop
        while (start + batch <= stop) {

            List<Put> puts = new ArrayList<Put>();
            //分页查询
            ResultSet rs = dbUtils.scan(dbName, tableName, start, batch);
            while (rs.next()) {
                //该builder用于存储一条db表信息所有的字段
                //将其转化为String格式
                StringBuilder builder = new StringBuilder();
                for (int i = 1; i <= length; i++) {
                    builder.append(rs.getObject(i) + "_");
                }
                //Log.say(builder);
                //构建put，将信息组合到一起，构建为一个key
                Put put = new Put(Log.s2b(builder.toString().substring(0, builder.length() - 1)));
                //其他的family，qualifier和value都为空
                put.addColumn(Log.s2b(tableName), Log.s2b(tableName), Log.s2b(""));
                //合并到puts中
                puts.add(put);

            }
            long t1 = System.currentTimeMillis();
            hBaseCommonDao.insertManyByPut(hBaseCommonDao.currentTable(), puts);
            long t2 = System.currentTimeMillis();
            Log.say((t2 - t1) + "   :" + start);
            start += batch;
        }

    }


    /**
     * 本方法用于将DB数据库表中的信息导入到一个特定的HBase表中，
     * 读取过程由DBCommonDao完成
     * 存入过程由HBaseDao完成
     * 废弃：因为所有的字段都放在一个key中，如果历史数据发生改变，则无法维护
     *
     * @param dbName    被导出的表所在DB数据库
     * @param tableName 被导出的数据库表
     */
    private void dBTable2HBase(String dbName, String tableName) throws SQLException, IOException {
        int stop = dbUtils.getTableSize(dbName, tableName);
        int batch = 10000;
        int start = 0;
        /***
         * 分为两种情况，事实上主要是第一种情况，即不设置tableName的情况，则只是从某个库导到某个hBase表
         **/
        /**
         * 此时针对整个数据库进行数据传送（数据库-->hBase表）
         */
        //undo
        //获取操作表的数据条数，主要用于分页查询，否则一次过千万可能会挤爆
        int length = dbUtils.getColumnSet(dbName, tableName).size();
        //防止start点超过总体的数据长度stop
        while (start + batch <= stop) {

            List<Put> puts = new ArrayList<Put>();
            //分页查询
            ResultSet rs = dbUtils.scan(dbName, tableName, start, batch);
            List<String> columnNames = dbUtils.getColumnList(dbName, tableName);
            String primaryKey = dbUtils.getPrimaryName(dbName, tableName);
            while (rs.next()) {
                //该builder用于存储一条db表信息所有的字段
                //将其转化为String格式--
                StringBuilder builder = new StringBuilder();
                //首先获取主键
                String hBaseRowKey = rs.getObject(primaryKey).toString();//dbCommonDao.getPrimaryName(dbName,tableName);
                //Log.say(builder);
                //其他的family，qualifier和value都为空
                for (int i = 0; i < length; i++) {
                    //构建put，将信息组合到一起，构建为一个key
                    Put put = new Put(Log.s2b(hBaseRowKey));
                    //当前的column非主键
                    if (!(columnNames.get(i).equals(primaryKey)))
                    //三个参数分别为family/qualifier/value
                    //对应的三个值分别为:tableName/column/value
                    {
                        put.addColumn(Log.s2b(tableName), Log.s2b(columnNames.get(i)), Log.s2b(rs.getObject(columnNames.get(i)).toString()));
                        //合并到puts中
                        puts.add(put);
                    }

                }//for

            }//while(rs.next())
            long t1 = System.currentTimeMillis();
//            hBaseCommonDao.insertManyByPut(hBaseCommonDao.currentTable(), puts);
            manager.insertManyByPut(puts);//mark
            long t2 = System.currentTimeMillis();
            Log.say2("HBase Saving Table", dbName + "." + tableName);
            Log.say2("HBase Saving Batch", batch);
            Log.say2("HBase Saving Time", (t2 - t1) + "ms");
            Log.line();
//            Log.say2( "   :" + start);
            start += batch;
        }//while (start + batch <= stop)

    }


    /**
     * 本方法用于将DB数据库表中的信息导入到一个特定的HBase表中，
     * 读取过程由DBCommonDao完成
     * 存入过程由HBaseDao完成
     * 废弃：因为所有的字段都放在一个key中，如果历史数据发生改变，则无法维护
     */
    protected void dBTable2HBase(DBTransUtils dbTransUtils, String fromDb, String fromDbTable, TableCommonManager toWhichHBaseTable) throws SQLException, IOException {
        int stop = dbTransUtils.getTableSize(fromDb, fromDbTable);
        int batch = 10000;
        int start = 0;
        /***
         * 分为两种情况，事实上主要是第一种情况，即不设置tableName的情况，则只是从某个库导到某个hBase表
         **/
        /**
         * 此时针对整个数据库进行数据传送（数据库-->hBase表）
         */
        //undo
        //获取操作表的数据条数，主要用于分页查询，否则一次过千万可能会挤爆
        int length = dbTransUtils.getColumnSet(fromDb, fromDbTable).size();
        //防止start点超过总体的数据长度stop
        while (start + batch <= stop) {

            List<Put> puts = new ArrayList<Put>();
            //分页查询
            ResultSet rs = dbTransUtils.scan(fromDb, fromDbTable, start, batch);
            List<String> columnNames = dbTransUtils.getColumnList(fromDb, fromDbTable);
            String primaryKey = dbTransUtils.getPrimaryName(fromDb, fromDbTable);
            while (rs.next()) {
                //该builder用于存储一条db表信息所有的字段
                //将其转化为String格式--
                StringBuilder builder = new StringBuilder();
                //首先获取主键
                String hBaseRowKey = rs.getObject(primaryKey).toString();//dbCommonDao.getPrimaryName(dbName,tableName);
                //Log.say(builder);
                //其他的family，qualifier和value都为空
                for (int i = 0; i < length; i++) {
                    //构建put，将信息组合到一起，构建为一个key
                    Put put = new Put(Log.s2b(hBaseRowKey));
                    //当前的column非主键
                    if (!(columnNames.get(i).equals(primaryKey)))
                    //三个参数分别为family/qualifier/value
                    //对应的三个值分别为:tableName/column/value
                    {
                        put.addColumn(Log.s2b(fromDbTable), Log.s2b(columnNames.get(i)), Log.s2b(rs.getObject(columnNames.get(i)).toString()));
                        //合并到puts中
                        puts.add(put);
                    }

                }//for

            }//while(rs.next())
            long t1 = System.currentTimeMillis();
//            hBaseCommonDao.insertManyByPut(hBaseCommonDao.currentTable(), puts);
            toWhichHBaseTable.insertManyByPut(puts);//mark
            long t2 = System.currentTimeMillis();
            Log.say2("HBase Saving Table", fromDb + "." + fromDbTable);
            Log.say2("HBase Saving Batch", batch);
            Log.say2("HBase Saving Time", (t2 - t1) + "ms");
            Log.line();
//            Log.say2( "   :" + start);
            start += batch;
        }//while (start + batch <= stop)

    }


    @Override
    public void condition() {

    }

    @Override
    public void setTime(String year_month_day_hour_minute_second) {
        this.year_month_day_hour_minute_second = year_month_day_hour_minute_second;
    }

    @Override
    public void setFrequency(long seconds) {
        this.seconds = seconds;
    }


    public static void main(String[] args) {
    }


    protected static DB2HTimelyService getService() throws IOException {
        //HBaseConnection
        HBaseConnection hConnection = HBaseConnectionStatic.get_NOTCLOSABLE_Connection();
        //HBaseUtils
        TableCommonUtils utils = new TableCommonUtilsBasic(hConnection, "hcdata9");
        TableCommonManager manager = new TableCommonManager(hConnection, "hcdata19");
//      utils.scanTable(utils.currentTable(), StaticConfiguration.DEFAULT_SCAN);
        //DatabaseDao
//      DBConnection dbConnection = new MysqlConnection();
        DBConnection dbConnection = DBConnectionCreate.create("mysql");
        DBTransUtils dbCommonDao = new DBTransUtilsBasic(dbConnection);
        //Service
        DB2HTimelyService service = new Db2HBasicServiceImplbak(manager, dbCommonDao, "casia", null) {
            @Override
            public void userService() {

            }
        };
        return service;
    }


}
