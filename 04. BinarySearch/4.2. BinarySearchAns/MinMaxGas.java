/**
 * Problem: Minimise Max Distance Between Gas Stations
 * 
 * Problem Description:
 * Given positions of N gas stations on a straight line,
 * and K additional gas stations to be added.
 * The goal is to add K stations such that the maximum distance
 * between adjacent stations is minimized.
 * 
 * Example:
 * Input: arr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], k = 9
 * Output: 0.5
 * Explanation: Place stations at 0.5 intervals
 * 
 * Alternative: arr = [1, 13, 17, 23], k = 5
 * 
 * Approach 1: Max Heap (Greedy)
 * - Place new stations in the largest gap
 * - Time: O(k log n)
 * 
 * Approach 2: Binary Search on Answer
 * - Search for minimum possible max distance
 * - Time: O(n log range)
 */

import java.util.*;

public class MinMaxGas {

    /**
     * Max Heap (Priority Queue) Approach
     * 
     * Idea: Always place the next station in the largest gap
     * 
     * @param arr - Positions of existing stations
     * @param k - Number of new stations to add
     * @return Minimized maximum distance
     * 
     * Time Complexity: O(k log n) where n = number of gaps
     * Space Complexity: O(n) for priority queue
     */
    public static double minimiseMaxDistance(int[] arr, int k) {
        int n = arr.length;
        
        // Edge cases
        if (n <= 1) return 0;
        
        // Array to track how many stations inserted in each gap
        int[] stationsInGap = new int[n - 1];
        
        // Max heap: store gaps with their index
        PriorityQueue<Gap> maxHeap = new PriorityQueue<>((a, b) -> 
            Double.compare(b.length, a.length) // Max heap: larger first
        );
        
        // Initialize heap with original gaps
        for (int i = 0; i < n - 1; i++) {
            double gapLength = arr[i + 1] - arr[i];
            maxHeap.offer(new Gap(gapLength, i));
        }
        
        // Add k stations greedily
        for (int station = 0; station < k; station++) {
            // Take the largest gap
            Gap largestGap = maxHeap.poll();
            int gapIndex = largestGap.index;
            
            // Add a station to this gap
            stationsInGap[gapIndex]++;
            
            // Calculate new length for this divided gap
            double originalLength = arr[gapIndex + 1] - arr[gapIndex];
            // When we have s stations in a gap, it creates s+1 segments
            double newSegmentLength = originalLength / (stationsInGap[gapIndex] + 1.0);
            
            // Put the gap back with new length
            maxHeap.offer(new Gap(newSegmentLength, gapIndex));
        }
        
        // The largest gap after placing all stations
        return maxHeap.peek().length;
    }
    
    /**
     * Binary Search on Answer Approach (More Efficient)
     * 
     * Idea: Binary search for the minimum possible max distance D
     * such that we can place k stations with no gap > D
     * 
     * @param arr - Positions of existing stations
     * @param k - Number of new stations to add
     * @return Minimized maximum distance
     * 
     * Time Complexity: O(n log(range/epsilon))
     * Space Complexity: O(1)
     */
    public static double minimiseMaxDistanceBinarySearch(int[] arr, int k) {
        int n = arr.length;
        
        if (n <= 1) return 0;
        
        // Binary search on possible max distance
        double left = 0; // Minimum possible distance
        double right = 0; // Maximum gap initially
        
        // Find maximum gap to set upper bound
        for (int i = 0; i < n - 1; i++) {
            right = Math.max(right, arr[i + 1] - arr[i]);
        }
        
        // Binary search with precision
        double epsilon = 1e-6; // 0.000001 precision
        
        while (right - left > epsilon) {
            double mid = left + (right - left) / 2.0;
            
            if (canPlaceStations(arr, k, mid)) {
                // Can place with max distance = mid, try smaller
                right = mid;
            } else {
                // Cannot place with max distance = mid, need larger
                left = mid;
            }
        }
        
        return left; // or right, both close enough
    }
    
    /**
     * Check if we can place k stations such that max gap ≤ maxDist
     * 
     * @param arr - Station positions
     * @param k - Stations to add
     * @param maxDist - Maximum allowed distance between stations
     * @return true if possible, false otherwise
     */
    private static boolean canPlaceStations(int[] arr, int k, double maxDist) {
        int stationsNeeded = 0;
        
        for (int i = 0; i < arr.length - 1; i++) {
            double gap = arr[i + 1] - arr[i];
            
            // If gap > maxDist, we need to add stations
            // Number of stations needed = ceil(gap / maxDist) - 1
            stationsNeeded += Math.max(0, Math.ceil(gap / maxDist) - 1);
            
            // Early exit optimization
            if (stationsNeeded > k) {
                return false;
            }
        }
        
        return stationsNeeded <= k;
    }
    
