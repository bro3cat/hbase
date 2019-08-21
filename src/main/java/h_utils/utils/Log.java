package h_utils.utils;

import org.apache.hadoop.hbase.util.Bytes;

public class Log {

    public static void say(Object o) {
        System.out.println(o.toString());
    }

    public static byte[] s2b(String str) {
        return Bytes.toBytes(str);
    }

    public static String b2s(byte[] bytes) {
        return Bytes.toString(bytes);
    }
}
