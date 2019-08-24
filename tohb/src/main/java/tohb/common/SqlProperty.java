package tohb.common;


import h_utils.config.Property;
import h_utils.config.StaticConfiguration;

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
        user = getLoadProperty("mysql_db.user");
        password = getLoadProperty("mysql_db.password");

    }

    private String generateURL(String propertyFilePath) {
        String url = "jdbc:mysql://";
        if (StaticConfiguration.mysql_property.equals(propertyFilePath))
            url = "jdbc:mysql://";
        String host = getLoadProperty("mysql_db.host");
        String port = getLoadProperty("mysql_db.port");
        String name = getLoadProperty("mysql_db.name");
//        url = url + host + ":" + port + "/" + name + "?useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true&characterEncoding=UTF-8&autoReconnect=true";
        url = url + host + ":" + port + "/?useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true&characterEncoding=UTF-8&autoReconnect=true";
        return url;
    }
}
