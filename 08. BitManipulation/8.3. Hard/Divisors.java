import java.util.ArrayList;
import java.util.List;

public class Divisors {

    public static List<Integer> divisors(int n) {
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
        
    }
}
