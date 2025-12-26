import java.util.*;

public class ConnectProvinceGFG {

    // Same as ConnectedProvinces but with signature similar to GFG: adj matrix and V
    public static int numProvinces(int[][] adj, int V) {
        boolean[] visited = new boolean[V];
        int count = 0;
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                count++;
                dfs(i, adj, visited);
            }
        }
        return count;
    }

    private static void dfs(int node, int[][] isConnected, boolean[] visited) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            int curr = stack.pop();
            if (visited[curr]) continue;
            visited[curr] = true;
            for (int nei = 0; nei < isConnected.length; nei++) {
                if (isConnected[curr][nei] == 1 && curr != nei && !visited[nei]) {
                    stack.push(nei);
                }
            }
        }
    }

    public static void main(String[] args) {
        int V = 3;
        int[][] adj = {
                {1, 1, 0},
                {1, 1, 0},
                {0, 0, 1}
        };
        System.out.println("Number of provinces: " + numProvinces(adj, V));
    }
}
