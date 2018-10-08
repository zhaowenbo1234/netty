package com.zhaowb.netty.leetcode;

/**
 * Created with IDEA
 * <p>
 * 给定一个含有 M x N 个元素的矩阵（M行，N列），请以对角线遍历的顺序返回这个矩阵中的所有元素，对角线遍历如下图所示。
 * <p>
 * 示例:
 * <p>
 * 输入:
 * [
 * [ 1, 2, 3 ],
 * [ 4, 5, 6 ],
 * [ 7, 8, 9 ]
 * ]
 * 输出:  [1,2,4,7,5,3,6,8,9]
 * 解释:
 * <p>
 * 说明:
 * <p>
 * 给定矩阵中的元素总数不会超过 100000 。
 *  题号： 498
 * @author zwb
 * @create 2018/9/17 9:20
 */
public class DiagonalTraversal {
    public static void main(String[] args) {
        DiagonalTraversal d = new DiagonalTraversal();
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[] num = d.findDiagonalOrder(matrix);
        for (int n : num) {
            System.out.print(n + " ");
        }

    }

    public int[] findDiagonalOrder(int[][] matrix) {

        if (matrix.length == 0) {
            return new int[0];
        }
        // 待处理的矩阵有M行、N列
        int m = matrix.length;
        int n = matrix[0].length;
        //存放遍历后的结果
        int[] result = new int[m * n];
        int r = 0;
        int c = 0;
        for (int i = 0; i < result.length; i++) {
            result[i] = matrix[r][c];
            if ((r + c) % 2 == 0) {
                if (c == n - 1) {
                    // 元素在最后一列，往下走
                    r++;
                } else if (r == 0) {
                    // 元素在第一行，往右走
                    c++;
                } else {
                    // 其他情况，往右上走
                    r--;
                    c++;
                }
            } else {
                if (r == m - 1) {
                    //元素在最后一行，往右走
                    c++;
                } else if (c == 0) {
                    // //元素在第一列，往下走
                    r++;
                } else {
                    //其他情况，往左下走
                    r++;
                    c--;
                }
            }
        }
        return result;
    }
}
