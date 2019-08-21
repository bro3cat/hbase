package h_utils.dao.table;

import org.apache.hadoop.hbase.client.Scan;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {

        HBaseTableDaoIn h = new HBaseTableDaoNaiveImpl("hcdata12");
        h.addColumnFamily("he");
//        new Test().test(h, new Scan());
//        new HBaseTableDaoNaiveImpl("hcdata12");//.scanTable(new Scan());
        Test test = new Test();
        test.test_getResult(h, "01010012000p20181027jc0100392");
//        test.test_scanTable(h, new Scan());
    }

    void test_scanTable(HBaseTableDaoIn h, Scan scan) {
        try {
            h.scanTable(scan);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void test_getResult(HBaseTableDaoIn h, String key) {
        try {
//            h.scanTable(scan);
            h.getResult(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