    /**
     * Alternative: Linear search with stations count
     * Helper to visualize station placement
     */
    public static double minimiseMaxDistanceWithPlacement(int[] arr, int k) {
        int n = arr.length;
        if (n <= 1) return 0;
        
        int[] stationsInGap = new int[n - 1];
        PriorityQueue<Gap> maxHeap = new PriorityQueue<>(
            (a, b) -> Double.compare(b.length, a.length)
        );
        
        // Initialize
        for (int i = 0; i < n - 1; i++) {
            maxHeap.offer(new Gap(arr[i + 1] - arr[i], i));
        }
        
        System.out.println("Initial gaps:");
        for (int i = 0; i < n - 1; i++) {
            System.out.printf("Gap %d: %.2f km\n", i, arr[i+1] - arr[i]);
        }
        System.out.println();
        
        // Add k stations
        for (int station = 1; station <= k; station++) {
            Gap largestGap = maxHeap.poll();
            int gapIndex = largestGap.index;
            stationsInGap[gapIndex]++;
            
            double originalLength = arr[gapIndex + 1] - arr[gapIndex];
            double newLength = originalLength / (stationsInGap[gapIndex] + 1.0);
            
            maxHeap.offer(new Gap(newLength, gapIndex));
            
            System.out.printf("Station %d: Placed in gap %d (%.2f km)\n", 
                station, gapIndex, originalLength);
            System.out.printf("  Gap now has %d stations, segment length: %.2f km\n",
                stationsInGap[gapIndex], newLength);
            
            // Show current max gap
            double currentMax = maxHeap.peek().length;
            System.out.printf("  Current maximum distance: %.2f km\n\n", currentMax);
        }
        
        return maxHeap.peek().length;
    }
    
    /**
     * Gap class for priority queue
     */
    private static class Gap {
        double length; // Current segment length in this gap
        int index;     // Which gap (between arr[i] and arr[i+1])
        
