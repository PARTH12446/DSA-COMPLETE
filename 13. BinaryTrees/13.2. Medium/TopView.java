import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * Top View of Binary Tree
 * 
 * PROBLEM: Print the top-most nodes at each horizontal distance
 * Horizontal Distance (HD): 
 *   - Root HD = 0
 *   - Left child HD = parent HD - 1
 *   - Right child HD = parent HD + 1
 * 
 * For each HD, we need the FIRST (top-most) node seen in BFS traversal
 * 
 * APPROACH: BFS (Level Order) with horizontal distance tracking
 * TIME: O(n) - each node visited once
 * SPACE: O(n) - queue and map storage
 */
public class TopView {

    static class TreeNode {
        int val; 
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    // Helper class to store node with its horizontal distance
    static class NodeCol {
        TreeNode node; 
        int col; // horizontal distance
        NodeCol(TreeNode n, int c) { node = n; col = c; }
    }

    /**
     * Get top view of binary tree
     * @param root - root of binary tree
     * @return List of top-most nodes from left to right
     * 
     * STRATEGY:
     * 1. Use BFS to traverse level by level
     * 2. Track horizontal distance (col) for each node
     * 3. Use TreeMap to store first seen node for each col
     *    - TreeMap keeps keys (cols) in sorted order
     *    - Only put if col not already in map (first occurrence)
     * 4. BFS ensures nodes at same HD are processed top to bottom
     * 5. Return map values from smallest to largest col (left to right)
     */
    public List<Integer> topView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        // TreeMap maintains keys in sorted order (smallest col to largest)
        Map<Integer, Integer> map = new TreeMap<>();
        Queue<NodeCol> queue = new LinkedList<>();
        
        // Start with root at horizontal distance 0
        queue.offer(new NodeCol(root, 0));
        
        // BFS traversal
        while (!queue.isEmpty()) {
            NodeCol current = queue.poll();
            TreeNode node = current.node;
            int col = current.col;
            
            // Only add if this column hasn't been seen before (first/top node)
            if (!map.containsKey(col)) {
                map.put(col, node.val);
            }
            
            // Add left child with col-1
            if (node.left != null) {
                queue.offer(new NodeCol(node.left, col - 1));
            }
            
            // Add right child with col+1
            if (node.right != null) {
                queue.offer(new NodeCol(node.right, col + 1));
            }
        }
        
        // TreeMap values are in sorted key order (left to right)
        for (int val : map.values()) {
            result.add(val);
        }
        
        return result;
    }
    
    /**
     * Alternative: Using HashMap and tracking min/max col
     * More efficient than TreeMap for large trees
     */
    public List<Integer> topViewOptimized(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Map<Integer, Integer> map = new java.util.HashMap<>();
        Queue<NodeCol> queue = new LinkedList<>();
        int minCol = 0, maxCol = 0;
        
        queue.offer(new NodeCol(root, 0));
        
        while (!queue.isEmpty()) {
            NodeCol current = queue.poll();
            TreeNode node = current.node;
            int col = current.col;
            
            // Only add if first occurrence for this column
            if (!map.containsKey(col)) {
                map.put(col, node.val);
            }
            
            // Track min and max column for iteration
            minCol = Math.min(minCol, col);
            maxCol = Math.max(maxCol, col);
            
            if (node.left != null) {
                queue.offer(new NodeCol(node.left, col - 1));
            }
            if (node.right != null) {
                queue.offer(new NodeCol(node.right, col + 1));
            }
        }
        
        // Retrieve values in column order using min/max
        for (int col = minCol; col <= maxCol; col++) {
            result.add(map.get(col));
        }
        
        return result;
    }
    
    /**
     * DFS approach (for comparison)
     * Less intuitive but works
     */
    public List<Integer> topViewDFS(TreeNode root) {
        Map<Integer, Pair> map = new TreeMap<>();
        dfs(root, 0, 0, map);
        
        List<Integer> result = new ArrayList<>();
        for (Pair p : map.values()) {
            result.add(p.value);
        }
        return result;
    }
    
