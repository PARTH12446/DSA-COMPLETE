// Problem: LeetCode <ID>. <Title>
/*
 * PROBLEM: Merge Intervals (LeetCode 56)
 * 
 * Given an array of intervals where intervals[i] = [starti, endi],
 * merge all overlapping intervals, and return an array of the 
 * non-overlapping intervals that cover all the intervals in the input.
 * 
 * DEFINITION:
 * Two intervals [a,b] and [c,d] overlap if c <= b.
 * When merging, the merged interval is [min(a,c), max(b,d)].
 * 
 * CONSTRAINTS:
 * - 1 <= intervals.length <= 10^4
 * - intervals[i].length == 2
 * - 0 <= starti <= endi <= 10^4
 * 
 * APPROACH: Sort + Linear Scan (Greedy)
 * 
 * INTUITION:
 * 1. Sort intervals by start time to bring overlapping intervals together
 * 2. Iterate through sorted intervals
 * 3. If current interval overlaps with last merged interval, merge them
 * 4. Otherwise, add as new interval
 * 
 * TIME COMPLEXITY: O(n log n)
 *   - Sorting: O(n log n) dominates
 *   - Merging: O(n) linear scan
 * 
 * SPACE COMPLEXITY: O(n) [or O(1) if modifying input]
 *   - Output list of merged intervals
 *   - Sorting uses O(log n) space for recursion stack
 */

import java.util.*;

public class MergeOverlapsLeetHard {

    /**
     * Main solution: Merge overlapping intervals
     * 
     * @param intervals 2D array of [start, end] intervals
     * @return List of merged non-overlapping intervals
     */
    public List<int[]> merge(int[][] intervals) {
        // Edge case: empty input
        if (intervals == null || intervals.length == 0) {
            return new ArrayList<>();
        }
        
        // Step 1: Sort intervals by start time
        // Using lambda comparator for clarity
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Step 2: Merge intervals
        List<int[]> merged = new ArrayList<>();
        merged.add(intervals[0].clone()); // Add first interval
        
        for (int i = 1; i < intervals.length; i++) {
            int[] lastInterval = merged.get(merged.size() - 1);
            int[] currentInterval = intervals[i];
            
            // Check for overlap: current starts before or at last ends
            if (currentInterval[0] <= lastInterval[1]) {
                // Overlap exists, merge by extending end time
                lastInterval[1] = Math.max(lastInterval[1], currentInterval[1]);
            } else {
                // No overlap, add as new interval
                merged.add(currentInterval.clone());
            }
        }
        
        return merged;
    }
    
    /**
     * Alternative: Using while loop and pointer
     */
    public List<int[]> mergeWithPointer(int[][] intervals) {
        if (intervals.length == 0) return new ArrayList<>();
        
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        List<int[]> result = new ArrayList<>();
        
        int i = 0;
        while (i < intervals.length) {
            int start = intervals[i][0];
            int end = intervals[i][1];
            
            // Merge all overlapping intervals starting from i
            while (i < intervals.length - 1 && end >= intervals[i + 1][0]) {
                i++;
                end = Math.max(end, intervals[i][1]);
            }
            
            result.add(new int[]{start, end});
            i++;
        }
        
        return result;
    }
    
    /**
     * In-place merging (modifies input array)
     * Returns array instead of List
     */
    public int[][] mergeInPlace(int[][] intervals) {
        if (intervals.length <= 1) return intervals;
        
        // Sort by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        int writeIndex = 0; // Index to write merged intervals
        
        for (int i = 1; i < intervals.length; i++) {
            int[] last = intervals[writeIndex];
            int[] current = intervals[i];
            
            if (current[0] <= last[1]) {
                // Merge: extend end of last interval
                last[1] = Math.max(last[1], current[1]);
            } else {
                // No overlap, move to next position
                writeIndex++;
                intervals[writeIndex] = current;
            }
        }
        
        // Return only the merged portion
        return Arrays.copyOfRange(intervals, 0, writeIndex + 1);
    }
    
    /**
     * Using LinkedList for efficient add/remove at ends
     */
    public List<int[]> mergeWithLinkedList(int[][] intervals) {
        if (intervals.length == 0) return new LinkedList<>();
        
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        LinkedList<int[]> merged = new LinkedList<>();
        merged.add(intervals[0]);
        
        for (int i = 1; i < intervals.length; i++) {
            int[] last = merged.getLast();
            int[] current = intervals[i];
            
            if (current[0] <= last[1]) {
                last[1] = Math.max(last[1], current[1]);
            } else {
                merged.add(current);
            }
        }
        
        return merged;
    }
    
