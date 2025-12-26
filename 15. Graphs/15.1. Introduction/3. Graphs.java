// Who cares
import java.util.*;

class Graphs {

    // Count labeled undirected graphs on n vertices: 2^(n*(n-1)/2)
    public static long countGraphs(int n) {
        long edges = (long) n * (n - 1) / 2; // number of possible undirected edges
        // For small n this fits in a long; for large n the value will overflow.
        long result = 1L;
        for (long i = 0; i < edges; i++) {
            result <<= 1; // multiply by 2
        }
        return result;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter number of vertices n: ");
            int n = sc.nextInt();

            long totalGraphs = countGraphs(n);
            System.out.println("Number of labeled undirected graphs on " + n + " vertices: " + totalGraphs);

            // Build adjacency list of a simple example graph on n vertices (path 0-1-2-...)
            List<List<Integer>> adj = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                adj.add(new ArrayList<>());
            }
            for (int i = 0; i + 1 < n; i++) {
                adj.get(i).add(i + 1);
                adj.get(i + 1).add(i);
            }

            System.out.println("Example adjacency list for a path graph:");
            for (int i = 0; i < n; i++) {
                System.out.println(i + " -> " + adj.get(i));
            }
        }
    }
}