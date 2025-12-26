import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Iterative Inorder Traversal using a stack.
 * 
 * INORDER TRAVERSAL (Iterative Approach):
 * Process: Left subtree → Root → Right subtree
 * Algorithm uses explicit stack to simulate recursion
 * 
 * KEY IDEA: 
 * 1. Go to leftmost node (push all left children)
 * 2. Visit node (pop from stack)  
 * 3. Move to right subtree
 * 
 * TIME COMPLEXITY: O(n) - Each node visited once
 * SPACE COMPLEXITY: O(h) - Stack stores at most height of tree
 *   - Best case (balanced): O(log n)
 *   - Worst case (skewed): O(n)
 */
public class IterInorder {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    /**
     * Iterative Inorder Traversal
     * @param root - Root of binary tree
     * @return List of values in inorder order
     * 
     * ALGORITHM STEPS:
     * 1. Initialize empty result list and stack
     * 2. Set current node = root
     * 3. While (current != null OR stack not empty):
     *    a. Go to leftmost: while(current != null):
     *       - push current to stack
     *       - current = current.left
     *    b. Visit node: current = stack.pop()
     *       - add current.val to result
     *    c. Move to right: current = current.right
     */
    public List<Integer> inorderIterative(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> st = new Stack<>();
        TreeNode curr = root;
        
        while (curr != null || !st.isEmpty()) {
            // Step 1: Go to leftmost node (push all left children)
            while (curr != null) {
                st.push(curr);
                curr = curr.left;
            }
            
            // Step 2: Visit node (leftmost or parent)
            curr = st.pop();
            res.add(curr.val);
            
            // Step 3: Move to right subtree
            curr = curr.right;
        }
        return res;
    }
    
    /**
     * Alternative implementation with more explicit steps
     * Easier for beginners to understand
     */
    public List<Integer> inorderIterativeAlt(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        
        while (true) {
            // If current exists, go to its leftmost
            if (current != null) {
                stack.push(current);
                current = current.left;
            } 
            // If current is null but stack has nodes
            else if (!stack.isEmpty()) {
                current = stack.pop();
                result.add(current.val);  // Visit node
                current = current.right;  // Go to right subtree
            }
            // Both current and stack are empty - done
            else {
                break;
            }
        }
        return result;
    }

    /**
     * Visual Walkthrough for tree:
     *        1
     *       / \
     *      2   3
     *     / \   \
     *    4   5   6
     * 
     * Execution trace:
     * 
     * Initial: curr=1, stack=[]
     * 
     * Outer while: curr=1 not null
     *   Inner while: push 1, curr=2
     *                push 2, curr=4
     *                push 4, curr=null
     *   
     *   curr=stack.pop()=4, res=[4], curr=4.right=null
     *   
     * Outer while: curr=null, stack=[1,2]
     *   curr=stack.pop()=2, res=[4,2], curr=2.right=5
     *   
     * Outer while: curr=5 not null
     *   Inner while: push 5, curr=null
     *   
     *   curr=stack.pop()=5, res=[4,2,5], curr=5.right=null
     *   
     * Outer while: curr=null, stack=[1]
     *   curr=stack.pop()=1, res=[4,2,5,1], curr=1.right=3
     *   
     * Outer while: curr=3 not null
     *   Inner while: push 3, curr=null
     *   
     *   curr=stack.pop()=3, res=[4,2,5,1,3], curr=3.right=6
     *   
     * Outer while: curr=6 not null
     *   Inner while: push 6, curr=null
     *   
     *   curr=stack.pop()=6, res=[4,2,5,1,3,6], curr=6.right=null
     *   
     * Done. Result: [4,2,5,1,3,6]
     */

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

        IterInorder solver = new IterInorder();
        
        System.out.println("Iterative Inorder: " + solver.inorderIterative(root));
        // Expected: [4, 2, 5, 1, 3, 6]
        
        System.out.println("Alternative: " + solver.inorderIterativeAlt(root));
        // Same expected result
        
        // Test edge cases
        System.out.println("Empty tree: " + solver.inorderIterative(null)); // []
        
        TreeNode single = new TreeNode(10);
        System.out.println("Single node: " + solver.inorderIterative(single)); // [10]
        