    /**
     * Test method with LeetCode examples
     */
    public static void main(String[] args) {
        MergeOverlapsLeetHard solver = new MergeOverlapsLeetHard();
        
        // Test case 1: LeetCode Example 1
        int[][] intervals1 = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        System.out.println("Test 1: [[1,3],[2,6],[8,10],[15,18]]");
        List<int[]> result1 = solver.merge(intervals1);
        printResult(result1);
        System.out.println("Expected: [[1,6],[8,10],[15,18]]");
        
        // Test case 2: LeetCode Example 2
        int[][] intervals2 = {{1, 4}, {4, 5}};
        System.out.println("\nTest 2: [[1,4],[4,5]]");
        List<int[]> result2 = solver.merge(intervals2);
        printResult(result2);
        System.out.println("Expected: [[1,5]]");
        
        // Test case 3: Single interval
        int[][] intervals3 = {{1, 3}};
        System.out.println("\nTest 3: [[1,3]]");
        List<int[]> result3 = solver.merge(intervals3);
        printResult(result3);
        System.out.println("Expected: [[1,3]]");
        
        // Test case 4: Empty input
        int[][] intervals4 = {};
        System.out.println("\nTest 4: []");
        List<int[]> result4 = solver.merge(intervals4);
        printResult(result4);
        System.out.println("Expected: []");
        
        // Test case 5: All intervals overlap
        int[][] intervals5 = {{1, 4}, {2, 5}, {3, 6}, {4, 7}};
        System.out.println("\nTest 5: [[1,4],[2,5],[3,6],[4,7]]");
        List<int[]> result5 = solver.merge(intervals5);
        printResult(result5);
        System.out.println("Expected: [[1,7]]");
        
        // Test case 6: Nested intervals
        int[][] intervals6 = {{1, 10}, {2, 6}, {3, 5}, {7, 9}};
        System.out.println("\nTest 6: [[1,10],[2,6],[3,5],[7,9]]");
        List<int[]> result6 = solver.merge(intervals6);
        printResult(result6);
        System.out.println("Expected: [[1,10]]");
        
        // Test case 7: Intervals that just touch
        int[][] intervals7 = {{1, 2}, {2, 3}, {3, 4}};
        System.out.println("\nTest 7: [[1,2],[2,3],[3,4]]");
        List<int[]> result7 = solver.merge(intervals7);
        printResult(result7);
        System.out.println("Expected: [[1,4]]");
        
        // Performance test
        System.out.println("\n=== Performance Test ===");
        int[][] largeIntervals = generateLargeIntervals(100000);
        
        long startTime = System.currentTimeMillis();
        List<int[]> perfResult = solver.merge(largeIntervals);
        long endTime = System.currentTimeMillis();
        System.out.println("Time for 100,000 intervals: " + (endTime - startTime) + "ms");
        System.out.println("Number of merged intervals: " + perfResult.size());
        
        // Compare different approaches
        System.out.println("\n=== Comparing Approaches ===");
        int[][] testCase = {{1, 3}, {2, 4}, {5, 7}, {6, 8}};
        
        System.out.println("Original: ");
        printResult(solver.merge(testCase));
        System.out.println("With Pointer: ");
        printResult(solver.mergeWithPointer(testCase));
        System.out.println("With LinkedList: ");
        printResult(solver.mergeWithLinkedList(testCase));
    }
    
    /**
     * Helper to generate large interval arrays for testing
     */
    private static int[][] generateLargeIntervals(int size) {
        int[][] intervals = new int[size][2];
        Random rand = new Random();
        
        for (int i = 0; i < size; i++) {
            int start = rand.nextInt(10000);
            int end = start + rand.nextInt(100);
            intervals[i][0] = start;
            intervals[i][1] = end;
        }
        
        return intervals;
    }
    
