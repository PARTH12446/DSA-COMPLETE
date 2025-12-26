import java.util.*;

public class CourseScheduleLeet2 {

    // Return one valid course order, or empty array if impossible
    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) adj.add(new ArrayList<>());
        int[] indeg = new int[numCourses];

        for (int[] p : prerequisites) {
            int src = p[0];
            int dst = p[1];
            indeg[dst]++;
            adj.get(src).add(dst);
        }

        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (indeg[i] == 0) q.offer(i);
        }

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int node = q.poll();
            order.add(node);
            for (int nei : adj.get(node)) {
                indeg[nei]--;
                if (indeg[nei] == 0) q.offer(nei);
            }
        }

        if (order.size() != numCourses) return new int[0];
        // Reverse like Python code; order of valid topo is fine either way
        Collections.reverse(order);
        int[] res = new int[numCourses];
        for (int i = 0; i < numCourses; i++) res[i] = order.get(i);
        return res;
    }

    public static void main(String[] args) {
        int numCourses = 4;
        int[][] prereq = {{1,0},{2,0},{3,1},{3,2}};
        System.out.println("Course order: " + Arrays.toString(findOrder(numCourses, prereq)));
    }
}
