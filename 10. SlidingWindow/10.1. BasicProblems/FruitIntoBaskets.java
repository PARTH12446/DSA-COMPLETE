import java.util.*;

/**
 * Fruit Into Baskets (LeetCode 904)
 * 
 * Problem: You are visiting a farm that has a single row of fruit trees.
 * You have two baskets, and each basket can only hold a single type of fruit.
 * There is no limit on the amount of fruit each basket can hold.
 * 
 * Starting from any tree, you must pick exactly one fruit from every tree
 * while moving to the right. The fruits picked must fit in your baskets.
 * Once you reach a tree with fruit that cannot fit in your baskets, stop.
 * 
 * Return the maximum number of fruits you can pick (i.e., maximum window size
 * with at most 2 distinct fruit types).
 * 
 * Problem Restatement: Find the longest contiguous subarray with at most
 * 2 distinct values.
 * 
 * Example: fruits = [1, 2, 1]
 *          We can pick [1, 2, 1] → 3 fruits
 * 
 * Time Complexity: O(n) - Sliding window with frequency map
 * Space Complexity: O(1) - Frequency array of fixed size (fruits.length + 1)
 */
public class FruitIntoBaskets {
    
    /**
     * Calculate maximum number of fruits that can be collected in two baskets.
     * 
     * Algorithm (Sliding Window with Frequency Map):
     * 1. Use array freq to count occurrences of each fruit type in current window
     * 2. Maintain 'distinct' count of unique fruit types in window
     * 3. Expand right pointer, update frequency and distinct count
     * 4. While distinct > 2, shrink from left
     * 5. Track maximum window length
     * 
     * Why this works:
     * - We need at most 2 distinct fruit types (one per basket)
     * - Window [left, right] always contains ≤ 2 distinct types
     * - Maximum window length = maximum fruits we can collect
     * 
     * @param fruits Array where fruits[i] is type of fruit at tree i
     * @return Maximum number of fruits that can be collected
     */
    public int totalFruit(int[] fruits) {
        // Edge cases
        if (fruits == null || fruits.length == 0) {
            return 0;
        }
        
        // Frequency array: index = fruit type, value = count in current window
        // Assuming fruit types are non-negative and within reasonable range
        // If not, we'd use HashMap instead
        int maxFruitType = 0;
        for (int fruit : fruits) {
            maxFruitType = Math.max(maxFruitType, fruit);
        }
        int[] freq = new int[maxFruitType + 1];
        
        int distinct = 0;      // Number of distinct fruit types in current window
        int left = 0;          // Left pointer of sliding window
        int maxFruits = 0;     // Maximum window length found
        
        for (int right = 0; right < fruits.length; right++) {
            // Add fruit at 'right' to window
            int currentFruit = fruits[right];
            if (freq[currentFruit] == 0) {
                distinct++;  // New fruit type in window
            }
            freq[currentFruit]++;
            
            // Shrink window if we have more than 2 distinct fruit types
            while (distinct > 2) {
                int leftFruit = fruits[left];
                freq[leftFruit]--;
                if (freq[leftFruit] == 0) {
                    distinct--;  // This fruit type is no longer in window
                }
                left++;  // Shrink window from left
            }
            
            // Update maximum window length
            int currentWindowSize = right - left + 1;
            maxFruits = Math.max(maxFruits, currentWindowSize);
        }
        
        return maxFruits;
    }
    
    /**
     * Alternative: Using HashMap for general case (any fruit type values).
     * More flexible but slightly slower.
     */
    public int totalFruitHashMap(int[] fruits) {
        if (fruits == null || fruits.length == 0) {
            return 0;
        }
        
        Map<Integer, Integer> fruitCount = new HashMap<>();
        int left = 0;
        int maxFruits = 0;
        
        for (int right = 0; right < fruits.length; right++) {
            // Add current fruit to basket
            int currentFruit = fruits[right];
            fruitCount.put(currentFruit, fruitCount.getOrDefault(currentFruit, 0) + 1);
            
            // While we have more than 2 types of fruits, shrink window
            while (fruitCount.size() > 2) {
                int leftFruit = fruits[left];
                fruitCount.put(leftFruit, fruitCount.get(leftFruit) - 1);
                if (fruitCount.get(leftFruit) == 0) {
                    fruitCount.remove(leftFruit);
                }
                left++;
            }
            
            // Update maximum
            maxFruits = Math.max(maxFruits, right - left + 1);
        }
        
        return maxFruits;
    }
    
