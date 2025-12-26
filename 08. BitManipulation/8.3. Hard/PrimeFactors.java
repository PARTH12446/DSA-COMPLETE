import java.util.ArrayList;
import java.util.List;

public class PrimeFactors {

    public static List<Integer> primeFactors(int n) {
        List<Integer> res = new ArrayList<>();
        for (int p = 2; p * p <= n; p++) {
            while (n % p == 0) {
                res.add(p);
                n /= p;
            }
        }
        if (n > 1) res.add(n);
        return res;
    }
    public static void main(String[] args) {
        
    }
}
