package com.zhaowb.netty.javabase;

/**
 * Created with IDEA
 *
 * @author zwb
 * @create 2018/12/11 11:28
 */
public class StringCompareEmp {

    public static void main(String[] args) {
        String str = "Hello World ,Hello Runoob";
        String anotherString = "hello world";
        Object objStr = str;
        System.out.println(str.compareTo(anotherString));
        System.out.println(str.compareToIgnoreCase(anotherString));
        int lastIndex = str.lastIndexOf("Runoob");
        if (lastIndex != -1) {
            System.out.println("Runoob 字符串最后出现的位置" + lastIndex);
        }

        System.out.println("字符串反转" + new StringBuilder(str).reverse().toString());
//        System.out.println(removeCharAt(str, 3));

        System.out.println("大写" + str.toUpperCase());
    }

    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }
}
