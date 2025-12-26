import java.util.*;

public class CycleInDirected {

    // Detect cycle in a directed graph using DFS with color states
    // color: 0 = unvisited, 1 = visiting, 2 = visited
    public static boolean hasCycleDirected(List<List<Integer>> graph) {
        int v = graph.size();
        int[] color = new int[v];
        for (int i = 0; i < v; i++) {
            if (color[i] == 0) {
                if (!dfs(graph, i, color)) return false;
            }
        }
        return true;
    }

    private static boolean dfs(List<List<Integer>> graph, int src, int[] color) {
        color[src] = 1; // visiting
        for (int nei : graph.get(src)) {
            if (color[nei] == 0) {
                if (!dfs(graph, nei, color)) return false;
            } else if (color[nei] == 1) {
                return false; // found back edge
            }
        }
        color[src] = 2; // done
        return true;
    }

    public static void main(String[] args) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < 4; i++) graph.add(new ArrayList<>());
        // 0 -> 1 -> 2 -> 0 forms a cycle
        graph.get(0).add(1);
        graph.get(1).add(2);
        graph.get(2).add(0);
        graph.get(2).add(3);

        System.out.println("Has cycle in directed graph? " + !hasCycleDirected(graph));
    }
}
