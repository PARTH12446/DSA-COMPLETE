import java.util.ArrayList;
import java.util.List;

/**
 * Boundary Traversal of Binary Tree
 * 
 * PROBLEM: Traverse the boundary of binary tree in anti-clockwise direction:
 * 1. Root (if not a leaf)
 * 2. Left boundary (top-down, excluding leaf nodes)
 * 3. Leaf nodes (left to right)
 * 4. Right boundary (bottom-up, excluding leaf nodes)
 * 
 * APPROACH: Three separate traversals combined
 * TIME: O(n) - each node visited at most twice
 * SPACE: O(h) - recursion depth for leaves
 */
public class Boundary {

    static class TreeNode {
        int val; 
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    /**
     * Check if node is a leaf (no children)
     */
    private boolean isLeaf(TreeNode node) {
        return node != null && node.left == null && node.right == null;
    }

    /**
     * Add left boundary (excluding leaves)
     * Traverse: root → left child → left child ... (if exists, else right child)
     */
    private void addLeftBoundary(TreeNode root, List<Integer> res) {
        TreeNode curr = root.left;
        while (curr != null) {
            // Add non-leaf nodes
            if (!isLeaf(curr)) {
                res.add(curr.val);
            }
            // Prefer left child, else right child
            if (curr.left != null) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
    }

    /**
     * Add right boundary (excluding leaves) in REVERSE order
     * Traverse: root → right child → right child ... (if exists, else left child)
     * Store in temp list, then reverse to get bottom-up order
     */
    private void addRightBoundary(TreeNode root, List<Integer> res) {
        TreeNode curr = root.right;
        List<Integer> temp = new ArrayList<>();
        while (curr != null) {
            // Add non-leaf nodes to temp list
            if (!isLeaf(curr)) {
                temp.add(curr.val);
            }
            // Prefer right child, else left child
            if (curr.right != null) {
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }
        // Add in reverse order (bottom-up)
        for (int i = temp.size() - 1; i >= 0; i--) {
            res.add(temp.get(i));
        }
    }

    /**
     * Add all leaf nodes (left to right order)
     * Using DFS (inorder-like) to get left-to-right order
     */
    private void addLeaves(TreeNode root, List<Integer> res) {
        if (root == null) return;
        
        // Add leaf node
        if (isLeaf(root)) {
            res.add(root.val);
            return;
        }
        
        // Recursively add leaves from left and right subtrees
        addLeaves(root.left, res);
        addLeaves(root.right, res);
    }

    /**
     * Main boundary traversal method
     * Order: Root → Left Boundary → Leaves → Right Boundary
     */
    public List<Integer> boundaryTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        // 1. Add root (if not a leaf)
        if (!isLeaf(root)) {
            result.add(root.val);
        }
        
        // 2. Add left boundary (excluding root and leaves)
        addLeftBoundary(root, result);
        
        // 3. Add all leaves
        addLeaves(root, result);
        
        // 4. Add right boundary (reverse order, excluding root and leaves)
        addRightBoundary(root, result);
        
        return result;
    }
    
    /**
     * Alternative: All in one method
     * More compact but less readable
     */
    public List<Integer> boundaryTraversalAlt(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        
        if (!isLeaf(root)) res.add(root.val);
        
        // Left boundary
        TreeNode curr = root.left;
        while (curr != null && !isLeaf(curr)) {
            res.add(curr.val);
            curr = curr.left != null ? curr.left : curr.right;
        }
        
        // Leaves
        addLeaves(root, res);
        
        // Right boundary (reverse)
        List<Integer> right = new ArrayList<>();
        curr = root.right;
        while (curr != null && !isLeaf(curr)) {
            right.add(curr.val);
            curr = curr.right != null ? curr.right : curr.left;
        }
        for (int i = right.size() - 1; i >= 0; i--) {
            res.add(right.get(i));
        }
        
        return res;
    }

    /**
     * Visual Example:
     * 
     * Tree:
     *           1
     *         /   \
     *        2     3
     *       / \   / \
     *      4   5 6   7
     *         / \
     *        8   9
     * 
     * Boundary traversal:
     * 1. Root: 1
     * 2. Left boundary: 2 (not 4 because it's a leaf)
     * 3. Leaves: 4, 8, 9, 6, 7
     * 4. Right boundary: 3 (not 7 because it's a leaf)
     * 
     * Result: [1, 2, 4, 8, 9, 6, 7, 3]
     * 
     * Note: 
     * - Node 5 is not a leaf (has children 8,9)
     * - Leaves are 4,8,9,6,7 in left-to-right order
     */

    public static void main(String[] args) {
        Boundary b = new Boundary();
        
        // Example 1: Simple tree
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.left = new TreeNode(4);
        root1.left.right = new TreeNode(5);
        root1.right.left = new TreeNode(6);
        root1.right.right = new TreeNode(7);
        
        System.out.println("Example 1: " + b.boundaryTraversal(root1));
        // Expected: [1, 2, 4, 5, 6, 7, 3] 
        // Actually leaves: 4,5,6,7, right boundary: 3
        
        // Example 2: More complex tree
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(3);
        root2.left.left = new TreeNode(4);
        root2.left.right = new TreeNode(5);
        root2.left.right.left = new TreeNode(8);
        root2.left.right.right = new TreeNode(9);
        root2.right.left = new TreeNode(6);
        root2.right.right = new TreeNode(7);
        
        System.out.println("Example 2: " + b.boundaryTraversal(root2));
        // Expected: [1, 2, 4, 8, 9, 6, 7, 3]
        
        // Test cases
        System.out.println("\n=== Test Cases ===");
        
        // Test 1: Empty tree
        System.out.println("Empty tree: " + b.boundaryTraversal(null)); // []
        
        // Test 2: Single node (leaf)
        TreeNode single = new TreeNode(1);
        System.out.println("Single node: " + b.boundaryTraversal(single)); // [1]
        
        // Test 3: Root with only left child
        TreeNode leftOnly = new TreeNode(1);
        leftOnly.left = new TreeNode(2);
        System.out.println("Left only: " + b.boundaryTraversal(leftOnly)); // [1, 2]
        
        // Test 4: Root with only right child
        TreeNode rightOnly = new TreeNode(1);
        rightOnly.right = new TreeNode(2);
        System.out.println("Right only: " + b.boundaryTraversal(rightOnly)); // [1, 2]
        
        // Test 5: Left-skewed tree
        TreeNode leftSkewed = new TreeNode(1);
        leftSkewed.left = new TreeNode(2);
        leftSkewed.left.left = new TreeNode(3);
        System.out.println("Left-skewed: " + b.boundaryTraversal(leftSkewed)); // [1, 2, 3]
        
        // Test 6: Right-skewed tree
        TreeNode rightSkewed = new TreeNode(1);
        rightSkewed.right = new TreeNode(2);
        rightSkewed.right.right = new TreeNode(3);
        System.out.println("Right-skewed: " + b.boundaryTraversal(rightSkewed)); // [1, 2, 3]
        
        // Test 7: Tree where root is leaf (only root)
        TreeNode rootLeaf = new TreeNode(1);
        System.out.println("Root leaf: " + b.boundaryTraversal(rootLeaf)); // [1]
        
        // Test 8: Complex tree
        TreeNode complex = new TreeNode(20);
        complex.left = new TreeNode(8);
        complex.right = new TreeNode(22);
        complex.left.left = new TreeNode(4);
        complex.left.right = new TreeNode(12);
        complex.left.right.left = new TreeNode(10);
        complex.left.right.right = new TreeNode(14);
        complex.right.right = new TreeNode(25);
        
        System.out.println("Complex tree: " + b.boundaryTraversal(complex));
        // Expected: [20, 8, 4, 10, 14, 25, 22]
    }
}

/**
 * BOUNDARY TRAVERSAL RULES:
 * 
 * 1. Root node (if not a leaf)
 * 2. Left boundary (excluding leaves, top-down):
 *    - Start from root's left child
 *    - Go left if possible, else go right
 *    - Stop when reaching a leaf
 * 3. Leaf nodes (left to right order):
 *    - All nodes with no children
 *    - Use inorder-like traversal to get left-to-right order
 * 4. Right boundary (excluding leaves, bottom-up):
 *    - Start from root's right child
 *    - Go right if possible, else go left
 *    - Store in temp list, then reverse for bottom-up order
 * 
 * IMPORTANT: No node should appear twice in the traversal!
 */

/**
 * TIME & SPACE ANALYSIS:
 * 
 * Time Complexity: O(n)
 *   - Each node visited at most twice
 *   - Left boundary: O(h) where h = height
 *   - Leaves: O(n) (all nodes)
 *   - Right boundary: O(h)
 * 
 * Space Complexity: O(h)
 *   - Recursion stack for leaves: O(h)
 *   - Temp list for right boundary: O(h)
 *   - Result list: O(n) but that's output
 */

/**
 * KEY INSIGHTS:
 * 
 * 1. Leaf nodes are critical:
 *    - They separate left/right boundaries
 *    - Included in traversal but not in boundaries
 * 
 * 2. Direction matters:
 *    - Left boundary: top-down (root to leaf)
 *    - Right boundary: bottom-up (leaf to root)
 * 
 * 3. Precedence in boundary traversal:
 *    - Prefer left child for left boundary
 *    - Prefer right child for right boundary
 *    - If preferred child is null, take the other child
 * 
 * 4. Root special case:
 *    - Only included if not a leaf
 *    - If root is leaf, only root appears (as leaf)
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Including leaves in left/right boundaries:
 *    - Boundaries should exclude leaf nodes
 *    - Leaves are added separately
 * 
 * 2. Wrong order for right boundary:
 *    - Should be bottom-up (reverse of traversal)
 * 
 * 3. Duplicate nodes:
 *    - A node could be both in boundary and leaves
 *    - Check isLeaf() to avoid duplicates
 * 
 * 4. Handling single node tree:
 *    - Root is leaf, should be included once
 * 
 * 5. Null pointer checks:
 *    - Check root == null
 *    - Check children before accessing
 */

/**
 * VARIATIONS:
 * 
 * 1. Clockwise boundary traversal:
 *    - Reverse the entire process
 * 
 * 2. Exterior traversal (similar):
 *    - Includes only outermost nodes
 * 
 * 3. Print boundary with levels
 * 
 * 4. Store boundary nodes in separate lists
 */

/**
 * PRACTICE EXERCISES:
 * 
 * 1. Modify to handle tree where root has only one child
 * 2. Print boundary nodes with their levels
 * 3. Check if a node is on boundary
 * 4. Find sum of boundary nodes
 * 5. Clockwise boundary traversal
 */