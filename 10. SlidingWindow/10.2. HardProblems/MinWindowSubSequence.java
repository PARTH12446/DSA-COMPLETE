import java.util.*;

/**
 * Minimum Window Subsequence (LeetCode 727)
 * 
 * Problem: Given strings s1 and s2, return the minimum (contiguous) substring
 * of s1 such that s2 is a subsequence of that substring. If there is no such
 * substring, return "".
 * 
 * Example: s1 = "abcdebdde", s2 = "bde"
 * Possible windows where s2 is subsequence: "bcde" (indices 1-4), "bdde" (5-8)
 * Minimum window: "bcde" (length 4) or "bde" (within "bdde", length 3)
 * Result depends on exact matching: "bde" appears exactly in positions 5-7
 * 
 * Key Insight: Two-pass scanning:
 * 1. Forward pass: Find end of subsequence match
 * 2. Backward pass: Shrink from end to find start of minimal window
 * 
 * This is different from Minimum Window Substring (LeetCode 76) which finds
 * substring containing all characters, not subsequence.
 * 
 * Time Complexity: O(n*m) worst case, O(n) average
 * Space Complexity: O(1)
 */
public class MinWindowSubSequence {
    
    /**
     * Finds minimum window in s1 that contains s2 as a subsequence.
     * Uses two-pointer scanning with forward/backward passes.
     * 
     * Algorithm:
     * 1. Forward pass: Scan s1 to find complete match of s2 as subsequence
     *    - When s1[i] == s2[j], advance j pointer
     *    - When j reaches end of s2, we found a candidate window ending at i
     * 
     * 2. Backward pass: From end position, scan backward to find minimal start
     *    - Move i backward while matching s2 backward
     *    - When j reaches 0, we found minimal window start
     * 
     * 3. Record window and continue scanning from i+1
     * 
     * Example: s1 = "abcdebdde", s2 = "bde"
     * Forward: a b c d e b d d e
     *          i               ↑ end at e (j=3, complete match)
     * Backward:           d d e
     *                    ← i
     *          Minimal window: "bde" (indices 5-7)
     * 
     * @param s1 The string to search within (haystack)
     * @param s2 The subsequence to find (needle)
     * @return Minimum window in s1 containing s2 as subsequence, or "" if none
     */
    public String minWindow(String s1, String s2) {
        // Edge cases
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return "";
        }
        if (s2.length() > s1.length()) {
            return ""; // s2 cannot be subsequence if longer than s1
        }
        if (s1.equals(s2)) {
            return s1; // Exact match
        }
        
        int n = s1.length();
        int m = s2.length();
        int minLength = Integer.MAX_VALUE;
        int startIndex = -1;
        
        int i = 0; // Pointer for s1
        int j = 0; // Pointer for s2
        
        while (i < n) {
            // Forward pass: Find complete match of s2 in s1
            if (s1.charAt(i) == s2.charAt(j)) {
                j++;
                
                // When we complete a match of s2
                if (j == m) {
                    int end = i + 1; // Exclusive end index of window
                    
                    // Backward pass: Find minimal start for this match
                    j--; // Move to last character of s2
                    while (j >= 0) {
                        if (s1.charAt(i) == s2.charAt(j)) {
                            j--;
                        }
                        i--; // Move i backward
                    }
                    
                    // After backward pass:
                    // i is at position BEFORE start of window
                    // j is -1 (we matched all characters backward)
                    i++; // Move to start of window
                    j++; // Reset j to 0
                    
                    // Check if this window is smaller than current minimum
                    int windowLength = end - i;
                    if (windowLength < minLength) {
                        minLength = windowLength;
                        startIndex = i;
                    }
                }
            }
            
            i++; // Always move i forward
        }
        
