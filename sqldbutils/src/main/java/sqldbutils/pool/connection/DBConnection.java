package sqldbutils.pool.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 *
 */
public interface DBConnection {

    /**
     * 返回一个数据库连接
     *
     * @return
     */
    public Connection get_connection();

    /**
     * 返回一个Statement
     *
     * @return
     */
    public Statement createStatement();

    /**
     * 返回一个PreparedStatement
     *
     * @param sql
     * @return
     */
    public PreparedStatement prepareStatement(String sql);

    /**
     * 关闭数据库连接
     */
    public void closeConnection();


}
