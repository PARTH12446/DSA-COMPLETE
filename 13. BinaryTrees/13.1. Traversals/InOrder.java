import java.util.ArrayList;
import java.util.List;

/**
 * Binary Tree Inorder Traversal (left, root, right) - recursive version.
 * 
 * INORDER TRAVERSAL: Visit left subtree → root → right subtree
 * For Binary Search Trees (BST), inorder gives elements in SORTED order.
 * 
 * RECURSIVE APPROACH:
 * 1. Base case: if node is null, return
 * 2. Recursively traverse left subtree
 * 3. Visit current node (add to result)
 * 4. Recursively traverse right subtree
 * 
 * TIME COMPLEXITY: O(n) - each node visited exactly once
 * SPACE COMPLEXITY: O(h) - recursion stack height, where h = tree height
 *   - Best case (balanced): O(log n)
 *   - Worst case (skewed): O(n)
 */
public class InOrder {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    /**
     * Recursive inorder traversal
     * @param root - current tree node
     * @param res  - list to store traversal result
     * 
     * Example walkthrough for tree:
     *        1
     *       / \
     *      2   3
     *     / \   \
     *    4   5   6
     * 
     * Execution order:
     * inorder(1)
     *   → inorder(2)
     *     → inorder(4)
     *       → add 4
     *     → add 2
     *     → inorder(5)
     *       → add 5
     *   → add 1
     *   → inorder(3)
     *     → inorder(null) (3.left)
     *     → add 3
     *     → inorder(6)
     *       → add 6
     * 
     * Result: [4, 2, 5, 1, 3, 6]
     */
    public void inorder(TreeNode root, List<Integer> res) {
        // Base case: empty node
        if (root == null) return;
        
        // 1. Traverse left subtree
        inorder(root.left, res);
        
        // 2. Visit current node
        res.add(root.val);
        
        // 3. Traverse right subtree
        inorder(root.right, res);
    }
    
    /**
     * Alternative: Return new list (wrapper method)
     * Useful for single method call
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    public static void main(String[] args) {
        // Build sample tree:
        //        1
        //       / \
        //      2   3
        //     / \   \
        //    4   5   6
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);

        InOrder solver = new InOrder();
        List<Integer> res = new ArrayList<>();
        solver.inorder(root, res);
        
        System.out.println("Inorder traversal: " + res); // [4, 2, 5, 1, 3, 6]
        
        // Test with wrapper method
        System.out.println("Using wrapper: " + solver.inorderTraversal(root));
        
        // Edge cases
        System.out.println("Empty tree: " + solver.inorderTraversal(null)); // []
        
        TreeNode single = new TreeNode(10);
        System.out.println("Single node: " + solver.inorderTraversal(single)); // [10]
        
        // Skewed tree test (right skewed)
        TreeNode skewed = new TreeNode(1);
        skewed.right = new TreeNode(2);
        skewed.right.right = new TreeNode(3);
        System.out.println("Right-skewed: " + solver.inorderTraversal(skewed)); // [1, 2, 3]
        
        // BST test (should give sorted order)
        TreeNode bst = new TreeNode(4);
        bst.left = new TreeNode(2);
        bst.right = new TreeNode(6);
        bst.left.left = new TreeNode(1);
        bst.left.right = new TreeNode(3);
        bst.right.left = new TreeNode(5);
        bst.right.right = new TreeNode(7);
        System.out.println("BST (sorted): " + solver.inorderTraversal(bst)); // [1, 2, 3, 4, 5, 6, 7]
    }
}

/**
 * COMPARISON WITH OTHER TRAVERSALS:
 * 
 * PREORDER (Root → Left → Right):
 *   public void preorder(TreeNode root, List<Integer> res) {
 *       if (root == null) return;
 *       res.add(root.val);          // Visit root first
 *       preorder(root.left, res);   // Then left
 *       preorder(root.right, res);  // Then right
 *   }
 * 
 * POSTORDER (Left → Right → Root):
 *   public void postorder(TreeNode root, List<Integer> res) {
 *       if (root == null) return;
 *       postorder(root.left, res);   // First left
 *       postorder(root.right, res);  // Then right
 *       res.add(root.val);           // Visit root last
 *   }
 * 
 * INORDER (Left → Root → Right) ← THIS IMPLEMENTATION
 */

/**
 * ITERATIVE VERSION (for comparison):
 * 
 * public List<Integer> inorderIterative(TreeNode root) {
 *     List<Integer> res = new ArrayList<>();
 *     Stack<TreeNode> stack = new Stack<>();
 *     TreeNode curr = root;
 *     
 *     while (curr != null || !stack.isEmpty()) {
 *         // Go to leftmost node
 *         while (curr != null) {
 *             stack.push(curr);
 *             curr = curr.left;
 *         }
 *         
 *         // Visit node
 *         curr = stack.pop();
 *         res.add(curr.val);
 *         
 *         // Move to right subtree
 *         curr = curr.right;
 *     }
 *     return res;
 * }
 */

/**
 * APPLICATIONS OF INORDER TRAVERSAL:
 * 
 * 1. Binary Search Tree (BST) operations:
 *    - Get elements in sorted order
 *    - Validate BST (check if inorder is sorted)
 *    - Find kth smallest element
 * 
 * 2. Expression trees:
 *    - Convert to infix notation
 *    
 * 3. Threaded binary trees
 * 
 * 4. Morris traversal (O(1) space)
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Forgetting base case (root == null) → StackOverflowError
 * 2. Wrong order of recursive calls (should be left → root → right)
 * 3. Not passing result list correctly
 * 4. Using recursion on very deep trees (risk of stack overflow)
 */

/**
 * TIME & SPACE ANALYSIS:
 * 
 * Time: O(n) - Each node visited exactly once
 * Space (recursive): O(h) where h = tree height
 *   - Balanced tree: h = log₂n → O(log n)
 *   - Skewed tree: h = n → O(n)
 *   
 * For iterative version: Same time, explicit stack instead of recursion stack
 */