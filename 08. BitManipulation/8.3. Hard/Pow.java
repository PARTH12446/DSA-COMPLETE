class PowMod {

    public static long fastPow(long x, long n, long mod) {
        x %= mod;
        long res = 1;
        while (n > 0) {
            if ((n & 1) == 1) res = (res * x) % mod;
            x = (x * x) % mod;
            n >>= 1;
        }
        return res;
    }
    public static void main(String[] args) {
        
    }
}