    class Pair {
        int value;
        int depth; // track depth to choose shallower node
        Pair(int v, int d) { value = v; depth = d; }
    }
    
    private void dfs(TreeNode node, int col, int depth, Map<Integer, Pair> map) {
        if (node == null) return;
        
        // If this column not visited, or current node is shallower
        if (!map.containsKey(col) || depth < map.get(col).depth) {
            map.put(col, new Pair(node.val, depth));
        }
        
        dfs(node.left, col - 1, depth + 1, map);
        dfs(node.right, col + 1, depth + 1, map);
    }

    /**
     * Visual Example:
     * 
     * Tree:
     *           1
     *         /   \
     *        2     3
     *         \   
     *          4
     *           \
     *            5
     *             \
     *              6
     * 
     * Horizontal Distances:
     * HD -1: 2  (top-most at HD -1)
     * HD  0: 1  (top-most at HD 0)
     * HD  1: 3  (top-most at HD 1)
     * 
     * Top View: [2, 1, 3]
     * 
     * Explanation:
     * - At HD -1: Node 2 is top-most (depth 1)
     * - At HD 0: Node 1 is top-most (depth 0) 
     * - At HD 1: Node 3 is top-most (depth 1)
     * - Nodes 4,5,6 are at HD -1 but deeper than node 2, so not visible
     * 
     * BFS Order with HD:
     * (1,0) → map[0]=1
     * (2,-1) → map[-1]=2
     * (3,1) → map[1]=3
     * (4,-2) → map[-2]=4
     * (5,-3) → map[-3]=5
     * (6,-4) → map[-4]=6
     * 
     * Wait, this shows a different tree than the example.
     * Let me trace the actual example tree...
     * 
     * Given tree: 1, left=2, right=3, left.right=4, left.right.right=5, ...
     * This creates a right-skewed left subtree
     * 
     * Actually the example tree is:
     *       1
     *      / \
     *     2   3
     *      \
     *       4
     *        \
     *         5
     *          \
     *           6
     * 
     * Horizontal Distances:
     * Node 1: HD 0
     * Node 2: HD -1
     * Node 3: HD +1
     * Node 4: HD 0 (2.right has HD -1+1=0)
     * Node 5: HD +1
     * Node 6: HD +2
     * 
     * So top view:
     * HD -1: 2
     * HD 0: 1 (not 4, because 1 is shallower/top-most)
     * HD +1: 3 (not 5, because 3 is shallower)
     * HD +2: 6
     * 
     * Top View: [2, 1, 3, 6]
     */

