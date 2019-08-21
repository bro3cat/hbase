package tohb.service;

import h_utils.config.StaticConfiguration;
import h_utils.dao.hbase.HBaseCommonDao;
import h_utils.dao.hbase.HBaseCommonDaoIn;
import h_utils.pool.conf.HBaseConfigNaive;
import h_utils.pool.conf.HConfiguration;
import h_utils.pool.connection.HBaseConnectionBase;
import h_utils.pool.connection.HConnection;
import h_utils.utils.Log;
import org.apache.hadoop.hbase.client.Put;
import tohb.dao.DBCommonDao;
import tohb.dao.DBCommonDaoIn;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Db2HBaseTransService implements BaseService {

    private String tableName = "";
    private String dbName = "";
    private String who = "";
    private String year_month_day_hour_minute_second = "";
    private long seconds = 0;
    protected DBCommonDaoIn dbCommonDao;
    protected HBaseCommonDaoIn hBaseCommonDao;

    protected void init(HBaseCommonDaoIn hBaseDao, DBCommonDaoIn dbCommonDao) {
        this.hBaseCommonDao = hBaseDao;
        this.dbCommonDao = dbCommonDao;
    }

    /**
     * 从数据库（表）到HBase表的数据发送
     *
     * @param hBaseCommonDao hBase数据库的基本操作
     * @param dbCommonDao    关系数据库的通用操作
     * @param tableName      关系数据库表名，如果针对库区读，则不需要设置表名
     * @param dbName         如果针对库去读，则不必设置数据库名
     */
    public Db2HBaseTransService(HBaseCommonDaoIn hBaseCommonDao, DBCommonDaoIn dbCommonDao, String dbName, String tableName) {
        init(hBaseCommonDao, dbCommonDao);
        if (tableName != null) this.tableName = tableName;
        if (dbName != null) this.dbName = dbName;
    }

    @Override
    public void setWho(String who) {
        this.who = who;
    }


    @Override
    public void run() {
        fromDB2HBase(dbName, tableName);
    }


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
            if (null == tableName || "".equals(tableName)) {
                Log.say("读取数据库" + dbName + "中的全部表");
                dB2HBase(dbName);
            } else {
                Log.say("读取数据库" + dbName + "." + tableName + "一张表");
                dBTable2HBase_allRow2Key(dbName, tableName);
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
        List<String> tableNames = dbCommonDao.getTableSet(dbName);
        //遍历，如果HBase中不存在该columnName则以表名新建
        for (String tableName : tableNames)
            if (!hBaseCommonDao.ifExistColumnFamily(hBaseCommonDao.currentTable(), tableName)) {
                Log.say("not Exist:" + tableName);
                hBaseCommonDao.addColumnFamily(hBaseCommonDao.currentTable().getName().toString(), tableName);
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
        int stop = dbCommonDao.getTableSize(dbName, tableName);
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
        int length = dbCommonDao.getColumnSet(dbName, tableName).size();
        //方式start点超过总体的数据长度stop
        while (start + batch <= stop) {

            List<Put> puts = new ArrayList<Put>();
            //分页查询
            ResultSet rs = dbCommonDao.scan(dbName, tableName, start, batch);
            while (rs.next()) {
                //该builder用于存储一条db表信息所有的字段
                //将其转化为String格式
                StringBuilder builder = new StringBuilder();
                for (int i = 1; i <= length; i++) {
                    builder.append(rs.getObject(i) + "_");
                }
                Log.say(builder);
                //构建put，将信息组合到一起，构建为一个key
                Put put = new Put(Log.s2b(builder.toString().substring(0, builder.length() - 1)));
                //其他的family，qualifier和value都为空
//                hBaseCommonDao.
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
        int stop = dbCommonDao.getTableSize(dbName, tableName);
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
        int length = dbCommonDao.getColumnSet(dbName, tableName).size();
        //防止start点超过总体的数据长度stop
        while (start + batch <= stop) {

            List<Put> puts = new ArrayList<Put>();
            //分页查询
            ResultSet rs = dbCommonDao.scan(dbName, tableName, start, batch);
            List<String> columnNames = dbCommonDao.getColumnList(dbName, tableName);
            String primaryKey = dbCommonDao.getPrimaryName(dbName, tableName);
            while (rs.next()) {
                //该builder用于存储一条db表信息所有的字段
                //将其转化为String格式--
                StringBuilder builder = new StringBuilder();
                //首先获取主键
                String hBaseRowKey = rs.getObject(primaryKey).toString();//dbCommonDao.getPrimaryName(dbName,tableName);
                Log.say(builder);
                //其他的family，qualifier和value都为空
//                hBaseCommonDao.
                for (int i = 0; i < length; i++) {
                    //构建put，将信息组合到一起，构建为一个key
                    Put put = new Put(Log.s2b(hBaseRowKey));
                    //当前的column非主键
                    if (!(columnNames.get(i).equals(primaryKey)))
                    //三个参数分别为family/qualifier/value
                    //对应的三个值分别为:tableName/column/value
                    {
                        put.addColumn(Log.s2b(tableName), Log.s2b(columnNames.get(i)), Log.s2b(rs.getObject(columnNames.get(i)).toString()));
//                        Log.say(tableName + ":" + columnNames.get(i) + ":" + rs.getObject(columnNames.get(i)).toString());
                        //合并到puts中
                        puts.add(put);
                    }

                }//for

            }//while(rs.next())
            long t1 = System.currentTimeMillis();
            hBaseCommonDao.insertManyByPut(hBaseCommonDao.currentTable(), puts);
            long t2 = System.currentTimeMillis();
            Log.say((t2 - t1) + "   :" + start);
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
        HConfiguration configuration = new HBaseConfigNaive(StaticConfiguration.hmaster);
        HConnection connection = new HBaseConnectionBase(configuration);
        HBaseCommonDaoIn hBaseCommonDao = new HBaseCommonDao(connection, "hcdata3");

        DBCommonDaoIn dbCommonDao = new DBCommonDao();

        BaseService service = new Db2HBaseTransService(hBaseCommonDao, dbCommonDao, "casia", null) {
        };
        service.run();
    }

}
//
//
// /**
// * 本方法用于将DB数据库/表中的信息导入到一个特定的HBase表中，
// * 读取过程由DBCommonDao完成
// * 存入过程由HBaseDao完成
// *
// * @param dbName    被导出的DB数据库
// * @param tableName 被导出的数据库表，当表为空字符串，表示从dbName库中导出
// */
//private void fromDbTable2HBase(String hTableName, String dbName, String tableName) {
//
//    try {
//        int stop = dbCommonDao.getTableSize(dbName, tableName);
//        int batch = 10000;
//        int start = 0;
//        /***
//         * 分为两种情况，事实上主要是第一种情况，即不设置tableName的情况，则只是从某个库导到某个hBase表
//         **/
//        if ("".equals(tableName)) {
//            /**
//             * 此时针对整个数据库进行数据传送（数据库-->hBase表）
//             */
//            //undo
//        } else {//针对单个表进行操作
//            //获取操作表的数据条数，主要用于分页查询，否则一次过千万可能会挤爆
//            int length = dbCommonDao.getColumnSet(dbName, tableName).size();
//            //方式start点超过总体的数据长度stop
//            while (start + batch <= stop) {
//
//                List<Put> puts = new ArrayList<Put>();
//                //分页查询
//                ResultSet rs = dbCommonDao.scan(tableName, start, batch);
//                while (rs.next()) {
//                    //该builder用于存储一条db表信息所有的字段
//                    //将其转化为String格式
//                    StringBuilder builder = new StringBuilder();
//                    for (int i = 1; i <= length; i++) {
//                        builder.append(rs.getObject(i) + "_");
//                    }
//                    //构建put，将信息组合到一起，构建为一个key
//                    Put put = new Put(Log.s2b(builder.toString().substring(0, builder.length() - 1)));
//                    //其他的family，qualifier和value都为空
//                    put.addColumn(Log.s2b("test1"), Log.s2b("data"), Log.s2b(""));
//                    //合并到puts中
//                    puts.add(put);
//
//                }
//                long t1 = System.currentTimeMillis();
//                hBaseDao.insertManyByPut(hBaseDao.currentTable(), puts);
//                long t2 = System.currentTimeMillis();
//                Log.say((t2 - t1) + "   :" + start);
//                start += batch;
//            }
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//
//
//}