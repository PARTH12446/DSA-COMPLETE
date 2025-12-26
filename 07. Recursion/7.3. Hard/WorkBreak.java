import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Word Break Problem
 * 
 * Problem: Given a string s and a dictionary of words, determine if s can be
 * segmented into a space-separated sequence of one or more dictionary words.
 * 
 * Example: s = "leetcode", wordDict = ["leet", "code"]
 * Output: true because "leet code" are both in dictionary
 * 
 * Applications:
 * - Text segmentation in NLP
 * - Spell checking and correction
 * - Compiler design (tokenization)
 * - Bioinformatics (DNA sequence segmentation)
 */
public class WorkBreak {

    /**
     * Main method: Check if string can be segmented into dictionary words.
     * Uses memoized recursion (top-down DP).
     * 
     * @param s String to segment
     * @param wordDict List of valid words
     * @return true if string can be segmented
     * 
     * Time Complexity: O(n³) worst case (n² substring checks × n positions)
     *                 But with memoization, much better in practice
     * Space Complexity: O(n) for memo array + recursion stack
     */
    public static boolean wordBreak(String s, List<String> wordDict) {
        // Validate inputs
        if (s == null || s.isEmpty()) return true; // Empty string can always be segmented
        if (wordDict == null || wordDict.isEmpty()) return false;
        
        // Convert dictionary to Set for O(1) lookups
        Set<String> dict = new HashSet<>(wordDict);
        
        // Memoization array: dp[i] = can segment s[i:n] ?
        Boolean[] memo = new Boolean[s.length()];
        
        return canBreak(0, s, dict, memo);
    }

    /**
     * Recursive helper with memoization.
     * 
     * Approach: At each position, try all possible end positions.
     * If substring is in dictionary, recursively check the remainder.
     * 
     * @param start Current starting index
     * @param s Original string
     * @param dict Dictionary of valid words
     * @param memo Memoization array
     * @return true if s[start:] can be segmented
     */
    private static boolean canBreak(int start, String s, Set<String> dict, Boolean[] memo) {
        // Base case: reached end of string
        if (start == s.length()) {
            return true;
        }
        
        // Return memoized result if available
        if (memo[start] != null) {
            return memo[start];
        }
        
        // Try all possible ending positions
        for (int end = start + 1; end <= s.length(); end++) {
            String currentWord = s.substring(start, end);
            
            // If current substring is a valid word
            if (dict.contains(currentWord)) {
                // Recursively check the remainder
                if (canBreak(end, s, dict, memo)) {
                    memo[start] = true;
                    return true;
                }
            }
        }
        
        // No valid segmentation found from this position
        memo[start] = false;
        return false;
    }