    public static void main(String[] args) {
        TopView t = new TopView();
        
        // Example from problem
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.left.right.right = new TreeNode(5);
        root.left.right.right.right = new TreeNode(6);
        
        System.out.println("Top View (BFS): " + t.topView(root));
        // Expected: [2, 1, 3, 6]
        
        System.out.println("Top View (Optimized): " + t.topViewOptimized(root));
        // Expected: same
        
        System.out.println("Top View (DFS): " + t.topViewDFS(root));
        // Expected: same
        
        // Test cases
        System.out.println("\n=== Test Cases ===");
        
        // Test 1: Empty tree
        System.out.println("Empty tree: " + t.topView(null)); // []
        
        // Test 2: Single node
        TreeNode single = new TreeNode(1);
        System.out.println("Single node: " + t.topView(single)); // [1]
        
        // Test 3: Left-skewed tree
        TreeNode leftSkewed = new TreeNode(1);
        leftSkewed.left = new TreeNode(2);
        leftSkewed.left.left = new TreeNode(3);
        System.out.println("Left-skewed: " + t.topView(leftSkewed)); // [3, 1]
        // HD: 3 at -2, 2 at -1, 1 at 0 → [3, 2, 1]? Actually 2 covers 1 at HD -1?
        // Let's trace: HD 0:1, HD -1:2, HD -2:3 → top view [3, 2, 1]
        
        // Test 4: Right-skewed tree
        TreeNode rightSkewed = new TreeNode(1);
        rightSkewed.right = new TreeNode(2);
        rightSkewed.right.right = new TreeNode(3);
        System.out.println("Right-skewed: " + t.topView(rightSkewed)); // [1, 2, 3]
        
        // Test 5: Simple symmetric tree
        TreeNode sym = new TreeNode(1);
        sym.left = new TreeNode(2);
        sym.right = new TreeNode(3);
        System.out.println("Symmetric: " + t.topView(sym)); // [2, 1, 3]
        
        // Test 6: Complex tree
        TreeNode complex = new TreeNode(1);
        complex.left = new TreeNode(2);
        complex.right = new TreeNode(3);
        complex.left.left = new TreeNode(4);
        complex.left.right = new TreeNode(5);
        complex.right.left = new TreeNode(6);
        complex.right.right = new TreeNode(7);
        System.out.println("Complex tree: " + t.topView(complex));
        // Expected: [4, 2, 1, 3, 7]
        
        // Test 7: Tree where multiple nodes at same HD
        TreeNode multi = new TreeNode(1);
        multi.left = new TreeNode(2);
        multi.right = new TreeNode(3);
        multi.left.right = new TreeNode(4);
        multi.right.left = new TreeNode(5);
        System.out.println("Multiple at same HD: " + t.topView(multi));
        // Tree:    1
        //         / \
        //        2   3
        //         \ /
        //         4 5
        // HD: 2 at -1, 4 at 0, 1 at 0, 5 at 0, 3 at 1
        // Top view: [2, 1, 3] (4 and 5 are deeper than 1 at HD 0)
    }
}

/**
 * KEY CONCEPTS:
 * 
 * 1. Horizontal Distance (HD):
 *    - Root: 0
 *    - Move left: -1
 *    - Move right: +1
 * 
 * 2. Top-most node:
 *    - For each HD, we want the node seen FIRST in BFS traversal
 *    - BFS processes top to bottom, so first node is shallowest
 * 
 * 3. TreeMap vs HashMap:
 *    - TreeMap: keys sorted automatically, simpler code
 *    - HashMap + min/max: more efficient, manual iteration
 */

/**
 * COMPARISON WITH BOTTOM VIEW:
 * 
 * Top View:                    Bottom View:
 * - Keep FIRST node for each HD - Keep LAST node for each HD
 * - Don't overwrite values      - Always overwrite values
 * - Check if map contains key   - map.put() always
 * - if (!map.containsKey(col))  - map.put(col, val)
 * 
 * Same BFS structure, different map update logic
 */

/**
 * TIME & SPACE ANALYSIS:
 * 
 * Time Complexity: O(n)
 *   - Each node visited once in BFS: O(n)
 *   - TreeMap operations (containsKey/put): O(log n) each, total O(n log n)
 *   - HashMap version: O(n) average
 * 
 * Space Complexity: O(n)
 *   - Queue stores O(n) nodes in worst case (complete tree)
 *   - Map stores O(w) entries where w = width of tree
 *   - Worst case: O(n) for wide tree
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Always putting values (like bottom view):
 *    - Should only put first occurrence
 *    - Use containsKey() check
 * 
 * 2. Using DFS without depth tracking:
 *    - DFS doesn't guarantee top-most node without depth comparison
 * 
 * 3. Wrong horizontal distance calculation:
 *    - Left: parent HD - 1, Right: parent HD + 1
 * 
 * 4. Forgetting to handle null root
 * 
 * 5. Not sorting columns correctly:
 *    - Need left to right order
 */

/**
 * VARIATIONS:
 * 
 * 1. Bottom View of Binary Tree
 * 2. Left View of Binary Tree
 * 3. Right View of Binary Tree
 * 4. Vertical Order Traversal
 * 5. Diagonal View of Binary Tree
 */

/**
 * PRACTICE EXERCISES:
 * 
 * 1. Modify to return bottom view
 * 2. Return nodes with their HD values
 * 3. Handle case where multiple nodes at same HD and depth
 * 4. Implement using DFS instead of BFS
 * 5. Find width of tree (max HD - min HD + 1)
 */