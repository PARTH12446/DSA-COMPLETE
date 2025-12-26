/**
 * Problem: Minimum Rate to Eat Bananas (Koko Eating Bananas)
 * 
 * Problem Description:
 * Koko loves to eat bananas. There are N piles of bananas, the i-th pile has piles[i] bananas.
 * Koko can decide her bananas-per-hour eating speed of K.
 * Each hour, she chooses some pile of bananas and eats K bananas from that pile.
 * If the pile has less than K bananas, she eats all of them and won't eat any more bananas that hour.
 * 
 * Koko likes to eat slowly but wants to finish eating all the bananas within H hours.
 * 
 * Goal: Return the minimum integer K such that she can eat all the bananas within H hours.
 * 
 * Example:
 * Input: piles = [3, 6, 7, 11], H = 8
 * Output: 4
 * Explanation:
 * With K = 4:
 * - Pile 1 (3): 1 hour (eats 3)
 * - Pile 2 (6): 2 hours (eats 4, then 2)
 * - Pile 3 (7): 2 hours (eats 4, then 3)
 * - Pile 4 (11): 3 hours (eats 4, 4, then 3)
 * Total hours = 1 + 2 + 2 + 3 = 8 hours ✓
 * 
 * With K = 3:
 * Total hours = 1 + 2 + 3 + 4 = 10 hours > 8 ✗
 * 
 * Approach: Binary Search on Eating Speed K
 * 
 * Time Complexity: O(N log M) where N = piles.length, M = max(piles)
 * Space Complexity: O(1)
 */

import java.util.Arrays;

public class Koko {

    /**
     * Calculates total hours needed to eat all bananas at given speed
     * 
     * @param piles - Array of banana piles
     * @param speed - Bananas eaten per hour (K)
     * @return Total hours required to eat all bananas at given speed
     * 
     * Mathematical Formula:
     * Hours for pile i = ceil(piles[i] / speed)
     * = (piles[i] + speed - 1) / speed  (integer division trick for ceiling)
     * 
     * Example:
     * pile = 11, speed = 4
     * ceil(11/4) = ceil(2.75) = 3
     * Using formula: (11 + 4 - 1) / 4 = 14 / 4 = 3 (integer division)
     */
    private static long calculateTotalHours(int[] piles, int speed) {
        long totalHours = 0;
        
        for (int bananas : piles) {
            // Ceiling division: (a + b - 1) / b
            totalHours += (bananas + (long)speed - 1) / speed;
            
            // Alternative using Math.ceil (slower):
            // totalHours += (int)Math.ceil((double)bananas / speed);
        }
        
        return totalHours;
    }
    
    /**
     * Enhanced version with detailed calculation logging
     */
    private static long calculateTotalHoursVerbose(int[] piles, int speed, int maxHours) {
        System.out.printf("  Speed %d: ", speed);
        long totalHours = 0;
        
        for (int i = 0; i < piles.length; i++) {
            int hoursForPile = (piles[i] + speed - 1) / speed;
            totalHours += hoursForPile;
            
            System.out.printf("Pile %d (%d bananas) → %d hours", 
                            i + 1, piles[i], hoursForPile);
            if (i < piles.length - 1) {
                System.out.print(", ");
            }
            
            // Early exit if already exceeded max hours
            if (totalHours > maxHours) {
                System.out.printf("... (exceeds %d)\n", maxHours);
                return totalHours;
            }
        }
        
        System.out.printf(" = %d total hours\n", totalHours);
        return totalHours;
    }
    
    /**
     * Finds minimum eating speed to finish bananas within H hours
     * 
     * @param v - Array of banana piles
     * @param h - Maximum hours available
     * @return Minimum integer speed K
     * 
     * Binary Search Setup:
     * - low = 1 (minimum possible speed, eat 1 banana per hour)
     * - high = max(piles) (maximum needed speed, eat largest pile in 1 hour)
     * 
     * Monotonic Property:
     * Let f(K) = total hours needed at speed K
     * - If K increases → f(K) decreases (monotonically decreasing)
     * - If K decreases → f(K) increases
     * 
     * Binary Search Logic:
     * For candidate speed mid:
     * - If f(mid) ≤ h: Can finish in time → try slower speed (high = mid - 1)
     * - If f(mid) > h: Takes too long → need faster speed (low = mid + 1)
     */
    public static int minimumRateToEatBananas(int[] v, int h) {
        // Edge cases
        if (v == null || v.length == 0) {
            return 0;
        }
        
        if (h < v.length) {
            // Cannot finish even if eating 1 pile per hour
            return -1;
        }
        
        // Find maximum pile size (upper bound for speed)
        int maxPile = 0;
        for (int bananas : v) {
            maxPile = Math.max(maxPile, bananas);
        }
        
        // Special case: if hours equal number of piles, must eat each pile in 1 hour
        if (h == v.length) {
            return maxPile;
        }
        
        // Binary search boundaries
        int low = 1;               // Minimum possible speed
        int high = maxPile;        // Maximum needed speed
        int answer = high;         // Initialize with worst case
        
        System.out.println("Initial setup:");
        System.out.println("  Piles: " + Arrays.toString(v));
        System.out.println("  Max hours: " + h);
        System.out.println("  Max pile: " + maxPile);
        System.out.println("  Search range: [" + low + ", " + high + "]\n");
        
        while (low <= high) {
            int mid = low + (high - low) / 2;  // Candidate speed
            
            long totalHours = calculateTotalHours(v, mid);
            System.out.printf("Trying speed %d → %d hours", mid, totalHours);
            
            if (totalHours <= h) {
                System.out.println(" ✓ (within " + h + " hours)");
                // Can finish in time with current speed
                answer = Math.min(answer, mid);  // Update minimum speed
                high = mid - 1;                  // Try slower speed
            } else {
                System.out.println(" ✗ (exceeds " + h + " hours)");
                // Takes too long, need faster speed
                low = mid + 1;
            }
        }
        
        return answer;
    }
    
