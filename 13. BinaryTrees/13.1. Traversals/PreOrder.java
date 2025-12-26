import java.util.ArrayList;
import java.util.List;

/**
 * Binary Tree Preorder Traversal (root, left, right) - recursive version.
 * 
 * PREORDER TRAVERSAL: Visit root → left subtree → right subtree
 * 
 * RECURSIVE APPROACH:
 * 1. Base case: if node is null, return
 * 2. Visit current node (add to result)
 * 3. Recursively traverse left subtree
 * 4. Recursively traverse right subtree
 * 
 * TIME: O(n) - each node visited once
 * SPACE: O(h) - recursion stack height (h = tree height)
 */
public class PreOrder {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    /**
     * Recursive Preorder Traversal
     * @param root - current tree node
     * @param res  - list to store traversal result
     * 
     * Order: Root → Left → Right
     * 
     * Example for tree:
     *        1
     *       / \
     *      2   3
     *     / \   \
     *    4   5   6
     * 
     * Execution:
     * preorder(1)
     *   → add 1
     *   → preorder(2)
     *     → add 2
     *     → preorder(4) → add 4
     *     → preorder(5) → add 5
     *   → preorder(3)
     *     → add 3
     *     → preorder(6) → add 6
     * 
     * Result: [1, 2, 4, 5, 3, 6]
     */
    public void preorder(TreeNode root, List<Integer> res) {
        // Base case: empty node
        if (root == null) return;
        
        // 1. Visit current node (ROOT FIRST)
        res.add(root.val);
        
        // 2. Traverse left subtree
        preorder(root.left, res);
        
        // 3. Traverse right subtree
        preorder(root.right, res);
    }
    
