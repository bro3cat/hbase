package h_utils.utils;

import org.apache.hadoop.hbase.util.Bytes;

public class Log {

    public static void say(Object o) {
        System.out.println(o.toString());
    }

    public static void say2(Object o, Object o2) {
        System.out.printf("%40s  ： %s\n", o.toString(), o2.toString());
    }

    public static byte[] s2b(String str) {
        return Bytes.toBytes(str);
    }

    public static String b2s(byte[] bytes) {
        return Bytes.toString(bytes);
    }
}
