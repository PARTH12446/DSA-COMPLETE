import java.util.ArrayList;
import java.util.List;

/**
 * Binary Tree Postorder Traversal (left, right, root) - recursive version.
 * 
 * POSTORDER TRAVERSAL: Visit left subtree → right subtree → root
 * 
 * RECURSIVE APPROACH:
 * 1. Base case: if node is null, return
 * 2. Recursively traverse left subtree
 * 3. Recursively traverse right subtree
 * 4. Visit current node (add to result)
 * 
 * TIME: O(n) - each node visited once
 * SPACE: O(h) - recursion stack height (h = tree height)
 */
public class PostOrder {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    /**
     * Recursive postorder traversal
     * @param root - current tree node
     * @param res  - list to store traversal result
     * 
     * Order: Left → Right → Root
     * 
     * Example for tree:
     *        1
     *       / \
     *      2   3
     *     / \   \
     *    4   5   6
     * 
     * Execution:
     * postorder(1)
     *   → postorder(2)
     *     → postorder(4) → add 4
     *     → postorder(5) → add 5
     *     → add 2
     *   → postorder(3)
     *     → postorder(null) (3.left)
     *     → postorder(6) → add 6
     *     → add 3
     *   → add 1
     * 
     * Result: [4, 5, 2, 6, 3, 1]
     */
    public void postorder(TreeNode root, List<Integer> res) {
        // Base case: empty node
        if (root == null) return;
        
        // 1. Traverse left subtree
        postorder(root.left, res);
        
        // 2. Traverse right subtree
        postorder(root.right, res);
        
        // 3. Visit current node (ROOT LAST)
        res.add(root.val);
    }
    
