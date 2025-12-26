// Problem: Compute factorial of n using recursion
// Idea: n! = n * (n-1)! with base case 0! = 1 and 1! = 1
public class FactorialLessThanN {

    public static long factorial(int n) {
        if (n <= 1) return 1L;
        return n * factorial(n - 1);
    }

    public static void main(String[] args) {
        int n = 5;
        long fact = factorial(n);
        System.out.println("Factorial of " + n + " = " + fact);
    }
}
