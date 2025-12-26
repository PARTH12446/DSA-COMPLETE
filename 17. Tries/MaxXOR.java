

public class MaxXOR {

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

    public static int findMaximumXOR(int[] nums) {
        Trie trie = new Trie();
        for (int num : nums) trie.insert(num);
        int ans = 0;
        for (int num : nums) ans = Math.max(ans, trie.getMax(num));
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {3,10,5,25,2,8};
        System.out.println("Maximum XOR: " + findMaximumXOR(nums));
    }
}
