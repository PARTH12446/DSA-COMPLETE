/**
 * Problem: Rose Garden / Minimum Days to Make M Bouquets
 * 
 * Problem Description:
 * You are given an integer array 'bloomDay' where bloomDay[i] is the day
 * when the i-th flower will be in full bloom.
 * You are also given integers 'm' (number of bouquets needed) and 
 * 'k' (number of adjacent flowers required for each bouquet).
 * 
 * A bouquet can only be made from flowers that have bloomed on or before
 * a certain day, and they must be adjacent in the garden.
 * 
 * Return the minimum number of days you need to wait to be able to make
 * 'm' bouquets from the garden. If it's impossible, return -1.
 * 
 * Example 1:
 * Input: bloomDay = [1,10,3,10,2], m = 3, k = 1
 * Output: 3
 * Explanation: After day 3, flowers at indices [0,2,4] have bloomed.
 *              With k=1, each flower makes a bouquet, so we get 3 bouquets.
 * 
 * Example 2:
 * Input: bloomDay = [1,10,3,10,2], m = 3, k = 2
 * Output: -1
 * Explanation: We need 3 bouquets of 2 flowers each = 6 flowers total.
 *              But we only have 5 flowers total, so impossible.
 * 
 * Approach: Binary Search on Answer
 * 
 * Time Complexity: O(n log(max(bloomDay) - min(bloomDay)))
 * Space Complexity: O(1)
 */

import java.util.Arrays;

public class RoseGarden {

    /**
     * Checks if we can make m bouquets by a given day
     * 
     * @param bloomDay - Array of bloom days for each flower
     * @param day - Candidate day to check
     * @param m - Number of bouquets needed
     * @param k - Number of adjacent flowers per bouquet
     * @return true if possible to make m bouquets by 'day', false otherwise
     * 
     * Greedy Strategy:
     * 1. Scan through the flower array
     * 2. Count consecutive flowers that have bloomed by 'day'
     * 3. Whenever we have k consecutive bloomed flowers, make a bouquet
     * 4. Reset count after making a bouquet (can't reuse flowers)
     * 5. Check if we've made at least m bouquets
     */
    private static boolean canMakeBouquets(int[] bloomDay, int day, int m, int k) {
        int bouquetsMade = 0;
        int consecutiveBloom = 0;
        
        for (int bloom : bloomDay) {
            if (bloom <= day) {
                // Flower has bloomed by this day
                consecutiveBloom++;
                
                // If we have enough consecutive bloomed flowers for a bouquet
                if (consecutiveBloom == k) {
                    bouquetsMade++;
                    consecutiveBloom = 0; // Reset for next bouquet
                    
                    // Early exit: if we've made enough bouquets
                    if (bouquetsMade >= m) {
                        return true;
                    }
                }
            } else {
                // Flower hasn't bloomed yet, break the consecutive streak
                consecutiveBloom = 0;
            }
        }
        
        return bouquetsMade >= m;
    }
    
    /**
     * Enhanced version with detailed logging
     */
    private static boolean canMakeBouquetsVerbose(int[] bloomDay, int day, int m, int k) {
        System.out.printf("\n  Checking day %d:\n", day);
        System.out.print("  Bloom status: [");
        for (int i = 0; i < bloomDay.length; i++) {
            System.out.print(bloomDay[i] <= day ? "✓" : "✗");
            if (i < bloomDay.length - 1) System.out.print(", ");
        }
        System.out.println("]");
        
        int bouquetsMade = 0;
        int consecutiveBloom = 0;
        int flowerCount = 0;
        
        System.out.print("  Bouquet making: ");
        for (int i = 0; i < bloomDay.length; i++) {
            if (bloomDay[i] <= day) {
                consecutiveBloom++;
                flowerCount++;
                System.out.print("F" + (i+1) + " ");
                
                if (consecutiveBloom == k) {
                    bouquetsMade++;
                    System.out.printf("[Bouquet %d] ", bouquetsMade);
                    consecutiveBloom = 0;
                    
                    if (bouquetsMade >= m) {
                        System.out.printf("\n  Made %d bouquets (need %d) → SUCCESS\n", bouquetsMade, m);
                        return true;
                    }
                }
            } else {
                if (consecutiveBloom > 0) {
                    System.out.print("| ");
                }
                consecutiveBloom = 0;
            }
        }
        
        System.out.printf("\n  Made %d bouquets (need %d) → FAIL\n", bouquetsMade, m);
        return false;
    }
    
