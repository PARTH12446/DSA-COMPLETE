import java.util.Scanner;

/**
 * Jump Game I (LeetCode 55)
 * 
 * Problem Statement:
 * You are given an integer array nums. You are initially positioned at the first index,
 * and each element in the array represents your maximum jump length at that position.
 * Return true if you can reach the last index, otherwise return false.
 * 
 * Example 1:
 * Input: nums = [2,3,1,1,4]
 * Output: true
 * Explanation: Jump 1 step from index 0 to 1, then 3 steps to last index.
 * 
 * Example 2:
 * Input: nums = [3,2,1,0,4]
 * Output: false
 * Explanation: You will always stop at index 3 which has value 0.
 * 
 * Greedy Approach:
 * Track the farthest index we can reach (maxReach).
 * Iterate through array:
 * 1. If current index > maxReach, we cannot proceed → return false
 * 2. Update maxReach = max(maxReach, i + nums[i])
 * 3. If maxReach ≥ last index, return true
 * 
 * Time Complexity: O(n) - single pass
 * Space Complexity: O(1) - only constant extra space
 */
public class JumpGame {

    /**
     * Greedy solution for Jump Game I
     * 
     * @param nums Array where nums[i] = max jump length from position i
     * @return true if can reach last index, false otherwise
     */
    public boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        
        int n = nums.length;
        int maxReach = 0;  // Farthest index we can reach so far
        
        for (int i = 0; i < n; i++) {
            // If we can't reach current index, game over
            if (i > maxReach) {
                return false;
            }
            
            // Update farthest index we can reach from current position
            maxReach = Math.max(maxReach, i + nums[i]);
            
            // Optimization: if we can already reach last index, return true
            if (maxReach >= n - 1) {
                return true;
            }
        }
        