    /**
     * Alternative implementation with clearer variable names
     */
    public static int minEatingSpeed(int[] piles, int h) {
        if (piles == null || piles.length == 0 || h <= 0) {
            return 0;
        }
        
        // Find maximum pile (upper bound)
        int maxPile = 0;
        for (int pile : piles) {
            maxPile = Math.max(maxPile, pile);
        }
        
        // If hours equals piles count, must eat each pile in 1 hour
        if (h == piles.length) {
            return maxPile;
        }
        
        // Binary search for minimum speed
        int left = 1;
        int right = maxPile;
        int minSpeed = maxPile;  // Worst case
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (canFinish(piles, h, mid)) {
                // Can finish with this speed
                minSpeed = Math.min(minSpeed, mid);
                right = mid - 1;  // Try slower
            } else {
                // Cannot finish, need faster
                left = mid + 1;
            }
        }
        
        return minSpeed;
    }
    
    /**
     * Checks if Koko can finish bananas within h hours at given speed
     */
    private static boolean canFinish(int[] piles, int maxHours, int speed) {
        long hoursNeeded = 0;
        
        for (int pile : piles) {
            // Ceiling division
            hoursNeeded += (pile + (long)speed - 1) / speed;
            
            // Early exit optimization
            if (hoursNeeded > maxHours) {
                return false;
            }
        }
        
        return hoursNeeded <= maxHours;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Koko Eating Bananas Problem ===\n");
        
        // Test Case 1: Standard example
        System.out.println("Test Case 1: Standard example");
        int[] piles1 = {3, 6, 7, 11};
        int hours1 = 8;
        System.out.println("Piles: " + Arrays.toString(piles1));
        System.out.println("Hours available: " + hours1);
        
        int result1 = minimumRateToEatBananas(piles1, hours1);
        System.out.println("\nMinimum eating speed: " + result1);
        System.out.println("Expected: 4\n");
        
        // Show calculation for optimal speed
        System.out.println("Verification for speed " + result1 + ":");
        calculateTotalHoursVerbose(piles1, result1, hours1);
        
        // Test Case 2: More hours available
        System.out.println("\n\nTest Case 2: More hours available");
        int[] piles2 = {30, 11, 23, 4, 20};
        int hours2 = 6;
        System.out.println("Piles: " + Arrays.toString(piles2));
        System.out.println("Hours available: " + hours2);
        
        int result2 = minimumRateToEatBananas(piles2, hours2);
        System.out.println("\nMinimum eating speed: " + result2);
        System.out.println("Expected: 23\n");
        
        // Test Case 3: Large piles, few hours
        System.out.println("\n\nTest Case 3: Large piles, tight deadline");
        int[] piles3 = {30, 11, 23, 4, 20};
        int hours3 = 5;
        System.out.println("Piles: " + Arrays.toString(piles3));
        System.out.println("Hours available: " + hours3);
        
        int result3 = minimumRateToEatBananas(piles3, hours3);
        System.out.println("\nMinimum eating speed: " + result3);
        System.out.println("Expected: 30\n");
        
        // Test Case 4: Exactly one pile per hour
        System.out.println("\n\nTest Case 4: Exactly one pile per hour");
        int[] piles4 = {3, 6, 7, 11};
        int hours4 = 4;
        System.out.println("Piles: " + Arrays.toString(piles4));
        System.out.println("Hours available: " + hours4);
        
        int result4 = minimumRateToEatBananas(piles4, hours4);
        System.out.println("\nMinimum eating speed: " + result4);
        System.out.println("Expected: 11 (largest pile)\n");
        
        // Test Case 5: Very large number of hours
        System.out.println("\n\nTest Case 5: Plenty of time");
        int[] piles5 = {332484035, 524908576, 855865114, 632922376, 222257295, 
                       690155293, 112677673, 679580077, 337406589, 290818316, 
                       877337160, 901728858, 679284947, 688210097, 692137887, 
                       718203285, 629455728, 941802184};
        int hours5 = 823855818;
        System.out.println("Piles: [large array], Length: " + piles5.length);
        System.out.println("Hours available: " + hours5);
        
        int result5 = minimumRateToEatBananas(piles5, hours5);
        System.out.println("\nMinimum eating speed: " + result5);
        System.out.println("Expected: 14 (from LeetCode test case)\n");
        
        // Test Case 6: Single pile
        System.out.println("\n\nTest Case 6: Single pile");
        int[] piles6 = {25};
        int hours6 = 5;
        System.out.println("Piles: " + Arrays.toString(piles6));
        System.out.println("Hours available: " + hours6);
        
        int result6 = minimumRateToEatBananas(piles6, hours6);
        System.out.println("\nMinimum eating speed: " + result6);
        System.out.println("Expected: 5 (25/5 = 5 hours)\n");
        
        // Test Case 7: Edge case - impossible
        System.out.println("\n\nTest Case 7: Too few hours");
        int[] piles7 = {3, 6, 7, 11};
        int hours7 = 3;  // Need at least 4 hours (one per pile)
        System.out.println("Piles: " + Arrays.toString(piles7));
        System.out.println("Hours available: " + hours7);
        
        int result7 = minimumRateToEatBananas(piles7, hours7);
        System.out.println("\nMinimum eating speed: " + result7);
        System.out.println("Note: Returns max pile when impossible\n");
        
        // Performance test
        System.out.println("=== Performance Test ===");
        int[] largePiles = new int[100000];
        for (int i = 0; i < largePiles.length; i++) {
            largePiles[i] = (int)(Math.random() * 1000000000) + 1;
        }
        int largeHours = 1000000000;
        
        long startTime = System.currentTimeMillis();
        int largeResult = minEatingSpeed(largePiles, largeHours);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Large test: " + largePiles.length + " piles, " + largeHours + " hours");
        System.out.println("Minimum speed: " + largeResult);
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
    }
}

