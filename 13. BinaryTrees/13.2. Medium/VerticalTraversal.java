import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Vertical Order Traversal of a Binary Tree (LeetCode 987)
 * 
 * PROBLEM: Traverse binary tree vertically, column by column from left to right.
 * For each column, nodes should be sorted by row (top to bottom).
 * If multiple nodes have same (row, col), sort by value (ascending).
 * 
 * APPROACH: DFS with (col, row) tracking + sorting
 * TIME: O(n log n) - sorting each column list
 * SPACE: O(n) - storing all nodes in map
 */
public class VerticalTraversal {

    static class TreeNode {
        int val; 
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    // Helper class to store node with its row and value
    static class Pair {
        int row, val;
        Pair(int r, int v) { row = r; val = v; }
    }

    /**
     * Vertical order traversal with sorting
     * @param root - root of binary tree
     * @return List of columns, each column is list of values sorted by row then value
     * 
     * STRATEGY:
     * 1. DFS traversal tracking (col, row) for each node
     * 2. Store nodes in map: col → List of (row, value) pairs
     * 3. Sort columns using TreeMap (sorted by col)
     * 4. For each column list, sort by row (ascending), then value (ascending)
     * 5. Extract values from sorted pairs
     */
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        // Map: column → list of (row, value) pairs
        Map<Integer, List<Pair>> map = new HashMap<>();
        
        // DFS to populate map
        dfs(root, 0, 0, map);
        
        // Sort columns using TreeMap (natural ordering of keys)
        TreeMap<Integer, List<Pair>> sortedMap = new TreeMap<>(map);
        
        // Prepare result
        List<List<Integer>> result = new ArrayList<>();
        
        for (List<Pair> columnList : sortedMap.values()) {
            // Sort each column list by row, then by value
            Collections.sort(columnList, new Comparator<Pair>() {
                @Override
                public int compare(Pair a, Pair b) {
                    if (a.row == b.row) {
                        // Same row: sort by value
                        return Integer.compare(a.val, b.val);
                    }
                    // Different row: sort by row (top to bottom)
                    return Integer.compare(a.row, b.row);
                }
            });
            
            // Extract values from sorted pairs
            List<Integer> columnValues = new ArrayList<>();
            for (Pair p : columnList) {
                columnValues.add(p.val);
            }
            result.add(columnValues);
        }
        
        return result;
    }
    
    /**
     * DFS to populate map with (col, row, value) information
     * @param node - current node
     * @param col - horizontal distance (column)
     * @param row - vertical distance (row/depth)
     * @param map - map to store column lists
     */
    private void dfs(TreeNode node, int col, int row, Map<Integer, List<Pair>> map) {
        if (node == null) return;
        
        // Add current node to its column list
        map.computeIfAbsent(col, k -> new ArrayList<>())
           .add(new Pair(row, node.val));
        
        // Recursively traverse left and right subtrees
        dfs(node.left, col - 1, row + 1, map);   // Left: col-1, row+1
        dfs(node.right, col + 1, row + 1, map);  // Right: col+1, row+1
    }
    
    /**
     * Alternative: BFS approach
     * Ensures nodes are processed in level order (row order)
     */
    public List<List<Integer>> verticalTraversalBFS(TreeNode root) {
        if (root == null) return new ArrayList<>();
        
        // Map: column → list of (row, value) pairs
        Map<Integer, List<Pair>> map = new HashMap<>();
        
        // Queue for BFS: stores (node, col, row)
        Queue<NodeInfo> queue = new java.util.LinkedList<>();
        queue.offer(new NodeInfo(root, 0, 0));
        
        while (!queue.isEmpty()) {
            NodeInfo info = queue.poll();
            TreeNode node = info.node;
            int col = info.col;
            int row = info.row;
            
            // Add to map
            map.computeIfAbsent(col, k -> new ArrayList<>())
               .add(new Pair(row, node.val));
            
            // Add children
            if (node.left != null) {
                queue.offer(new NodeInfo(node.left, col - 1, row + 1));
            }
            if (node.right != null) {
                queue.offer(new NodeInfo(node.right, col + 1, row + 1));
            }
        }
        
        // Same sorting and result preparation as DFS
        TreeMap<Integer, List<Pair>> sortedMap = new TreeMap<>(map);
        List<List<Integer>> result = new ArrayList<>();
        
        for (List<Pair> list : sortedMap.values()) {
            Collections.sort(list, (a, b) -> {
                if (a.row == b.row) return Integer.compare(a.val, b.val);
                return Integer.compare(a.row, b.row);
            });
            
            List<Integer> column = new ArrayList<>();
            for (Pair p : list) column.add(p.val);
            result.add(column);
        }
        
        return result;
    }
    