    /**
     * Finds minimum days to make m bouquets
     * 
     * @param arr - Bloom days for each flower
     * @param k - Flowers needed per bouquet (must be adjacent)
     * @param m - Number of bouquets needed
     * @return Minimum days needed, or -1 if impossible
     * 
     * Binary Search Setup:
     * - low = min(bloomDay): Minimum possible day
     * - high = max(bloomDay): Maximum needed day
     * 
     * Monotonic Property:
     * Let f(day) = can make m bouquets by this day
     * - If f(day) is true, then f(day') is true for all day' > day
     * - If f(day) is false, then f(day') is false for all day' < day
     */
    public static int roseGarden(int[] arr, int k, int m) {
        // Edge case: not enough flowers total
        long totalFlowersNeeded = (long) m * k;
        if (totalFlowersNeeded > arr.length) {
            return -1;
        }
        
        // Edge case: no bouquets needed
        if (m == 0) {
            return 0;
        }
        
        // Find min and max bloom days for search bounds
        int minDay = Integer.MAX_VALUE;
        int maxDay = Integer.MIN_VALUE;
        
        for (int day : arr) {
            minDay = Math.min(minDay, day);
            maxDay = Math.max(maxDay, day);
        }
        
        System.out.println("Finding minimum days to make " + m + " bouquets");
        System.out.println("Flowers per bouquet: " + k);
        System.out.println("Bloom days: " + Arrays.toString(arr));
        System.out.println("Total flowers: " + arr.length);
        System.out.println("Flowers needed: " + totalFlowersNeeded);
        System.out.println("Search range: [" + minDay + ", " + maxDay + "]");
        
        // Special case: if we need all flowers immediately
        if (totalFlowersNeeded == arr.length) {
            // All flowers must bloom, so we need max bloom day
            return maxDay;
        }
        
        int low = minDay;
        int high = maxDay;
        int result = -1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            System.out.printf("\nTrying day %d (range [%d, %d]):", mid, low, high);
            
            if (canMakeBouquets(arr, mid, m, k)) {
                System.out.println(" ✓ Can make bouquets");
                // Can make bouquets by this day, try earlier
                result = mid;
                high = mid - 1;
            } else {
                System.out.println(" ✗ Cannot make bouquets");
                // Cannot make bouquets by this day, need later
                low = mid + 1;
            }
        }
        