        // Left-skewed tree
        TreeNode leftSkewed = new TreeNode(3);
        leftSkewed.left = new TreeNode(2);
        leftSkewed.left.left = new TreeNode(1);
        System.out.println("Left-skewed: " + solver.inorderIterative(leftSkewed)); // [1, 2, 3]
        
        // Right-skewed tree (tests stack usage)
        TreeNode rightSkewed = new TreeNode(1);
        rightSkewed.right = new TreeNode(2);
        rightSkewed.right.right = new TreeNode(3);
        System.out.println("Right-skewed: " + solver.inorderIterative(rightSkewed)); // [1, 2, 3]
        
        // BST test
        TreeNode bst = new TreeNode(4);
        bst.left = new TreeNode(2);
        bst.right = new TreeNode(6);
        bst.left.left = new TreeNode(1);
        bst.left.right = new TreeNode(3);
        bst.right.left = new TreeNode(5);
        bst.right.right = new TreeNode(7);
        System.out.println("BST (sorted): " + solver.inorderIterative(bst)); // [1, 2, 3, 4, 5, 6, 7]
    }
}

/**
 * COMPARISON WITH RECURSIVE VERSION:
 * 
 * RECURSIVE:
 *   void inorder(TreeNode root, List<Integer> res) {
 *       if (root == null) return;
 *       inorder(root.left, res);
 *       res.add(root.val);
 *       inorder(root.right, res);
 *   }
 * 
 * ITERATIVE (this implementation):
 *   - Uses explicit Stack instead of call stack
 *   - Avoids recursion limit issues for deep trees
 *   - More control over traversal
 *   - Slightly more complex to implement
 */

/**
 * OTHER ITERATIVE TRAVERSALS FOR REFERENCE:
 * 
 * PREORDER (Iterative):
 *   public List<Integer> preorder(TreeNode root) {
 *       List<Integer> res = new ArrayList<>();
 *       if (root == null) return res;
 *       Stack<TreeNode> st = new Stack<>();
 *       st.push(root);
 *       while (!st.isEmpty()) {
 *           TreeNode node = st.pop();
 *           res.add(node.val);
 *           if (node.right != null) st.push(node.right);
 *           if (node.left != null) st.push(node.left);
 *       }
 *       return res;
 *   }
 * 
 * POSTORDER (Iterative using 2 stacks):
 *   public List<Integer> postorder(TreeNode root) {
 *       List<Integer> res = new ArrayList<>();
 *       if (root == null) return res;
 *       Stack<TreeNode> s1 = new Stack<>();
 *       Stack<TreeNode> s2 = new Stack<>();
 *       s1.push(root);
 *       while (!s1.isEmpty()) {
 *           TreeNode node = s1.pop();
 *           s2.push(node);
 *           if (node.left != null) s1.push(node.left);
 *           if (node.right != null) s1.push(node.right);
 *       }
 *       while (!s2.isEmpty()) res.add(s2.pop().val);
 *       return res;
 *   }
 */

/**
 * ADVANTAGES OF ITERATIVE APPROACH:
 * 1. No recursion limit (can handle very deep trees)
 * 2. More memory control (explicit stack)
 * 3. Can pause/resume traversal
 * 4. Easier to debug (visible stack state)
 * 
 * DISADVANTAGES:
 * 1. More complex code
 * 2. Manual stack management
 * 3. Less intuitive than recursion
 */

/**
 * COMMON MISTAKES TO AVOID:
 * 1. Forgetting to check while(curr != null) in outer loop
 * 2. Not resetting curr to right after popping
 * 3. Using wrong stack operations (push/pop)
 * 4. Infinite loop if conditions wrong
 * 5. Not handling empty tree case
 */

/**
 * OPTIMIZATIONS:
 * 1. Use ArrayDeque instead of Stack for better performance
 * 2. Pre-allocate result list capacity if tree size known
 * 3. Use Morris traversal for O(1) space (but modifies tree)
 */

/**
 * TIME & SPACE ANALYSIS:
 * Time: O(n) - Each node visited exactly once
 * Space: O(h) - Stack stores path from root to current node
 *   - Balanced tree: O(log n)
 *   - Skewed tree: O(n)
 *   
 * Each node: pushed once, popped once → 2 operations per node
 */