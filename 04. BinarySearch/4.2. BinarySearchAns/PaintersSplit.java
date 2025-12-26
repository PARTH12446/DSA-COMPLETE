/**
 * Problem: Painter's Partition Problem
 * 
 * Problem Description:
 * Given an array 'boards' where boards[i] represents length of i-th board.
 * There are 'k' painters available.
 * Each painter takes 1 unit time to paint 1 unit of board.
 * 
 * Constraints:
 * - A painter will paint contiguous boards.
 * - Board cannot be painted by more than one painter.
 * - Painter will only paint contiguous boards.
 * 
 * Goal: Assign boards to painters such that the time to paint
 * all boards is minimized (minimize the maximum time any painter takes).
 * 
 * Example:
 * boards = [10, 20, 30, 40], k = 2
 * 
 * Possible partitions:
 * Painter 1: [10, 20, 30] = 60 units
 * Painter 2: [40] = 40 units
 * Maximum = 60
 * 
 * Painter 1: [10, 20] = 30
 * Painter 2: [30, 40] = 70
 * Maximum = 70
 * 
 * Painter 1: [10] = 10
 * Painter 2: [20, 30, 40] = 90
 * Maximum = 90
 * 
 * Minimum maximum = 60
 * 
 * Approach: Binary Search on Answer
 * 
 * Time Complexity: O(n log S) where S = sum of boards
 * Space Complexity: O(1)
 */

import java.util.Arrays;

public class PaintersSplit {

    /**
     * Checks if we can paint boards with given maximum time per painter
     * 
     * @param boards - Array of board lengths
     * @param maxTime - Maximum time allowed per painter
     * @return Number of painters needed with given constraint
     * 
     * Greedy Strategy:
     * 1. Give boards to current painter until adding next board exceeds maxTime
     * 2. Then assign that board to next painter
     * 3. Count total painters needed
     */
    private static int paintersRequired(int[] boards, int maxTime) {
        int painters = 1;  // At least one painter needed
        int currentTime = 0; // Time taken by current painter
        
        for (int board : boards) {
            // Check if adding this board exceeds max time for current painter
            if (currentTime + board > maxTime) {
                // Start new painter with this board
                painters++;
                currentTime = board;
            } else {
                // Add board to current painter
                currentTime += board;
            }
        }
        
        return painters;
    }
    
    /**
     * Enhanced check function with detailed logging
     */
    private static int paintersRequiredVerbose(int[] boards, int maxTime, int maxPainters) {
        System.out.printf("\n  Checking maxTime = %d:\n", maxTime);
        int painters = 1;
        int currentTime = 0;
        
        System.out.print("    Assignment: Painter 1: [");
        for (int i = 0; i < boards.length; i++) {
            if (currentTime + boards[i] > maxTime) {
                System.out.printf("] (%d units)", currentTime);
                painters++;
                currentTime = boards[i];
                System.out.printf("\n    Painter %d: [%d", painters, boards[i]);
                
                // Early exit optimization
                if (painters > maxPainters) {
                    System.out.println("...]");
                    System.out.printf("    Result: Need %d painters (exceeds %d)\n", painters, maxPainters);
                    return painters;
                }
            } else {
                currentTime += boards[i];
                if (i > 0 && currentTime != boards[i]) {
                    System.out.print(", " + boards[i]);
                }
            }
        }
        System.out.printf("] (%d units)\n", currentTime);
        System.out.printf("    Result: Need %d painters\n", painters);
        
        return painters;
    }
    