    /**
     * Alternative: Return new list (wrapper method)
     * Useful for single method call
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorder(root, result);
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

        PostOrder solver = new PostOrder();
        List<Integer> res = new ArrayList<>();
        solver.postorder(root, res);
        
        System.out.println("Postorder traversal: " + res); // [4, 5, 2, 6, 3, 1]
        
        // Test with wrapper method
        System.out.println("Using wrapper: " + solver.postorderTraversal(root));
        
        // Edge cases
        System.out.println("Empty tree: " + solver.postorderTraversal(null)); // []
        
        TreeNode single = new TreeNode(10);
        System.out.println("Single node: " + solver.postorderTraversal(single)); // [10]
        
        // Skewed tree test
        TreeNode skewed = new TreeNode(1);
        skewed.right = new TreeNode(2);
        skewed.right.right = new TreeNode(3);
        System.out.println("Right-skewed: " + solver.postorderTraversal(skewed)); // [3, 2, 1]
        
        // Expression tree example
        TreeNode exprTree = new TreeNode('*');
        exprTree.left = new TreeNode('+');
        exprTree.right = new TreeNode('-');
        exprTree.left.left = new TreeNode('a');
        exprTree.left.right = new TreeNode('b');
        exprTree.right.left = new TreeNode('c');
        exprTree.right.right = new TreeNode('d');
        
        // For expression tree: (a+b)*(c-d)
        // Postorder gives postfix notation: a b + c d - *
        List<Integer> exprResult = new ArrayList<>();
        solver.postorder(exprTree, exprResult);
        System.out.println("Expression tree: " + exprResult);
        // Output depends on char to int conversion
    }
}

/**
 * COMPARISON WITH OTHER TRAVERSALS:
 * 
 * PREORDER (Root → Left → Right):
 *   void preorder(TreeNode root, List<Integer> res) {
 *       if (root == null) return;
 *       res.add(root.val);
 *       preorder(root.left, res);
 *       preorder(root.right, res);
 *   }
 * 
 * INORDER (Left → Root → Right):
 *   void inorder(TreeNode root, List<Integer> res) {
 *       if (root == null) return;
 *       inorder(root.left, res);
 *       res.add(root.val);
 *       inorder(root.right, res);
 *   }
 * 
 * POSTORDER (Left → Right → Root) ← THIS IMPLEMENTATION
 *   void postorder(TreeNode root, List<Integer> res) {
 *       if (root == null) return;
 *       postorder(root.left, res);
 *       postorder(root.right, res);
 *       res.add(root.val);
 *   }
 */

/**
 * ITERATIVE VERSIONS (for comparison):
 * 
 * 1. Using Two Stacks (easier):
 *    public List<Integer> postorderTwoStacks(TreeNode root) {
 *        List<Integer> res = new ArrayList<>();
 *        if (root == null) return res;
 *        
 *        Stack<TreeNode> s1 = new Stack<>();
 *        Stack<TreeNode> s2 = new Stack<>();
 *        s1.push(root);
 *        
 *        while (!s1.isEmpty()) {
 *            TreeNode node = s1.pop();
 *            s2.push(node);
 *            if (node.left != null) s1.push(node.left);
 *            if (node.right != null) s1.push(node.right);
 *        }
 *        
 *        while (!s2.isEmpty()) res.add(s2.pop().val);
 *        return res;
 *    }
 * 
 * 2. Using One Stack (more complex):
 *    public List<Integer> postorderOneStack(TreeNode root) {
 *        List<Integer> res = new ArrayList<>();
 *        Stack<TreeNode> st = new Stack<>();
 *        TreeNode curr = root, lastVisited = null;
 *        
 *        while (curr != null || !st.isEmpty()) {
 *            if (curr != null) {
 *                st.push(curr);
 *                curr = curr.left;
 *            } else {
 *                TreeNode peek = st.peek();
 *                if (peek.right != null && lastVisited != peek.right) {
 *                    curr = peek.right;
 *                } else {
 *                    res.add(peek.val);
 *                    lastVisited = st.pop();
 *                }
 *            }
 *        }
 *        return res;
 *    }
 */

/**
 * APPLICATIONS OF POSTORDER TRAVERSAL:
 * 
 * 1. Deleting/freeing binary tree:
 *    - Must delete children before parent
 *    - Postorder ensures children processed first
 * 
 * 2. Expression tree evaluation:
 *    - Postorder gives postfix notation
 *    - Example: (a+b)*(c-d) → "a b + c d - *"
 *    - Easy to evaluate using stack
 * 
 * 3. Calculating height/depth of nodes:
 *    - Need children's height to compute parent's
 *    - Bottom-up approach
 * 
 * 4. Computing disk space in directory tree
 * 
 * 5. Bottom-up dynamic programming on trees
 */

/**
 * TIME & SPACE ANALYSIS:
 * 
 * Time Complexity: O(n)
 *   - Each node visited exactly once
 *   - Constant work per node (check null, recursive calls, add to list)
 * 
 * Space Complexity: O(h) where h = height of tree
 *   - Best case (balanced tree): h = log₂n → O(log n)
 *   - Worst case (skewed tree): h = n → O(n)
 *   - Space used by recursion stack
 *   - Result list O(n) not counted as extra space (it's output)
 */

/**
 * WHEN TO USE RECURSIVE VS ITERATIVE:
 * 
 * RECURSIVE (this implementation):
 *   + Simple, clean, intuitive
 *   + Easy to understand and write
 *   + Natural for tree problems
 *   - Risk of stack overflow for deep trees
 *   - Less control over stack usage
 * 
 * ITERATIVE:
 *   + No recursion limit
 *   + More control over memory
 *   + Can pause/resume traversal
 *   - More complex code
 *   - Harder to debug
 *   - Less intuitive
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Wrong order of recursive calls
 *    - Should be: left, right, root
 *    - Common error: root, left, right (preorder) or left, root, right (inorder)
 * 
 * 2. Forgetting base case (root == null)
 *    - Leads to StackOverflowError
 * 
 * 3. Not passing result list correctly
 *    - Need to pass as parameter or return
 * 
 * 4. Using recursion on very deep trees
 *    - Consider iterative version for deep trees
 * 
 * 5. Confusing postorder with other traversals
 */

/**
 * TEST CASES TO VERIFY:
 * 
 * 1. Empty tree → []
 * 2. Single node → [value]
 * 3. Left-skewed tree → nodes in reverse insertion order
 * 4. Right-skewed tree → nodes in insertion order
 * 5. Complete binary tree → verify level structure
 * 6. Expression tree → verify postfix notation
 */

/**
 * RECURSION VISUALIZATION:
 * 
 * For tree:       1
 *                / \
 *               2   3
 * 
 * Call stack:
 * postorder(1)
 *   postorder(2)
 *     postorder(null) → return
 *     postorder(null) → return
 *     add 2
 *   postorder(3)
 *     postorder(null) → return
 *     postorder(null) → return
 *     add 3
 *   add 1
 * 
 * Result: [2, 3, 1]
 */