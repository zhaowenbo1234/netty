package com.zhaowb.netty.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组和一个目标值，找出数组中和为目标值的两个数。
 * <p>
 * 你可以假设每个输入只对应一种答案，且同样的元素不能被重复利用。
 * <p>
 * 示例:
 * <p>
 * 给定 nums = [2, 7, 11, 15], target = 9
 * <p>
 * 因为 nums[0] + nums[1] = 2 + 7 = 9 所以返回 [0, 1]
 *
 * @author zwb
 */
public class LeetCode001 {

    public static void main(String[] args) {

        int[] nums = {2, 7, 11, 15};
        int target = 9;
        LeetCode001 leetCode001 = new LeetCode001();
        int[] tt = leetCode001.twoSum(nums, target);
        for (int i = 0; i < tt.length; i++) {
            System.out.print(tt[i] + " ");
        }


    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int component = target - nums[i];
            if (map.containsKey(component)) {
                return new int[]{map.get(component), i};
            } else {
                map.put(nums[i], i);
            }
        }
        throw new IllegalArgumentException("error");
    }
}
