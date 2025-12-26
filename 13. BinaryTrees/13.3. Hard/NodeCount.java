/** 
 * Count nodes in a binary tree - Various approaches
 * Time complexities range from O(n) to O(log²n)
 */
public class NodeCount {
    // TreeNode definition for binary tree
    static class TreeNode { 
        int val; 
        TreeNode left, right; 
        TreeNode(int v) { val = v; } 
    }

    /**
     * Approach 1: Simple Recursive DFS
     * Time: O(n) - visits every node
     * Space: O(h) - recursion stack height (h = tree height)
     * Works for any binary tree
     */
    public int countNodes(TreeNode root) {
        if (root == null) return 0;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    /**
     * Approach 2: Iterative DFS using Stack
     * Time: O(n), Space: O(h)
     * Avoids recursion overhead
     */
    public int countNodesIterativeDFS(TreeNode root) {
        if (root == null) return 0;
        
        java.util.Stack<TreeNode> stack = new java.util.Stack<>();
        stack.push(root);
        int count = 0;
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            count++;
            
            // Push right first so left gets processed first (preorder-like)
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }
        
        return count;
    }

    /**
     * Approach 3: Iterative BFS using Queue
     * Time: O(n), Space: O(w) where w is max width
     * Level-order traversal approach
     */
    public int countNodesBFS(TreeNode root) {
        if (root == null) return 0;
        
        java.util.LinkedList<TreeNode> queue = new java.util.LinkedList<>();
        queue.add(root);
        int count = 0;
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            count++;
            
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
        
        return count;
    }

    /**
     * Approach 4: Optimized for Complete Binary Tree
     * Time: O(log² n) = O(h²) where h = height
     * Space: O(log n) for recursion
     * 
     * Key Insight for Complete Binary Trees:
     * 1. If left subtree height == right subtree height:
     *    - Left subtree is perfect (full)
     *    - Count = 2^h - 1 + 1 + countNodes(right)
     * 2. If heights differ:
     *    - Right subtree is perfect (but one level less)
     *    - Count = 2^(h-1) - 1 + 1 + countNodes(left)
     * 
     * Perfect binary tree node count formula: 2^h - 1
     * (where h is height starting from 1 for single node)
     */
    public int countNodesOptimized(TreeNode root) {
        if (root == null) return 0;
        
        int leftHeight = getLeftHeight(root);
        int rightHeight = getRightHeight(root);
        
        if (leftHeight == rightHeight) {
            // Left subtree is perfect (full)
            // Total nodes = nodes in perfect left subtree + root + nodes in right subtree
            return (1 << leftHeight) - 1 + 1 + countNodesOptimized(root.right);
        } else {
            // Right subtree is perfect (but one level shallower)
            // Total nodes = nodes in perfect right subtree + root + nodes in left subtree
            return (1 << rightHeight) - 1 + 1 + countNodesOptimized(root.left);
        }
    }
    
    /**
     * Get height following only left children
     * Time: O(log n) for complete binary tree
     */
    private int getLeftHeight(TreeNode node) {
        int height = 0;
        while (node != null) {
            height++;
            node = node.left;
        }
        return height;
    }
    
    /**
     * Get height following only right children
     * Time: O(log n) for complete binary tree
     */
    private int getRightHeight(TreeNode node) {
        int height = 0;
        while (node != null) {
            height++;
            node = node.right;
        }
        return height;
    }

    /**
     * Approach 5: Morris Traversal (O(1) space)
     * Time: O(n), Space: O(1)
     * Uses threaded binary tree traversal
     */
    public int countNodesMorris(TreeNode root) {
        int count = 0;
        TreeNode curr = root;
        
        while (curr != null) {
            if (curr.left == null) {
                // No left child, count this node and go right
                count++;
                curr = curr.right;
            } else {
                // Find inorder predecessor
                TreeNode prev = curr.left;
                while (prev.right != null && prev.right != curr) {
                    prev = prev.right;
                }
                
                if (prev.right == null) {
                    // Create thread
                    prev.right = curr;
                    curr = curr.left;
                } else {
                    // Remove thread and count this node
                    prev.right = null;
                    count++;
                    curr = curr.right;
                }
            }
        }
        
        return count;
    }

    /**
     * Approach 6: Using Binary Tree Properties
     * Counts leaves and internal nodes separately
     */
    public int[] countNodesByType(TreeNode root) {
        int[] counts = new int[3]; // [total, internal, leaves]
        countNodesByTypeHelper(root, counts);
        return counts;
    }
    
    private void countNodesByTypeHelper(TreeNode node, int[] counts) {
        if (node == null) return;
        
        counts[0]++; // total
        
        if (node.left != null || node.right != null) {
            counts[1]++; // internal node
        } else {
            counts[2]++; // leaf node
        }
        
        countNodesByTypeHelper(node.left, counts);
        countNodesByTypeHelper(node.right, counts);
    }

