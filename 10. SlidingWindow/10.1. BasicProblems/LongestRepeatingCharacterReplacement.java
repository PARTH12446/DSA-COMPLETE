import java.util.*;

/**
 * Longest Repeating Character Replacement (LeetCode 424)
 * 
 * Problem: Given a string s and an integer k, you can choose any character
 * of the string and change it to any other uppercase English character.
 * Find the length of the longest substring containing the same letter
 * you can get after performing at most k operations.
 * 
 * Example: s = "AABABBA", k = 1
 * We can change the second 'B' to 'A': "AAAABBA" → longest 'A' substring length = 4
 * 
 * Key Insight: For a window [left, right], the number of replacements needed is:
 *   replacements = window_size - max_frequency_in_window
 * Where max_frequency_in_window is count of most frequent character in window.
 * 
 * We want: window_size - max_frequency ≤ k
 * 
 * Time Complexity: O(n) - Each character processed at most twice
 * Space Complexity: O(1) - Fixed size frequency array (26 for uppercase letters)
 */
public class LongestRepeatingCharacterReplacement {
    
    /**
     * Finds length of longest substring with same character after at most k replacements.
     * 
     * Algorithm (Sliding Window with Frequency Count):
     * 1. Maintain frequency count of characters in current window
     * 2. Track maxCount = frequency of most common character in window
     * 3. Expand right pointer, update frequency and maxCount
     * 4. While (window_size - maxCount > k): window needs more than k replacements
     *    - Shrink from left, update frequency
     *    - Note: maxCount doesn't need to be updated when shrinking (optimization)
     * 5. Track maximum window size
     * 
     * Why window_size - maxCount works:
     * - window_size = total characters in window
     * - maxCount = count of most frequent character
     * - window_size - maxCount = characters that need to be replaced
     * - We can replace these with the most frequent character
     * 
     * @param s Input string (uppercase English letters)
     * @param k Maximum allowed replacements
     * @return Length of longest valid substring
     */
    public int characterReplacement(String s, int k) {
        // Edge cases
        if (s == null || s.length() == 0) {
            return 0;
        }
        if (k >= s.length()) {
            return s.length(); // Can replace all characters if needed
        }
        
        // Frequency array for 26 uppercase letters
        int[] freq = new int[26];
        int left = 0;           // Left pointer of sliding window
        int maxFreq = 0;        // Frequency of most common character in window
        int maxLength = 0;      // Maximum valid window length found
        
        for (int right = 0; right < s.length(); right++) {
            // Add current character to window
            char currentChar = s.charAt(right);
            int charIndex = currentChar - 'A';
            freq[charIndex]++;
            
            // Update max frequency (most common character in window)
            maxFreq = Math.max(maxFreq, freq[charIndex]);
            
            // Calculate current window size
            int windowSize = right - left + 1;
            
            // If we need more than k replacements, shrink window
            while (windowSize - maxFreq > k) {
                // Remove leftmost character from window
                char leftChar = s.charAt(left);
                freq[leftChar - 'A']--;
                left++;
                
                // Update window size
                windowSize = right - left + 1;
                
                // Note: We don't update maxFreq here (optimization explained below)
            }
            
            // Update maximum length
            maxLength = Math.max(maxLength, windowSize);
        }
        
        return maxLength;
    }
    
