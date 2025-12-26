// Problem: Sum of first N natural numbers using recursion
// Idea: sum(n) = n + sum(n-1) with base case sum(0) = 0
public class SumOfFirstNNumbers {

    public static int sum(int n) {
        if (n == 0) return 0;
        return n + sum(n - 1);
    }

    public static void main(String[] args) {
        int n = 5;
        int ans = sum(n);
        System.out.println("Sum of first " + n + " numbers = " + ans);
    }
}
