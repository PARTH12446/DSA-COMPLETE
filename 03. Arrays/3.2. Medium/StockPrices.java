// Problem: LeetCode <ID>. <Title>
/**
 * Problem: Best Time to Buy and Sell Stock
 * Source: Coding Ninjas (https://www.codingninjas.com/studio/problems/best-time-to-bwuy-and-sell-stock_6194560)
 * 
 * Problem Statement:
 * You are given an array/list 'prices' where the elements of the array represent
 * the prices of a particular stock on each day. You need to find the maximum profit
 * you can make by buying and selling the stock. You can only make one transaction
 * (buy once and sell once) and you must buy before you sell.
 * 
 * Approach: One-pass algorithm (Kadane's algorithm variation)
 * Time Complexity: O(n) where n is the number of days
 * Space Complexity: O(1) using only constant extra space
 */
public class StockPrices {

    /**
     * Calculates the maximum profit that can be achieved with one transaction.
     * 
     * Algorithm:
     * 1. Track the minimum price seen so far (minimum buying price)
     * 2. For each day, calculate potential profit if sold on that day
     * 3. Update maximum profit if current profit is higher
     * 4. Update minimum price if current price is lower
     * 
     * @param prices Array of stock prices where prices[i] = price on day i
     * @return Maximum profit achievable, 0 if no profit is possible
     * 
     * Example:
     * Input: [7, 1, 5, 3, 6, 4]
     * Process:
     *   Day 0: mini = 7, profit = 0, maxProfit = 0
     *   Day 1: mini = 1 (updated), profit = 0, maxProfit = 0
     *   Day 2: mini = 1, profit = 4, maxProfit = 4
     *   Day 3: mini = 1, profit = 2, maxProfit = 4
     *   Day 4: mini = 1, profit = 5, maxProfit = 5
     *   Day 5: mini = 1, profit = 3, maxProfit = 5
     * Output: 5 (Buy at 1, sell at 6)
     */
    public static int bestTimeToBuyAndSellStock(int[] prices) {
        // Edge case: If array has less than 2 elements, no transaction is possible
        if (prices == null || prices.length < 2) {
            return 0;
        }
        
        // Initialize minimum price to first day's price
        int mini = prices[0];
        
        // Initialize maximum profit to 0 (minimum profit possible)
        int maxProfit = 0;
        
        // Iterate through each day starting from day 2
        for (int i = 1; i < prices.length; i++) {
            // Calculate potential profit if we bought at 'mini' and sell today
            int currentProfit = prices[i] - mini;
            
            // Update maximum profit if current profit is better
            if (currentProfit > maxProfit) {
                maxProfit = currentProfit;
            }
            
            // Update minimum buying price if today's price is lower
            if (prices[i] < mini) {
                mini = prices[i];
            }
        }
        
        // Return maximum profit (will be 0 if prices continuously decrease)
        return maxProfit;
    }
    
    /**
     * Alternative implementation with explicit comments for each step.
     * This version makes the logic even more explicit for learning purposes.
     */
    public static int bestTimeToBuyAndSellStockAlternative(int[] prices) {
        // Handle edge cases
        if (prices == null || prices.length < 2) {
            return 0;
        }
        
        int minBuyingPrice = prices[0];  // Track lowest price to buy
        int maximumProfit = 0;           // Track highest profit
        
        for (int currentDay = 1; currentDay < prices.length; currentDay++) {
            int currentPrice = prices[currentDay];
            
            // What profit would we get if we bought at minBuyingPrice 
            // and sold at currentPrice?
            int profitIfSoldToday = currentPrice - minBuyingPrice;
            
            // Is this our best profit so far?
            maximumProfit = Math.max(maximumProfit, profitIfSoldToday);
            
            // Can we get a better buying price for future sales?
            minBuyingPrice = Math.min(minBuyingPrice, currentPrice);
        }
        
        return maximumProfit;
    }
    
