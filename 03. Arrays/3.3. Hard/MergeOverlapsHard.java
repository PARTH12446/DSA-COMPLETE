// Problem: LeetCode <ID>. <Title>
/*
 * PROBLEM: Merge Overlapping Intervals (Coding Ninjas / LeetCode 56)
 * 
 * Given an array of intervals where intervals[i] = [start_i, end_i],
 * merge all overlapping intervals, and return an array of the 
 * non-overlapping intervals that cover all the intervals in the input.
 * 
 * DEFINITION:
 * Two intervals [a, b] and [c, d] overlap if:
 *   c <= b (start of second <= end of first)
 * After merging overlapping intervals: [min(a,c), max(b,d)]
 * 
 * CONSTRAINTS:
 * - 1 = intervals.length = 10^5
 * - intervals[i].length == 2
 * - 0 = start_i = end_i = 10^4
 * 
 * APPROACH: Sorting + Linear Scan
 * 
 * INTUITION:
 * 1. Sort intervals by start time
 * 2. Iterate through sorted intervals
 * 3. If current interval overlaps with previous (last merged), merge them
 * 4. Otherwise, add current interval as new merged interval
 * 
 * TIME COMPLEXITY: O(n log n)
 *   - Sorting: O(n log n)
 *   - Merging: O(n)
 * 
 * SPACE COMPLEXITY: O(n) [or O(1) if modifying input]
 *   - Output list of merged intervals
 *   - Sorting uses O(log n) space (in-place sort)
 * 
 * EDGE CASES:
 * - Single interval
 * - No overlaps
 * - All intervals overlap
 * - Nested intervals ([1,5] and [2,3])
 * - Adjacent intervals ([1,2] and [2,3]) - typically considered overlapping
 */

import java.util.*;

public class MergeOverlapsHard {

    /**
     * Merge overlapping intervals
     * 
     * @param arr 2D array of intervals [start, end]
     * @return List of merged non-overlapping intervals
     */
    public static List<int[]> mergeOverlappingIntervals(int[][] arr) {
        // Step 1: Sort intervals by start time
        Arrays.sort(arr, Comparator.comparingInt(o -> o[0]));
        
        // Step 2: Merge intervals
        List<int[]> merged = new ArrayList<>();
        
        for (int[] interval : arr) {
            // If list is empty or no overlap, add new interval
            if (merged.isEmpty() || interval[0] > merged.get(merged.size() - 1)[1]) {
                merged.add(interval.clone()); // Clone to avoid modifying input
            } 
            // Overlap exists, merge with last interval
            else {
                int[] last = merged.get(merged.size() - 1);
                last[1] = Math.max(last[1], interval[1]); // Extend end time
            }
        }
        
        return merged;
    }
    
