/**
 * Maximum Depth of Binary Tree (LeetCode 104)
 * 
 * PROBLEM: Find the maximum depth (height) of binary tree
 * DEFINITION: Number of nodes along longest path from root to farthest leaf
 * 
 * APPROACH: Recursive DFS
 * TIME: O(n) - each node visited once
 * SPACE: O(h) - recursion stack (h = height)
 */
public class Height {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    /**
     * Calculate maximum depth/height of binary tree
     * @param root - root of binary tree
     * @return maximum depth (root has depth 1)
     * 
     * RECURSIVE DEFINITION:
     * - Null node: depth 0
     * - Non-null: depth = 1 + max(depth(left), depth(right))
     */
    public int maxDepth(TreeNode root) {
        // Base case: empty tree has depth 0
        if (root == null) return 0;
        
        // Recursively find depths of left and right subtrees
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        
        // Current depth = 1 + max of children depths
        return Math.max(leftDepth, rightDepth) + 1;
    }
    
    /**
     * Alternative: Iterative BFS (Level Order)
     * Count number of levels
     */
    public int maxDepthBFS(TreeNode root) {
        if (root == null) return 0;
        
        Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.offer(root);
        int depth = 0;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            depth++;
            
            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        
        return depth;
    }
    
    /**
     * Alternative: Iterative DFS with explicit stack
     */
    public int maxDepthDFS(TreeNode root) {
        if (root == null) return 0;
        
        Stack<TreeNode> nodeStack = new java.util.Stack<>();
        Stack<Integer> depthStack = new java.util.Stack<>();
        
        nodeStack.push(root);
        depthStack.push(1);
        int maxDepth = 0;
        
        while (!nodeStack.isEmpty()) {
            TreeNode node = nodeStack.pop();
            int currentDepth = depthStack.pop();
            
            maxDepth = Math.max(maxDepth, currentDepth);
            
            if (node.left != null) {
                nodeStack.push(node.left);
                depthStack.push(currentDepth + 1);
            }
            if (node.right != null) {
                nodeStack.push(node.right);
                depthStack.push(currentDepth + 1);
            }
        }
        
        return maxDepth;
    }
    
    /**
     * Minimum depth (LeetCode 111 - for comparison)
     * Depth to nearest leaf node
     */
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        
        // If left subtree is empty, only consider right
        if (root.left == null) return minDepth(root.right) + 1;
        
        // If right subtree is empty, only consider left
        if (root.right == null) return minDepth(root.left) + 1;
        
        // Both subtrees exist, take minimum
        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
    }

    /**
     * Visual Examples:
     * 
     * Example 1:
     *       1
     *      / \
     *     2   3
     *    / \
     *   4   5
     * 
     * Depth calculation:
     * depth(4): left=0, right=0 → return 1
     * depth(5): left=0, right=0 → return 1
     * depth(2): left=1, right=1 → return 2
     * depth(3): left=0, right=0 → return 1
     * depth(1): left=2, right=1 → return 3
     * 
     * Max depth = 3 ✓
     * 
     * Example 2 (skewed tree):
     *   1
     *    \
     *     2
     *      \
     *       3
     * 
     * depth(3): 1
     * depth(2): 1 + depth(3)=1 → 2
     * depth(1): 1 + depth(2)=2 → 3
     * Max depth = 3
     */

    public static void main(String[] args) {
        Height h = new Height();
        
        // Example from problem
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        
        System.out.println("Max Depth (Recursive): " + h.maxDepth(root)); // 3
        System.out.println("Max Depth (BFS): " + h.maxDepthBFS(root)); // 3
        System.out.println("Max Depth (DFS): " + h.maxDepthDFS(root)); // 3
        
        // Test cases
        System.out.println("\n=== Test Cases ===");
        
        // Test 1: Empty tree
        System.out.println("Empty tree: " + h.maxDepth(null)); // 0
        
        // Test 2: Single node
        TreeNode single = new TreeNode(1);
        System.out.println("Single node: " + h.maxDepth(single)); // 1
        
        // Test 3: Left-skewed tree
        TreeNode leftSkewed = new TreeNode(1);
        leftSkewed.left = new TreeNode(2);
        leftSkewed.left.left = new TreeNode(3);
        System.out.println("Left-skewed: " + h.maxDepth(leftSkewed)); // 3
        
        // Test 4: Right-skewed tree
        TreeNode rightSkewed = new TreeNode(1);
        rightSkewed.right = new TreeNode(2);
        rightSkewed.right.right = new TreeNode(3);
        System.out.println("Right-skewed: " + h.maxDepth(rightSkewed)); // 3
        
        // Test 5: Perfect binary tree
        TreeNode perfect = new TreeNode(1);
        perfect.left = new TreeNode(2);
        perfect.right = new TreeNode(3);
        perfect.left.left = new TreeNode(4);
        perfect.left.right = new TreeNode(5);
        perfect.right.left = new TreeNode(6);
        perfect.right.right = new TreeNode(7);
        System.out.println("Perfect binary: " + h.maxDepth(perfect)); // 3
        
        // Test 6: Complex tree
        TreeNode complex = new TreeNode(1);
        complex.left = new TreeNode(2);
        complex.right = new TreeNode(3);
        complex.left.left = new TreeNode(4);
        complex.left.right = new TreeNode(5);
        complex.right.right = new TreeNode(6);
        complex.left.left.left = new TreeNode(7);
        System.out.println("Complex: " + h.maxDepth(complex)); // 4
        
        // Test min depth
        System.out.println("\n=== Min Depth Tests ===");
        System.out.println("Example tree min depth: " + h.minDepth(root)); // 2
        System.out.println("Left-skewed min depth: " + h.minDepth(leftSkewed)); // 3
        System.out.println("Perfect binary min depth: " + h.minDepth(perfect)); // 3
    }
}

