import java.util.*;

public class Prims {

    // Prim's algorithm using a min-heap on (weight, node)
    public static int spanningTree(int V, List<List<int[]>> adj) {
        boolean[] vis = new boolean[V];
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[]{0, 0}); // weight, node
        int totalWeight = 0;

        int visitedCount = 0;
        while (visitedCount < V && !pq.isEmpty()) {
            int[] cur = pq.poll();
            int w = cur[0], u = cur[1];
            if (vis[u]) continue;
            vis[u] = true;
            visitedCount++;
            totalWeight += w;

            for (int[] edge : adj.get(u)) {
                int v = edge[0], wt = edge[1];
                if (!vis[v]) {
                    pq.offer(new int[]{wt, v});
                }
            }
        }
        return totalWeight;
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

        System.out.println("Prim MST weight: " + spanningTree(V, adj));
    }

    private static void addEdge(List<List<int[]>> adj, int u, int v, int w) {
        adj.get(u).add(new int[]{v, w});
        adj.get(v).add(new int[]{u, w});
    }
}
