import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Iterative Postorder Traversal using one stack.
 * 
 * POSTORDER TRAVERSAL: Left → Right → Root
 * Most complex iterative traversal (requires tracking last visited node)
 * 
 * ALGORITHM (Single Stack):
 * 1. Initialize stack, curr = root, lastVisited = null
 * 2. While (curr != null OR stack not empty):
 *    a. If curr != null: push curr, go left
 *    b. Else: peek top of stack
 *       - If right child exists AND not visited yet: go right
 *       - Else: visit node, pop, mark as lastVisited
 * 
 * TIME: O(n) - each node visited once
 * SPACE: O(h) - stack height (worst O(n))
 */
public class Post1Stack {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    /**
     * Postorder Traversal using Single Stack
     * @param root - Root of binary tree
     * @return List of values in postorder
     * 
     * KEY CONCEPT: Need to track last visited node
     * to know if we should process current node or go to right child
     */
    public List<Integer> postorderOneStack(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> st = new Stack<>();
        TreeNode curr = root;
        TreeNode lastVisited = null;
        
        while (curr != null || !st.isEmpty()) {
            if (curr != null) {
                // Go to leftmost node
                st.push(curr);
                curr = curr.left;
            } else {
                // At null, check top of stack
                TreeNode peek = st.peek();
                
                // If right child exists and not visited yet
                if (peek.right != null && lastVisited != peek.right) {
                    // Go to right subtree
                    curr = peek.right;
                } else {
                    // Visit node (left done, right done or null)
                    res.add(peek.val);
                    lastVisited = st.pop();
                }
            }
        }
        return res;
    }
    
    /**
     * Alternative: More readable version with comments
     */
    public List<Integer> postorderOneStackAlt(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        TreeNode lastVisited = null;
        
        while (current != null || !stack.isEmpty()) {
            // Go to leftmost
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            
            // Check top node
            TreeNode top = stack.peek();
            
            // If right child exists and not just visited
            if (top.right != null && top.right != lastVisited) {
                // Process right subtree
                current = top.right;
            } else {
                // Visit this node
                result.add(top.val);
                lastVisited = stack.pop();
            }
        }
        return result;
    }
    
    /**
     * Postorder using 2 stacks (easier to understand)
     * Algorithm:
     * 1. Push root to stack1
     * 2. While stack1 not empty:
     *    - Pop node, push to stack2
     *    - Push left then right to stack1
     * 3. Pop all from stack2 for postorder
     */
    public List<Integer> postorderTwoStacks(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        
        Stack<TreeNode> s1 = new Stack<>();
        Stack<TreeNode> s2 = new Stack<>();
        s1.push(root);
        
        while (!s1.isEmpty()) {
            TreeNode node = s1.pop();
            s2.push(node);
            
            // Push left then right (so right processes first in s2)
            if (node.left != null) s1.push(node.left);
            if (node.right != null) s1.push(node.right);
        }
        
        while (!s2.isEmpty()) {
            res.add(s2.pop().val);
        }
        return res;
    }

