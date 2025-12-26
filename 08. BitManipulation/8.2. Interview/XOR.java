public class XOR {

    public static int xorSubarrays(int[] nums) {
        int res = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int freq = (i + 1) * (n - i);
            if ((freq & 1) == 1) {
                res ^= nums[i];
            }
        }
        return res;
    }
    public static void main(String[] args) {
        
    }
}
