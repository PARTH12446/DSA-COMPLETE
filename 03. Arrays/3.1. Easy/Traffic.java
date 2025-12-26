// Problem: LeetCode <ID>. <Title>
// Problem: Traffic (Longest subarray with at most m zeros)
// Goal: Find longest contiguous segment by flipping at most m zeros to ones

public class Traffic {

    /**
     * OPTIMAL SOLUTION: Sliding Window
     * Time Complexity: O(n) - Each element processed at most twice
     * Space Complexity: O(1) - Only pointers and counters
     * 
     * Steps:
     * 1. Initialize left=0, right=0, zerosCount=0, maxLen=0
     * 2. Expand right pointer: if 0, increment zerosCount
     * 3. While zerosCount > m: shrink from left
     * 4. Update maxLen with window size
     * 5. Return maxLen
     */
    public static int traffic(int n, int m, int[] vehicle) {
        int left = 0, right = 0;
        int zerosCount = 0;
        int maxLen = 0;
        
        while (right < n) {
            // Expand window from right
            if (vehicle[right] == 0) {
                zerosCount++;
            }
            
            // Shrink window if zeros exceed m
            while (zerosCount > m) {
                if (vehicle[left] == 0) {
                    zerosCount--;
                }
                left++;
            }
            
            // Update maximum length
            maxLen = Math.max(maxLen, right - left + 1);
            right++;
        }
        
        return maxLen;
    }
    
    /**
     * Alternative: Cleaner sliding window implementation
     */
    public static int trafficClean(int n, int m, int[] vehicle) {
        int maxLen = 0;
        int zeros = 0;
        int left = 0;
        
        for (int right = 0; right < n; right++) {
            if (vehicle[right] == 0) {
                zeros++;
            }
            
            while (zeros > m) {
                if (vehicle[left] == 0) {
                    zeros--;
                }
                left++;
            }
            
            maxLen = Math.max(maxLen, right - left + 1);
        }
        
        return maxLen;
    }

    /**
     * Example Walkthrough:
     * Input: vehicle = [1,0,0,1,1,0,1], m = 2
     * 
     * left=0, right=0: zeros=0, maxLen=1
     * right=1: zeros=1, maxLen=2
     * right=2: zeros=2, maxLen=3
     * right=3: zeros=2, maxLen=4
     * right=4: zeros=2, maxLen=5
     * right=5: zeros=3 → shrink: left=1, zeros=2, maxLen=5
     * right=6: zeros=2, maxLen=6
     * 
     * Result: 6 (flip zeros at indices 1,2 or 2,5)
     */

    public static void main(String[] args) {
        int[] vehicle = {1, 0, 0, 1, 1, 0, 1};
        int n = vehicle.length;
        int m = 2;
        int ans = traffic(n, m, vehicle);
        
        System.out.println("Max subarray with at most " + m + " zeros = " + ans); // 6
        
        // Test cases
        int[] test1 = {0, 0, 0, 0};
        System.out.println("All zeros, m=2: " + traffic(test1.length, 2, test1)); // 3
        
        int[] test2 = {1, 1, 1, 1};
        System.out.println("All ones, m=2: " + traffic(test2.length, 2, test2)); // 4
        
        int[] test3 = {0, 1, 1, 0, 1, 0, 1};
        System.out.println("Mixed, m=1: " + traffic(test3.length, 1, test3)); // 4
        
        int[] test4 = {1};
        System.out.println("Single one, m=1: " + traffic(test4.length, 1, test4)); // 1
        
        int[] test5 = {0};
        System.out.println("Single zero, m=0: " + traffic(test5.length, 0, test5)); // 0
    }
}
