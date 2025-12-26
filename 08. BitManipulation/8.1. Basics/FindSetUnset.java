public class FindSetUnset {

    public static boolean isBitSet(int n, int k) {
        // k is 0-based index
        return (n & (1 << k)) != 0;
    }
    public static void main(String[] args) {
        
    }
}
