

public class UnionByRank {

    static class DSU {
        int[] parent;
        int[] rank;

        DSU(int n) {
            parent = new int[n + 1];
            rank = new int[n + 1];
            for (int i = 0; i <= n; i++) parent[i] = i;
        }

        int find(int x) {
            if (x != parent[x]) parent[x] = find(parent[x]);
            return parent[x];
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
        dsu.unionByRank(1, 2);
        dsu.unionByRank(2, 3);
        System.out.println("Find(1) == Find(3)? " + (dsu.find(1) == dsu.find(3)));
        System.out.println("Find(4) == Find(5)? " + (dsu.find(4) == dsu.find(5)));
    }
}
