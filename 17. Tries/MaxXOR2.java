import java.util.*;

public class MaxXOR2 {

    static class Node {
        Node[] links = new Node[2];
    }

    static class Trie {
        Node root = new Node();

        void insert(int num) {
            Node node = root;
            for (int i = 31; i >= 0; i--) {
                int bit = (num >> i) & 1;
                if (node.links[bit] == null) node.links[bit] = new Node();
                node = node.links[bit];
            }
        }

        int getMax(int num) {
            Node node = root;
            int maxNum = 0;
            for (int i = 31; i >= 0; i--) {
                int bit = (num >> i) & 1;
                int toggled = 1 - bit;
                if (node.links[toggled] != null) {
                    maxNum |= (1 << i);
                    node = node.links[toggled];
                } else {
                    node = node.links[bit];
                }
            }
            return maxNum;
        }
    }

    // nums: array, queries: [x, m], maximize x ^ num with num <= m
    public static int[] maximizeXor(int[] nums, int[][] queries) {
        Arrays.sort(nums);
        int q = queries.length;
        int[][] queries2 = new int[q][3]; // [m, x, idx]
        for (int i = 0; i < q; i++) {
            queries2[i][0] = queries[i][1]; // m
            queries2[i][1] = queries[i][0]; // x
            queries2[i][2] = i;             // idx
        }
        Arrays.sort(queries2, Comparator.comparingInt(a -> a[0]));

        int[] ans = new int[q];
        Trie trie = new Trie();
        int i = 0, n = nums.length;
        for (int[] qu : queries2) {
            int m = qu[0], x = qu[1], idx = qu[2];
            while (i < n && nums[i] <= m) {
                trie.insert(nums[i]);
                i++;
            }
            if (i == 0) ans[idx] = -1;
            else ans[idx] = trie.getMax(x);
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {0,1,2,3,4};
        int[][] queries = {{3,1},{1,3},{5,6}}; // [x, m]
        int[] res = maximizeXor(nums, queries);
        System.out.println("Max XOR per query:");
        System.out.println(Arrays.toString(res));
    }
}
