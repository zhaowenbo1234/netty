package com.zhaowb.netty;

public class Test {
    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder();
        String s = "B1201\n" +
                "B1202\n" +
                "B1203\n" +
                "B1206\n" +
                "B1207\n" +
                "B1208\n" +
                "B1301\n" +
                "B1302\n" +
                "B1303\n" +
                "B1306\n" +
                "B1307\n";
        String[] ss = s.split("\n");
        for (String m : ss) {
            sb.append(m + ",");
        }
        sb.deleteCharAt(sb.length() - 1);

        System.out.println(sb.toString());
    }
}
