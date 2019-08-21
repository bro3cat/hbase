package service;

import java.sql.ResultSet;

import dao.DBCommonDao;
import dao.DBCommonDaoIn;
import h_utils.dao.table.HBaseTableDaoIn;
import h_utils.dao.table.HBaseTableDaoNaiveImpl;


import java.sql.SQLException;
import java.util.Map;

public class TestService extends Db2HBaseTransService {

    private String tableName = "";


    public TestService(HBaseTableDaoIn hBaseDao, DBCommonDaoIn dbCommonDao, String tableName) {
        super(hBaseDao, dbCommonDao);
        if (tableName != null)
            this.tableName = tableName;
    }


    @Override
    protected void fromDbTable2HBase() throws SQLException {
        if ("".equals(tableName)) {
            System.out.println("针对数据库全部表");
        } else {//针对单个表
            Map<String, String> map = dbCommonDao.getColumnSet("test", "test1");

            int length = 0;
            if (null != map) {
                length = map.size();
                System.out.println("not null");
            }
            ResultSet rs = dbCommonDao.scan(tableName, 1000);
            int io = 0;
            while (rs.next()) {
                io++;
                if (io == 4) break;
                for (int i = 1; i <= length; i++)
                    System.out.println(i + ":" + rs.getObject(i).toString());
                ;
            }
        }

    }

    @Override
    protected void fromDb2HBase() {

    }


    public static void main(String[] args) {

        HBaseTableDaoIn hBaseDao = new HBaseTableDaoNaiveImpl("hcdata2");
        DBCommonDaoIn dbCommonDao = new DBCommonDao();

        BaseService service = new TestService(hBaseDao, dbCommonDao, "test1");
        service.run();
//        hBaseDao.clo

//        DatabaseMetaData dbmd = dbCommonDao.


    }
}