    /**
     * Finds the minimum possible maximum time any painter takes
     * 
     * @param boards - Array of board lengths
     * @param k - Number of painters available
     * @return Minimum possible maximum time, or -1 if impossible
     * 
     * Binary Search Bounds:
     * - low = max(boards): A painter must paint at least one complete board
     * - high = sum(boards): If only one painter, they paint all boards
     * 
     * Monotonic Property:
     * Let f(T) = painters needed if maximum time per painter = T
     * - If T increases → f(T) decreases (monotonically decreasing)
     * - If T decreases → f(T) increases
     */
    public static int findLargestMinDistance(int[] boards, int k) {
        // Edge cases
        if (boards == null || boards.length == 0) {
            return 0;
        }
        
        // If more painters than boards, some painters won't work
        // but allocation is still possible
        if (k > boards.length) {
            // Can assign each painter at most one board
            // So maximum time = maximum board length
            int maxBoard = 0;
            for (int board : boards) maxBoard = Math.max(maxBoard, board);
            return maxBoard;
        }
        
        // If only one painter, they paint all boards
        if (k == 1) {
            int sum = 0;
            for (int board : boards) sum += board;
            return sum;
        }
        
        // If painters equal boards, each paints one board
        if (k == boards.length) {
            int maxBoard = 0;
            for (int board : boards) maxBoard = Math.max(maxBoard, board);
            return maxBoard;
        }
        
        // Calculate search bounds
        int low = 0;     // Will be maximum board length
        int high = 0;    // Will be sum of all boards
        
        for (int board : boards) {
            low = Math.max(low, board);
            high += board;
        }
        
        System.out.println("Initial setup:");
        System.out.println("  Boards: " + Arrays.toString(boards));
        System.out.println("  Painters: " + k);
        System.out.println("  Low (max board): " + low);
        System.out.println("  High (sum): " + high);
        System.out.println();
        
        int result = high; // Worst case: all boards to one painter
        
        // Binary search for minimum maximum time
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            int paintersNeeded = paintersRequired(boards, mid);
            System.out.printf("  Trying maxTime = %d → need %d painters", mid, paintersNeeded);
            
            if (paintersNeeded > k) {
                System.out.println(" (too many painters, increase time)");
                // Need more painters than available
                // Current maxTime is too small, increase it
                low = mid + 1;
            } else {
                System.out.println(" (feasible, try smaller time)");
                // Can paint with k or fewer painters
                // This is a valid solution, try to minimize further
                result = Math.min(result, mid);
                high = mid - 1;
            }
        }
        
