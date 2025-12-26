/**
 * Problem: Capacity to Ship Packages Within D Days (LeetCode 1011)
 * 
 * Problem Description:
 * A conveyor belt has packages that must be shipped from one port to another within d days.
 * The i-th package on the conveyor belt has a weight of weights[i].
 * Each day, we load the ship with packages on the conveyor belt (in the order given).
 * We cannot load more weight than the maximum weight capacity of the ship.
 * 
 * Return the least weight capacity of the ship that will result in all packages
 * being shipped within d days.
 * 
 * Example 1:
 * Input: weights = [1,2,3,4,5,6,7,8,9,10], d = 5
 * Output: 15
 * Explanation:
 * A ship capacity of 15 is the minimum to ship all packages in 5 days:
 * Day 1: 1,2,3,4,5
 * Day 2: 6,7
 * Day 3: 8
 * Day 4: 9
 * Day 5: 10
 * 
 * Example 2:
 * Input: weights = [3,2,2,4,1,4], d = 3
 * Output: 6
 * Explanation:
 * Day 1: 3,2
 * Day 2: 2,4
 * Day 3: 1,4
 * 
 * Approach: Binary Search on Answer
 * 
 * Key Insight:
 * - Minimum capacity must be at least max(weights) (ship one heaviest package per day)
 * - Maximum capacity needed is sum(weights) (ship all in one day)
 * - Binary search for minimum capacity that allows shipping within d days
 * 
 * Time Complexity: O(n log S) where n = weights.length, S = sum(weights)
 * Space Complexity: O(1)
 */

import java.util.Arrays;

public class ShipCap {

    /**
     * Calculates number of days needed to ship packages with given capacity
     * 
     * @param weights - Array of package weights
     * @param capacity - Ship's weight capacity
     * @return Number of days required to ship all packages
     * 
     * Greedy Strategy:
     * 1. Load packages onto current day until adding next package exceeds capacity
     * 2. Then start new day with that package
     * 3. Count total days needed
     */
    private static int daysRequired(int[] weights, int capacity) {
        int days = 1; // At least one day needed
        int currentLoad = 0; // Weight loaded on current day
        
        for (int weight : weights) {
            // Check if adding this package exceeds capacity
            if (currentLoad + weight > capacity) {
                // Start new day with this package
                days++;
                currentLoad = weight;
            } else {
                // Add package to current day's load
                currentLoad += weight;
            }
        }
        
        return days;
    }
    
    /**
     * Enhanced version with detailed logging
     */
    private static int daysRequiredVerbose(int[] weights, int capacity, int maxDays) {
        System.out.printf("\n  Checking capacity %d:\n", capacity);
        int days = 1;
        int currentLoad = 0;
        
        System.out.print("    Day 1: [");
        for (int i = 0; i < weights.length; i++) {
            if (currentLoad + weights[i] > capacity) {
                System.out.printf("] (%d kg)", currentLoad);
                days++;
                currentLoad = weights[i];
                System.out.printf("\n    Day %d: [%d", days, weights[i]);
                
                // Early exit if already exceeded max days
                if (days > maxDays) {
                    System.out.println("...]");
                    System.out.printf("    Result: Need %d days (exceeds %d)\n", days, maxDays);
                    return days;
                }
            } else {
                currentLoad += weights[i];
                if (i > 0 && currentLoad != weights[i]) {
                    System.out.print(", " + weights[i]);
                }
            }
        }
        System.out.printf("] (%d kg)\n", currentLoad);
        System.out.printf("    Result: Need %d days\n", days);
        
        return days;
    }
    
