public class FlipBits {

    // count max consecutive 1s after flipping at most one 0 to 1
    public static int maxConsecutiveOnesAfterFlip(int[] nums) {
        int left = 0, zeros = 0, best = 0;
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) zeros++;
            while (zeros > 1) {
                if (nums[left] == 0) zeros--;
                left++;
            }
            best = Math.max(best, right - left + 1);
        }
        return best;
    }
    public static void main(String[] args) {
        
    }
}
