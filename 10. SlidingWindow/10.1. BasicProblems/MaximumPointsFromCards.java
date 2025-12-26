import java.util.*;

/**
 * Maximum Points You Can Obtain from Cards (LeetCode 1423)
 * 
 * Problem: There are several cards arranged in a row, each with points.
 * You can take exactly k cards from the beginning or end of the row.
 * Return the maximum score you can obtain.
 * 
 * Example: cardPoints = [1, 2, 3, 4, 5, 6, 1], k = 3
 * Take cards: 1 (end) + 6 (end) + 5 (end) = 12
 * Or: 1 (start) + 2 (start) + 1 (end) = 4 (not optimal)
 * 
 * Key Insight: The problem is equivalent to finding the minimum sum
 * of a contiguous subarray of length (n - k), then subtracting from total.
 * 
 * Why? Because cards taken from ends = total cards - cards left in middle.
 * Maximizing ends = minimizing middle subarray of length (n - k).
 * 
 * Time Complexity: O(n) - Single pass with sliding window
 * Space Complexity: O(1) - Only a few variables
 */
public class MaximumPointsFromCards {
    
    /**
     * Calculates maximum points by picking k cards from ends.
     * 
     * Algorithm (Sliding Window):
     * 1. Calculate total sum of all cards
     * 2. We need to leave (n - k) cards in the middle (not picked)
     * 3. Find minimum sum of any contiguous subarray of length (n - k)
     * 4. Maximum points = total - minimum middle sum
     * 
     * Mathematical Proof:
     * Let total = sum of all cards
     * Let middle_sum = sum of cards not taken (length = n - k)
     * Then points = total - middle_sum
     * To maximize points, we minimize middle_sum
     * 
     * @param cardPoints Array of points on each card
     * @param k Number of cards to pick from ends
     * @return Maximum points achievable
     */
    public int maxScore(int[] cardPoints, int k) {
        // Edge cases
        if (cardPoints == null || cardPoints.length == 0 || k <= 0) {
            return 0;
        }
        
        int n = cardPoints.length;
        
        // If we can take all cards, return total sum
        if (k >= n) {
            int total = 0;
            for (int points : cardPoints) total += points;
            return total;
        }
        
        // Calculate total sum of all cards
        int totalSum = 0;
        for (int points : cardPoints) {
            totalSum += points;
        }
        
        // We need to leave (n - k) cards in the middle
        int windowSize = n - k;
        
        // Calculate sum of first window of size (n - k)
        int windowSum = 0;
        for (int i = 0; i < windowSize; i++) {
            windowSum += cardPoints[i];
        }
        
        // Track minimum window sum
        int minWindowSum = windowSum;
        
        // Slide window to find minimum sum of any subarray of size (n - k)
        for (int right = windowSize; right < n; right++) {
            // Add new element, remove leftmost element
            windowSum += cardPoints[right] - cardPoints[right - windowSize];
            minWindowSum = Math.min(minWindowSum, windowSum);
        }
        
        // Maximum points = total - minimum middle sum
        return totalSum - minWindowSum;
    }
    
    /**
     * Alternative: Prefix sum approach.
     * More intuitive but uses O(n) space.
     */
    public int maxScorePrefixSum(int[] cardPoints, int k) {
        if (cardPoints == null || cardPoints.length == 0 || k <= 0) return 0;
        
        int n = cardPoints.length;
        if (k >= n) {
            int sum = 0;
            for (int points : cardPoints) sum += points;
            return sum;
        }
        
        // Calculate prefix sums
        int[] prefixSum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + cardPoints[i];
        }
        
        int maxScore = 0;
        
        // Try all possibilities: take i cards from left, (k-i) from right
        for (int leftCards = 0; leftCards <= k; leftCards++) {
            int rightCards = k - leftCards;
            
            int leftSum = prefixSum[leftCards]; // Sum of first 'leftCards' cards
            int rightSum = prefixSum[n] - prefixSum[n - rightCards]; // Sum of last 'rightCards' cards
            
            maxScore = Math.max(maxScore, leftSum + rightSum);
        }
        