    /**
     * Alternative: Using Stack for merging
     */
    public static List<int[]> mergeOverlappingIntervalsStack(int[][] intervals) {
        if (intervals.length <= 1) {
            List<int[]> result = new ArrayList<>();
            for (int[] interval : intervals) {
                result.add(interval.clone());
            }
            return result;
        }
        
        // Sort intervals
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));
        
        Stack<int[]> stack = new Stack<>();
        stack.push(intervals[0]);
        
        for (int i = 1; i < intervals.length; i++) {
            int[] top = stack.peek();
            
            if (intervals[i][0] <= top[1]) {
                // Overlap, merge intervals
                top[1] = Math.max(top[1], intervals[i][1]);
            } else {
                // No overlap, push new interval
                stack.push(intervals[i]);
            }
        }
        
        // Convert stack to list
        List<int[]> result = new ArrayList<>(stack);
        return result;
    }
    
    /**
     * Merge intervals without extra space (modifies input)
     */
    public static int[][] mergeOverlappingIntervalsInPlace(int[][] intervals) {
        if (intervals.length == 0) return new int[0][2];
        
        // Sort intervals
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));
        
        int mergeIndex = 0; // Index of last merged interval
        
        for (int i = 1; i < intervals.length; i++) {
            int[] current = intervals[i];
            int[] lastMerged = intervals[mergeIndex];
            
            if (current[0] <= lastMerged[1]) {
                // Overlap, merge into last merged interval
                lastMerged[1] = Math.max(lastMerged[1], current[1]);
            } else {
                // No overlap, move to next position
                mergeIndex++;
                intervals[mergeIndex] = current;
            }
        }
        
        // Copy only merged intervals
        return Arrays.copyOfRange(intervals, 0, mergeIndex + 1);
    }
    
    /**
     * Brute force approach (O(n²) time) for verification
     */
    public static List<int[]> mergeOverlappingIntervalsBruteForce(int[][] intervals) {
        List<int[]> result = new ArrayList<>();
        boolean[] merged = new boolean[intervals.length];
        
        for (int i = 0; i < intervals.length; i++) {
            if (merged[i]) continue;
            
            int start = intervals[i][0];
            int end = intervals[i][1];
            merged[i] = true;
            
            // Try to merge with all other intervals
            for (int j = i + 1; j < intervals.length; j++) {
                if (merged[j]) continue;
                
                int currStart = intervals[j][0];
                int currEnd = intervals[j][1];
                
                // Check for overlap
                if (currStart <= end && currEnd >= start) {
                    start = Math.min(start, currStart);
                    end = Math.max(end, currEnd);
                    merged[j] = true;
                }
            }
            
            result.add(new int[]{start, end});
        }
        
        // Sort result by start time
        result.sort(Comparator.comparingInt(o -> o[0]));
        return result;
    }
    
    /**
     * Test method with examples
     */
    public static void main(String[] args) {
        // Test case 1: Overlapping intervals
        int[][] intervals1 = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        System.out.println("Test 1: [[1,3],[2,6],[8,10],[15,18]]");
        List<int[]> result1 = mergeOverlappingIntervals(intervals1);
        printIntervals(result1);
        System.out.println("Expected: [[1,6],[8,10],[15,18]]");
        
        // Test case 2: All intervals overlap
        int[][] intervals2 = {{1, 4}, {4, 5}, {5, 9}, {7, 10}};
        System.out.println("\nTest 2: [[1,4],[4,5],[5,9],[7,10]]");
        List<int[]> result2 = mergeOverlappingIntervals(intervals2);
        printIntervals(result2);
        System.out.println("Expected: [[1,10]]");
        
        // Test case 3: No overlaps
        int[][] intervals3 = {{1, 2}, {3, 4}, {5, 6}};
        System.out.println("\nTest 3: [[1,2],[3,4],[5,6]]");
        List<int[]> result3 = mergeOverlappingIntervals(intervals3);
        printIntervals(result3);
        System.out.println("Expected: [[1,2],[3,4],[5,6]]");
        
        // Test case 4: Single interval
        int[][] intervals4 = {{1, 3}};
        System.out.println("\nTest 4: [[1,3]]");
        List<int[]> result4 = mergeOverlappingIntervals(intervals4);
        printIntervals(result4);
        System.out.println("Expected: [[1,3]]");
        
        // Test case 5: Empty input
        int[][] intervals5 = {};
        System.out.println("\nTest 5: []");
        List<int[]> result5 = mergeOverlappingIntervals(intervals5);
        printIntervals(result5);
        System.out.println("Expected: []");
        
        // Test case 6: Nested intervals
        int[][] intervals6 = {{1, 10}, {2, 5}, {3, 4}, {6, 8}};
        System.out.println("\nTest 6: [[1,10],[2,5],[3,4],[6,8]]");
        List<int[]> result6 = mergeOverlappingIntervals(intervals6);
        printIntervals(result6);
        System.out.println("Expected: [[1,10]]");
        
        // Test case 7: Adjacent intervals (touch at endpoints)
        int[][] intervals7 = {{1, 2}, {2, 3}, {3, 4}};
        System.out.println("\nTest 7: [[1,2],[2,3],[3,4]]");
        List<int[]> result7 = mergeOverlappingIntervals(intervals7);
        printIntervals(result7);
        System.out.println("Expected: [[1,4]]");
        
        // Performance test
        System.out.println("\n=== Performance Test ===");
        int[][] largeIntervals = new int[100000][2];
        Random rand = new Random();
        for (int i = 0; i < largeIntervals.length; i++) {
            int start = rand.nextInt(10000);
            int end = start + rand.nextInt(100);
            largeIntervals[i][0] = start;
            largeIntervals[i][1] = end;
        }
        
        long startTime = System.currentTimeMillis();
        List<int[]> perfResult = mergeOverlappingIntervals(largeIntervals);
        long endTime = System.currentTimeMillis();
        System.out.println("Time for 100,000 intervals: " + (endTime - startTime) + "ms");
        System.out.println("Number of merged intervals: " + perfResult.size());
        
        // Verify with brute force on small input
        System.out.println("\n=== Verification with Brute Force ===");
        int[][] testIntervals = {{1, 3}, {2, 4}, {5, 7}, {6, 8}};
        List<int[]> algoResult = mergeOverlappingIntervals(testIntervals);
        List<int[]> bruteResult = mergeOverlappingIntervalsBruteForce(testIntervals);
        System.out.println("Algorithm result: ");
        printIntervals(algoResult);
        System.out.println("Brute force result: ");
        printIntervals(bruteResult);
        System.out.println("Match: " + intervalsEqual(algoResult, bruteResult));
    }
    
    /**
     * Helper method to print intervals
     */
    private static void printIntervals(List<int[]> intervals) {
        System.out.print("[");
        for (int i = 0; i < intervals.size(); i++) {
            int[] interval = intervals.get(i);
            System.out.print("[" + interval[0] + "," + interval[1] + "]");
            if (i < intervals.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
    
    /**
     * Helper to compare two lists of intervals
     */
    private static boolean intervalsEqual(List<int[]> list1, List<int[]> list2) {
        if (list1.size() != list2.size()) return false;
        for (int i = 0; i < list1.size(); i++) {
            int[] interval1 = list1.get(i);
            int[] interval2 = list2.get(i);
            if (interval1[0] != interval2[0] || interval1[1] != interval2[1]) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Why sorting is necessary:
     * 
     * Without sorting, we might miss some merges:
     * Example: [[1,4], [0,1], [3,5]]
     * Without sort: 
     *   Compare [1,4] and [0,1] ? merge to [0,4]
     *   Compare [0,4] and [3,5] ? merge to [0,5]
     * 
     * But what about [[3,5], [0,1], [1,4]]?
     * Without sort:
     *   Compare [3,5] and [0,1] ? no overlap
     *   Compare [3,5] and [1,4] ? merge to [1,5]
     *   Result: [[3,5], [1,5]] WRONG!
     * 
     * With sorting: [[0,1], [1,4], [3,5]]
     *   Merge [0,1] and [1,4] ? [0,4]
     *   Merge [0,4] and [3,5] ? [0,5]
     *   Result: [[0,5]] CORRECT!
     */
    
    /**
     * Step-by-step merging process:
     * 
     * Input: [[1,3], [2,6], [8,10], [15,18]]
     * 
     * Step 1: Sort (already sorted)
     * Step 2: Initialize result = []
     * 
     * Iteration 1: interval = [1,3]
     *   result empty ? add [1,3]
     *   result = [[1,3]]
     * 
     * Iteration 2: interval = [2,6]
     *   Compare with last [1,3]: 2 <= 3 ? overlap
     *   Merge: max(3,6) = 6 ? [1,6]
     *   result = [[1,6]]
     * 
     * Iteration 3: interval = [8,10]
     *   Compare with last [1,6]: 8 > 6 ? no overlap
     *   Add [8,10]
     *   result = [[1,6], [8,10]]
     * 
     * Iteration 4: interval = [15,18]
     *   Compare with last [8,10]: 15 > 10 ? no overlap
     *   Add [15,18]
     *   result = [[1,6], [8,10], [15,18]]
     */
    
    /**
     * Overlap conditions:
     * 
     * Two intervals [a,b] and [c,d] overlap if:
     * 1. c <= b (start of second <= end of first)
     * AND
     * 2. a <= d (start of first <= end of second)
     * 
     * But since we sort by start time, a = c always.
     * So we only need to check: c = b
     * 
     * Cases:
     * 1. No overlap: c > b
     *    [a, b] [c, d] where c > b
     * 
     * 2. Overlap: c = b
     *    [a, b] [c, d] where c = b
     *    Merged: [a, max(b,d)]
     * 
     * 3. Nested: c = a and d = b
     *    [a, b] contains [c, d]
     *    Merged: [a, b] (unchanged)
     * 
     * 4. Adjacent: c == b
     *    [a, b] [b, d] ? typically considered overlapping
     *    Merged: [a, d]
     */
    
    /**
     * Edge Cases:
     * 
     * 1. Empty input: Return empty list
     * 2. Single interval: Return that interval
     * 3. Intervals with same start time:
     *    Sort by start, then merge based on end
     *    Example: [[1,4], [1,3]] ? merge to [1,4]
     * 
     * 4. Large intervals covering many small ones:
     *    Example: [[1,100], [2,3], [4,5], [6,7]]
     *    Result: [[1,100]]
     * 
     * 5. Negative intervals (if allowed):
     *    Same logic applies
     */
    
    /**
     * Common Mistakes:
     * 
     * 1. Not sorting intervals first
     * 2. Incorrect overlap condition
     * 3. Modifying input array when shouldn't
     * 4. Forgetting to handle empty input
     * 5. Using Integer.MAX_VALUE/MIN_VALUE incorrectly
     * 6. Not cloning intervals when adding to result
     */
    
    /**
     * Optimization Opportunities:
     * 
     * 1. In-place merging:
     *    - Modify input array to save space
     *    - Need to track valid indices
     * 
     * 2. Early termination:
     *    - If intervals are already sorted, skip sorting
     *    - But generally can't assume
     * 
     * 3. Parallel sorting:
     *    - For very large n, use parallel sort
     *    - Arrays.parallelSort() in Java
     */
    
    /**
     * Related Problems:
     * 
     * 1. Insert Interval (LeetCode 57)
     * 2. Meeting Rooms (LeetCode 252)
     * 3. Meeting Rooms II (LeetCode 253)
     * 4. Non-overlapping Intervals (LeetCode 435)
     * 5. Minimum Number of Arrows to Burst Balloons (LeetCode 452)
     */
    
    /**
     * Applications:
     * 
     * 1. Calendar/scheduling systems
     * 2. Resource allocation
     * 3. Database query optimization (range queries)
     * 4. Genomics (gene sequence overlaps)
     * 5. Network bandwidth allocation
     */
    
    /**
     * Time Complexity Analysis:
     * 
     * Sorting: O(n log n) where n = number of intervals
     *   - Java uses TimSort (adaptive, stable)
     *   - Average case O(n log n), worst case O(n log n)
     * 
     * Merging: O(n)
     *   - Single pass through sorted intervals
     *   - Each interval processed once
     * 
     * Total: O(n log n) dominated by sorting
     */
    
    /**
     * Space Complexity Analysis:
     * 
     * Sorting: O(log n) for recursion stack (in-place sort)
     * Result: O(k) where k = n (number of merged intervals)
     * 
     * Total: O(n) in worst case (no merges)
     */
    
    /**
     * Alternative Data Structures:
     * 
     * 1. Interval Tree:
     *    - Efficient for dynamic interval operations
     *    - O(log n) for insert/delete/query
     *    - Overkill for simple merging
     * 
     * 2. Sweep Line Algorithm:
     *    - Process start and end points separately
     *    - Useful for counting overlaps
     */
}
