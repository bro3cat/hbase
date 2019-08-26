package service.db2hb;

import h_utils.dao.TableCommonManager;
import h_utils.dao.TableModifier;
import h_utils.dao.TableStatus;
import h_utils.pool.connection.HBaseConnection;
import h_utils.pool.connection.HBaseConnectionStatic;
import h_utils.utils.Log;
import org.apache.hadoop.hbase.client.Put;
import tohb.dao.trans.DBTransUtils;
import tohb.dao.trans.DBTransUtilsBasic;
import tohb.pool.connection.DBConnectionCreate;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestService extends Db2HTimelyServiceImpl implements DB2HTransService {

    public TestService() {
    }

    @Override
    public void userService() {
        defaultService();
    }


    public static void main(String[] args) {
        DBTransUtils dbTransUtils = new DBTransUtilsBasic(DBConnectionCreate.create("mysql"));
        String fromDb = "casia";
        HBaseConnection hBaseConnection = HBaseConnectionStatic.get_NOTCLOSABLE_Connection();
        TableCommonManager toWhichHBaseTable = new TableCommonManager(hBaseConnection, "lisan");


        DB2HTransService service = new TestService();
        service.trans(dbTransUtils, fromDb, null, toWhichHBaseTable);
    }



    /**
     * @param dbTransUtils
     * @param fromDb
     * @param fromDbTable
     * @param toWhichHBaseTable
     */
    public void trans(DBTransUtils dbTransUtils, String fromDb, String fromDbTable, TableCommonManager toWhichHBaseTable) {
        try {
            //不设置tableName，直接导出数据库
            if (null == fromDbTable || "".equals(fromDbTable)) {
                Log.say("读取数据库" + fromDb + "中的全部表");
                dB2HBase(dbTransUtils, fromDb, toWhichHBaseTable);
            } else {
                Log.say("读取数据库" + fromDb + "." + fromDbTable + "一张表");
                //设置了table Name，执行dBTable2HBase
                dBTable2HBase(dbTransUtils, fromDb, fromDbTable, toWhichHBaseTable);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 将db数据库中所有表，导入到HBase表
     *
     * @param fromDb 被导出数据库
     * @throws IOException
     * @throws SQLException
     */
    private void dB2HBase(DBTransUtils dbTransUtils, String fromDb, TableCommonManager toWhichHBaseTable) throws IOException, SQLException {
        //获取一个数据库中所有表
        List<String> tableNames = dbTransUtils.getTableSet(fromDb);
        //遍历，如果HBase中不存在该columnName则以表名新建
        for (String tableName : tableNames)
            if (!TableStatus.ifColumnExist(toWhichHBaseTable.currentTable(), tableName)) {
                Log.say2(tableName + " as Family", "not Exist");
                TableModifier.addColumnFamily(toWhichHBaseTable.currentTable(), tableName);
            }
        //遍历，调用DBTable2HBase
        for (String tableName : tableNames) {
            dBTable2HBase(dbTransUtils, fromDb, tableName, toWhichHBaseTable);
        }


    }


    /**
     * 本方法用于将DB数据库表中的信息导入到一个特定的HBase表中，
     * 读取过程由DBCommonDao完成
     * 存入过程由HBaseDao完成
     * 废弃：因为所有的字段都放在一个key中，如果历史数据发生改变，则无法维护
     */
    private void dBTable2HBase(DBTransUtils dbTransUtils, String fromDb, String fromDbTable, TableCommonManager toWhichHBaseTable) throws SQLException, IOException {
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
            toWhichHBaseTable.insertManyByPut(puts);//mark
            long t2 = System.currentTimeMillis();
            Log.say2("HBase Saving Table", fromDb + "." + fromDbTable);
            Log.say2("HBase Saving Batch", batch);
            Log.say2("HBase Saving Time", (t2 - t1) + "ms");
            Log.line();
            start += batch;
        }//while (start + batch <= stop)

    }

}