        return maxScore;
    }
    
    /**
     * Alternative: Two-pointer sliding window from ends.
     * Direct approach without total sum calculation.
     */
    public int maxScoreTwoPointer(int[] cardPoints, int k) {
        if (cardPoints == null || cardPoints.length == 0 || k <= 0) return 0;
        
        int n = cardPoints.length;
        if (k >= n) {
            int sum = 0;
            for (int points : cardPoints) sum += points;
            return sum;
        }
        
        // Start by taking all k cards from the left
        int leftSum = 0;
        for (int i = 0; i < k; i++) {
            leftSum += cardPoints[i];
        }
        
        int maxScore = leftSum;
        int rightSum = 0;
        
        // Gradually replace left cards with right cards
        for (int i = 0; i < k; i++) {
            // Remove one card from left end
            leftSum -= cardPoints[k - 1 - i];
            // Add one card from right end
            rightSum += cardPoints[n - 1 - i];
            
            maxScore = Math.max(maxScore, leftSum + rightSum);
        }
        
        return maxScore;
    }
    
    /**
     * Variation: Return which cards to pick (indices).
     */
    public List<Integer> maxScoreCards(int[] cardPoints, int k) {
        List<Integer> result = new ArrayList<>();
        if (cardPoints == null || cardPoints.length == 0 || k <= 0) {
            return result;
        }
        
        int n = cardPoints.length;
        if (k >= n) {
            for (int i = 0; i < n; i++) result.add(i);
            return result;
        }
        
        // Use prefix sum approach to find optimal split
        int[] prefixSum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + cardPoints[i];
        }
        
        int maxScore = 0;
        int bestLeft = 0, bestRight = 0;
        
        for (int leftCards = 0; leftCards <= k; leftCards++) {
            int rightCards = k - leftCards;
            
            int leftSum = prefixSum[leftCards];
            int rightSum = prefixSum[n] - prefixSum[n - rightCards];
            int total = leftSum + rightSum;
            
            if (total > maxScore) {
                maxScore = total;
                bestLeft = leftCards;
                bestRight = rightCards;
            }
        }
        
        // Add left cards
        for (int i = 0; i < bestLeft; i++) {
            result.add(i);
        }
        
        // Add right cards
        for (int i = n - bestRight; i < n; i++) {
            result.add(i);
        }
        
        return result;
    }
    
    /**
     * Brute force solution for verification (O(k²)).
     */
    public int maxScoreBruteForce(int[] cardPoints, int k) {
        if (cardPoints == null || cardPoints.length == 0 || k <= 0) return 0;
        
        int n = cardPoints.length;
        if (k >= n) {
            int sum = 0;
            for (int points : cardPoints) sum += points;
            return sum;
        }
        
        int maxScore = 0;
        
        // Try all possible splits: i cards from left, (k-i) from right
        for (int leftCards = 0; leftCards <= k; leftCards++) {
            int rightCards = k - leftCards;
            int score = 0;
            
            // Sum left cards
            for (int i = 0; i < leftCards; i++) {
                score += cardPoints[i];
            }
            
            // Sum right cards
            for (int i = 0; i < rightCards; i++) {
                score += cardPoints[n - 1 - i];
            }
            
            maxScore = Math.max(maxScore, score);
        }
        
        return maxScore;
    }
    
    /**
     * Visualization helper to show the sliding window process.
     */
    public void visualizeMaxScore(int[] cardPoints, int k) {
        System.out.println("\n=== Maximum Points from Cards Visualization ===");
        System.out.println("Cards: " + Arrays.toString(cardPoints));
        System.out.println("k = " + k + " cards to pick from ends");
        System.out.println("n = " + cardPoints.length + " total cards");
        System.out.println();
        
        int n = cardPoints.length;
        int totalSum = 0;
        for (int points : cardPoints) totalSum += points;
        System.out.println("Total sum of all cards: " + totalSum);
        
        int windowSize = n - k;
        System.out.println("Need to leave " + windowSize + " cards in the middle (not picked)");
        System.out.println();
        
        System.out.println("Finding minimum sum of any " + windowSize + " consecutive cards:");
        System.out.println("Window | Cards in Window | Window Sum | Min So Far");
        System.out.println("-------|-----------------|------------|-----------");
        
        // Calculate first window
        int windowSum = 0;
        for (int i = 0; i < windowSize; i++) {
            windowSum += cardPoints[i];
        }
        int minWindowSum = windowSum;
        
        System.out.printf("[0,%d] | %15s | %10d | %10d%n",
            windowSize - 1,
            Arrays.toString(Arrays.copyOfRange(cardPoints, 0, windowSize)),
            windowSum, minWindowSum);
        
        // Slide window
        for (int right = windowSize; right < n; right++) {
            int left = right - windowSize + 1;
            windowSum += cardPoints[right] - cardPoints[right - windowSize];
            minWindowSum = Math.min(minWindowSum, windowSum);
            
            System.out.printf("[%d,%d] | %15s | %10d | %10d%n",
                left, right,
                Arrays.toString(Arrays.copyOfRange(cardPoints, left, right + 1)),
                windowSum, minWindowSum);
        }
        
        System.out.println("\nMinimum middle sum: " + minWindowSum);
        System.out.println("Maximum points = Total - Minimum middle = " + totalSum + " - " + minWindowSum + " = " + (totalSum - minWindowSum));
        
        // Show which cards to pick
        System.out.println("\nOptimal strategy:");
        System.out.println("Leave these " + windowSize + " cards in middle (sum = " + minWindowSum + ")");
        System.out.println("Pick all other cards from ends");
    }
    
    /**
     * Test cases for maximum points from cards problem.
     */
    public static void runTestCases() {
        MaximumPointsFromCards solver = new MaximumPointsFromCards();
        
        System.out.println("=== Maximum Points from Cards Test Cases ===\n");
        
        // Test 1: Standard case
        int[] test1 = {1, 2, 3, 4, 5, 6, 1};
        int k1 = 3;
        System.out.println("Test 1:");
        System.out.println("cards = " + Arrays.toString(test1) + ", k = " + k1);
        int result1 = solver.maxScore(test1, k1);
        System.out.println("Result: " + result1);
        System.out.println("Expected: 12 (take 1 + 6 + 5 from right end)");
        
        // Verify with brute force
        int brute1 = solver.maxScoreBruteForce(test1, k1);
        System.out.println("Brute force: " + brute1 + " (matches: " + (result1 == brute1) + ")");
        System.out.println("Cards to pick: " + solver.maxScoreCards(test1, k1));
        System.out.println();
        
        // Test 2: All cards from one side
        int[] test2 = {2, 2, 2};
        int k2 = 2;
        System.out.println("Test 2:");
        System.out.println("cards = " + Arrays.toString(test2) + ", k = " + k2);
        int result2 = solver.maxScore(test2, k2);
        System.out.println("Result: " + result2);
        System.out.println("Expected: 4 (take two cards, all have same value)");
        System.out.println();
        
        // Test 3: k equals array length
        int[] test3 = {1, 79, 80, 1, 1, 1, 200, 1};
        int k3 = 8;
        System.out.println("Test 3 (k = n):");
        System.out.println("cards = " + Arrays.toString(test3) + ", k = " + k3);
        int result3 = solver.maxScore(test3, k3);
        System.out.println("Result: " + result3);
        System.out.println("Expected: 364 (sum of all cards)");
        System.out.println();
        
        // Test 4: Single card
        int[] test4 = {100};
        int k4 = 1;
        System.out.println("Test 4 (single card):");
        System.out.println("cards = " + Arrays.toString(test4) + ", k = " + k4);
        int result4 = solver.maxScore(test4, k4);
        System.out.println("Result: " + result4);
        System.out.println("Expected: 100");
        System.out.println();
        
        // Test 5: From LeetCode
        int[] test5 = {9, 7, 7, 9, 7, 7, 9};
        int k5 = 7;
        System.out.println("Test 5:");
        System.out.println("cards = " + Arrays.toString(test5) + ", k = " + k5);
        int result5 = solver.maxScore(test5, k5);
        System.out.println("Result: " + result5);
        System.out.println("Expected: 55 (sum of all cards)");
        System.out.println();
        
        // Test 6: Complex case
        int[] test6 = {11, 49, 100, 20, 86, 29, 72};
        int k6 = 4;
        System.out.println("Test 6:");
        System.out.println("cards = " + Arrays.toString(test6) + ", k = " + k6);
        int result6 = solver.maxScore(test6, k6);
        System.out.println("Result: " + result6);
        System.out.println("Expected: 232 (take 11+49+72+100)");
        
        // Test different implementations
        System.out.println("\nTesting different implementations:");
        System.out.println("Sliding window: " + solver.maxScore(test6, k6));
        System.out.println("Prefix sum: " + solver.maxScorePrefixSum(test6, k6));
        System.out.println("Two-pointer: " + solver.maxScoreTwoPointer(test6, k6));
    }
    
    /**
     * Performance comparison between different approaches.
     */
    public static void benchmark() {
        MaximumPointsFromCards solver = new MaximumPointsFromCards();
        
        System.out.println("\n=== Performance Comparison ===");
        
        // Generate large test array
        int n = 1000000;
        int k = 500000;
        int[] cards = new int[n];
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            cards[i] = rand.nextInt(1000) + 1;
        }
        
        System.out.println("Array size: " + n + ", k = " + k);
        
        // Sliding window approach
        long start = System.currentTimeMillis();
        int result1 = solver.maxScore(cards, k);
        long time1 = System.currentTimeMillis() - start;
        System.out.println("Sliding window: " + time1 + " ms, result: " + result1);
        
        // Two-pointer approach
        start = System.currentTimeMillis();
        int result2 = solver.maxScoreTwoPointer(cards, k);
        long time2 = System.currentTimeMillis() - start;
        System.out.println("Two-pointer: " + time2 + " ms, result: " + result2);
        
        // Prefix sum approach
        start = System.currentTimeMillis();
        int result3 = solver.maxScorePrefixSum(cards, k);
        long time3 = System.currentTimeMillis() - start;
        System.out.println("Prefix sum: " + time3 + " ms, result: " + result3);
        
        System.out.println("All results match: " + 
                          (result1 == result2 && result2 == result3));
        System.out.println("Sliding window is most efficient for this problem");
    }
    
    /**
     * Explain the mathematical transformation.
     */
    public static void explainTransformation() {
        System.out.println("\n=== Mathematical Transformation ===");
        System.out.println();
        System.out.println("Let cards = [c₀, c₁, ..., cₙ₋₁]");
        System.out.println("Let k = number of cards to pick from ends");
        System.out.println("Let n = total number of cards");
        System.out.println();
        System.out.println("Observation 1:");
        System.out.println("  When we pick k cards from ends,");
        System.out.println("  we leave (n - k) cards in the middle");
        System.out.println();
        System.out.println("Observation 2:");
        System.out.println("  Points = sum of picked cards");
        System.out.println("          = total sum - sum of cards left in middle");
        System.out.println();
        System.out.println("Therefore:");
        System.out.println("  Maximize points = Minimize sum of middle cards");
        System.out.println("  Middle cards form a contiguous subarray of length (n - k)");
        System.out.println();
        System.out.println("Example: cards = [1,2,3,4,5,6,1], k = 3");
        System.out.println("  n = 7, n - k = 4 cards left in middle");
        System.out.println("  Find minimum sum of 4 consecutive cards");
        System.out.println("  Minimum middle sum = 1+2+3+4 = 10");
        System.out.println("  Total sum = 22");
        System.out.println("  Maximum points = 22 - 10 = 12");
    }
    
    /**
     * Show edge cases and special scenarios.
     */
    public static void showEdgeCases() {
        System.out.println("\n=== Edge Cases and Special Scenarios ===");
        
        MaximumPointsFromCards solver = new MaximumPointsFromCards();
        
        // Case 1: k = 0
        int[] case1 = {1, 2, 3, 4, 5};
        int k1 = 0;
        System.out.println("Case 1: k = 0 (pick no cards)");
        System.out.println("Result: " + solver.maxScore(case1, k1));
        System.out.println("Expected: 0");
        System.out.println();
        
        // Case 2: All negative points (though problem states non-negative)
        int[] case2 = {-1, -2, -3, -4};
        int k2 = 2;
        System.out.println("Case 2: Negative points (hypothetical)");
        System.out.println("Result: " + solver.maxScore(case2, k2));
        System.out.println("Explanation: Algorithm still works mathematically");
        System.out.println();
        
        // Case 3: Very large k
        int[] case3 = {10, 20, 30};
        int k3 = 100;
        System.out.println("Case 3: k > n");
        System.out.println("Result: " + solver.maxScore(case3, k3));
        System.out.println("Expected: 60 (sum of all cards)");
        System.out.println();
        
        // Case 4: All cards have same value
        int[] case4 = {5, 5, 5, 5, 5};
        int k4 = 3;
        System.out.println("Case 4: All cards have same value");
        System.out.println("Result: " + solver.maxScore(case4, k4));
        System.out.println("Expected: 15 (any 3 cards)");
    }
    
    public static void main(String[] args) {
        // Run test cases
        runTestCases();
        
        // Visualize the algorithm
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Detailed Visualization of Example:");
        System.out.println("=".repeat(60));
        
        MaximumPointsFromCards solver = new MaximumPointsFromCards();
        int[] cards = {1, 2, 3, 4, 5, 6, 1};
        int k = 3;
        solver.visualizeMaxScore(cards, k);
        
        // Explain the transformation
        explainTransformation();
        
        // Show edge cases
        showEdgeCases();
        
        // Run benchmark (optional)
        // benchmark();
    }
}