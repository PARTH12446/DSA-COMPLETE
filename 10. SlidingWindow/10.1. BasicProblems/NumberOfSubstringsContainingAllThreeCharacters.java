import java.util.*;

/**
 * Number of Substrings Containing All Three Characters (LeetCode 1358)
 * 
 * Problem: Given a string s consisting only of characters 'a', 'b', and 'c',
 * return the number of substrings containing at least one occurrence of all
 * three characters.
 * 
 * Example: s = "abcabc"
 * Substrings containing all three: "abc", "abca", "abcab", "abcabc",
 *                                  "bca", "bcab", "bcabc", "cab", "cabc", "abc" (second)
 * Result: 10
 * 
 * Key Insight: For each position i, find the smallest j such that s[j..i]
 * contains all three characters. Then all substrings ending at i that start
 * at positions 0..j also contain all three characters.
 * 
 * Alternative: Sliding window counting substrings ending at each position.
 * 
 * Time Complexity: O(n) - Single pass through string
 * Space Complexity: O(1) - Fixed size arrays
 */
public class NumberOfSubstringsContainingAllThreeCharacters {
    
    /**
     * Counts number of substrings containing at least one 'a', 'b', and 'c'.
     * Uses last occurrence tracking for O(n) time.
     * 
     * Algorithm:
     * 1. Track last occurrence positions of 'a', 'b', 'c' (last[3])
     * 2. For each position i:
     *    a. Update last occurrence of current character
     *    b. Find minimum last occurrence among all three characters
     *    c. If all three have been seen (minLast != -1):
     *       - All substrings ending at i that start at 0..minLast
     *         contain all three characters
     *       - Count = minLast + 1 (indices 0 through minLast)
     * 
     * Why it works:
     * - For substring ending at i to contain all three characters,
     *   it must start at or before the earliest of the three last occurrences
     * - Any start position ≤ minLast ensures all three characters are included
     * - Number of such substrings = minLast + 1
     * 
     * @param s String containing only 'a', 'b', 'c'
     * @return Number of substrings containing at least one of each character
     */
    public int numberOfSubstrings(String s) {
        // Edge cases
        if (s == null || s.length() < 3) {
            return 0;
        }
        
        int n = s.length();
        // last[0] = last index of 'a', last[1] = 'b', last[2] = 'c'
        int[] last = {-1, -1, -1}; // Initialize to -1 (not seen yet)
        int count = 0;
        
        for (int i = 0; i < n; i++) {
            char currentChar = s.charAt(i);
            
            // Update last occurrence of current character
            last[currentChar - 'a'] = i;
            
            // Find earliest last occurrence among all three characters
            int minLast = Math.min(last[0], Math.min(last[1], last[2]));
            
            // If all three characters have been seen at least once
            if (minLast != -1) {
                // All substrings ending at i that start at 0..minLast are valid
                // Number of such substrings = minLast + 1
                count += minLast + 1;
            }
        }
        
        return count;
    }
    
    /**
     * Alternative: Sliding window approach.
     * Count substrings ending at each position using window [left, right].
     */
    public int numberOfSubstringsSlidingWindow(String s) {
        if (s == null || s.length() < 3) return 0;
        
        int n = s.length();
        int[] freq = new int[3]; // Frequency of 'a','b','c' in current window
        int left = 0, count = 0;
        
        for (int right = 0; right < n; right++) {
            // Add current character to window
            freq[s.charAt(right) - 'a']++;
            
            // Shrink window while it contains all three characters
            while (freq[0] > 0 && freq[1] > 0 && freq[2] > 0) {
                // Current window [left, right] contains all three
                // All substrings starting at left..right and ending at right are valid
                count += n - right; // All extensions to end of string are valid
                
                // Remove leftmost character and shrink
                freq[s.charAt(left) - 'a']--;
                left++;
            }
        }
        
        return count;
    }
    
