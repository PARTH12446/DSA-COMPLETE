/**
 * Problem: Aggressive Cows
 * 
 * Problem Statement:
 * Given N stalls located at positions on a straight line, and K cows.
 * Assign cows to stalls such that the minimum distance between any two cows is MAXIMIZED.
 * 
 * Constraints:
 * - Cows must be placed in stalls (not between stalls)
 * - Only one cow per stall
 * - We want to maximize the minimum distance between any two cows
 * 
 * Example:
 * Stalls: [1, 2, 4, 8, 9]
 * Cows: 3
 * 
 * Optimal arrangement: Place cows at positions 1, 4, 8 or 1, 4, 9
 * Minimum distance = min(4-1=3, 8-4=4) = 3 (or similar)
 * 
 * Approach:
 * 1. Sort the stalls (required for binary search approach)
 * 2. Binary search on the answer (minimum distance)
 * 3. For each candidate distance 'mid', check if we can place all K cows
 * 4. If yes, try larger distance (move right)
 * 5. If no, try smaller distance (move left)
 * 
 * Time Complexity: O(N log M) where M is range of distances
 * Space Complexity: O(1)
 */

import java.util.Arrays;

public class AggressiveCows {

    /**
     * Checks if we can place K cows with at least 'dist' minimum distance
     * 
     * @param stalls - Array of stall positions (sorted)
     * @param dist - Minimum distance to maintain between cows
     * @param cows - Number of cows to place
     * @return true if we can place all cows with given minimum distance, false otherwise
     * 
     * Greedy Strategy:
     * 1. Place first cow at first stall
     * 2. For each subsequent stall, place cow only if distance from last placed cow ≥ dist
     * 3. Stop when all cows are placed or no more stalls
     * 
     * Why greedy works?
     * - Placing cows as early as possible leaves more room for remaining cows
     * - If we skip a stall when we could place a cow, we might not have enough stalls later
     */
    private static boolean canPlaceCows(int[] stalls, int dist, int cows) {
        int cowsPlaced = 1; // First cow always at first stall
        int lastPosition = stalls[0]; // Position of last placed cow
        
        for (int i = 1; i < stalls.length; i++) {
            // Check if current stall is at least 'dist' away from last placed cow
            if (stalls[i] - lastPosition >= dist) {
                cowsPlaced++;           // Place a cow here
                lastPosition = stalls[i]; // Update last position
                
                // Early exit: if we've placed all cows
                if (cowsPlaced >= cows) {
                    return true;
                }
            }
            // Continue checking other stalls
        }
        
        return cowsPlaced >= cows;
    }
    
    /**
     * Alternative implementation with more detailed logging (for understanding)
     */
    private static boolean canPlaceCowsVerbose(int[] stalls, int dist, int cows) {
        System.out.println("\nTrying distance = " + dist);
        System.out.print("Stalls: ");
        for (int stall : stalls) System.out.print(stall + " ");
        System.out.println();
        
        int cowsPlaced = 1;
        int lastPosition = stalls[0];
        System.out.println("Cow 1 placed at stall " + lastPosition);
        
        for (int i = 1; i < stalls.length; i++) {
            int currentDist = stalls[i] - lastPosition;
            System.out.print("Stall " + stalls[i] + ": distance from last = " + currentDist);
            
            if (currentDist >= dist) {
                cowsPlaced++;
                lastPosition = stalls[i];
                System.out.println(" ✓ Place cow " + cowsPlaced + " here");
                
                if (cowsPlaced >= cows) {
                    System.out.println("SUCCESS: Placed all " + cows + " cows with min distance " + dist);
                    return true;
                }
            } else {
                System.out.println(" ✗ Too close, skip");
            }
        }
        
        System.out.println("FAILED: Could only place " + cowsPlaced + " cows with min distance " + dist);
        return false;
    }
    
