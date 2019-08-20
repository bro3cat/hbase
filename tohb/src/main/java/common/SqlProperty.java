package common;


public class SqlProperty extends Property {

    private String url;
    private String user;
    private String password;

    public String url(){return url;}
    public String user(){return user;}
    public String password(){return password;}


    public SqlProperty(String propertyFilePath) {
        super(propertyFilePath);
        url = generateURL(propertyFilePath);
        user = getLoadProperty("db.user");
        password = getLoadProperty("db.password");

    }

    private String generateURL(String propertyFilePath) {
        String url = "jdbc:mysql://";
        if (StaticConfiguration.mysql_property.equals(propertyFilePath))
            url = "jdbc:mysql://";
        String host = getLoadProperty("db.host");
        String port = getLoadProperty("db.port");
        String name = getLoadProperty("db.name");
        url = url + host + ":" + port + "/" + name + "?useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true&characterEncoding=UTF-8&autoReconnect=true";
        return url;
    }

}
