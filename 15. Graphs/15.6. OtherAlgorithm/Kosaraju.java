import java.util.*;

public class Kosaraju {

    // Kosaraju's algorithm: count strongly connected components in directed graph
    public static int kosaraju(List<List<Integer>> adj) {
        int n = adj.size();
        boolean[] vis = new boolean[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            if (!vis[i]) dfs1(i, adj, vis, stack);
        }

        List<List<Integer>> rev = new ArrayList<>();
        for (int i = 0; i < n; i++) rev.add(new ArrayList<>());
        for (int i = 0; i < n; i++) {
            for (int nei : adj.get(i)) {
                rev.get(nei).add(i);
            }
        }
        Arrays.fill(vis, false);

        int scc = 0;
        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (!vis[node]) {
                dfs2(node, rev, vis);
                scc++;
            }
        }
        return scc;
    }

    private static void dfs1(int node, List<List<Integer>> adj, boolean[] vis, Deque<Integer> stack) {
        vis[node] = true;
        for (int nei : adj.get(node)) {
            if (!vis[nei]) dfs1(nei, adj, vis, stack);
        }
        stack.push(node);
    }

    private static void dfs2(int node, List<List<Integer>> adj, boolean[] vis) {
        vis[node] = true;
        for (int nei : adj.get(node)) {
            if (!vis[nei]) dfs2(nei, adj, vis);
        }
    }

    public static void main(String[] args) {
        int n = 5;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        // Example with 3 SCCs
        adj.get(0).add(2);
        adj.get(2).add(1);
        adj.get(1).add(0);
        adj.get(0).add(3);
        adj.get(3).add(4);

        System.out.println("Number of SCCs: " + kosaraju(adj));
    }
}
