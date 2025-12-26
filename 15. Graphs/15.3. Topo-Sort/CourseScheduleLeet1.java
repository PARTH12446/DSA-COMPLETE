import java.util.*;

public class CourseScheduleLeet1 {

    // Can we finish all courses? (LeetCode Course Schedule I)
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
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

        int taken = 0;
        while (!q.isEmpty()) {
            int node = q.poll();
            taken++;
            for (int nei : adj.get(node)) {
                indeg[nei]--;
                if (indeg[nei] == 0) q.offer(nei);
            }
        }
        return taken == numCourses;
    }

    public static void main(String[] args) {
        int numCourses = 2;
        int[][] prereq = {{1, 0}}; // 0 -> 1
        System.out.println("Can finish courses? " + canFinish(numCourses, prereq));
    }
}