    /**
     * Main function to find maximum minimum distance
     * 
     * @param stalls - Array of stall positions (may be unsorted)
     * @param k - Number of cows to place
     * @return Maximum possible minimum distance between any two cows
     * 
     * Binary Search Setup:
     * - low = 1 (minimum possible distance between adjacent stalls after sorting)
     * - high = max(stalls) - min(stalls) (maximum possible distance)
     * - mid = candidate minimum distance to check
     * 
     * Why binary search works?
     * - If we can place cows with distance D, we can definitely place them with distance < D
     * - If we cannot place cows with distance D, we cannot place them with distance > D
     * - This monotonic property allows binary search
     */
    public static int aggressiveCows(int[] stalls, int k) {
        // Edge cases
        if (stalls == null || stalls.length < k) {
            return 0; // Not enough stalls for all cows
        }
        
        // Sort stalls first (crucial step!)
        Arrays.sort(stalls);
        
        // Binary search bounds
        int low = 1; // Minimum possible distance
        int high = stalls[stalls.length - 1] - stalls[0]; // Maximum possible distance
        
        // Special case: if only 2 cows, maximum distance is simply max-min
        if (k == 2) {
            return high;
        }
        
        int result = 0; // Store the maximum feasible distance
        
        while (low <= high) {
            int mid = low + (high - low) / 2; // Candidate minimum distance
            
            if (canPlaceCows(stalls, mid, k)) {
                // We can place cows with this distance
                // Try for larger distance (move right)
                result = mid; // This is a valid solution
                low = mid + 1;
            } else {
                // Cannot place cows with this distance
                // Try smaller distance (move left)
                high = mid - 1;
            }
        }
        
        return result; // Could also return 'high'
    }
    
    /**
     * Alternative implementation returning 'high' as in original code
     */
    public static int aggressiveCowsAlt(int[] stalls, int k) {
        Arrays.sort(stalls);
        int low = 1;
        int high = stalls[stalls.length - 1] - stalls[0];
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (canPlaceCows(stalls, mid, k)) {
                // Can place cows with distance 'mid', try larger
                low = mid + 1;
            } else {
                // Cannot place cows with distance 'mid', try smaller
                high = mid - 1;
            }
        }
        
        // 'high' will be the maximum distance that worked
        return high;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Aggressive Cows Problem ===\n");
        
        // Test Case 1: Standard case
        int[] test1 = {1, 2, 4, 8, 9};
        int cows1 = 3;
        System.out.println("Test 1:");
        System.out.println("Stalls: " + Arrays.toString(test1));
        System.out.println("Cows: " + cows1);
        System.out.println("Maximum minimum distance: " + aggressiveCows(test1, cows1));
        System.out.println("Expected: 3 (or similar)\n");
        
        // Test Case 2: More cows than obvious positions
        int[] test2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int cows2 = 4;
        System.out.println("Test 2:");
        System.out.println("Stalls: " + Arrays.toString(test2));
        System.out.println("Cows: " + cows2);
        System.out.println("Maximum minimum distance: " + aggressiveCows(test2, cows2));
        System.out.println("Expected: 3 (positions 1, 4, 7, 10)\n");
        
        // Test Case 3: Unevenly spaced stalls
        int[] test3 = {0, 3, 4, 7, 10, 9};
        int cows3 = 4;
        System.out.println("Test 3:");
        System.out.println("Stalls: " + Arrays.toString(test3) + " (will be sorted)");
        Arrays.sort(test3);
        System.out.println("Sorted: " + Arrays.toString(test3));
        System.out.println("Cows: " + cows3);
        System.out.println("Maximum minimum distance: " + aggressiveCows(test3, cows3));
        System.out.println("Expected: 3 (positions 0, 3, 7, 10)\n");
        
        // Test Case 4: Minimum possible (all cows adjacent)
        int[] test4 = {1, 2, 3, 4, 5};
        int cows4 = 5;
        System.out.println("Test 4:");
        System.out.println("Stalls: " + Arrays.toString(test4));
        System.out.println("Cows: " + cows4);
        System.out.println("Maximum minimum distance: " + aggressiveCows(test4, cows4));
        System.out.println("Expected: 1\n");
        
        // Test Case 5: Maximum possible (2 cows at extremes)
        int[] test5 = {1, 100, 200, 300, 400};
        int cows5 = 2;
        System.out.println("Test 5:");
        System.out.println("Stalls: " + Arrays.toString(test5));
        System.out.println("Cows: " + cows5);
        System.out.println("Maximum minimum distance: " + aggressiveCows(test5, cows5));
        System.out.println("Expected: 399\n");
        
