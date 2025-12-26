/**
 * Validates whether a binary tree is a valid Binary Search Tree (BST).
 * 
 * BST Definition:
 * - The left subtree of a node contains only nodes with keys LESS THAN the node's key.
 * - The right subtree of a node contains only nodes with keys GREATER THAN the node's key.
 * - Both the left and right subtrees must also be binary search trees.
 * 
 * Approach: Recursive traversal with valid range propagation.
 * For each node, we maintain a valid range (low, high) that the node's value must fall within.
 * Initially, the root can have any value, so the range is (-∞, +∞).
 * 
 * When traversing left:  upper bound becomes current node's value (exclusive)
 * When traversing right: lower bound becomes current node's value (exclusive)
 * 
 * Key Implementation Details:
 * 1. Uses LONG bounds (Long.MIN_VALUE, Long.MAX_VALUE) to handle edge cases where
 *    node values are Integer.MIN_VALUE or Integer.MAX_VALUE.
 * 2. Boundaries are EXCLUSIVE (<= low or >= high returns false) to enforce strict BST property.
 * 
 * Time Complexity: O(n) - each node visited once
 * Space Complexity: O(h) - recursion stack, where h is tree height
 *                   O(n) in worst case (skewed tree), O(log n) in balanced tree
 * 
 * Alternative approaches:
 * 1. In-order traversal: Validate that traversal produces strictly increasing sequence
 * 2. Iterative DFS with explicit stack
 * 3. Morris traversal for O(1) space
 */
public class CheckBST {
    /**
     * TreeNode structure representing a node in the binary tree.
     * Each node contains an integer value and references to left/right children.
     */
    static class TreeNode { 
        int val; 
        TreeNode left, right; 
        
        TreeNode(int v) {
            val = v;
        }
    }

    /**
     * Public interface to validate BST starting from root.
     * 
     * @param root The root node of the binary tree
     * @return true if the tree is a valid BST, false otherwise
     */
    public boolean isValidBST(TreeNode root) {
        // Use Long bounds to handle edge cases with Integer min/max values
        return helper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    /**
     * Recursive helper that validates subtree rooted at 'node' falls within (low, high) range.
     * 
     * @param node  Current node being validated
     * @param low   Exclusive lower bound - node value must be GREATER than low
     * @param high  Exclusive upper bound - node value must be LESS than high
     * @return      true if subtree rooted at 'node' is a valid BST within given bounds
     * 
     * Note: The bounds are updated as we traverse:
     * - Going left:  high becomes current node's value (all left descendants must be < current)
     * - Going right: low becomes current node's value (all right descendants must be > current)
     */
    private boolean helper(TreeNode node, long low, long high) {
        // Base case: empty tree/subtree is always valid
        if (node == null) return true;
        
        // Check if current node's value violates BST property
        // Note: Using <= and >= to ensure STRICT inequality (no duplicates in BST)
        if (node.val <= low || node.val >= high) return false;
        
        // Recursively validate left and right subtrees with updated bounds
        // Left subtree: values must be between current low and current node value
        // Right subtree: values must be between current node value and current high
        return helper(node.left, low, node.val) && helper(node.right, node.val, high);
    }
    
    /**
     * Main method for testing. Example test cases:
     * 
     * Valid BST example:
     *       10
     *      /  \
     *     5   15
     *        /  \
     *       12  20
     * 
     * Invalid BST example:
     *       10
     *      /  \
     *     5   15
     *        /  \
     *        6  20  // 6 < 10 (violates right subtree property)
     */
    public static void main(String[] args) {
        // Example 1: Valid BST
        TreeNode root1 = new TreeNode(10);
        root1.left = new TreeNode(5);
        root1.right = new TreeNode(15);
        root1.right.left = new TreeNode(12);
        root1.right.right = new TreeNode(20);
        
        // Example 2: Invalid BST
        TreeNode root2 = new TreeNode(10);
        root2.left = new TreeNode(5);
        root2.right = new TreeNode(15);
        root2.right.left = new TreeNode(6);  // Invalid: 6 < 10
        
        CheckBST checker = new CheckBST();
        System.out.println("Tree 1 is valid BST: " + checker.isValidBST(root1)); // true
        System.out.println("Tree 2 is valid BST: " + checker.isValidBST(root2)); // false
        
        // Edge case: Single node with Integer.MAX_VALUE
        TreeNode root3 = new TreeNode(Integer.MAX_VALUE);
        System.out.println("Tree 3 (MAX_VALUE) is valid BST: " + checker.isValidBST(root3)); // true
        
        // Edge case: Tree with duplicate values
        TreeNode root4 = new TreeNode(10);
        root4.left = new TreeNode(10);  // Invalid: equal to parent
        System.out.println("Tree 4 (duplicate) is valid BST: " + checker.isValidBST(root4)); // false
    }
}