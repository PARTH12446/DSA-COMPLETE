// Problem: LeetCode <ID>. <Title>
/*
 * PROBLEM: Pascal's Triangle (LeetCode 118 / Coding Ninjas variant)
 * 
 * Given an integer numRows, return the first numRows of Pascal's triangle.
 * 
 * In Pascal's triangle, each number is the sum of the two numbers directly above it.
 * 
 * EXAMPLE for numRows = 5:
 *     1
 *    1 1
 *   1 2 1
 *  1 3 3 1
 * 1 4 6 4 1
 * 
 * MATHEMATICAL PROPERTIES:
 * 1. Row r has (r+1) elements (0-indexed)
 * 2. Element at position (r, c) = C(r, c) = r! / (c! * (r-c)!)
 * 3. Symmetrical: element (r, c) = element (r, r-c)
 * 4. Sum of elements in row r = 2^r
 * 5. Each row starts and ends with 1
 * 
 * CONSTRAINTS:
 * - 1 <= n <= 30 (typically)
 * - Should handle up to n = 30 without overflow (fits in 32-bit int)
 * 
 * APPROACH: Dynamic Programming / Iterative Construction
 * 
 * INTUITION:
 * 1. First row is always [1]
 * 2. For each subsequent row:
 *    - First element = 1
 *    - Middle elements = sum of two elements above
 *    - Last element = 1
 * 
 * TIME COMPLEXITY: O(n²)
 *   - n rows, row i has i elements
 *   - Total elements: 1 + 2 + ... + n = n(n+1)/2 = O(n²)
 * 
 * SPACE COMPLEXITY: O(n²) for output
 *   - Need to store all rows
 *   - Could be O(1) extra space if not counting output
 */

import java.util.ArrayList;
import java.util.List;

public class PascalTriangle {

    /**
     * Generate first n rows of Pascal's Triangle
     * 
     * @param n Number of rows to generate
     * @return List of lists representing Pascal's Triangle
     */
    public static List<List<Integer>> pascalTriangle(int n) {
        List<List<Integer>> triangle = new ArrayList<>();
        
        // Base case: n must be at least 1
        if (n <= 0) {
            return triangle;
        }
        
        // First row is always [1]
        List<Integer> firstRow = new ArrayList<>();
        firstRow.add(1);
        triangle.add(firstRow);
        
        // If only one row requested, return
        if (n == 1) {
            return triangle;
        }
        
        // Generate remaining rows
        for (int rowNum = 1; rowNum < n; rowNum++) {
            List<Integer> previousRow = triangle.get(rowNum - 1);
            List<Integer> currentRow = new ArrayList<>();
            
            // First element is always 1
            currentRow.add(1);
            
            // Middle elements: sum of two elements above
            for (int j = 1; j < rowNum; j++) {
                int sum = previousRow.get(j - 1) + previousRow.get(j);
                currentRow.add(sum);
            }
            
            // Last element is always 1
            currentRow.add(1);
            
            triangle.add(currentRow);
        }
        
        return triangle;
    }
    
    /**
     * Alternative: Using array for better performance
     */
    public static List<List<Integer>> pascalTriangleArray(int n) {
        List<List<Integer>> triangle = new ArrayList<>();
        
        for (int row = 0; row < n; row++) {
            int[] currentRow = new int[row + 1];
            currentRow[0] = 1;  // First element
            currentRow[row] = 1; // Last element
            
            // Calculate middle elements
            for (int col = 1; col < row; col++) {
                currentRow[col] = triangle.get(row - 1).get(col - 1) 
                                + triangle.get(row - 1).get(col);
            }
            
            // Convert array to list and add to triangle
            List<Integer> rowList = new ArrayList<>();
            for (int num : currentRow) {
                rowList.add(num);
            }
            triangle.add(rowList);
        }
        
        return triangle;
    }
    
    /**
     * Get specific row of Pascal's Triangle (space optimized)
     * Uses O(rowIndex) extra space instead of O(n²)
     */
    public static List<Integer> getRow(int rowIndex) {
        List<Integer> row = new ArrayList<>();
        
        // Initialize first element
        row.add(1);
        
        // Generate row using combination formula: C(rowIndex, i)
        // Use the relation: C(n, k) = C(n, k-1) * (n - k + 1) / k
        for (int i = 1; i <= rowIndex; i++) {
            // Calculate next element using previous element
            // Avoid integer overflow by using long
            long value = (long) row.get(i - 1) * (rowIndex - i + 1) / i;
            row.add((int) value);
        }
        
        return row;
    }
    
