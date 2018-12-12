package com.zhaowb.netty.javabase.java8;

/**
 * Created with IDEA
 *
 * @author zwb
 * @create 2018/12/12 15:11
 */
public class VehicleImpl implements Vehicle,FourWheeler {


    @Override
    public void print() {
        FourWheeler.super.print();
    }

    @Override
    public void printHello() {
        System.out.println("printHello");
    }
}
