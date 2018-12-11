package com.zhaowb.netty.javabase.array;

/**
 * Created with IDEA
 * 斐波那契数列指的是这样一个数列 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233，377，610，987，1597，2584，4181，
 * 6765，10946，17711，28657，46368……
 * 特别指出：第0项是0，第1项是第一个1。
 * 这个数列从第三项开始，每一项都等于前两项之和。
 *
 * @author zwb
 * @create 2018/12/11 15:00
 */
public class FibonacciSequence {
    public static void main(String[] args) {
        long num = 10;
        long sum = fibonacci(num);
        System.out.println(sum);
    }

    public static long fibonacci(long num) {
        if ((num == 0) || (num == 1)) {
            return num;
        } else {
            return fibonacci(num - 1) + fibonacci(num - 2);
        }
    }
}
