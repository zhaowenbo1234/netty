package com.zhaowb.netty.javabase.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created with IDEA
 * 方法引用
 *
 * @author zwb
 * @create 2018/12/12 14:20
 */
public class Car {

    // Supplier 是jdk1.8的接口，这里和lamda一起使用了
    public static Car create(final Supplier<Car> supplier) {
        return supplier.get();
    }

    public static void collide(final Car car) {
        System.out.println("Collided " + car.toString());
    }

    public void follow(final Car another) {
        System.out.println("Following the " + another.toString());
    }

    public void repair() {
        System.out.println("Repaired " + this.toString());
    }

    public static void main(String[] args) {
        final Car car = Car.create(Car::new);
        final List<Car> carList = Arrays.asList(car);
        carList.forEach(Car::collide);

        carList.forEach(Car::repair);
        carList.forEach(car::follow);
        System.out.println(car);

        List names = new ArrayList();

        names.add("Google");
        names.add("Runoob");
        names.add("Taobao");
        names.add("Baidu");
        names.add("Sina");

        names.forEach(System.out::println);

        System.out.println("lamda...");
        names.forEach(s -> System.out.println(s));

    }
}
