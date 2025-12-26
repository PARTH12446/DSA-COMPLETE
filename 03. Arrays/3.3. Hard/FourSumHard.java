// Problem: LeetCode <ID>. <Title>
import java.util.*;

public class FourSumHard {
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        int n = nums.length;
        if (n < 4) return ans;
        Arrays.sort(nums);
        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            long minSum = (long) nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3];
            if (minSum > target) break;
            long maxSum = (long) nums[i] + nums[n - 3] + nums[n - 2] + nums[n - 1];
            if (maxSum < target) continue;
            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
                minSum = (long) nums[i] + nums[j] + nums[j + 1] + nums[j + 2];
                if (minSum > target) break;
                maxSum = (long) nums[i] + nums[j] + nums[n - 2] + nums[n - 1];
                if (maxSum < target) continue;
                int k = j + 1;
                int l = n - 1;
                while (k < l) {
                    long sum = (long) nums[i] + nums[j] + nums[k] + nums[l];
                    if (sum == target) {
                        ans.add(Arrays.asList(nums[i], nums[j], nums[k], nums[l]));
                        k++;
                        l--;
                        while (k < l && nums[k] == nums[k - 1]) k++;
                        while (k < l && nums[l] == nums[l + 1]) l--;
                    } else if (sum < target) {
                        k++;
                    } else {
                        l--;
                    }
                }
            }
        }
        return ans;
    }
    public static void main(String[] args) {
        int[] nums1 = {1, 0, -1, 0, -2, 2};
        int target1 = 0;
        System.out.println("Test 1: nums = [1,0,-1,0,-2,2], target = 0");
        System.out.println("Result: " + fourSum(nums1, target1));
        int[] nums2 = {0, 0, 0, 0};
        int target2 = 0;
        System.out.println("\nTest 2: nums = [0,0,0,0], target = 0");
        System.out.println("Result: " + fourSum(nums2, target2));
        int[] nums3 = {1, 2, 3, 4};
        int target3 = 10;
        System.out.println("\nTest 3: nums = [1,2,3,4], target = 10");
        System.out.println("Result: " + fourSum(nums3, target3));
        int[] nums4 = {1000000000, 1000000000, 1000000000, 1000000000};
        int target4 = -294967296;
        System.out.println("\nTest 4: Large numbers with potential overflow");
        System.out.println("Result: " + fourSum(nums4, target4));
        int[] nums5 = {2, 2, 2, 2, 2, 2, 2, 2};
        int target5 = 8;
        System.out.println("\nTest 5: All 2's, target = 8");
        System.out.println("Result: " + fourSum(nums5, target5));
        int[] nums6 = {-3, -2, -1, 0, 0, 1, 2, 3};
        int target6 = 0;
        System.out.println("\nTest 6: Mixed numbers, target = 0");
        System.out.println("Result: " + fourSum(nums6, target6));
    }
}
