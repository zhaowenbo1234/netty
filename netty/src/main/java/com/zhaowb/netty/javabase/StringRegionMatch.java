package com.zhaowb.netty.javabase;

/**
 * Created with IDEA
 *
 * @author zwb
 * @create 2018/12/11 11:39
 */
public class StringRegionMatch {
    public static void main(String[] args) {
        String first_str = "Welcome to Microsoft";
        String second_str = "I work with microsoft";

        System.out.println(first_str.substring(11, 20));
        System.out.println(second_str.substring(12, 21));

        boolean match1 = first_str.regionMatches(11, second_str, 12, 9);
        //第一个参数 true 表示忽略大小写区别
        boolean match2 = first_str.regionMatches(true, 11, second_str, 12, 9);
        System.out.println("区分大小写返回值：" + match1);
        System.out.println("不区分大小写返回值：" + match2);


        String curDir = System.getProperty("user.dir");
        System.out.println("你当前的工作目录为 :" + curDir);
    }
}
