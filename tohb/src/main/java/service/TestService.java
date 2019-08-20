package service;

import dao.DBDaoIn;
import dao.mysql.MysqlDao;
import dao.table.HBaseTableDaoIn;
import dao.table.HBaseTableDaoNaiveImpl;

public class TestService extends Db2HBaseTransService {

    private String tableName = "";


    public TestService(HBaseTableDaoIn hBaseDao, DBDaoIn dbCommonDao, String tableName) {
        super(hBaseDao, dbCommonDao);
        if (tableName != null)
            this.tableName = tableName;

    }


    @Override
    protected void fromDbTable2HBase() {
        if ("".equals(tableName)) System.out.println("针对数据库全部表");

    }

    @Override
    protected void fromDb2HBase() {

    }


    public static void main(String[] args) {

        HBaseTableDaoIn hBaseDao = new HBaseTableDaoNaiveImpl("hcdata2");
        DBDaoIn dbCommonDao = new MysqlDao("test1");

        BaseService service = new TestService(hBaseDao, dbCommonDao, "test1");
        service.run();

//        DatabaseMetaData dbmd = dbCommonDao.


    }
}
