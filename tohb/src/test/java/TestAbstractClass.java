import dao.mysql.MysqlCommon;

public class TestAbstractClass extends MysqlCommon {
    public TestAbstractClass(String tableName) {
        super(tableName);
        this.tableName = null;
    }
}
