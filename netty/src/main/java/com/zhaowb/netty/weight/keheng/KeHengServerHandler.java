package com.zhaowb.netty.weight.keheng;

import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Created with IDEA
 *
 * @author zwb
 * @create 2018/9/21 15:31
 */
public class KeHengServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private static final Logger logger = LoggerFactory.getLogger(KeHengServerHandler.class);

    private static final String STARTBYTE = "5A";

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket msg) {

        InetSocketAddress address = msg.sender();

        byte[] bytes = new byte[msg.content().readableBytes()];
        msg.content().readBytes(bytes);

        String message = ByteBufUtil.hexDump(bytes);
        logger.info("address = {} ,received message = {}", address.toString(), message);

        String json = prepareMessage(address.toString(), bytes);
        if (json != null) {
            logger.info(" TODO send cloud json={}", json);
        }
    }

    public String prepareMessage(String socketAddress, byte[] bytes) {

        String str = ByteBufUtil.hexDump(bytes);
        str = str.replaceAll("(.{2})", "$1 ");
        String[] arrStr = str.split(" ");

        if (STARTBYTE.equalsIgnoreCase(arrStr[0])) {
            // 转换为int型数组
            int[] arr = new int[5];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = Integer.parseInt(arrStr[i], 16);
            }

            // 校验和
            boolean checkSum = validate(arr);

            if (checkSum) {
                //  符号判断，1为负，0为正
                int b1s7 = (arr[1] & 0x80) >> 7;

                // 小数位判断，一位小数点时小数位为001，两位是010，三位小数位100
                int b1s6 = (arr[1] & 0x40) >> 6;
                int b1s5 = (arr[1] & 0x20) >> 5;
                int b1s4 = (arr[1] & 0x10) >> 4;

                int b1s0123 = (arr[1] & 0x0F);

                String stringWeight = b1s0123 + arrStr[2] + arrStr[3];
                double weight = Double.valueOf(stringWeight);
                if (b1s7 == 1) {
                    weight = -weight;
                }
                if (b1s6 == 1) {
                    weight = weight / 1000;
                } else if (b1s5 == 1) {
                    weight = weight / 100;
                } else if (b1s4 == 1) {
                    weight = weight / 10;
                }
                return "{ \"socketAddress\":" + socketAddress + ",\"weight\":" + weight + "}";
            }
        }
        return null;
    }

    /**
     * 校验和算法
     * K5:K1—K4的异或校验的结果再异或0XA5
     *
     * @param arr int数组
     * @return 如果校验成功返回true 校验失败返回false
     */
    public static boolean validate(int[] arr) {
        int bb = 0;
        boolean flag = false;
        for (int i = 0; i < arr.length - 1; i++) {
            bb = bb ^ arr[i];
        }
        bb = bb ^ 0xA5;
        logger.info("Calculated checksum = {},received checksum  = {}", bb, arr[arr.length - 1]);
        if (arr[arr.length - 1] == bb) {
            flag = true;
        }
        return flag;
    }
}