        return result;
    }
    
    /**
     * Alternative implementation with clearer variable names
     */
    public static int minDaysForBouquets(int[] bloomDay, int m, int k) {
        // Quick impossibility check
        if ((long) m * k > bloomDay.length) {
            return -1;
        }
        
        if (m == 0) return 0;
        
        // Find bounds for binary search
        int minBloom = Integer.MAX_VALUE;
        int maxBloom = Integer.MIN_VALUE;
        
        for (int day : bloomDay) {
            minBloom = Math.min(minBloom, day);
            maxBloom = Math.max(maxBloom, day);
        }
        
        // Binary search
        int left = minBloom;
        int right = maxBloom;
        int answer = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (canMakeBouquetsOptimized(bloomDay, m, k, mid)) {
                answer = mid;
                right = mid - 1; // Try smaller day
            } else {
                left = mid + 1; // Need larger day
            }
        }
        
        return answer;
    }
    
    /**
     * Optimized validation with early exit
     */
    private static boolean canMakeBouquetsOptimized(int[] bloomDay, int m, int k, int day) {
        int bouquets = 0;
        int consecutive = 0;
        
        for (int bloom : bloomDay) {
            if (bloom <= day) {
                consecutive++;
                if (consecutive == k) {
                    bouquets++;
                    consecutive = 0;
                    
                    // Early exit if we've made enough bouquets
                    if (bouquets >= m) {
                        return true;
                    }
                }
            } else {
                consecutive = 0;
            }
        }
        
        return bouquets >= m;
    }
    
    /**
     * Linear search for comparison (slow but simple)
     */
    public static int minDaysLinear(int[] bloomDay, int m, int k) {
        if ((long) m * k > bloomDay.length) return -1;
        if (m == 0) return 0;
        
        int maxDay = 0;
        for (int day : bloomDay) maxDay = Math.max(maxDay, day);
        
        for (int day = 1; day <= maxDay; day++) {
            if (canMakeBouquets(bloomDay, day, m, k)) {
                return day;
            }
        }
        
        return -1;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Rose Garden (Minimum Days to Make M Bouquets) ===\n");
        
        // Test Case 1: Example from problem
        System.out.println("Test Case 1:");
        int[] bloom1 = {1, 10, 3, 10, 2};
        int m1 = 3, k1 = 1;
        System.out.println("Bloom days: " + Arrays.toString(bloom1));
        System.out.println("Bouquets needed: " + m1);
        System.out.println("Flowers per bouquet: " + k1);
        
        int result1 = roseGarden(bloom1, k1, m1);
        int linear1 = minDaysLinear(bloom1, m1, k1);
        
        System.out.println("\nBinary Search Result: " + result1);
        System.out.println("Linear Search Result: " + linear1);
        System.out.println("Expected: 3");
        System.out.println("Match: " + (result1 == 3 ? "✓" : "✗"));
        System.out.println("Results match: " + (result1 == linear1 ? "✓" : "✗"));
        
        // Show detailed analysis for day 3
        System.out.println("\nDetailed analysis for day 3:");
        canMakeBouquetsVerbose(bloom1, 3, m1, k1);
        
        // Test Case 2: Impossible case
        System.out.println("\n\nTest Case 2:");
        int[] bloom2 = {1, 10, 3, 10, 2};
        int m2 = 3, k2 = 2;
        System.out.println("Bloom days: " + Arrays.toString(bloom2));
        System.out.println("Bouquets needed: " + m2);
        System.out.println("Flowers per bouquet: " + k2);
        
        int result2 = roseGarden(bloom2, k2, m2);
        System.out.println("\nResult: " + result2);
        System.out.println("Expected: -1 (need 6 flowers but only have 5)");
        System.out.println("Match: " + (result2 == -1 ? "✓" : "✗"));
        
        // Test Case 3: All flowers bloom same day
        System.out.println("\n\nTest Case 3:");
        int[] bloom3 = {5, 5, 5, 5, 5};
        int m3 = 2, k3 = 3;
        System.out.println("Bloom days: " + Arrays.toString(bloom3));
        System.out.println("Bouquets needed: " + m3);
        System.out.println("Flowers per bouquet: " + k3);
        
        int result3 = roseGarden(bloom3, k3, m3);
        int linear3 = minDaysLinear(bloom3, m3, k3);
        
        System.out.println("\nBinary Search Result: " + result3);
        System.out.println("Linear Search Result: " + linear3);
        System.out.println("Expected: 5");
        System.out.println("Match: " + (result3 == 5 ? "✓" : "✗"));
        
        // Test Case 4: Need exactly all flowers
        System.out.println("\n\nTest Case 4:");
        int[] bloom4 = {1, 2, 3, 4, 5};
        int m4 = 1, k4 = 5;
        System.out.println("Bloom days: " + Arrays.toString(bloom4));
        System.out.println("Bouquets needed: " + m4);
        System.out.println("Flowers per bouquet: " + k4);
        
        int result4 = roseGarden(bloom4, k4, m4);
        System.out.println("\nResult: " + result4);
        System.out.println("Expected: 5 (need flower 5 to bloom)");
        System.out.println("Match: " + (result4 == 5 ? "✓" : "✗"));
        
        // Test Case 5: Complex case
        System.out.println("\n\nTest Case 5:");
        int[] bloom5 = {7, 7, 7, 7, 13, 11, 12, 7};
        int m5 = 2, k5 = 3;
        System.out.println("Bloom days: " + Arrays.toString(bloom5));
        System.out.println("Bouquets needed: " + m5);
        System.out.println("Flowers per bouquet: " + k5);
        
        int result5 = roseGarden(bloom5, k5, m5);
        int linear5 = minDaysLinear(bloom5, m5, k5);
        
        System.out.println("\nBinary Search Result: " + result5);
        System.out.println("Linear Search Result: " + linear5);
        System.out.println("Expected: 12");
        System.out.println("Match: " + (result5 == 12 ? "✓" : "✗"));
        
        // Show why day 12 works
        System.out.println("\nWhy day 12 works:");
        canMakeBouquetsVerbose(bloom5, 12, m5, k5);
        
        // Test Case 6: m = 0
        System.out.println("\n\nTest Case 6:");
        int[] bloom6 = {1, 10, 3, 10, 2};
        int m6 = 0, k6 = 1;
        System.out.println("Bloom days: " + Arrays.toString(bloom6));
        System.out.println("Bouquets needed: " + m6);
        System.out.println("Flowers per bouquet: " + k6);
        
        int result6 = roseGarden(bloom6, k6, m6);
        System.out.println("\nResult: " + result6);
        System.out.println("Expected: 0 (no bouquets needed)");
        System.out.println("Match: " + (result6 == 0 ? "✓" : "✗"));
        
        // Test Case 7: Single bouquet
        System.out.println("\n\nTest Case 7:");
        int[] bloom7 = {100, 1, 10};
        int m7 = 1, k7 = 2;
        System.out.println("Bloom days: " + Arrays.toString(bloom7));
        System.out.println("Bouquets needed: " + m7);
        System.out.println("Flowers per bouquet: " + k7);
        
        int result7 = roseGarden(bloom7, k7, m7);
        System.out.println("\nResult: " + result7);
        System.out.println("Expected: 10 (need flowers 1 and 10 to bloom)");
        System.out.println("Match: " + (result7 == 10 ? "✓" : "✗"));
        
        // Test Case 8: Large k, small array
        System.out.println("\n\nTest Case 8:");
        int[] bloom8 = {1, 2};
        int m8 = 1, k8 = 3;
        System.out.println("Bloom days: " + Arrays.toString(bloom8));
        System.out.println("Bouquets needed: " + m8);
        System.out.println("Flowers per bouquet: " + k8);
        
        int result8 = roseGarden(bloom8, k8, m8);
        System.out.println("\nResult: " + result8);
        System.out.println("Expected: -1 (need 3 flowers but only have 2)");
        System.out.println("Match: " + (result8 == -1 ? "✓" : "✗"));
        
        // Performance comparison
        System.out.println("\n=== Performance Comparison ===");
        int[] largeBloom = new int[100000];
        for (int i = 0; i < largeBloom.length; i++) {
            largeBloom[i] = (int)(Math.random() * 1000000) + 1;
        }
        int largeM = 1000;
        int largeK = 50;
        
        // Binary Search
        long startTime = System.currentTimeMillis();
        int binaryResult = roseGarden(largeBloom, largeK, largeM);
        long binaryTime = System.currentTimeMillis() - startTime;
        
        // Linear Search (too slow for large arrays, so skip or use smaller)
        if (largeBloom.length <= 10000) {
            startTime = System.currentTimeMillis();
            int linearResult = minDaysLinear(largeBloom, largeM, largeK);
            long linearTime = System.currentTimeMillis() - startTime;
            
            System.out.println("Array size: " + largeBloom.length);
            System.out.println("Binary Search: " + binaryResult + " (" + binaryTime + "ms)");
            System.out.println("Linear Search: " + linearResult + " (" + linearTime + "ms)");
            System.out.println("Speedup: " + (linearTime/binaryTime) + "x faster");
        } else {
            System.out.println("Array size: " + largeBloom.length + " (too large for linear search)");
            System.out.println("Binary Search: " + binaryResult + " (" + binaryTime + "ms)");
        }
    }
}