    /**
     * Alternative: With maxFreq update during shrinking (more accurate but slower).
     * Updates maxFreq when shrinking window.
     */
    public int characterReplacementWithMaxUpdate(String s, int k) {
        if (s == null || s.length() == 0) return 0;
        
        int[] freq = new int[26];
        int left = 0, maxFreq = 0, maxLength = 0;
        
        for (int right = 0; right < s.length(); right++) {
            freq[s.charAt(right) - 'A']++;
            
            // Find new max frequency (O(26) operation)
            maxFreq = 0;
            for (int count : freq) {
                maxFreq = Math.max(maxFreq, count);
            }
            
            // Shrink if needed
            while ((right - left + 1) - maxFreq > k) {
                freq[s.charAt(left) - 'A']--;
                left++;
                
                // Update maxFreq after shrinking
                maxFreq = 0;
                for (int count : freq) {
                    maxFreq = Math.max(maxFreq, count);
                }
            }
            
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    /**
     * Brute force solution for verification (O(n²)).
     */
    public int characterReplacementBruteForce(String s, int k) {
        if (s == null || s.length() == 0) return 0;
        
        int maxLength = 0;
        
        for (int start = 0; start < s.length(); start++) {
            int[] freq = new int[26];
            int maxFreq = 0;
            
            for (int end = start; end < s.length(); end++) {
                freq[s.charAt(end) - 'A']++;
                maxFreq = Math.max(maxFreq, freq[s.charAt(end) - 'A']);
                
                int windowSize = end - start + 1;
                if (windowSize - maxFreq <= k) {
                    maxLength = Math.max(maxLength, windowSize);
                } else {
                    break; // Further expansion will only need more replacements
                }
            }
        }
        
        return maxLength;
    }
    
    /**
     * Variation: Find the actual substring (not just length).
     */
    public String characterReplacementSubstring(String s, int k) {
        if (s == null || s.length() == 0) return "";
        
        int[] freq = new int[26];
        int left = 0, maxFreq = 0, maxLength = 0;
        int bestLeft = 0, bestRight = 0;
        
        for (int right = 0; right < s.length(); right++) {
            int idx = s.charAt(right) - 'A';
            freq[idx]++;
            maxFreq = Math.max(maxFreq, freq[idx]);
            
            while ((right - left + 1) - maxFreq > k) {
                freq[s.charAt(left) - 'A']--;
                left++;
                // Don't update maxFreq - it's safe not to (explained in comments)
            }
            
            int windowSize = right - left + 1;
            if (windowSize > maxLength) {
                maxLength = windowSize;
                bestLeft = left;
                bestRight = right;
            }
        }
        
        return s.substring(bestLeft, bestRight + 1);
    }
    
    /**
     * Visualization helper to show the sliding window process.
     */
    public void visualizeCharacterReplacement(String s, int k) {
        System.out.println("\n=== Character Replacement Visualization ===");
        System.out.println("String: " + s);
        System.out.println("k = " + k + " (max replacements allowed)");
        System.out.println();
        
        System.out.println("Window | Window String | Freq Map | Max Freq | Replacements Needed | Action | Max Len");
        System.out.println("-------|---------------|----------|----------|---------------------|--------|--------");
        
        int[] freq = new int[26];
        int left = 0, maxFreq = 0, maxLength = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char current = s.charAt(right);
            freq[current - 'A']++;
            maxFreq = Math.max(maxFreq, freq[current - 'A']);
            
            String windowStr = s.substring(left, right + 1);
            int windowSize = right - left + 1;
            int replacementsNeeded = windowSize - maxFreq;
            String action = "Add '" + current + "'";
            
            // Shrink if needed
            while (replacementsNeeded > k) {
                char leftChar = s.charAt(left);
                freq[leftChar - 'A']--;
                left++;
                windowStr = s.substring(left, right + 1);
                windowSize = right - left + 1;
                
                // Recalculate maxFreq
                maxFreq = 0;
                for (int count : freq) maxFreq = Math.max(maxFreq, count);
                
                replacementsNeeded = windowSize - maxFreq;
                action = "Remove '" + leftChar + "' (need " + replacementsNeeded + " replacements)";
            }
            
            maxLength = Math.max(maxLength, windowSize);
            
            // Format frequency map for display
            StringBuilder freqMap = new StringBuilder("{");
            for (int i = 0; i < 26; i++) {
                if (freq[i] > 0) {
                    freqMap.append((char)('A' + i)).append(":").append(freq[i]).append(", ");
                }
            }
            if (freqMap.length() > 1) freqMap.setLength(freqMap.length() - 2);
            freqMap.append("}");
            
            System.out.printf("[%d,%d] | %13s | %8s | %8d | %19d | %6s | %7d%n",
                left, right, windowStr, freqMap.toString(), maxFreq,
                replacementsNeeded, action, maxLength);
        }
        
        System.out.println("\nMaximum length substring with same character: " + maxLength);
    }
    
    /**
     * Test cases for character replacement problem.
     */
    public static void runTestCases() {
        LongestRepeatingCharacterReplacement solver = new LongestRepeatingCharacterReplacement();
        
        System.out.println("=== Longest Repeating Character Replacement Test Cases ===\n");
        
        // Test 1: Standard case
        String test1 = "AABABBA";
        int k1 = 1;
        System.out.println("Test 1:");
        System.out.println("s = \"" + test1 + "\", k = " + k1);
        int result1 = solver.characterReplacement(test1, k1);
        System.out.println("Result: " + result1);
        System.out.println("Expected: 4 (change second B to A: AAAABBA)");
        
        // Verify with brute force
        int brute1 = solver.characterReplacementBruteForce(test1, k1);
        System.out.println("Brute force: " + brute1 + " (matches: " + (result1 == brute1) + ")");
        System.out.println();
        
        // Test 2: No replacements needed
        String test2 = "AAAAA";
        int k2 = 0;
        System.out.println("Test 2 (all same, k=0):");
        System.out.println("s = \"" + test2 + "\", k = " + k2);
        int result2 = solver.characterReplacement(test2, k2);
        System.out.println("Result: " + result2);
        System.out.println("Expected: 5 (all characters already same)");
        System.out.println();
        
        // Test 3: Can replace all
        String test3 = "ABAB";
        int k3 = 2;
        System.out.println("Test 3:");
        System.out.println("s = \"" + test3 + "\", k = " + k3);
        int result3 = solver.characterReplacement(test3, k3);
        System.out.println("Result: " + result3);
        System.out.println("Expected: 4 (change both B's to A or both A's to B)");
        System.out.println();
        
        // Test 4: Complex pattern
        String test4 = "AABABBB";
        int k4 = 1;
        System.out.println("Test 4:");
        System.out.println("s = \"" + test4 + "\", k = " + k4);
        int result4 = solver.characterReplacement(test4, k4);
        System.out.println("Result: " + result4);
        System.out.println("Expected: 5 (AABABBB → change middle A to B: AABBBBB)");
        System.out.println();
        
        // Test 5: Empty string
        String test5 = "";
        int k5 = 2;
        System.out.println("Test 5 (empty string):");
        System.out.println("s = \"\", k = " + k5);
        int result5 = solver.characterReplacement(test5, k5);
        System.out.println("Result: " + result5);
        System.out.println("Expected: 0");
        System.out.println();
        
        // Test 6: Single character
        String test6 = "Z";
        int k6 = 5;
        System.out.println("Test 6 (single character):");
        System.out.println("s = \"" + test6 + "\", k = " + k6);
        int result6 = solver.characterReplacement(test6, k6);
        System.out.println("Result: " + result6);
        System.out.println("Expected: 1");
        System.out.println();
        
        // Test 7: From LeetCode
        String test7 = "ABAA";
        int k7 = 0;
        System.out.println("Test 7 (k=0):");
        System.out.println("s = \"" + test7 + "\", k = " + k7);
        int result7 = solver.characterReplacement(test7, k7);
        System.out.println("Result: " + result7);
        System.out.println("Expected: 2 (longest run of same character: AA)");
        System.out.println();
        
        // Test 8: All different characters
        String test8 = "ABCDE";
        int k8 = 2;
        System.out.println("Test 8 (all different):");
        System.out.println("s = \"" + test8 + "\", k = " + k8);
        int result8 = solver.characterReplacement(test8, k8);
        System.out.println("Result: " + result8);
        System.out.println("Expected: 3 (change 2 characters to match a third)");
        
        // Test substring version
        String substring = solver.characterReplacementSubstring(test8, k8);
        System.out.println("Longest substring: \"" + substring + "\" (length: " + substring.length() + ")");
    }
    
    /**
     * Performance comparison between different approaches.
     */
    public static void benchmark() {
        LongestRepeatingCharacterReplacement solver = new LongestRepeatingCharacterReplacement();
        
        System.out.println("\n=== Performance Comparison ===");
        
        // Generate large test string
        int n = 1000000;
        StringBuilder sb = new StringBuilder();
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            sb.append((char)('A' + rand.nextInt(26)));
        }
        String s = sb.toString();
        int k = 100;
        
        System.out.println("String length: " + n + ", k = " + k);
        
        // Optimized sliding window (maxFreq not updated during shrinking)
        long start = System.currentTimeMillis();
        int result1 = solver.characterReplacement(s, k);
        long time1 = System.currentTimeMillis() - start;
        System.out.println("Optimized sliding window: " + time1 + " ms, result: " + result1);
        
        // Sliding window with maxFreq update
        start = System.currentTimeMillis();
        int result2 = solver.characterReplacementWithMaxUpdate(s, k);
        long time2 = System.currentTimeMillis() - start;
        System.out.println("With maxFreq update: " + time2 + " ms, result: " + result2);
        
        System.out.println("Results match: " + (result1 == result2));
        System.out.println("Speedup: " + (time2 * 1.0 / time1) + "x");
    }
    
