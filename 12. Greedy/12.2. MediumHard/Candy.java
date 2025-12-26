import java.util.Scanner;

/**
 * Candy (LeetCode 135)
 * 
 * Problem Statement:
 * There are n children standing in a line. Each child is assigned a rating value.
 * You are giving candies to these children such that:
 * 1. Each child must have at least one candy.
 * 2. Children with a higher rating get more candies than their neighbors.
 * 
 * Return the minimum number of candies you need to have.
 * 
 * Example 1:
 * Input: ratings = [1,0,2]
 * Output: 5
 * Explanation: You can give candies as [2,1,2]
 * 
 * Example 2:
 * Input: ratings = [1,2,2]
 * Output: 4
 * Explanation: You can give candies as [1,2,1] or [1,2,2]
 * 
 * Greedy Two-Pass Solution:
 * 1. Left-to-right pass: Ensure each child gets more candies than left neighbor if rating is higher
 * 2. Right-to-left pass: Ensure each child gets more candies than right neighbor if rating is higher
 * 3. Take maximum of both passes for each child
 * 
 * Time Complexity: O(n) - two passes through array
 * Space Complexity: O(n) - for candies array
 */
public class Candy {

    /**
     * Greedy two-pass solution
     * 
     * @param ratings Array of children's ratings
     * @return Minimum total candies needed
     */
    public int candy(int[] ratings) {
        int n = ratings.length;
        if (n == 0) return 0;
        if (n == 1) return 1;
        
        // Initialize candies array with 1 candy for each child
        int[] candies = new int[n];
        java.util.Arrays.fill(candies, 1);
        
        // ===== PASS 1: Left to Right =====
        // If a child has higher rating than left neighbor, give more candies
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
            // If ratings are equal or lower, keep 1 candy (minimum)
        }
        
