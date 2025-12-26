import java.util.ArrayList;
import java.util.List;

public class Sieve {

    public static List<Integer> sieve(int n) {
        List<Integer> primes = new ArrayList<>();
        if (n < 2) return primes;
        boolean[] isPrime = new boolean[n + 1];
        for (int i = 2; i <= n; i++) isPrime[i] = true;
        for (int p = 2; p * p <= n; p++) {
            if (isPrime[p]) {
                for (int j = p * p; j <= n; j += p) isPrime[j] = false;
            }
        }
        for (int i = 2; i <= n; i++) if (isPrime[i]) primes.add(i);
        return primes;
    }
    public static void main(String[] args) {
        
    }
}
