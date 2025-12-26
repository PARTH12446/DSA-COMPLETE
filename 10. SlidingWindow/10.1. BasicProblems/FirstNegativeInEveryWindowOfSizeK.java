import java.util.*;

/**
 * First Negative Integer in Every Window of Size K
 * 
 * Problem: Given an array of integers and a window size k,
 * find the first negative integer in every window of size k.
 * If a window does not contain any negative integer, return 0.
 * 
 * Example: nums = [12, -1, -7, 8, -15, 30, 16, 28], k = 3
 * Windows: [12, -1, -7] → -1, [-1, -7, 8] → -1, [-7, 8, -15] → -7,
 *          [8, -15, 30] → -15, [-15, 30, 16] → -15, [30, 16, 28] → 0
 * Result: [-1, -1, -7, -15, -15, 0]
 * 
 * Approaches:
 * 1. Brute force: O(n*k) time, O(1) space
 * 2. Sliding window with queue: O(n) time, O(k) space
 * 3. Sliding window with index tracking: O(n) time, O(1) space (implemented here)
 * 
 * Time Complexity: O(n) - Each element processed at most twice
 * Space Complexity: O(1) - Only a few variables
 */
public class FirstNegativeInEveryWindowOfSizeK {
    
    /**
     * Finds first negative integer in every window of size k.
     * Uses sliding window with index tracking for O(1) space.
     * 
     * Algorithm:
     * 1. Track index of first negative in current window (firstNegIndex)
     * 2. For first window [0, k-1], find first negative
     * 3. For subsequent windows:
     *    a. Slide window right by 1
     *    b. If previous first negative is now out of window, reset and find new
     *    c. Otherwise, keep same first negative
     *    d. Check new element (rightmost) if it's negative and we don't have one
     * 
     * @param nums Array of integers
     * @param k Window size
     * @return List containing first negative in each window (0 if none)
     */
    public List<Integer> firstNegativeInWindow(int[] nums, int k) {
        List<Integer> result = new ArrayList<>();
        
        // Edge cases
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            return result;
        }
        
        // Special case: window size = 1
        if (k == 1) {
            for (int num : nums) {
                result.add(num < 0 ? num : 0);
            }
            return result;
        }
        
        // Track index of first negative in current window
        int firstNegIndex = -1; // -1 means no negative found in current window
        
        // Initialize first window [0, k-1]
        for (int i = 0; i < k; i++) {
            if (nums[i] < 0 && firstNegIndex == -1) {
                firstNegIndex = i;
            }
        }
        
        // Add result for first window
        result.add(firstNegIndex != -1 ? nums[firstNegIndex] : 0);
        
        // Process remaining windows using sliding window
        for (int right = k; right < nums.length; right++) {
            int left = right - k + 1; // Start of current window
            
            // Check if previous first negative is still in current window
            if (firstNegIndex < left) {
                // First negative from previous window is now out of window
                firstNegIndex = -1;
                
                // Find first negative in current window
                for (int i = left; i <= right; i++) {
                    if (nums[i] < 0) {
                        firstNegIndex = i;
                        break;
                    }
                }
            } else {
                // Previous first negative is still in window, check new element
                if (firstNegIndex == -1 && nums[right] < 0) {
                    // We didn't have a negative, but new element is negative
                    firstNegIndex = right;
                }
            }
            
            // Add result for current window
            result.add(firstNegIndex != -1 ? nums[firstNegIndex] : 0);
        }
        
