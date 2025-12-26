/**
 * Recovers a Binary Search Tree (BST) where exactly two nodes have been swapped.
 * 
 * Problem: Given a BST where two nodes have been swapped by mistake,
 * restore the BST to its correct structure without changing the tree shape.
 * 
 * Key Insight:
 * - Inorder traversal of a BST produces a strictly increasing sequence
 * - When two nodes are swapped, the inorder sequence has exactly two "dips"
 * - Find the two nodes that violate the increasing order property
 * 
 * Cases:
 * 1. Adjacent nodes swapped: One violation in inorder traversal
 * 2. Non-adjacent nodes swapped: Two violations in inorder traversal
 * 
 * Approach: Inorder traversal tracking previous node
 * - Traverse inorder, track previous node (prev)
 * - When we find a violation (prev.val >= current.val):
 *   - First violation: first = prev, second = current
 *   - Second violation: update second = current
 * - After traversal, swap values of first and second
 * 
 * Time Complexity: O(n) - single inorder traversal
 * Space Complexity: O(h) for recursion stack, O(1) extra space
 * 
 * Example:
 * Correct BST:   1 2 3 4 5 6 7
 * Swapped BST:   1 6 3 4 5 2 7  (2 and 6 swapped)
 * Inorder shows: 1 6 3 4 5 2 7
 * Violations:    6>3 and 5>2
 * First = 6, Second = 2
 */
public class RectifyBST {
    static class TreeNode { 
        int val; 
        TreeNode left, right; 
        TreeNode(int v) {
            val = v;
        }
    }

    // Pointers to track the two swapped nodes
    private TreeNode first, second;
    // Previous node in inorder traversal
    private TreeNode prev;

    /**
     * Main method to recover the BST.
     * Swaps the values of the two misplaced nodes.
     * 
     * @param root Root of the corrupted BST
     */
    public void recoverTree(TreeNode root) {
        // Initialize pointers
        first = second = null;
        prev = new TreeNode(Integer.MIN_VALUE);  // Sentinel with minimum value
        
        // Find the two swapped nodes
        inorder(root);
        
        // Swap values of the two nodes
        if (first != null && second != null) {
            int tmp = first.val;
            first.val = second.val;
            second.val = tmp;
        }
    }

    /**
     * Inorder traversal to find swapped nodes.
     * 
     * @param root Current node in traversal
     */
    private void inorder(TreeNode root) {
        if (root == null) return;
        
        // Traverse left subtree
        inorder(root.left);
        
        // Check for violation: previous value should be less than current
        if (first == null && prev.val >= root.val) {
            // First violation found
            first = prev;
        }
        
        if (first != null && prev.val >= root.val) {
            // Second violation (or continuation of first if adjacent swap)
            second = root;
        }
        
        // Update previous node
        prev = root;
        
        // Traverse right subtree
        inorder(root.right);
    }
    
    /**
     * Alternative: Iterative inorder traversal using stack.
     * Avoids recursion stack overflow for deep trees.
     */
    public void recoverTreeIterative(TreeNode root) {
        first = second = null;
        prev = new TreeNode(Integer.MIN_VALUE);
        
        java.util.Stack<TreeNode> stack = new java.util.Stack<>();
        TreeNode current = root;
        
        while (current != null || !stack.isEmpty()) {
            // Go to leftmost node
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            
            // Process node
            current = stack.pop();
            
            // Check for violation
            if (first == null && prev.val >= current.val) {
                first = prev;
            }
            if (first != null && prev.val >= current.val) {
                second = current;
            }
            
            prev = current;
            current = current.right;
        }
        
        // Swap values
        if (first != null && second != null) {
            int tmp = first.val;
            first.val = second.val;
            second.val = tmp;
        }
    }
    
    /**
     * Alternative: Morris traversal for O(1) space.
     * Modifies tree temporarily but restores it.
     */
    public void recoverTreeMorris(TreeNode root) {
        first = second = null;
        prev = new TreeNode(Integer.MIN_VALUE);
        
        TreeNode current = root;
        
        while (current != null) {
            if (current.left == null) {
                // Process current node
                if (first == null && prev.val >= current.val) {
                    first = prev;
                }
                if (first != null && prev.val >= current.val) {
                    second = current;
                }
                prev = current;
                
                current = current.right;
            } else {
                // Find inorder predecessor
                TreeNode predecessor = current.left;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }
                
                if (predecessor.right == null) {
                    // Create thread
                    predecessor.right = current;
                    current = current.left;
                } else {
                    // Remove thread and process node
                    predecessor.right = null;
                    
                    if (first == null && prev.val >= current.val) {
                        first = prev;
                    }
                    if (first != null && prev.val >= current.val) {
                        second = current;
                    }
                    prev = current;
                    
                    current = current.right;
                }
            }
        }
        
