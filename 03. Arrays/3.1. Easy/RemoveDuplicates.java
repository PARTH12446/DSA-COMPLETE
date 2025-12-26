// Problem: LeetCode <ID>. <Title>
// Problem: Remove Duplicates from Sorted Array
// Goal: Remove duplicates in-place, return count of unique elements

public class RemoveDuplicates {

    /**
     * OPTIMAL SOLUTION: Two-pointer in-place removal
     * Time Complexity: O(n) - Single pass through array
     * Space Complexity: O(1) - In-place modification
     * 
     * Steps:
     * 1. If array empty, return 0
     * 2. unique = 1 (first element is always unique)
     * 3. For i = 1 to n-1:
     *    - If arr[i] != arr[unique-1] (current vs last unique)
     *    - Copy arr[i] to arr[unique]
     *    - Increment unique
     * 4. Return unique count
     */
    public static int removeDuplicates(int[] arr, int n) {
        if (n == 0) return 0; // Edge case
        
        int unique = 1; // First element is always unique
        
        for (int i = 1; i < n; i++) {
            // Compare with last unique element
            if (arr[i] != arr[unique - 1]) {
                arr[unique] = arr[i]; // Place at next unique position
                unique++;
            }
            // If duplicate, skip (i increments in loop)
        }
        
        return unique; // Number of unique elements
    }
    
    /**
     * Alternative: More explicit version
     */
    public static int removeDuplicatesAlt(int[] arr, int n) {
        if (n <= 1) return n;
        
        int j = 0; // Pointer for unique elements
        
        for (int i = 1; i < n; i++) {
            if (arr[i] != arr[j]) {
                j++;
                arr[j] = arr[i];
            }
        }
        
        return j + 1; // j is index, count is index+1
    }

    /**
     * Example Walkthrough:
     * Input: [1, 1, 2, 2, 2, 3, 3]
     * 
     * unique=1 (arr[0]=1 is first unique)
     * i=1: 1 != arr[0]=1? No (duplicate) → skip
     * i=2: 2 != arr[0]=1? Yes → arr[1]=2, unique=2
     * i=3: 2 != arr[1]=2? No → skip  
     * i=4: 2 != arr[1]=2? No → skip
     * i=5: 3 != arr[1]=2? Yes → arr[2]=3, unique=3
     * i=6: 3 != arr[2]=3? No → skip
     * 
     * Result: unique=3, array=[1,2,3,2,2,3,3]
     * First 3 elements are unique: [1,2,3]
     */

    public static void main(String[] args) {
        int[] arr = {1, 1, 2, 2, 2, 3, 3};
        int k = removeDuplicates(arr, arr.length);
        
        System.out.println("Number of unique elements = " + k); // 3
        System.out.print("First " + k + " elements (unique): ");
        for (int i = 0; i < k; i++) System.out.print(arr[i] + " "); // 1 2 3
        System.out.println();
        
        // Test cases
        int[] test1 = {1, 2, 3, 4, 5};
        System.out.println("\nAlready unique: " + removeDuplicates(test1, 5)); // 5
        
        int[] test2 = {1, 1, 1, 1};
        System.out.println("All duplicates: " + removeDuplicates(test2, 4)); // 1
        
        int[] test3 = {};
        System.out.println("Empty array: " + removeDuplicates(test3, 0)); // 0
        
        int[] test4 = {7};
        System.out.println("Single element: " + removeDuplicates(test4, 1)); // 1
        
        int[] test5 = {1, 1, 2, 3, 3, 3, 4, 5, 5};
        int k5 = removeDuplicates(test5, 9);
        System.out.println("Mixed: " + k5 + " -> ");
        for (int i = 0; i < k5; i++) System.out.print(test5[i] + " "); // 1 2 3 4 5
    }
}