/**
 * KEY CONCEPTS:
 * 
 * 1. Depth vs Height:
 *    - Depth: Distance from root to node (root depth = 1)
 *    - Height: Distance from node to farthest leaf (leaf height = 1)
 *    - For root: max depth = height of tree
 * 
 * 2. Base case importance:
 *    - Null node returns 0
 *    - This makes leaf nodes return 1 (0 + 1)
 * 
 * 3. Tree terminology:
 *    - Root: Top node (depth 1)
 *    - Leaf: Node with no children
 *    - Internal node: Node with at least one child
 */

/**
 * TIME & SPACE ANALYSIS:
 * 
 * Recursive:
 *   Time: O(n) - Each node visited once
 *   Space: O(h) - Recursion stack, where h = height
 *     - Best case (balanced): O(log n)
 *     - Worst case (skewed): O(n)
 * 
 * BFS:
 *   Time: O(n) - Each node visited once
 *   Space: O(w) - Queue size, where w = max width
 *     - Best case (skewed): O(1)
 *     - Worst case (complete): O(n/2) ≈ O(n)
 * 
 * DFS (iterative):
 *   Time: O(n)
 *   Space: O(h) - Stack size
 */

/**
 * RECURSION WALKTHROUGH:
 * 
 * For tree:   1
 *            / \
 *           2   3
 * 
 * maxDepth(1):
 *   left = maxDepth(2)
 *     left = maxDepth(null) = 0
 *     right = maxDepth(null) = 0
 *     return max(0,0)+1 = 1
 *   right = maxDepth(3) = 1 (similar)
 *   return max(1,1)+1 = 2
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Returning 1 for null node:
 *    - Null should return 0, not 1
 *    - Leaf returns 1 (0 from children + 1)
 * 
 * 2. Using depth vs height inconsistently:
 *    - Some definitions: root depth = 0, root height = max depth
 *    - LeetCode uses root depth = 1
 * 
 * 3. Confusing min and max depth:
 *    - Max depth: longest path to leaf
 *    - Min depth: shortest path to leaf
 * 
 * 4. Stack overflow for deep trees:
 *    - Use iterative BFS/DFS for very deep trees
 */

/**
 * VARIATIONS:
 * 
 * 1. Minimum Depth (LeetCode 111)
 * 2. Average depth of all nodes
 * 3. Depth of specific node
 * 4. Check if all leaves at same depth
 * 5. Count nodes at each depth
 */

/**
 * PRACTICE EXERCISES:
 * 
 * 1. Calculate depth using iterative BFS
 * 2. Find node with maximum depth
 * 3. Check if tree is balanced (depth diff ≤ 1)
 * 4. Print all nodes at given depth
 * 5. Calculate sum of depths of all nodes
 */

/**
 * RELATED PROBLEMS:
 * 
 * 1. LeetCode 104: Maximum Depth
 * 2. LeetCode 111: Minimum Depth
 * 3. LeetCode 110: Balanced Binary Tree
 * 4. LeetCode 543: Diameter of Binary Tree
 * 5. LeetCode 559: Maximum Depth of N-ary Tree
 */