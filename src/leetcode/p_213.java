package leetcode;

/**
 * 本题可以看成时p198的升级版。依旧使用动态规划
 * 环形意味着首尾两家不能同时偷窃，要么偷o~len-1,要么偷1~len。
 * 所以我改写了helper方法，helper方法可以求出某个范围内的最优值
 * 但是要注意一些边界条件：
 * 1）、构造dp[]时不是使用nums[]的start和start+1，而是使用start与Math.max(nums[start],nums[start+1])
 * 2）、还有这个dp[]的覆盖，此处要分情况讨论：
 *  2.1）、当start为奇数时，dp[(i-1)%2]=Math.max(dp[(i-1)%2]+nums[i],dp[i%2])
 *  2.2）、当start为偶数时，dp[i%2]=Math.max(dp[i%2]+nums[i],dp[(i-1)%2])
 * 3）、元素个数必须大于2
 */
public class p_213 {
    class Solution {
        public int rob(int[] nums) {

            int len = nums.length;
            if (len == 0) return 0;
            if (len == 1) return nums[0];
            if (len == 2) return Math.max(nums[0], nums[1]);

            return Math.max(helper(nums, 0, len - 1), helper(nums, 1, len));
        }

        /**
         * 计算在某一区间内，小偷可以偷到的金额
         *
         * @param nums  每个房屋的金额
         * @param start 开始界限(包括)
         * @param end   结束界限(不包括)
         * @return 在[start, end)中，小偷能偷到的最大金额
         */
        private int helper(int[] nums, int start, int end) {
            int[] dp = new int[]{nums[start], Math.max(nums[start], nums[start + 1])};
            for (int i = start + 2; i < end; i++) {
                if (start % 2 == 0) {
                    dp[i % 2] = Math.max(dp[(i + 1) % 2], dp[i % 2] + nums[i]);
                } else {
                    dp[(i + 1) % 2] = Math.max(dp[i % 2], dp[(i + 1) % 2] + nums[i]);
                }
            }
            return Math.max(dp[0], dp[1]);
        }
    }
// @solution-sync:end
}
