// Problem: LeetCode <ID>. <Title>
// Problem: Second Largest & Second Smallest elements
// Goal: Find without sorting (optimal approach)

import java.util.Arrays;

public class SecondLargeAndSmall {

    /**
     * OPTIMAL SOLUTION: Single pass tracking
     * Time Complexity: O(n) - Single pass through array
     * Space Complexity: O(1) - Only few variables
     * 
     * Steps:
     * 1. Track largest, secondLargest, smallest, secondSmallest
     * 2. Initialize with appropriate values
     * 3. Update through single pass
     * 4. Handle duplicates properly
     */
    public static int[] getSecondOrderElements(int n, int[] a) {
        if (n < 2) {
            // Not enough elements, return sentinel values
            return new int[]{-1, -1};
        }
        
        // Initialize with extremes
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;
        int smallest = Integer.MAX_VALUE;
        int secondSmallest = Integer.MAX_VALUE;
        
        for (int num : a) {
            // Update largest and secondLargest
            if (num > largest) {
                secondLargest = largest;
                largest = num;
            } else if (num > secondLargest && num != largest) {
                secondLargest = num;
            }
            
            // Update smallest and secondSmallest
            if (num < smallest) {
                secondSmallest = smallest;
                smallest = num;
            } else if (num < secondSmallest && num != smallest) {
                secondSmallest = num;
            }
        }
        
        // Check if second largest/smallest exist
        if (secondLargest == Integer.MIN_VALUE) {
            secondLargest = -1; // All elements same
        }
        if (secondSmallest == Integer.MAX_VALUE) {
            secondSmallest = -1; // All elements same
        }
        
        return new int[]{secondLargest, secondSmallest};
    }
    
    /**
     * Alternative: Two-pass approach (easier to understand)
     * Find largest, then find second largest excluding largest
     */
    public static int[] getSecondOrderElementsTwoPass(int n, int[] a) {
        if (n < 2) return new int[]{-1, -1};
        
        // Find largest
        int largest = Integer.MIN_VALUE;
        for (int num : a) {
            if (num > largest) largest = num;
        }
        
        // Find second largest (smallest among those less than largest)
        int secondLargest = Integer.MIN_VALUE;
        for (int num : a) {
            if (num > secondLargest && num < largest) {
                secondLargest = num;
            }
        }
        
        // Find smallest
        int smallest = Integer.MAX_VALUE;
        for (int num : a) {
            if (num < smallest) smallest = num;
        }
        
        // Find second smallest
        int secondSmallest = Integer.MAX_VALUE;
        for (int num : a) {
            if (num < secondSmallest && num > smallest) {
                secondSmallest = num;
            }
        }
        
        if (secondLargest == Integer.MIN_VALUE) secondLargest = -1;
        if (secondSmallest == Integer.MAX_VALUE) secondSmallest = -1;
        
        return new int[]{secondLargest, secondSmallest};
    }

    /**
     * Example Walkthrough (single pass):
     * Input: [1, 2, 3, 4, 5]
     * 
     * Start: largest=MIN, secondL=MIN, smallest=MAX, secondS=MAX
     * 
     * num=1: 
     *   largest=1, secondL=MIN
     *   smallest=1, secondS=MAX
     *   
     * num=2:
     *   2>1 → secondL=1, largest=2
     *   2<1? No, but 2<MAX && 2≠1 → secondS=2
     *   
     * num=3:
     *   3>2 → secondL=2, largest=3
     *   3<2? No, 3<2? No (skip)
     *   
     * num=4:
     *   4>3 → secondL=3, largest=4
     *   
     * num=5:
     *   5>4 → secondL=4, largest=5
     *   
     * Result: secondLargest=4, secondSmallest=2
     */

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5};
        int[] res = getSecondOrderElements(a.length, a);
        System.out.println("Second largest = " + res[0] + ", Second smallest = " + res[1]);
        // Output: 4, 2
        
        // Test cases
        int[] test1 = {10, 5, 10, 8, 2};
        int[] res1 = getSecondOrderElements(test1.length, test1);
        System.out.println("\n[10,5,10,8,2]: " + res1[0] + ", " + res1[1]); // 8, 5
        
        int[] test2 = {5, 5, 5, 5};
        int[] res2 = getSecondOrderElements(test2.length, test2);
        System.out.println("[5,5,5,5]: " + res2[0] + ", " + res2[1]); // -1, -1
        
        int[] test3 = {2, 1};
        int[] res3 = getSecondOrderElements(test3.length, test3);
        System.out.println("[2,1]: " + res3[0] + ", " + res3[1]); // 1, 2
        
        int[] test4 = {7};
        int[] res4 = getSecondOrderElements(test4.length, test4);
        System.out.println("[7]: " + res4[0] + ", " + res4[1]); // -1, -1
        
        int[] test5 = {3, 1, 4, 1, 5, 9, 2, 6};
        int[] res5 = getSecondOrderElements(test5.length, test5);
        System.out.println("[3,1,4,1,5,9,2,6]: " + res5[0] + ", " + res5[1]); // 6, 2
    }
}
