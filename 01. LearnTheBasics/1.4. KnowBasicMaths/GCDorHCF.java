// Problem: Compute GCD / HCF of two numbers
// Idea: Use Euclidean algorithm: gcd(a, b) = gcd(b, a % b)
public class GCDorHCF {

    public static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }

    public static void main(String[] args) {
        int a = 36, b = 60;
        int g = gcd(a, b);
        System.out.println("GCD(" + a + ", " + b + ") = " + g);
    }
}
