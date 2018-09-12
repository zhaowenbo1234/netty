package com.zhaowb.netty.leetcode;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 寻找数组的中心索引
 * 给定一个整数类型的数组 nums，请编写一个能够返回数组“中心索引”的方法。
 * <p>
 * 我们是这样定义数组中心索引的：数组中心索引的左侧所有元素相加的和等于右侧所有元素相加的和。
 * <p>
 * 如果数组不存在中心索引，那么我们应该返回 -1。如果数组有多个中心索引，那么我们应该返回最靠近左边的那一个。
 * <p>
 * 示例 1:
 * <p>
 * 输入:
 * nums = [1, 7, 3, 6, 5, 6]
 * 输出: 3
 * 解释:
 * 索引3 (nums[3] = 6) 的左侧数之和(1 + 7 + 3 = 11)，与右侧数之和(5 + 6 = 11)相等。
 * 同时, 3 也是第一个符合要求的中心索引。
 * 示例 2:
 * <p>
 * 输入:
 * nums = [1, 2, 3]
 * 输出: -1
 * 解释:
 * 数组中不存在满足此条件的中心索引。
 * 说明:
 * <p>
 * nums 的长度范围为 [0, 10000]。
 * 任何一个 nums[i] 将会是一个范围在 [-1000, 1000]的整数。
 */
public class ArrayCenterIndex {

    private static Logger logger = LoggerFactory.getLogger(ArrayCenterIndex.class);

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        int[] nums = {-1, -1, -1, -1, -1, 0};
        ArrayCenterIndex arrayCenterIndex = new ArrayCenterIndex();
        int i = arrayCenterIndex.pivotIndex(nums);
        long end = System.currentTimeMillis();
        System.out.println(i);

        logger.info("用时{}", (end - start));
    }

    public int pivotIndex(int[] nums) {
        int length = nums.length;
        if (length <= 2) {
            return -1;
        }
        int num1 = 0;
        int num2 = 0;
        int n = length / 2;
        for (int i = 0; i < n; i++) {
            num1 = num1 + nums[i];
        }
        for (int j = n; j < length - 1; j++) {
            num2 = num2 + nums[j];
        }
        if (num1 == num2) {
            return n;
        }
        while (n > 0 && n < length - 1) {
            if (num1 == num2) {
                return n;
            } else if (num1 > num2) {
                if (nums[n] <= 0) {
                    num1 = num1 + nums[n];
                    num2 = num2 - nums[n];
                } else {
                    num1 = num1 - nums[n];
                    num2 = num2 + nums[n];
                }
                if (num1 < num2) {
                    return -1;
                }
                n--;

            } else {
                num1 = num1 + nums[n];
                num2 = num2 - nums[n];
                if (num1 > num2) {
                    return -1;
                }
                n++;
            }

        }
        return -1;
    }
}
