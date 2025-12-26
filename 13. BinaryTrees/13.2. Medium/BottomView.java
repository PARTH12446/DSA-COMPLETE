import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * Bottom View of Binary Tree
 * 
 * PROBLEM: Print the bottom-most nodes at each horizontal distance
 * Horizontal Distance (HD): 
 *   - Root HD = 0
 *   - Left child HD = parent HD - 1
 *   - Right child HD = parent HD + 1
 * 
 * For each HD, we need the LAST (bottom-most) node seen in BFS traversal
 * 
 * APPROACH: BFS (Level Order) with horizontal distance tracking
 * TIME: O(n) - each node visited once
 * SPACE: O(n) - queue and map storage
 */
public class BottomView {

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
     * Get bottom view of binary tree
     * @param root - root of binary tree
     * @return List of bottom-most nodes from left to right
     * 
     * STRATEGY:
     * 1. Use BFS to traverse level by level
     * 2. Track horizontal distance (col) for each node
     * 3. Use TreeMap to store last seen node for each col
     *    - TreeMap keeps keys (cols) in sorted order
     *    - Overwrite previous value to keep last (bottom-most) node
     * 4. BFS ensures nodes at same HD are processed top to bottom
     * 5. Return map values from smallest to largest col (left to right)
     */
    public List<Integer> bottomView(TreeNode root) {
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
            
            // Overwrite existing value - last node (BFS) wins for this col
            map.put(col, node.val);
            
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
    public List<Integer> bottomViewOptimized(TreeNode root) {
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
            
            // Always update - last node for this column wins
            map.put(col, node.val);
            
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
    public List<Integer> bottomViewDFS(TreeNode root) {
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
        int depth; // track depth to choose deeper node
        Pair(int v, int d) { value = v; depth = d; }
    }
    
    private void dfs(TreeNode node, int col, int depth, Map<Integer, Pair> map) {
        if (node == null) return;
        
        // If this column not visited, or current node is deeper
        if (!map.containsKey(col) || depth >= map.get(col).depth) {
            map.put(col, new Pair(node.val, depth));
        }
        
        dfs(node.left, col - 1, depth + 1, map);
        dfs(node.right, col + 1, depth + 1, map);
    }

    /**
     * Visual Example:
     * 
     * Tree:
     *           20
     *          /  \
     *         8    22
     *        / \   / \
     *       5   3 4   25
     *          / \
     *         10 14
     * 
     * Horizontal Distances:
     * HD -2: 5
     * HD -1: 10
     * HD  0: 4  (both 20 and 4 at HD 0, but 4 is bottom-most)
     * HD  1: 14
     * HD  2: 25
     * 
     * Bottom View: [5, 10, 4, 14, 25]
     * 
     * BFS Order with HD:
     * (20,0) → map[0]=20
     * (8,-1) → map[-1]=8
     * (22,1) → map[1]=22
     * (5,-2) → map[-2]=5
     * (3,0)  → map[0]=3  (overwrites 20)
     * (4,0)  → map[0]=4  (overwrites 3)
     * (25,2) → map[2]=25
     * (10,-1)→ map[-1]=10 (overwrites 8)
     * (14,1) → map[1]=14 (overwrites 22)
     * 
     * Final map: {-2:5, -1:10, 0:4, 1:14, 2:25}
     * Sorted: [5, 10, 4, 14, 25]
     */

    public static void main(String[] args) {
        BottomView b = new BottomView();
        
        // Example from problem
        TreeNode root = new TreeNode(20);
        root.left = new TreeNode(8);
        root.right = new TreeNode(22);
        root.left.left = new TreeNode(5);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(25);
        root.left.right.left = new TreeNode(10);
        root.left.right.right = new TreeNode(14);
        
        System.out.println("Bottom View (BFS): " + b.bottomView(root));
        // Expected: [5, 10, 4, 14, 25]
        
        System.out.println("Bottom View (Optimized): " + b.bottomViewOptimized(root));
        // Expected: same
        
        System.out.println("Bottom View (DFS): " + b.bottomViewDFS(root));
        // Expected: same
        
        // Test cases
        System.out.println("\n=== Test Cases ===");
        
        // Test 1: Empty tree
        System.out.println("Empty tree: " + b.bottomView(null)); // []
        
        // Test 2: Single node
        TreeNode single = new TreeNode(1);
        System.out.println("Single node: " + b.bottomView(single)); // [1]
        
        // Test 3: Left-skewed tree
        TreeNode leftSkewed = new TreeNode(1);
        leftSkewed.left = new TreeNode(2);
        leftSkewed.left.left = new TreeNode(3);
        System.out.println("Left-skewed: " + b.bottomView(leftSkewed)); // [3]
        
        // Test 4: Right-skewed tree
        TreeNode rightSkewed = new TreeNode(1);
        rightSkewed.right = new TreeNode(2);
        rightSkewed.right.right = new TreeNode(3);
        System.out.println("Right-skewed: " + b.bottomView(rightSkewed)); // [3]
        
        // Test 5: Simple symmetric tree
        TreeNode sym = new TreeNode(1);
        sym.left = new TreeNode(2);
        sym.right = new TreeNode(3);
        System.out.println("Symmetric: " + b.bottomView(sym)); // [2, 1, 3] or [2, 3]
        // Actually output depends on which node is "bottom" for HD 0
        
        // Test 6: Tree where multiple nodes at same HD
        TreeNode multi = new TreeNode(1);
        multi.left = new TreeNode(2);
        multi.right = new TreeNode(3);
        multi.left.right = new TreeNode(4);
        multi.right.left = new TreeNode(5);
        System.out.println("Multiple at same HD: " + b.bottomView(multi));
        // Tree:    1
        //         / \
        //        2   3
        //         \ /
        //         4 5
        // Bottom View: [2, 4, 5, 3] or similar
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
 * 2. Bottom-most node:
 *    - For each HD, we want the node seen LAST in BFS traversal
 *    - BFS processes top to bottom, so last node is deepest
 * 
 * 3. TreeMap vs HashMap:
 *    - TreeMap: keys sorted automatically, simpler code
 *    - HashMap + min/max: more efficient, manual iteration
 */

/**
 * TIME & SPACE ANALYSIS:
 * 
 * Time Complexity: O(n)
 *   - Each node visited once in BFS: O(n)
 *   - TreeMap operations (put/get): O(log n) each, total O(n log n)
 *   - HashMap version: O(n) average
 * 
 * Space Complexity: O(n)
 *   - Queue stores O(n) nodes in worst case (complete tree)
 *   - Map stores O(w) entries where w = width of tree
 *   - Worst case: O(n) for skewed tree (width = 1, but queue stores all)
 */

/**
 * COMPARISON WITH TOP VIEW:
 * 
 * Bottom View:                  Top View:
 * - Keep LAST node for each HD  - Keep FIRST node for each HD
 * - Overwrite previous values   - Don't overwrite (check if exists)
 * - Map.put(col, val) always    - if (!map.containsKey(col)) map.put(col, val)
 * 
 * Same BFS structure, different map update logic
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Using DFS without depth tracking:
 *    - DFS doesn't guarantee bottom-most node without depth comparison
 * 
 * 2. Not overwriting values in map:
 *    - Should always put to keep last (bottom-most) node
 * 
 * 3. Wrong horizontal distance calculation:
 *    - Left: parent HD - 1, Right: parent HD + 1
 * 
 * 4. Forgetting to handle null root
 * 
 * 5. Using wrong traversal order:
 *    - BFS (level order) is natural for this problem
 */

/**
 * VARIATIONS:
 * 
 * 1. Top View of Binary Tree
 * 2. Left View of Binary Tree
 * 3. Right View of Binary Tree
 * 4. Diagonal View of Binary Tree
 * 5. Vertical Order Traversal
 */

/**
 * PRACTICE EXERCISES:
 * 
 * 1. Modify to return top view
 * 2. Return nodes with their HD values
 * 3. Handle case where multiple nodes at same HD and depth
 * 4. Implement using DFS instead of BFS
 * 5. Find width of tree (max HD - min HD + 1)
 */