        // Test Case 6: Complex case
        int[] test6 = {1, 2, 8, 4, 9};
        int cows6 = 3;
        System.out.println("Test 6:");
        System.out.println("Stalls: " + Arrays.toString(test6) + " (unsorted)");
        System.out.println("Cows: " + cows6);
        int result6 = aggressiveCows(test6, cows6);
        System.out.println("Maximum minimum distance: " + result6);
        
        // Verify with verbose check
        Arrays.sort(test6);
        System.out.println("\nVerification for distance " + result6 + ":");
        canPlaceCowsVerbose(test6, result6, cows6);
        
        // Test Case 7: Edge case - not enough stalls
        int[] test7 = {1, 2, 3};
        int cows7 = 5;
        System.out.println("\nTest 7:");
        System.out.println("Stalls: " + Arrays.toString(test7));
        System.out.println("Cows: " + cows7);
        System.out.println("Maximum minimum distance: " + aggressiveCows(test7, cows7));
        System.out.println("Expected: 0 (not enough stalls)\n");
        
        // Performance test
        System.out.println("=== Performance Test ===");
        int size = 100000;
        int[] largeTest = new int[size];
        for (int i = 0; i < size; i++) {
            largeTest[i] = (int)(Math.random() * 1000000);
        }
        int largeCows = 1000;
        
        long startTime = System.currentTimeMillis();
        int result = aggressiveCows(largeTest, largeCows);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Large test: " + size + " stalls, " + largeCows + " cows");
        System.out.println("Result: " + result);
        System.out.println("Time: " + (endTime - startTime) + " ms");
    }
}

/**
 * PROOF OF CORRECTNESS:
 * 
 * 1. Why sorting is necessary?
 *    - We need to consider stalls in increasing order
 *    - Greedy algorithm relies on sorted positions
 *    
 * 2. Why greedy placement works?
 *    Lemma: If we can place K cows with minimum distance D, placing the first cow
 *    at the leftmost stall and subsequent cows as early as possible always works.
 *    
 *    Proof by contradiction:
 *    Assume optimal solution doesn't place cow at first stall when possible.
 *    We can always shift placements left without reducing minimum distance.
 *    
 * 3. Why binary search on answer works?
 *    Let f(d) = true if we can place cows with minimum distance ≥ d
 *    Property: If f(d) is true, then f(d') is true for all d' < d
 *    If f(d) is false, then f(d') is false for all d' > d
 *    This monotonic property allows binary search.
 *    
 * 4. Upper bound of search space:
 *    Maximum distance = max(stall) - min(stall)
 *    This occurs when placing only 2 cows at extremes.
 */

/**
 * VARIATIONS OF THE PROBLEM:
 * 
 * 1. Minimum maximum distance (split array into K segments to minimize max segment)
 * 2. Place K identical items in N bins with constraints
 * 3. Allocate tasks to workers minimizing maximum load
 * 
 * PATTERN RECOGNITION:
 * This is a "binary search on answer" + "greedy verification" pattern
 * Common in problems where we need to maximize/minimize something with constraints
 */

/**
 * VISUAL EXAMPLE:
 * 
 * Stalls: [1, 2, 4, 8, 9], K = 3
 * Sorted: [1, 2, 4, 8, 9]
 * 
 * Search space: low=1, high=8
 * 
 * mid=4: Can we place cows with min distance 4?
 *   - Place cow at 1
 *   - Next stall 2: distance=1 < 4 → skip
 *   - Stall 4: distance=3 < 4 → skip
 *   - Stall 8: distance=7 ≥ 4 → place cow
 *   - Stall 9: distance=1 < 4 → skip
 *   Only placed 2 cows → FAIL
 *   
 * mid=2: Can we place cows with min distance 2?
 *   - Place cow at 1
 *   - Stall 2: distance=1 < 2 → skip
 *   - Stall 4: distance=3 ≥ 2 → place cow
 *   - Stall 8: distance=4 ≥ 2 → place cow
 *   Placed 3 cows → SUCCESS
 *   
 * Continue binary search to find maximum...
 */