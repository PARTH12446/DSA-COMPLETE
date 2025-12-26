/**
 * Inserts a value into a Binary Search Tree (BST) while maintaining BST properties.
 * 
 * BST Insertion Rules:
 * 1. If tree is empty: create new node as root
 * 2. Compare value with current node:
 *    - If value < node.val: insert in left subtree
 *    - If value > node.val: insert in right subtree
 *    - If value == node.val: typically not inserted (BSTs usually have unique values)
 * 
 * Approach: Recursive traversal to find correct position
 * - Base case: null node → create new TreeNode
 * - Recursive case: traverse left or right based on comparison
 * - Return the (possibly new) root at each level
 * 
 * Time Complexity: O(h) where h is tree height
 * - O(log n) for balanced BST
 * - O(n) for skewed tree
 * 
 * Space Complexity: O(h) for recursion stack
 * - Can be O(1) with iterative approach
 * 
 * Properties Maintained:
 * - BST property (left < parent < right)
 * - No duplicate values inserted (though implementation can be modified)
 * - Insertion always at leaf position
 */
public class InsertBST {
    static class TreeNode { 
        int val; 
        TreeNode left, right; 
        TreeNode(int v) {
            val = v;
        }
    }

    /**
     * Recursive insertion into BST.
     * 
     * @param root Current root of (sub)tree
     * @param val  Value to insert
     * @return     Root of the modified tree (same as input root unless tree was empty)
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        // Base case: empty spot found, create new node
        if (root == null) {
            return new TreeNode(val);
        }
        
        // Recursive insertion based on BST property
        if (val < root.val) {
            // Insert in left subtree
            root.left = insertIntoBST(root.left, val);
        } else if (val > root.val) {
            // Insert in right subtree
            root.right = insertIntoBST(root.right, val);
        }
        // Note: If val == root.val, we do nothing (no duplicates)
        
        return root;  // Return the (unchanged) root
    }
    
    /**
     * Iterative insertion into BST (more space-efficient).
     * O(1) extra space (excluding tree nodes).
     */
    public TreeNode insertIntoBSTIterative(TreeNode root, int val) {
        TreeNode newNode = new TreeNode(val);
        
        // Special case: empty tree
        if (root == null) {
            return newNode;
        }
        
        TreeNode current = root;
        TreeNode parent = null;
        
        // Traverse to find insertion point
        while (current != null) {
            parent = current;
            
            if (val < current.val) {
                current = current.left;
            } else if (val > current.val) {
                current = current.right;
            } else {
                // Value already exists (optional: handle duplicates differently)
                return root;
            }
        }
        
        // Insert at correct position
        if (val < parent.val) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        
        return root;
    }
    
    /**
     * Insertion that allows duplicates (stores count in node).
     * Useful when BST needs to handle multiple identical values.
     */
    static class CountTreeNode {
        int val;
        int count;  // Number of duplicates
        CountTreeNode left, right;
        CountTreeNode(int v) {
            val = v;
            count = 1;
        }
    }
    
    public CountTreeNode insertWithDuplicates(CountTreeNode root, int val) {
        if (root == null) {
            return new CountTreeNode(val);
        }
        
        if (val < root.val) {
            root.left = insertWithDuplicates(root.left, val);
        } else if (val > root.val) {
            root.right = insertWithDuplicates(root.right, val);
        } else {
            // Duplicate found, increment count
            root.count++;
        }
        
        return root;
    }
    
    /**
     * Insert multiple values into BST.
     * Useful for building tree from array/list.
     */
    public TreeNode insertMultiple(TreeNode root, int[] values) {
        for (int val : values) {
            root = insertIntoBST(root, val);
        }
        return root;
    }
    
    /**
     * Insertion with rebalancing (AVL-style, simplified concept).
     * Note: Full AVL implementation requires rotations and height tracking.
     */
    public TreeNode insertAndBalance(TreeNode root, int val) {
        root = insertIntoBST(root, val);
        // Additional balancing logic would go here
        // This is just a placeholder for the concept
        return root;
    }
    
