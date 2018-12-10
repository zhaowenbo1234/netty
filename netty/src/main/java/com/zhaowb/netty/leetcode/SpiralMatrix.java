package com.zhaowb.netty.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA
 * 给定一个包含 m x n 个元素的矩阵（m 行, n 列），请按照顺时针螺旋顺序，返回矩阵中的所有元素。
 * <p>
 * 示例 1:
 * <p>
 * 输入:
 * [
 * [ 1, 2, 3 ],
 * [ 4, 5, 6 ],
 * [ 7, 8, 9 ]
 * ]
 * 输出: [1,2,3,6,9,8,7,4,5]
 * 示例 2:
 * <p>
 * 输入:
 * [
 * [1, 2, 3, 4],
 * [5, 6, 7, 8],
 * [9,10,11,12]
 * ]
 * 输出: [1,2,3,4,8,12,11,10,9,5,6,7]
 *
 * @author zwb
 * @create 2018/10/10 17:00
 */
public class SpiralMatrix {
    public static void main(String[] args) {

    }

    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null){
            return new ArrayList<>();
        }
        // 待处理的矩阵有M行、N列
        int m = matrix.length;
        int n = matrix[0].length;
        //存放遍历后的结果
        List<Integer> result = new ArrayList<>();
        int r = 0;
        int c = 0;

       for (int i = 0; i < matrix.length;i++){
           for (int j = 0;j<matrix[0].length;j++){

           }
       }
        return null;
    }
}
