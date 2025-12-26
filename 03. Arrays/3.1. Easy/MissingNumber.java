// Problem: LeetCode <ID>. <Title>
// Problem: Missing Number (LeetCode)
// Link: https://leetcode.com/problems/missing-number/
//
// Statement:
// Given an array nums containing n distinct numbers taken from the range [0, n],
// exactly one number from this range is missing in the array. You need to find
// and return that missing number.
//
// Example:
// nums = [3, 0, 1]  -> n = 3 (length of array)
// Numbers in range [0..3] are {0, 1, 2, 3}
// Missing number is 2.
//
// Approach (XOR trick):
// 1) XOR has a useful property: a ^ a = 0 and a ^ 0 = a.
// 2) If we XOR all numbers from 0..n and also XOR all elements of the array,
//    all numbers that appear in both sets will cancel out (become 0),
//    leaving only the missing number.
// 3) Instead of two separate loops, we combine them:
//      - For each index i from 0 to n-1, XOR with (i + 1) and nums[i].
//      - This effectively XORs all numbers from 1..n and all array values.
//      - Note that 0 is not added explicitly here, but since xor starts from 0,
//        that is equivalent to having already XORed with 0.
// 4) After the loop, xor will hold the missing number.
//
// Time Complexity: O(n)  - single pass through the array.
// Space Complexity: O(1) - only a few extra variables are used.
public class MissingNumber {

    public int missingNumber(int[] nums) {
        // xor will store the cumulative XOR of indices (1..n) and elements of nums
        int xor = 0;
        // n is the number of elements in the array
        int n = nums.length;
        // Iterate over all indices of the array
        for (int i = 0; i < n; i++) {
            // At each step we XOR:
            // 1) (i + 1): this represents numbers from 1 to n
            // 2) nums[i]: the current array element
            // Over the full loop this builds: (1 ^ 2 ^ ... ^ n) ^ (nums[0] ^ ... ^ nums[n-1])
            xor = xor ^ (i + 1) ^ nums[i];
        }
        // After the loop, all numbers that appear in both the range [0..n]
        // and in nums will cancel out. The remaining value is the missing number.
        return xor;
    }

    public static void main(String[] args) {
        // Create an instance of the class to call the non-static method missingNumber
        MissingNumber solver = new MissingNumber();
        // Example input array: contains numbers from [0..3] but is missing 2
        int[] nums = {3, 0, 1};

        // Call the method to compute the missing number
        int ans = solver.missingNumber(nums);

        // Print the result to the console
        System.out.println("Missing number = " + ans);  // Expected output: 2
    }
}

