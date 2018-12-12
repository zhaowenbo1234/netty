package com.zhaowb.netty.javabase.java8;

/**
 * Created with IDEA
 *
 * @author zwb
 * @create 2018/12/12 15:09
 */
public interface Vehicle {

    default void print(){
        System.out.println("我是一辆车");
    }

    void printHello();

    static void helloWord(){
        System.out.println("helloWord");
    }
}
