public class Swap {

    public static void swapUsingXor(int[] a, int i, int j) {
        if (i == j) return;
        a[i] ^= a[j];
        a[j] ^= a[i];
        a[i] ^= a[j];
    }
    public static void main(String[] args) {
        
    }
}
