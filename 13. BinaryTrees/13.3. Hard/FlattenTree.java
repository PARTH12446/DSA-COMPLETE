/** 
 * Flatten Binary Tree to Linked List (LeetCode 114)
 * 
 * PROBLEM: Convert binary tree to right-skewed linked list in-place.
 * Linked list should follow pre-order traversal order.
 * 
 * APPROACH: Recursive post-order processing
 * TIME: O(n) - each node visited once
 * SPACE: O(h) - recursion stack (h = height)
 */
public class FlattenTree {
    static class TreeNode { 
        int val; 
        TreeNode left, right; 
        TreeNode(int v) { val = v; } 
    }

    /**
     * Flatten binary tree to right-skewed linked list (pre-order)
     * @param root - root of binary tree
     * 
     * RESULT: Tree becomes right-skewed list where:
     * - right pointer acts as "next" in linked list
     * - left pointer is always null
     * - Order follows pre-order traversal
     * 
     * APPROACH: Recursive post-order
     * 1. Recursively flatten left and right subtrees
     * 2. If left subtree exists:
     *    a. Find rightmost node of flattened left subtree
     *    b. Attach flattened right subtree to it
     *    c. Move left subtree to right
     *    d. Set left to null
     */
    public void flatten(TreeNode root) {
        if (root == null) return;
        
        // 1. Recursively flatten subtrees
        flatten(root.left);
        flatten(root.right);
        
        // 2. Process current node
        if (root.left != null) {
            // Find rightmost node of flattened left subtree
            TreeNode temp = root.left;
            while (temp.right != null) {
                temp = temp.right;
            }
            
            // Attach flattened right subtree to rightmost of left
            temp.right = root.right;
            
            // Move left subtree to right
            root.right = root.left;
            root.left = null;
        }
    }
    
    /**
     * Alternative: Iterative approach using stack
     * Pre-order traversal with explicit stack
     */
    public void flattenIterative(TreeNode root) {
        if (root == null) return;
        
        java.util.Stack<TreeNode> stack = new java.util.Stack<>();
        stack.push(root);
        TreeNode prev = null;
        
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            
            // Push children in reverse order (right then left)
            if (curr.right != null) stack.push(curr.right);
            if (curr.left != null) stack.push(curr.left);
            
            // Connect current node to previous in list
            if (prev != null) {
                prev.right = curr;
                prev.left = null;
            }
            prev = curr;
        }
    }
    
    /**
     * Alternative: Morris-like traversal (O(1) space)
     * Uses threaded binary tree concept
     */
    public void flattenMorris(TreeNode root) {
        TreeNode curr = root;
        
        while (curr != null) {
            if (curr.left != null) {
                // Find rightmost node in left subtree
                TreeNode prev = curr.left;
                while (prev.right != null) {
                    prev = prev.right;
                }
                
                // Re-wire connections
                prev.right = curr.right;
                curr.right = curr.left;
                curr.left = null;
            }
            // Move to next node
            curr = curr.right;
        }
    }
    
    /**
     * Alternative: Reverse post-order approach
     * Process nodes in reverse pre-order: right, left, root
     */
    private TreeNode prev = null;
    
    public void flattenReversePostorder(TreeNode root) {
        if (root == null) return;
        
        // Process right first, then left (reverse pre-order)
        flattenReversePostorder(root.right);
        flattenReversePostorder(root.left);
        
        // Current node's right points to previous processed node
        root.right = prev;
        root.left = null;
        prev = root;
    }

    /**
     * Visual Examples:
     * 
     * Example tree:
     *       1
     *      / \
     *     2   5
     *    / \   \
     *   3   4   6
     * 
     * Pre-order: 1,2,3,4,5,6
     * 
     * Flattening process:
     * 1. Flatten subtrees recursively
     * 2. For node 2: left=3, right=4
     *    - Find rightmost of left (3) → attach right (4) → 3→4
     *    - Move left to right: 2→3→4
     * 3. For node 1: left=2→3→4, right=5→6
     *    - Find rightmost of left (4) → attach right (5→6) → 4→5→6
     *    - Move left to right: 1→2→3→4→5→6
     * 
     * Result: 1→2→3→4→5→6
     * 
     * After flattening, tree becomes:
     *   1
     *    \
     *     2
     *      \
     *       3
     *        \
     *         4
     *          \
     *           5
     *            \
     *             6
     */

    public static void main(String[] args) {
        FlattenTree ft = new FlattenTree();
        
        // Example tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(6);
        
        System.out.println("Original tree (pre-order):");
        printPreOrder(root); // 1 2 3 4 5 6
        
        // Test recursive method
        TreeNode root1 = copyTree(root);
        ft.flatten(root1);
        System.out.println("\nAfter flatten (recursive):");
        printRightList(root1); // 1 2 3 4 5 6
        
        // Test iterative method
        TreeNode root2 = copyTree(root);
        ft.flattenIterative(root2);
        System.out.println("\nAfter flatten (iterative):");
        printRightList(root2); // 1 2 3 4 5 6
        
        // Test Morris method
        TreeNode root3 = copyTree(root);
        ft.flattenMorris(root3);
        System.out.println("\nAfter flatten (Morris):");
        printRightList(root3); // 1 2 3 4 5 6
        
        // Test reverse post-order method
        TreeNode root4 = copyTree(root);
        ft.prev = null; // Reset static variable
        ft.flattenReversePostorder(root4);
        System.out.println("\nAfter flatten (reverse post-order):");
        printRightList(root4); // 1 2 3 4 5 6
        
        // Test cases
        System.out.println("\n=== Test Cases ===");
        
        // Test 1: Empty tree
        TreeNode empty = null;
        ft.flatten(empty);
        System.out.println("Empty tree: " + (empty == null)); // true
        
        // Test 2: Single node
        TreeNode single = new TreeNode(1);
        ft.flatten(single);
        System.out.println("Single node flattened: " + single.val + ", left=" + single.left + ", right=" + single.right);
        
        // Test 3: Left-skewed tree
        TreeNode leftSkewed = new TreeNode(1);
        leftSkewed.left = new TreeNode(2);
        leftSkewed.left.left = new TreeNode(3);
        ft.flatten(leftSkewed);
        System.out.println("Left-skewed flattened: ");
        printRightList(leftSkewed); // 1 2 3
        
        // Test 4: Right-skewed tree (already flattened)
        TreeNode rightSkewed = new TreeNode(1);
        rightSkewed.right = new TreeNode(2);
        rightSkewed.right.right = new TreeNode(3);
        ft.flatten(rightSkewed);
        System.out.println("Right-skewed flattened: ");
        printRightList(rightSkewed); // 1 2 3
        
        // Test 5: Complete binary tree
        TreeNode complete = new TreeNode(1);
        complete.left = new TreeNode(2);
        complete.right = new TreeNode(3);
        complete.left.left = new TreeNode(4);
        complete.left.right = new TreeNode(5);
        complete.right.left = new TreeNode(6);
        complete.right.right = new TreeNode(7);
        ft.flatten(complete);
        System.out.println("Complete tree flattened: ");
        printRightList(complete); // 1 2 4 5 3 6 7
    }
    
    // Helper to print pre-order traversal
    private static void printPreOrder(TreeNode root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        printPreOrder(root.left);
        printPreOrder(root.right);
    }
    
    // Helper to print right-skewed list
    private static void printRightList(TreeNode root) {
        TreeNode curr = root;
        while (curr != null) {
            System.out.print(curr.val + " ");
            curr = curr.right;
        }
        System.out.println();
    }
    
    // Helper to copy tree
    private static TreeNode copyTree(TreeNode root) {
        if (root == null) return null;
        TreeNode copy = new TreeNode(root.val);
        copy.left = copyTree(root.left);
        copy.right = copyTree(root.right);
        return copy;
    }
}

