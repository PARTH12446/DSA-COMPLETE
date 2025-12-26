import java.util.*;

public class AlienDictionary {

    // Find alien dictionary order from sorted words and alphabet size k
    public static String findOrder(String[] dict, int k) {
        int n = dict.length;
        Map<Character, List<Character>> graph = new HashMap<>();
        for (int i = 0; i < k; i++) {
            graph.put((char) ('a' + i), new ArrayList<>());
        }

        for (int i = 0; i < n - 1; i++) {
            String w1 = dict[i];
            String w2 = dict[i + 1];
            int len = Math.min(w1.length(), w2.length());
            for (int j = 0; j < len; j++) {
                char c1 = w1.charAt(j);
                char c2 = w2.charAt(j);
                if (c1 != c2) {
                    graph.get(c1).add(c2);
                    break;
                }
            }
        }

        List<Character> stack = new ArrayList<>();
        Set<Character> visited = new HashSet<>();

        for (char c : graph.keySet()) {
            dfs(c, graph, visited, stack);
        }

        Collections.reverse(stack);
        StringBuilder sb = new StringBuilder();
        for (char c : stack) sb.append(c);
        return sb.toString();
    }

    private static void dfs(char c, Map<Character, List<Character>> graph,
                            Set<Character> visited, List<Character> stack) {
        if (visited.contains(c)) return;
        visited.add(c);
        for (char nei : graph.get(c)) {
            dfs(nei, graph, visited, stack);
        }
        stack.add(c);
    }

    public static void main(String[] args) {
        String[] dict = {"baa", "abcd", "abca", "cab", "cad"};
        int k = 4; // a, b, c, d
        System.out.println("Alien dictionary order: " + findOrder(dict, k));
    }
}
