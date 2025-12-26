import java.util.Scanner;

/**
 * Jump Game II (LeetCode 45)
 * 
 * Problem Statement:
 * Given an array of non-negative integers nums, you are initially positioned at the first index.
 * Each element in the array represents your maximum jump length at that position.
 * Your goal is to reach the last index in the minimum number of jumps.
 * You can assume that you can always reach the last index.
 * 
 * Example 1:
 * Input: nums = [2,3,1,1,4]
 * Output: 2
 * Explanation: Jump from index 0 → 1 (1 step), then from index 1 → 4 (3 steps)
 * 
 * Example 2:
 * Input: nums = [2,3,0,1,4]
 * Output: 2
 * Explanation: Same as above
 * 
 * Greedy BFS-like Approach:
 * Think of the problem as BFS layers:
 * - Layer 0: Only index 0 (starting point)
 * - Layer 1: All indices reachable from layer 0 with 1 jump
 * - Layer 2: All indices reachable from layer 1 with 1 jump
 * - etc.
 * 
 * Algorithm:
 * 1. Initialize: l = 0 (start of current layer), r = 0 (end of current layer), jumps = 0
 * 2. While we haven't reached last index:
 *    a. For each index in current layer [l, r], find farthest reachable index
 *    b. Move to next layer: l = r + 1, r = farthest
 *    c. jumps++
 * 3. Return jumps
 * 
 * Time Complexity: O(n) - each element visited at most twice
 * Space Complexity: O(1) - only constant extra space
 */
public class JumpGameII {

    /**
     * Greedy BFS-like solution for minimum jumps
     * 
     * @param nums Array where nums[i] = max jump length from position i
     * @return Minimum number of jumps to reach last index
     */
    public int jump(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0; // Already at last index or invalid
        }
        
        int n = nums.length;
        int jumps = 0;
        int left = 0;      // Start index of current "layer"
        int right = 0;     // End index of current "layer" (farthest reachable with current jumps)
        
        while (right < n - 1) {  // While we haven't reached last index
            int farthest = 0;     // Farthest index reachable from current layer
            
            // Explore all indices in current layer
            for (int i = left; i <= right; i++) {
                farthest = Math.max(farthest, i + nums[i]);
            }
            
            // Move to next layer
            left = right + 1;
            right = farthest;
            jumps++;
            
            // Early exit if we can already reach the end
            if (right >= n - 1) {
                break;
            }
        }
        
