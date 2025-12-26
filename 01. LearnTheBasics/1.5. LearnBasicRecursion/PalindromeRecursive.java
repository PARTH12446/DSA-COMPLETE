// Problem: Check if a string is a palindrome using recursion
// Idea: Compare characters from both ends and move pointers inward
public class PalindromeRecursive {

    public static boolean isPalindrome(String s) {
        return helper(s, 0, s.length() - 1);
    }

    private static boolean helper(String s, int l, int r) {
        if (l >= r) return true;
        if (s.charAt(l) != s.charAt(r)) return false;
        return helper(s, l + 1, r - 1);
    }

    public static void main(String[] args) {
        String s = "racecar";
        boolean ans = isPalindrome(s);
        System.out.println(s + " is palindrome? " + ans);
    }
}
