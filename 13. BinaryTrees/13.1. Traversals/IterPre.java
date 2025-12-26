import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Iterative Preorder Traversal using a stack.
 * 
 * PREORDER TRAVERSAL (Iterative Approach):
 * Process: Root → Left subtree → Right subtree
 * 
 * ALGORITHM: Use stack, push right then left (LIFO)
 * 1. Push root to stack
 * 2. While stack not empty:
 *    a. Pop node, add to result (VISIT ROOT)
 *    b. Push right child (if exists)
 *    c. Push left child (if exists)
 * 
 * TIME: O(n) - each node visited once
 * SPACE: O(h) - stack height (worst O(n) for skewed)
 */
public class IterPre {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    /**
     * Iterative Preorder Traversal
     * @param root - Root of binary tree
     * @return List of values in preorder
     * 
     * KEY INSIGHT: Stack is LIFO, so push right before left
     * to process left first
     */
    public List<Integer> preorderIterative(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        
        Stack<TreeNode> st = new Stack<>();
        st.push(root);
        
        while (!st.isEmpty()) {
            TreeNode node = st.pop();
            res.add(node.val);           // VISIT ROOT
            
            // Push right first (LIFO: will be processed after left)
            if (node.right != null) st.push(node.right);
            if (node.left != null) st.push(node.left);
        }
        return res;
    }
    
    /**
     * Alternative: Right-to-left stack approach
     * Similar logic, different order of pushing
     */
    public List<Integer> preorderIterative2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            result.add(current.val);
            
            // Right then left for LIFO order
            if (current.right != null) {
                stack.push(current.right);
            }
            if (current.left != null) {
                stack.push(current.left);
            }
        }
        return result;
    }
    
    /**
     * Alternative 2: Using only right children in stack
     * More memory efficient for certain trees
     */
    public List<Integer> preorderIterative3(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> rights = new Stack<>();
        TreeNode current = root;
        
        while (current != null) {
            result.add(current.val);  // Visit
            
            // Save right child for later
            if (current.right != null) {
                rights.push(current.right);
            }
            
            // Move to left child
            current = current.left;
            
            // If no left, get right from stack
            if (current == null && !rights.isEmpty()) {
                current = rights.pop();
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
     * Initial: stack=[1]
     * 
     * Iteration 1: pop=1, res=[1]
     *   push 3 (right), push 2 (left)
     *   stack=[3,2]
     *   
     * Iteration 2: pop=2, res=[1,2]
     *   push 5 (right), push 4 (left)
     *   stack=[3,5,4]
     *   
     * Iteration 3: pop=4, res=[1,2,4]
     *   no children
     *   stack=[3,5]
     *   
     * Iteration 4: pop=5, res=[1,2,4,5]
     *   no children
     *   stack=[3]
     *   
     * Iteration 5: pop=3, res=[1,2,4,5,3]
     *   push 6 (right), no left
     *   stack=[6]
     *   
     * Iteration 6: pop=6, res=[1,2,4,5,3,6]
     *   no children
     *   stack=[]
     *   
     * Result: [1,2,4,5,3,6]
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

        IterPre solver = new IterPre();
        
        System.out.println("Iterative Preorder: " + solver.preorderIterative(root));
        // Expected: [1, 2, 4, 5, 3, 6]
        
        System.out.println("Alternative 1: " + solver.preorderIterative2(root));
        // Same expected result
        
        System.out.println("Alternative 2: " + solver.preorderIterative3(root));
        // Same expected result
        
        // Test edge cases
        System.out.println("Empty tree: " + solver.preorderIterative(null)); // []
        
        TreeNode single = new TreeNode(10);
        System.out.println("Single node: " + solver.preorderIterative(single)); // [10]
        
        // Left-skewed tree
        TreeNode leftSkewed = new TreeNode(1);
        leftSkewed.left = new TreeNode(2);
        leftSkewed.left.left = new TreeNode(3);
        System.out.println("Left-skewed: " + solver.preorderIterative(leftSkewed)); // [1, 2, 3]
        
        // Right-skewed tree
        TreeNode rightSkewed = new TreeNode(1);
        rightSkewed.right = new TreeNode(2);
        rightSkewed.right.right = new TreeNode(3);
        System.out.println("Right-skewed: " + solver.preorderIterative(rightSkewed)); // [1, 2, 3]
        
        // Complete binary tree
        TreeNode complete = new TreeNode(1);
        complete.left = new TreeNode(2);
        complete.right = new TreeNode(3);
        complete.left.left = new TreeNode(4);
        complete.left.right = new TreeNode(5);
        complete.right.left = new TreeNode(6);
        complete.right.right = new TreeNode(7);
        System.out.println("Complete binary: " + solver.preorderIterative(complete));
        // Expected: [1, 2, 4, 5, 3, 6, 7]
    }
}

/**
 * COMPARISON WITH RECURSIVE VERSION:
 * 
 * RECURSIVE PREORDER:
 *   void preorder(TreeNode root, List<Integer> res) {
 *       if (root == null) return;
 *       res.add(root.val);          // Root
 *       preorder(root.left, res);   // Left
 *       preorder(root.right, res);  // Right
 *   }
 * 
 * ITERATIVE (this implementation):
 *   - Simulates recursion with explicit stack
 *   - Pushes right before left (LIFO order)
 *   - Visits root, then left, then right
 */

/**
 * PREORDER TRAVERSAL PROPERTIES:
 * 
 * 1. First element is always root
 * 2. Good for copying tree structure
 * 3. Used in prefix expression evaluation
 * 4. Can reconstruct tree from preorder + inorder
 * 5. Serialization of binary trees
 */

/**
 * OTHER TRAVERSALS FOR REFERENCE:
 * 
 * INORDER (Iterative):
 *   while(curr != null || !stack.isEmpty()) {
 *       while(curr != null) { stack.push(curr); curr = curr.left; }
 *       curr = stack.pop();
 *       res.add(curr.val);
 *       curr = curr.right;
 *   }
 * 
 * POSTORDER (Iterative with 2 stacks):
 *   s1.push(root);
 *   while(!s1.isEmpty()) {
 *       TreeNode node = s1.pop();
 *       s2.push(node);
 *       if(node.left != null) s1.push(node.left);
 *       if(node.right != null) s1.push(node.right);
 *   }
 *   while(!s2.isEmpty()) res.add(s2.pop().val);
 */

/**
 * WHY PUSH RIGHT BEFORE LEFT?
 * 
 * Stack is LIFO (Last-In-First-Out):
 * - If we push left then right: right will be processed before left ❌
 * - If we push right then left: left will be processed before right ✓
 * 
 * Preorder: Root → Left → Right
 * So left should be processed before right
 */

/**
 * TIME & SPACE ANALYSIS:
 * 
 * Time Complexity: O(n) - Each node visited once
 * Space Complexity: O(h) - Maximum stack size = tree height
 *   - Best case (balanced tree): O(log n)
 *   - Worst case (skewed tree): O(n)
 *   
 * Each node: pushed once, popped once → 2 operations per node
 */

/**
 * APPLICATIONS:
 * 
 * 1. Tree serialization/deserialization
 * 2. Expression tree evaluation (prefix)
 * 3. Copying tree structure
 * 4. Finding root-to-leaf paths
 * 5. Prefix notation conversion
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Pushing left before right (wrong order)
 * 2. Forgetting null check for root
 * 3. Not checking if children exist before pushing
 * 4. Using queue instead of stack (would give level order)
 * 5. Infinite loop if stack operations incorrect
 */