import java.util.*;

public class FloydWarshal {

    // Floyd-Warshall all-pairs shortest paths with -1 for no edge
    public static void shortest_distance(int[][] matrix) {
        int n = matrix.length;
        int INF = 1_000_000_000;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == -1) matrix[i][j] = INF;
            }
            matrix[i][i] = 0;
        }
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrix[i][k] + matrix[k][j] < matrix[i][j]) {
                        matrix[i][j] = matrix[i][k] + matrix[k][j];
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == INF) matrix[i][j] = -1;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {0, 3, -1, 7},
                {-1, 0, 2, -1},
                {-1, -1, 0, 1},
                {-1, -1, -1, 0}
        };
        shortest_distance(matrix);
        System.out.println("Floyd-Warshall result:");
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}
