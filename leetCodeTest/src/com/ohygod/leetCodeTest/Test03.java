package com.ohygod.leetCodeTest;

/**
 * ���ظ��ַ�����Ӵ��ĳ���
 */
public class Test03 {
    public static void main(String[] args) {
        String s = "pwwkew";
        int result = lengthOfLongestSubstring(s);
        System.out.println(result);
    }

    private static int lengthOfLongestSubstring(String s) {
        //  ����¸��ַ��͵�ǰ�ַ���ͬ ������ظ�
        int length = s.length();
        int tempBegin = 0;
        String temp = "";
        int result = 0;
        for (int i = 0; i < length; i++) {
            temp = s.substring(tempBegin, i);
            for (int j = i + 1; j < length; j++) {
                char b = s.charAt(j);
                if (temp.contains(String.valueOf(b))) {
                    if (temp.length() > result) {
                        result = temp.length();
                    }
                    i = j;
                    tempBegin = j;
                    break;
                }
            }
        }
        return result;
    }
}
