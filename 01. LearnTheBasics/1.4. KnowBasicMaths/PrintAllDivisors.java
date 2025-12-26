import java.util.ArrayList;
import java.util.List;

// Problem: Print / list all divisors of a number
// Idea: Iterate i from 1 to sqrt(n); for each divisor i add both i and n/i
public class PrintAllDivisors {

    public static List<Integer> getDivisors(int n) {
        List<Integer> res = new ArrayList<>();
        for (int i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                res.add(i);
                if (i != n / i) res.add(n / i);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int n = 36;
        List<Integer> divisors = getDivisors(n);
        System.out.println("Divisors of " + n + " = " + divisors);
    }
}