    // Test cases and examples
    public static void main(String[] args) {
        NodeCount counter = new NodeCount();
        
        // Test 1: Empty tree
        TreeNode root1 = null;
        System.out.println("Empty tree:");
        System.out.println("  Recursive: " + counter.countNodes(root1)); // 0
        System.out.println("  Optimized: " + counter.countNodesOptimized(root1)); // 0
        
        // Test 2: Single node
        TreeNode root2 = new TreeNode(1);
        System.out.println("\nSingle node:");
        System.out.println("  Recursive: " + counter.countNodes(root2)); // 1
        System.out.println("  Optimized: " + counter.countNodesOptimized(root2)); // 1
        
        // Test 3: Complete binary tree
        //       1
        //      / \
        //     2   3
        //    / \ / \
        //   4  5 6  7
        TreeNode root3 = new TreeNode(1);
        root3.left = new TreeNode(2);
        root3.right = new TreeNode(3);
        root3.left.left = new TreeNode(4);
        root3.left.right = new TreeNode(5);
        root3.right.left = new TreeNode(6);
        root3.right.right = new TreeNode(7);
        
        System.out.println("\nComplete binary tree (7 nodes):");
        System.out.println("  Recursive: " + counter.countNodes(root3)); // 7
        System.out.println("  Optimized: " + counter.countNodesOptimized(root3)); // 7
        System.out.println("  Morris: " + counter.countNodesMorris(root3)); // 7
        
        // Test 4: Incomplete binary tree
        //       1
        //      / \
        //     2   3
        //    /   /
        //   4   5
        TreeNode root4 = new TreeNode(1);
        root4.left = new TreeNode(2);
        root4.right = new TreeNode(3);
        root4.left.left = new TreeNode(4);
        root4.right.left = new TreeNode(5);
        
        System.out.println("\nIncomplete binary tree (5 nodes):");
        System.out.println("  Recursive: " + counter.countNodes(root4)); // 5
        System.out.println("  Optimized: " + counter.countNodesOptimized(root4)); // 5
        System.out.println("  BFS: " + counter.countNodesBFS(root4)); // 5
        
        // Test 5: Skewed tree (linked list)
        //   1
        //    \
        //     2
        //      \
        //       3
        TreeNode root5 = new TreeNode(1);
        root5.right = new TreeNode(2);
        root5.right.right = new TreeNode(3);
        
        System.out.println("\nRight-skewed tree (3 nodes):");
        System.out.println("  Recursive: " + counter.countNodes(root5)); // 3
        System.out.println("  Iterative DFS: " + counter.countNodesIterativeDFS(root5)); // 3
        
        // Test 6: Count by type
        int[] types = counter.countNodesByType(root3);
        System.out.println("\nComplete tree node types:");
        System.out.println("  Total: " + types[0]); // 7
        System.out.println("  Internal: " + types[1]); // 3 (nodes 1, 2, 3)
        System.out.println("  Leaves: " + types[2]); // 4 (nodes 4, 5, 6, 7)
    }

    /**
     * Complexity Comparison:
     * 
     * | Method                  | Time       | Space      | Best For                  |
     * |-------------------------|------------|------------|---------------------------|
     * | Simple Recursive        | O(n)       | O(h)       | General purpose, simple   |
     * | Iterative DFS           | O(n)       | O(h)       | Avoiding recursion        |
     * | Iterative BFS           | O(n)       | O(w)       | Level-order needed        |
     * | Optimized (Complete)    | O(log² n)  | O(log n)   | Complete binary trees     |
     * | Morris Traversal        | O(n)       | O(1)       | Memory-constrained        |
     * 
     * Note: w = maximum width, h = height, n = number of nodes
     */

    /**
     * Additional utility methods
     */
    
    /**
     * Check if tree is perfect (all internal nodes have 2 children, 
     * all leaves at same level)
     */
    public boolean isPerfect(TreeNode root) {
        if (root == null) return true;
        
        int leftHeight = getLeftHeight(root);
        int rightHeight = getRightHeight(root);
        
        return leftHeight == rightHeight && 
               isPerfect(root.left) && 
               isPerfect(root.right);
    }
    
    /**
     * Count nodes at specific level k (0-indexed, root is level 0)
     */
    public int countNodesAtLevel(TreeNode root, int k) {
        if (root == null) return 0;
        if (k == 0) return 1;
        return countNodesAtLevel(root.left, k - 1) + 
               countNodesAtLevel(root.right, k - 1);
    }
    
    /**
     * Count leaf nodes only
     */
    public int countLeafNodes(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        return countLeafNodes(root.left) + countLeafNodes(root.right);
    }
    
    /**
     * Count internal nodes (nodes with at least one child)
     */
    public int countInternalNodes(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 0;
        return 1 + countInternalNodes(root.left) + countInternalNodes(root.right);
    }
}