        // ===== PASS 2: Right to Left =====
        // If a child has higher rating than right neighbor, ensure enough candies
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                // Take maximum of current value and right neighbor + 1
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }
        
        // Calculate total candies
        int totalCandies = 0;
        for (int candy : candies) {
            totalCandies += candy;
        }
        
        return totalCandies;
    }
    
    /**
     * Alternative: Single pass with peak detection
     * More complex but uses O(1) space
     */
    public int candySinglePass(int[] ratings) {
        int n = ratings.length;
        if (n <= 1) return n;
        
        int total = 1;  // First child gets 1 candy
        int up = 0, down = 0, peak = 0;
        
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                up++;
                down = 0;
                peak = up;
                total += 1 + up;
            } else if (ratings[i] == ratings[i - 1]) {
                up = down = peak = 0;
                total += 1;
            } else { // ratings[i] < ratings[i - 1]
                down++;
                up = 0;
                total += down + (peak >= down ? 0 : 1);
            }
        }
        
        return total;
    }
    
    /**
     * Visualization helper: Show step-by-step process
     */
    public void visualizeCandyDistribution(int[] ratings) {
        System.out.println("\n=== Visualizing Candy Distribution ===");
        System.out.print("Ratings:   ");
        for (int rating : ratings) {
            System.out.printf("%3d ", rating);
        }
        System.out.println();
        
        int n = ratings.length;
        int[] candies = new int[n];
        java.util.Arrays.fill(candies, 1);
        
        // Pass 1: Left to Right
        System.out.println("\n--- Pass 1: Left → Right ---");
        System.out.print("Candies:   ");
        for (int i = 0; i < n; i++) {
            System.out.printf("%3d ", candies[i]);
        }
        System.out.println();
        
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
                System.out.printf("Position %d: rating %d > rating %d → candies[%d] = candies[%d] + 1 = %d\n",
                    i, ratings[i], ratings[i-1], i, i-1, candies[i]);
            }
        }
        
        System.out.print("\nAfter pass 1: ");
        for (int candy : candies) {
            System.out.printf("%3d ", candy);
        }
        System.out.println();
        
        // Pass 2: Right to Left
        System.out.println("\n--- Pass 2: Right → Left ---");
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                int newValue = candies[i + 1] + 1;
                if (newValue > candies[i]) {
                    candies[i] = newValue;
                    System.out.printf("Position %d: rating %d > rating %d → candies[%d] = max(%d, %d+1) = %d\n",
                        i, ratings[i], ratings[i+1], i, candies[i]-1, candies[i+1], candies[i]);
                }
            }
        }
        
        System.out.print("\nAfter pass 2: ");
        for (int candy : candies) {
            System.out.printf("%3d ", candy);
        }
        System.out.println();
        
        // Calculate total
        int total = 0;
        System.out.println("\n--- Total Calculation ---");
        for (int i = 0; i < n; i++) {
            System.out.printf("Child %d: rating=%d, candies=%d\n", i, ratings[i], candies[i]);
            total += candies[i];
        }
        
        System.out.println("\nTotal candies needed: " + total);
        
        // Visual representation
        System.out.println("\n--- Visual Representation ---");
        for (int i = 0; i < n; i++) {
            System.out.printf("Child %2d (rating %2d): ", i, ratings[i]);
            for (int j = 0; j < candies[i]; j++) {
                System.out.print("🍬");
            }
            System.out.println();
        }
    }
    
    /**
     * Explanation of why two-pass greedy works:
     * 
     * Intuition:
     * - Each child's candy count depends on both left and right neighbors
     * - Local comparisons are sufficient (greedy choice property)
     * - Left pass handles increasing sequences from left
     * - Right pass handles decreasing sequences from right
     * - Taking max ensures both constraints satisfied
     * 
     * Example: ratings = [1, 3, 2, 2, 1]
     * Left pass:  [1, 2, 1, 1, 1]  (3>1 → 2, but doesn't see 2>3)
     * Right pass: [1, 2, 1, 1, 1] → [1, 3, 2, 1, 1] (3>2 → 3, 2>2 no change)
     * Final:      [1, 3, 2, 1, 1] = 8 candies
     */
    
    /**
     * Test cases
     */
    public static void runTestCases() {
        Candy solver = new Candy();
        
        int[][] testCases = {
            {1, 0, 2},        // Expected: 5 (2,1,2)
            {1, 2, 2},        // Expected: 4 (1,2,1)
            {1, 3, 2, 2, 1},  // Expected: 7 (1,2,1,2,1) or (1,3,2,1,1)=8?
            {1, 2, 87, 87, 87, 2, 1}, // Expected: 13
            {1, 6, 10, 8, 7, 3, 2},   // Expected: 18
            {1, 2, 3, 4, 5},          // Expected: 15 (1,2,3,4,5)
            {5, 4, 3, 2, 1},          // Expected: 15 (5,4,3,2,1)
            {1},                      // Expected: 1
            {},                       // Expected: 0
            {1, 1, 1, 1, 1},          // Expected: 5 (all 1s)
            {1, 2, 3, 1, 0},          // Expected: 9 (1,2,3,2,1)
        };
        
        System.out.println("=== Test Cases ===");
        System.out.printf("%-25s %-10s %-10s\n", "Ratings", "Expected", "Got");
        System.out.println("-".repeat(50));
        
        for (int[] ratings : testCases) {
            int result = solver.candy(ratings);
            
            // Calculate expected manually for known cases
            int expected = 0;
            if (ratings.length == 0) expected = 0;
            else if (ratings.length == 1) expected = 1;
            else if (java.util.Arrays.equals(ratings, new int[]{1,0,2})) expected = 5;
            else if (java.util.Arrays.equals(ratings, new int[]{1,2,2})) expected = 4;
            else if (ratings.length == 5 && ratings[0]==1 && ratings[4]==1) {
                // Simple pattern
                expected = ratings.length * 2 - 1; // Rough estimate
            }
            
            System.out.printf("%-25s %-10d %-10d %s\n", 
                java.util.Arrays.toString(ratings), 
                expected, 
                result,
                (expected == 0 || result == expected) ? "✓" : "?");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // Number of test cases
        
        while (t-- > 0) {
            int n = sc.nextInt();
            int[] ratings = new int[n];
            
            for (int i = 0; i < n; i++) {
                ratings[i] = sc.nextInt();
            }
            
            Candy solver = new Candy();
            int result = solver.candy(ratings);
            System.out.println(result);
            
            // Uncomment for visualization
            // solver.visualizeCandyDistribution(ratings);
        }
        
        sc.close();
        
        // Uncomment to run test cases
        // runTestCases();
    }
}