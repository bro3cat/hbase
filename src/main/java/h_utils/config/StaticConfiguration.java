package h_utils.config;

import org.apache.hadoop.hbase.client.Scan;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认的配置文件
 *
 * @author wyd
 */
public class StaticConfiguration {

    /**
     * 默认的property文件所在
     */
    public static final String configPath = "/config/config.property";

    public static final String hmaster = "10.0.112.216";

    public static final String mysql_property = "D:\\share\\config.txt";

    public static final List<String> static_list = new ArrayList<String>();

    public static final Scan DEFAULT_SCAN = new Scan();

    static {
        static_list.add("data");
    }
//	public static final String config_property = ""
}
