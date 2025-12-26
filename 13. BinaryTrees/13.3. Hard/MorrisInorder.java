import java.util.ArrayList;
import java.util.List;

/** 
 * Morris Inorder Traversal - Threaded Binary Tree Traversal
 * Performs inorder traversal with O(1) extra space (no stack/recursion)
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
public class MorrisInorder {
    
    // TreeNode definition for binary tree
    static class TreeNode { 
        int val; 
        TreeNode left, right; 
        TreeNode(int v) { val = v; } 
    }

    /**
     * Morris Inorder Traversal Algorithm
     * ----------------------------------
     * The algorithm uses temporary threading of the tree to traverse 
     * without using stack or recursion.
     * 
     * Key Idea: Create temporary links (threads) to the inorder successor,
     * traverse using them, then remove the threads.
     * 
     * Steps:
     * 1. Initialize current as root
     * 2. While current is not null:
     *    a) If left child is null:
     *       - Visit current node
     *       - Move to right child
     *    b) Else (left child exists):
     *       - Find inorder predecessor (rightmost node in left subtree)
     *       - If predecessor's right is null (not yet threaded):
     *          * Create thread: predecessor.right = current
     *          * Move current to left child
     *       - Else (thread already exists):
     *          * Remove thread: predecessor.right = null
     *          * Visit current node
     *          * Move current to right child
     * 
     * Visualization:
     *       1
     *      / \
     *     2   3
     *    / \
     *   4   5
     * 
     * Step-by-step for node 1:
     * 1. curr=1, left exists (2), find predecessor=5
     * 2. 5.right=null, so set 5.right=1 (thread), curr=2
     * 3. curr=2, left exists (4), find predecessor=4
     * 4. 4.right=null, so set 4.right=2 (thread), curr=4
     * 5. curr=4, left=null, visit 4, curr=4.right=2
     * 6. curr=2, left exists, find predecessor=4
     * 7. 4.right=2 (thread exists), so remove thread: 4.right=null
     * 8. Visit 2, curr=2.right=5
     * 9. ... and so on
     * 
     * @param root Root of the binary tree
     * @return List containing inorder traversal of the tree
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        TreeNode curr = root;
        
        while (curr != null) {
            // Case 1: No left child - visit current and go right
            if (curr.left == null) {
                res.add(curr.val);          // Visit node
                curr = curr.right;          // Move to right child or thread
            } 
            // Case 2: Left child exists
            else {
                // Find inorder predecessor (rightmost node in left subtree)
                TreeNode prev = curr.left;
                
                // Traverse to rightmost node
                // Second condition ensures we don't follow existing threads
                while (prev.right != null && prev.right != curr) {
                    prev = prev.right;
                }
                
                // Case 2a: Thread not yet created
                if (prev.right == null) {
                    // Create temporary thread to current node
                    prev.right = curr;
                    
                    // Move to left child
                    curr = curr.left;
                }
                // Case 2b: Thread already exists (we've visited left subtree)
                else {
                    // Remove the temporary thread
                    prev.right = null;
                    
                    // Visit current node (inorder position)
                    res.add(curr.val);
                    
                    // Move to right child
                    curr = curr.right;
                }
            }
        }
        
        return res;
    }
    
    /**
     * Morris Preorder Traversal (for comparison)
     * Similar to inorder but visits node when thread is created (first time seeing node)
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        TreeNode curr = root;
        
        while (curr != null) {
            if (curr.left == null) {
                res.add(curr.val);
                curr = curr.right;
            } else {
                TreeNode prev = curr.left;
                while (prev.right != null && prev.right != curr) {
                    prev = prev.right;
                }
                
                if (prev.right == null) {
                    // Visit node BEFORE creating thread (preorder)
                    res.add(curr.val);
                    prev.right = curr;
                    curr = curr.left;
                } else {
                    prev.right = null;
                    curr = curr.right;
                }
            }
        }
        
        return res;
    }
    
    /**
     * Test the Morris Inorder Traversal
     */
    public static void main(String[] args) {
        MorrisInorder morris = new MorrisInorder();
        
        // Create a sample binary tree:
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        
        // Test inorder traversal
        List<Integer> inorder = morris.inorderTraversal(root);
        System.out.println("Morris Inorder Traversal: " + inorder);
        // Expected: [4, 2, 5, 1, 3]
        
        // Test preorder traversal
        List<Integer> preorder = morris.preorderTraversal(root);
        System.out.println("Morris Preorder Traversal: " + preorder);
        // Expected: [1, 2, 4, 5, 3]
        
        // Test with null tree
        List<Integer> empty = morris.inorderTraversal(null);
        System.out.println("Empty tree: " + empty);
        // Expected: []
        
        // Test single node
        TreeNode single = new TreeNode(10);
        List<Integer> singleResult = morris.inorderTraversal(single);
        System.out.println("Single node: " + singleResult);
        // Expected: [10]
    }
    
    /**
     * Traditional recursive inorder traversal (for comparison)
     * Time: O(n), Space: O(h) for recursion stack
     */
    public List<Integer> inorderRecursive(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        inorderHelper(root, res);
        return res;
    }
    
    private void inorderHelper(TreeNode node, List<Integer> res) {
        if (node == null) return;
        inorderHelper(node.left, res);
        res.add(node.val);
        inorderHelper(node.right, res);
    }
    
    /**
     * Key Advantages of Morris Traversal:
     * 1. O(1) extra space - no stack or recursion overhead
     * 2. Modifies tree temporarily but restores it
     * 3. Useful in memory-constrained environments
     * 
     * Disadvantages:
     * 1. Modifies tree during traversal (not thread-safe)
     * 2. Slightly more complex to understand
     * 3. Not suitable if tree cannot be modified
     * 
     * Time Complexity Analysis:
     * - Each node is visited at least twice
     * - But total operations remain O(n)
     * - Each edge is traversed at most 3 times
     */
    
    /**
     * Alternative implementation with clearer variable names
     */
    public List<Integer> inorderTraversalAlt(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode current = root;
        
        while (current != null) {
            if (current.left == null) {
                // No left subtree, visit current and move right
                result.add(current.val);
                current = current.right;
            } else {
                // Find inorder predecessor in left subtree
                TreeNode predecessor = current.left;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }
                
                if (predecessor.right == null) {
                    // First time visiting current - create thread
                    predecessor.right = current;
                    current = current.left;
                } else {
                    // Second time visiting current - remove thread
                    predecessor.right = null;
                    result.add(current.val);
                    current = current.right;
                }
            }
        }
        
        return result;
    }
}