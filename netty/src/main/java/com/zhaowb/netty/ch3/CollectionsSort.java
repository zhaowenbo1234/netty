package com.zhaowb.netty.ch3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionsSort {

    public static void main(String[] args) {


        List<Integer> list = new ArrayList();
        list.add(3);
        list.add(6);
        list.add(8);
        list.add(9);
        list.add(5);
        list.add(4);
        list.add(1);
        list.add(2);
        list.add(7);
        list.add(11);
        list.add(32);

        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2) {
                    return 1;
                } else if (o1.equals(o2)) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });


        for (Integer i : list) {
            System.out.print(i + " ");
        }
    }
}