        return low; // At the end, low is the minimum feasible maxTime
    }
    
    /**
     * Alternative implementation with clearer variable names
     */
    public static int painterPartition(int[] boards, int painters) {
        if (boards == null || boards.length == 0 || painters <= 0) {
            return 0;
        }
        
        int n = boards.length;
        
        // Calculate bounds for binary search
        int maxBoard = 0;
        int totalLength = 0;
        
        for (int board : boards) {
            maxBoard = Math.max(maxBoard, board);
            totalLength += board;
        }
        
        // If more painters than boards
        if (painters >= n) {
            return maxBoard;
        }
        
        // Binary search
        int left = maxBoard;
        int right = totalLength;
        int answer = totalLength;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (canPaint(boards, painters, mid)) {
                // Can paint with this max time
                answer = Math.min(answer, mid);
                right = mid - 1; // Try smaller time
            } else {
                // Cannot paint, need larger time
                left = mid + 1;
            }
        }
        
        return answer;
    }
    
    /**
     * Checks if painting is possible with given constraints
     */
    private static boolean canPaint(int[] boards, int maxPainters, int maxTime) {
        int paintersUsed = 1;
        int currentTime = 0;
        
        for (int board : boards) {
            if (currentTime + board <= maxTime) {
                currentTime += board;
            } else {
                paintersUsed++;
                currentTime = board;
                
                // Early exit if already exceeded max painters
                if (paintersUsed > maxPainters) {
                    return false;
                }
            }
        }
        
        return paintersUsed <= maxPainters;
    }
    
    /**
     * Dynamic Programming approach (for comparison)
     * Time: O(k * n^2), Space: O(k * n)
     */
    public static int painterPartitionDP(int[] boards, int k) {
        int n = boards.length;
        
        // prefix sum array
        int[] prefix = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            prefix[i] = prefix[i - 1] + boards[i - 1];
        }
        
        // dp[i][j] = minimum maximum time to paint first i boards with j painters
        int[][] dp = new int[n + 1][k + 1];
        
        // Base cases
        for (int i = 1; i <= n; i++) {
            dp[i][1] = prefix[i]; // One painter paints all
        }
        
        for (int j = 1; j <= k; j++) {
            dp[1][j] = boards[0]; // One board with any number of painters
        }
        
        // Fill DP table
        for (int i = 2; i <= n; i++) {
            for (int j = 2; j <= k; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                for (int p = 1; p <= i; p++) {
                    int cost = Math.max(dp[p][j - 1], prefix[i] - prefix[p]);
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }
        
        return dp[n][k];
    }
    
    public static void main(String[] args) {
        System.out.println("=== Painter's Partition Problem ===\n");
        
        // Test Case 1: Standard example
        System.out.println("Test Case 1:");
        int[] boards1 = {10, 20, 30, 40};
        int painters1 = 2;
        System.out.println("Boards: " + Arrays.toString(boards1));
        System.out.println("Painters: " + painters1);
        
        int result1 = findLargestMinDistance(boards1, painters1);
        int dpResult1 = painterPartitionDP(boards1, painters1);
        
        System.out.println("\nBinary Search Result: " + result1);
        System.out.println("DP Result: " + dpResult1);
        System.out.println("Expected: 60");
        System.out.println("Match: " + (result1 == 60 ? "✓" : "✗"));
        System.out.println("DP matches: " + (result1 == dpResult1 ? "✓" : "✗"));
        
        // Show optimal assignment
        System.out.println("\nOptimal assignment for maxTime = " + result1 + ":");
        paintersRequiredVerbose(boards1, result1, painters1);
        
        // Test Case 2: More painters than needed
        System.out.println("\n\nTest Case 2:");
        int[] boards2 = {10, 20, 30, 40, 50};
        int painters2 = 5;
        System.out.println("Boards: " + Arrays.toString(boards2));
        System.out.println("Painters: " + painters2);
        
        int result2 = findLargestMinDistance(boards2, painters2);
        System.out.println("\nResult: " + result2);
        System.out.println("Expected: 50 (each painter gets one board, max board = 50)");
        System.out.println("Match: " + (result2 == 50 ? "✓" : "✗"));
        
        // Test Case 3: Single painter
        System.out.println("\n\nTest Case 3:");
        int[] boards3 = {10, 20, 30};
        int painters3 = 1;
        System.out.println("Boards: " + Arrays.toString(boards3));
        System.out.println("Painters: " + painters3);
        
        int result3 = findLargestMinDistance(boards3, painters3);
        System.out.println("\nResult: " + result3);
        System.out.println("Expected: 60 (sum of all boards)");
        System.out.println("Match: " + (result3 == 60 ? "✓" : "✗"));
        
        // Test Case 4: Painters equal to boards
        System.out.println("\n\nTest Case 4:");
        int[] boards4 = {7, 3, 9, 12, 5};
        int painters4 = 5;
        System.out.println("Boards: " + Arrays.toString(boards4));
        System.out.println("Painters: " + painters4);
        
        int result4 = findLargestMinDistance(boards4, painters4);
        System.out.println("\nResult: " + result4);
        System.out.println("Expected: 12 (max board length)");
        System.out.println("Match: " + (result4 == 12 ? "✓" : "✗"));
        
        // Test Case 5: Complex case
        System.out.println("\n\nTest Case 5:");
        int[] boards5 = {100, 200, 300, 400, 500, 600, 700, 800, 900};
        int painters5 = 3;
        System.out.println("Boards: " + Arrays.toString(boards5));
        System.out.println("Painters: " + painters5);
        
        int result5 = findLargestMinDistance(boards5, painters5);
        System.out.println("\nResult: " + result5);
        System.out.println("Expected: 1700 (optimal partition)");
        
        // Verify with DP for accuracy
        int dpResult5 = painterPartitionDP(boards5, painters5);
        System.out.println("DP Result: " + dpResult5);
        System.out.println("Binary Search matches DP: " + (result5 == dpResult5 ? "✓" : "✗"));
        
        // Test Case 6: Large board at end
        System.out.println("\n\nTest Case 6:");
        int[] boards6 = {1, 2, 3, 100};
        int painters6 = 2;
        System.out.println("Boards: " + Arrays.toString(boards6));
        System.out.println("Painters: " + painters6);
        
        int result6 = findLargestMinDistance(boards6, painters6);
        System.out.println("\nResult: " + result6);
        System.out.println("Expected: 100 ([1,2,3]=6 and [100]=100)");
        System.out.println("Match: " + (result6 == 100 ? "✓" : "✗"));
        
        // Test Case 7: All boards same length
        System.out.println("\n\nTest Case 7:");
        int[] boards7 = {5, 5, 5, 5, 5};
        int painters7 = 2;
        System.out.println("Boards: " + Arrays.toString(boards7));
        System.out.println("Painters: " + painters7);
        
        int result7 = findLargestMinDistance(boards7, painters7);
        System.out.println("\nResult: " + result7);
        System.out.println("Expected: 15 (3 boards to one painter = 15, 2 boards to other = 10)");
        System.out.println("Match: " + (result7 == 15 ? "✓" : "✗"));
        
        // Performance comparison
        System.out.println("\n=== Performance Comparison ===");
        int[] largeBoards = new int[10000];
        for (int i = 0; i < largeBoards.length; i++) {
            largeBoards[i] = (int)(Math.random() * 100) + 1;
        }
        int largePainters = 100;
        
        // Binary Search
        long startTime = System.currentTimeMillis();
        int binaryResult = findLargestMinDistance(largeBoards, largePainters);
        long binaryTime = System.currentTimeMillis() - startTime;
        
        // DP (will be very slow for large n)
        if (largeBoards.length <= 100) { // Limit size for DP
            startTime = System.currentTimeMillis();
            int dpResult = painterPartitionDP(largeBoards, largePainters);
            long dpTime = System.currentTimeMillis() - startTime;
            
            System.out.println("Array size: " + largeBoards.length);
            System.out.println("Painters: " + largePainters);
            System.out.println("Binary Search: " + binaryResult + " (" + binaryTime + "ms)");
            System.out.println("DP: " + dpResult + " (" + dpTime + "ms)");
            System.out.println("Results match: " + (binaryResult == dpResult ? "✓" : "✗"));
            System.out.println("Binary Search is " + (dpTime/binaryTime) + "x faster");
        } else {
            System.out.println("Array size: " + largeBoards.length + " (too large for DP)");
            System.out.println("Binary Search: " + binaryResult + " (" + binaryTime + "ms)");
        }
    }
}