    /**
     * Finds minimum ship capacity to ship all packages within d days
     * 
     * @param weights - Array of package weights
     * @param d - Maximum days allowed
     * @return Minimum required ship capacity
     * 
     * Binary Search Bounds:
     * - low = max(weights): Must ship heaviest package in one day
     * - high = sum(weights): Could ship all in one day if capacity allows
     * 
     * Monotonic Property:
     * Let f(capacity) = days needed with given capacity
     * - If capacity increases → f(capacity) decreases (or stays same)
     * - If capacity decreases → f(capacity) increases (or stays same)
     */
    public static int leastWeightCapacity(int[] weights, int d) {
        // Edge cases
        if (weights == null || weights.length == 0) {
            return 0;
        }
        
        if (d <= 0) {
            return -1; // Invalid input
        }
        
        // Calculate search bounds
        int maxWeight = 0;
        int totalWeight = 0;
        
        for (int weight : weights) {
            maxWeight = Math.max(maxWeight, weight);
            totalWeight += weight;
        }
        
        System.out.println("Finding minimum ship capacity:");
        System.out.println("  Weights: " + Arrays.toString(weights));
        System.out.println("  Days allowed: " + d);
        System.out.println("  Max weight (lower bound): " + maxWeight);
        System.out.println("  Total weight (upper bound): " + totalWeight);
        
        // Special cases
        if (d == 1) {
            return totalWeight; // Must ship all in one day
        }
        
        if (d >= weights.length) {
            // Can ship at most one package per day
            // So capacity must be at least the heaviest package
            return maxWeight;
        }
        
        int low = maxWeight;
        int high = totalWeight;
        int result = totalWeight; // Worst case: all in one day
        
        // Binary search for minimum capacity
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            int daysNeeded = daysRequired(weights, mid);
            System.out.printf("\n  Capacity %d → %d days needed", mid, daysNeeded);
            
            if (daysNeeded <= d) {
                System.out.println(" ✓ (within " + d + " days)");
                // Can ship within d days, try smaller capacity
                result = Math.min(result, mid);
                high = mid - 1;
            } else {
                System.out.println(" ✗ (exceeds " + d + " days)");
                // Takes too many days, need larger capacity
                low = mid + 1;
            }
        }
        