    class NodeInfo {
        TreeNode node; int col, row;
        NodeInfo(TreeNode n, int c, int r) { node = n; col = c; row = r; }
    }
    
    /**
     * Simple vertical order (no sorting within column)
     * Variation: Just group by column, no sorting by row/value
     */
    public List<List<Integer>> verticalOrderSimple(TreeNode root) {
        if (root == null) return new ArrayList<>();
        
        Map<Integer, List<Integer>> map = new HashMap<>();
        Queue<NodeCol> queue = new java.util.LinkedList<>();
        queue.offer(new NodeCol(root, 0));
        
        while (!queue.isEmpty()) {
            NodeCol nc = queue.poll();
            map.computeIfAbsent(nc.col, k -> new ArrayList<>())
               .add(nc.node.val);
            
            if (nc.node.left != null) queue.offer(new NodeCol(nc.node.left, nc.col - 1));
            if (nc.node.right != null) queue.offer(new NodeCol(nc.node.right, nc.col + 1));
        }
        
        TreeMap<Integer, List<Integer>> sorted = new TreeMap<>(map);
        return new ArrayList<>(sorted.values());
    }
    
    class NodeCol {
        TreeNode node; int col;
        NodeCol(TreeNode n, int c) { node = n; col = c; }
    }

    /**
     * Visual Examples:
     * 
     * Example 1 (from LeetCode):
     *       3
     *      / \
     *     9   20
     *        /  \
     *       15   7
     * 
     * Node positions:
     * Node 3: (col=0, row=0, val=3)
     * Node 9: (col=-1, row=1, val=9)
     * Node 20: (col=1, row=1, val=20)
     * Node 15: (col=0, row=2, val=15)
     * Node 7: (col=2, row=2, val=7)
     * 
     * Map:
     * col -1: [(1,9)]
     * col  0: [(0,3), (2,15)]
     * col  1: [(1,20)]
     * col  2: [(2,7)]
     * 
     * After sorting columns and within columns:
     * col -1: [9]
     * col  0: [3,15]  (sorted by row: 0 then 2)
     * col  1: [20]
     * col  2: [7]
     * 
     * Result: [[9], [3,15], [20], [7]]
     * 
     * Example 2 (with same row,col positions):
     *       1
     *      / \
     *     2   3
     *    / \ / \
     *   4  5 6  7
     * 
     * Node positions:
     * Node 5 and 6 both at (col=0, row=2)
     * Need to sort by value when row,col same: 5 then 6
     */

