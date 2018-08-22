package com.zhaowb.netty.ch4_3;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import static sun.misc.Version.println;

public class Test {
    public static void main(String[] args) {
        List list = new ArrayList<>();
        list.add("1");
        list.add("w");
        list.add("s");
        list.add("d");
        list.add("f");
        list.add("3");
        int index = 0;
        list.forEach((n) -> System.out.print(n));
        list.forEach(System.out::print);

        System.out.println();
        System.out.println("===========================");
        double i = 987;
        double i2 = 123;

        double t = i / i2;
        System.out.println(t);
        System.out.println("===========================");

        List<Double> doubles = new ArrayList<Double>();
        doubles.add(1.0);
        doubles.add(11.0);
        doubles.add(10.0);
        doubles.add(9.0);
        doubles.add(19.0);
        doubles.add(17.0);
        doubles.add(13.0);
        doubles.add(11.0);
        doubles.add(16.0);
        doubles.add(13.0);
        int size = doubles.size();

        for (int m = size - 1; m >= 0; m--) {

            double tt = Math.abs(doubles.get(m) - 10) / 10;

            if (tt > 0.05) {
                doubles.remove(m);
            }
        }

        doubles.forEach(System.out::println);
    }
}