    /**
     * Visual Walkthrough for tree:
     *        1
     *       / \
     *      2   3
     *     / \   \
     *    4   5   6
     * 
     * Single Stack Execution:
     * 
     * Initial: curr=1, lastVisited=null, stack=[]
     * 
     * Step 1: curr=1 not null → push 1, curr=2
     * Step 2: curr=2 not null → push 2, curr=4
     * Step 3: curr=4 not null → push 4, curr=null
     * 
     * Step 4: curr=null, peek=4, right=null → visit 4, pop 4, lastVisited=4
     * Step 5: curr=null, peek=2, right=5, lastVisited=4 != 5 → curr=5
     * 
     * Step 6: curr=5 not null → push 5, curr=null
     * Step 7: curr=null, peek=5, right=null → visit 5, pop 5, lastVisited=5
     * Step 8: curr=null, peek=2, right=5, lastVisited=5 == 5 → visit 2, pop 2, lastVisited=2
     * 
     * Step 9: curr=null, peek=1, right=3, lastVisited=2 != 3 → curr=3
     * Step 10: curr=3 not null → push 3, curr=null
     * 
     * Step 11: curr=null, peek=3, right=6, lastVisited=2 != 6 → curr=6
     * Step 12: curr=6 not null → push 6, curr=null
     * 
     * Step 13: curr=null, peek=6, right=null → visit 6, pop 6, lastVisited=6
     * Step 14: curr=null, peek=3, right=6, lastVisited=6 == 6 → visit 3, pop 3, lastVisited=3
     * Step 15: curr=null, peek=1, right=3, lastVisited=3 == 3 → visit 1, pop 1, lastVisited=1
     * 
     * Result: [4, 5, 2, 6, 3, 1]
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

        Post1Stack solver = new Post1Stack();
        
        System.out.println("Postorder (1 stack): " + solver.postorderOneStack(root));
        // Expected: [4, 5, 2, 6, 3, 1]
        
        System.out.println("Alternative (1 stack): " + solver.postorderOneStackAlt(root));
        // Same expected result
        
        System.out.println("Postorder (2 stacks): " + solver.postorderTwoStacks(root));
        // Same expected result
        
        // Test edge cases
        System.out.println("Empty tree: " + solver.postorderOneStack(null)); // []
        
        TreeNode single = new TreeNode(10);
        System.out.println("Single node: " + solver.postorderOneStack(single)); // [10]
        
        // Left-skewed tree
        TreeNode leftSkewed = new TreeNode(1);
        leftSkewed.left = new TreeNode(2);
        leftSkewed.left.left = new TreeNode(3);
        System.out.println("Left-skewed: " + solver.postorderOneStack(leftSkewed)); // [3, 2, 1]
        
        // Right-skewed tree
        TreeNode rightSkewed = new TreeNode(1);
        rightSkewed.right = new TreeNode(2);
        rightSkewed.right.right = new TreeNode(3);
        System.out.println("Right-skewed: " + solver.postorderOneStack(rightSkewed)); // [3, 2, 1]
        
        // Complete binary tree
        TreeNode complete = new TreeNode(1);
        complete.left = new TreeNode(2);
        complete.right = new TreeNode(3);
        complete.left.left = new TreeNode(4);
        complete.left.right = new TreeNode(5);
        complete.right.left = new TreeNode(6);
        complete.right.right = new TreeNode(7);
        System.out.println("Complete binary: " + solver.postorderOneStack(complete));
        // Expected: [4, 5, 2, 6, 7, 3, 1]
    }
}

/**
 * COMPARISON WITH OTHER TRAVERSALS:
 * 
 * PREORDER (Root → Left → Right):
 *   - Simple: push root, pop, push right, push left
 * 
 * INORDER (Left → Root → Right):
 *   - Medium: go left, visit, go right
 * 
 * POSTORDER (Left → Right → Root):
 *   - Complex: need lastVisited tracking
 *   - Most challenging iterative traversal
 */

/**
 * WHY LASTVISITED TRACKING?
 * 
 * Problem: After processing left subtree, we need to know:
 * 1. Should we go to right subtree? OR
 * 2. Should we visit current node?
 * 
 * Solution: Track last visited node:
 * - If lastVisited == right child: right subtree done, visit node
 * - Else: go to right subtree
 */

/**
 * 2-STACK APPROACH vs 1-STACK:
 * 
 * 2-STACK (Easier):
 *   + Simpler to understand
 *   + No lastVisited tracking
 *   - Uses O(2h) space (two stacks)
 *   - Extra stack operations
 * 
 * 1-STACK (Efficient):
 *   + Uses O(h) space
 *   + Fewer operations
 *   - More complex logic
 *   - Harder to debug
 */

/**
 * TIME & SPACE ANALYSIS (1-stack):
 * 
 * Time Complexity: O(n) - Each node visited once
 * Space Complexity: O(h) - Stack stores path from root to current
 *   - Best case (balanced): O(log n)
 *   - Worst case (skewed): O(n)
 * 
 * Each node: 
 *   - Pushed once
 *   - Popped once  
 *   - Peeked multiple times (constant)
 */

/**
 * APPLICATIONS OF POSTORDER:
 * 
 * 1. Deleting binary tree (free children before parent)
 * 2. Expression tree evaluation (postfix)
 * 3. Computing height/depth of nodes
 * 4. Bottom-up calculations
 * 5. Tree to postfix conversion
 */

/**
 * RECURSIVE POSTORDER FOR REFERENCE:
 * 
 * void postorder(TreeNode root, List<Integer> res) {
 *     if (root == null) return;
 *     postorder(root.left, res);   // Left
 *     postorder(root.right, res);  // Right
 *     res.add(root.val);           // Root
 * }
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Forgetting to check if right child is null
 * 2. Not comparing lastVisited correctly (should be != not ==)
 * 3. Using peek().right instead of storing in variable
 * 4. Infinite loop if conditions wrong
 * 5. Not handling empty tree case
 */

/**
 * DEBUGGING TIPS:
 * 
 * 1. Print stack and lastVisited at each step
 * 2. Draw tree and trace execution
 * 3. Test with small trees first
 * 4. Compare with recursive result
 */

/**
 * OPTIMIZATIONS:
 * 
 * 1. Use ArrayDeque instead of Stack
 * 2. Pre-allocate result list if tree size known
 * 3. For complete trees, 2-stack may be simpler
 */