import java.util.*;

/**
 * Stock Span Problem
 * 
 * Problem: For each day, calculate the span of the stock's price which is defined
 * as the maximum number of consecutive days (including current day) for which
 * the stock price was less than or equal to the current day's price.
 * 
 * Mathematical Definition:
 * span[i] = i - previousGreaterDay[i] 
 * where previousGreaterDay[i] = index of closest day before i with price > prices[i]
 * If no such day exists, span[i] = i + 1
 * 
 * Approach: Use monotonic decreasing stack to track decreasing prices.
 * For each price, pop all smaller or equal prices from stack, then span = distance to stack top.
 * 
 * Time Complexity: O(n) - Each index pushed/popped at most once
 * Space Complexity: O(n) - Stack storage
 * 
 * Example: prices = [100, 80, 60, 70, 60, 75, 85]
 *          spans  = [1,   1,  1,  2,  1,  4,  6]
 * 
 * Applications:
 * 1. Financial analysis of stock trends
 * 2. Technical analysis in trading
 * 3. Pattern recognition in time series data
 * 4. Component in larger algorithms (largest rectangle in histogram)
 */
public class StockSpan {
    
    /**
     * Calculate stock spans for each day.
     * 
     * Algorithm:
     * 1. Use monotonic decreasing stack (taller prices to smaller from bottom to top)
     * 2. For each price prices[i]:
     *    a. Pop while stack top price <= prices[i] (current price is higher or equal)
     *    b. Calculate span:
     *       - If stack empty: span = i + 1 (all previous days have lower/equal prices)
     *       - Otherwise: span = i - stack.peek() (distance to previous higher price)
     *    c. Push current index to stack
     * 
     * @param prices Array of stock prices for consecutive days
     * @return Array of spans for each day
     */
    public int[] calculateSpan(int[] prices) {
        int n = prices.length;
        int[] span = new int[n];
        Stack<Integer> stack = new Stack<>(); // Stores indices of prices in decreasing order
        
        for (int i = 0; i < n; i++) {
            // Remove indices from stack where price is <= current price
            // These prices can't be "previous greater" for any future price
            while (!stack.isEmpty() && prices[stack.peek()] <= prices[i]) {
                stack.pop();
            }
            
            // Calculate span for current day
            // If stack is empty: all previous prices were <= current price
            // Otherwise: distance to previous higher price
            span[i] = stack.isEmpty() ? i + 1 : i - stack.peek();
            
            // Push current index to stack
            stack.push(i);
        }
        
        return span;
    }
    
    /**
     * Alternative: Brute force solution (O(n²) for verification.
     */
    public int[] calculateSpanBruteForce(int[] prices) {
        int n = prices.length;
        int[] span = new int[n];
        
        for (int i = 0; i < n; i++) {
            span[i] = 1; // At least the current day
            
            // Look backwards for consecutive days with lower or equal prices
            int j = i - 1;
            while (j >= 0 && prices[j] <= prices[i]) {
                span[i]++;
                j--;
            }
        }
        
        return span;
    }
    
    /**
     * Calculate spans and also return the indices of previous greater elements.
     * Useful for understanding the underlying concept.
     */
    public Map<String, int[]> calculateSpanWithPreviousGreater(int[] prices) {
        int n = prices.length;
        int[] span = new int[n];
        int[] previousGreater = new int[n]; // Store indices of previous greater prices
        Arrays.fill(previousGreater, -1); // -1 means no previous greater price
        
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < n; i++) {
            // Remove smaller or equal prices
            while (!stack.isEmpty() && prices[stack.peek()] <= prices[i]) {
                stack.pop();
            }
            
            // Record previous greater price index
            if (!stack.isEmpty()) {
                previousGreater[i] = stack.peek();
            }
            
            // Calculate span
            span[i] = stack.isEmpty() ? i + 1 : i - stack.peek();
            
            stack.push(i);
        }
        