/**
 * MATHEMATICAL ANALYSIS:
 * 
 * 1. Impossibility Condition:
 *    If m * k > n, impossible because we need more flowers than available.
 * 
 * 2. Binary Search Validity:
 *    Function f(day) = "can make m bouquets by day" is monotonic:
 *    - If f(day) is true, then f(day') is true for all day' > day
 *    - If f(day) is false, then f(day') is false for all day' < day
 * 
 * 3. Why Greedy Works for Validation:
 *    Given a specific day, we want to maximize number of bouquets.
 *    Making bouquets as soon as possible (when k consecutive are available)
 *    is optimal because it doesn't waste any flowers.
 */

/**
 * TIME COMPLEXITY ANALYSIS:
 * 
 * Let n = number of flowers, d = max(bloomDay) - min(bloomDay)
 * 
 * Binary Search Approach:
 * - Binary search iterations: O(log d)
 * - Each validation: O(n) to scan all flowers
 * - Total: O(n log d)
 * 
 * Linear Search:
 * - O(n * d) worst case
 * - Impractical for large d
 */

/**
 * VISUAL EXAMPLE:
 * 
 * bloomDay = [1, 10, 3, 10, 2], m = 3, k = 1
 * 
 * Day 1: Flowers bloomed: [✓, ✗, ✗, ✗, ✗] → 1 bouquet
 * Day 2: Flowers bloomed: [✓, ✗, ✗, ✗, ✓] → 2 bouquets  
 * Day 3: Flowers bloomed: [✓, ✗, ✓, ✗, ✓] → 3 bouquets ✓
 * 
 * So answer = 3
 */

