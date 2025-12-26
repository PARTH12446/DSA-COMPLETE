

public class UnionFind {

    static class DSU {
        int[] parent;
        int[] rank;
        int[] size;

        DSU(int n) {
            parent = new int[n + 1];
            rank = new int[n + 1];
            size = new int[n + 1];
            for (int i = 0; i <= n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int x) {
            if (x != parent[x]) parent[x] = find(parent[x]);
            return parent[x];
        }

        void unionBySize(int u, int v) {
            int pu = find(u);
            int pv = find(v);
            if (pu == pv) return;
            if (size[pu] < size[pv]) {
                parent[pu] = pv;
                size[pv] += size[pu];
            } else {
                parent[pv] = pu;
                size[pu] += size[pv];
            }
        }

        void unionByRank(int u, int v) {
            int pu = find(u);
            int pv = find(v);
            if (pu == pv) return;
            if (rank[pu] < rank[pv]) {
                parent[pu] = pv;
            } else if (rank[pv] < rank[pu]) {
                parent[pv] = pu;
            } else {
                parent[pv] = pu;
                rank[pu]++;
            }
        }
    }

    public static void main(String[] args) {
        DSU dsu = new DSU(5);
        dsu.unionBySize(1, 2);
        dsu.unionByRank(3, 4);
        dsu.unionBySize(2, 3);
        System.out.println("Parent of 4: " + dsu.find(4));
    }
}
