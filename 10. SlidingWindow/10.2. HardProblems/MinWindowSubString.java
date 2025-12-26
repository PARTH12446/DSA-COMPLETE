// Problem: Minimum Window Substring (LeetCode 76)
// Technique: Variable-size sliding window with character frequency tracking
// Time Complexity: O(n) where n = s.length()
// Space Complexity: O(1) - fixed 256-element array
public class MinWindowSubString {
    public String minWindow(String s, String t) {
        // Edge case: empty strings
        if (s.length() == 0 || t.length() == 0) return "";
        
        // ASCII frequency array (256 covers extended ASCII)
        // POSITIVE values: characters needed from string t
        // ZERO: character balanced (present exactly as needed in current window)
        // NEGATIVE values: character in excess in current window
        int[] need = new int[256];
        
        // Initialize need array with character frequencies from t
        // Example: t = "ABC" → need['A'] = 1, need['B'] = 1, need['C'] = 1
        for (char c : t.toCharArray()) need[c]++;
        
        // Counter for how many characters from t are still needed
        // Initially equals length of t, becomes 0 when window contains all characters
        int required = t.length();
        
        // Window pointers and result tracking
        int left = 0;           // Left pointer of sliding window
        int minLen = Integer.MAX_VALUE;  // Length of minimum window found
        int start = 0;          // Starting index of minimum window
        
        // Expand window by moving right pointer
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            
            // If this character is needed (positive in need array)
            // Decrement required counter because we're adding a needed character
            if (need[c] > 0) required--;
            
            // Always decrement need count for this character
            // This tracks total characters in current window:
            // - For characters in t: counts how many we have vs need
            // - For characters not in t: becomes negative (excess)
            need[c]--;
            
            // When required == 0, we have a valid window containing all characters from t
            // Now contract from left to find minimum valid window
            while (required == 0) {
                // Update minimum window if current is smaller
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    start = left;
                }
                
                // Remove left character from window
                char leftChar = s.charAt(left++);
                
                // Restore need count for removed character
                need[leftChar]++;
                
                // If after restoration, need[leftChar] becomes positive,
                // it means we've removed a character that was needed
                // So we need to find another one (increment required)
                if (need[leftChar] > 0) required++;
            }
        }
        
        // Return result or empty string if no valid window found
        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }

    public static void main(String[] args) {
        MinWindowSubString solver = new MinWindowSubString();
        String s = "ADOBECODEBANC";
        String t = "ABC";
        String ans = solver.minWindow(s, t);
        System.out.println("Minimum window substring containing all characters of t = " + ans);
        // Expected: "BANC"
        
        // Additional test cases:
        // s = "a", t = "a" → "a"
        // s = "a", t = "aa" → ""
        // s = "ADOBECODEBANC", t = "ABC" → "BANC"
        // s = "a", t = "b" → ""
    }
}