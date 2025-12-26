import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Insert Interval (LeetCode 57)
 * 
 * Problem Statement:
 * You are given an array of non-overlapping intervals where intervals[i] = [start_i, end_i]
 * represent the start and end of the i-th interval, and intervals is sorted in ascending order
 * by start_i. You are also given an interval newInterval = [start, end] that represents the
 * start and end of another interval.
 * 
 * Insert newInterval into intervals such that intervals is still sorted in ascending order by
 * start_i and intervals still does not have any overlapping intervals (merge overlapping intervals
 * if necessary).
 * 
 * Example 1:
 * Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
 * Output: [[1,5],[6,9]]
 * Explanation: New interval [2,5] overlaps with [1,3], so they merge to [1,5]
 * 
 * Example 2:
 * Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
 * Output: [[1,2],[3,10],[12,16]]
 * Explanation: New interval [4,8] overlaps with [3,5],[6,7],[8,10] → merge to [3,10]
 * 
 * Greedy Three-Phase Approach:
 * 1. Add all intervals that end before newInterval starts (non-overlapping left)
 * 2. Merge all overlapping intervals with newInterval
 * 3. Add all intervals that start after newInterval ends (non-overlapping right)
 * 
 * Time Complexity: O(n) - single pass through intervals
 * Space Complexity: O(n) - for result list
 */
public class InsertInterval {

