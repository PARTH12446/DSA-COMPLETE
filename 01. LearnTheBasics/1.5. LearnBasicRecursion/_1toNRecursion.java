// Problem: Print numbers from 1 to N using recursion
// Idea: Recurse down to 1, then print on the way back (backtracking)
public class _1toNRecursion {

    public static void print1ToN(int n) {
        if (n == 0) return;
        print1ToN(n - 1);
        System.out.print(n + " ");
    }

    public static void main(String[] args) {
        int n = 5;
        print1ToN(n);
        System.out.println();
    }
}
