import java.util.*;

public class ConnectedProvinces {

    // DFS on adjacency matrix (isConnected) to count provinces
    public static int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int count = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                count++;
                dfs(i, isConnected, visited);
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
        int[][] isConnected = {
                {1, 1, 0},
                {1, 1, 0},
                {0, 0, 1}
        };
        System.out.println("Number of provinces: " + findCircleNum(isConnected));
    }
}
