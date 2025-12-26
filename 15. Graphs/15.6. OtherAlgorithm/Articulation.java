import java.util.*;

public class Articulation {

    // Find articulation points in an undirected graph using Tarjan-like algorithm
    public static List<Integer> articulationPoints(int n, List<List<Integer>> adj) {
        int[] tin = new int[n];
        int[] low = new int[n];
        boolean[] vis = new boolean[n];
        boolean[] isArt = new boolean[n];
        int[] timer = {0};

        dfs(0, -1, adj, vis, tin, low, timer, isArt);

        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (isArt[i]) ans.add(i);
        }
        if (ans.isEmpty()) ans.add(-1);
        Collections.sort(ans);
        return ans;
    }

    private static void dfs(int node, int parent, List<List<Integer>> adj,
                            boolean[] vis, int[] tin, int[] low, int[] timer,
                            boolean[] isArt) {
        vis[node] = true;
        tin[node] = low[node] = timer[0]++;
        int children = 0;

        for (int nei : adj.get(node)) {
            if (nei == parent) continue;
            if (!vis[nei]) {
                children++;
                dfs(nei, node, adj, vis, tin, low, timer, isArt);
                low[node] = Math.min(low[node], low[nei]);
                if (parent != -1 && low[nei] >= tin[node]) {
                    isArt[node] = true;
                }
            } else {
                low[node] = Math.min(low[node], tin[nei]);
            }
        }
        if (parent == -1 && children > 1) {
            isArt[node] = true;
        }
    }

    public static void main(String[] args) {
        int n = 5;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        addEdge(adj, 0, 1);
        addEdge(adj, 1, 2);
        addEdge(adj, 2, 0);
        addEdge(adj, 1, 3);
        addEdge(adj, 3, 4);

        System.out.println("Articulation points: " + articulationPoints(n, adj));
    }

    private static void addEdge(List<List<Integer>> adj, int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }
}
