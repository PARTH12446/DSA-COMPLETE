// Problem: Print a string N times using recursion
// Idea: Print once, then recurse with n-1 until n reaches 0
public class PrintNTimes {

    public static void printNTimes(String s, int n) {
        if (n == 0) return;
        System.out.print(s);
        printNTimes(s, n - 1);
    }

    public static void main(String[] args) {
        String s = "Hello ";
        int n = 3;
        printNTimes(s, n);
        System.out.println();
    }
}
