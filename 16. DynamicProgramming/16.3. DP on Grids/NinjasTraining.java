

public class NinjasTraining {

    // Space-optimized DP as in space_pro_max
    public static int ninjaTraining(int n, int[][] points) {
        int[] prev = new int[4];
        prev[0] = Math.max(points[0][1], points[0][2]);
        prev[1] = Math.max(points[0][0], points[0][2]);
        prev[2] = Math.max(points[0][0], points[0][1]);
        prev[3] = Math.max(points[0][0], Math.max(points[0][1], points[0][2]));

        for (int day = 1; day < n; day++) {
            int[] curr = new int[4];
            for (int last = 0; last < 4; last++) {
                int best = 0;
                for (int task = 0; task < 3; task++) {
                    if (task == last) continue;
                    int point = points[day][task] + prev[task];
                    if (point > best) best = point;
                }
                curr[last] = best;
            }
            prev = curr;
        }
        return prev[3];
    }

    public static void main(String[] args) {
        int[][] points = {
                {10, 40, 70},
                {20, 50, 80},
                {30, 60, 90}
        };
        System.out.println("Max points: " + ninjaTraining(points.length, points));
    }
}
