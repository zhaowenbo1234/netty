package com.zhaowb.netty.leetcode;

/**
 * 给定一个 32 位有符号整数，将整数中的数字进行反转。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 123 输出: 321 示例 2:
 * <p>
 * 输入: -123 输出: -321 示例 3:
 * <p>
 * 输入: 120 输出: 21 注意:
 * <p>
 * 假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−231, 231 − 1]。根据这个假设，如果反转后的整数溢出，则返回 0。
 *
 * @author zwb
 */
public class LeetCode007 {

    public static void main(String[] args) {

        LeetCode007 leetCode007 = new LeetCode007();
        int tt = leetCode007.reverse(1534236469);
        System.out.println(tt);
    }

    public int reverse(int x) {

        int result = 0;
        while (x != 0) {

            int tmp = result * 10 + x % 10;
            x = x / 10;
            if (tmp / 10 != result) {
                result = 0;
                break;
            }
            result = tmp;

        }
        return result;
    }

}