    /**
     * Alternative: Optimized sliding window without inner while loop.
     * Uses last occurrence tracking for O(n) time with single pass.
     */
    public int totalFruitOptimized(int[] fruits) {
        if (fruits == null || fruits.length == 0) {
            return 0;
        }
        
        // Track last occurrence indices of two fruit types
        int firstType = -1, secondType = -1;
        int firstLastIndex = -1, secondLastIndex = -1;
        int left = 0, maxFruits = 0;
        
        for (int right = 0; right < fruits.length; right++) {
            int currentFruit = fruits[right];
            
            if (firstType == -1 || currentFruit == firstType) {
                firstType = currentFruit;
                firstLastIndex = right;
            } else if (secondType == -1 || currentFruit == secondType) {
                secondType = currentFruit;
                secondLastIndex = right;
            } else {
                // New third type found, need to discard one basket
                // Keep the type that appeared more recently
                if (firstLastIndex < secondLastIndex) {
                    // Discard first type
                    left = firstLastIndex + 1;
                    firstType = currentFruit;
                    firstLastIndex = right;
                } else {
                    // Discard second type
                    left = secondLastIndex + 1;
                    secondType = currentFruit;
                    secondLastIndex = right;
                }
            }
            
            maxFruits = Math.max(maxFruits, right - left + 1);
        }
        
        return maxFruits;
    }
    
    /**
     * Brute force solution for verification (O(n²)).
     */
    public int totalFruitBruteForce(int[] fruits) {
        if (fruits == null || fruits.length == 0) {
            return 0;
        }
        
        int maxFruits = 0;
        
        for (int start = 0; start < fruits.length; start++) {
            Set<Integer> basket = new HashSet<>();
            int count = 0;
            
            for (int end = start; end < fruits.length; end++) {
                basket.add(fruits[end]);
                if (basket.size() > 2) {
                    break;
                }
                count++;
                maxFruits = Math.max(maxFruits, count);
            }
        }
        
        return maxFruits;
    }
    
    /**
     * Variation: At most K distinct fruit types (generalized).
     */
    public int totalFruitKTypes(int[] fruits, int k) {
        if (fruits == null || fruits.length == 0 || k <= 0) {
            return 0;
        }
        
        Map<Integer, Integer> fruitCount = new HashMap<>();
        int left = 0, maxFruits = 0;
        
        for (int right = 0; right < fruits.length; right++) {
            fruitCount.put(fruits[right], fruitCount.getOrDefault(fruits[right], 0) + 1);
            
            while (fruitCount.size() > k) {
                fruitCount.put(fruits[left], fruitCount.get(fruits[left]) - 1);
                if (fruitCount.get(fruits[left]) == 0) {
                    fruitCount.remove(fruits[left]);
                }
                left++;
            }
            
            maxFruits = Math.max(maxFruits, right - left + 1);
        }
        
        return maxFruits;
    }
    
    /**
     * Visualization helper to show the sliding window process.
     */
    public void visualizeFruitCollection(int[] fruits) {
        System.out.println("\n=== Fruit Collection Visualization ===");
        System.out.println("Fruits: " + Arrays.toString(fruits));
        System.out.println("Baskets available: 2");
        System.out.println();
        
        System.out.println("Window | Fruits in Window | Basket Contents | Distinct | Action | Max So Far");
        System.out.println("-------|------------------|-----------------|----------|--------|-----------");
        
        Map<Integer, Integer> basket = new HashMap<>();
        int left = 0;
        int maxFruits = 0;
        
        for (int right = 0; right < fruits.length; right++) {
            int currentFruit = fruits[right];
            basket.put(currentFruit, basket.getOrDefault(currentFruit, 0) + 1);
            
            String action = "Add fruit " + currentFruit;
            
            // Shrink if more than 2 types
            while (basket.size() > 2) {
                int leftFruit = fruits[left];
                basket.put(leftFruit, basket.get(leftFruit) - 1);
                if (basket.get(leftFruit) == 0) {
                    basket.remove(leftFruit);
                }
                left++;
                action = "Remove fruit " + leftFruit + " (too many types)";
            }
            
            // Get basket contents string
            StringBuilder basketStr = new StringBuilder("{");
            for (Map.Entry<Integer, Integer> entry : basket.entrySet()) {
                basketStr.append("Type ").append(entry.getKey())
                        .append(": ").append(entry.getValue()).append(", ");
            }
            if (basketStr.length() > 1) {
                basketStr.setLength(basketStr.length() - 2);
            }
            basketStr.append("}");
            
            // Window content
            String windowContent = Arrays.toString(
                Arrays.copyOfRange(fruits, left, right + 1)
            );
            
            int currentSize = right - left + 1;
            maxFruits = Math.max(maxFruits, currentSize);
            
            System.out.printf("[%d,%d] | %16s | %15s | %8d | %6s | %11d%n",
                left, right, windowContent, basketStr.toString(),
                basket.size(), action, maxFruits);
        }
        
        System.out.println("\nMaximum fruits that can be collected: " + maxFruits);
    }
    