/**
 * KEY CONCEPTS:
 * 
 * 1. Result requirements:
 *    - Right pointer acts as "next" in linked list
 *    - Left pointer always null
 *    - Order follows pre-order traversal
 *    - In-place modification (no extra data structures)
 * 
 * 2. Recursive approach (post-order):
 *    - Flatten left and right subtrees first
 *    - Attach right subtree to rightmost of left subtree
 *    - Move left to right, set left to null
 * 
 * 3. Rightmost node finding:
 *    - Crucial step to connect subtrees
 *    - While(temp.right != null) temp = temp.right
 */

/**
 * TIME & SPACE ANALYSIS:
 * 
 * Recursive:
 *   Time: O(n) - Each node visited once
 *   Space: O(h) - Recursion stack, where h = height
 *     - Worst case (skewed): O(n)
 *     - Best case (balanced): O(log n)
 * 
 * Iterative with stack:
 *   Time: O(n)
 *   Space: O(h) - Stack size
 * 
 * Morris traversal:
 *   Time: O(n) - Each node visited at most twice
 *   Space: O(1) - No extra space (except pointers)
 */

/**
 * ALGORITHM WALKTHROUGH (recursive):
 * 
 * Tree:     1
 *          / \
 *         2   5
 *        / \   \
 *       3   4   6
 * 
 * flatten(1):
 *   flatten(2):
 *     flatten(3): leaf → return
 *     flatten(4): leaf → return
 *     root=2: left=3, right=4
 *       find rightmost of left: 3
 *       attach right: 3.right=4
 *       move left to right: 2.right=3→4
 *       set left=null
 *   
 *   flatten(5):
 *     flatten(6): leaf → return
 *     root=5: left=null → nothing to do
 *   
 *   root=1: left=2→3→4, right=5→6
 *     find rightmost of left: 4
 *     attach right: 4.right=5→6
 *     move left to right: 1.right=2→3→4→5→6
 *     set left=null
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Forgetting to set left to null
 * 2. Not finding rightmost node correctly
 * 3. Wrong order of operations (should be post-order)
 * 4. Not handling null left subtree case
 * 5. Creating cycles by incorrect wiring
 */

/**
 * VARIATIONS:
 * 
 * 1. Flatten to doubly linked list (left as prev, right as next)
 * 2. Flatten to circular linked list
 * 3. Flatten in different orders (in-order, post-order)
 * 4. Flatten with level-order traversal
 * 5. Unflatten (reconstruct tree from list)
 */

/**
 * PRACTICE EXERCISES:
 * 
 * 1. Implement iterative version
 * 2. Implement Morris traversal version
 * 3. Flatten to doubly linked list
 * 4. Check if tree is already flattened
 * 5. Reconstruct tree from flattened list
 */