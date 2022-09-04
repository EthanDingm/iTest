package com.ohygod.leetCodeTest;

public class Test04 {
    public static void main(String[] args) {
        int[] nums = new int[]{-1, 0, 3, 5, 9, 12};
        int target = 9;
        int search = search(nums, target);
        System.out.println(search);
    }

    public static int search(int[] nums, int target) {
        int length = nums.length;

        if (length==1&& target==nums[0]){
            return 0;
        }
        int zhong = length%2==0?nums[length/2-1]:nums[length/2];
        if (target==nums[zhong]){
            return zhong;
        }else if (target>nums[zhong]){
            for (int i = zhong; i < nums.length; i++) {
                if (target == nums[i]) {
                    return i;
                }
            }
        }else{
            for (int i = 0; i < zhong; i++) {
                if (target == nums[i]) {
                    return i;
                }
            }
        }

        return -1;
    }
}