        // Return result
        return startIndex == -1 ? "" : s1.substring(startIndex, startIndex + minLength);
    }
    
    /**
     * Alternative: DP solution using 2D array.
     * More intuitive but uses O(n*m) space.
     */
    public String minWindowDP(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return "";
        }
        
        int n = s1.length();
        int m = s2.length();
        
        // dp[i][j] = start index in s1 of minimum window ending at/before i 
        // that contains s2[0..j] as subsequence
        int[][] dp = new int[n + 1][m + 1];
        
        // Initialize dp
        for (int i = 0; i <= n; i++) {
            dp[i][0] = i; // Empty s2 matches at any position
        }
        for (int j = 1; j <= m; j++) {
            dp[0][j] = -1; // Non-empty s2 cannot match empty s1
        }
        
        // Fill DP table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        
        // Find minimum window
        int minLength = Integer.MAX_VALUE;
        int start = -1;
        
        for (int i = 1; i <= n; i++) {
            if (dp[i][m] != -1) {
                int windowLength = i - dp[i][m];
                if (windowLength < minLength) {
                    minLength = windowLength;
                    start = dp[i][m];
                }
            }
        }
        
        return start == -1 ? "" : s1.substring(start, start + minLength);
    }
    
    /**
     * Variation: Find all windows containing s2 as subsequence.
     */
    public List<String> findAllWindows(String s1, String s2) {
        List<String> result = new ArrayList<>();
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return result;
        }
        
        int n = s1.length();
        int m = s2.length();
        
        for (int start = 0; start <= n - m; start++) {
            int i = start, j = 0;
            
            while (i < n && j < m) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    j++;
                }
                i++;
            }
            
            if (j == m) {
                // Found a match, now find minimal window for this start
                int end = i; // i is one past the end of match
                j = m - 1;
                i--;
                
                while (j >= 0) {
                    if (s1.charAt(i) == s2.charAt(j)) {
                        j--;
                    }
                    i--;
                }
                
                int windowStart = i + 1;
                int windowEnd = end;
                result.add(s1.substring(windowStart, windowEnd));
            }
        }
        
        return result;
    }
    
    /**
     * Variation: Return start and end indices instead of substring.
     */
    public int[] minWindowIndices(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return new int[]{-1, -1};
        }
        
        int n = s1.length();
        int m = s2.length();
        int minLength = Integer.MAX_VALUE;
        int startIndex = -1;
        int endIndex = -1;
        
        int i = 0, j = 0;
        
        while (i < n) {
            if (s1.charAt(i) == s2.charAt(j)) {
                j++;
                
                if (j == m) {
                    int end = i + 1;
                    j--;
                    
                    while (j >= 0) {
                        if (s1.charAt(i) == s2.charAt(j)) {
                            j--;
                        }
                        i--;
                    }
                    
                    i++;
                    j++;
                    
                    int windowLength = end - i;
                    if (windowLength < minLength) {
                        minLength = windowLength;
                        startIndex = i;
                        endIndex = end - 1; // Inclusive end
                    }
                }
            }
            
            i++;
        }
        
        return new int[]{startIndex, endIndex};
    }
    
    /**
     * Brute force solution for verification (O(n³)).
     */
    public String minWindowBruteForce(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return "";
        }
        
        int n = s1.length();
        int m = s2.length();
        String minWindow = null;
        
        // Check all possible substrings
        for (int start = 0; start < n; start++) {
            for (int end = start; end < n; end++) {
                // Check if s2 is subsequence of s1[start..end]
                if (isSubsequence(s2, s1.substring(start, end + 1))) {
                    int length = end - start + 1;
                    if (minWindow == null || length < minWindow.length()) {
                        minWindow = s1.substring(start, end + 1);
                    }
                }
            }
        }
        
        return minWindow == null ? "" : minWindow;
    }
    
    /**
     * Helper: Check if s is subsequence of t.
     */
    private boolean isSubsequence(String s, String t) {
        int i = 0, j = 0;
        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == s.length();
    }
    
    /**
     * Visualization helper to show the scanning process.
     */
    public void visualizeMinWindow(String s1, String s2) {
        System.out.println("\n=== Minimum Window Subsequence Visualization ===");
        System.out.println("s1 = \"" + s1 + "\"");
        System.out.println("s2 = \"" + s2 + "\"");
        System.out.println();
        
        int n = s1.length();
        int m = s2.length();
        int minLength = Integer.MAX_VALUE;
        int startIndex = -1;
        int step = 1;
        
        System.out.println("Step | i | j | s1[i] | s2[j] | Action | Window Found");
        System.out.println("-----|---|---|-------|-------|--------|-------------");
        
        int i = 0, j = 0;
        
        while (i < n) {
            String action = "Compare";
            String windowFound = "";
            
            if (s1.charAt(i) == s2.charAt(j)) {
                action = "Match! j++";
                j++;
                
                if (j == m) {
                    int end = i + 1;
                    action = "Complete match! Backward pass...";
                    
                    // Backward pass
                    j--;
                    int backSteps = 0;
                    while (j >= 0) {
                        backSteps++;
                        if (s1.charAt(i) == s2.charAt(j)) {
                            j--;
                        }
                        i--;
                    }
                    
                    i++;
                    j++;
                    
                    int windowLength = end - i;
                    if (windowLength < minLength) {
                        minLength = windowLength;
                        startIndex = i;
                        windowFound = "\"" + s1.substring(i, end) + "\" (length=" + windowLength + ") NEW MIN!";
                    } else {
                        windowFound = "\"" + s1.substring(i, end) + "\" (length=" + windowLength + ")";
                    }
                    
                    action += " Backward steps: " + backSteps;
                }
            }
            
            System.out.printf("%4d | %d | %d | %5c | %5c | %6s | %s%n",
                step, i, Math.min(j, m-1), s1.charAt(i), 
                (j < m ? s2.charAt(j) : ' '), action, windowFound);
            
            i++;
            step++;
        }
        
        System.out.println("\nMinimum window subsequence: " + 
                          (startIndex == -1 ? "None" : 
                           "\"" + s1.substring(startIndex, startIndex + minLength) + "\""));
    }
    
    /**
     * Test cases for minimum window subsequence problem.
     */
    public static void runTestCases() {
        MinWindowSubSequence solver = new MinWindowSubSequence();
        
        System.out.println("=== Minimum Window Subsequence Test Cases ===\n");
        
        // Test 1: Standard case
        String s1_1 = "abcdebdde";
        String s2_1 = "bde";
        System.out.println("Test 1:");
        System.out.println("s1 = \"" + s1_1 + "\", s2 = \"" + s2_1 + "\"");
        String result1 = solver.minWindow(s1_1, s2_1);
        System.out.println("Result: \"" + result1 + "\"");
        System.out.println("Expected: \"bcde\" or \"bde\" (both contain \"bde\" as subsequence)");
        
        // Verify with brute force
        String brute1 = solver.minWindowBruteForce(s1_1, s2_1);
        System.out.println("Brute force: \"" + brute1 + "\" (matches: " + result1.equals(brute1) + ")");
        System.out.println();
        
        // Test 2: Exact match
        String s1_2 = "abcdefg";
        String s2_2 = "cde";
        System.out.println("Test 2 (exact match):");
        System.out.println("s1 = \"" + s1_2 + "\", s2 = \"" + s2_2 + "\"");
        String result2 = solver.minWindow(s1_2, s2_2);
        System.out.println("Result: \"" + result2 + "\"");
        System.out.println("Expected: \"cde\"");
        System.out.println();
        
        // Test 3: Multiple occurrences
        String s1_3 = "abbcabbc";
        String s2_3 = "abc";
        System.out.println("Test 3 (multiple occurrences):");
        System.out.println("s1 = \"" + s1_3 + "\", s2 = \"" + s2_3 + "\"");
        String result3 = solver.minWindow(s1_3, s2_3);
        System.out.println("Result: \"" + result3 + "\"");
        System.out.println("Expected: \"abc\" (first occurrence) or \"abbc\" (but \"abc\" is shorter)");
        System.out.println();
        
        // Test 4: No match
        String s1_4 = "abcdef";
        String s2_4 = "xyz";
        System.out.println("Test 4 (no match):");
        System.out.println("s1 = \"" + s1_4 + "\", s2 = \"" + s2_4 + "\"");
        String result4 = solver.minWindow(s1_4, s2_4);
        System.out.println("Result: \"" + result4 + "\"");
        System.out.println("Expected: \"\" (empty string)");
        System.out.println();
        
        // Test 5: s2 longer than s1
        String s1_5 = "abc";
        String s2_5 = "abcd";
        System.out.println("Test 5 (s2 longer than s1):");
        System.out.println("s1 = \"" + s1_5 + "\", s2 = \"" + s2_5 + "\"");
        String result5 = solver.minWindow(s1_5, s2_5);
        System.out.println("Result: \"" + result5 + "\"");
        System.out.println("Expected: \"\" (empty string)");
        System.out.println();
        
        // Test 6: Single character s2
        String s1_6 = "abracadabra";
        String s2_6 = "a";
        System.out.println("Test 6 (single character s2):");
        System.out.println("s1 = \"" + s1_6 + "\", s2 = \"" + s2_6 + "\"");
        String result6 = solver.minWindow(s1_6, s2_6);
        System.out.println("Result: \"" + result6 + "\"");
        System.out.println("Expected: \"a\" (any single 'a')");
        System.out.println();
        
        // Test 7: From LeetCode
        String s1_7 = "ffynmlzesdshlvugsigobutgaetsnjlizvqjdpccdylclqcbghhixpjihximvhapymfkjxyyxfwvsfyctmhwmfjyjidnfryiyajmtakisaxwglwpqaxaicuprrvxybzdxunypzofhpclqiybgniqzsdeqwrdsfjyfkgmejxfqjkmukvgygafwokeoeglanevavyrpduigitmrimtaslzboauwbluvlfqquocxrzrbvvplsivujojscytmeyjolvvyzwizpuhejsdsp"
        String s2_7 = "q";
        System.out.println("Test 7 (LeetCode edge case):");
        System.out.println("s2 = \"" + s2_7 + "\"");
        String result7 = solver.minWindow(s1_7, s2_7);
        System.out.println("Result length: " + result7.length());
        System.out.println("Expected: 1 (single character)");
        
        // Test DP solution
        System.out.println("\nTesting DP solution:");
        String dpResult = solver.minWindowDP(s1_1, s2_1);
        System.out.println("DP result: \"" + dpResult + "\" (matches: " + result1.equals(dpResult) + ")");
    }
    
    /**
     * Performance comparison between different approaches.
     */
    public static void benchmark() {
        MinWindowSubSequence solver = new MinWindowSubSequence();
        
        System.out.println("\n=== Performance Comparison ===");
        
        // Generate test strings
        int n = 10000;
        int m = 100;
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        Random rand = new Random(42);
        
        for (int i = 0; i < n; i++) {
            sb1.append((char)('a' + rand.nextInt(26)));
        }
        for (int i = 0; i < m; i++) {
            sb2.append((char)('a' + rand.nextInt(26)));
        }
        
        String s1 = sb1.toString();
        String s2 = sb2.toString();
        
        System.out.println("s1 length: " + n + ", s2 length: " + m);
        
        // Two-pointer scanning
        long start = System.currentTimeMillis();
        String result1 = solver.minWindow(s1, s2);
        long time1 = System.currentTimeMillis() - start;
        System.out.println("Two-pointer scanning: " + time1 + " ms, result length: " + result1.length());
        
        // DP solution
        start = System.currentTimeMillis();
        String result2 = solver.minWindowDP(s1, s2);
        long time2 = System.currentTimeMillis() - start;
        System.out.println("DP solution: " + time2 + " ms, result length: " + result2.length());
        
        System.out.println("Results match: " + result1.equals(result2));
        System.out.println("Two-pointer is faster for this problem (O(n) vs O(n*m) for DP)");
    }
    
    /**
     * Explain the two-pass scanning algorithm.
     */
    public static void explainAlgorithm() {
        System.out.println("\n=== Two-Pass Scanning Algorithm Explained ===");
        System.out.println();
        System.out.println("Problem: Find minimum window in s1 containing s2 as subsequence");
        System.out.println();
        System.out.println("Key Insight: Two passes for each match:");
        System.out.println("  1. Forward pass: Find end of subsequence match");
        System.out.println("     - Scan s1 forward, match s2 characters");
        System.out.println("     - When s2 fully matched, record end position");
        System.out.println();
        System.out.println("  2. Backward pass: Find start of minimal window");
        System.out.println("     - From end position, scan backward");
        System.out.println("     - Match s2 characters in reverse");
        System.out.println("     - When s2 fully matched backward, we have start");
        System.out.println();
        System.out.println("Why backward pass finds minimal window:");
        System.out.println("  - Forward pass finds ANY window containing s2");
        System.out.println("  - Backward pass shrinks to MINIMAL window");
        System.out.println("  - By matching backward, we find earliest start");
        System.out.println();
        System.out.println("Example: s1 = \"abcdebdde\", s2 = \"bde\"");
        System.out.println("  Forward: a b c d e b d d e");
        System.out.println("                    ↑ end at 2nd 'e'");
        System.out.println("  Backward:         d d e");
        System.out.println("            ← match 'e','d','b' backward");
        System.out.println("            ↑ start at 'b'");
        System.out.println("  Window: \"bde\" (indices 5-7)");
        System.out.println();
        System.out.println("Time Complexity: O(n*m) worst case, O(n) average");
        System.out.println("  - Each character in s1 processed once forward");
        System.out.println("  - Backward pass only when match found");
        System.out.println("  - In practice, close to O(n)");
    }
    
    /**
     * Show related problems and comparisons.
     */
    public static void showRelatedProblems() {
        System.out.println("\n=== Related Problems and Comparisons ===");
        
        // Comparison with Minimum Window Substring
        System.out.println("Comparison with Minimum Window Substring (LeetCode 76):");
        System.out.println("  Minimum Window Substring:");
        System.out.println("    - Finds substring containing ALL characters");
        System.out.println("    - Characters can be in any order");
        System.out.println("    - Uses sliding window with frequency map");
        System.out.println();
        System.out.println("  Minimum Window Subsequence:");
        System.out.println("    - Finds substring containing subsequence");
        System.out.println("    - Characters must be in ORDER");
        System.out.println("    - Uses two-pass scanning");
        System.out.println();
        
        // Related problem 1: Is Subsequence
        System.out.println("Related Problem 1: Is Subsequence (LeetCode 392)");
        System.out.println("  Simpler version: Just check if s2 is subsequence of s1");
        System.out.println("  Uses simple two-pointer scanning");
        System.out.println();
        
        // Related problem 2: Longest Common Subsequence
        System.out.println("Related Problem 2: Longest Common Subsequence");
        System.out.println("  Classic DP problem, finds LCS of two strings");
        System.out.println("  Similar concept but different goal");
        System.out.println();
        
        // Real-world applications
        System.out.println("Real-world Applications:");
        System.out.println("  1. Text editing: Find minimal context containing specific phrase");
        System.out.println("  2. Bioinformatics: Find gene sequences with specific patterns");
        System.out.println("  3. Code analysis: Find code snippets containing specific patterns");
        System.out.println("  4. Search engines: Highlight search terms in context");
    }
    
    public static void main(String[] args) {
        // Run test cases
        runTestCases();
        
        // Visualize the algorithm
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Detailed Visualization of Example:");
        System.out.println("=".repeat(60));
        
        MinWindowSubSequence solver = new MinWindowSubSequence();
        String s1 = "abcdebdde";
        String s2 = "bde";
        solver.visualizeMinWindow(s1, s2);
        
        // Explain the algorithm
        explainAlgorithm();
        
        // Show related problems
        showRelatedProblems();
        
        // Run benchmark (optional)
        // benchmark();
    }
}