    /**
     * Alternative: Two-pointer approach with last occurrence tracking.
     * More similar to original but with explicit pointers.
     */
    public int numberOfSubstringsTwoPointer(String s) {
        if (s == null || s.length() < 3) return 0;
        
        int n = s.length();
        int[] last = {-1, -1, -1};
        int count = 0;
        
        for (int i = 0; i < n; i++) {
            last[s.charAt(i) - 'a'] = i;
            
            // Check if we've seen all three characters
            if (last[0] != -1 && last[1] != -1 && last[2] != -1) {
                // The earliest position where we can start the substring
                int minLast = Math.min(last[0], Math.min(last[1], last[2]));
                count += minLast + 1;
            }
        }
        
        return count;
    }
    
    /**
     * Variation: Return the actual substrings (for small strings).
     */
    public List<String> getAllValidSubstrings(String s) {
        List<String> result = new ArrayList<>();
        if (s == null || s.length() < 3) return result;
        
        int n = s.length();
        for (int start = 0; start < n; start++) {
            int[] seen = new int[3]; // Track if 'a','b','c' seen
            for (int end = start; end < n; end++) {
                seen[s.charAt(end) - 'a'] = 1;
                
                // Check if all three characters seen
                if (seen[0] + seen[1] + seen[2] == 3) {
                    result.add(s.substring(start, end + 1));
                }
            }
        }
        
        return result;
    }
    
