import dao.mysql.MysqlDao;

public class TestAbstractClass extends MysqlDao {
    public TestAbstractClass(String tableName) {
        super(tableName);
        this.tableName = null;
    }
}
