package com.zhaowb.netty.javabase;

/**
 * Created with IDEA
 *
 * @author zwb
 * @create 2018/12/11 11:48
 */
public class StringConcatenate {

    private static final int length = 5000;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < length; i++) {
            String result = "This is"
                    + "testing the"
                    + "difference" + "between"
                    + "String" + "and" + "StringBuffer";
        }
        long endTime = System.currentTimeMillis();
        System.out.println("字符串连接"
                + " - 使用 + 操作符 : "
                + (endTime - startTime) + " ms");
        long startTime1 = System.currentTimeMillis();
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < length; i++) {
            result.append("This is");
            result.append("testing the");
            result.append("difference");
            result.append("between");
            result.append("String");
            result.append("and");
            result.append("StringBuffer");
        }
        long endTime1 = System.currentTimeMillis();
        System.out.println("字符串连接"
                + " - 使用 StringBuffer : "
                + (endTime1 - startTime1) + " ms");
    }
}