        Gap(double length, int index) {
            this.length = length;
            this.index = index;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Minimise Max Distance Between Gas Stations ===\n");
        
        // Test Case 1: Uniform stations, add many stations
        System.out.println("Test Case 1:");
        int[] arr1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int k1 = 9;
        System.out.println("Stations: " + Arrays.toString(arr1));
        System.out.println("Additional stations to add: " + k1);
        
        double result1Heap = minimiseMaxDistance(arr1, k1);
        double result1BS = minimiseMaxDistanceBinarySearch(arr1, k1);
        
        System.out.println("\nMax Heap Result: " + result1Heap);
        System.out.println("Binary Search Result: " + result1BS);
        System.out.println("Expected: 0.5 (uniform placement)");
        System.out.println("Difference: " + Math.abs(result1Heap - result1BS));
        System.out.println();
        
        // Test Case 2: Uneven stations
        System.out.println("Test Case 2:");
        int[] arr2 = {1, 13, 17, 23};
        int k2 = 5;
        System.out.println("Stations: " + Arrays.toString(arr2));
        System.out.println("Additional stations to add: " + k2);
        
        double result2Heap = minimiseMaxDistance(arr2, k2);
        double result2BS = minimiseMaxDistanceBinarySearch(arr2, k2);
        
        System.out.println("\nMax Heap Result: " + result2Heap);
        System.out.println("Binary Search Result: " + result2BS);
        System.out.println("Expected: ~1.6667");
        System.out.println("Difference: " + Math.abs(result2Heap - result2BS));
        System.out.println();
        
        // Show detailed placement for Test Case 2
        System.out.println("Detailed placement for Test Case 2:");
        minimiseMaxDistanceWithPlacement(arr2, k2);
        
        // Test Case 3: Single gap
        System.out.println("Test Case 3:");
        int[] arr3 = {0, 100};
        int k3 = 10;
        System.out.println("Stations: " + Arrays.toString(arr3));
        System.out.println("Additional stations to add: " + k3);
        
        double result3Heap = minimiseMaxDistance(arr3, k3);
        double result3BS = minimiseMaxDistanceBinarySearch(arr3, k3);
        
        System.out.println("\nMax Heap Result: " + result3Heap);
        System.out.println("Binary Search Result: " + result3BS);
        System.out.println("Expected: 9.0909... (100/11)");
        System.out.println("Difference: " + Math.abs(result3Heap - result3BS));
        System.out.println();
        
        // Test Case 4: Already close stations
        System.out.println("Test Case 4:");
        int[] arr4 = {0, 5, 10, 15, 20};
        int k4 = 3;
        System.out.println("Stations: " + Arrays.toString(arr4));
        System.out.println("Additional stations to add: " + k4);
        
        double result4Heap = minimiseMaxDistance(arr4, k4);
        double result4BS = minimiseMaxDistanceBinarySearch(arr4, k4);
        
        System.out.println("\nMax Heap Result: " + result4Heap);
        System.out.println("Binary Search Result: " + result4BS);
        System.out.println("Difference: " + Math.abs(result4Heap - result4BS));
        System.out.println();
        
        // Test Case 5: Edge case - no stations to add
        System.out.println("Test Case 5:");
        int[] arr5 = {1, 3, 6, 10};
        int k5 = 0;
        System.out.println("Stations: " + Arrays.toString(arr5));
        System.out.println("Additional stations to add: " + k5);
        
        double result5Heap = minimiseMaxDistance(arr5, k5);
        double result5BS = minimiseMaxDistanceBinarySearch(arr5, k5);
        
        System.out.println("\nMax Heap Result: " + result5Heap);
        System.out.println("Binary Search Result: " + result5BS);
        System.out.println("Expected: 4.0 (largest gap: 10-6=4)");
        System.out.println("Difference: " + Math.abs(result5Heap - result5BS));
        System.out.println();
        
        // Performance comparison
        System.out.println("=== Performance Comparison ===");
        int[] largeArr = new int[1000];
        for (int i = 0; i < largeArr.length; i++) {
            largeArr[i] = i * 10 + (int)(Math.random() * 5); // Slightly random
        }
        int largeK = 10000;
        
        // Binary Search
        long start = System.currentTimeMillis();
        double bsResult = minimiseMaxDistanceBinarySearch(largeArr, largeK);
        long bsTime = System.currentTimeMillis() - start;
        
        // Max Heap (might be slower for large k)
        start = System.currentTimeMillis();
        double heapResult = minimiseMaxDistance(largeArr, largeK);
        long heapTime = System.currentTimeMillis() - start;
        
        System.out.println("Array size: " + largeArr.length);
        System.out.println("Stations to add: " + largeK);
        System.out.println("Binary Search: " + bsResult + " (" + bsTime + "ms)");
        System.out.println("Max Heap: " + heapResult + " (" + heapTime + "ms)");
        System.out.println("Difference: " + Math.abs(bsResult - heapResult));
        System.out.println("Binary Search is " + (heapTime/bsTime) + "x faster");
    }
}

/**
 * MATHEMATICAL INSIGHTS:
 * 
 * 1. Greedy Approach Proof:
 *    To minimize the maximum gap, always place the next station
 *    in the current largest gap. This is optimal because:
 *    - Any other placement would leave a gap at least as large
 *    - Dividing the largest gap reduces the maximum more than
 *      dividing any smaller gap
 * 
 * 2. Binary Search Validity:
 *    Let f(D) = "can place k stations with max gap ≤ D"
 *    - If f(D) is true, then f(D') is true for all D' > D
 *    - If f(D) is false, then f(D') is false for all D' < D
 *    This monotonic property allows binary search.
 * 
 * 3. Station Count Formula:
 *    For a gap of length L and max allowed distance D,
 *    stations needed = ceil(L/D) - 1
 *    Example: L=10, D=3 → ceil(10/3)=4 → stations=3
 *    This creates segments: 3, 3, 3, 1 (all ≤ 3)
 */

/**
 * TIME COMPLEXITY ANALYSIS:
 * 
 * Max Heap Approach:
 * - Build heap: O(n)
 * - k operations: O(k log n)
 * - Total: O(k log n)
 * 
 * Binary Search Approach:
 * - Binary search iterations: O(log(range/epsilon))
 * - Each iteration: O(n) to check all gaps
 * - Total: O(n log(range/epsilon))
 * 
 * For k >> n, binary search is better.
 * For small k, max heap might be acceptable.
 */

/**
 * VISUAL EXAMPLE:
 * 
 * Stations: [0, 5, 10, 20]
 * k = 3
 * 
 * Initial gaps: [5, 5, 10]
 * 
 * Step 1: Place in largest gap (10 between 10 and 20)
 *         New gaps: [5, 5, 5, 5]
 * 
 * Step 2: All gaps equal (5), place anywhere
 *         New gaps: [5, 2.5, 2.5, 5]
 * 
 * Step 3: Place in largest gap (5)
 *         New gaps: [2.5, 2.5, 2.5, 2.5, 2.5]
 * 
 * Final max distance: 2.5
 */

/**
 * EDGE CASES:
 * 
 * 1. k = 0: Return largest original gap
 * 2. n = 1: Only one station, max distance = 0
 * 3. Large k: Binary search handles efficiently
 * 4. Floating point precision: Use epsilon in binary search
 */

/**
 * RELATED PROBLEMS:
 * 
 * 1. Aggressive Cows: Maximize minimum distance
 * 2. Capacity To Ship Packages: Minimize maximum weight
 * 3. Koko Eating Bananas: Minimize eating speed
 * 4. Split Array Largest Sum: Minimize maximum sum
 * 
 * All follow "binary search on answer" pattern.
 */

/**
 * OPTIMIZATION TIPS:
 * 
 * 1. For binary search:
 *    - Use epsilon = 1e-6 for 6 decimal precision
 *    - Early exit in canPlaceStations
 *    - Use double for high precision
 * 
 * 2. For max heap:
 *    - Store original gap length to recalculate
 *    - Use comparator for max heap
 *    - Consider using array for stations count
 */

/**
 * PRACTICAL APPLICATIONS:
 * 
 * 1. Gas station placement optimization
 * 2. Cell tower placement
 * 3. WiFi hotspot placement
 * 4. Bus stop placement along a route
 * 5. Any resource placement along a line
 */