    /**
     * Test cases for fruit into baskets problem.
     */
    public static void runTestCases() {
        FruitIntoBaskets solver = new FruitIntoBaskets();
        
        System.out.println("=== Fruit Into Baskets Test Cases ===\n");
        
        // Test 1: Standard case
        int[] test1 = {1, 2, 1};
        System.out.println("Test 1:");
        System.out.println("fruits = " + Arrays.toString(test1));
        int result1 = solver.totalFruit(test1);
        System.out.println("Result: " + result1);
        System.out.println("Expected: 3 (collect all fruits: [1, 2, 1])");
        
        // Verify with brute force
        int brute1 = solver.totalFruitBruteForce(test1);
        System.out.println("Brute force: " + brute1 + " (matches: " + (result1 == brute1) + ")");
        System.out.println();
        
        // Test 2: Longer sequence
        int[] test2 = {0, 1, 2, 2};
        System.out.println("Test 2:");
        System.out.println("fruits = " + Arrays.toString(test2));
        int result2 = solver.totalFruit(test2);
        System.out.println("Result: " + result2);
        System.out.println("Expected: 3 (collect [1, 2, 2])");
        System.out.println();
        
        // Test 3: Classic example
        int[] test3 = {1, 2, 3, 2, 2};
        System.out.println("Test 3:");
        System.out.println("fruits = " + Arrays.toString(test3));
        int result3 = solver.totalFruit(test3);
        System.out.println("Result: " + result3);
        System.out.println("Expected: 4 (collect [2, 3, 2, 2] or [3, 2, 2])");
        System.out.println();
        
        // Test 4: All same type
        int[] test4 = {3, 3, 3, 3, 3};
        System.out.println("Test 4 (all same):");
        System.out.println("fruits = " + Arrays.toString(test4));
        int result4 = solver.totalFruit(test4);
        System.out.println("Result: " + result4);
        System.out.println("Expected: 5 (collect all, only one type)");
        System.out.println();
        
        // Test 5: Alternating types
        int[] test5 = {1, 2, 1, 2, 1, 2, 1};
        System.out.println("Test 5 (alternating):");
        System.out.println("fruits = " + Arrays.toString(test5));
        int result5 = solver.totalFruit(test5);
        System.out.println("Result: " + result5);
        System.out.println("Expected: 7 (collect all, only two types)");
        System.out.println();
        
        // Test 6: Empty array
        int[] test6 = {};
        System.out.println("Test 6 (empty):");
        System.out.println("fruits = []");
        int result6 = solver.totalFruit(test6);
        System.out.println("Result: " + result6);
        System.out.println("Expected: 0");
        System.out.println();
        
        // Test 7: Three distinct types
        int[] test7 = {1, 2, 3, 1, 2, 3, 1, 2, 3};
        System.out.println("Test 7 (three types repeating):");
        System.out.println("fruits = " + Arrays.toString(test7));
        int result7 = solver.totalFruit(test7);
        System.out.println("Result: " + result7);
        System.out.println("Expected: 2 (any two consecutive fruits)");
        
        // Test with HashMap approach
        int hashMapResult = solver.totalFruitHashMap(test7);
        System.out.println("HashMap result: " + hashMapResult + " (matches: " + (result7 == hashMapResult) + ")");
        System.out.println();
        
        // Test 8: Edge case with large numbers
        int[] test8 = {100, 200, 100, 200, 300, 100, 200};
        System.out.println("Test 8 (large numbers):");
        System.out.println("fruits = " + Arrays.toString(test8));
        int result8 = solver.totalFruit(test8);
        System.out.println("Result: " + result8);
        System.out.println("Expected: 4 (collect [100, 200, 100, 200])");
    }
    