        Map<String, int[]> result = new HashMap<>();
        result.put("span", span);
        result.put("previousGreater", previousGreater);
        return result;
    }
    
    /**
     * Visualization helper to show the span calculation process.
     */
    public void visualizeSpanCalculation(int[] prices) {
        System.out.println("\n=== Stock Span Calculation Visualization ===");
        System.out.println("Prices: " + Arrays.toString(prices));
        System.out.println();
        
        System.out.println("Day | Price | Stack (indices) | Previous Greater | Span Calculation | Span");
        System.out.println("----|-------|-----------------|------------------|------------------|-----");
        
        Stack<Integer> stack = new Stack<>();
        int n = prices.length;
        
        for (int i = 0; i < n; i++) {
            // Before processing current day
            System.out.printf("%3d | %5d | %15s", i, prices[i], stack.toString());
            
            // Remove smaller or equal prices
            while (!stack.isEmpty() && prices[stack.peek()] <= prices[i]) {
                stack.pop();
            }
            
            // Calculate span and get previous greater
            int previousGreater = stack.isEmpty() ? -1 : stack.peek();
            int span = stack.isEmpty() ? i + 1 : i - stack.peek();
            
            String calc = stack.isEmpty() 
                ? "No higher price before → span = " + (i + 1)
                : "Previous higher at day " + previousGreater + " → span = " + i + " - " + previousGreater;
            
            System.out.printf(" | %15d | %16s | %4d%n", 
                            previousGreater, calc, span);
            
            // Push current index
            stack.push(i);
        }
        
        int[] spans = calculateSpan(prices);
        System.out.println("\nFinal spans: " + Arrays.toString(spans));
    }
    
    /**
     * Extended version: Calculate spans for multiple stocks (2D array).
     * Each row represents a stock, each column represents a day.
     */
    public int[][] calculateSpanForMultipleStocks(int[][] stocks) {
        int numStocks = stocks.length;
        int numDays = stocks[0].length;
        int[][] allSpans = new int[numStocks][numDays];
        
        for (int stockIdx = 0; stockIdx < numStocks; stockIdx++) {
            allSpans[stockIdx] = calculateSpan(stocks[stockIdx]);
        }
        
        return allSpans;
    }
    
    /**
     * Calculate maximum span where price increase is at least X%.
     * Variation: Find consecutive days where price increased by at least threshold %.
     */
    public int[] calculatePercentSpan(int[] prices, double thresholdPercent) {
        int n = prices.length;
        int[] span = new int[n];
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < n; i++) {
            // Calculate threshold price
            double thresholdPrice = prices[i] * (100.0 / (100.0 + thresholdPercent));
            
            // Remove indices where price is <= threshold price
            while (!stack.isEmpty() && prices[stack.peek()] <= thresholdPrice) {
                stack.pop();
            }
            
            span[i] = stack.isEmpty() ? i + 1 : i - stack.peek();
            stack.push(i);
        }
        
        return span;
    }
    
    /**
     * Test cases for stock span problem.
     */
    public static void runTestCases() {
        StockSpan solver = new StockSpan();
        
        System.out.println("=== Stock Span Test Cases ===\n");
        
        // Test 1: Standard case
        int[] test1 = {100, 80, 60, 70, 60, 75, 85};
        System.out.println("Test 1:");
        System.out.println("Prices: " + Arrays.toString(test1));
        int[] spans1 = solver.calculateSpan(test1);
        System.out.println("Spans:  " + Arrays.toString(spans1));
        System.out.println("Expected: [1, 1, 1, 2, 1, 4, 6]");
        
        // Verify with brute force
        int[] brute1 = solver.calculateSpanBruteForce(test1);
        System.out.println("Matches brute force: " + Arrays.equals(spans1, brute1));
        System.out.println();
        
        // Test 2: Increasing prices
        int[] test2 = {10, 20, 30, 40, 50};
        System.out.println("Test 2 (increasing):");
        System.out.println("Prices: " + Arrays.toString(test2));
        int[] spans2 = solver.calculateSpan(test2);
        System.out.println("Spans:  " + Arrays.toString(spans2));
        System.out.println("Expected: [1, 2, 3, 4, 5]");
        System.out.println();
        
        // Test 3: Decreasing prices
        int[] test3 = {50, 40, 30, 20, 10};
        System.out.println("Test 3 (decreasing):");
        System.out.println("Prices: " + Arrays.toString(test3));
        int[] spans3 = solver.calculateSpan(test3);
        System.out.println("Spans:  " + Arrays.toString(spans3));
        System.out.println("Expected: [1, 1, 1, 1, 1]");
        System.out.println();
        
        // Test 4: Equal prices
        int[] test4 = {42, 42, 42, 42, 42};
        System.out.println("Test 4 (all equal):");
        System.out.println("Prices: " + Arrays.toString(test4));
        int[] spans4 = solver.calculateSpan(test4);
        System.out.println("Spans:  " + Arrays.toString(spans4));
        System.out.println("Expected: [1, 2, 3, 4, 5]");
        System.out.println();
        
        // Test 5: Single day
        int[] test5 = {100};
        System.out.println("Test 5 (single day):");
        System.out.println("Prices: " + Arrays.toString(test5));
        int[] spans5 = solver.calculateSpan(test5);
        System.out.println("Spans:  " + Arrays.toString(spans5));
        System.out.println("Expected: [1]");
        System.out.println();
        
        // Test 6: Random case
        int[] test6 = {31, 27, 14, 21, 30, 22};
        System.out.println("Test 6 (random):");
        System.out.println("Prices: " + Arrays.toString(test6));
        int[] spans6 = solver.calculateSpan(test6);
        System.out.println("Spans:  " + Arrays.toString(spans6));
        System.out.println("Expected: [1, 1, 1, 2, 5, 1]");
        System.out.println();
        
        // Test 7: With previous greater calculation
        System.out.println("Test 7: With previous greater indices:");
        Map<String, int[]> result = solver.calculateSpanWithPreviousGreater(test1);
        System.out.println("Spans: " + Arrays.toString(result.get("span")));
        System.out.println("Previous greater indices: " + Arrays.toString(result.get("previousGreater")));
        System.out.println("(-1 means no previous greater price)");
    }
    
    /**
     * Performance comparison between stack and brute force.
     */
    public static void benchmark() {
        StockSpan solver = new StockSpan();
        
        System.out.println("\n=== Performance Comparison ===");
        
        // Generate large test array
        int n = 1000000;
        int[] prices = new int[n];
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            prices[i] = rand.nextInt(1000) + 1;
        }
        
        System.out.println("Array size: " + n);
        
        // Stack solution
        long start = System.currentTimeMillis();
        int[] result1 = solver.calculateSpan(prices);
        long time1 = System.currentTimeMillis() - start;
        System.out.println("Stack solution (O(n)): " + time1 + " ms");
        
        // Brute force (only for small n)
        if (n <= 10000) {
            start = System.currentTimeMillis();
            int[] result2 = solver.calculateSpanBruteForce(prices);
            long time2 = System.currentTimeMillis() - start;
            System.out.println("Brute force (O(n²)): " + time2 + " ms");
            System.out.println("Speedup: " + (time2 * 1.0 / time1) + "x");
            System.out.println("Results match: " + Arrays.equals(result1, result2));
        } else {
            System.out.println("Brute force skipped for large n (would be too slow)");
        }
    }
    
    /**
     * Real-world application examples.
     */
    public static void showApplications() {
        System.out.println("\n=== Real-world Applications ===");
        System.out.println();
        System.out.println("1. Financial Analysis:");
        System.out.println("   - Identify how long a stock price remains at certain levels");
        System.out.println("   - Detect support/resistance levels");
        System.out.println("   - Analyze trend strength");
        System.out.println();
        System.out.println("2. Trading Strategies:");
        System.out.println("   - Breakout trading: Long spans indicate strong trends");
        System.out.println("   - Mean reversion: Short spans may indicate overbought/oversold");
        System.out.println();
        System.out.println("3. Technical Indicators:");
        System.out.println("   - Component of various technical indicators");
        System.out.println("   - Can be used to calculate price momentum");
        System.out.println();
        System.out.println("4. Algorithmic Components:");
        System.out.println("   - Used in 'Largest Rectangle in Histogram' problem");
        System.out.println("   - Pattern matching in time series data");
        System.out.println("   - Anomaly detection in sequential data");
    }
    
    /**
     * Interactive demonstration with user input.
     */
    public static void interactiveDemo() {
        Scanner scanner = new Scanner(System.in);
        StockSpan solver = new StockSpan();
        
        System.out.println("\n=== Interactive Stock Span Demo ===");
        System.out.println("Enter stock prices separated by spaces (e.g., '100 80 60 70 60 75 85'):");
        
        try {
            String input = scanner.nextLine();
            String[] tokens = input.trim().split("\\s+");
            int[] prices = new int[tokens.length];
            
            for (int i = 0; i < tokens.length; i++) {
                prices[i] = Integer.parseInt(tokens[i]);
            }
            
            System.out.println("\nAnalyzing prices: " + Arrays.toString(prices));
            solver.visualizeSpanCalculation(prices);
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter integers separated by spaces.");
        } finally {
            scanner.close();
        }
    }
    
    public static void main(String[] args) {
        // Run test cases
        runTestCases();
        
        // Visualize a specific example
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Detailed Visualization of Classic Example:");
        System.out.println("=".repeat(60));
        
        StockSpan solver = new StockSpan();
        int[] prices = {100, 80, 60, 70, 60, 75, 85};
        solver.visualizeSpanCalculation(prices);
        
        // Show applications
        showApplications();
        
        // Run performance benchmark (optional)
        // benchmark();
        
        // Interactive demo (optional)
        // interactiveDemo();
    }
}