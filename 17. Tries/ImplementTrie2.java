import java.util.*;

public class ImplementTrie2 {

    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        int v = 0;   // words ending here
        int pv = 0;  // words passing through here
    }

    static class Trie {
        TrieNode root = new TrieNode();

        public void insert(String word) {
            TrieNode cur = root;
            for (char c : word.toCharArray()) {
                cur.children.putIfAbsent(c, new TrieNode());
                cur = cur.children.get(c);
                cur.pv++;
            }
            cur.v++;
        }

        public int countWordsEqualTo(String word) {
            TrieNode node = searchNode(word);
            return node == null ? 0 : node.v;
        }

        public int countWordsStartingWith(String prefix) {
            TrieNode node = searchNode(prefix);
            return node == null ? 0 : node.pv;
        }

        public void erase(String word) {
            TrieNode cur = root;
            Deque<Map.Entry<TrieNode, Character>> stack = new ArrayDeque<>();
            for (char c : word.toCharArray()) {
                TrieNode next = cur.children.get(c);
                if (next == null) return; // word not present
                stack.push(new AbstractMap.SimpleEntry<>(cur, c));
                cur = next;
            }
            cur.v--;
            while (!stack.isEmpty()) {
                Map.Entry<TrieNode, Character> e = stack.pop();
                TrieNode parent = e.getKey();
                char ch = e.getValue();
                TrieNode child = parent.children.get(ch);
                if (child == null) return;
                child.pv--;
                if (child.pv == 0 && child.v == 0) {
                    parent.children.remove(ch);
                }
            }
        }

        private TrieNode searchNode(String word) {
            TrieNode cur = root;
            for (char c : word.toCharArray()) {
                TrieNode next = cur.children.get(c);
                if (next == null) return null;
                cur = next;
            }
            return cur;
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        trie.insert("apple");
        trie.insert("app");
        System.out.println(trie.countWordsEqualTo("apple"));      // 2
        System.out.println(trie.countWordsStartingWith("app"));   // 3
        trie.erase("apple");
        System.out.println(trie.countWordsEqualTo("apple"));      // 1
    }
}