    /**
     * Helper to print result
     */
    private static void printResult(List<int[]> intervals) {
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
     * Why sorting is crucial:
     * 
     * Without sorting, we can't guarantee that overlapping intervals
     * will be adjacent. Consider:
     *   [[3,5], [1,4], [2,6]]
     * 
     * Without sorting:
     *   Compare [3,5] and [1,4] ? merge to [1,5]
     *   Compare [1,5] and [2,6] ? merge to [1,6]
     *   Result: [[1,6]] ?
     * 
     * But another example:
     *   [[1,10], [2,3], [11,12]]
     * Without sorting works, but generally unsafe.
     * 
     * With sorting: intervals are processed in order of start time,
     * ensuring that if interval B starts after A, and B overlaps A,
     * then all intervals between A and B in sorted order also overlap.
     */
    
    /**
     * Step-by-step example:
     * 
     * Input: [[1,3], [2,6], [8,10], [15,18]]
     * 
     * 1. Sort (already sorted)
     * 2. Initialize: result = [[1,3]]
     * 
     * Iteration 1 (i=1): current = [2,6], last = [1,3]
     *   Check: 2 <= 3 ? overlap
     *   Merge: last[1] = max(3,6) = 6
     *   Result: [[1,6]]
     * 
     * Iteration 2 (i=2): current = [8,10], last = [1,6]
     *   Check: 8 <= 6? No ? no overlap
     *   Add new: result = [[1,6], [8,10]]
     * 
     * Iteration 3 (i=3): current = [15,18], last = [8,10]
     *   Check: 15 <= 10? No ? no overlap
     *   Add new: result = [[1,6], [8,10], [15,18]]
     */
    
    /**
     * Overlap condition explained:
     * 
     * For sorted intervals [a,b] and [c,d] where a <= c:
     * 
     * 1. No overlap: c > b
     *    [a, b]   [c, d]
     *           gap
     * 
     * 2. Overlap: c <= b
     *    [a,     b]
     *        [c,     d]
     *    Merged: [a, max(b,d)]
     * 
     * 3. Nested: c >= a and d <= b
     *    [a,         b]
     *        [c, d]
     *    Merged: [a, b]
     * 
     * 4. Adjacent: c == b
     *    [a, b][b, d]
     *    Typically considered overlapping
     *    Merged: [a, d]
     */
    
    /**
     * Edge Cases:
     * 
     * 1. Input validation:
     *    - Null check
     *    - Empty array
     *    - Single interval
     * 
     * 2. Duplicate intervals:
     *    - [[1,2], [1,2]] ? merge to [1,2]
     * 
     * 3. Large intervals covering many:
     *    - [[1,100], [2,3], [4,5], [6,7]] ? [1,100]
     * 
     * 4. Intervals with same start:
     *    - [[1,4], [1,3]] ? merge to [1,4]
     *    - Sorting handles this (end times may vary)
     */
    
    /**
     * Common Mistakes:
     * 
     * 1. Forgetting to clone intervals:
     *    - Modifying input array when shouldn't
     *    - Solution: Use .clone() or create new array
     * 
     * 2. Incorrect overlap condition:
     *    - Using < instead of <= for adjacent intervals
     *    - Problem may specify if adjacent should merge
     * 
     * 3. Not handling empty input:
     *    - Return empty list, not null
     * 
     * 4. Modifying while iterating:
     *    - Be careful when modifying list during iteration
     * 
     * 5. Integer overflow in comparator:
     *    - Use Integer.compare(a, b) instead of a - b
     */
    
    /**
     * Optimization Tips:
     * 
     * 1. Early exit for sorted input:
     *    - Check if already sorted before sorting
     *    - But generally not worth the check
     * 
     * 2. Use Arrays.copyOfRange for in-place:
     *    - Avoids creating new data structures
     * 
     * 3. Choose appropriate data structure:
     *    - ArrayList for random access
     *    - LinkedList for frequent add/remove at ends
     */
    
    /**
     * Related LeetCode Problems:
     * 
     * 1. Insert Interval (57) - Add new interval to sorted list
     * 2. Meeting Rooms (252) - Check if person can attend all meetings
     * 3. Meeting Rooms II (253) - Minimum rooms needed
     * 4. Non-overlapping Intervals (435) - Remove min intervals to make non-overlap
     * 5. Minimum Number of Arrows to Burst Balloons (452)
     */
    
    /**
     * Applications:
     * 
     * 1. Calendar/scheduling apps
     * 2. Resource allocation systems
     * 3. Database range queries optimization
     * 4. Genomic sequence alignment
     * 5. Network bandwidth management
     */
    
    /**
     * Time Complexity Analysis:
     * 
     * Sorting: O(n log n)
     *   - Java's Arrays.sort() uses Dual-Pivot Quicksort
     *   - For object arrays, uses TimSort (stable, adaptive)
     * 
     * Merging: O(n)
     *   - Single pass through sorted intervals
     *   - Constant work per interval
     * 
     * Total: O(n log n) dominated by sorting
     */
    
    /**
     * Space Complexity Analysis:
     * 
     * Worst case: O(n)
     *   - When no intervals overlap
     *   - Output list contains all n intervals
     * 
     * Best case: O(1)
     *   - All intervals overlap into one
     *   - But still need O(log n) for sorting recursion
     * 
     * Average case: O(k) where k = number of merged intervals
     */
    
    /**
     * Alternative Approaches:
     * 
     * 1. Sweep Line Algorithm:
     *    - Process all start and end points
     *    - Keep count of active intervals
     *    - More complex but useful for counting overlaps
     * 
     * 2. Interval Tree:
     *    - Efficient for dynamic interval operations
     *    - Overkill for simple merging
     * 
     * 3. Segment Tree:
     *    - For interval queries on static data
     *    - Can answer range queries efficiently
     */
    
    /**
     * Testing Strategy:
     * 
     * 1. Basic cases from problem statement
     * 2. Edge cases (empty, single, all same)
     * 3. Random large inputs for performance
     * 4. Verify with brute force for small inputs
     * 5. Check for memory usage with large inputs
     */
}