    /**
     * Brute force solution for verification (O(n³)).
     */
    public int numberOfSubstringsBruteForce(String s) {
        if (s == null || s.length() < 3) return 0;
        
        int n = s.length();
        int count = 0;
        
        for (int start = 0; start < n; start++) {
            for (int end = start; end < n; end++) {
                if (containsAllThree(s, start, end)) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Helper for brute force: Check if substring contains all three characters.
     */
    private boolean containsAllThree(String s, int start, int end) {
        boolean hasA = false, hasB = false, hasC = false;
        for (int i = start; i <= end; i++) {
            char c = s.charAt(i);
            if (c == 'a') hasA = true;
            else if (c == 'b') hasB = true;
            else if (c == 'c') hasC = true;
            
            if (hasA && hasB && hasC) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Generalized: Count substrings containing at least K different characters.
     */
    public int numberOfSubstringsContainingKChars(String s, int k) {
        if (s == null || s.length() < k) return 0;
        
        int n = s.length();
        int count = 0;
        
        // For each starting position
        for (int start = 0; start < n; start++) {
            int[] freq = new int[26];
            int distinct = 0;
            
            for (int end = start; end < n; end++) {
                int idx = s.charAt(end) - 'a';
                if (freq[idx] == 0) distinct++;
                freq[idx]++;
                
                if (distinct >= k) {
                    // Once we reach k distinct chars, all further extensions are valid
                    count += (n - end);
                    break;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Visualization helper to show the counting process.
     */
    public void visualizeSubstringCounting(String s) {
        System.out.println("\n=== Substrings Containing All Three Characters ===");
        System.out.println("String: " + s);
        System.out.println();
        
        System.out.println("Index | Char | Last[a,b,c] | Min Last | Valid Starts | Count Added | Total");
        System.out.println("------|------|-------------|----------|--------------|-------------|------");
        
        int[] last = {-1, -1, -1};
        int total = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            last[c - 'a'] = i;
            
            int minLast = Math.min(last[0], Math.min(last[1], last[2]));
            int countAdded = 0;
            
            if (minLast != -1) {
                countAdded = minLast + 1;
                total += countAdded;
            }
            
            System.out.printf("%5d | %4c | [%2d,%2d,%2d] | %8d | %12s | %11d | %5d%n",
                i, c, last[0], last[1], last[2], minLast,
                minLast != -1 ? "0.." + minLast : "none",
                countAdded, total);
        }
        
        System.out.println("\nTotal substrings containing at least one 'a', 'b', and 'c': " + total);
        
        // Show example substrings for verification
        if (s.length() <= 10) {
            List<String> substrings = getAllValidSubstrings(s);
            System.out.println("\nAll valid substrings (" + substrings.size() + "):");
            for (int i = 0; i < substrings.size(); i++) {
                System.out.printf("%3d. %s%n", i + 1, substrings.get(i));
            }
        }
    }
    
    /**
     * Test cases for substrings containing all three characters.
     */
    public static void runTestCases() {
        NumberOfSubstringsContainingAllThreeCharacters solver = new NumberOfSubstringsContainingAllThreeCharacters();
        
        System.out.println("=== Substrings Containing All Three Characters Test Cases ===\n");
        
        // Test 1: Standard case
        String test1 = "abcabc";
        System.out.println("Test 1:");
        System.out.println("s = \"" + test1 + "\"");
        int result1 = solver.numberOfSubstrings(test1);
        System.out.println("Result: " + result1);
        System.out.println("Expected: 10");
        
        // Verify with brute force
        int brute1 = solver.numberOfSubstringsBruteForce(test1);
        System.out.println("Brute force: " + brute1 + " (matches: " + (result1 == brute1) + ")");
        System.out.println();
        
        // Test 2: Minimal case
        String test2 = "abc";
        System.out.println("Test 2 (minimal):");
        System.out.println("s = \"" + test2 + "\"");
        int result2 = solver.numberOfSubstrings(test2);
        System.out.println("Result: " + result2);
        System.out.println("Expected: 1 (only the whole string)");
        System.out.println();
        
        // Test 3: Missing one character
        String test3 = "aaabbbb";
        System.out.println("Test 3 (missing 'c'):");
        System.out.println("s = \"" + test3 + "\"");
        int result3 = solver.numberOfSubstrings(test3);
        System.out.println("Result: " + result3);
        System.out.println("Expected: 0 (no 'c' present)");
        System.out.println();
        
        // Test 4: All same character
        String test4 = "aaaaaa";
        System.out.println("Test 4 (all same):");
        System.out.println("s = \"" + test4 + "\"");
        int result4 = solver.numberOfSubstrings(test4);
        System.out.println("Result: " + result4);
        System.out.println("Expected: 0 (only 'a's present)");
        System.out.println();
        
        // Test 5: Complex pattern
        String test5 = "aaabc";
        System.out.println("Test 5:");
        System.out.println("s = \"" + test5 + "\"");
        int result5 = solver.numberOfSubstrings(test5);
        System.out.println("Result: " + result5);
        System.out.println("Expected: 3 (\"aaabc\", \"aabc\", \"abc\")");
        System.out.println();
        
        // Test 6: From LeetCode
        String test6 = "ababccc";
        System.out.println("Test 6:");
        System.out.println("s = \"" + test6 + "\"");
        int result6 = solver.numberOfSubstrings(test6);
        System.out.println("Result: " + result6);
        System.out.println("Expected: 10");
        
        // Show actual substrings for small test
        List<String> substrings6 = solver.getAllValidSubstrings(test6);
        System.out.println("Valid substrings: " + substrings6);
        System.out.println();
        
        // Test 7: Edge cases
        String test7 = "";
        System.out.println("Test 7 (empty string):");
        System.out.println("s = \"\"");
        int result7 = solver.numberOfSubstrings(test7);
        System.out.println("Result: " + result7);
        System.out.println("Expected: 0");
        System.out.println();
        
        String test8 = "ab";
        System.out.println("Test 8 (length 2):");
        System.out.println("s = \"" + test8 + "\"");
        int result8 = solver.numberOfSubstrings(test8);
        System.out.println("Result: " + result8);
        System.out.println("Expected: 0 (missing 'c')");
    }
    
    /**
     * Performance comparison between different approaches.
     */
    public static void benchmark() {
        NumberOfSubstringsContainingAllThreeCharacters solver = new NumberOfSubstringsContainingAllThreeCharacters();
        
        System.out.println("\n=== Performance Comparison ===");
        
        // Generate large test string
        int n = 1000000;
        StringBuilder sb = new StringBuilder();
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            // Randomly generate 'a', 'b', or 'c'
            int r = rand.nextInt(3);
            sb.append((char)('a' + r));
        }
        String s = sb.toString();
        
        System.out.println("String length: " + n);
        
        // Last occurrence tracking
        long start = System.currentTimeMillis();
        int result1 = solver.numberOfSubstrings(s);
        long time1 = System.currentTimeMillis() - start;
        System.out.println("Last occurrence tracking: " + time1 + " ms, result: " + result1);
        
        // Sliding window approach
        start = System.currentTimeMillis();
        int result2 = solver.numberOfSubstringsSlidingWindow(s);
        long time2 = System.currentTimeMillis() - start;
        System.out.println("Sliding window: " + time2 + " ms, result: " + result2);
        
        System.out.println("Results match: " + (result1 == result2));
    }
    
    /**
     * Explain the mathematical reasoning.
     */
    public static void explainAlgorithm() {
        System.out.println("\n=== Algorithm Explanation ===");
        System.out.println();
        System.out.println("Key Insight:");
        System.out.println("  For a substring ending at position i to contain all three characters,");
        System.out.println("  it must start at or before the earliest occurrence (among a,b,c)");
        System.out.println("  that ensures all three are included.");
        System.out.println();
        System.out.println("Mathematical Form:");
        System.out.println("  Let lastA[i], lastB[i], lastC[i] = last occurrence indices up to i");
        System.out.println("  For substring ending at i to contain a,b,c:");
        System.out.println("    start ≤ min(lastA[i], lastB[i], lastC[i])");
        System.out.println("  Number of valid substrings ending at i = minLast + 1");
        System.out.println();
        System.out.println("Example: s = \"abcabc\", i = 5 (last character 'c')");
        System.out.println("  lastA = 3, lastB = 4, lastC = 5");
        System.out.println("  minLast = min(3,4,5) = 3");
        System.out.println("  Valid start positions: 0,1,2,3");
        System.out.println("  Valid substrings: \"abcabc\", \"bcabc\", \"cabc\", \"abc\"");
        System.out.println("  Count = 4 = 3 + 1");
        System.out.println();
        System.out.println("Why +1?");
        System.out.println("  If minLast = k, valid starts are 0,1,...,k");
        System.out.println("  That's (k+1) possibilities = minLast + 1");
    }
    
    /**
     * Show related problems and generalizations.
     */
    public static void showGeneralizations() {
        System.out.println("\n=== Generalizations and Related Problems ===");
        
        NumberOfSubstringsContainingAllThreeCharacters solver = new NumberOfSubstringsContainingAllThreeCharacters();
        
        // Generalization 1: At least K different characters
        System.out.println("Generalization 1: At least K different characters");
        String s1 = "abcabc";
        for (int k = 1; k <= 4; k++) {
            int count = solver.numberOfSubstringsContainingKChars(s1, k);
            System.out.println("  s = \"" + s1 + "\", at least " + k + " different chars: " + count);
        }
        System.out.println();
        
        // Generalization 2: Different character set
        System.out.println("Generalization 2: Different character set (a,b,c,d)");
        System.out.println("  Can extend algorithm to track last occurrences of 4 characters");
        System.out.println("  Same principle applies");
        System.out.println();
        
        // Related problem 1: Minimum window substring
        System.out.println("Related Problem 1: Minimum Window Substring");
        System.out.println("  Find smallest substring containing all characters");
        System.out.println("  Uses sliding window with similar logic");
        System.out.println();
        
        // Related problem 2: Subarrays with exactly K different integers
        System.out.println("Related Problem 2: Subarrays with exactly K different integers");
        System.out.println("  Similar sliding window counting technique");
    }
    
    public static void main(String[] args) {
        // Run test cases
        runTestCases();
        
        // Visualize the algorithm
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Detailed Visualization of Example:");
        System.out.println("=".repeat(60));
        
        NumberOfSubstringsContainingAllThreeCharacters solver = new NumberOfSubstringsContainingAllThreeCharacters();
        String s = "abcabc";
        solver.visualizeSubstringCounting(s);
        
        // Explain the algorithm
        explainAlgorithm();
        
        // Show generalizations
        showGeneralizations();
        
        // Run benchmark (optional)
        // benchmark();
    }
}