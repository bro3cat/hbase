import common.Property;
import common.StaticConfiguration;
import dao.SqlList;
import dao.mysql.MysqlCommon;

import java.util.*;

public class Test {
    public static void main(String[] args) {
//        String name = new Property(StaticConfiguration.mysql_property).getLoadProperty("db.host");
//        System.out.println(name);


        MysqlCommon mc = new MysqlCommon("test1") {
        };

//        mc.test1();

        List<List<String>> list2 = new ArrayList<List<String>>();
        for (int i = 0; i < 30000; i++) {
            List<String> list = new ArrayList<String>();

            list.add("00013" + i);
            list.add("zhangsan" + i);
            list.add("18" + i);
            //        mc.insert( list);
            list2.add(list);
            if (i % 1000 == 0) System.out.println(i);
        }
        mc.insertMany(list2);

    }
}
