import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Demonstrates all basic traversals on the same tree.
 * 
 * TREE STRUCTURE used in examples:
 *        1
 *       / \
 *      2   3
 *     / \   \
 *    4   5   6
 * 
 * PREORDER: Root → Left → Right = [1, 2, 4, 5, 3, 6]
 * INORDER: Left → Root → Right = [4, 2, 5, 1, 3, 6]  
 * POSTORDER: Left → Right → Root = [4, 5, 2, 6, 3, 1]
 * LEVELORDER: Level by level = [[1], [2, 3], [4, 5, 6]]
 */
public class AllTraversals {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    /**
     * PREORDER TRAVERSAL (Iterative)
     * Algorithm: Use stack, visit node, push right then left
     * Time: O(n) where n = number of nodes
     * Space: O(h) where h = height of tree (worst O(n) for skewed)
     * 
     * Process: Root → Left → Right
     * Stack order: Push right first, then left (LIFO)
     */
    public List<Integer> preorder(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        
        Stack<TreeNode> st = new Stack<>();
        st.push(root);
        
        while (!st.isEmpty()) {
            TreeNode node = st.pop();
            res.add(node.val);               // VISIT ROOT
            
            // Push right first so left gets processed first (LIFO)
            if (node.right != null) st.push(node.right);
            if (node.left != null) st.push(node.left);
        }
        return res;
    }

    /**
     * INORDER TRAVERSAL (Iterative)
     * Algorithm: Use stack to simulate recursion
     * 1. Go to leftmost node (push all left nodes)
     * 2. Visit node (top of stack)
     * 3. Move to right subtree
     * 
     * Time: O(n) - Each node pushed/popped once
     * Space: O(h) - Stack height
     * 
     * Process: Left → Root → Right
     */
    public List<Integer> inorder(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> st = new Stack<>();
        TreeNode curr = root;
        
        while (curr != null || !st.isEmpty()) {
            // Go to leftmost node (push all left children)
            while (curr != null) {
                st.push(curr);
                curr = curr.left;
            }
            
            // Visit node (leftmost)
            curr = st.pop();
            res.add(curr.val);               // VISIT NODE
            
            // Move to right subtree
            curr = curr.right;
        }
        return res;
    }

    /**
     * POSTORDER TRAVERSAL (Iterative using 2 stacks)
     * Algorithm: 
     * 1. Push root to stack1
     * 2. Pop from stack1, push to stack2
     * 3. Push left then right to stack1
     * 4. Finally pop all from stack2
     * 
     * Time: O(n) - Each node processed twice
     * Space: O(n) - Two stacks needed
     * 
     * Process: Left → Right → Root
     * Alternative: Can be done with 1 stack (more complex)
     */
    public List<Integer> postorder(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        
        Stack<TreeNode> s1 = new Stack<>();
        Stack<TreeNode> s2 = new Stack<>();
        s1.push(root);
        
        while (!s1.isEmpty()) {
            TreeNode node = s1.pop();
            s2.push(node);                    // Store in reverse order
            
            // Push left then right (so right pops first from s2)
            if (node.left != null) s1.push(node.left);
            if (node.right != null) s1.push(node.right);
        }
        
        // s2 contains nodes in reverse postorder
        while (!s2.isEmpty()) {
            res.add(s2.pop().val);
        }
        return res;
    }

    /**
     * LEVEL ORDER TRAVERSAL (BFS)
     * Algorithm: Use queue, process level by level
     * 1. Add root to queue
     * 2. Process all nodes at current level
     * 3. Add children to queue for next level
     * 
     * Time: O(n) - Each node visited once
     * Space: O(w) where w = max width of tree
     * 
     * Returns: List of levels (each level is a list)
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();          // Nodes at current level
            List<Integer> level = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);               // Add to current level
                
                // Add children for next level
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            result.add(level);                     // Store completed level
        }
        return result;
    }

    /**
     * RECURSIVE VERSIONS (for comparison)
     * 
     * Preorder recursive:
     * void preorder(TreeNode root, List<Integer> res) {
     *     if (root == null) return;
     *     res.add(root.val);
     *     preorder(root.left, res);
     *     preorder(root.right, res);
     * }
     * 
     * Inorder recursive:
     * void inorder(TreeNode root, List<Integer> res) {
     *     if (root == null) return;
     *     inorder(root.left, res);
     *     res.add(root.val);
     *     inorder(root.right, res);
     * }
     * 
     * Postorder recursive:
     * void postorder(TreeNode root, List<Integer> res) {
     *     if (root == null) return;
     *     postorder(root.left, res);
     *     postorder(root.right, res);
     *     res.add(root.val);
     * }
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

        AllTraversals t = new AllTraversals();
        
        System.out.println("Preorder:   " + t.preorder(root));
        // Expected: [1, 2, 4, 5, 3, 6]
        
        System.out.println("Inorder:    " + t.inorder(root));
        // Expected: [4, 2, 5, 1, 3, 6]
        
        System.out.println("Postorder:  " + t.postorder(root));
        // Expected: [4, 5, 2, 6, 3, 1]
        
        System.out.println("LevelOrder: " + t.levelOrder(root));
        // Expected: [[1], [2, 3], [4, 5, 6]]
    }
}