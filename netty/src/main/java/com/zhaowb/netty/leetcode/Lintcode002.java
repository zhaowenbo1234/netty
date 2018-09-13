package com.zhaowb.netty.leetcode;

/**
 * 给出两个整数 aa 和 bb , 求他们的和。
 * <p>
 * 你不需要从输入流读入数据，只需要根据aplusb的两个参数a和b，计算他们的和并返回就行。
 * <p>
 * 您在真实的面试中是否遇到过这个题？
 * 说明
 * a和b都是 32位 整数么？
 * <p>
 * 是的
 * 我可以使用位运算符么？
 * <p>
 * 当然可以
 * 样例
 * 如果 a=1 并且 b=2，返回3。
 * <p>
 * 挑战
 * 显然你可以直接 return a + b，但是你是否可以挑战一下不这样做？（不使用++等算数运算符）
 *
 * @author zwb
 */
public class Lintcode002 {

    public static void main(String[] args) {
        Lintcode002 lintcode002 = new Lintcode002();
        ListNode l1 = new ListNode(257);
        ListNode l2 = new ListNode(369);

        ListNode listNode = lintcode002.addTwoNumbers(l1, l2);
        System.out.println(listNode.val);

    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode node = new ListNode(0);
        if (l1 == null && l2 == null) {
            return l1;
        }
        back(node, l1, l2);
        return node;
    }

    public void back(ListNode result, ListNode l1, ListNode l2) {
        if (l1 != null) {
            result.val += l1.val;
        } else {
            l1 = new ListNode(0);
        }
        if (l2 != null) {
            result.val += l2.val;
        } else {
            l2 = new ListNode(0);
        }
        ListNode node = new ListNode(0);
        if (result.val >= 10) {
            result.val = result.val % 10;
            node.val = 1;
            result.next = node;
        }
        if (l1.next != null || l2.next != null) {
            result.next = node;
            back(result.next, l1.next, l2.next);
        }
    }
}