    /**
     * Explain the key optimization (why we don't update maxFreq when shrinking).
     */
    public static void explainOptimization() {
        System.out.println("\n=== Key Optimization: Why maxFreq Doesn't Need Updating ===");
        System.out.println();
        System.out.println("In the algorithm, when we shrink the window:");
        System.out.println("  while (windowSize - maxFreq > k) {");
        System.out.println("    freq[s.charAt(left)]--;");
        System.out.println("    left++;");
        System.out.println("    // Note: maxFreq NOT updated here");
        System.out.println("  }");
        System.out.println();
        System.out.println("Why is this correct?");
        System.out.println("1. maxFreq might become smaller after shrinking");
        System.out.println("2. But if maxFreq decreases, then (windowSize - maxFreq) INCREASES");
        System.out.println("3. We're already in the while loop because (windowSize - maxFreq) > k");
        System.out.println("4. If maxFreq decreases, condition remains true, so we keep shrinking");
        System.out.println("5. The only way to exit the loop is to shrink enough");
        System.out.println();
        System.out.println("Mathematical Proof:");
        System.out.println("Let m = original maxFreq");
        System.out.println("Let w = windowSize");
        System.out.println("We have: w - m > k  (need to shrink)");
        System.out.println("After shrinking, new maxFreq m' ≤ m");
        System.out.println("So: w' - m' ≥ w' - m  (since m' ≤ m)");
        System.out.println("Thus condition remains true if it was true before");
        System.out.println();
        System.out.println("Practical Implication:");
        System.out.println("We only need to update maxFreq when expanding window");
        System.out.println("This gives O(n) time instead of O(26n)");
    }
    