    /**
     * Alternative: Return new list (wrapper method)
     * Useful for single method call
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorder(root, result);
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

        PreOrder solver = new PreOrder();
        List<Integer> res = new ArrayList<>();
        solver.preorder(root, res);
        
        System.out.println("Preorder traversal: " + res); // [1, 2, 4, 5, 3, 6]
        
        // Test with wrapper method
        System.out.println("Using wrapper: " + solver.preorderTraversal(root));
        
        // Edge cases
        System.out.println("Empty tree: " + solver.preorderTraversal(null)); // []
        
        TreeNode single = new TreeNode(10);
        System.out.println("Single node: " + solver.preorderTraversal(single)); // [10]
        
        // Skewed tree tests
        TreeNode leftSkewed = new TreeNode(1);
        leftSkewed.left = new TreeNode(2);
        leftSkewed.left.left = new TreeNode(3);
        System.out.println("Left-skewed: " + solver.preorderTraversal(leftSkewed)); // [1, 2, 3]
        
        TreeNode rightSkewed = new TreeNode(1);
        rightSkewed.right = new TreeNode(2);
        rightSkewed.right.right = new TreeNode(3);
        System.out.println("Right-skewed: " + solver.preorderTraversal(rightSkewed)); // [1, 2, 3]
        
        // Expression tree example
        TreeNode exprTree = new TreeNode('+');
        exprTree.left = new TreeNode('*');
        exprTree.right = new TreeNode('-');
        exprTree.left.left = new TreeNode('a');
        exprTree.left.right = new TreeNode('b');
        exprTree.right.left = new TreeNode('c');
        exprTree.right.right = new TreeNode('d');
        
        // For expression tree: (a*b)+(c-d)
        // Preorder gives prefix notation: + * a b - c d
        List<Integer> exprResult = new ArrayList<>();
        solver.preorder(exprTree, exprResult);
        System.out.println("Expression tree: " + exprResult);
        // Output depends on char to int conversion
    }
}

/**
 * COMPARISON WITH OTHER TRAVERSALS:
 * 
 * PREORDER (Root → Left → Right) ← THIS IMPLEMENTATION
 *   void preorder(TreeNode root, List<Integer> res) {
 *       if (root == null) return;
 *       res.add(root.val);          // Root
 *       preorder(root.left, res);   // Left
 *       preorder(root.right, res);  // Right
 *   }
 * 
 * INORDER (Left → Root → Right):
 *   void inorder(TreeNode root, List<Integer> res) {
 *       if (root == null) return;
 *       inorder(root.left, res);    // Left
 *       res.add(root.val);          // Root
 *       inorder(root.right, res);   // Right
 *   }
 * 
 * POSTORDER (Left → Right → Root):
 *   void postorder(TreeNode root, List<Integer> res) {
 *       if (root == null) return;
 *       postorder(root.left, res);   // Left
 *       postorder(root.right, res);  // Right
 *       res.add(root.val);           // Root
 *   }
 */

/**
 * ITERATIVE VERSION (for comparison):
 * 
 * public List<Integer> preorderIterative(TreeNode root) {
 *     List<Integer> res = new ArrayList<>();
 *     if (root == null) return res;
 *     
 *     Stack<TreeNode> st = new Stack<>();
 *     st.push(root);
 *     
 *     while (!st.isEmpty()) {
 *         TreeNode node = st.pop();
 *         res.add(node.val);
 *         
 *         // Push right first, then left (LIFO)
 *         if (node.right != null) st.push(node.right);
 *         if (node.left != null) st.push(node.left);
 *     }
 *     return res;
 * }
 */

/**
 * APPLICATIONS OF PREORDER TRAVERSAL:
 * 
 * 1. Tree serialization/deserialization:
 *    - Preorder uniquely represents tree structure
 *    - Can reconstruct tree from preorder + inorder
 * 
 * 2. Expression tree evaluation (prefix notation):
 *    - Example: (a*b)+(c-d) → "+ * a b - c d"
 *    - Useful for compilers and calculators
 * 
 * 3. Copying tree structure:
 *    - Create new nodes in same order
 * 
 * 4. Finding path from root to node:
 *    - Preorder visits root first
 * 
 * 5. Directory tree listing:
 *    - Show directory then its contents
 */

/**
 * TIME & SPACE ANALYSIS:
 * 
 * Time Complexity: O(n)
 *   - Each node visited exactly once
 *   - Constant work per node (check null, add to list, recursive calls)
 * 
 * Space Complexity: O(h) where h = height of tree
 *   - Best case (balanced tree): h = log₂n → O(log n)
 *   - Worst case (skewed tree): h = n → O(n)
 *   - Space used by recursion stack
 *   - Result list O(n) not counted as extra space (it's output)
 */

/**
 * PREORDER PROPERTIES:
 * 
 * 1. First element is always the root
 * 2. Left subtree appears before right subtree
 * 3. Natural order for many tree operations
 * 4. Can be used to clone/copy tree
 * 5. Basis for prefix notation in expression trees
 */

/**
 * WHEN TO USE RECURSIVE VS ITERATIVE:
 * 
 * RECURSIVE (this implementation):
 *   + Simple, clean, natural for tree problems
 *   + Easy to understand and implement
 *   + Less code, more readable
 *   - Risk of stack overflow for very deep trees
 *   - Harder to pause/resume
 * 
 * ITERATIVE:
 *   + No recursion limit
 *   + More control over execution
 *   + Can handle very deep trees
 *   - More complex code
 *   - Manual stack management
 *   - Less intuitive
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Wrong order of operations:
 *    - Should be: root, left, right
 *    - Common error: left, root, right (inorder) or left, right, root (postorder)
 * 
 * 2. Forgetting base case (root == null):
 *    - Leads to StackOverflowError
 * 
 * 3. Not passing result list correctly:
 *    - Need to pass as parameter or return new list
 * 
 * 4. Using recursion on extremely deep trees:
 *    - Consider iterative approach for millions of nodes
 * 
 * 5. Confusing with other traversals:
 *    - Remember: PRE-order = ROOT first
 */

/**
 * TEST CASES TO VERIFY:
 * 
 * 1. Empty tree → []
 * 2. Single node → [value]
 * 3. Left-skewed tree → nodes in insertion order
 * 4. Right-skewed tree → nodes in insertion order
 * 5. Complete binary tree → verify structure
 * 6. Expression tree → verify prefix notation
 */

/**
 * RECURSION VISUALIZATION:
 * 
 * For tree:       1
 *                / \
 *               2   3
 * 
 * Call stack:
 * preorder(1)
 *   add 1
 *   preorder(2)
 *     add 2
 *     preorder(null) → return
 *     preorder(null) → return
 *   preorder(3)
 *     add 3
 *     preorder(null) → return
 *     preorder(null) → return
 * 
 * Result: [1, 2, 3]
 */

/**
 * ADVANTAGES OF PREORDER:
 * 
 * 1. Natural for tree creation/cloning
 * 2. Root-first approach useful for many algorithms
 * 3. Simple to implement recursively
 * 4. Basis for other algorithms (serialization)
 * 
 * DISADVANTAGES:
 * 
 * 1. Not sorted for Binary Search Trees
 * 2. Recursive version has stack overflow risk
 * 3. Not optimal for bottom-up computations
 */