    /**
     * Performance comparison between different approaches.
     */
    public static void benchmark() {
        FruitIntoBaskets solver = new FruitIntoBaskets();
        
        System.out.println("\n=== Performance Comparison ===");
        
        // Generate large test array
        int n = 1000000;
        int[] fruits = new int[n];
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            // 5 different fruit types
            fruits[i] = rand.nextInt(5);
        }
        
        System.out.println("Array size: " + n + ", 5 fruit types");
        
        // Frequency array solution
        long start = System.currentTimeMillis();
        int result1 = solver.totalFruit(fruits);
        long time1 = System.currentTimeMillis() - start;
        System.out.println("Frequency array: " + time1 + " ms, result: " + result1);
        
        // HashMap solution
        start = System.currentTimeMillis();
        int result2 = solver.totalFruitHashMap(fruits);
        long time2 = System.currentTimeMillis() - start;
        System.out.println("HashMap: " + time2 + " ms, result: " + result2);
        
        // Optimized solution
        start = System.currentTimeMillis();
        int result3 = solver.totalFruitOptimized(fruits);
        long time3 = System.currentTimeMillis() - start;
        System.out.println("Optimized: " + time3 + " ms, result: " + result3);
        
        System.out.println("All results match: " + 
                          (result1 == result2 && result2 == result3));
    }
    
    /**
     * Show generalized solution for K baskets.
     */
    public static void showGeneralizedSolution() {
        System.out.println("\n=== Generalized Solution for K Baskets ===");
        
        FruitIntoBaskets solver = new FruitIntoBaskets();
        int[] fruits = {1, 2, 3, 2, 2, 3, 3, 1, 4};
        
        System.out.println("fruits = " + Arrays.toString(fruits));
        System.out.println();
        
        for (int k = 1; k <= 4; k++) {
            int result = solver.totalFruitKTypes(fruits, k);
            System.out.println("With " + k + " basket(s): maximum fruits = " + result);
            
            // Explain the result
            if (k == 1) {
                System.out.println("  (Longest run of same fruit type)");
            } else if (k == 2) {
                System.out.println("  (Standard problem)");
            } else if (k >= 4) {
                System.out.println("  (Can collect all fruits)");
            }
        }
    }
    
    /**
     * Explain the problem transformation.
     */
    public static void explainTransformation() {
        System.out.println("\n=== Problem Transformation ===");
        System.out.println();
        System.out.println("Original Problem:");
        System.out.println("  - Two baskets, each holds one fruit type");
        System.out.println("  - Pick fruits consecutively from trees");
        System.out.println("  - Stop when you encounter third type");
        System.out.println();
        System.out.println("Mathematical Restatement:");
        System.out.println("  Find longest contiguous subarray with at most 2 distinct values");
        System.out.println();
        System.out.println("Why is this equivalent?");
        System.out.println("  1. Each basket = one fruit type");
        System.out.println("  2. Two baskets = at most two fruit types");
        System.out.println("  3. Consecutive picking = contiguous subarray");
        System.out.println("  4. Stop at third type = window with ≤ 2 distinct values");
        System.out.println();
        System.out.println("Generalization:");
        System.out.println("  With K baskets → at most K distinct values");
        System.out.println("  This becomes standard sliding window problem");
    }
    
    public static void main(String[] args) {
        // Run test cases
        runTestCases();
        
        // Visualize the algorithm
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Detailed Visualization of Example:");
        System.out.println("=".repeat(60));
        
        FruitIntoBaskets solver = new FruitIntoBaskets();
        int[] fruits = {1, 2, 1, 2, 3, 2, 2};
        solver.visualizeFruitCollection(fruits);
        
        // Show generalized solution
        showGeneralizedSolution();
        
        // Explain the transformation
        explainTransformation();
        
        // Run benchmark (optional)
        // benchmark();
    }
}