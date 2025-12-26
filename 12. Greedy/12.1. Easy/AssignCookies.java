import java.util.Arrays;
import java.util.Scanner;

/**
 * Assign Cookies (LeetCode 455)
 *
 * Problem Statement:
 * Assume you are an awesome parent and want to give your children some cookies.
 * But, you should give each child at most one cookie.
 * Each child i has a greed factor g[i], which is the minimum size of a cookie
 * that the child will be content with. Each cookie j has a size s[j].
 * If s[j] >= g[i], we can assign cookie j to child i, and the child i will be content.
 * Your goal is to maximize the number of content children and output the maximum number.
 *
 * Example 1:
 * Input: g = [1,2,3], s = [1,1]
 * Output: 1
 * Explanation: You have 3 children and 2 cookies. The greed factors are 1,2,3.
 * Even though you have 2 cookies, their sizes are both 1, so you can only make child 1 content.
 *
 * Example 2:
 * Input: g = [1,2], s = [1,2,3]
 * Output: 2
 * Explanation: You have 2 children and 3 cookies. Both children can be satisfied.
 *
 * Greedy Approach:
 * - Sort children by greed factor (ascending)
 * - Sort cookies by size (ascending)
 * - Use two pointers: one for children, one for cookies
 * - For each cookie (from smallest to largest), try to satisfy the current child
 * - If cookie satisfies child, move to next child and next cookie
 * - If not, try next cookie (but keep same child)
 * - Continue until we run out of cookies or all children are satisfied
 *
 * Time Complexity: O(n log n + m log m) for sorting, O(min(n,m)) for two-pointer traversal
 * Space Complexity: O(1) excluding sorting space (O(log n + log m) for quicksort)
 */
public class AssignCookies {

    /**
     * Greedy solution using two pointers
     * 
     * @param g array of greed factors for children
     * @param s array of cookie sizes
     * @return maximum number of content children
     */
    public int findContentChildren(int[] g, int[] s) {
        // Edge cases: no children or no cookies
        if (g == null || s == null || g.length == 0 || s.length == 0) {
            return 0;
        }

        // Step 1: Sort both arrays in ascending order
        Arrays.sort(g);  // Children sorted by greed factor
        Arrays.sort(s);  // Cookies sorted by size
        
        // Step 2: Initialize pointers
        int childIndex = 0;  // Points to current child to satisfy
        int cookieIndex = 0; // Points to current cookie to assign
        
        // Step 3: Greedy assignment
        while (childIndex < g.length && cookieIndex < s.length) {
            // If current cookie can satisfy current child
            if (s[cookieIndex] >= g[childIndex]) {
                // Assign cookie to child, move to next child
                childIndex++;
            }
            // Move to next cookie regardless of assignment
            // (if cookie assigned, need new cookie; if not, try next larger cookie)
            cookieIndex++;
        }
        
        // Number of satisfied children = how many children we assigned cookies to
        return childIndex;
    }
    
    /**
     * Alternative implementation with explicit counting and comments
     */
    public int findContentChildrenAlternative(int[] g, int[] s) {
        if (g == null || s == null || g.length == 0 || s.length == 0) {
            return 0;
        }
        
        Arrays.sort(g);
        Arrays.sort(s);
        
        int satisfiedChildren = 0;
        
        // We want to satisfy children with smallest greed first
        // and use smallest cookies that can satisfy them
        for (int i = 0, j = 0; i < g.length && j < s.length; j++) {
            // Try to assign cookie j to child i
            if (s[j] >= g[i]) {
                satisfiedChildren++;  // Child i is satisfied
                i++;                  // Move to next child
            }
            // If cookie j doesn't satisfy child i, try next cookie (j increments in loop)
        }
        
        return satisfiedChildren;
    }
    
    /**
     * Visualization helper: Show step-by-step assignment
     */
    public void visualizeAssignment(int[] g, int[] s) {
        System.out.println("\n=== Visualizing Cookie Assignment ===");
        System.out.println("Children's greed factors (g): " + Arrays.toString(g));
        System.out.println("Cookie sizes (s): " + Arrays.toString(s));
        
        // Create copies to avoid modifying originals
        int[] sortedG = g.clone();
        int[] sortedS = s.clone();
        Arrays.sort(sortedG);
        Arrays.sort(sortedS);
        
        System.out.println("\nSorted greed factors: " + Arrays.toString(sortedG));
        System.out.println("Sorted cookie sizes: " + Arrays.toString(sortedS));
        
        int childIdx = 0, cookieIdx = 0;
        int step = 1;
        
        System.out.println("\nAssignment Process:");
        while (childIdx < sortedG.length && cookieIdx < sortedS.length) {
            System.out.println("\nStep " + step++ + ":");
            System.out.println("  Child " + childIdx + " needs cookie size >= " + sortedG[childIdx]);
            System.out.println("  Cookie " + cookieIdx + " has size " + sortedS[cookieIdx]);
            
            if (sortedS[cookieIdx] >= sortedG[childIdx]) {
                System.out.println("  ✓ Cookie " + sortedS[cookieIdx] + " satisfies child " + childIdx);
                childIdx++;
                System.out.println("  Moving to next child: child " + childIdx);
            } else {
                System.out.println("  ✗ Cookie " + sortedS[cookieIdx] + " is too small for child " + childIdx);
                System.out.println("  Keeping same child, trying next cookie");
            }
            
            cookieIdx++;
            System.out.println("  Moving to next cookie: cookie " + cookieIdx);
        }
        
        System.out.println("\nResult: " + childIdx + " satisfied children");
        System.out.println("Unsatisfied children: " + (sortedG.length - childIdx));
        System.out.println("Unused cookies: " + (sortedS.length - cookieIdx));
    }
    
    /**
     * Greedy proof intuition:
     * Why smallest cookies to smallest greed children?
     * 1. If we use a large cookie for a child with small greed, we waste cookie size
     * 2. A smaller cookie might satisfy the child, leaving larger cookies for children with larger greed
     * 3. This is a classic "greedy stays ahead" proof pattern
     */

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // Number of test cases
        
        while (t-- > 0) {
            int n = sc.nextInt();  // Number of children
            int m = sc.nextInt();  // Number of cookies
            
            int[] g = new int[n];  // Greed factors
            int[] s = new int[m];  // Cookie sizes
            
            for (int i = 0; i < n; i++) {
                g[i] = sc.nextInt();
            }
            
            for (int i = 0; i < m; i++) {
                s[i] = sc.nextInt();
            }
            
            AssignCookies solver = new AssignCookies();
            int result = solver.findContentChildren(g, s);
            System.out.println("Maximum content children: " + result);
            
            // Uncomment for visualization
            // solver.visualizeAssignment(g, s);
        }
        
        sc.close();
    }
}