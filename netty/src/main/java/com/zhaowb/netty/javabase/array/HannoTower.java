package com.zhaowb.netty.javabase.array;

/**
 * Created with IDEA
 * 汉诺塔
 *
 * @author zwb
 * @create 2018/12/11 14:39
 */
public class HannoTower {

    private static int count = 1;

    public static void doTowers(int topN, char from, char inter, char to) {
        if (topN == 1) {
            System.out.println(count + "Disk 1 from " + from + " to " + to);
            count++;
        } else {
            doTowers(topN - 1, from, to, inter);
            System.out.println(count + "Disk " + topN + " from " + from + " to " + to);
            count++;
            doTowers(topN - 1, inter, from, to);
        }
    }

    public static void main(String[] args) {
        int nDisks = 3;
        doTowers(nDisks, 'A', 'B', 'C');
    }
}
