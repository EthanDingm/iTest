package com.ohygod.leetCodeTest;

/**
 * 给团队成员发LeetCoin
 */
public class Test02 {

    public static void main(String[] args) {
        int target = 9;
        //System.out.println(isPalindrome(target));
        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
        ListNode node = addTwoNumbers(l1, l2);
        StringBuilder builder = new StringBuilder(String.valueOf(node.val));
        ListNode cur =node;
        while (cur.next != null) {
            builder.append(cur.next.val);
            cur = cur.next;
        }
        System.out.println(builder.toString());


    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int a = parseIntValue(l1);
        int b = parseIntValue(l2);
        String strAfterAdd = String.valueOf(a + b);
        int length = strAfterAdd.length();
        Integer.parseInt(String.valueOf(strAfterAdd.charAt(length - 1)));
        ListNode node = new ListNode(Integer.parseInt(String.valueOf(strAfterAdd.charAt(length - 1))));
        ListNode tempNode = node;
        if (length == 1) return node;
        for (int i = length - 2; i >= 0; i--) {
            tempNode.next = new ListNode(Integer.parseInt(String.valueOf(strAfterAdd.charAt(i))));
            tempNode = tempNode.next;
        }
        return node;

    }

    private static int parseIntValue(ListNode node) {
        StringBuilder builder = new StringBuilder(String.valueOf(node.val));
        while (node.next != null) {
            builder.append(node.next.val);
            node = node.next;
        }
        return Integer.parseInt(builder.toString());
    }

    public static boolean isPalindrome(int x) {

        String str = String.valueOf(x);
        StringBuilder builder = new StringBuilder();

        for (int i = str.length() - 1; i >= 0; i--) {
            builder.append(str.charAt(i));
        }
        return str.equals(builder.toString());
    }
}