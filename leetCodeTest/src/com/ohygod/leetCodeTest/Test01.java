package com.ohygod.leetCodeTest;

import java.util.HashMap;
import java.util.Map;

public class Test01 {

    public static void main(String[] args) throws Exception {
        twoSum(null, 2);
    }

    public static int[] twoSum(int[] nums, int target) throws Exception {
        Map  map = new HashMap();
        for (int i = 0; i < nums.length; i++) {

                  if (map.containsKey(target-nums[i])){
                      return new int[]{(int) map.get(target-nums[i]),i};
                  }
        }
        throw new Exception("exception");
    }
}
