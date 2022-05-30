package leetcode;

//动态规划
//状态转移方程
//空间复杂度O(1)
public class p_198 {
    class Solution {
        public int rob(int[] nums) {
            int len = nums.length;
            if (len == 0) return 0;
            if (len == 1) return nums[0];
            int[] dp = new int[]{nums[0], Math.max(nums[0], nums[1])};
            for (int i = 2; i < len; i++) {
                dp[i % 2] = Math.max(dp[(i - 1) % 2], dp[i % 2] + nums[i]);
            }
            return Math.max(dp[0], dp[1]);
        }
    }
}
