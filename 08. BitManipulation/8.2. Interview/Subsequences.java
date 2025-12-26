import java.util.ArrayList;
import java.util.List;

public class Subsequences {

    public static List<List<Integer>> allSubsequences(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(0, nums, new ArrayList<>(), res);
        return res;
    }

    private static void backtrack(int idx, int[] nums, List<Integer> curr, List<List<Integer>> res) {
        if (idx == nums.length) {
            res.add(new ArrayList<>(curr));
            return;
        }
        curr.add(nums[idx]);
        backtrack(idx + 1, nums, curr, res);
        curr.remove(curr.size() - 1);
        backtrack(idx + 1, nums, curr, res);
    }
    public static void main(String[] args) {
        
    }
}