    /**
     * Print Pascal's Triangle in pyramid format
     */
    public static void printPascalTriangle(int n) {
        List<List<Integer>> triangle = pascalTriangle(n);
        
        for (int i = 0; i < n; i++) {
            // Print spaces for centering
            for (int space = 0; space < n - i - 1; space++) {
                System.out.print(" ");
            }
            
            // Print numbers
            List<Integer> row = triangle.get(i);
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Get element at specific position (r, c) using combination formula
     * Time: O(min(c, r-c)), Space: O(1)
     */
    public static int getPascalValue(int r, int c) {
        // Use symmetry: C(r, c) = C(r, r-c)
        c = Math.min(c, r - c);
        
        long result = 1;
        for (int i = 1; i <= c; i++) {
            result = result * (r - i + 1) / i;
        }
        
        return (int) result;
    }
    
    /**
     * Test method with examples
     */
    public static void main(String[] args) {
        // Test case 1: n = 1
        System.out.println("Test 1: n = 1");
        List<List<Integer>> result1 = pascalTriangle(1);
        System.out.println(result1);
        System.out.println("Expected: [[1]]");
        
        // Test case 2: n = 5
        System.out.println("\nTest 2: n = 5");
        List<List<Integer>> result2 = pascalTriangle(5);
        System.out.println(result2);
        System.out.println("Expected: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]");
        
        // Test case 3: n = 0
        System.out.println("\nTest 3: n = 0");
        List<List<Integer>> result3 = pascalTriangle(0);
        System.out.println(result3);
        System.out.println("Expected: []");
        
        // Test case 4: n = 7
        System.out.println("\nTest 4: First 7 rows");
        printPascalTriangle(7);
        
        // Test case 5: Get specific row
        System.out.println("\nTest 5: 5th row (0-indexed)");
        List<Integer> row5 = getRow(5);
        System.out.println(row5);
        System.out.println("Expected: [1, 5, 10, 10, 5, 1]");
        
        // Test case 6: Get specific element
        System.out.println("\nTest 6: Element at (6, 3)");
        int element = getPascalValue(6, 3);
        System.out.println(element);
        System.out.println("Expected: 20 (C(6,3) = 20)");
        
        // Test case 7: Verify properties
        System.out.println("\nTest 7: Verify triangle properties for n=6");
        List<List<Integer>> triangle = pascalTriangle(6);
        
        // Check each row sums to 2^row
        for (int i = 0; i < triangle.size(); i++) {
            List<Integer> row = triangle.get(i);
            int sum = 0;
            for (int num : row) {
                sum += num;
            }
            int expectedSum = (int) Math.pow(2, i);
            System.out.println("Row " + i + " sum: " + sum + " = 2^" + i + " = " + expectedSum + " ?");
        }
        
        // Performance test
        System.out.println("\n=== Performance Test ===");
        long startTime = System.currentTimeMillis();
        List<List<Integer>> largeTriangle = pascalTriangle(30);
        long endTime = System.currentTimeMillis();
        System.out.println("Time to generate 30 rows: " + (endTime - startTime) + "ms");
        System.out.println("Total elements: " + (30 * 31 / 2));
        
        // Compare different methods
        System.out.println("\n=== Comparing Methods ===");
        startTime = System.currentTimeMillis();
        List<List<Integer>> triangle1 = pascalTriangle(20);
        endTime = System.currentTimeMillis();
        System.out.println("List method: " + (endTime - startTime) + "ms");
        
        startTime = System.currentTimeMillis();
        List<List<Integer>> triangle2 = pascalTriangleArray(20);
        endTime = System.currentTimeMillis();
        System.out.println("Array method: " + (endTime - startTime) + "ms");
        
        // Verify they produce same result
        boolean same = triangle1.equals(triangle2);
        System.out.println("Results match: " + same);
    }
    
    /**
     * Step-by-step generation example:
     * 
     * For n = 4:
     * 
     * Step 1: Add first row [1]
     * triangle = [[1]]
     * 
     * Step 2: Generate row 1 (index 1)
     * previousRow = [1]
     * currentRow = [1] (first element)
     * No middle elements (rowNum=1, so j from 1 to 0 doesn't run)
     * Add last element: [1, 1]
     * triangle = [[1], [1, 1]]
     * 
     * Step 3: Generate row 2 (index 2)
     * previousRow = [1, 1]
     * currentRow = [1] (first)
     * Middle: j=1: previousRow[0] + previousRow[1] = 1+1=2
     * currentRow = [1, 2]
     * Add last: [1, 2, 1]
     * triangle = [[1], [1, 1], [1, 2, 1]]
     * 
     * Step 4: Generate row 3 (index 3)
     * previousRow = [1, 2, 1]
     * currentRow = [1]
     * Middle: 
     *   j=1: previousRow[0]+previousRow[1] = 1+2=3
     *   j=2: previousRow[1]+previousRow[2] = 2+1=3
     * currentRow = [1, 3, 3]
     * Add last: [1, 3, 3, 1]
     * triangle = [[1], [1, 1], [1, 2, 1], [1, 3, 3, 1]]
     */
    
    /**
     * Mathematical Background:
     * 
     * Pascal's Triangle represents binomial coefficients:
     * 
     * Row 0: C(0,0) = 1
     * Row 1: C(1,0)=1, C(1,1)=1
     * Row 2: C(2,0)=1, C(2,1)=2, C(2,2)=1
     * 
     * The recurrence relation:
     * C(n, k) = C(n-1, k-1) + C(n-1, k)
     * with base cases: C(n, 0) = C(n, n) = 1
     * 
     * This is exactly what our algorithm implements.
     */
    
    /**
     * Properties of Pascal's Triangle:
     * 
     * 1. Symmetry: Each row reads the same forwards and backwards
     * 2. Hockey Stick Pattern: Sum of diagonal elements equals element below
     * 3. Fibonacci Numbers: Sums of shallow diagonals give Fibonacci numbers
     * 4. Powers of 11: First few rows resemble powers of 11 (row n = 11^n)
     * 5. Sierpinski Triangle: Coloring odd numbers creates fractal pattern
     */
    
    /**
     * Edge Cases:
     * 
     * 1. n = 0: Return empty list
     * 2. n = 1: Return [[1]]
     * 3. Large n: Ensure integer overflow doesn't occur (n <= 30 is safe)
     * 4. Negative n: Should return empty list (input constraint usually n >= 0)
     */
    
    /**
     * Common Mistakes:
     * 
     * 1. Off-by-one errors in loop boundaries
     * 2. Forgetting to add first/last 1 in each row
     * 3. Using wrong indices when accessing previous row
     * 4. Integer overflow for large n (use long or BigInteger)
     * 5. Modifying previous row while creating current row
     */
    
    /**
     * Optimization Opportunities:
     * 
     * 1. Use array instead of ArrayList for better performance
     * 2. Calculate using combination formula for specific elements
     * 3. Generate only needed rows, not entire triangle
     * 4. Use symmetry to calculate only half of each row
     */
    
    /**
     * Related Problems:
     * 
     * 1. Pascal's Triangle II (LeetCode 119) - Get specific row
     * 2. Unique Paths (LeetCode 62) - Can be solved using Pascal's Triangle
     * 3. Combination Sum (LeetCode 39)
     * 4. Triangle (LeetCode 120) - Minimum path sum in triangle
     */
    
    /**
     * Applications:
     * 
     * 1. Probability theory (binomial distribution)
     * 2. Combinatorics (counting combinations)
     * 3. Algebra (binomial theorem expansion)
     * 4. Computer graphics (Bézier curves)
     * 5. Game theory (probability calculations)
     */
    
    /**
     * Time Complexity Analysis:
     * 
     * For n rows:
     * Row 0: 1 element
     * Row 1: 2 elements
     * Row 2: 3 elements
     * ...
     * Row n-1: n elements
     * 
     * Total elements = 1 + 2 + ... + n = n(n+1)/2 = O(n²)
     * Each element calculated in O(1) time
     * Total time: O(n²)
     */
    
    /**
     * Space Complexity Analysis:
     * 
     * Output storage: O(n²) elements
     * Extra space (excluding output): O(1) or O(n) depending on implementation
     * 
     * For getRow() method: O(n) space for single row
     */
    
    /**
     * Implementation Notes:
     * 
     * 1. Use ArrayList for dynamic resizing
     * 2. Store triangle as List<List<Integer>> for flexibility
     * 3. Handle edge cases (n=0, n=1) separately for clarity
     * 4. Use descriptive variable names (rowNum, previousRow, currentRow)
     */
}