        return true;
    }
    
    /**
     * Alternative: Dynamic Programming solution (Jump Game I)
     * dp[i] = can we reach index i
     * Time: O(n²), Space: O(n) - less efficient than greedy
     */
    public boolean canJumpDP(int[] nums) {
        if (nums == null || nums.length == 0) return false;
        
        int n = nums.length;
        boolean[] dp = new boolean[n];
        dp[0] = true;  // Start at index 0
        
        for (int i = 0; i < n; i++) {
            if (!dp[i]) continue;  // Can't reach this index
            
            // Mark all reachable indices from i
            int maxJump = Math.min(i + nums[i], n - 1);
            for (int j = i + 1; j <= maxJump; j++) {
                dp[j] = true;
            }
            
            // Early exit if we can reach last index
            if (dp[n - 1]) return true;
        }
        
        return dp[n - 1];
    }
    
    /**
     * Jump Game II (LeetCode 45): Minimum number of jumps to reach last index
     * Greedy BFS-like approach
     */
    public int jumpGameII(int[] nums) {
        if (nums == null || nums.length <= 1) return 0;
        
        int n = nums.length;
        int jumps = 0;
        int currentEnd = 0;  // End of current jump range
        int farthest = 0;    // Farthest point reachable
        
        for (int i = 0; i < n - 1; i++) {
            // Update farthest point reachable
            farthest = Math.max(farthest, i + nums[i]);
            
            // If we've reached end of current jump range
            if (i == currentEnd) {
                jumps++;
                currentEnd = farthest;
                
                // If we can reach the end
                if (currentEnd >= n - 1) {
                    break;
                }
            }
        }
        
        return jumps;
    }
    
    /**
     * Visualization helper: Show step-by-step jumping process
     */
    public void visualizeJumpGame(int[] nums) {
        System.out.println("\n=== Visualizing Jump Game ===");
        System.out.print("Array: [");
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i]);
            if (i < nums.length - 1) System.out.print(", ");
        }
        System.out.println("]");
        System.out.println("Length: " + nums.length);
        System.out.println("Goal: Reach index " + (nums.length - 1));
        
        int maxReach = 0;
        System.out.println("\nStep-by-step analysis:");
        System.out.printf("%-10s %-10s %-15s %-20s\n", 
            "Index", "Value", "Max Reach", "Can Reach Index?");
        System.out.println("-".repeat(55));
        
        for (int i = 0; i < nums.length; i++) {
            boolean canReach = i <= maxReach;
            String reachStatus = canReach ? "✓ Yes" : "✗ No";
            
            System.out.printf("%-10d %-10d %-15d %-20s\n", 
                i, nums[i], maxReach, reachStatus);
            
            if (!canReach) {
                System.out.println("\n✗ FAILED: Cannot reach index " + i);
                System.out.println("Maximum reachable index was: " + maxReach);
                return;
            }
            
            // Update maxReach
            int newReach = i + nums[i];
            if (newReach > maxReach) {
                System.out.printf("  Update: maxReach = max(%d, %d+%d) = %d\n", 
                    maxReach, i, nums[i], newReach);
                maxReach = newReach;
            }
            
            // Check if we can reach the end
            if (maxReach >= nums.length - 1) {
                System.out.println("\n✓ SUCCESS: Can reach last index from index " + i);
                System.out.println("Maximum reachable index: " + maxReach);
                break;
            }
        }
        
        if (maxReach >= nums.length - 1) {
            System.out.println("\n✓ Final Result: Can reach last index!");
        } else {
            System.out.println("\n✗ Final Result: Cannot reach last index.");
        }
        
        // Visual path demonstration
        System.out.println("\n--- Possible Jump Paths ---");
        findAndShowPaths(nums, 0, new StringBuilder());
    }
    
    /**
     * Helper for finding example paths (DFS for visualization)
     */
    private void findAndShowPaths(int[] nums, int index, StringBuilder path) {
        if (index >= nums.length - 1) {
            System.out.println("Path: " + path.toString() + "→ End");
            return;
        }
        
        if (nums[index] == 0) {
            return;  // Dead end
        }
        
        // Try jumps of decreasing size (greedy-like)
        for (int jump = Math.min(nums[index], nums.length - 1 - index); jump >= 1; jump--) {
            int nextIndex = index + jump;
            if (nextIndex < nums.length) {
                StringBuilder newPath = new StringBuilder(path);
                if (path.length() > 0) newPath.append("→");
                newPath.append(index);
                findAndShowPaths(nums, nextIndex, newPath);
                // Just show first few paths for clarity
                if (nextIndex >= nums.length - 1) {
                    return;
                }
            }
        }
    }
    
    /**
     * Test cases and examples
     */
    public static void runTestCases() {
        JumpGame solver = new JumpGame();
        
        int[][] testCases = {
            {2, 3, 1, 1, 4},      // true - Example 1
            {3, 2, 1, 0, 4},      // false - Example 2
            {0},                   // true (already at last index)
            {0, 1},                // false (stuck at index 0)
            {1, 1, 1, 1, 1},      // true
            {2, 0, 0},             // true (jump directly to end)
            {1, 2, 3, 4, 5},      // true
            {5, 4, 3, 2, 1, 0, 0}, // false
            {1, 0, 1, 0},          // false
            {2, 5, 0, 0},          // true
            {1},                   // true (single element)
            {},                    // false (empty)
        };
        
        boolean[] expected = {
            true, false, true, false, true, true, true, false, false, true, true, false
        };
        
        System.out.println("=== Test Cases for Jump Game I ===");
        System.out.printf("%-25s %-10s %-10s %s\n", 
            "Input", "Expected", "Got", "Status");
        System.out.println("-".repeat(60));
        
        for (int i = 0; i < testCases.length; i++) {
            boolean result = solver.canJump(testCases[i]);
            boolean correct = (result == expected[i]);
            
            String inputStr = arrayToString(testCases[i]);
            System.out.printf("%-25s %-10s %-10s %s\n", 
                inputStr, expected[i], result, correct ? "✓" : "✗");
        }
        
        // Jump Game II test cases
        System.out.println("\n=== Test Cases for Jump Game II (Min Jumps) ===");
        int[][] jump2Tests = {
            {2, 3, 1, 1, 4},      // 2 jumps: 0→1→4
            {2, 3, 0, 1, 4},      // 2 jumps: 0→1→4
            {1, 1, 1, 1},         // 3 jumps: 0→1→2→3
            {4, 1, 1, 3, 1, 1},   // 2 jumps: 0→3→5
            {1},                   // 0 jumps (already at end)
            {2, 1},                // 1 jump: 0→1
        };
        
        int[] jump2Expected = {2, 2, 3, 2, 0, 1};
        
        System.out.printf("%-25s %-15s %-15s %s\n", 
            "Input", "Expected", "Got", "Status");
        System.out.println("-".repeat(60));
        
        for (int i = 0; i < jump2Tests.length; i++) {
            int result = solver.jumpGameII(jump2Tests[i]);
            boolean correct = (result == jump2Expected[i]);
            
            String inputStr = arrayToString(jump2Tests[i]);
            System.out.printf("%-25s %-15d %-15d %s\n", 
                inputStr, jump2Expected[i], result, correct ? "✓" : "✗");
        }
    }
    
    private static String arrayToString(int[] arr) {
        if (arr == null) return "null";
        if (arr.length == 0) return "[]";
        
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // Number of test cases
        
        while (t-- > 0) {
            int n = sc.nextInt();
            int[] nums = new int[n];
            
            for (int i = 0; i < n; i++) {
                nums[i] = sc.nextInt();
            }
            
            JumpGame solver = new JumpGame();
            boolean result = solver.canJump(nums);
            System.out.println(result ? "true" : "false");
            
            // Uncomment for visualization
            // solver.visualizeJumpGame(nums);
            
            // Uncomment for Jump Game II
            // int minJumps = solver.jumpGameII(nums);
            // System.out.println("Minimum jumps: " + minJumps);
        }
        
        sc.close();
        
        // Uncomment to run test cases
        // runTestCases();
    }
}