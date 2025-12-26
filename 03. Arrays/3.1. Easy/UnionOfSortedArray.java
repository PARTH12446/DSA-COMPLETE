// Problem: LeetCode <ID>. <Title>
// Problem: Union of Two Sorted Arrays (unique elements only)
// Goal: Merge two sorted arrays, remove duplicates, maintain sorted order

import java.util.ArrayList;
import java.util.List;

public class UnionOfSortedArray {

    /**
     * OPTIMAL SOLUTION: Two-pointer merge with duplicate skipping
     * Time Complexity: O(n + m) - Single pass through both arrays
     * Space Complexity: O(1) extra space (excluding result list)
     * 
     * Steps:
     * 1. Initialize i=0, j=0, result list
     * 2. While both arrays have elements:
     *    - Compare a[i] and b[j]
     *    - Add smaller (if not duplicate of last added)
     *    - Increment pointer
     * 3. Process remaining elements in either array
     */
    public static List<Integer> sortedArray(int[] a, int[] b) {
        int n = a.length;
        int m = b.length;
        List<Integer> union = new ArrayList<>();
        int i = 0, j = 0;
        
        // Merge both arrays
        while (i < n && j < m) {
            if (a[i] <= b[j]) {
                // Add a[i] if not duplicate of last element
                if (union.isEmpty() || union.get(union.size() - 1) != a[i]) {
                    union.add(a[i]);
                }
                i++;
            } else {
                // Add b[j] if not duplicate
                if (union.isEmpty() || union.get(union.size() - 1) != b[j]) {
                    union.add(b[j]);
                }
                j++;
            }
        }
        
        // Add remaining elements from array a
        while (i < n) {
            if (union.isEmpty() || union.get(union.size() - 1) != a[i]) {
                union.add(a[i]);
            }
            i++;
        }
        
        // Add remaining elements from array b
        while (j < m) {
            if (union.isEmpty() || union.get(union.size() - 1) != b[j]) {
                union.add(b[j]);
            }
            j++;
        }
        
        return union;
    }
    
    /**
     * Alternative: Without using last element check
     * Use while loops to skip duplicates explicitly
     */
    public static List<Integer> sortedArrayAlt(int[] a, int[] b) {
        List<Integer> union = new ArrayList<>();
        int i = 0, j = 0;
        int n = a.length, m = b.length;
        
        while (i < n && j < m) {
            // Skip duplicates in a
            while (i > 0 && i < n && a[i] == a[i - 1]) i++;
            // Skip duplicates in b
            while (j > 0 && j < m && b[j] == b[j - 1]) j++;
            
            if (i >= n || j >= m) break;
            
            if (a[i] < b[j]) {
                union.add(a[i]);
                i++;
            } else if (a[i] > b[j]) {
                union.add(b[j]);
                j++;
            } else { // a[i] == b[j]
                union.add(a[i]);
                i++;
                j++;
            }
        }
        
        // Add remaining from a (skip duplicates)
        while (i < n) {
            if (i == 0 || a[i] != a[i - 1]) {
                union.add(a[i]);
            }
            i++;
        }
        
        // Add remaining from b (skip duplicates)
        while (j < m) {
            if (j == 0 || b[j] != b[j - 1]) {
                union.add(b[j]);
            }
            j++;
        }
        
        return union;
    }

    /**
     * Example Walkthrough:
     * a = [1, 2, 2, 3, 4]
     * b = [2, 3, 5, 6]
     * 
     * i=0, j=0: 1<2 → add 1 → union=[1], i=1
     * i=1, j=0: 2==2 → add 2 → union=[1,2], i=2
     * i=2, j=0: a[2]=2 (duplicate) → i=3 (skip in array a)
     * i=3, j=0: 3>2 → add 2? No, 2 already exists (check last=2)
     * Actually 3>2 → should add b[j]=2? But 2 already in union
     * Let's trace correctly...
     * 
     * Better walkthrough:
     * 1. Compare a[0]=1, b[0]=2 → add 1, i=1
     * 2. Compare a[1]=2, b[0]=2 → add 2, i=2
     * 3. Compare a[2]=2, b[0]=2 → skip 2 (duplicate), i=3
     * 4. Compare a[3]=3, b[0]=2 → add 2? Wait 2<3, so add b[0]=2? 
     *    But 2 already in union → skip, j=1
     * 5. Compare a[3]=3, b[1]=3 → add 3, i=4, j=2
     * 6. Compare a[4]=4, b[2]=5 → add 4, i=5
     * 7. Add remaining b[2]=5, b[3]=6
     * Result: [1,2,3,4,5,6]
     */

    public static void main(String[] args) {
        int[] a = {1, 2, 2, 3, 4};
        int[] b = {2, 3, 5, 6};
        List<Integer> res = sortedArray(a, b);
        
        System.out.println("Union of sorted arrays = " + res); // [1, 2, 3, 4, 5, 6]
        
        // Test cases
        int[] test1a = {1, 3, 5, 7};
        int[] test1b = {2, 4, 6, 8};
        System.out.println("\nNo overlap: " + sortedArray(test1a, test1b)); // [1,2,3,4,5,6,7,8]
        
        int[] test2a = {1, 1, 1};
        int[] test2b = {1, 1, 1};
        System.out.println("All same: " + sortedArray(test2a, test2b)); // [1]
        
        int[] test3a = {};
        int[] test3b = {2, 4, 6};
        System.out.println("First empty: " + sortedArray(test3a, test3b)); // [2,4,6]
        
        int[] test4a = {1, 5, 9};
        int[] test4b = {};
        System.out.println("Second empty: " + sortedArray(test4a, test4b)); // [1,5,9]
        
        int[] test5a = {1, 2, 3, 4, 5};
        int[] test5b = {1, 2, 6, 7, 8};
        System.out.println("Partial overlap: " + sortedArray(test5a, test5b)); // [1,2,3,4,5,6,7,8]
    }
}
