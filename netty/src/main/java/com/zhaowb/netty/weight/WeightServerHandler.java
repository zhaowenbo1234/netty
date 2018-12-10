package com.zhaowb.netty.weight;

import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.apache.commons.math3.stat.StatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zwb
 */
public class WeightServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private final static Logger logger = LoggerFactory.getLogger(WeightServerHandler.class);
    static Map<String, RingBuffer> bufferMap = new HashMap<>();

    @Override
    protected synchronized void messageReceived(ChannelHandlerContext ctx, DatagramPacket packet) {
        InetSocketAddress address = packet.sender();

        byte[] bytes = new byte[packet.content().readableBytes()];
        packet.content().readBytes(bytes);

        String msg = ByteBufUtil.hexDump(bytes);
        logger.info(address.toString() + " receive " + msg);

        String json = prepareMessage(address.toString(), bytes);
        if (json != null) {
            logger.info("send cloud json={}", json);
        }
    }

    public static String prepareMessage(String socketAddress, byte[] bytes) {
        RingBuffer cRingBuffer = bufferMap.get(socketAddress);
        if (cRingBuffer == null) {
            cRingBuffer = new RingBuffer();
            bufferMap.put(socketAddress, cRingBuffer);
        }
        String str = ByteBufUtil.hexDump(bytes);
        str = str.replaceAll("(.{2})", "$1 ");
        String[] arrStr = str.split(" ");
        // 转换为int型数组
        int[] arr = new int[18];

        for (int i = 0; i < 18; i++) {
            arr[i] = Integer.parseInt(arrStr[i], 16);
        }
        // 数组处理
        double result = 0.0;
        int iCheckStatus = 0;
        double variance = 0;

        // 校验和
        boolean checkSum = validate(arr);
        //   字节1 ：起始字节，为0x02。字节4：固定字节 0x20。字节 17 ：为 0x0D
        boolean condtion0 = checkSum && arr[0] == 0x02 && arr[3] == 0x20 && arr[16] == 0x0D;
        // 先判断第一个字节和第17个字节
        logger.info("check condition [checkSum  && arr[0] == 0x02 && arr[3] == 0x20 && arr[16] == 0x0D] ->" + condtion0);
        if (condtion0) {
            char c = 48;
            int[] a = new int[]{arr[4] - c, arr[5] - c, arr[6] - c, arr[7] - c, arr[8] - c, arr[9] - c};
            String str5 = "" + a[0] + a[1] + a[2] + a[3] + a[4] + a[5];

            Long num5 = Long.parseLong(str5);
            // 第三个字节
            int b2s1 = (arr[2] & 0x2) >> 1;
            // 第四个字节
            int b3s012 = (arr[3] & 0x7);
            int b3s3 = (arr[3] & 0x4) >> 3;
            int b3s56 = (arr[3] & 0x60) >> 5;

            iCheckStatus = b3s3;
            boolean condtion1 = (b3s012 == 0 && b3s56 == 1);
            logger.info("check condition [b3s012 == 0 && b3s56 == 1] -> " + condtion1);
            result = num5;
            if (b2s1 == 1) {
                result = (-result);
            }

            if (iCheckStatus == 1) {
                cRingBuffer.clear();
                return String.valueOf(result);
            } else {
                cRingBuffer.putData(result);
                // 满10个数，计算方差
                int iArrBufferLength = cRingBuffer.getiCurrentNum();
                if (iArrBufferLength >= RingBuffer.MAX_BUFFER_NUM) {

                    variance = StatUtils.variance(cRingBuffer.getArrBuffer());
                    logger.info("variance = {variance}" + variance);
                    cRingBuffer.clear();
                    logger.info("重量数据test:-------------" + result);
                    return String.valueOf(result);
                }
            }
        }
        return null;
    }

    /**
     * 校验和算法
     *
     * @param arr int数组
     * @return
     */
    public static boolean validate(int[] arr) {

        int i = 0;
        int tt = 0;
        int bb = 0;
        boolean flag = false;
        for (i = 0; i < arr.length - 1; i++) {
            bb += arr[i];
        }
        tt = bb & 0xff;

        logger.info(" arr[arr.length-1] = {}", arr[arr.length - 1]);
        if (tt == arr[arr.length - 1]) {
            flag = true;
        }
        logger.info("校验和 = [{}]", tt);
        return flag;
    }
}