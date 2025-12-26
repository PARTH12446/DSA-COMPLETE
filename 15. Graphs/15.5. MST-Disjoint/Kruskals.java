import java.util.*;

public class Kruskals {

    static class DSU {
        int[] parent;
        int[] rank;

        DSU(int n) {
            parent = new int[n + 1];
            rank = new int[n + 1];
            for (int i = 0; i <= n; i++) parent[i] = i;
        }

        int find(int x) {
            if (x != parent[x]) parent[x] = find(parent[x]);
            return parent[x];
        }

        void unionByRank(int u, int v) {
            int pu = find(u);
            int pv = find(v);
            if (pu == pv) return;
            if (rank[pu] < rank[pv]) {
                parent[pu] = pv;
            } else if (rank[pv] < rank[pu]) {
                parent[pv] = pu;
            } else {
                parent[pv] = pu;
                rank[pu]++;
            }
        }
    }

    // Kruskal's algorithm, adj as list of (v, w) for each u
    public static int spanningTree(int V, List<List<int[]>> adj) {
        DSU dsu = new DSU(V);
        List<int[]> edges = new ArrayList<>(); // (w, u, v)
        boolean[][] seen = new boolean[V][V];
        for (int u = 0; u < V; u++) {
            for (int[] e : adj.get(u)) {
                int v = e[0], w = e[1];
                if (!seen[u][v] && !seen[v][u]) {
                    edges.add(new int[]{w, u, v});
                    seen[u][v] = seen[v][u] = true;
                }
            }
        }
        edges.sort(Comparator.comparingInt(a -> a[0]));
        int weight = 0;
        int used = 0;
        for (int[] e : edges) {
            int w = e[0], u = e[1], v = e[2];
            if (dsu.find(u) != dsu.find(v)) {
                dsu.unionByRank(u, v);
                weight += w;
                used++;
                if (used == V - 1) break;
            }
        }
        return weight;
    }

    public static void main(String[] args) {
        int V = 4;
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());
        addEdge(adj, 0, 1, 10);
        addEdge(adj, 0, 2, 6);
        addEdge(adj, 0, 3, 5);
        addEdge(adj, 1, 3, 15);
        addEdge(adj, 2, 3, 4);

        System.out.println("Kruskal MST weight: " + spanningTree(V, adj));
    }

    private static void addEdge(List<List<int[]>> adj, int u, int v, int w) {
        adj.get(u).add(new int[]{v, w});
        adj.get(v).add(new int[]{u, w});
    }
}
