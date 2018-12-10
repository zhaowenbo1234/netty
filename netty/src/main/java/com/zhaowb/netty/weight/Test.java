package com.zhaowb.netty.weight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zwb
 */
public class Test {
    private final static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {

        //02 2B 78 20 30 30 30 31 30 35 30 30 30 30 30 30 0D 18
        String s = "0x02 0x2B 0x78 0x20 0x30 0x30 0x30 0x31 0x30 0x35 0x30 0x30 0x30 0x30 0x30 0x30 0x0D 0x18";
        String[] ss = s.split(" ");

        byte[] bytes = s.getBytes();

        WeightServerHandler.prepareMessage("",bytes);

        // 转换为int型数组
//        int[] arr = new int[18];
//        for (int i = 0; i < arr.length; i++) {
//            arr[i] = Integer.parseInt(ss[i], 16);
//            System.out.print(arr[i] + " ");
//        }
//        System.out.println();
//        boolean validate = WeightServerHandler.validate(arr);
//        logger.info("validate = [{}]", validate);
    }
}
