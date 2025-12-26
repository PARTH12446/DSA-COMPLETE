import java.util.*;

public class CompleteString {

    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean end = false;
    }

    static class Trie {
        TrieNode root = new TrieNode();

        void insert(String word) {
            TrieNode cur = root;
            for (char ch : word.toCharArray()) {
                cur.children.putIfAbsent(ch, new TrieNode());
                cur = cur.children.get(ch);
            }
            cur.end = true;
        }

        boolean hasAllPrefixes(String word) {
            TrieNode cur = root;
            for (char ch : word.toCharArray()) {
                TrieNode next = cur.children.get(ch);
                if (next == null || !next.end) return false;
                cur = next;
            }
            return true;
        }
    }

    // Returns the longest string such that all its prefixes are present
    public static String completeString(List<String> words) {
        Trie trie = new Trie();
        for (String w : words) trie.insert(w);
        String longest = "";
        for (String w : words) {
            if (trie.hasAllPrefixes(w)) {
                if (w.length() > longest.length() ||
                        (w.length() == longest.length() && w.compareTo(longest) < 0)) {
                    longest = w;
                }
            }
        }
        return longest.isEmpty() ? "None" : longest;
    }

    public static void main(String[] args) {
        List<String> words = Arrays.asList("n","ni","nin","ninj","ninja","ninga");
        System.out.println("Complete string: " + completeString(words));
    }
}
