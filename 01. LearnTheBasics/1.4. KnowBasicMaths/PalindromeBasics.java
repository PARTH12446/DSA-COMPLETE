// Problem: Check if an integer is a palindrome (reads same forwards and backwards)
// Idea: Reverse the number and compare with original (negative numbers are not palindromes)
public class PalindromeBasics {

    public static boolean isPalindrome(int n) {
        if (n < 0) return false;
        return n == ReverseBits.reverseInt(n);
    }

    public static void main(String[] args) {
        int n = 1221;
        boolean ans = isPalindrome(n);
        System.out.println(n + " is palindrome? " + ans);
    }
}
