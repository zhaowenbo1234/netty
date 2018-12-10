package com.zhaowb.netty.weight;

import io.netty.buffer.ByteBufUtil;

/**
 * @author zwb
 */
public class Tests {
    private final static int LENGTH = 100;

    public static void main(String[] args) {
        byte[] bytes = {0x59};

        String a = ByteBufUtil.hexDump(bytes);
        System.out.println(a);
    }
}
