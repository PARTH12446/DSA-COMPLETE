import java.util.*;

public class MinMultiplications {

    // BFS on state space of values mod 100000 using given multipliers
    public static int minimumMultiplications(int[] arr, int start, int end) {
        if (start == end) return 0;
        int mod = 100_000;
        int[] dist = new int[mod];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Deque<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{start, 0});
        dist[start] = 0;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int node = cur[0], steps = cur[1];
            for (int num : arr) {
                int next = (int) ((long) num * node % mod);
                if (steps + 1 < dist[next]) {
                    dist[next] = steps + 1;
                    if (next == end) return steps + 1;
                    q.offer(new int[]{next, steps + 1});
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {2,5,7};
        System.out.println("Min multiplications: " + minimumMultiplications(arr, 3, 30));
    }
}
