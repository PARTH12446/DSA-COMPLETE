import java.util.*;

/**
 * Longest Substring with At Most K Distinct Characters (LeetCode 340)
 * 
 * Problem: Given a string s and an integer k, return the length of the longest
 * substring that contains at most k distinct characters.
 * 
 * Example: s = "aaabbcc", k = 2
 * Possible substrings with ≤ 2 distinct chars: "aaa", "aaabb", "bbcc"
 * Longest: "aaabb" → length 5
 * 
 * Approaches:
 * 1. Brute force: O(n²) - Check all substrings
 * 2. Sliding window with frequency map: O(n) - Expand while ≤ k distinct, shrink when > k
 * 
 * Key Insight: Maintain window with ≤ k distinct characters using frequency map.
 * Expand right pointer, update distinct count, shrink from left when distinct > k.
 * 
 * Time Complexity: O(n) - Each character processed at most twice
 * Space Complexity: O(k) - Frequency map stores at most k+1 characters
 */
public class LongestSubstringWithKChar {
    
    /**
     * Finds length of longest substring with at most k distinct characters.
     * Uses sliding window with frequency array for O(1) character lookups.
     * 
     * Algorithm:
     * 1. Use freq[256] to count occurrences of each character in current window
     * 2. Track distinct count of characters in window
     * 3. Expand right pointer, update frequency and distinct count
     * 4. While distinct > k, shrink from left, update frequency and distinct
     * 5. Track maximum window length
     * 
     * Why it works:
     * - Window always maintains ≤ k distinct characters
     * - We try to expand as much as possible
     * - When we exceed k, we shrink minimally to restore validity
     * - Maximum window encountered is the answer
     * 
     * @param s Input string
     * @param k Maximum number of distinct characters allowed
     * @return Length of longest substring with at most k distinct characters
     */
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        // Edge cases
        if (s == null || s.length() == 0 || k <= 0) {
            return 0;
        }
        
        // If k is large enough to include all characters
        if (k >= s.length()) {
            return s.length();
        }
        
        // Frequency array for ASCII characters (0-255)
        int[] freq = new int[256];
        int distinct = 0;          // Number of distinct characters in current window
        int left = 0;              // Left pointer of sliding window
        int maxLength = 0;         // Maximum window length found
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            // Add current character to window
            if (freq[currentChar] == 0) {
                distinct++;  // New distinct character in window
            }
            freq[currentChar]++;
            
            // If we have too many distinct characters, shrink window
            while (distinct > k) {
                char leftChar = s.charAt(left);
                freq[leftChar]--;
                
                if (freq[leftChar] == 0) {
                    distinct--;  // This character is no longer in window
                }
                
                left++;  // Shrink window from left
            }
            
