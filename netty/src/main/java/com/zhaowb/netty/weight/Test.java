package com.zhaowb.netty.weight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zwb
 */
public class Test {
    private final static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        String s = "02 2B 78 20 30 30 30 31 30 35 30 30 30 30 30 30 0D 18";
        String[] ss = s.split(" ");


        // 转换为int型数组
        int[] arr = new int[18];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(ss[i], 16);
        }

        boolean validate = WeightServerHandler.validate(arr);
        logger.info("validate = [{}]", validate);
    }
}
