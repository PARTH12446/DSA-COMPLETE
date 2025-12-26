// Problem: Count Digits of a Number
// Idea: Repeatedly divide by 10 and count how many times until n becomes 0
public class CountDigits {

    public static int countDigits(int n) {
        if (n == 0) return 1;
        n = Math.abs(n);
        int count = 0;
        while (n > 0) {
            n /= 10;
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        int n = 12345;
        int digits = countDigits(n);
        System.out.println("Number of digits in " + n + " = " + digits);
    }
}
