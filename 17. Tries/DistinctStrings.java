import java.util.*;

public class DistinctStrings {

    // Count distinct substrings using a trie
    static class Trie {
        Map<Character, Trie> children = new HashMap<>();
    }

    public static int countDistinctSubstrings(String s) {
        int cnt = 0;
        Trie root = new Trie();
        for (int i = 0; i < s.length(); i++) {
            Trie node = root;
            for (int j = i; j < s.length(); j++) {
                char ch = s.charAt(j);
                Trie next = node.children.get(ch);
                if (next == null) {
                    next = new Trie();
                    node.children.put(ch, next);
                    cnt++;
                }
                node = next;
            }
        }
        return cnt + 1; // including empty substring
    }

    public static void main(String[] args) {
        String s = "abab";
        System.out.println("Distinct substrings count: " + countDistinctSubstrings(s));
    }
}