/**
 * MATHEMATICAL FORMULATION:
 * 
 * Let:
 * - B = [b₁, b₂, ..., bₙ] be board lengths
 * - k = number of painters
 * 
 * We want to partition B into k contiguous segments
 * to minimize: max(sum(segment₁), sum(segment₂), ..., sum(segmentₖ))
 * 
 * This is exactly the same as:
 * - Book Allocation Problem
 * - Split Array Largest Sum
 * - Capacity To Ship Packages
 */

/**
 * PROOF OF CORRECTNESS:
 * 
 * 1. Binary Search Bounds:
 *    - Lower bound: max(boards) → A painter must paint at least one board
 *    - Upper bound: sum(boards) → All boards to one painter
 *    
 * 2. Greedy Assignment is Optimal:
 *    Given maxTime = T, greedy minimizes painter count.
 *    Proof: If any assignment uses ≤ k painters, greedy will find it.
 *    
 * 3. Monotonic Property:
 *    Let f(T) = painters needed with max time T
 *    If T₁ < T₂, then f(T₁) ≥ f(T₂) (more time → fewer painters needed)
 */

/**
 * TIME COMPLEXITY ANALYSIS:
 * 
 * Let n = number of boards, S = sum of boards
 * 
 * Binary Search Approach:
 * - Binary search iterations: O(log(S - max))
 * - Each validation: O(n)
 * - Total: O(n log S)
 * 
 * Dynamic Programming:
 * - O(k * n^2) time
 * - O(k * n) space
 * 
 * For large n, binary search is much more efficient.
 */

/**
 * VISUAL EXAMPLE:
 * 
 * boards = [10, 20, 30, 40], k = 2
 * 
 * Search space: low = 40, high = 100
 * 
 * mid = 70: Can paint with max 70 units per painter?
 *   Painter 1: 10 + 20 + 30 = 60 ≤ 70
 *   Painter 2: 40 ≤ 70
 *   Need 2 painters ≤ 2 → SUCCESS, try smaller
 *   
 * mid = 55: Can paint with max 55 units per painter?
 *   Painter 1: 10 + 20 + 30 = 60 > 55 → split
 *   Painter 1: 10 + 20 = 30 ≤ 55
 *   Painter 2: 30 ≤ 55
 *   Painter 3: 40 ≤ 55
 *   Need 3 painters > 2 → FAIL, try larger
 *   
 * Continue binary search...
 * Final answer: 60
 */

/**
 * RELATED PROBLEMS:
 * 
 * 1. Book Allocation: Exactly the same problem
 * 2. Split Array Largest Sum: Same concept
 * 3. Capacity To Ship Packages: Similar with days constraint
 * 4. Koko Eating Bananas: Similar optimization
 * 5. Aggressive Cows: Maximize minimum distance
 * 
 * All follow "binary search on answer + greedy validation" pattern.
 */

/**
 * PRACTICAL APPLICATIONS:
 * 
 * 1. Task Scheduling: Assign tasks to workers minimizing max load
 * 2. Data Partitioning: Split data across servers
 * 3. Manufacturing: Divide work among machines
 * 4. Construction: Assign work to crews
 * 5. Cloud Computing: Distribute workloads
 */

/**
 * OPTIMIZATION TIPS:
 * 
 * 1. Early Exit in Validation:
 *    Stop if painters needed > k
 * 
 * 2. Use long for Sums:
 *    Prevent integer overflow with large boards
 * 
 * 3. Precompute Prefix Sums:
 *    For faster range sum queries if needed
 * 
 * 4. For Very Large k:
 *    Return max(board) immediately
 */

/**
 * LEETCODE VERSION:
 * If this were on LeetCode, similar to:
 * - 410. Split Array Largest Sum (Exactly the same)
 * - 1011. Capacity To Ship Packages Within D Days (Similar)
 * - 875. Koko Eating Bananas (Similar pattern)
 */