        return result; // Could also return low
    }
    
    /**
     * Alternative implementation with clearer variable names
     */
    public static int shipWithinDays(int[] weights, int days) {
        if (weights == null || weights.length == 0 || days <= 0) {
            return 0;
        }
        
        // Calculate bounds for binary search
        int maxSingleWeight = 0;
        int totalWeight = 0;
        
        for (int weight : weights) {
            maxSingleWeight = Math.max(maxSingleWeight, weight);
            totalWeight += weight;
        }
        
        // If only one day available, must ship all at once
        if (days == 1) {
            return totalWeight;
        }
        
        // If many days available, need at least heaviest package capacity
        if (days >= weights.length) {
            return maxSingleWeight;
        }
        
        // Binary search
        int left = maxSingleWeight;
        int right = totalWeight;
        int minCapacity = totalWeight;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (canShip(weights, days, mid)) {
                // Can ship with this capacity, try smaller
                minCapacity = Math.min(minCapacity, mid);
                right = mid - 1;
            } else {
                // Cannot ship, need larger capacity
                left = mid + 1;
            }
        }
        
        return minCapacity;
    }
    
    /**
     * Checks if we can ship all packages within given days using given capacity
     */
    private static boolean canShip(int[] weights, int maxDays, int capacity) {
        int daysUsed = 1;
        int currentLoad = 0;
        
        for (int weight : weights) {
            if (currentLoad + weight <= capacity) {
                currentLoad += weight;
            } else {
                daysUsed++;
                currentLoad = weight;
                
                // Early exit if already exceeded max days
                if (daysUsed > maxDays) {
                    return false;
                }
            }
        }
        
        return daysUsed <= maxDays;
    }
    
    /**
     * Linear search for comparison (slow but simple)
     */
    public static int leastWeightCapacityLinear(int[] weights, int d) {
        int maxWeight = 0;
        int totalWeight = 0;
        
        for (int weight : weights) {
            maxWeight = Math.max(maxWeight, weight);
            totalWeight += weight;
        }
        
        // Try each possible capacity from maxWeight to totalWeight
        for (int capacity = maxWeight; capacity <= totalWeight; capacity++) {
            if (canShip(weights, d, capacity)) {
                return capacity;
            }
        }
        
        return totalWeight; // Should always work
    }
    
    public static void main(String[] args) {
        System.out.println("=== Capacity to Ship Packages Within D Days ===\n");
        
        // Test Case 1: Example from problem description
        System.out.println("Test Case 1:");
        int[] weights1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int days1 = 5;
        System.out.println("Weights: " + Arrays.toString(weights1));
        System.out.println("Days: " + days1);
        
        int result1 = leastWeightCapacity(weights1, days1);
        int linear1 = leastWeightCapacityLinear(weights1, days1);
        
        System.out.println("\nBinary Search Result: " + result1);
        System.out.println("Linear Search Result: " + linear1);
        System.out.println("Expected: 15");
        System.out.println("Match: " + (result1 == 15 ? "✓" : "✗"));
        System.out.println("Results match: " + (result1 == linear1 ? "✓" : "✗"));
        
        // Show shipping schedule for optimal capacity
        System.out.println("\nShipping schedule for capacity " + result1 + ":");
        daysRequiredVerbose(weights1, result1, days1);
        
        // Test Case 2: Another example
        System.out.println("\n\nTest Case 2:");
        int[] weights2 = {3, 2, 2, 4, 1, 4};
        int days2 = 3;
        System.out.println("Weights: " + Arrays.toString(weights2));
        System.out.println("Days: " + days2);
        
        int result2 = leastWeightCapacity(weights2, days2);
        int linear2 = leastWeightCapacityLinear(weights2, days2);
        
        System.out.println("\nBinary Search Result: " + result2);
        System.out.println("Linear Search Result: " + linear2);
        System.out.println("Expected: 6");
        System.out.println("Match: " + (result2 == 6 ? "✓" : "✗"));
        
        // Test Case 3: One day only
        System.out.println("\n\nTest Case 3:");
        int[] weights3 = {1, 2, 3, 4, 5};
        int days3 = 1;
        System.out.println("Weights: " + Arrays.toString(weights3));
        System.out.println("Days: " + days3);
        
        int result3 = leastWeightCapacity(weights3, days3);
        System.out.println("\nResult: " + result3);
        System.out.println("Expected: 15 (sum of all weights)");
        System.out.println("Match: " + (result3 == 15 ? "✓" : "✗"));
        
        // Test Case 4: Many days (one package per day)
        System.out.println("\n\nTest Case 4:");
        int[] weights4 = {1, 2, 3, 4, 5};
        int days4 = 5;
        System.out.println("Weights: " + Arrays.toString(weights4));
        System.out.println("Days: " + days4);
        
        int result4 = leastWeightCapacity(weights4, days4);
        System.out.println("\nResult: " + result4);
        System.out.println("Expected: 5 (heaviest package)");
        System.out.println("Match: " + (result4 == 5 ? "✓" : "✗"));
        
        // Test Case 5: Large packages
        System.out.println("\n\nTest Case 5:");
        int[] weights5 = {10, 20, 30, 40};
        int days5 = 2;
        System.out.println("Weights: " + Arrays.toString(weights5));
        System.out.println("Days: " + days5);
        
        int result5 = leastWeightCapacity(weights5, days5);
        System.out.println("\nResult: " + result5);
        System.out.println("Expected: 60 (optimal: [10+20+30=60], [40=40])");
        System.out.println("Match: " + (result5 == 60 ? "✓" : "✗"));
        
        // Test Case 6: All packages same weight
        System.out.println("\n\nTest Case 6:");
        int[] weights6 = {5, 5, 5, 5, 5, 5};
        int days6 = 3;
        System.out.println("Weights: " + Arrays.toString(weights6));
        System.out.println("Days: " + days6);
        
        int result6 = leastWeightCapacity(weights6, days6);
        System.out.println("\nResult: " + result6);
        System.out.println("Expected: 10 (2 packages per day = 10 each)");
        System.out.println("Match: " + (result6 == 10 ? "✓" : "✗"));
        
        // Test Case 7: Very large weight difference
        System.out.println("\n\nTest Case 7:");
        int[] weights7 = {1, 100};
        int days7 = 2;
        System.out.println("Weights: " + Arrays.toString(weights7));
        System.out.println("Days: " + days7);
        
        int result7 = leastWeightCapacity(weights7, days7);
        System.out.println("\nResult: " + result7);
        System.out.println("Expected: 100 (heaviest package)");
        System.out.println("Match: " + (result7 == 100 ? "✓" : "✗"));
        
        // Test Case 8: Empty array
        System.out.println("\n\nTest Case 8:");
        int[] weights8 = {};
        int days8 = 5;
        System.out.println("Weights: []");
        System.out.println("Days: " + days8);
        
        int result8 = leastWeightCapacity(weights8, days8);
        System.out.println("\nResult: " + result8);
        System.out.println("Expected: 0");
        System.out.println("Match: " + (result8 == 0 ? "✓" : "✗"));
        
        // Test Case 9: Single package
        System.out.println("\n\nTest Case 9:");
        int[] weights9 = {42};
        int days9 = 1;
        System.out.println("Weights: " + Arrays.toString(weights9));
        System.out.println("Days: " + days9);
        
        int result9 = leastWeightCapacity(weights9, days9);
        System.out.println("\nResult: " + result9);
        System.out.println("Expected: 42");
        System.out.println("Match: " + (result9 == 42 ? "✓" : "✗"));
        
        // Performance comparison
        System.out.println("\n=== Performance Comparison ===");
        int[] largeWeights = new int[100000];
        for (int i = 0; i < largeWeights.length; i++) {
            largeWeights[i] = (int)(Math.random() * 1000) + 1;
        }
        int largeDays = 100;
        
        long startTime = System.currentTimeMillis();
        int binaryResult = leastWeightCapacity(largeWeights, largeDays);
        long binaryTime = System.currentTimeMillis() - startTime;
        
        // Linear search is too slow for large arrays
        if (largeWeights.length <= 10000) {
            startTime = System.currentTimeMillis();
            int linearResult = leastWeightCapacityLinear(largeWeights, largeDays);
            long linearTime = System.currentTimeMillis() - startTime;
            
            System.out.println("Array size: " + largeWeights.length);
            System.out.println("Binary Search: " + binaryResult + " (" + binaryTime + "ms)");
            System.out.println("Linear Search: " + linearResult + " (" + linearTime + "ms)");
            System.out.println("Speedup: " + (linearTime/binaryTime) + "x faster");
        } else {
            System.out.println("Array size: " + largeWeights.length + " (too large for linear search)");
            System.out.println("Binary Search: " + binaryResult + " (" + binaryTime + "ms)");
        }
        
        // Additional verification with random tests
        System.out.println("\n=== Random Test Verification ===");
        int numTests = 100;
        int passed = 0;
        
        for (int test = 0; test < numTests; test++) {
            int n = (int)(Math.random() * 100) + 1;
            int[] randomWeights = new int[n];
            int randomTotal = 0;
            
            for (int i = 0; i < n; i++) {
                randomWeights[i] = (int)(Math.random() * 100) + 1;
                randomTotal += randomWeights[i];
            }
            
            int randomDays = (int)(Math.random() * n) + 1;
            
            int binaryAns = leastWeightCapacity(randomWeights, randomDays);
            int linearAns = leastWeightCapacityLinear(randomWeights, randomDays);
            
            if (binaryAns == linearAns) {
                passed++;
            } else {
                System.out.println("Mismatch: weights=" + Arrays.toString(randomWeights) +
                                 ", days=" + randomDays +
                                 ", binary=" + binaryAns + ", linear=" + linearAns);
            }
        }
        
        System.out.println("Passed " + passed + "/" + numTests + " random tests");
    }
}

