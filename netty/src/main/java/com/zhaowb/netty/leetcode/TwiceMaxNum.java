package com.zhaowb.netty.leetcode;

/**
 * Created with IDEA
 *
 * @author zwb
 * @create 2018/9/13 9:10
 * 在一个给定的数组nums中，总是存在一个最大元素 。
 * 查找数组中的最大元素是否至少是数组中每个其他数字的两倍。
 * 如果是，则返回最大元素的索引，否则返回-1。
 * 示例 1:
 * 输入: nums = [3, 6, 1, 0]
 * 输出: 1
 * 解释: 6是最大的整数, 对于数组中的其他整数,
 * 6大于数组中其他元素的两倍。6的索引是1, 所以我们返回1.
 * 示例 2:
 * 输入: nums = [1, 2, 3, 4]
 * 输出: -1
 * 解释: 4没有超过3的两倍大, 所以我们返回 -1.
 * 提示:
 * nums 的长度范围在[1, 50].
 * 每个 nums[i] 的整数范围在 [0, 99]
 * {0,0,0,1} 输出3，
 * {1} 输出0
 * {1,0} 输出0
 * {0,0,3,2} 输出 -1
 */
public class TwiceMaxNum {
    public static void main(String[] args) {
        int[] nums = {0, 0, 0, 2};
        TwiceMaxNum twiceMaxNum = new TwiceMaxNum();
        int m = twiceMaxNum.dominantIndex(nums);
        System.out.println(m);
    }

    public int dominantIndex(int[] nums) {

        // 如果只有一个数，直接返回0
        if (nums.length == 1) {
            return 0;
        }

        int multiple = 2;
        int max = nums[0];
        int lateMax = 0;
        int index = 0;
        // 找到数组中最大值以及所有在位置，并找到次大值
        for (int i = 0; i < nums.length; i++) {
            if (i != 0) {
                if (max <= nums[i]) {
                    lateMax = max;
                    max = nums[i];
                    index = i;
                } else if (lateMax < nums[i]) {
                    lateMax = nums[i];
                }
            }
        }
        // 比较最大值是否大于等于次大值的2倍，如果大于则返回最大值索引，否则返回-1
        if (max >= multiple * lateMax) {
            return index;
        } else {
            return -1;
        }
    }
}