        return jumps;
    }
    
    /**
     * Alternative: Single-pass greedy (most common solution)
     * Track current end of jump and farthest reachable point
     */
    public int jumpSinglePass(int[] nums) {
        if (nums == null || nums.length <= 1) return 0;
        
        int n = nums.length;
        int jumps = 0;
        int currentEnd = 0;  // End of range reachable with current jumps
        int farthest = 0;    // Farthest point reachable
        
        for (int i = 0; i < n - 1; i++) {
            // Update farthest reachable point
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
     * Dynamic Programming solution (less efficient)
     * dp[i] = min jumps to reach index i
     * Time: O(n²), Space: O(n)
     */
    public int jumpDP(int[] nums) {
        if (nums == null || nums.length <= 1) return 0;
        
        int n = nums.length;
        int[] dp = new int[n];
        java.util.Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        
        for (int i = 0; i < n; i++) {
            if (dp[i] == Integer.MAX_VALUE) continue;
            
            int maxJump = Math.min(i + nums[i], n - 1);
            for (int j = i + 1; j <= maxJump; j++) {
                dp[j] = Math.min(dp[j], dp[i] + 1);
            }
            
            if (dp[n - 1] != Integer.MAX_VALUE) {
                return dp[n - 1];
            }
        }
        
        return dp[n - 1];
    }
    
    /**
     * Visualization helper: Show BFS layers
     */
    public void visualizeJumpLayers(int[] nums) {
        System.out.println("\n=== Visualizing Jump Game II (BFS Layers) ===");
        System.out.print("Array: [");
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i]);
            if (i < nums.length - 1) System.out.print(", ");
        }
        System.out.println("]");
        System.out.println("Goal: Reach index " + (nums.length - 1) + " with minimum jumps\n");
        
        if (nums.length <= 1) {
            System.out.println("Already at last index. Jumps needed: 0");
            return;
        }
        
        int jumps = 0;
        int left = 0;
        int right = 0;
        
        while (right < nums.length - 1) {
            System.out.println("=== Layer " + jumps + " ===");
            System.out.println("Indices in current layer: [" + left + " - " + right + "]");
            
            int farthest = 0;
            System.out.println("\nExploring from each index in current layer:");
            
            for (int i = left; i <= right; i++) {
                int reachable = i + nums[i];
                farthest = Math.max(farthest, reachable);
                System.out.printf("  Index %d (value %d): can reach up to index %d\n", 
                    i, nums[i], reachable);
            }
            
            System.out.println("\nFarthest reachable from this layer: " + farthest);
            
            // Move to next layer
            left = right + 1;
            right = Math.min(farthest, nums.length - 1);
            jumps++;
            
            System.out.println("Next layer will be: [" + left + " - " + right + "]");
            System.out.println("Total jumps so far: " + jumps);
            
            if (right >= nums.length - 1) {
                System.out.println("\n✓ Reached last index!");
                break;
            }
            
            // Visual BFS tree
            System.out.println("\nCurrent BFS tree:");
            printBFSTree(nums, jumps, left, right);
        }
        
        System.out.println("\n=== Result ===");
        System.out.println("Minimum jumps required: " + jumps);
        
        // Show optimal path
        System.out.println("\n--- One Optimal Path ---");
        findAndShowOptimalPath(nums);
    }
    
    /**
     * Helper to print BFS tree visualization
     */
    private void printBFSTree(int[] nums, int jumps, int left, int right) {
        System.out.println("Level 0: [0]");
        
        int currentLeft = 0;
        int currentRight = 0;
        
        for (int level = 0; level <= jumps; level++) {
            System.out.print("Level " + level + ": ");
            
            // Calculate reachable indices for this level
            int nextLeft = currentRight + 1;
            int nextRight = 0;
            
            for (int i = currentLeft; i <= currentRight && i < nums.length; i++) {
                nextRight = Math.max(nextRight, Math.min(i + nums[i], nums.length - 1));
            }
            
            // Print indices for this level
            for (int i = currentLeft; i <= currentRight && i < nums.length; i++) {
                System.out.print(i + " ");
            }
            
            System.out.println();
            
            if (nextRight >= nums.length - 1) break;
            
            currentLeft = nextLeft;
            currentRight = nextRight;
        }
    }
    
    /**
     * Find and display one optimal path (greedy: always jump to farthest)
     */
    private void findAndShowOptimalPath(int[] nums) {
        if (nums.length <= 1) return;
        
        StringBuilder path = new StringBuilder("0");
        int current = 0;
        int jumps = 0;
        
        while (current < nums.length - 1) {
            int maxReach = 0;
            int nextJump = current;
            
            // Find best next position (greedy: jump to farthest reachable)
            for (int i = 1; i <= nums[current]; i++) {
                int next = current + i;
                if (next >= nums.length - 1) {
                    nextJump = nums.length - 1;
                    break;
                }
                
                // Prefer positions that can jump farther
                if (next + nums[next] > maxReach) {
                    maxReach = next + nums[next];
                    nextJump = next;
                }
            }
            
            current = nextJump;
            path.append(" → ").append(current);
            jumps++;
            
            if (current >= nums.length - 1) break;
        }
        
        System.out.println("Path: " + path.toString());
        System.out.println("Jumps: " + jumps);
    }
    
    /**
     * Test cases and examples
     */
    public static void runTestCases() {
        JumpGameII solver = new JumpGameII();
        
        int[][] testCases = {
            {2, 3, 1, 1, 4},      // Expected: 2 (0→1→4)
            {2, 3, 0, 1, 4},      // Expected: 2 (0→1→4)
            {1, 1, 1, 1, 1},      // Expected: 4 (0→1→2→3→4)
            {4, 1, 1, 3, 1, 1},   // Expected: 2 (0→3→5)
            {1, 2, 3, 4, 5},      // Expected: 3 (0→1→3→4)
            {5, 4, 3, 2, 1, 0},   // Expected: 1 (0→5)
            {1},                   // Expected: 0 (already at end)
            {1, 1},                // Expected: 1 (0→1)
            {2, 1},                // Expected: 1 (0→1)
            {1, 2, 1, 1, 1},      // Expected: 3 (0→1→2→4)
            {7, 0, 9, 6, 9, 6, 1, 7, 9, 0, 1, 2, 9, 0, 3} // Expected: 2
        };
        
        int[] expected = {2, 2, 4, 2, 3, 1, 0, 1, 1, 3, 2};
        
        System.out.println("=== Test Cases for Jump Game II ===");
        System.out.printf("%-35s %-15s %-15s %s\n", 
            "Input", "Expected", "Got", "Status");
        System.out.println("-".repeat(80));
        
        for (int i = 0; i < testCases.length; i++) {
            int result = solver.jump(testCases[i]);
            boolean correct = (result == expected[i]);
            
            String inputStr = arrayToString(testCases[i]);
            System.out.printf("%-35s %-15d %-15d %s\n", 
                inputStr, expected[i], result, correct ? "✓" : "✗");
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
            
            JumpGameII solver = new JumpGameII();
            int result = solver.jump(nums);
            System.out.println(result);
            
            // Uncomment for visualization
            // solver.visualizeJumpLayers(nums);
            
            // Alternative solutions
            // int result2 = solver.jumpSinglePass(nums);
            // System.out.println("Single pass: " + result2);
            
            // int result3 = solver.jumpDP(nums);
            // System.out.println("DP: " + result3);
        }
        
        sc.close();
        
        // Uncomment to run test cases
        // runTestCases();
    }
}