        // Swap values
        if (first != null && second != null) {
            int tmp = first.val;
            first.val = second.val;
            second.val = tmp;
        }
    }
    
    /**
     * Finds swapped nodes and returns them without modifying tree.
     * Useful for verification or when only detection is needed.
     */
    public TreeNode[] findSwappedNodes(TreeNode root) {
        first = second = null;
        prev = new TreeNode(Integer.MIN_VALUE);
        
        inorder(root);
        
        if (first != null && second != null) {
            return new TreeNode[]{first, second};
        }
        return new TreeNode[2];
    }
    
    /**
     * Validates if tree is a valid BST.
     * Useful for testing recovery.
     */
    public boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    private boolean validate(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return validate(node.left, min, node.val) && 
               validate(node.right, node.val, max);
    }
    
    /**
     * Test cases and examples
     */
    public static void main(String[] args) {
        RectifyBST recoverer = new RectifyBST();
        
        System.out.println("=== Test Case 1: Adjacent nodes swapped ===");
        // Original BST:    3
        //                 / \
        //                1   4
        //                   /
        //                  2
        // Inorder: 1, 3, 2, 4  (3 and 2 are swapped)
        
        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(1);
        root1.right = new TreeNode(4);
        root1.right.left = new TreeNode(2);  // Should be 2, but let's make it wrong
        
        System.out.println("Tree before recovery (values swapped: 2 and 3):");
        System.out.println("       3");
        System.out.println("      / \\");
        System.out.println("     1   4");
        System.out.println("        /");
        System.out.println("       2");
        
        System.out.println("Is valid BST before recovery? " + recoverer.isValidBST(root1));
        
        recoverer.recoverTree(root1);
        
        System.out.println("\nAfter recovery:");
        System.out.println("       2");
        System.out.println("      / \\");
        System.out.println("     1   4");
        System.out.println("        /");
        System.out.println("       3");
        System.out.println("Is valid BST after recovery? " + recoverer.isValidBST(root1));
        
        System.out.println("\n=== Test Case 2: Non-adjacent nodes swapped ===");
        // Original BST:        5
        //                    /   \
        //                   3     8
        //                  / \   / \
        //                 2   4 6   9
        // Swap 3 and 8:   5
        //                /   \
        //               8     3
        //              / \   / \
        //             2   4 6   9
        // Inorder: 2, 8, 4, 5, 6, 3, 9 (two violations: 8>4 and 6>3)
        
        TreeNode root2 = new TreeNode(5);
        root2.left = new TreeNode(8);  // Should be 3
        root2.right = new TreeNode(3); // Should be 8
        root2.left.left = new TreeNode(2);
        root2.left.right = new TreeNode(4);
        root2.right.left = new TreeNode(6);
        root2.right.right = new TreeNode(9);
        
        System.out.println("\nTree before recovery (values swapped: 3 and 8):");
        System.out.println("        5");
        System.out.println("       / \\");
        System.out.println("      8   3");
        System.out.println("     / \\ / \\");
        System.out.println("    2  4 6  9");
        
        System.out.println("Is valid BST before recovery? " + recoverer.isValidBST(root2));
        
        TreeNode[] swapped = recoverer.findSwappedNodes(root2);
        System.out.println("Swapped nodes found: " + swapped[0].val + " and " + swapped[1].val);
        
        recoverer.recoverTree(root2);
        
        System.out.println("\nAfter recovery:");
        System.out.println("        5");
        System.out.println("       / \\");
        System.out.println("      3   8");
        System.out.println("     / \\ / \\");
        System.out.println("    2  4 6  9");
        System.out.println("Is valid BST after recovery? " + recoverer.isValidBST(root2));
        
        System.out.println("\n=== Test Case 3: Root and leaf swapped ===");
        // Original:    10
        //             /  \
        //            5    20
        //           / \
        //          1   7
        // Swap 10 and 1
        
        TreeNode root3 = new TreeNode(1);  // Should be 10
        root3.left = new TreeNode(5);
        root3.right = new TreeNode(20);
        root3.left.left = new TreeNode(10);  // Should be 1
        root3.left.right = new TreeNode(7);
        
        System.out.println("\nTree before recovery (root and leaf swapped):");
        System.out.println("        1");
        System.out.println("       / \\");
        System.out.println("      5   20");
        System.out.println("     / \\");
        System.out.println("    10  7");
        
        recoverer.recoverTreeMorris(root3);
        
        System.out.println("\nAfter recovery (using Morris traversal):");
        System.out.println("        10");
        System.out.println("       /  \\");
        System.out.println("      5    20");
        System.out.println("     / \\");
        System.out.println("    1   7");
        System.out.println("Is valid BST after recovery? " + recoverer.isValidBST(root3));
        
        System.out.println("\n=== Test Case 4: Edge cases ===");
        
        // Single node tree
        TreeNode root4 = new TreeNode(42);
        System.out.println("\nSingle node tree:");
        recoverer.recoverTree(root4);
        System.out.println("Value: " + root4.val + " (unchanged)");
        
        // Already correct BST
        TreeNode root5 = new TreeNode(2);
        root5.left = new TreeNode(1);
        root5.right = new TreeNode(3);
        System.out.println("\nAlready correct BST:");
        System.out.println("Before: Is valid? " + recoverer.isValidBST(root5));
        recoverer.recoverTree(root5);
        System.out.println("After: Is valid? " + recoverer.isValidBST(root5));
        
        // Empty tree
        System.out.println("\nEmpty tree:");
        recoverer.recoverTree(null);
        System.out.println("No error thrown");
    }
    
    /**
     * Helper to print inorder traversal
     */
    private static void printInorder(TreeNode root) {
        if (root == null) return;
        printInorder(root.left);
        System.out.print(root.val + " ");
        printInorder(root.right);
    }
}