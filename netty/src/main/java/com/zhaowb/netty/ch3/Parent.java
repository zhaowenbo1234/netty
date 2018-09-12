package com.zhaowb.netty.ch3;

public class Parent {

    Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Parent() {
        System.out.println("Parent 构造方法");
    }

    static {
        System.out.println("Parent 静态代码块");
    }
}
