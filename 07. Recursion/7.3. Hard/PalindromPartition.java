import java.util.ArrayList;
import java.util.List;

/**
 * Palindrome Partitioning
 * 
 * Problem: Given a string s, partition s such that every substring 
 * of the partition is a palindrome. Return all possible palindrome 
 * partitioning of s.
 * 
 * Example: s = "aab"
 * Output: [["a","a","b"], ["aa","b"]]
 * 
 * Applications:
 * - Text segmentation in NLP
 * - Data compression
 * - DNA sequence analysis
 * - Word segmentation in languages without spaces
 */
public class PalindromPartition {

    /**
     * Main method to find all palindrome partitions.
     * 
     * @param s Input string
     * @return List of all palindrome partitions
     * 
     * Time Complexity: O(n * 2^n) in worst case
     * - There are 2^(n-1) possible partition points
     * - Each palindrome check takes O(n) in naive approach
     * - With DP preprocessing: O(n² + 2^n)
     * Space Complexity: O(n) recursion stack + O(n²) for DP table
     */
    public static List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        // Precompute palindrome table for O(1) palindrome checks
        boolean[][] isPal = precomputePalindromes(s);
        backtrack(0, s, new ArrayList<>(), res, isPal);
        return res;
    }

    /**
     * Recursive backtracking function.
     * 
     * Decision Tree Visualization for "aab":
     * 
     *                    "aab"
     *                 /     |     \
     *            "a"+"ab" "aa"+"b" "aab" (invalid)
     *            /    \
     *       "a"+"a"+"b" "ab"(invalid)
     * 
     * @param start Starting index for current partition
     * @param s Original string
     * @param curr Current partition being built
     * @param res Result list
     * @param isPal Precomputed palindrome table
     */
    private static void backtrack(int start, String s, List<String> curr, 
                                 List<List<String>> res, boolean[][] isPal) {
        // Base case: reached end of string
        if (start == s.length()) {
            res.add(new ArrayList<>(curr));
            return;
        }
        
        // Try all possible ending positions
        for (int end = start; end < s.length(); end++) {
            // Check if substring s[start:end] is palindrome
            if (isPal[start][end]) {
                // Add palindrome substring to current partition
                curr.add(s.substring(start, end + 1));
                // Recurse to partition the remaining string
                backtrack(end + 1, s, curr, res, isPal);
                // Backtrack
                curr.remove(curr.size() - 1);
            }
        }
    }

    /**
     * Precompute palindrome table using dynamic programming.
     * isPal[i][j] = true if s[i:j] is a palindrome.
     * 
     * @param s Input string
     * @return 2D boolean array of palindrome information
     * 
     * DP Recurrence:
     * isPal[i][j] = true if:
     *   1. s[i] == s[j]
     *   2. AND (j-i <= 2 OR isPal[i+1][j-1] == true)
     */
    private static boolean[][] precomputePalindromes(String s) {
        int n = s.length();
        boolean[][] isPal = new boolean[n][n];
        
        // All single characters are palindromes
        for (int i = 0; i < n; i++) {
            isPal[i][i] = true;
        }
        
        // Check for 2-character palindromes
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                isPal[i][i + 1] = true;
            }
        }
        
        // Check for longer palindromes
        for (int length = 3; length <= n; length++) {
            for (int i = 0; i <= n - length; i++) {
                int j = i + length - 1;
                if (s.charAt(i) == s.charAt(j) && isPal[i + 1][j - 1]) {
                    isPal[i][j] = true;
                }
            }
        }
        
        return isPal;
    }

    /**
     * Alternative: Find minimum cuts needed for palindrome partitioning.
     * Returns minimum number of cuts required.
     * 
     * Example: "aab" → 1 cut (aa|b)
     * 
     * @param s Input string
     * @return Minimum number of cuts
     */
    public static int minCut(String s) {
        int n = s.length();
        if (n <= 1) return 0;
        
        boolean[][] isPal = precomputePalindromes(s);
        int[] dp = new int[n]; // dp[i] = min cuts for s[0:i]
        
        // Initialize with worst case: cut after every character
        for (int i = 0; i < n; i++) {
            dp[i] = i; // i cuts for first i+1 characters
        }
        
        for (int i = 0; i < n; i++) {
            if (isPal[0][i]) {
                dp[i] = 0; // Whole substring is palindrome
                continue;
            }
            
            // Try all possible partition points
            for (int j = 0; j < i; j++) {
                if (isPal[j + 1][i]) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }
        
        return dp[n - 1];
    }

    /**
     * Alternative: Count all possible palindrome partitions.
     * 
     * @param s Input string
     * @return Number of valid palindrome partitions
     */
    public static int countPartitions(String s) {
        boolean[][] isPal = precomputePalindromes(s);
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = 1; // Empty string has 1 partition
        
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (isPal[j][i - 1]) {
                    dp[i] += dp[j];
                }
            }
        }
        
        return dp[n];
    }

    /**
     * Memory-optimized backtracking without DP table.
     * Uses palindrome checking on the fly.
     */
    public static List<List<String>> partitionMemoryOptimized(String s) {
        List<List<String>> res = new ArrayList<>();
        backtrackMemory(0, s, new ArrayList<>(), res);
        return res;
    }
    
    private static void backtrackMemory(int start, String s, 
                                       List<String> curr, List<List<String>> res) {
        if (start == s.length()) {
            res.add(new ArrayList<>(curr));
            return;
        }
        
        for (int end = start; end < s.length(); end++) {
            if (isPalindromeOptimized(s, start, end)) {
                curr.add(s.substring(start, end + 1));
                backtrackMemory(end + 1, s, curr, res);
                curr.remove(curr.size() - 1);
            }
        }
    }
    
    /**
     * Optimized palindrome check with early termination.
     */
    private static boolean isPalindromeOptimized(String s, int l, int r) {
        while (l < r) {
            if (s.charAt(l++) != s.charAt(r--)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Find longest palindrome substring in each partition.
     * Returns partitions sorted by their longest palindrome length.
     */
    public static List<List<String>> partitionByLongestPalindrome(String s) {
        List<List<String>> partitions = partition(s);
        
        // Sort partitions by maximum palindrome length in descending order
        partitions.sort((a, b) -> {
            int maxA = a.stream().mapToInt(String::length).max().orElse(0);
            int maxB = b.stream().mapToInt(String::length).max().orElse(0);
            return Integer.compare(maxB, maxA);
        });
        
        return partitions;
    }

    /**
     * Comprehensive test driver.
     */
    public static void main(String[] args) {
        System.out.println("=== PALINDROME PARTITIONING DEMO ===\n");
        
        testBasicCases();
        testPerformance();
        testEdgeCases();
        testMinimumCut();
        testCountPartitions();
        testVisualization();
    }
    
    private static void testBasicCases() {
        System.out.println("1. BASIC TEST CASES");
        
        String[] testCases = {
            "aab",
            "a",
            "aa",
            "ab",
            "aba",
            "racecar",
            "noon",
            "level"
        };
        
        for (String s : testCases) {
            System.out.println("\nInput: \"" + s + "\"");
            List<List<String>> partitions = partition(s);
            System.out.println("Number of partitions: " + partitions.size());
            
            if (partitions.size() <= 10) {
                System.out.println("All partitions:");
                for (int i = 0; i < partitions.size(); i++) {
                    System.out.println("  " + (i + 1) + ": " + partitions.get(i));
                }
            } else {
                System.out.println("First 5 partitions:");
                for (int i = 0; i < 5 && i < partitions.size(); i++) {
                    System.out.println("  " + (i + 1) + ": " + partitions.get(i));
                }
                System.out.println("  ... and " + (partitions.size() - 5) + " more");
            }
        }
    }
    
    private static void testPerformance() {
        System.out.println("\n\n2. PERFORMANCE COMPARISON");
        
        String testString = "ababababab"; // 10 characters
        
        System.out.println("Testing string: \"" + testString + "\" (length = " + testString.length() + ")");
        
        // With DP preprocessing
        long start = System.nanoTime();
        List<List<String>> result1 = partition(testString);
        long time1 = System.nanoTime() - start;
        
        // Without DP preprocessing
        start = System.nanoTime();
        List<List<String>> result2 = partitionMemoryOptimized(testString);
        long time2 = System.nanoTime() - start;
        
        System.out.println("Number of partitions: " + result1.size());
        System.out.printf("With DP table:    %8.3f ms\n", time1 / 1_000_000.0);
        System.out.printf("Without DP table: %8.3f ms\n", time2 / 1_000_000.0);
        System.out.println("Results match: " + result1.equals(result2));
    }
    
    private static void testEdgeCases() {
        System.out.println("\n\n3. EDGE CASES");
        
        // Empty string
        System.out.println("\na) Empty string:");
        List<List<String>> empty = partition("");
        System.out.println("Partitions: " + empty);
        System.out.println("Expected: [[]] (one partition with empty list)");
        
        // Single character
        System.out.println("\nb) Single character \"x\":");
        List<List<String>> single = partition("x");
        System.out.println("Partitions: " + single);
        System.out.println("Expected: [[\"x\"]]");
        
        // All same characters
        System.out.println("\nc) String \"aaaa\":");
        List<List<String>> allSame = partition("aaaa");
        System.out.println("Number of partitions: " + allSame.size());
        System.out.println("Expected: 2^(n-1) = 8 partitions");
        
        // No palindrome possible (except single characters)
        System.out.println("\nd) String \"abcde\":");
        List<List<String>> noPalindrome = partition("abcde");
        System.out.println("Number of partitions: " + noPalindrome.size());
        System.out.println("Expected: 1 partition (all single characters)");
    }
    
    private static void testMinimumCut() {
        System.out.println("\n\n4. MINIMUM CUT PROBLEM");
        
        String[] testCases = {
            "aab",      // min cuts: 1 (aa|b)
            "aba",      // min cuts: 0 (whole string is palindrome)
            "abcde",    // min cuts: 4 (a|b|c|d|e)
            "racecar",  // min cuts: 0
            "abccba",   // min cuts: 0
            "abcdcba",  // min cuts: 0
            "abacdfg"   // min cuts: ? 
        };
        
        for (String s : testCases) {
            int minCuts = minCut(s);
            System.out.println("String: \"" + s + "\" → Minimum cuts: " + minCuts);
        }
    }
    
    private static void testCountPartitions() {
        System.out.println("\n\n5. COUNTING PARTITIONS");
        
        String[] testCases = {"a", "aa", "ab", "aaa", "aba", "ababa"};
        
        for (String s : testCases) {
            int count = countPartitions(s);
            System.out.println("String: \"" + s + "\" → Number of partitions: " + count);
            
            // Verify by actually generating partitions
            List<List<String>> partitions = partition(s);
            if (count != partitions.size()) {
                System.out.println("  WARNING: Count mismatch! Generated: " + partitions.size());
            }
        }
    }
    
    private static void testVisualization() {
        System.out.println("\n\n6. VISUALIZING DP TABLE FOR \"aab\"");
        
        String s = "aab";
        boolean[][] isPal = precomputePalindromes(s);
        int n = s.length();
        
        System.out.println("String: " + s);
        System.out.println("Palindrome DP Table (isPal[i][j]):");
        
        // Print header
        System.out.print("    ");
        for (int j = 0; j < n; j++) {
            System.out.printf(" %c ", s.charAt(j));
        }
        System.out.println();
        
        // Print table
        for (int i = 0; i < n; i++) {
            System.out.printf("%c | ", s.charAt(i));
            for (int j = 0; j < n; j++) {
                if (j < i) {
                    System.out.print("   ");
                } else {
                    System.out.printf(" %c ", isPal[i][j] ? 'T' : 'F');
                }
            }
            System.out.println();
        }
        
        System.out.println("\nValid substrings:");
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (isPal[i][j]) {
                    System.out.println("  s[" + i + ":" + j + "] = \"" + s.substring(i, j+1) + "\"");
                }
            }
        }
    }
    
    /**
     * Utility: Check if a list of strings forms a valid palindrome partition.
     */
    public static boolean isValidPartition(List<String> partition, String original) {
        // Verify concatenation equals original string
        StringBuilder sb = new StringBuilder();
        for (String str : partition) {
            sb.append(str);
        }
        if (!sb.toString().equals(original)) {
            return false;
        }
        
        // Verify each substring is palindrome
        for (String str : partition) {
            if (!isPalindromeOptimized(str, 0, str.length() - 1)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Find all partitions with minimum number of substrings.
     */
    public static List<List<String>> partitionsWithMinFragments(String s) {
        List<List<String>> allPartitions = partition(s);
        if (allPartitions.isEmpty()) return allPartitions;
        
        // Find minimum number of fragments
        int minFragments = Integer.MAX_VALUE;
        for (List<String> partition : allPartitions) {
            minFragments = Math.min(minFragments, partition.size());
        }
        
        // Collect partitions with minimum fragments
        List<List<String>> result = new ArrayList<>();
        for (List<String> partition : allPartitions) {
            if (partition.size() == minFragments) {
                result.add(partition);
            }
        }
        
        return result;
    }
    
    /**
     * Find all partitions with maximum palindrome length.
     */
    public static List<List<String>> partitionsWithLongestPalindrome(String s) {
        List<List<String>> allPartitions = partition(s);
        if (allPartitions.isEmpty()) return allPartitions;
        
        // Find maximum palindrome length
        int maxLength = 0;
        for (List<String> partition : allPartitions) {
            for (String fragment : partition) {
                maxLength = Math.max(maxLength, fragment.length());
            }
        }
        
        // Collect partitions containing a palindrome of maxLength
        List<List<String>> result = new ArrayList<>();
        for (List<String> partition : allPartitions) {
            for (String fragment : partition) {
                if (fragment.length() == maxLength) {
                    result.add(partition);
                    break;
                }
            }
        }
        
        return result;
    }
}