package com.zhaowb.netty.weight;

/**
 * @author zwb
 */
public class Tests {
    private final static int LENGTH = 100;

    public static void main(String[] args) {
        int a;
        int b;
        for (a = 1, b = 1; a <= LENGTH; a++) {
            if (b >= 20) {
                break;
            }

            if (b % 3 == 1) {
                b += 3;
                continue;
            }
            b -= 5;
        }
        System.out.println(a);
    }
}
