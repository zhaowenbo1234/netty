package com.zhaowb.netty.javabase.java8;

/**
 * Created with IDEA
 * 当一个类实现的接口中，只有一个默认方法，可以不重写此默认方法，若有多个重名默认方法，则需要重写默认方法，
 * 可以使用 接口.super.默认方法名进行重新，如   FourWheeler.super.print();
 *
 * java 8 接口可以声明静态方法
 * @author zwb
 * @create 2018/12/12 15:12
 */
public class VehicleTest {

    public static void main(String[] args) {
        Vehicle vehicle = new VehicleImpl();
        vehicle.print();
        vehicle.printHello();
        Vehicle.helloWord();
    }
}
