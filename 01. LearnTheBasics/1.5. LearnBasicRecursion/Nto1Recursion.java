// Problem: Print numbers from N down to 1 using recursion
// Idea: Print current n then recurse to n-1
public class Nto1Recursion {

    public static void printNTo1(int n) {
        if (n == 0) return;
        System.out.print(n + " ");
        printNTo1(n - 1);
    }

    public static void main(String[] args) {
        int n = 5;
        printNTo1(n);
        System.out.println();
    }
}
