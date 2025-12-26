class MissingNumberBit {

    public static int missingNumber(int[] nums) {
        int xor = 0;
        int n = nums.length;
        for (int i = 0; i <= n; i++) xor ^= i;
        for (int v : nums) xor ^= v;
        return xor;
    }
    public static void main(String[] args) {
        
    }
}
