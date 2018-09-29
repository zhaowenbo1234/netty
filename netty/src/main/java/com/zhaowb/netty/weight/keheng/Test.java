package com.zhaowb.netty.weight.keheng;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.awt.*;
import java.util.Random;

/**
 * Created with IDEA
 *
 * @author zwb
 * @create 2018/9/21 17:43
 * <p>
 * 5A 01 68 46 D1   显示重量为16846KG
 * 5A 10 68 46 C1  显示重量为684.6KG
 * 5A 90 31 54 0A  显示重量为-315.4KG
 * 5A A0 31 54 3A  显示重量为-31.54KG
 */
public class Test {
    public static void main(String[] args) {

        String s = "5A A0 31 54 3A";
        String[] ss = s.split(" ");
        int[] arr = new int[5];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(ss[i], 16);
        }

        boolean check = KeHengServerHandler.validate(arr);
        System.out.println("check = " + check);
        int b1s7 = (arr[1] & 0x80) >> 7;

        int b1s6 = (arr[1] & 0x40) >> 6;
        int b1s5 = (arr[1] & 0x20) >> 5;
        int b1s4 = (arr[1] & 0x10) >> 4;

        int b1s0123 = (arr[1] & 0x0F);

        String stringWeight = b1s0123 + ss[2] + ss[3];
        double weight = Double.valueOf(stringWeight);
        if (b1s7 == 1) {
            weight = -weight;
        }
        if (b1s6 == 1) {
            weight = weight / 1000;
        } else if (b1s5 == 1) {
            System.out.println("b1s5 = " + b1s5);
            weight = weight / 100;
        } else if (b1s4 == 1) {
            weight = weight / 10;
        }

        JSONObject object = JSON.parseObject("{ \"socketAddress\":" + 192 + ",\"weight\":" + weight + "}");
        System.out.println("object  = " + object);
        System.out.println("weight = " + weight);
        System.out.println("b1s0123 = " + b1s0123);

        new Random(10).nextInt();

        new Font("",Font.LAYOUT_LEFT_TO_RIGHT,12);
    }
}