/**
 * MATHEMATICAL ANALYSIS:
 * 
 * 1. Search Space Justification:
 *    - Lower bound: max(weights)
 *      A ship must be able to carry the heaviest package
 *    - Upper bound: sum(weights)
 *      A ship with this capacity can carry all packages in one day
 * 
 * 2. Monotonic Property Proof:
 *    Let f(capacity) = days needed to ship all packages
 *    - If capacity₁ < capacity₂, then f(capacity₁) ≥ f(capacity₂)
 *    - More capacity → fewer days needed (or same)
 *    - Less capacity → more days needed (or same)
 * 
 * 3. Greedy Validation Optimality:
 *    Given a capacity, loading packages greedily (as much as possible each day)
 *    minimizes the number of days needed.
 */

/**
 * TIME COMPLEXITY ANALYSIS:
 * 
 * Let n = number of packages, S = sum(weights)
 * 
 * Binary Search Approach:
 * - Binary search iterations: O(log(S - max))
 * - Each validation: O(n) to simulate shipping
 * - Total: O(n log S)
 * 
 * Linear Search:
 * - O(n * S) worst case
 * - Impractical for large S
 */

/**
 * VISUAL EXAMPLE:
 * 
 * weights = [1,2,3,4,5,6,7,8,9,10], d = 5
 * 
 * Try capacity = 15:
 * Day 1: 1+2+3+4+5 = 15
 * Day 2: 6+7 = 13
 * Day 3: 8 = 8
 * Day 4: 9 = 9
 * Day 5: 10 = 10
 * Total days = 5 ✓
 * 
 * Try capacity = 14:
 * Day 1: 1+2+3+4 = 10 (can't add 5, would be 15 > 14)
 * Day 2: 5+6 = 11
 * Day 3: 7+8 = 15 > 14 → 7, then 8
 * Day 4: 9 = 9
 * Day 5: 10 = 10
 * Total days = 6 ✗
 * 
 * So minimum capacity = 15
 */

