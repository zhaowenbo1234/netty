package com.zhaowb.netty.javabase.array;

/**
 * Created with IDEA
 * 阶乘
 *
 * @author zwb
 * @create 2018/12/11 15:05
 */
public class Factorial {
    public static void main(String[] args) {
        long num = 7;
        long sum = factorial(num);
        System.out.println(sum);
    }

    public static long factorial(long num) {
        if (num < 1) {
            return 1;
        } else {
            return num * factorial(num - 1);
        }
    }
}
