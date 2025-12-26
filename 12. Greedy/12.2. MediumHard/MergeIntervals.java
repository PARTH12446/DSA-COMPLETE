import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Merge Intervals (LeetCode 56)
 * 
 * Problem Statement:
 * Given an array of intervals where intervals[i] = [start_i, end_i],
 * merge all overlapping intervals and return an array of the non-overlapping
 * intervals that cover all the intervals in the input.
 * 
 * Example 1:
 * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6]
 * 
 * Example 2:
 * Input: intervals = [[1,4],[4,5]]
 * Output: [[1,5]]
 * Explanation: Intervals [1,4] and [4,5] are considered overlapping
 * 
 * Greedy Approach:
 * 1. Sort intervals by start time (ascending)
 * 2. Initialize with first interval as current
 * 3. Iterate through sorted intervals:
 *    - If next interval overlaps with current, merge (update end time)
 *    - If no overlap, add current to result and move to next interval
 * 4. Add last interval to result
 * 
 * Time Complexity: O(n log n) for sorting + O(n) for merging = O(n log n)
 * Space Complexity: O(n) for result (or O(1) excluding result space)
 */
public class MergeIntervals {

    /**
     * Greedy solution for merging intervals
     * 
     * @param intervals Array of intervals [start, end]
     * @return Merged non-overlapping intervals
     */
    public int[][] merge(int[][] intervals) {
        // Edge cases
        if (intervals == null || intervals.length == 0) {
            return new int[0][0];
        }
        
        // Step 1: Sort intervals by start time (ascending)
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Step 2: Initialize result list and current interval
        List<int[]> result = new ArrayList<>();
        int[] currentInterval = intervals[0];
        result.add(currentInterval);
        
        // Step 3: Iterate through intervals
        for (int i = 1; i < intervals.length; i++) {
            int[] nextInterval = intervals[i];
            
            // Check if intervals overlap
            // Overlap if: current end >= next start
            if (currentInterval[1] >= nextInterval[0]) {
                // Merge: update current interval's end to max of both ends
                currentInterval[1] = Math.max(currentInterval[1], nextInterval[1]);
            } else {
                // No overlap: start a new current interval
                currentInterval = nextInterval;
                result.add(currentInterval);
            }
        }
        
        // Step 4: Convert list to array
        return result.toArray(new int[result.size()][]);
    }
    
    /**
     * Alternative: In-place merging (modifies input)
     */
    public int[][] mergeInPlace(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new int[0][0];
        }
        
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        int mergeIndex = 0; // Index of last merged interval
        
        for (int i = 1; i < intervals.length; i++) {
            int[] current = intervals[mergeIndex];
            int[] next = intervals[i];
            
            if (current[1] >= next[0]) {
                // Merge: update current interval's end
                current[1] = Math.max(current[1], next[1]);
            } else {
                // Move to next position and copy next interval
                mergeIndex++;
                intervals[mergeIndex] = next;
            }
        }
        
