package com.zhaowb.netty.leetcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IDEA
 * 给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
 * 最高位数字存放在数组的首位， 数组中每个元素只存储一个数字。
 * 你可以假设除了整数 0 之外，这个整数不会以零开头。
 * 示例 1:
 * 输入: [1,2,3]
 * 输出: [1,2,4]
 * 解释: 输入数组表示数字 123。
 * 示例 2:
 * 输入: [4,3,2,1]
 * 输出: [4,3,2,2]
 * 解释: 输入数组表示数字 4321。
 * 输入{9}，输出 {1,0}
 * 输入{9,9} 输出{1,0,0}
 * <p>
 * 思路1 ：
 *      转为String 在转为Integer + 1 ，但是当数组长度过长时，失败抛出异常
 *      Exception in thread "main" java.lang.NumberFormatException: For input string: "9876543210"
 *          at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
 *          at java.lang.Integer.parseInt(Integer.java:583)
 *          at java.lang.Integer.parseInt(Integer.java:615)
 * 思路2：
 *      1.从数组的后边向前判断是否是9，若是则将该位置为0，否则该位置数字 +1 退出循环。
 *      2.判断数组的第一个数字是否是10，或者0，若是则新建一个数组，将数组的第一位设为0，后边默认0，不动，返回新数组
 *
 * @author zwb
 * @create 2018/9/13 17:30
 */
public class PlusOne {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlusOne.class);

    public static void main(String[] args) {
        PlusOne addOne = new PlusOne();
        long time = System.currentTimeMillis();
        int[] nums = addOne.plusOne(new int[]{9,9});
        LOGGER.info("用时 ： {}", System.currentTimeMillis() - time);
        for (int n : nums) {
            System.out.print(n + " ,");
        }
    }

    public int[] plusOne(int[] digits) {
        int max = 9;
        int ten = 10;
        int length = digits.length;
        for (int i = length - 1; i >= 0; i--) {
            if (digits[i] >= max) {
                digits[i] = 0;
            } else {
                digits[i] = digits[i] + 1;
                break;
            }
        }
        if ( digits[0] == ten || digits[0] == 0) {
            int[] nums = new int[length + 1];
            nums[0] = 1;
            return nums;
        }
        return digits;
    }
}