package com.zhaowb.netty.weight;

import java.util.Arrays;

/**
 * @author 缓冲类
 */
public final class RingBuffer {
    /**
     * 最大容量
     */
    public static final int MAX_BUFFER_NUM =
            Integer.parseInt("10");
    /**
     * 写指针
     */
    private int iWritePointer = 0;
    /**
     * 当前数组元素个数
     */
    private int iCurrentNum = 0;
    /**
     * 定义一个数组来缓存数据
     */
    private double[] arrBuffer = new double[MAX_BUFFER_NUM];

    public int getiCurrentNum() {
        return iCurrentNum;
    }

    public double[] getArrBuffer() {

        return arrBuffer;
    }

    public int addRing(int i) {

        return (i + 1) == MAX_BUFFER_NUM ? 0 : i + 1;
    }

    public void putData(double z) {
        if (iCurrentNum < MAX_BUFFER_NUM) {
            arrBuffer[iWritePointer] = z;

            iWritePointer = addRing(iWritePointer);
            iCurrentNum++;
        } else {
        }
    }

    public void clear() {
        iCurrentNum = 0;
        iWritePointer = 0;
    }

    @Override
    public String toString() {
        return Arrays.toString(arrBuffer);
    }
}
