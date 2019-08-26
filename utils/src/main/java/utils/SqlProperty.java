package utils;


public class SqlProperty extends Property {

    private String url;
    private String user;
    private String password;


    public String url() {
        return url;
    }

    public String user() {
        return user;
    }

    public String password() {
        return password;
    }


    /**
     * 数据库配置信息
     *
     * @param propertyFilePath
     */
    public SqlProperty(String propertyFilePath) {
        super(propertyFilePath);
        url = generateURL(propertyFilePath);
        user = getProperty("mysql_db.user");
        password = getProperty("mysql_db.password");

    }

    private String generateURL(String propertyFilePath) {
        String url = "jdbc:mysql://";
        String host = getProperty("mysql_db.host");
        String port = getProperty("mysql_db.port");
        String name = getProperty("mysql_db.name");
//        url = url + host + ":" + port + "/" + name + "?useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true&characterEncoding=UTF-8&autoReconnect=true";
        url = url + host + ":" + port + "/?useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true&characterEncoding=UTF-8&autoReconnect=true";
        return url;
    }
}