    /**
     * Greedy solution for inserting interval
     * 
     * @param intervals Sorted array of non-overlapping intervals
     * @param newInterval Interval to insert
     * @return Merged intervals with newInterval inserted
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int n = intervals.length;
        int i = 0;
        
        // Phase 1: Add all intervals that end before newInterval starts
        // These intervals don't overlap with newInterval and come before it
        while (i < n && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            i++;
        }
        
        // Phase 2: Merge all intervals that overlap with newInterval
        // An interval overlaps if it starts before or at newInterval's end
        while (i < n && intervals[i][0] <= newInterval[1]) {
            // Expand newInterval to cover the overlap
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }
        // Add the merged interval
        result.add(newInterval);
        
        // Phase 3: Add remaining intervals (those that start after newInterval ends)
        while (i < n) {
            result.add(intervals[i]);
            i++;
        }
        
        // Convert List to array
        return result.toArray(new int[result.size()][]);
    }
    
    /**
     * Alternative: Binary search approach
     * Find insertion point using binary search, then merge
     */
    public int[][] insertBinarySearch(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int n = intervals.length;
        
        // Find position to insert using binary search
        int left = 0, right = n - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (intervals[mid][0] < newInterval[0]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        // Insert position is at 'left'
        // Now merge as needed
        for (int i = 0; i < left; i++) {
            result.add(intervals[i]);
        }
        
        // Merge with intervals around insertion point
        int[] current = newInterval.clone();
        while (left < n && intervals[left][0] <= current[1]) {
            current[0] = Math.min(current[0], intervals[left][0]);
            current[1] = Math.max(current[1], intervals[left][1]);
            left++;
        }
        result.add(current);
        
        // Add remaining intervals
        while (left < n) {
            result.add(intervals[left]);
            left++;
        }
        
        return result.toArray(new int[result.size()][]);
    }
    
    /**
     * Visualization helper: Show step-by-step merging process
     */
    public void visualizeInsertInterval(int[][] intervals, int[] newInterval) {
        System.out.println("\n=== Visualizing Interval Insertion ===");
        System.out.print("Original intervals: ");
        for (int[] interval : intervals) {
            System.out.print("[" + interval[0] + "," + interval[1] + "] ");
        }
        System.out.println("\nNew interval to insert: [" + newInterval[0] + "," + newInterval[1] + "]");
        
        List<int[]> result = new ArrayList<>();
        int n = intervals.length;
        int i = 0;
        
        System.out.println("\n--- Phase 1: Add non-overlapping intervals before ---");
        while (i < n && intervals[i][1] < newInterval[0]) {
            System.out.println("  Interval [" + intervals[i][0] + "," + intervals[i][1] + 
                             "] ends before newInterval starts (" + newInterval[0] + ") → add as-is");
            result.add(intervals[i]);
            i++;
        }
        
        System.out.println("\n--- Phase 2: Merge overlapping intervals ---");
        System.out.println("  Starting merge with newInterval: [" + newInterval[0] + "," + newInterval[1] + "]");
        while (i < n && intervals[i][0] <= newInterval[1]) {
            System.out.println("  Interval [" + intervals[i][0] + "," + intervals[i][1] + 
                             "] overlaps (starts at " + intervals[i][0] + " ≤ " + newInterval[1] + ")");
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            System.out.println("  Merged interval updated to: [" + newInterval[0] + "," + newInterval[1] + "]");
            i++;
        }
        result.add(newInterval);
        System.out.println("  Added merged interval to result");
        
        System.out.println("\n--- Phase 3: Add non-overlapping intervals after ---");
        while (i < n) {
            System.out.println("  Interval [" + intervals[i][0] + "," + intervals[i][1] + 
                             "] starts after newInterval ends (" + newInterval[1] + ") → add as-is");
            result.add(intervals[i]);
            i++;
        }
        
        System.out.println("\n=== Final Result ===");
        System.out.print("Merged intervals: ");
        for (int[] interval : result) {
            System.out.print("[" + interval[0] + "," + interval[1] + "] ");
        }
        
        // Visual timeline
        System.out.println("\n\n--- Visual Timeline ---");
        int minStart = Integer.MAX_VALUE;
        int maxEnd = Integer.MIN_VALUE;
        
        for (int[] interval : intervals) {
            minStart = Math.min(minStart, interval[0]);
            maxEnd = Math.max(maxEnd, interval[1]);
        }
        minStart = Math.min(minStart, newInterval[0]);
        maxEnd = Math.max(maxEnd, newInterval[1]);
        
        // Simple ASCII visualization
        for (int[] interval : intervals) {
            System.out.printf("Original : ");
            for (int t = minStart; t <= maxEnd; t++) {
                if (t >= interval[0] && t <= interval[1]) {
                    System.out.print("▬");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println(" [" + interval[0] + "-" + interval[1] + "]");
        }
        
        System.out.printf("New      : ");
        for (int t = minStart; t <= maxEnd; t++) {
            if (t >= newInterval[0] && t <= newInterval[1]) {
                System.out.print("█");
            } else {
                System.out.print(" ");
            }
        }
        System.out.println(" [" + newInterval[0] + "-" + newInterval[1] + "] (to insert)");
        
        System.out.println("\nMerged result:");
        for (int[] interval : result) {
            System.out.printf("Result   : ");
            for (int t = minStart; t <= maxEnd; t++) {
                if (t >= interval[0] && t <= interval[1]) {
                    System.out.print("▣");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println(" [" + interval[0] + "-" + interval[1] + "]");
        }
    }
    
    /**
     * Check if two intervals overlap
     */
    private boolean overlap(int[] a, int[] b) {
        return !(a[1] < b[0] || b[1] < a[0]);
    }
    
    /**
     * Test cases and examples
     */
    public static void runTestCases() {
        InsertInterval solver = new InsertInterval();
        
        Object[][] testCases = {
            // intervals, newInterval, expected output
            {new int[][]{{1,3},{6,9}}, new int[]{2,5}, new int[][]{{1,5},{6,9}}},
            {new int[][]{{1,2},{3,5},{6,7},{8,10},{12,16}}, new int[]{4,8}, new int[][]{{1,2},{3,10},{12,16}}},
            {new int[][]{}, new int[]{5,7}, new int[][]{{5,7}}},
            {new int[][]{{1,5}}, new int[]{2,3}, new int[][]{{1,5}}},
            {new int[][]{{1,5}}, new int[]{2,7}, new int[][]{{1,7}}},
            {new int[][]{{1,5}}, new int[]{6,8}, new int[][]{{1,5},{6,8}}},
            {new int[][]{{1,5}}, new int[]{0,3}, new int[][]{{0,5}}},
            {new int[][]{{1,5},{6,8}}, new int[]{0,9}, new int[][]{{0,9}}},
            {new int[][]{{1,5},{10,12}}, new int[]{7,8}, new int[][]{{1,5},{7,8},{10,12}}},
        };
        
        System.out.println("=== Test Cases ===");
        System.out.printf("%-40s %-15s %-40s %s\n", 
            "Intervals", "New Interval", "Expected", "Result");
        System.out.println("-".repeat(120));
        
        for (Object[] testCase : testCases) {
            int[][] intervals = (int[][]) testCase[0];
            int[] newInterval = (int[]) testCase[1];
            int[][] expected = (int[][]) testCase[2];
            
            int[][] result = solver.insert(intervals, newInterval);
            
            // Convert to string for display
            String intervalsStr = arrayToString(intervals);
            String newIntervalStr = "[" + newInterval[0] + "," + newInterval[1] + "]";
            String expectedStr = arrayToString(expected);
            String resultStr = arrayToString(result);
            
            boolean passed = arraysEqual(expected, result);
            System.out.printf("%-40s %-15s %-40s %-40s %s\n", 
                intervalsStr, newIntervalStr, expectedStr, resultStr,
                passed ? "✓" : "✗");
        }
    }
    
    private static String arrayToString(int[][] arr) {
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
            
            int[] newInterval = new int[2];
            newInterval[0] = sc.nextInt();
            newInterval[1] = sc.nextInt();
            
            InsertInterval solver = new InsertInterval();
            int[][] result = solver.insert(intervals, newInterval);
            
            for (int[] interval : result) {
                System.out.print("[" + interval[0] + "," + interval[1] + "] ");
            }
            System.out.println();
            
            // Uncomment for visualization
            // solver.visualizeInsertInterval(intervals, newInterval);
        }
        
        sc.close();
        
        // Uncomment to run test cases
        // runTestCases();
    }
}