    /**
     * Bottom-up dynamic programming solution.
     * More efficient iterative approach.
     * 
     * dp[i] = true if s[0:i] can be segmented into dictionary words
     * dp[0] = true (empty string)
     * 
     * Recurrence: dp[i] = true if ∃ j < i such that dp[j] = true 
     *                      and s[j:i] ∈ dict
     */
    public static boolean wordBreakDP(String s, List<String> wordDict) {
        if (s == null || s.isEmpty()) return true;
        if (wordDict == null || wordDict.isEmpty()) return false;
        
        Set<String> dict = new HashSet<>(wordDict);
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true; // Empty string can always be segmented
        
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                // If prefix s[0:j] can be segmented and s[j:i] is in dictionary
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break; // No need to check other j values
                }
            }
        }
        
        return dp[n];
    }

    /**
     * BFS approach: Treat positions as nodes in a graph.
     * There's an edge from i to j if s[i:j] is in dictionary.
     * Check if there's a path from 0 to n.
     */
    public static boolean wordBreakBFS(String s, List<String> wordDict) {
        if (s == null || s.isEmpty()) return true;
        if (wordDict == null || wordDict.isEmpty()) return false;
        
        Set<String> dict = new HashSet<>(wordDict);
        int n = s.length();
        boolean[] visited = new boolean[n + 1];
        Queue<Integer> queue = new LinkedList<>();
        
        queue.offer(0); // Start from position 0
        
        while (!queue.isEmpty()) {
            int start = queue.poll();
            
            // Skip if already visited from this position
            if (visited[start]) continue;
            visited[start] = true;
            
            // Try all possible end positions
            for (int end = start + 1; end <= n; end++) {
                String word = s.substring(start, end);
                if (dict.contains(word)) {
                    // If reached end, success
                    if (end == n) {
                        return true;
                    }
                    // Otherwise, continue BFS from this position
                    queue.offer(end);
                }
            }
        }
        
        return false;
    }

    /**
     * Find all possible segmentations (Word Break II).
     * Returns list of all possible space-separated sentences.
     * 
     * @param s String to segment
     * @param wordDict Dictionary of valid words
     * @return List of all possible segmentations
     */
    public static List<String> wordBreakAll(String s, List<String> wordDict) {
        List<String> result = new ArrayList<>();
        if (s == null || s.isEmpty() || wordDict == null || wordDict.isEmpty()) {
            return result;
        }
        
        Set<String> dict = new HashSet<>(wordDict);
        List<String>[] memo = new List[s.length() + 1];
        
        return backtrackAll(0, s, dict, memo);
    }
    
    private static List<String> backtrackAll(int start, String s, 
                                            Set<String> dict, List<String>[] memo) {
        // Return memoized result if available
        if (memo[start] != null) {
            return memo[start];
        }
        
        List<String> result = new ArrayList<>();
        
        // Base case: reached end of string
        if (start == s.length()) {
            result.add(""); // Empty string to build upon
            return result;
        }
        
        // Try all possible endings
        for (int end = start + 1; end <= s.length(); end++) {
            String word = s.substring(start, end);
            
            if (dict.contains(word)) {
                // Get all segmentations for the remainder
                List<String> subResults = backtrackAll(end, s, dict, memo);
                
                // Combine current word with each sub-result
                for (String sub : subResults) {
                    if (sub.isEmpty()) {
                        result.add(word);
                    } else {
                        result.add(word + " " + sub);
                    }
                }
            }
        }
        
        memo[start] = result;
        return result;
    }

    /**
     * Optimized version with pruning: Only try word lengths that exist in dictionary.
     */
    public static boolean wordBreakOptimized(String s, List<String> wordDict) {
        if (s == null || s.isEmpty()) return true;
        if (wordDict == null || wordDict.isEmpty()) return false;
        
        Set<String> dict = new HashSet<>(wordDict);
        
        // Track word lengths for efficient iteration
        Set<Integer> wordLengths = new HashSet<>();
        for (String word : dict) {
            wordLengths.add(word.length());
        }
        
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        
        for (int i = 1; i <= n; i++) {
            for (int len : wordLengths) {
                int j = i - len;
                if (j >= 0 && dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        
        return dp[n];
    }

    /**
     * Returns the minimum number of segments (words) needed.
     * 
     * @param s String to segment
     * @param wordDict Dictionary
     * @return Minimum number of words, or -1 if not possible
     */
    public static int minWordBreakSegments(String s, List<String> wordDict) {
        if (s == null || s.isEmpty()) return 0;
        if (wordDict == null || wordDict.isEmpty()) return -1;
        
        Set<String> dict = new HashSet<>(wordDict);
        int n = s.length();
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0; // Empty string needs 0 words
        
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] != Integer.MAX_VALUE && dict.contains(s.substring(j, i))) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }
        
        return dp[n] == Integer.MAX_VALUE ? -1 : dp[n];
    }

    /**
     * Returns the maximum length word used in segmentation.
     * 
     * @param s String to segment
     * @param wordDict Dictionary
     * @return Maximum word length used, or -1 if not possible
     */
    public static int maxWordLengthInSegmentation(String s, List<String> wordDict) {
        if (s == null || s.isEmpty()) return 0;
        if (wordDict == null || wordDict.isEmpty()) return -1;
        
        Set<String> dict = new HashSet<>(wordDict);
        int n = s.length();
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] != -1 && dict.contains(s.substring(j, i))) {
                    int wordLen = i - j;
                    dp[i] = Math.max(dp[i], Math.max(dp[j], wordLen));
                }
            }
        }
        
        return dp[n];
    }

    /**
     * Check if dictionary can be used to form concatenated words.
     * A word is concatenated if it can be formed by concatenating
     * two or more shorter dictionary words.
     */
    public static List<String> findAllConcatenatedWords(List<String> words) {
        List<String> result = new ArrayList<>();
        Set<String> dict = new HashSet<>(words);
        
        for (String word : words) {
            if (canFormConcatenatedWord(word, dict)) {
                result.add(word);
            }
        }
        
        return result;
    }
    
    private static boolean canFormConcatenatedWord(String word, Set<String> dict) {
        // Temporarily remove the word from dictionary to prevent self-matching
        dict.remove(word);
        
        // Check if word can be formed by other dictionary words
        boolean canForm = wordBreakDP(word, new ArrayList<>(dict));
        
        // Restore the word
        dict.add(word);
        
        return canForm;
    }

    /**
     * Print all segmentations in readable format.
     */
    public static void printSegmentations(String s, List<String> wordDict) {
        List<String> segmentations = wordBreakAll(s, wordDict);
        
        System.out.println("Input: \"" + s + "\"");
        System.out.println("Dictionary: " + wordDict);
        System.out.println("Number of segmentations: " + segmentations.size());
        
        if (segmentations.isEmpty()) {
            System.out.println("No valid segmentations found.");
        } else {
            System.out.println("All segmentations:");
            for (int i = 0; i < segmentations.size(); i++) {
                System.out.println("  " + (i + 1) + ": \"" + segmentations.get(i) + "\"");
            }
        }
    }

    /**
     * Comprehensive test driver.
     */
    public static void main(String[] args) {
        System.out.println("=== WORD BREAK DEMO ===\n");
        
        testBasicCases();
        testAllSegmentations();
        testConcatenatedWords();
        testPerformance();
        testEdgeCases();
        testAdvancedMetrics();
    }
    
    private static void testBasicCases() {
        System.out.println("1. BASIC TEST CASES");
        
        // Test case 1: Simple segmentation
        String s1 = "leetcode";
        List<String> dict1 = Arrays.asList("leet", "code");
        System.out.println("\nTest 1: s = \"" + s1 + "\", dict = " + dict1);
        System.out.println("Memoized recursion: " + wordBreak(s1, dict1));
        System.out.println("Bottom-up DP: " + wordBreakDP(s1, dict1));
        System.out.println("BFS: " + wordBreakBFS(s1, dict1));
        System.out.println("Optimized: " + wordBreakOptimized(s1, dict1));
        
        // Test case 2: Multiple possibilities
        String s2 = "catsanddog";
        List<String> dict2 = Arrays.asList("cat", "cats", "and", "sand", "dog");
        System.out.println("\nTest 2: s = \"" + s2 + "\", dict = " + dict2);
        System.out.println("Can be segmented: " + wordBreak(s2, dict2));
        
        // Test case 3: Impossible case
        String s3 = "applepenapple";
        List<String> dict3 = Arrays.asList("apple", "pena"); // Missing "pen"
        System.out.println("\nTest 3: s = \"" + s3 + "\", dict = " + dict3);
        System.out.println("Can be segmented: " + wordBreak(s3, dict3));
        
        // Test case 4: Empty string
        String s4 = "";
        List<String> dict4 = Arrays.asList("a", "b");
        System.out.println("\nTest 4: Empty string, dict = " + dict4);
        System.out.println("Can be segmented: " + wordBreak(s4, dict4));
    }
    
    private static void testAllSegmentations() {
        System.out.println("\n\n2. ALL POSSIBLE SEGMENTATIONS");
        
        String s = "catsanddog";
        List<String> dict = Arrays.asList("cat", "cats", "and", "sand", "dog");
        
        printSegmentations(s, dict);
        
        // Test with multiple segmentations
        String s2 = "pineapplepenapple";
        List<String> dict2 = Arrays.asList("apple", "pen", "applepen", "pine", "pineapple");
        System.out.println("\n--- Multiple segmentations example ---");
        printSegmentations(s2, dict2);
    }
    
    private static void testConcatenatedWords() {
        System.out.println("\n\n3. CONCATENATED WORDS");
        
        List<String> words = Arrays.asList(
            "cat", "cats", "catsdogcats", "dog", "dogcatsdog", 
            "hippopotamuses", "rat", "ratcatdogcat"
        );
        
        List<String> concatenated = findAllConcatenatedWords(words);
        System.out.println("All words: " + words);
        System.out.println("Concatenated words: " + concatenated);
    }
    
    private static void testPerformance() {
        System.out.println("\n\n4. PERFORMANCE COMPARISON");
        
        // Create a long string and large dictionary
        StringBuilder sb = new StringBuilder();
        List<String> dict = new ArrayList<>();
        
        // Build dictionary with common words
        for (int i = 0; i < 1000; i++) {
            String word = "word" + i;
            dict.add(word);
            sb.append(word);
        }
        
        String s = sb.toString();
        
        System.out.println("Testing with string length " + s.length() + 
                         " and dictionary size " + dict.size());
        
        // Test different approaches
        long start = System.nanoTime();
        boolean result1 = wordBreakDP(s, dict);
        long time1 = System.nanoTime() - start;
        
        start = System.nanoTime();
        boolean result2 = wordBreakOptimized(s, dict);
        long time2 = System.nanoTime() - start;
        
        System.out.println("DP approach: " + result1 + " (" + 
                         (time1/1000000.0) + " ms)");
        System.out.println("Optimized: " + result2 + " (" + 
                         (time2/1000000.0) + " ms)");
    }
    
    private static void testEdgeCases() {
        System.out.println("\n\n5. EDGE CASES");
        
        // Case 1: Single character
        String s1 = "a";
        List<String> dict1 = Arrays.asList("a");
        System.out.println("Single character 'a': " + wordBreak(s1, dict1));
        
        // Case 2: Repeated characters
        String s2 = "aaaaaa";
        List<String> dict2 = Arrays.asList("a", "aa", "aaa");
        System.out.println("Repeated 'a's: " + wordBreak(s2, dict2));
        
        // Case 3: Overlapping words
        String s3 = "aaaaab";
        List<String> dict3 = Arrays.asList("a", "aa", "aaa", "aaaa", "b");
        System.out.println("Overlapping patterns: " + wordBreak(s3, dict3));
        
        // Case 4: Dictionary contains entire string
        String s4 = "helloworld";
        List<String> dict4 = Arrays.asList("hello", "world", "helloworld");
        System.out.println("Dictionary contains entire string: " + 
                         wordBreak(s4, dict4));
    }
    
    private static void testAdvancedMetrics() {
        System.out.println("\n\n6. ADVANCED METRICS");
        
        String s = "catsanddog";
        List<String> dict = Arrays.asList("cat", "cats", "and", "sand", "dog");
        
        int minSegments = minWordBreakSegments(s, dict);
        int maxWordLength = maxWordLengthInSegmentation(s, dict);
        
        System.out.println("String: \"" + s + "\"");
        System.out.println("Dictionary: " + dict);
        System.out.println("Minimum segments: " + 
                         (minSegments == -1 ? "Not possible" : minSegments));
        System.out.println("Maximum word length in segmentation: " + 
                         (maxWordLength == -1 ? "Not possible" : maxWordLength));
        
        // Test with impossible string
        String s2 = "xyzabc";
        List<String> dict2 = Arrays.asList("abc", "def");
        int min2 = minWordBreakSegments(s2, dict2);
        System.out.println("\nImpossible string \"" + s2 + "\": " + 
                         (min2 == -1 ? "Not segmentable" : "Segmentable"));
    }
    
    /**
     * Trie-based solution for more efficient dictionary lookups.
     * Useful when dictionary has many words with common prefixes.
     */
    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isWord = false;
    }
    
    public static boolean wordBreakWithTrie(String s, List<String> wordDict) {
        if (s == null || s.isEmpty()) return true;
        
        // Build Trie
        TrieNode root = new TrieNode();
        for (String word : wordDict) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) {
                    node.children[idx] = new TrieNode();
                }
                node = node.children[idx];
            }
            node.isWord = true;
        }
        
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        
        for (int i = 0; i < n; i++) {
            if (!dp[i]) continue;
            
            // Traverse Trie from current position
            TrieNode node = root;
            for (int j = i; j < n; j++) {
                char c = s.charAt(j);
                int idx = c - 'a';
                if (node.children[idx] == null) {
                    break; // No words with this prefix
                }
                
                node = node.children[idx];
                if (node.isWord) {
                    dp[j + 1] = true;
                }
            }
        }
        
        return dp[n];
    }
}