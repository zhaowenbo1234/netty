package com.zhaowb.netty.ch3;

public class Children extends Parent {

    public Children() {
        System.out.println("Children 构造方法");
    }
    static {
        System.out.println("Children 静态代码块");
    }

    public static void main(String[] args) {
        new Children();
    }
}