            // Update maximum window length
            int currentLength = right - left + 1;
            maxLength = Math.max(maxLength, currentLength);
        }
        
        return maxLength;
    }
    
    /**
     * Alternative: HashMap-based solution.
     * More flexible for Unicode characters and easier to understand.
     */
    public int lengthOfLongestSubstringKDistinctHashMap(String s, int k) {
        if (s == null || s.length() == 0 || k <= 0) return 0;
        
        Map<Character, Integer> charCount = new HashMap<>();
        int left = 0, maxLength = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);
            charCount.put(rightChar, charCount.getOrDefault(rightChar, 0) + 1);
            
            // Shrink window if we have more than k distinct characters
            while (charCount.size() > k) {
                char leftChar = s.charAt(left);
                charCount.put(leftChar, charCount.get(leftChar) - 1);
                
                if (charCount.get(leftChar) == 0) {
                    charCount.remove(leftChar);
                }
                
                left++;
            }
            
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    /**
     * Optimized: Use LinkedHashMap to maintain insertion order.
     * Enables O(1) removal of oldest character when shrinking.
     */
    public int lengthOfLongestSubstringKDistinctOptimized(String s, int k) {
        if (s == null || s.length() == 0 || k <= 0) return 0;
        
        // LinkedHashMap maintains insertion order
        Map<Character, Integer> charIndex = new LinkedHashMap<>();
        int left = 0, maxLength = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            
            // Remove if exists to update insertion order
            if (charIndex.containsKey(c)) {
                charIndex.remove(c);
            }
            charIndex.put(c, right);
            
            // If we have more than k distinct characters
            if (charIndex.size() > k) {
                // Remove the oldest character (first entry)
                Map.Entry<Character, Integer> leftMost = charIndex.entrySet().iterator().next();
                charIndex.remove(leftMost.getKey());
                left = leftMost.getValue() + 1;
            }
            
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    /**
     * Variation: Return the actual longest substring.
     */
    public String longestSubstringKDistinct(String s, int k) {
        if (s == null || s.length() == 0 || k <= 0) return "";
        
        int[] freq = new int[256];
        int distinct = 0, left = 0;
        int maxLength = 0, bestLeft = 0, bestRight = -1;
        
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (freq[c] == 0) distinct++;
            freq[c]++;
            
            while (distinct > k) {
                char leftChar = s.charAt(left);
                freq[leftChar]--;
                if (freq[leftChar] == 0) distinct--;
                left++;
            }
            
            int currentLength = right - left + 1;
            if (currentLength > maxLength) {
                maxLength = currentLength;
                bestLeft = left;
                bestRight = right;
            }
        }
        
        return bestRight >= bestLeft ? s.substring(bestLeft, bestRight + 1) : "";
    }
    
    /**
     * Brute force solution for verification (O(n²)).
     */
    public int lengthOfLongestSubstringKDistinctBruteForce(String s, int k) {
        if (s == null || s.length() == 0 || k <= 0) return 0;
        
        int maxLength = 0;
        
        for (int start = 0; start < s.length(); start++) {
            Set<Character> seen = new HashSet<>();
            
            for (int end = start; end < s.length(); end++) {
                seen.add(s.charAt(end));
                
                if (seen.size() > k) {
                    break;
                }
                
                maxLength = Math.max(maxLength, end - start + 1);
            }
        }
        
        return maxLength;
    }
    
    /**
     * Variation: At least K distinct characters.
     * Find longest substring with at least K distinct characters.
     */
    public int lengthOfLongestSubstringAtLeastKDistinct(String s, int k) {
        if (s == null || s.length() == 0 || k <= 0) return 0;
        
        int maxLength = 0;
        
        // For each starting position
        for (int start = 0; start < s.length(); start++) {
            int[] freq = new int[256];
            int distinct = 0;
            
            for (int end = start; end < s.length(); end++) {
                char c = s.charAt(end);
                if (freq[c] == 0) distinct++;
                freq[c]++;
                
                if (distinct >= k) {
                    maxLength = Math.max(maxLength, end - start + 1);
                }
            }
        }
        
        return maxLength;
    }
    
    /**
     * Variation: Exactly K distinct characters.
     * Find longest substring with exactly K distinct characters.
     * Uses the "at most" technique: exactly(K) = atMost(K) - atMost(K-1) for each position.
     */
    public int lengthOfLongestSubstringExactlyKDistinct(String s, int k) {
        return lengthOfLongestSubstringKDistinct(s, k) - lengthOfLongestSubstringKDistinct(s, k - 1);
    }
    
    /**
     * Visualization helper to show the sliding window process.
     */
    public void visualizeKDistinct(String s, int k) {
        System.out.println("\n=== Longest Substring with At Most " + k + " Distinct Characters ===");
        System.out.println("String: " + s);
        System.out.println();
        
        System.out.println("Window | Window Content | Distinct Chars | Frequency Map | Action | Max Length");
        System.out.println("-------|----------------|----------------|---------------|--------|-----------");
        
        int[] freq = new int[256];
        int distinct = 0, left = 0, maxLength = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            // Update frequency and distinct count
            if (freq[currentChar] == 0) distinct++;
            freq[currentChar]++;
            
            String action = "Add '" + currentChar + "'";
            String windowContent = s.substring(left, right + 1);
            
            // Shrink if needed
            while (distinct > k) {
                char leftChar = s.charAt(left);
                freq[leftChar]--;
                if (freq[leftChar] == 0) distinct--;
                left++;
                action = "Remove '" + leftChar + "' (too many distinct)";
                windowContent = s.substring(left, right + 1);
            }
            
            // Update max length
            int currentLength = right - left + 1;
            if (currentLength > maxLength) {
                maxLength = currentLength;
                action += " → New max length!";
            }
            
            // Create frequency map string
            StringBuilder freqMap = new StringBuilder("{");
            for (int i = 0; i < 256; i++) {
                if (freq[i] > 0) {
                    freqMap.append((char)i).append(":").append(freq[i]).append(", ");
                }
            }
            if (freqMap.length() > 1) freqMap.setLength(freqMap.length() - 2);
            freqMap.append("}");
            
            System.out.printf("[%d,%d] | %14s | %14d | %13s | %6s | %10d%n",
                left, right, windowContent, distinct, freqMap.toString(), action, maxLength);
        }
        
        System.out.println("\nMaximum length substring with ≤ " + k + " distinct characters: " + maxLength);
        
        // Show the actual substring
        String longest = longestSubstringKDistinct(s, k);
        System.out.println("Longest substring: \"" + longest + "\" (length: " + longest.length() + ")");
    }
    
    /**
     * Test cases for longest substring with k distinct characters.
     */
    public static void runTestCases() {
        LongestSubstringWithKChar solver = new LongestSubstringWithKChar();
        
        System.out.println("=== Longest Substring with K Distinct Characters Test Cases ===\n");
        
        // Test 1: Standard case
        String test1 = "aaabbcc";
        int k1 = 2;
        System.out.println("Test 1:");
        System.out.println("s = \"" + test1 + "\", k = " + k1);
        int result1 = solver.lengthOfLongestSubstringKDistinct(test1, k1);
        System.out.println("Result: " + result1);
        System.out.println("Expected: 5 (substring \"aaabb\")");
        
        // Verify with brute force
        int brute1 = solver.lengthOfLongestSubstringKDistinctBruteForce(test1, k1);
        System.out.println("Brute force: " + brute1 + " (matches: " + (result1 == brute1) + ")");
        System.out.println("Longest substring: \"" + solver.longestSubstringKDistinct(test1, k1) + "\"");
        System.out.println();
        
        // Test 2: k = 1
        String test2 = "aaabbcc";
        int k2 = 1;
        System.out.println("Test 2 (k=1):");
        System.out.println("s = \"" + test2 + "\", k = " + k2);
        int result2 = solver.lengthOfLongestSubstringKDistinct(test2, k2);
        System.out.println("Result: " + result2);
        System.out.println("Expected: 3 (substring \"aaa\")");
        System.out.println();
        
        // Test 3: All distinct characters
        String test3 = "abcdef";
        int k3 = 3;
        System.out.println("Test 3 (all distinct):");
        System.out.println("s = \"" + test3 + "\", k = " + k3);
        int result3 = solver.lengthOfLongestSubstringKDistinct(test3, k3);
        System.out.println("Result: " + result3);
        System.out.println("Expected: 3 (any 3 distinct characters)");
        System.out.println();
        
        // Test 4: k larger than distinct characters in string
        String test4 = "aabbcc";
        int k4 = 10;
        System.out.println("Test 4 (k > distinct chars):");
        System.out.println("s = \"" + test4 + "\", k = " + k4);
        int result4 = solver.lengthOfLongestSubstringKDistinct(test4, k4);
        System.out.println("Result: " + result4);
        System.out.println("Expected: 6 (entire string)");
        System.out.println();
        
        // Test 5: Mixed case
        String test5 = "eceba";
        int k5 = 2;
        System.out.println("Test 5:");
        System.out.println("s = \"" + test5 + "\", k = " + k5);
        int result5 = solver.lengthOfLongestSubstringKDistinct(test5, k5);
        System.out.println("Result: " + result5);
        System.out.println("Expected: 3 (substring \"ece\")");
        System.out.println();
        
        // Test 6: Empty string
        String test6 = "";
        int k6 = 2;
        System.out.println("Test 6 (empty string):");
        System.out.println("s = \"\", k = " + k6);
        int result6 = solver.lengthOfLongestSubstringKDistinct(test6, k6);
        System.out.println("Result: " + result6);
        System.out.println("Expected: 0");
        System.out.println();
        
        // Test 7: k = 0
        String test7 = "abc";
        int k7 = 0;
        System.out.println("Test 7 (k=0):");
        System.out.println("s = \"" + test7 + "\", k = " + k7);
        int result7 = solver.lengthOfLongestSubstringKDistinct(test7, k7);
        System.out.println("Result: " + result7);
        System.out.println("Expected: 0 (can't have any characters)");
        System.out.println();
        
        // Test variations
        System.out.println("Testing variations:");
        String test8 = "abcabcabc";
        int k8 = 2;
        System.out.println("Exactly " + k8 + " distinct characters: " + 
                          solver.lengthOfLongestSubstringExactlyKDistinct(test8, k8));
        System.out.println("At least " + k8 + " distinct characters: " + 
                          solver.lengthOfLongestSubstringAtLeastKDistinct(test8, k8));
    }
    
    /**
     * Performance comparison between different approaches.
     */
    public static void benchmark() {
        LongestSubstringWithKChar solver = new LongestSubstringWithKChar();
        
        System.out.println("\n=== Performance Comparison ===");
        
        // Generate large test string
        int n = 1000000;
        StringBuilder sb = new StringBuilder();
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            // Use 5 different characters to simulate limited alphabet
            char c = (char)('a' + rand.nextInt(5));
            sb.append(c);
        }
        String s = sb.toString();
        int k = 3;
        
        System.out.println("String length: " + n + ", k = " + k);
        System.out.println("Alphabet size: 5 characters");
        
        // Frequency array approach
        long start = System.currentTimeMillis();
        int result1 = solver.lengthOfLongestSubstringKDistinct(s, k);
        long time1 = System.currentTimeMillis() - start;
        System.out.println("Frequency array: " + time1 + " ms, result: " + result1);
        
        // HashMap approach
        start = System.currentTimeMillis();
        int result2 = solver.lengthOfLongestSubstringKDistinctHashMap(s, k);
        long time2 = System.currentTimeMillis() - start;
        System.out.println("HashMap: " + time2 + " ms, result: " + result2);
        
        // Optimized LinkedHashMap approach
        start = System.currentTimeMillis();
        int result3 = solver.lengthOfLongestSubstringKDistinctOptimized(s, k);
        long time3 = System.currentTimeMillis() - start;
        System.out.println("LinkedHashMap: " + time3 + " ms, result: " + result3);
        
        System.out.println("All results match: " + (result1 == result2 && result2 == result3));
        System.out.println("Frequency array is fastest for limited alphabet");
    }
    
    /**
     * Explain the sliding window technique for distinct characters.
     */
    public static void explainSlidingWindow() {
        System.out.println("\n=== Sliding Window for Distinct Characters ===");
        System.out.println();
        System.out.println("Problem: Find longest substring with ≤ k distinct characters");
        System.out.println();
        System.out.println("Key Insight: Use frequency map to track characters in window");
        System.out.println("  - Expand right pointer: add character, update frequency");
        System.out.println("  - Shrink left pointer when distinct count > k");
        System.out.println("  - Track maximum window length");
        System.out.println();
        System.out.println("Example: s = \"aaabbcc\", k = 2");
        System.out.println("  Step 1: [a] distinct=1, max=1");
        System.out.println("  Step 2: [aa] distinct=1, max=2");
        System.out.println("  Step 3: [aaa] distinct=1, max=3");
        System.out.println("  Step 4: [aaab] distinct=2, max=4");
        System.out.println("  Step 5: [aaabb] distinct=2, max=5");
        System.out.println("  Step 6: [aaabbc] distinct=3 > 2 → shrink");
        System.out.println("          [aabbc] distinct=3 > 2 → shrink");
        System.out.println("          [abbc] distinct=3 > 2 → shrink");
        System.out.println("          [bbc] distinct=2, max=5 (unchanged)");
        System.out.println("  Step 7: [bbcc] distinct=2, max=5");
        System.out.println();
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Each character added once (right pointer)");
        System.out.println("  - Each character removed at most once (left pointer)");
        System.out.println("  - Total: O(2n) → O(n)");
        System.out.println();
        System.out.println("Space Complexity: O(k) or O(1)");
        System.out.println("  - Frequency map stores at most k+1 characters");
        System.out.println("  - For ASCII, fixed array of 256 → O(1)");
        System.out.println("  - For Unicode, HashMap → O(k)");
    }
    
    /**
     * Show related problems and variations.
     */
    public static void showRelatedProblems() {
        System.out.println("\n=== Related Problems and Variations ===");
        
        LongestSubstringWithKChar solver = new LongestSubstringWithKChar();
        
        // Variation 1: Minimum window substring
        System.out.println("Variation 1: Minimum Window Substring");
        System.out.println("  Find smallest substring containing all characters of pattern");
        System.out.println("  Uses similar sliding window with pattern frequency map");
        System.out.println();
        
        // Variation 2: Longest substring without repeating characters
        System.out.println("Variation 2: Longest Substring Without Repeating Characters");
        System.out.println("  Special case with k = 1 distinct character");
        System.out.println("  Actually k = 1 for 'distinct' means all characters same");
        System.out.println("  The real 'no repeating' problem is different");
        System.out.println();
        
        // Variation 3: Subarrays with K different integers
        System.out.println("Variation 3: Subarrays with K Different Integers");
        System.out.println("  Same problem but for integer arrays instead of strings");
        System.out.println("  Uses same sliding window technique");
        System.out.println();
        
        // Variation 4: Fruit Into Baskets
        System.out.println("Variation 4: Fruit Into Baskets (LeetCode 904)");
        System.out.println("  Special case with k = 2 distinct characters/types");
        System.out.println("  Find longest subarray with at most 2 distinct values");
        int[] fruits = {1, 2, 1, 2, 3, 2, 2};
        System.out.println("  Equivalent to string with characters {1,2,3}, k=2");
        System.out.println();
        
        // Real-world applications
        System.out.println("Real-world Applications:");
        System.out.println("  1. Data compression: Find repetitive patterns");
        System.out.println("  2. Bioinformatics: Find DNA sequences with limited bases");
        System.out.println("  3. Text analysis: Find repetitive text patterns");
        System.out.println("  4. Network protocols: Packet analysis with limited symbols");
    }
    
    public static void main(String[] args) {
        // Run test cases
        runTestCases();
        
        // Visualize the algorithm
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Detailed Visualization of Example:");
        System.out.println("=".repeat(60));
        
        LongestSubstringWithKChar solver = new LongestSubstringWithKChar();
        String s = "aaabbcc";
        int k = 2;
        solver.visualizeKDistinct(s, k);
        
        // Explain the technique
        explainSlidingWindow();
        
        // Show related problems
        showRelatedProblems();
        
        // Run benchmark (optional)
        // benchmark();
    }
}