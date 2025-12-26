import java.util.*;

public class HouseRobberLeet2 {

    // House Robber II: houses in a circle
    public static int rob(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        int[] arr1 = Arrays.copyOfRange(nums, 1, nums.length);      // exclude first
        int[] arr2 = Arrays.copyOfRange(nums, 0, nums.length - 1);  // exclude last
        return Math.max(robLine(arr1), robLine(arr2));
    }

    private static int robLine(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        int prev2 = 0;
        int prev1 = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int pick = nums[i];
            if (i > 1) pick += prev2;
            int notPick = prev1;
            int curr = Math.max(pick, notPick);
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }

    public static void main(String[] args) {
        int[] nums = {2,3,2};
        System.out.println("Max rob (II): " + rob(nums));
    }
}
