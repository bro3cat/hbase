import dao.mysql.Deprecated_MysqlDao;

public class TestAbstractClass extends Deprecated_MysqlDao {
    public TestAbstractClass(String tableName) {
        super(tableName);
        this.tableName = null;
    }
}