        return result;
    }
    
    /**
     * Alternative: Queue-based solution.
     * Uses queue to store indices of negative numbers in current window.
     * More intuitive but uses O(k) space.
     * 
     * Algorithm:
     * 1. Process first k elements, add negative indices to queue
     * 2. For each window:
     *    a. Front of queue is first negative (if queue not empty)
     *    b. Remove indices from front that are out of window
     *    c. Add new element if negative
     */
    public List<Integer> firstNegativeInWindowQueue(int[] nums, int k) {
        List<Integer> result = new ArrayList<>();
        
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            return result;
        }
        
        Queue<Integer> negIndices = new LinkedList<>();
        
        // Process first k elements
        for (int i = 0; i < k; i++) {
            if (nums[i] < 0) {
                negIndices.offer(i);
            }
        }
        
        // Add result for first window
        result.add(!negIndices.isEmpty() ? nums[negIndices.peek()] : 0);
        
        // Process remaining windows
        for (int right = k; right < nums.length; right++) {
            int left = right - k + 1;
            
            // Remove indices that are out of current window
            while (!negIndices.isEmpty() && negIndices.peek() < left) {
                negIndices.poll();
            }
            
            // Add new element if negative
            if (nums[right] < 0) {
                negIndices.offer(right);
            }
            
            // Add result for current window
            result.add(!negIndices.isEmpty() ? nums[negIndices.peek()] : 0);
        }
        
        return result;
    }
    
    /**
     * Brute force solution for verification (O(n*k)).
     */
    public List<Integer> firstNegativeInWindowBruteForce(int[] nums, int k) {
        List<Integer> result = new ArrayList<>();
        
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            return result;
        }
        
        for (int i = 0; i <= nums.length - k; i++) {
            int firstNegative = 0; // Default to 0 (no negative)
            
            for (int j = i; j < i + k; j++) {
                if (nums[j] < 0) {
                    firstNegative = nums[j];
                    break;
                }
            }
            
            result.add(firstNegative);
        }
        
        return result;
    }
    
    /**
     * Variation: Return index instead of value.
     * Returns -1 if no negative in window.
     */
    public List<Integer> firstNegativeIndexInWindow(int[] nums, int k) {
        List<Integer> result = new ArrayList<>();
        
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            return result;
        }
        
        int firstNegIndex = -1;
        
        // Initialize first window
        for (int i = 0; i < k; i++) {
            if (nums[i] < 0 && firstNegIndex == -1) {
                firstNegIndex = i;
            }
        }
        result.add(firstNegIndex);
        
        // Process remaining windows
        for (int right = k; right < nums.length; right++) {
            int left = right - k + 1;
            
            if (firstNegIndex < left) {
                firstNegIndex = -1;
                for (int i = left; i <= right; i++) {
                    if (nums[i] < 0) {
                        firstNegIndex = i;
                        break;
                    }
                }
            } else if (firstNegIndex == -1 && nums[right] < 0) {
                firstNegIndex = right;
            }
            
            result.add(firstNegIndex);
        }
        
        return result;
    }
    
    /**
     * Visualization helper to show the sliding window process.
     */
    public void visualizeWindowProcess(int[] nums, int k) {
        System.out.println("\n=== First Negative in Window Visualization ===");
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("Window size k = " + k);
        System.out.println();
        
        System.out.println("Window | Window Content | First Negative Index | First Negative | Action");
        System.out.println("-------|----------------|----------------------|----------------|--------");
        
        int firstNegIndex = -1;
        
        // Initialize first window
        for (int i = 0; i < k; i++) {
            if (nums[i] < 0 && firstNegIndex == -1) {
                firstNegIndex = i;
            }
        }
        
        // Print first window
        String window1 = "[0," + (k-1) + "]";
        String content1 = Arrays.toString(Arrays.copyOfRange(nums, 0, k));
        System.out.printf("%6s | %14s | %20d | %14d | %s%n",
            window1, content1, firstNegIndex, 
            firstNegIndex != -1 ? nums[firstNegIndex] : 0,
            "Initial window");
        
        // Process remaining windows
        for (int right = k; right < nums.length; right++) {
            int left = right - k + 1;
            String window = "[" + left + "," + right + "]";
            String content = Arrays.toString(Arrays.copyOfRange(nums, left, right + 1));
            String action = "";
            
            // Update first negative index
            if (firstNegIndex < left) {
                // Previous first negative is out of window
                firstNegIndex = -1;
                action = "Previous first negative out of window, searching...";
                
                // Find new first negative
                for (int i = left; i <= right; i++) {
                    if (nums[i] < 0) {
                        firstNegIndex = i;
                        action = "Found new first negative at index " + i;
                        break;
                    }
                }
                
                if (firstNegIndex == -1) {
                    action = "No negative found in current window";
                }
            } else if (firstNegIndex == -1 && nums[right] < 0) {
                // New element is negative and we didn't have one
                firstNegIndex = right;
                action = "New element at index " + right + " is first negative";
            } else {
                action = "Keep previous first negative";
            }
            
            System.out.printf("%6s | %14s | %20d | %14d | %s%n",
                window, content, firstNegIndex,
                firstNegIndex != -1 ? nums[firstNegIndex] : 0,
                action);
        }
    }
    
    /**
     * Test cases for first negative in window problem.
     */
    public static void runTestCases() {
        FirstNegativeInEveryWindowOfSizeK solver = new FirstNegativeInEveryWindowOfSizeK();
        
        System.out.println("=== First Negative in Window Test Cases ===\n");
        
        // Test 1: Standard case
        int[] test1 = {12, -1, -7, 8, -15, 30, 16, 28};
        int k1 = 3;
        System.out.println("Test 1:");
        System.out.println("nums = " + Arrays.toString(test1) + ", k = " + k1);
        List<Integer> result1 = solver.firstNegativeInWindow(test1, k1);
        System.out.println("Result: " + result1);
        System.out.println("Expected: [-1, -1, -7, -15, -15, 0]");
        
        // Verify with brute force
        List<Integer> brute1 = solver.firstNegativeInWindowBruteForce(test1, k1);
        System.out.println("Brute force: " + brute1 + " (matches: " + result1.equals(brute1) + ")");
        System.out.println();
        
        // Test 2: All positive
        int[] test2 = {1, 2, 3, 4, 5};
        int k2 = 2;
        System.out.println("Test 2 (all positive):");
        System.out.println("nums = " + Arrays.toString(test2) + ", k = " + k2);
        List<Integer> result2 = solver.firstNegativeInWindow(test2, k2);
        System.out.println("Result: " + result2);
        System.out.println("Expected: [0, 0, 0, 0]");
        System.out.println();
        
        // Test 3: All negative
        int[] test3 = {-1, -2, -3, -4, -5};
        int k3 = 2;
        System.out.println("Test 3 (all negative):");
        System.out.println("nums = " + Arrays.toString(test3) + ", k = " + k3);
        List<Integer> result3 = solver.firstNegativeInWindow(test3, k3);
        System.out.println("Result: " + result3);
        System.out.println("Expected: [-1, -2, -3, -4]");
        System.out.println();
        
        // Test 4: Single negative
        int[] test4 = {1, 2, -3, 4, 5};
        int k4 = 3;
        System.out.println("Test 4 (single negative):");
        System.out.println("nums = " + Arrays.toString(test4) + ", k = " + k4);
        List<Integer> result4 = solver.firstNegativeInWindow(test4, k4);
        System.out.println("Result: " + result4);
        System.out.println("Expected: [-3, -3, -3]");
        System.out.println();
        
        // Test 5: Window size = 1
        int[] test5 = {1, -2, 3, -4, 5};
        int k5 = 1;
        System.out.println("Test 5 (k=1):");
        System.out.println("nums = " + Arrays.toString(test5) + ", k = " + k5);
        List<Integer> result5 = solver.firstNegativeInWindow(test5, k5);
        System.out.println("Result: " + result5);
        System.out.println("Expected: [0, -2, 0, -4, 0]");
        System.out.println();
        
        // Test 6: Window size = array length
        int[] test6 = {1, -2, 3, -4, 5};
        int k6 = 5;
        System.out.println("Test 6 (k=n):");
        System.out.println("nums = " + Arrays.toString(test6) + ", k = " + k6);
        List<Integer> result6 = solver.firstNegativeInWindow(test6, k6);
        System.out.println("Result: " + result6);
        System.out.println("Expected: [-2] (first negative in whole array)");
        System.out.println();
        
        // Test 7: Mixed with zeros
        int[] test7 = {0, -1, 2, 0, -3, 4};
        int k7 = 2;
        System.out.println("Test 7 (with zeros):");
        System.out.println("nums = " + Arrays.toString(test7) + ", k = " + k7);
        List<Integer> result7 = solver.firstNegativeInWindow(test7, k7);
        System.out.println("Result: " + result7);
        System.out.println("Expected: [-1, -1, 0, -3, -3]");
        System.out.println();
        
        // Test queue solution
        System.out.println("Test Queue solution (same as Test 1):");
        List<Integer> queueResult = solver.firstNegativeInWindowQueue(test1, k1);
        System.out.println("Queue result: " + queueResult + " (matches: " + result1.equals(queueResult) + ")");
    }
    
    /**
     * Performance comparison between different approaches.
     */
    public static void benchmark() {
        FirstNegativeInEveryWindowOfSizeK solver = new FirstNegativeInEveryWindowOfSizeK();
        
        System.out.println("\n=== Performance Comparison ===");
        
        // Generate large test array
        int n = 1000000;
        int k = 1000;
        int[] nums = new int[n];
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            // 20% negative numbers
            nums[i] = rand.nextInt(100) - 20;
        }
        
        System.out.println("Array size: " + n + ", Window size: " + k);
        System.out.println("(20% negative numbers)");
        
        // Index tracking solution
        long start = System.currentTimeMillis();
        List<Integer> result1 = solver.firstNegativeInWindow(nums, k);
        long time1 = System.currentTimeMillis() - start;
        System.out.println("Index tracking: " + time1 + " ms");
        
        // Queue solution
        start = System.currentTimeMillis();
        List<Integer> result2 = solver.firstNegativeInWindowQueue(nums, k);
        long time2 = System.currentTimeMillis() - start;
        System.out.println("Queue solution: " + time2 + " ms");
        
        // Brute force (only for small sizes)
        if (n <= 10000 && k <= 100) {
            start = System.currentTimeMillis();
            List<Integer> result3 = solver.firstNegativeInWindowBruteForce(nums, k);
            long time3 = System.currentTimeMillis() - start;
            System.out.println("Brute force: " + time3 + " ms");
        }
        
        System.out.println("Results match: " + result1.equals(result2));
        System.out.println("Queue uses O(k) space, Index tracking uses O(1) space");
    }
    
    /**
     * Real-world applications of window-based problems.
     */
    public static void showApplications() {
        System.out.println("\n=== Real-world Applications ===");
        System.out.println();
        System.out.println("1. Financial Analysis:");
        System.out.println("   - Find first negative return in rolling time window");
        System.out.println("   - Detect first loss in consecutive trading periods");
        System.out.println();
        System.out.println("2. Signal Processing:");
        System.out.println("   - Find first negative amplitude in sliding time window");
        System.out.println("   - Detect signal crossing below zero");
        System.out.println();
        System.out.println("3. Quality Control:");
        System.out.println("   - Find first defective item in batches (windows)");
        System.out.println("   - Monitor production line for anomalies");
        System.out.println();
        System.out.println("4. Network Monitoring:");
        System.out.println("   - Detect first packet loss in time windows");
        System.out.println("   - Find negative latency spikes");
        System.out.println();
        System.out.println("5. Algorithm Building Block:");
        System.out.println("   - Component in larger sliding window algorithms");
        System.out.println("   - Pattern matching in time series data");
    }
    
    /**
     * Explain the algorithm optimization.
     */
    public static void explainOptimization() {
        System.out.println("\n=== Algorithm Optimization ===");
        System.out.println();
        System.out.println("Brute Force (O(n*k)):");
        System.out.println("  For each of (n-k+1) windows:");
        System.out.println("    Scan k elements to find first negative");
        System.out.println("  Total operations: (n-k+1)*k ≈ n*k for k ≪ n");
        System.out.println();
        System.out.println("Optimized (O(n)):");
        System.out.println("  Key insight: When sliding window by 1:");
        System.out.println("    - Only one element leaves window");
        System.out.println("    - Only one element enters window");
        System.out.println("  Maintain first negative index:");
        System.out.println("    1. If leaving element was first negative, find new one");
        System.out.println("    2. If entering element is negative and we don't have one, use it");
        System.out.println("    3. Otherwise, keep current first negative");
        System.out.println("  Each element processed at most twice → O(n)");
    }
    
    public static void main(String[] args) {
        // Run test cases
        runTestCases();
        
        // Visualize the algorithm
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Detailed Visualization of Example:");
        System.out.println("=".repeat(60));
        
        FirstNegativeInEveryWindowOfSizeK solver = new FirstNegativeInEveryWindowOfSizeK();
        int[] nums = {12, -1, -7, 8, -15, 30, 16, 28};
        int k = 3;
        solver.visualizeWindowProcess(nums, k);
        
        // Show applications
        showApplications();
        
        // Explain optimization
        explainOptimization();
        
        // Run benchmark (optional)
        // benchmark();
    }
}