    /**
     * Test cases and examples
     */
    public static void main(String[] args) {
        InsertBST inserter = new InsertBST();
        
        System.out.println("=== Test Case 1: Build BST from scratch ===");
        TreeNode root1 = null;
        int[] values1 = {5, 3, 7, 2, 4, 6, 8};
        
        for (int val : values1) {
            root1 = inserter.insertIntoBST(root1, val);
        }
        
        System.out.println("BST structure after inserting [5, 3, 7, 2, 4, 6, 8]:");
        printTree(root1, "", true);
        System.out.println("Inorder traversal (should be sorted):");
        printInorder(root1);
        System.out.println("\n");
        
        System.out.println("=== Test Case 2: Insert into existing BST ===");
        TreeNode root2 = new TreeNode(10);
        root2.left = new TreeNode(5);
        root2.right = new TreeNode(15);
        
        System.out.println("Original tree:");
        printTree(root2, "", true);
        
        System.out.println("\nAfter inserting 3:");
        root2 = inserter.insertIntoBST(root2, 3);
        printTree(root2, "", true);
        
        System.out.println("\nAfter inserting 12:");
        root2 = inserter.insertIntoBST(root2, 12);
        printTree(root2, "", true);
        
        System.out.println("\nAfter inserting 20:");
        root2 = inserter.insertIntoBST(root2, 20);
        printTree(root2, "", true);
        
        System.out.println("\n=== Test Case 3: Insert duplicate ===");
        TreeNode root3 = new TreeNode(10);
        root3 = inserter.insertIntoBST(root3, 5);
        root3 = inserter.insertIntoBST(root3, 15);
        
        System.out.println("Tree before inserting duplicate 5:");
        printTree(root3, "", true);
        
        System.out.println("\nInserting duplicate 5 (should not change tree):");
        root3 = inserter.insertIntoBST(root3, 5);
        printTree(root3, "", true);
        
        System.out.println("\n=== Test Case 4: Iterative insertion ===");
        TreeNode root4 = null;
        int[] values4 = {8, 3, 10, 1, 6, 14, 4, 7, 13};
        
        for (int val : values4) {
            root4 = inserter.insertIntoBSTIterative(root4, val);
        }
        
        System.out.println("BST built with iterative insertion:");
        printTree(root4, "", true);
        
        System.out.println("\n=== Test Case 5: Insert with duplicates ===");
        CountTreeNode root5 = null;
        int[] values5 = {5, 3, 5, 7, 3, 5, 5};  // Multiple duplicates
        
        InsertBST inserterWithDup = new InsertBST();
        for (int val : values5) {
            root5 = inserterWithDup.insertWithDuplicates(root5, val);
        }
        
        System.out.println("Tree with duplicates (counts shown):");
        printCountTree(root5, "", true);
    }
    
    /**
     * Helper to print tree structure visually
     */
    private static void printTree(TreeNode root, String indent, boolean last) {
        if (root == null) return;
        
        System.out.print(indent);
        if (last) {
            System.out.print("└─");
            indent += "  ";
        } else {
            System.out.print("├─");
            indent += "│ ";
        }
        System.out.println(root.val);
        
        printTree(root.left, indent, false);
        printTree(root.right, indent, true);
    }
    
    /**
     * Helper to print tree with duplicate counts
     */
    private static void printCountTree(CountTreeNode root, String indent, boolean last) {
        if (root == null) return;
        
        System.out.print(indent);
        if (last) {
            System.out.print("└─");
            indent += "  ";
        } else {
            System.out.print("├─");
            indent += "│ ";
        }
        System.out.println(root.val + " (x" + root.count + ")");
        
        printCountTree(root.left, indent, false);
        printCountTree(root.right, indent, true);
    }
    
    /**
     * Helper to print inorder traversal (should be sorted for BST)
     */
    private static void printInorder(TreeNode root) {
        if (root == null) return;
        printInorder(root.left);
        System.out.print(root.val + " ");
        printInorder(root.right);
    }
    
    /**
     * Utility: Calculate height of tree
     */
    private int height(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(height(root.left), height(root.right));
    }
    
    /**
     * Utility: Check if tree is valid BST after insertion
     */
    private boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    private boolean isValidBST(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return isValidBST(node.left, min, node.val) && 
               isValidBST(node.right, node.val, max);
    }
}