/**
 * EDGE CASES:
 * 
 * 1. d = 1: Must ship all in one day → capacity = sum(weights)
 * 2. d >= n: Can ship one package per day → capacity = max(weights)
 * 3. Empty weights array: capacity = 0
 * 4. Single package: capacity = weight of that package
 * 5. All packages same weight: capacity = ceil(total/days) * weight
 */

/**
 * RELATED PROBLEMS:
 * 
 * 1. Split Array Largest Sum (LeetCode 410): Exactly the same problem
 * 2. Painter's Partition: Same mathematical formulation
 * 3. Book Allocation: Same "minimize maximum" pattern
 * 4. Koko Eating Bananas: Similar binary search approach
 * 5. Minimum Number of Days to Make m Bouquets: Similar pattern
 * 
 * All follow "binary search on answer + greedy validation" pattern.
 */

/**
 * PRACTICAL APPLICATIONS:
 * 
 * 1. Logistics: Determine minimum truck capacity for delivery schedule
 * 2. Manufacturing: Batch processing with capacity constraints
 * 3. Cloud Computing: Resource allocation for task scheduling
 * 4. Construction: Material delivery planning
 * 5. Agriculture: Harvest transport planning
 */

/**
 * LEETCODE VERSION (1011):
 * Constraints:
 * - 1 <= days <= weights.length <= 5 * 10^4
 * - 1 <= weights[i] <= 500
 * - So maximum total weight = 5 * 10^4 * 500 = 2.5 * 10^7
 * 
 * For binary search: O(n log S) = O(5*10^4 * log(2.5*10^7)) ≈ O(5*10^4 * 25) ≈ 1.25*10^6 operations ✓
 */

/**
 * OPTIMIZATION TIPS:
 * 
 * 1. Use early exit in validation when days exceed d
 * 2. Use long for total sum to prevent overflow
 * 3. Pre-calculate max and sum in single pass
 * 4. Use while (left < right) for cleaner binary search termination
 * 5. For very large weights, consider using binary search on answer directly
 */

/**
 * ALTERNATIVE APPROACHES:
 * 
 * 1. Dynamic Programming: O(n * d * S) - too slow for constraints
 * 2. Greedy with priority queue: Not optimal for this problem
 * 3. Integer programming: Too complex
 * 
 * Binary search is the optimal approach for given constraints.
 */