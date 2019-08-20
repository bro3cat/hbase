import dao.DBCommonDaoIn;
import dao.mysql.MysqlCommon;
import pool.connection.DBConnectionIn;
import pool.connection.MysqlConnection;

import java.util.*;
import java.util.List;

public class Test {
    public static void main(String[] args) {
//        String name = new Property(StaticConfiguration.mysql_property).getLoadProperty("db.host");
//        System.out.println(name);


        MysqlCommon mc = new MysqlCommon("test1") {
        };

//        mc.test1();

        List<List<String>> list2 = new ArrayList<List<String>>();
        for (int i = 0; i < 100000; i++) {
            List<String> list = new ArrayList<String>();

            list.add("00013" + i);
            list.add("zhangsan" + i);
            list.add("18" + i);
            //        mc.insert( list);
            list2.add(list);
//            if (i % 1000 == 0) System.out.println(i);
        }
//        mc.insertMany(list2);

        DBConnectionIn conn = new MysqlConnection();

        DBCommonDaoIn dao = new MysqlCommon("test1", conn) {
        };
        new Test().test(dao, list2);


    }

    public void test(DBCommonDaoIn dao, List list) {

        dao.test(list, 300);


//
//        int max = 2000;
//        int min = 50;
//        int batchSize = min;
//        int step = 10;
//        long temp = 0;
//        TreeMap<Integer, Long> map = new TreeMap<>();
//        List<Integer> index = new ArrayList<>();
//        List<Long> value = new ArrayList<>();
//        int mark = 0;
//        int now = 0;
//        int pre;
//        int back;
//        boolean start = true;
//
//        for (batchSize = min; batchSize <= 2000; batchSize += step) {
//            index.add(batchSize);
//            temp = dao.test(list, batchSize);
//            value.add(temp);
//            map.put(batchSize, temp);
//        }
//
//        for (int i = 0; i < index.size(); i++)
//            System.out.println(index.get(i) + "\t:   " + value.get(i));
//
    }


}