/**
 * ALGORITHM WALKTHROUGH:
 * 
 * For piles = [3, 6, 7, 11], H = 8
 * 
 * Step 1: Calculate bounds
 *   low = 1 (minimum speed)
 *   high = max(3, 6, 7, 11) = 11
 * 
 * Step 2: Binary search iterations
 * 
 * Iteration 1: mid = (1 + 11) / 2 = 6
 *   Calculate hours at speed 6:
 *     pile 3: ceil(3/6) = 1
 *     pile 6: ceil(6/6) = 1
 *     pile 7: ceil(7/6) = 2
 *     pile 11: ceil(11/6) = 2
 *     Total = 1+1+2+2 = 6 ≤ 8 ✓
 *   Update: high = 6-1 = 5 (try slower)
 * 
 * Iteration 2: mid = (1 + 5) / 2 = 3
 *   Calculate hours at speed 3:
 *     pile 3: ceil(3/3) = 1
 *     pile 6: ceil(6/3) = 2
 *     pile 7: ceil(7/3) = 3
 *     pile 11: ceil(11/3) = 4
 *     Total = 1+2+3+4 = 10 > 8 ✗
 *   Update: low = 3+1 = 4 (need faster)
 * 
 * Iteration 3: mid = (4 + 5) / 2 = 4
 *   Calculate hours at speed 4:
 *     pile 3: ceil(3/4) = 1
 *     pile 6: ceil(6/4) = 2
 *     pile 7: ceil(7/4) = 2
 *     pile 11: ceil(11/4) = 3
 *     Total = 1+2+2+3 = 8 ≤ 8 ✓
 *   Update: high = 4-1 = 3
 * 
 * Now low=4, high=3 → loop ends
 * Answer = 4
 */

/**
 * MATHEMATICAL INSIGHTS:
 * 
 * 1. Ceiling Division Trick:
 *    ceil(a/b) = (a + b - 1) / b  for positive integers
 *    This avoids floating point operations
 * 
 * 2. Upper Bound Optimization:
 *    We don't need to search beyond max(piles) because:
 *    - At speed = max(piles), each pile takes at most 1 hour
 *    - Total hours = number of piles (minimum possible)
 * 
 * 3. Lower Bound:
 *    Must be at least 1 (can't eat 0 bananas per hour)
 */

/**
 * RELATED PROBLEMS:
 * 
 * 1. Capacity To Ship Packages Within D Days: Similar binary search on capacity
 * 2. Divide Chocolate: Maximize minimum sum
 * 3. Split Array Largest Sum: Minimize maximum sum
 * 4. Magnetic Force Between Two Balls: Maximize minimum distance
 * 
 * All follow "binary search on answer + greedy validation" pattern
 */

/**
 * TIME COMPLEXITY ANALYSIS:
 * 
 * Let N = number of piles, M = max(pile size)
 * 
 * 1. Finding max: O(N)
 * 2. Binary search iterations: O(log M)
 * 3. Each validation: O(N)
 * 
 * Total: O(N log M)
 * 
 * Space: O(1) additional space
 */

/**
 * OPTIMIZATIONS:
 * 
 * 1. Early exit in validation: Stop if totalHours exceeds H
 * 2. Use long for totalHours to prevent overflow
 * 3. For very large M, we can set high = sum(piles) when H=1
 *    (though not needed for this problem)
 */