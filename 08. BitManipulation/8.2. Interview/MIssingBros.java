public class MIssingBros {

    // find two repeating/missing numbers using xor partition (placeholder logic depending on original problem)
    public static int[] twoSingleNumbers(int[] nums) {
        int xor = 0;
        for (int v : nums) xor ^= v;
        int setBit = xor & -xor;
        int a = 0, b = 0;
        for (int v : nums) {
            if ((v & setBit) != 0) a ^= v; else b ^= v;
        }
        return new int[]{a, b};
    }
    public static void main(String[] args) {
        
    }
}
