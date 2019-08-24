package h_utils.utils;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * 工具类，可扩展为日志类
 */
public class Log {

    /**
     * 代替Sysout
     *
     * @param o
     */
    public static void say(Object o) {
        System.out.println(o.toString());
    }

    /**
     * 输出长line“------------”
     */
    public static void line() {
        System.out.println("-----------------------------------------------------------------------------------------------");
    }

    /**
     * 格式化左对齐输出“key”：“value”
     *
     * @param o  key
     * @param o2 value
     */
    public static void say2(Object o, Object o2) {
        System.out.printf("%-50s  ： %s\n", o.toString(), o2.toString());
    }

    /**
     * 字符串转Bytes[]
     *
     * @param str 字符串String
     * @return Bytes[]
     */
    public static byte[] s2b(String str) {
        return Bytes.toBytes(str);
    }

    /**
     * Bytes[]转字符串
     *
     * @param bytes Bytes[]
     * @return 字符串String
     */
    public static String b2s(byte[] bytes) {
        return Bytes.toString(bytes);
    }
}