    public static void main(String[] args) {
        VerticalTraversal v = new VerticalTraversal();
        
        // Example from problem
        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(9);
        root1.right = new TreeNode(20);
        root1.right.left = new TreeNode(15);
        root1.right.right = new TreeNode(7);
        
        System.out.println("Example 1 (DFS): " + v.verticalTraversal(root1));
        // Expected: [[9], [3,15], [20], [7]]
        
        System.out.println("Example 1 (BFS): " + v.verticalTraversalBFS(root1));
        // Expected: same
        
        System.out.println("Simple vertical: " + v.verticalOrderSimple(root1));
        // Expected: [[9], [3,15], [20], [7]] (but order within column may differ)
        
        // Test cases
        System.out.println("\n=== Test Cases ===");
        
        // Test 1: Empty tree
        System.out.println("Empty tree: " + v.verticalTraversal(null)); // []
        
        // Test 2: Single node
        TreeNode single = new TreeNode(1);
        System.out.println("Single node: " + v.verticalTraversal(single)); // [[1]]
        
        // Test 3: Tree with same column, different rows
        TreeNode tree1 = new TreeNode(1);
        tree1.left = new TreeNode(2);
        tree1.right = new TreeNode(3);
        System.out.println("Tree 1-2-3: " + v.verticalTraversal(tree1));
        // Expected: [[2], [1], [3]]
        
        // Test 4: Tree where sorting by value matters
        TreeNode tree2 = new TreeNode(3);
        tree2.left = new TreeNode(1);
        tree2.right = new TreeNode(4);
        tree2.left.right = new TreeNode(2);
        System.out.println("Tree with sorting: " + v.verticalTraversal(tree2));
        // Tree:    3
        //         / \
        //        1   4
        //         \
        //          2
        // Expected: [[1], [3,2], [4]]
        // Column 0: nodes 3(row0), 2(row2) → sort by row: [3,2]
        
        // Test 5: Complex tree from LeetCode example
        TreeNode complex = new TreeNode(1);
        complex.left = new TreeNode(2);
        complex.right = new TreeNode(3);
        complex.left.left = new TreeNode(4);
        complex.left.right = new TreeNode(5);
        complex.right.left = new TreeNode(6);
        complex.right.right = new TreeNode(7);
        System.out.println("Complex tree: " + v.verticalTraversal(complex));
        // Expected: [[4], [2], [1,5,6], [3], [7]]
        // Column -2: [4], -1: [2], 0: [1,5,6], 1: [3], 2: [7]
        // At column 0: nodes 1(row0), 5(row2), 6(row2) → 6 before 5 (sort by value)
    }
}

/**
 * KEY CONCEPTS:
 * 
 * 1. Column (Horizontal Distance):
 *    - Root: 0
 *    - Left child: parent col - 1
 *    - Right child: parent col + 1
 * 
 * 2. Row (Depth/Level):
 *    - Root: 0
 *    - Child: parent row + 1
 * 
 * 3. Sorting requirements (LeetCode 987):
 *    - Columns sorted left to right (col asc)
 *    - Within column: sort by row asc (top to bottom)
 *    - If same row: sort by value asc
 * 
 * 4. TreeMap vs HashMap:
 *    - TreeMap: columns automatically sorted
 *    - HashMap + TreeMap: more efficient
 */

/**
 * TIME & SPACE ANALYSIS:
 * 
 * Time Complexity: O(n log n)
 *   - DFS/BFS: O(n) to visit all nodes
 *   - Sorting each column list: O(k log k) where k = nodes in column
 *   - Worst case: all nodes in one column → O(n log n)
 * 
 * Space Complexity: O(n)
 *   - Map stores all nodes: O(n)
 *   - Recursion stack (DFS): O(h) where h = height
 *   - Queue (BFS): O(w) where w = max width
 */

/**
 * SORTING LOGIC DETAILS:
 * 
 * Comparator for Pair (row, value):
 * 1. First compare by row (ascending)
 *    - Smaller row = higher in tree = comes first
 * 2. If rows equal, compare by value (ascending)
 *    - Smaller value comes first
 * 
 * Example: Column with [(2,5), (1,3), (2,1)]
 * Sorted: [(1,3), (2,1), (2,5)]
 * Because: row 1 before row 2, and for row 2: 1 < 5
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Not sorting within columns:
 *    - Must sort by row then value
 * 
 * 2. Wrong sorting order:
 *    - Should be row asc, then value asc
 * 
 * 3. Using BFS without row tracking:
 *    - Need explicit row count in BFS
 * 
 * 4. Not handling same (row, col) case:
 *    - Multiple nodes can have same position
 * 
 * 5. Forgetting to sort columns:
 *    - Columns should be in left-to-right order
 */

/**
 * VARIATIONS:
 * 
 * 1. Simple vertical order (no sorting within column)
 * 2. Top view (only first node in each column)
 * 3. Bottom view (only last node in each column)
 * 4. Diagonal traversal
 * 5. Print tree with coordinates
 */

/**
 * PRACTICE EXERCISES:
 * 
 * 1. Implement BFS version
 * 2. Print tree with (row,col) coordinates
 * 3. Find most dense column (most nodes)
 * 4. Check if tree is symmetric using vertical traversal
 * 5. Vertical order for N-ary tree
 */