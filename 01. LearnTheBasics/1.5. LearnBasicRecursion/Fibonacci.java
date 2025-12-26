// Problem: Compute n-th Fibonacci number using recursion
// Idea: fib(n) = fib(n-1) + fib(n-2) with base cases fib(0)=0, fib(1)=1
public class Fibonacci {

    public static int fib(int n) {
        if (n <= 1) return n;
        return fib(n - 1) + fib(n - 2);
    }

    public static void main(String[] args) {
        int n = 6;
        int ans = fib(n);
        System.out.println("fib(" + n + ") = " + ans);
    }
}
