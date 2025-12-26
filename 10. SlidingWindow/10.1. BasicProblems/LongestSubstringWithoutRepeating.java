import java.util.*;

/**
 * Longest Substring Without Repeating Characters (LeetCode 3)
 * 
 * Problem: Given a string s, find the length of the longest substring
 * without repeating characters.
 * 
 * Example: s = "abcabcbb"
 * Longest substring without repeating: "abc" → length = 3
 * 
 * Approaches:
 * 1. Brute force: O(n³) - Check all substrings
 * 2. Sliding window with HashSet: O(2n) = O(n) time, O(min(n, m)) space
 * 3. Sliding window with last index tracking: O(n) time, O(m) space (implemented)
 *    where m = character set size (256 for extended ASCII)
 * 
 * Key Insight: Use lastIndex array to track most recent occurrence of each character.
 * When duplicate found, jump left pointer to position after previous occurrence.
 */
public class LongestSubstringWithoutRepeating {
    
    /**
     * Finds length of longest substring without repeating characters.
     * Uses last occurrence tracking for O(n) time.
     * 
     * Algorithm:
     * 1. Array lastIndex[256] stores last occurrence index of each character
     *    Initialize to -1 (not seen yet)
     * 2. Maintain sliding window [left, right] with unique characters
     * 3. For each character at position right:
     *    a. If character seen before within current window (lastIndex[c] >= left):
     *       - Move left to position after previous occurrence (lastIndex[c] + 1)
     *    b. Update lastIndex[c] = right
     *    c. Update maximum window length
     * 
     * Why it works:
     * - When duplicate found, we know exactly where to restart
     * - No need to move left pointer one by one
     * - Each character processed once → O(n) time
     * 
     * @param s Input string
     * @return Length of longest substring without repeating characters
     */
    public int lengthOfLongestSubstring(String s) {
        // Edge cases
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        // For extended ASCII characters (0-255)
        // Could use 128 for standard ASCII or HashMap for Unicode
        int[] lastIndex = new int[256];
        Arrays.fill(lastIndex, -1); // -1 means character not seen yet
        
        int left = 0;               // Left boundary of current window
        int maxLength = 0;          // Maximum window length found
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            // If character seen before AND within current window
            if (lastIndex[currentChar] >= left) {
                // Move left boundary to position after previous occurrence
                left = lastIndex[currentChar] + 1;
            }
            
            // Update last occurrence of current character
            lastIndex[currentChar] = right;
            
            // Update maximum length
            int currentLength = right - left + 1;
            maxLength = Math.max(maxLength, currentLength);
        }
        