        // Return only the merged intervals
        return Arrays.copyOf(intervals, mergeIndex + 1);
    }
    
    /**
     * Visualization helper: Show step-by-step merging
     */
    public void visualizeMerge(int[][] intervals) {
        System.out.println("\n=== Visualizing Interval Merging ===");
        System.out.print("Original intervals: ");
        for (int[] interval : intervals) {
            System.out.print("[" + interval[0] + "," + interval[1] + "] ");
        }
        System.out.println();
        
        // Create copy for sorting
        int[][] sorted = intervals.clone();
        Arrays.sort(sorted, (a, b) -> Integer.compare(a[0], b[0]));
        
        System.out.print("Sorted by start:    ");
        for (int[] interval : sorted) {
            System.out.print("[" + interval[0] + "," + interval[1] + "] ");
        }
        System.out.println("\n");
        
        List<int[]> result = new ArrayList<>();
        int[] current = sorted[0];
        result.add(current);
        
        System.out.println("Step-by-step merging:");
        System.out.printf("%-25s %-25s %s\n", "Current", "Next", "Action");
        System.out.println("-".repeat(80));
        
        for (int i = 1; i < sorted.length; i++) {
            int[] next = sorted[i];
            
            System.out.printf("[" + current[0] + "," + current[1] + "]".padRight(25) + 
                            "[" + next[0] + "," + next[1] + "]".padRight(25));
            
            if (current[1] >= next[0]) {
                // Overlap - merge
                System.out.print("OVERLAP: merge → [" + current[0] + "," + 
                               Math.max(current[1], next[1]) + "]");
                current[1] = Math.max(current[1], next[1]);
            } else {
                // No overlap
                System.out.print("NO OVERLAP: keep both");
                current = next;
                result.add(current);
            }
            System.out.println();
        }
        
        System.out.println("\nFinal merged intervals:");
        for (int[] interval : result) {
            System.out.print("[" + interval[0] + "," + interval[1] + "] ");
        }
        System.out.println();
        
        // Visual timeline
        visualizeTimeline(intervals, result);
    }
    
    /**
     * Helper to visualize intervals on a timeline
     */
    private void visualizeTimeline(int[][] intervals, List<int[]> merged) {
        System.out.println("\n--- Visual Timeline ---");
        
        // Find min and max values
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        
        for (int[] interval : intervals) {
            min = Math.min(min, interval[0]);
            max = Math.max(max, interval[1]);
        }
        
        // Original intervals
        System.out.println("Original intervals:");
        for (int[] interval : intervals) {
            System.out.printf("  [%2d,%2d]: ", interval[0], interval[1]);
            for (int t = min; t <= max; t++) {
                if (t >= interval[0] && t <= interval[1]) {
                    System.out.print("█");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        
        // Merged intervals
        System.out.println("\nMerged intervals:");
        for (int[] interval : merged) {
            System.out.printf("  [%2d,%2d]: ", interval[0], interval[1]);
            for (int t = min; t <= max; t++) {
                if (t >= interval[0] && t <= interval[1]) {
                    System.out.print("▣");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Check if two intervals overlap
     */
    private boolean overlap(int[] a, int[] b) {
        return !(a[1] < b[0] || b[1] < a[0]);
    }
    
    /**
     * Merge two overlapping intervals
     */
    private int[] mergeTwo(int[] a, int[] b) {
        return new int[]{Math.min(a[0], b[0]), Math.max(a[1], b[1])};
    }
    
    /**
     * Test cases and examples
     */
    public static void runTestCases() {
        MergeIntervals solver = new MergeIntervals();
        
        Object[][] testCases = {
            // {intervals, expected output}
            {new int[][]{{1,3},{2,6},{8,10},{15,18}}, new int[][]{{1,6},{8,10},{15,18}}},
            {new int[][]{{1,4},{4,5}}, new int[][]{{1,5}}},
            {new int[][]{{1,4},{0,4}}, new int[][]{{0,4}}},
            {new int[][]{{1,4},{0,1}}, new int[][]{{0,4}}},
            {new int[][]{{1,4},{0,0}}, new int[][]{{0,0},{1,4}}},
            {new int[][]{{1,4},{0,2},{3,5}}, new int[][]{{0,5}}},
            {new int[][]{{2,3},{4,5},{6,7},{8,9},{1,10}}, new int[][]{{1,10}}},
            {new int[][]{}, new int[][]{}},
            {new int[][]{{1,2}}, new int[][]{{1,2}}},
            {new int[][]{{1,4},{2,3}}, new int[][]{{1,4}}},
        };
        
        System.out.println("=== Test Cases for Merge Intervals ===");
        System.out.printf("%-40s %-40s %s\n", "Input", "Expected", "Status");
        System.out.println("-".repeat(100));
        
        for (Object[] test : testCases) {
            int[][] intervals = (int[][]) test[0];
            int[][] expected = (int[][]) test[1];
            
            int[][] result = solver.merge(intervals);
            
            String inputStr = arrayToString(intervals);
            String expectedStr = arrayToString(expected);
            String resultStr = arrayToString(result);
            
            boolean passed = arraysEqual(expected, result);
            System.out.printf("%-40s %-40s %s\n", 
                inputStr, expectedStr, passed ? "✓" : "✗");
        }
    }
    
    private static String arrayToString(int[][] arr) {
        if (arr == null) return "null";
        if (arr.length == 0) return "[]";
        
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append("[").append(arr[i][0]).append(",").append(arr[i][1]).append("]");
            if (i < arr.length - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
    
    private static boolean arraysEqual(int[][] a, int[][] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (a[i][0] != b[i][0] || a[i][1] != b[i][1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // Number of test cases
        
        while (t-- > 0) {
            int n = sc.nextInt();
            int[][] intervals;
            
            if (n == 0) {
                intervals = new int[0][2];
            } else {
                intervals = new int[n][2];
                for (int i = 0; i < n; i++) {
                    intervals[i][0] = sc.nextInt();
                    intervals[i][1] = sc.nextInt();
                }
            }
            
            MergeIntervals solver = new MergeIntervals();
            int[][] result = solver.merge(intervals);
            
            for (int[] interval : result) {
                System.out.print("[" + interval[0] + "," + interval[1] + "] ");
            }
            System.out.println();
            
            // Uncomment for visualization
            // solver.visualizeMerge(intervals);
        }
        
        sc.close();
        
        // Uncomment to run test cases
        // runTestCases();
    }
}