    /**
     * Main method with test cases to demonstrate functionality.
     */
    public static void main(String[] args) {
        // Test Case 1: Normal case with profit
        int[] prices1 = {7, 1, 5, 3, 6, 4};
        System.out.println("Test Case 1:");
        System.out.println("Prices: [7, 1, 5, 3, 6, 4]");
        System.out.println("Maximum Profit: " + bestTimeToBuyAndSellStock(prices1));
        System.out.println("Expected: 5 (Buy at 1, Sell at 6)");
        System.out.println();
        
        // Test Case 2: Prices continuously decreasing (no profit possible)
        int[] prices2 = {7, 6, 4, 3, 1};
        System.out.println("Test Case 2:");
        System.out.println("Prices: [7, 6, 4, 3, 1]");
        System.out.println("Maximum Profit: " + bestTimeToBuyAndSellStock(prices2));
        System.out.println("Expected: 0 (No profitable transaction)");
        System.out.println();
        
        // Test Case 3: Prices continuously increasing
        int[] prices3 = {1, 2, 3, 4, 5};
        System.out.println("Test Case 3:");
        System.out.println("Prices: [1, 2, 3, 4, 5]");
        System.out.println("Maximum Profit: " + bestTimeToBuyAndSellStock(prices3));
        System.out.println("Expected: 4 (Buy at 1, Sell at 5)");
        System.out.println();
        
        // Test Case 4: Single price (no transaction possible)
        int[] prices4 = {5};
        System.out.println("Test Case 4:");
        System.out.println("Prices: [5]");
        System.out.println("Maximum Profit: " + bestTimeToBuyAndSellStock(prices4));
        System.out.println("Expected: 0 (Need at least 2 days to buy and sell)");
        System.out.println();
        
        // Test Case 5: Same prices throughout
        int[] prices5 = {3, 3, 3, 3, 3};
        System.out.println("Test Case 5:");
        System.out.println("Prices: [3, 3, 3, 3, 3]");
        System.out.println("Maximum Profit: " + bestTimeToBuyAndSellStock(prices5));
        System.out.println("Expected: 0 (No profit with same prices)");
        System.out.println();
        
        // Test Case 6: Random case
        int[] prices6 = {2, 4, 1, 7, 5, 3, 6, 4};
        System.out.println("Test Case 6:");
        System.out.println("Prices: [2, 4, 1, 7, 5, 3, 6, 4]");
        System.out.println("Maximum Profit: " + bestTimeToBuyAndSellStock(prices6));
        System.out.println("Expected: 6 (Buy at 1, Sell at 7)");
        
        // Test alternative implementation
        System.out.println("\nTesting Alternative Implementation:");
        System.out.println("Prices: [7, 1, 5, 3, 6, 4]");
        System.out.println("Alternative Method Result: " + 
                          bestTimeToBuyAndSellStockAlternative(prices1));
        System.out.println("Both methods match: " + 
                          (bestTimeToBuyAndSellStock(prices1) == 
                           bestTimeToBuyAndSellStockAlternative(prices1)));
    }
}

/**
 * Key Insights:
 * 1. We only need to track the minimum price seen so far and maximum profit
 * 2. The selling day is implicitly determined when we find a higher profit
 * 3. We don't need to track the actual buy/sell days, just the profit amount
 * 4. This is essentially finding the maximum difference where larger element comes after smaller
 * 
 * Common Mistakes to Avoid:
 * 1. Not handling edge cases (null array, single element)
 * 2. Trying to use nested loops (O(n²) approach is inefficient)
 * 3. Forgetting that buy must come before sell
 * 4. Not considering the case where no profit is possible (should return 0)
 * 
 * Related Variations of this Problem:
 * 1. Best Time to Buy and Sell Stock II - Multiple transactions allowed
 * 2. Best Time to Buy and Sell Stock III - At most two transactions
 * 3. Best Time to Buy and Sell Stock IV - At most K transactions
 * 4. Best Time to Buy and Sell Stock with Cooldown
 * 5. Best Time to Buy and Sell Stock with Transaction Fee
 */
