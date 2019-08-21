//package tohb.service;
//
//import h_utils.dao.hbase.HBaseCommonDao;
//import h_utils.dao.hbase.HBaseCommonDaoIn;
//import h_utils.dao.table.HBaseTableDaoIn;
//import h_utils.dao.table.HBaseTableDaoNaiveImpl;
//import h_utils.utils.Log;
//import org.apache.hadoop.hbase.client.Put;
//import tohb.dao.DBCommonDao;
//import tohb.dao.DBCommonDaoIn;
//
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Db2HBaseTransfer extends Db2HBaseTransService {
//
//    private String tableName = "";
//
//    /**
//     * 从数据库（表）到HBase表的数据发送
//     *
//     * @param hBaseCommonDao    hBase数据库的基本操作
//     * @param dbCommonDao 关系数据库的通用操作
//     * @param tableName   关系数据库名
//     */
//    public Db2HBaseTransfer(HBaseCommonDaoIn hBaseCommonDao, DBCommonDaoIn dbCommonDao,String dbName, String tableName) {
//        super(hBaseCommonDao, dbCommonDao, dbName,  tableName);
//        if (tableName != null)
//            this.tableName = tableName;
//    }
//
//
//    @Override
//    public void run() {
//        try {
//            fromDb2HBase();
//            fromDbTable2HBase();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected void fromDbTable2HBase() throws Exception {
//
//        int stop = dbCommonDao.getTableSize("test", "test1");
//        int batch = 10000;
//        int start = 0;
//        /***
//         * 分为两种情况，事实上主要是第一种情况，即不设置tableName的情况，则只是从某个库导到某个hBase表
//         **/
//        if ("".equals(tableName)) {
//            System.out.println("针对数据库全部表");
//        } else {//针对单个表
//            int length = dbCommonDao.getColumnSet("test", "test1").size();
//            while (start + batch <= stop) {
//                List<Put> puts = new ArrayList<Put>();
//                ResultSet rs = dbCommonDao.scan(tableName, start, batch);
//                while (rs.next()) {
//                    StringBuilder builder = new StringBuilder();
//                    for (int i = 1; i <= length; i++) {
//                        builder.append(rs.getObject(i) + "_");
//                    }
//                    Put put = new Put(Log.s2b(builder.toString().substring(0, builder.length() - 1)));
//                    put.addColumn(Log.s2b("test1"), Log.s2b("data"), Log.s2b(""));
//                    puts.add(put);
//
//                }
//                long t1 = System.currentTimeMillis();
//                hBaseCommonDao.insertManyByPut(puts);
//                long t2 = System.currentTimeMillis();
//                Log.say((t2 - t1) + "   :" + start);
//                start += batch;
//            }
//        }
//
//    }
//
//    @Override
//    protected void fromDb2HBase() {
//
//    }
//
//
//    public static void main(String[] args) {
//        try {
//            HBaseTableDaoIn hBaseDao = new HBaseTableDaoNaiveImpl("hcdata2");
//            DBCommonDaoIn dbCommonDao = new DBCommonDao();
//            BaseService service = new Db2HBaseTransfer(hBaseDao, dbCommonDao, "test1");
////            dbCommonDao.getTableSize("", "test1");
//            service.run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        hBaseDao.clo
//
////        DatabaseMetaData dbmd = dbCommonDao.
//
//
//    }
//}
//
//
///**
// * insertManyByList
// *
// * @Override protected void fromDbTable2HBase() throws Exception {
// * <p>
// * int stop = dbCommonDao.getTableSize("", "test1");
// * int batch = 100000;
// * int start = 0;
// * <p>
// * if ("".equals(tableName)) {
// * System.out.println("针对数据库全部表");
// * } else {//针对单个表
// * int length = dbCommonDao.getColumnSet("test", "test1").size();
// * while (start + batch <= stop) {
// * List<Put> puts = new ArrayList<Put>();
// * <p>
// * ResultSet rs = dbCommonDao.scan(tableName, start, batch);
// * List<Map<String, String>> list = new ArrayList<>();
// * while (rs.next()) {
// * <p>
// * <p>
// * Map<String, String> mapPut = new HashMap<>();
// * StringBuilder builder = new StringBuilder();
// * for (int i = 1; i <= length; i++) {
// * builder.append(rs.getObject(i) + "_");
// * }
// * <p>
// * mapPut.put("rowKey", builder.toString().substring(0, builder.length() - 1));
// * mapPut.put("family", "test1");
// * mapPut.put("qualifier", "data");
// * mapPut.put("value", "1");
// * list.add(mapPut);
// * <p>
// * Put put = new Put(Log.s2b(builder.toString().substring(0, builder.length() - 1)));
// * put.addColumn(Log.s2b("test1"), Log.s2b("data"), Log.s2b(""));
// * puts.add(put);
// * <p>
// * }
// * <p>
// * long t1 = System.currentTimeMillis();
// * hBaseDao.insertManyByPut(puts);
// * long t2 = System.currentTimeMillis();
// * Log.say((t2-t1)+"   :"+start);
// * start += batch;
// * }
// * }
// * <p>
// * }
// **/
