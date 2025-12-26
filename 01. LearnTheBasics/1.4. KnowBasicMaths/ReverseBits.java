// Problem: Reverse Digits of an Integer
// Idea: Build the reversed number by taking last digit each time (n % 10)
public class ReverseBits {

    public static int reverseInt(int n) {
        int rev = 0;
        while (n != 0) {
            rev = rev * 10 + n % 10;
            n /= 10;
        }
        return rev;
    }

    public static void main(String[] args) {
        int n = 1234;
        int rev = reverseInt(n);
        System.out.println("Reverse of " + n + " = " + rev);
    }
}