    /**
     * Show variation: Find minimum replacements to get substring of given length.
     */
    public static void showInverseProblem() {
        System.out.println("\n=== Inverse Problem: Minimum Replacements for Given Length ===");
        
        LongestRepeatingCharacterReplacement solver = new LongestRepeatingCharacterReplacement();
        String s = "AABABBA";
        
        System.out.println("String: " + s);
        System.out.println("\nFor each possible length L, minimum replacements needed:");
        System.out.println("L | Min Replacements | Example Substring");
        System.out.println("--|------------------|------------------");
        
        for (int L = 1; L <= s.length(); L++) {
            // Binary search for minimum k that gives length L
            int low = 0, high = L;
            while (low < high) {
                int mid = low + (high - low) / 2;
                if (solver.characterReplacement(s, mid) >= L) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }
            
            int minK = low;
            String example = solver.characterReplacementSubstring(s, minK);
            if (example.length() >= L) {
                example = example.substring(0, Math.min(L, example.length()));
            }
            
            System.out.printf("%d | %16d | %s%n", L, minK, example);
        }
    }
    
    public static void main(String[] args) {
        // Run test cases
        runTestCases();
        
        // Visualize the algorithm
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Detailed Visualization of Example:");
        System.out.println("=".repeat(60));
        
        LongestRepeatingCharacterReplacement solver = new LongestRepeatingCharacterReplacement();
        String s = "AABABBA";
        int k = 1;
        solver.visualizeCharacterReplacement(s, k);
        
        // Explain the optimization
        explainOptimization();
        
        // Show inverse problem
        showInverseProblem();
        
        // Run benchmark (optional)
        // benchmark();
    }
}