/**
 * EDGE CASES:
 * 
 * 1. m = 0: Always possible on day 0
 * 2. k = 0: Not meaningful, but could be handled
 * 3. m * k > n: Impossible, return -1
 * 4. All flowers bloom same day: Answer is that day
 * 5. Need all flowers: Answer is max bloom day
 */

/**
 * RELATED PROBLEMS:
 * 
 * 1. Capacity To Ship Packages Within D Days: Similar binary search
 * 2. Koko Eating Bananas: Similar "minimize maximum" pattern
 * 3. Split Array Largest Sum: Same concept
 * 4. Magnetic Force Between Two Balls: Similar "maximize minimum" pattern
 * 
 * All follow "binary search on answer + greedy validation" pattern.
 */

/**
 * OPTIMIZATION TIPS:
 * 
 * 1. Early Exit in Validation:
 *    Stop counting bouquets if we've already reached m
 * 
 * 2. Search Range Optimization:
 *    Instead of [1, maxDay], we can use [minDay, maxDay]
 * 
 * 3. Use long for Multiplication:
 *    Prevent overflow when calculating m * k
 * 
 * 4. For Large k:
 *    If k > n, impossible immediately
 */

/**
 * PRACTICAL APPLICATIONS:
 * 
 * 1. Flower harvesting optimization
 * 2. Crop harvesting scheduling
 * 3. Resource allocation with adjacency constraints
 * 4. Manufacturing with batch requirements
 */

/**
 * LEETCODE VERSION:
 * This is LeetCode 1482: Minimum Number of Days to Make m Bouquets
 * Constraints:
 * - bloomDay.length == n
 * - 1 <= n <= 10^5
 * - 1 <= bloomDay[i] <= 10^9
 * - 1 <= m <= 10^6
 * - 1 <= k <= n
 */