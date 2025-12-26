import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Replace every element with its rank when the array is sorted.
 * Elements with the same value get the same rank.
 * 
 * Problem Statement:
 * Given an array of integers, replace each element with its rank.
 * Rank starts from 1 (smallest element gets rank 1).
 * Elements with equal values get the same rank.
 * 
 * Example 1:
 * Input:  arr = [40, 10, 20, 30]
 * Output: [4, 1, 2, 3]
 * Explanation: Sorted: [10,20,30,40], ranks: 10→1, 20→2, 30→3, 40→4
 * 
 * Example 2:
 * Input:  arr = [100, 100, 50, 50, 25]
 * Output: [3, 3, 2, 2, 1]
 * Explanation: Sorted: [25,50,50,100,100], ranks: 25→1, 50→2, 100→3
 * 
 * Approach 1: Sorting + HashMap (implemented above)
 * 1. Create sorted copy of array
 * 2. Iterate through sorted array, assign ranks to unique values
 * 3. Use HashMap to store value→rank mapping
 * 4. Replace original array elements with their ranks
 * 
 * Time Complexity: O(n log n) for sorting + O(n) for rank assignment = O(n log n)
 * Space Complexity: O(n) for sorted copy + O(n) for HashMap = O(n)
 */
public class ReplaceWithRank {

    /**
     * Method 1: Sorting + HashMap (original implementation)
     */
    public int[] replaceWithRank(int[] arr) {
        int n = arr.length;
        
        // Edge case: empty array
        if (n == 0) {
            return new int[0];
        }
        
        // Step 1: Create sorted copy of array
        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        
        // Step 2: Build rank mapping (value → rank)
        Map<Integer, Integer> rankMap = new HashMap<>();
        int currentRank = 1;
        
        for (int value : sorted) {
            // Only assign rank if value hasn't been seen before
            if (!rankMap.containsKey(value)) {
                rankMap.put(value, currentRank);
                currentRank++;
            }
        }
        
        // Step 3: Replace original array elements with their ranks
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = rankMap.get(arr[i]);
        }
        
        return result;
    }
    
    /**
     * Method 2: Using TreeMap (maintains sorted order)
     * Advantage: No need to sort array separately
     * Time: O(n log n), Space: O(n)
     */
    public int[] replaceWithRankTreeMap(int[] arr) {
        int n = arr.length;
        if (n == 0) return new int[0];
        
        // TreeMap automatically sorts keys in ascending order
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        
        // Add all values to TreeMap (duplicates automatically handled)
        for (int value : arr) {
            treeMap.put(value, 0); // placeholder value
        }
        
        // Assign ranks to sorted keys
        int rank = 1;
        for (int key : treeMap.keySet()) {
            treeMap.put(key, rank++);
        }
        
        // Create result array
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = treeMap.get(arr[i]);
        }
        
        return result;
    }
    
    /**
     * Method 3: Using Pair objects to track original indices
     * Advantage: More intuitive for understanding
     * Time: O(n log n), Space: O(n)
     */
    public int[] replaceWithRankPairs(int[] arr) {
        int n = arr.length;
        if (n == 0) return new int[0];
        
        // Create array of pairs (value, originalIndex)
        Pair[] pairs = new Pair[n];
        for (int i = 0; i < n; i++) {
            pairs[i] = new Pair(arr[i], i);
        }
        
        // Sort pairs by value
        Arrays.sort(pairs, (a, b) -> Integer.compare(a.value, b.value));
        
        // Initialize result array
        int[] result = new int[n];
        int currentRank = 1;
        
        // Process sorted pairs
        for (int i = 0; i < n; i++) {
            // If same value as previous, assign same rank
            if (i > 0 && pairs[i].value != pairs[i-1].value) {
                currentRank++;
            }
            
            // Place rank at original position
            result[pairs[i].originalIndex] = currentRank;
        }
        
        return result;
    }
    
    // Helper class for Method 3
    private static class Pair {
        int value;
        int originalIndex;
        
        Pair(int value, int originalIndex) {
            this.value = value;
            this.originalIndex = originalIndex;
        }
    }
    
    /**
     * Method 4: Using Arrays.sort with custom comparator
     * Single-pass approach
     */
    public int[] replaceWithRankCompact(int[] arr) {
        int n = arr.length;
        if (n == 0) return new int[0];
        
        // Create copy with indices
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) indices[i] = i;
        
        // Sort indices based on arr values
        Arrays.sort(indices, (a, b) -> Integer.compare(arr[a], arr[b]));
        
        // Assign ranks
        int[] result = new int[n];
        int rank = 1;
        result[indices[0]] = rank;
        
        for (int i = 1; i < n; i++) {
            // If value is different from previous, increment rank
            if (arr[indices[i]] != arr[indices[i-1]]) {
                rank++;
            }
            result[indices[i]] = rank;
        }
        
        return result;
    }
    
    /**
     * Visualization helper: Show step-by-step process
     */
    public void visualizeReplaceWithRank(int[] arr) {
        System.out.println("\n=== Visualizing Replace With Rank ===");
        System.out.println("Original array: " + Arrays.toString(arr));
        
        // Create sorted copy
        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        System.out.println("Sorted array:   " + Arrays.toString(sorted));
        
        // Build rank mapping
        Map<Integer, Integer> rankMap = new HashMap<>();
        int currentRank = 1;
        
        System.out.println("\nBuilding rank mapping:");
        for (int value : sorted) {
            if (!rankMap.containsKey(value)) {
                System.out.println("  Value " + value + " → Rank " + currentRank);
                rankMap.put(value, currentRank);
                currentRank++;
            }
        }
        
        // Create result
        int[] result = new int[arr.length];
        System.out.println("\nReplacing original elements:");
        for (int i = 0; i < arr.length; i++) {
            result[i] = rankMap.get(arr[i]);
            System.out.println("  arr[" + i + "] = " + arr[i] + " → rank " + result[i]);
        }
        
        System.out.println("\nFinal result: " + Arrays.toString(result));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // Number of test cases
        
        while (t-- > 0) {
            int n = sc.nextInt();
            int[] arr = new int[n];
            
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }
            
            ReplaceWithRank solver = new ReplaceWithRank();
            
            // Method 1: Original approach
            int[] result = solver.replaceWithRank(arr);
            System.out.print("Method 1 (Sorting+HashMap): ");
            for (int val : result) {
                System.out.print(val + " ");
            }
            System.out.println();
            
            // Uncomment for visualization
            // solver.visualizeReplaceWithRank(arr);
            
            // Method 2: TreeMap approach
            // int[] result2 = solver.replaceWithRankTreeMap(arr);
            // System.out.print("Method 2 (TreeMap): ");
            // for (int val : result2) {
            //     System.out.print(val + " ");
            // }
            // System.out.println();
            
            // Method 3: Pairs approach
            // int[] result3 = solver.replaceWithRankPairs(arr);
            // System.out.print("Method 3 (Pairs): ");
            // for (int val : result3) {
            //     System.out.print(val + " ");
            // }
            // System.out.println();
        }
        
        sc.close();
    }
}