        return maxLength;
    }
    
    /**
     * Alternative: HashSet-based sliding window.
     * More intuitive but slightly slower due to HashSet overhead.
     */
    public int lengthOfLongestSubstringHashSet(String s) {
        if (s == null || s.length() == 0) return 0;
        
        Set<Character> seen = new HashSet<>();
        int left = 0, maxLength = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            // While duplicate found, shrink window from left
            while (seen.contains(currentChar)) {
                seen.remove(s.charAt(left));
                left++;
            }
            
            // Add current character to window
            seen.add(currentChar);
            
            // Update maximum length
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    /**
     * Alternative: HashMap-based solution.
     * Stores character → index mapping for O(1) lookups.
     */
    public int lengthOfLongestSubstringHashMap(String s) {
        if (s == null || s.length() == 0) return 0;
        
        Map<Character, Integer> charIndex = new HashMap<>();
        int left = 0, maxLength = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            // If duplicate found in current window
            if (charIndex.containsKey(currentChar)) {
                int lastSeen = charIndex.get(currentChar);
                // Only move left if duplicate is within current window
                if (lastSeen >= left) {
                    left = lastSeen + 1;
                }
            }
            
            // Update character's last index
            charIndex.put(currentChar, right);
            
            // Update maximum length
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    /**
     * Variation: Return the actual longest substring.
     */
    public String longestSubstringWithoutRepeating(String s) {
        if (s == null || s.length() == 0) return "";
        
        int[] lastIndex = new int[256];
        Arrays.fill(lastIndex, -1);
        
        int left = 0, maxLength = 0;
        int bestLeft = 0, bestRight = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            if (lastIndex[currentChar] >= left) {
                left = lastIndex[currentChar] + 1;
            }
            
            lastIndex[currentChar] = right;
            
            int currentLength = right - left + 1;
            if (currentLength > maxLength) {
                maxLength = currentLength;
                bestLeft = left;
                bestRight = right;
            }
        }
        
        return s.substring(bestLeft, bestRight + 1);
    }
    
    /**
     * Brute force solution for verification (O(n³)).
     */
    public int lengthOfLongestSubstringBruteForce(String s) {
        if (s == null || s.length() == 0) return 0;
        
        int maxLength = 0;
        int n = s.length();
        
        for (int start = 0; start < n; start++) {
            for (int end = start; end < n; end++) {
                if (hasUniqueCharacters(s, start, end)) {
                    maxLength = Math.max(maxLength, end - start + 1);
                } else {
                    break; // Further extension will also have duplicates
                }
            }
        }
        
        return maxLength;
    }
    
    /**
     * Helper for brute force: Check if substring has all unique characters.
     */
    private boolean hasUniqueCharacters(String s, int start, int end) {
        Set<Character> seen = new HashSet<>();
        for (int i = start; i <= end; i++) {
            if (seen.contains(s.charAt(i))) {
                return false;
            }
            seen.add(s.charAt(i));
        }
        return true;
    }
    
    /**
     * Visualization helper to show the sliding window process.
     */
    public void visualizeLongestSubstring(String s) {
        System.out.println("\n=== Longest Substring Without Repeating Characters ===");
        System.out.println("String: " + s);
        System.out.println();
        
        System.out.println("Step | Window | Current Char | Last Index | Action | Max Length");
        System.out.println("-----|--------|--------------|------------|--------|-----------");
        
        int[] lastIndex = new int[256];
        Arrays.fill(lastIndex, -1);
        
        int left = 0, maxLength = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            String window = s.substring(left, right + 1);
            String action = "";
            
            // Check if duplicate found
            if (lastIndex[currentChar] >= left) {
                int oldLeft = left;
                left = lastIndex[currentChar] + 1;
                action = "Duplicate '" + currentChar + "' found at index " + 
                        lastIndex[currentChar] + ". Move left from " + oldLeft + " to " + left;
            } else {
                action = "New character '" + currentChar + "' added to window";
            }
            
            // Update last index
            lastIndex[currentChar] = right;
            
            // Update maximum length
            int currentLength = right - left + 1;
            if (currentLength > maxLength) {
                maxLength = currentLength;
                action += " (new max length!)";
            }
            
            System.out.printf("%4d | %6s | %12c | %10d | %6s | %10d%n",
                right, window, currentChar, lastIndex[currentChar], action, maxLength);
        }
        
        System.out.println("\nMaximum length substring without repeating characters: " + maxLength);
    }
    
    /**
     * Test cases for longest substring without repeating characters.
     */
    public static void runTestCases() {
        LongestSubstringWithoutRepeating solver = new LongestSubstringWithoutRepeating();
        
        System.out.println("=== Longest Substring Without Repeating Characters Test Cases ===\n");
        
        // Test 1: Standard case
        String test1 = "abcabcbb";
        System.out.println("Test 1:");
        System.out.println("s = \"" + test1 + "\"");
        int result1 = solver.lengthOfLongestSubstring(test1);
        System.out.println("Result: " + result1);
        System.out.println("Expected: 3 (substring \"abc\")");
        
        // Verify with brute force
        int brute1 = solver.lengthOfLongestSubstringBruteForce(test1);
        System.out.println("Brute force: " + brute1 + " (matches: " + (result1 == brute1) + ")");
        System.out.println("Actual substring: \"" + solver.longestSubstringWithoutRepeating(test1) + "\"");
        System.out.println();
        
        // Test 2: All same characters
        String test2 = "bbbbb";
        System.out.println("Test 2 (all same):");
        System.out.println("s = \"" + test2 + "\"");
        int result2 = solver.lengthOfLongestSubstring(test2);
        System.out.println("Result: " + result2);
        System.out.println("Expected: 1 (any single character)");
        System.out.println();
        
        // Test 3: All unique
        String test3 = "abcdef";
        System.out.println("Test 3 (all unique):");
        System.out.println("s = \"" + test3 + "\"");
        int result3 = solver.lengthOfLongestSubstring(test3);
        System.out.println("Result: " + result3);
        System.out.println("Expected: 6 (entire string)");
        System.out.println();
        
        // Test 4: Empty string
        String test4 = "";
        System.out.println("Test 4 (empty string):");
        System.out.println("s = \"\"");
        int result4 = solver.lengthOfLongestSubstring(test4);
        System.out.println("Result: " + result4);
        System.out.println("Expected: 0");
        System.out.println();
        
        // Test 5: Single character
        String test5 = "a";
        System.out.println("Test 5 (single character):");
        System.out.println("s = \"" + test5 + "\"");
        int result5 = solver.lengthOfLongestSubstring(test5);
        System.out.println("Result: " + result5);
        System.out.println("Expected: 1");
        System.out.println();
        
        // Test 6: From LeetCode
        String test6 = "pwwkew";
        System.out.println("Test 6:");
        System.out.println("s = \"" + test6 + "\"");
        int result6 = solver.lengthOfLongestSubstring(test6);
        System.out.println("Result: " + result6);
        System.out.println("Expected: 3 (substring \"wke\")");
        System.out.println("Actual substring: \"" + solver.longestSubstringWithoutRepeating(test6) + "\"");
        System.out.println();
        
        // Test 7: With spaces and special characters
        String test7 = "abc def ghi!";
        System.out.println("Test 7 (with spaces and punctuation):");
        System.out.println("s = \"" + test7 + "\"");
        int result7 = solver.lengthOfLongestSubstring(test7);
        System.out.println("Result: " + result7);
        System.out.println("Expected: 9 (\"abc def gh\")");
        System.out.println();
        
        // Test different implementations
        System.out.println("Testing different implementations on same input:");
        String test8 = "abcabcbb";
        System.out.println("Input: \"" + test8 + "\"");
        System.out.println("Array method: " + solver.lengthOfLongestSubstring(test8));
        System.out.println("HashSet method: " + solver.lengthOfLongestSubstringHashSet(test8));
        System.out.println("HashMap method: " + solver.lengthOfLongestSubstringHashMap(test8));
    }
    
    /**
     * Performance comparison between different approaches.
     */
    public static void benchmark() {
        LongestSubstringWithoutRepeating solver = new LongestSubstringWithoutRepeating();
        
        System.out.println("\n=== Performance Comparison ===");
        
        // Generate large test string
        int n = 1000000;
        StringBuilder sb = new StringBuilder();
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            // Mix of lowercase, uppercase, digits, and spaces
            int type = rand.nextInt(4);
            switch (type) {
                case 0: sb.append((char)('a' + rand.nextInt(26))); break;
                case 1: sb.append((char)('A' + rand.nextInt(26))); break;
                case 2: sb.append((char)('0' + rand.nextInt(10))); break;
                case 3: sb.append(' '); break;
            }
        }
        String s = sb.toString();
        
        System.out.println("String length: " + n);
        
        // Array-based solution
        long start = System.currentTimeMillis();
        int result1 = solver.lengthOfLongestSubstring(s);
        long time1 = System.currentTimeMillis() - start;
        System.out.println("Array method: " + time1 + " ms, result: " + result1);
        
        // HashSet-based solution
        start = System.currentTimeMillis();
        int result2 = solver.lengthOfLongestSubstringHashSet(s);
        long time2 = System.currentTimeMillis() - start;
        System.out.println("HashSet method: " + time2 + " ms, result: " + result2);
        
        // HashMap-based solution
        start = System.currentTimeMillis();
        int result3 = solver.lengthOfLongestSubstringHashMap(s);
        long time3 = System.currentTimeMillis() - start;
        System.out.println("HashMap method: " + time3 + " ms, result: " + result3);
        
        System.out.println("All results match: " + 
                          (result1 == result2 && result2 == result3));
        System.out.println("Array method is fastest due to O(1) array access vs O(1) amortized for HashSet/HashMap");
    }
    
    /**
     * Explain the optimization of jumping left pointer.
     */
    public static void explainJumpOptimization() {
        System.out.println("\n=== Optimization: Jumping Left Pointer ===");
        System.out.println();
        System.out.println("Without optimization (HashSet approach):");
        System.out.println("  When duplicate found:");
        System.out.println("    while (set.contains(currentChar)) {");
        System.out.println("      set.remove(s.charAt(left));");
        System.out.println("      left++;");
        System.out.println("    }");
        System.out.println("  Time: O(n) in worst case (left moves one by one)");
        System.out.println();
        System.out.println("With optimization (array approach):");
        System.out.println("  When duplicate found:");
        System.out.println("    if (lastIndex[c] >= left) {");
        System.out.println("      left = lastIndex[c] + 1;");
        System.out.println("    }");
        System.out.println("  Time: O(1) (direct jump to new position)");
        System.out.println();
        System.out.println("Example: s = \"abcdeafg\"");
        System.out.println("  Without optimization:");
        System.out.println("    At 'a' (index 5): remove 'a','b','c','d','e' one by one");
        System.out.println("    left moves: 0→1→2→3→4→5");
        System.out.println("  With optimization:");
        System.out.println("    At 'a' (index 5): lastIndex['a'] = 0");
        System.out.println("    left jumps directly to 0 + 1 = 1");
        System.out.println("    Only one operation!");
    }
    
    /**
     * Show handling of different character sets.
     */
    public static void showCharacterSetHandling() {
        System.out.println("\n=== Handling Different Character Sets ===");
        
        LongestSubstringWithoutRepeating solver = new LongestSubstringWithoutRepeating();
        
        // Test with different character sets
        String[] testCases = {
            "abc123",                    // Alphanumeric
            "Hello, World!",             // With punctuation
            "Café",                      // Unicode character
            "𐌀𐌁𐌂𐌃𐌄",                  // Unicode outside BMP (needs larger array)
            "ab\ncd\tef"                 // With whitespace
        };
        
        for (String test : testCases) {
            System.out.println("\nInput: \"" + test + "\"");
            
            // Array method works for ASCII (0-255)
            // For full Unicode, we'd need HashMap
            int result = solver.lengthOfLongestSubstringHashMap(test);
            System.out.println("Length: " + result);
            
            String substring = solver.longestSubstringWithoutRepeating(test);
            System.out.println("Substring: \"" + substring + "\"");
        }
        
        System.out.println("\nNote: Array method uses 256 slots for extended ASCII.");
        System.out.println("For full Unicode, use HashMap with O(1) amortized access.");
    }
    
    public static void main(String[] args) {
        // Run test cases
        runTestCases();
        
        // Visualize the algorithm
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Detailed Visualization of Example:");
        System.out.println("=".repeat(60));
        
        LongestSubstringWithoutRepeating solver = new LongestSubstringWithoutRepeating();
        String s = "abcabcbb";
        solver.visualizeLongestSubstring(s);
        
        // Explain the optimization
        explainJumpOptimization();
        
        // Show character set handling
        showCharacterSetHandling();
        
        // Run benchmark (optional)
        // benchmark();
    }
}