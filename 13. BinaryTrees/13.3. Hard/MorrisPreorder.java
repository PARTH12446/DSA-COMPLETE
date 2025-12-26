import java.util.ArrayList;
import java.util.List;

/** 
 * Morris Preorder Traversal - Threaded Binary Tree Traversal
 * Performs preorder traversal with O(1) extra space (no stack/recursion)
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
public class MorrisPreorder {
    
    // TreeNode definition for binary tree
    static class TreeNode { 
        int val; 
        TreeNode left, right; 
        TreeNode(int v) { val = v; } 
    }

    /**
     * Morris Preorder Traversal Algorithm
     * -----------------------------------
     * The algorithm uses temporary threading of the tree to traverse 
     * without using stack or recursion. For preorder, we visit nodes
     * when we first encounter them (before exploring left subtree).
     * 
     * Key Idea: Create temporary links to the inorder successor,
     * visit node when creating the thread, then remove threads later.
     * 
     * Steps:
     * 1. Initialize current as root
     * 2. While current is not null:
     *    a) If left child is null:
     *       - Visit current node (preorder position)
     *       - Move to right child (or thread)
     *    b) Else (left child exists):
     *       - Find inorder predecessor (rightmost node in left subtree)
     *       - If predecessor's right is null (not yet threaded):
     *          * Create thread: predecessor.right = current
     *          * **Visit current node NOW (preorder!)**
     *          * Move current to left child
     *       - Else (thread already exists):
     *          * Remove thread: predecessor.right = null
     *          * Move current to right child
     * 
     * @param root Root of the binary tree
     * @return List containing preorder traversal of the tree
     */
    public List<Integer> preorderTraversal(TreeNode root) {
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
                
                // Traverse to rightmost node in left subtree
                // Second condition ensures we don't follow existing threads
                while (prev.right != null && prev.right != curr) {
                    prev = prev.right;
                }
                
                // Case 2a: Thread not yet created (first time seeing this node)
                if (prev.right == null) {
                    // Create temporary thread to current node
                    prev.right = curr;
                    
                    // **KEY DIFFERENCE FROM INORDER: Visit node HERE**
                    res.add(curr.val);      // Preorder: visit before going left
                    
                    // Move to left child
                    curr = curr.left;
                }
                // Case 2b: Thread already exists (left subtree fully visited)
                else {
                    // Remove the temporary thread
                    prev.right = null;
                    
                    // Move to right child
                    curr = curr.right;
                }
            }
        }
        
        return res;
    }
    
    /**
     * Visual Example:
     * 
     * Tree:
     *       1
     *      / \
     *     2   3
     *    / \
     *   4   5
     * 
     * Step-by-step traversal:
     * 1. curr=1, left=2 exists, find predecessor=5, thread 5->1, visit 1, curr=2
     * 2. curr=2, left=4 exists, find predecessor=4, thread 4->2, visit 2, curr=4
     * 3. curr=4, left=null, visit 4, curr=4.right=2 (via thread)
     * 4. curr=2, left=4 exists, find predecessor=4 (thread exists), remove thread, curr=2.right=5
     * 5. curr=5, left=null, visit 5, curr=5.right=1 (via thread)
     * 6. curr=1, left=2 exists, find predecessor=5 (thread exists), remove thread, curr=1.right=3
     * 7. curr=3, left=null, visit 3, curr=3.right=null
     * 
     * Result: [1, 2, 4, 5, 3]
     */
    
    /**
     * Alternative clearer implementation with detailed comments
     */
    public List<Integer> preorderTraversalAlt(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode current = root;
        
        while (current != null) {
            // If no left child, process current and move right
            if (current.left == null) {
                result.add(current.val);
                current = current.right;
            } else {
                // Find the inorder predecessor of current
                TreeNode predecessor = current.left;
                
                // Go to the rightmost node in the left subtree
                // Stop if we find current (thread already exists)
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }
                
                // Two cases based on whether we've created a thread before
                if (predecessor.right == null) {
                    // First time visiting current node in preorder
                    // Create a temporary thread back to current
                    predecessor.right = current;
                    
                    // Process current node (preorder: root, left, right)
                    result.add(current.val);
                    
                    // Move to left child
                    current = current.left;
                } else {
                    // We're revisiting current via the thread
                    // This means left subtree is fully processed
                    
                    // Remove the temporary thread
                    predecessor.right = null;
                    
                    // Move to right subtree
                    current = current.right;
                }
            }
        }
        
        return result;
    }
    
    /**
     * Test the Morris Preorder Traversal
     */
    public static void main(String[] args) {
        MorrisPreorder morris = new MorrisPreorder();
        
        // Test Case 1: Normal tree
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.left = new TreeNode(4);
        root1.left.right = new TreeNode(5);
        
        List<Integer> result1 = morris.preorderTraversal(root1);
        System.out.println("Test 1 - Normal tree: " + result1);
        System.out.println("Expected: [1, 2, 4, 5, 3]");
        
        // Test Case 2: Right-skewed tree
        //   1
        //    \
        //     2
        //      \
        //       3
        TreeNode root2 = new TreeNode(1);
        root2.right = new TreeNode(2);
        root2.right.right = new TreeNode(3);
        
        List<Integer> result2 = morris.preorderTraversal(root2);
        System.out.println("\nTest 2 - Right-skewed: " + result2);
        System.out.println("Expected: [1, 2, 3]");
        
        // Test Case 3: Left-skewed tree
        //       1
        //      /
        //     2
        //    /
        //   3
        TreeNode root3 = new TreeNode(1);
        root3.left = new TreeNode(2);
        root3.left.left = new TreeNode(3);
        
        List<Integer> result3 = morris.preorderTraversal(root3);
        System.out.println("\nTest 3 - Left-skewed: " + result3);
        System.out.println("Expected: [1, 2, 3]");
        
        // Test Case 4: Single node
        TreeNode root4 = new TreeNode(42);
        List<Integer> result4 = morris.preorderTraversal(root4);
        System.out.println("\nTest 4 - Single node: " + result4);
        System.out.println("Expected: [42]");
        
        // Test Case 5: Empty tree
        List<Integer> result5 = morris.preorderTraversal(null);
        System.out.println("\nTest 5 - Empty tree: " + result5);
        System.out.println("Expected: []");
        
        // Test Case 6: Complete tree
        //       1
        //      / \
        //     2   3
        //    / \ / \
        //   4  5 6  7
        TreeNode root6 = new TreeNode(1);
        root6.left = new TreeNode(2);
        root6.right = new TreeNode(3);
        root6.left.left = new TreeNode(4);
        root6.left.right = new TreeNode(5);
        root6.right.left = new TreeNode(6);
        root6.right.right = new TreeNode(7);
        
        List<Integer> result6 = morris.preorderTraversal(root6);
        System.out.println("\nTest 6 - Complete tree: " + result6);
        System.out.println("Expected: [1, 2, 4, 5, 3, 6, 7]");
    }
    
    /**
     * Comparison with other traversal methods
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left == null) {
                res.add(curr.val);
                curr = curr.right;
            } else {
                TreeNode prev = curr.left;
                while (prev.right != null && prev.right != curr) prev = prev.right;
                if (prev.right == null) {
                    prev.right = curr;
                    curr = curr.left;
                } else {
                    prev.right = null;
                    res.add(curr.val);  // **DIFFERENCE: Visit HERE for inorder**
                    curr = curr.right;
                }
            }
        }
        return res;
    }
    
    /**
     * Traditional recursive preorder (for comparison)
     */
    public List<Integer> preorderRecursive(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        preorderHelper(root, res);
        return res;
    }
    
    private void preorderHelper(TreeNode node, List<Integer> res) {
        if (node == null) return;
        res.add(node.val);           // Visit root
        preorderHelper(node.left, res);  // Visit left
        preorderHelper(node.right, res); // Visit right
    }
    
    /**
     * Traditional iterative preorder using stack
     */
    public List<Integer> preorderIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        java.util.Stack<TreeNode> stack = new java.util.Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);
            
            // Push right first so left is processed first (LIFO)
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }
        
        return result;
    }
    
    /**
     * Key Insights:
     * 
     * 1. The ONLY difference between Morris Inorder and Preorder:
     *    - Inorder: Visit node when thread ALREADY exists (second encounter)
     *    - Preorder: Visit node when CREATING thread (first encounter)
     * 
     * 2. When do we visit nodes in preorder?
     *    - Root: When we first encounter it
     *    - Left subtree: Before creating threads
     *    - Right subtree: After removing threads
     * 
     * 3. Thread creation/removal pattern:
     *    - Create thread: When going down to left subtree
     *    - Remove thread: When coming back up via thread
     * 
     * 4. The algorithm essentially performs:
     *    - Depth-first exploration without stack
     *    - Using tree nodes themselves as "breadcrumbs"
     */
    
    /**
     * Common Mistakes to Avoid:
     * 1. Forgetting to remove threads (memory leak!)
     * 2. Infinite loop if not checking `prev.right != curr`
     * 3. Wrong visit timing (inorder vs preorder confusion)
     * 4. Not handling null root
     */
}