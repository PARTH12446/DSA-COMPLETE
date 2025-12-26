/**
 * Deletes a node with given key from a Binary Search Tree (BST).
 * 
 * BST Deletion involves three cases:
 * 1. Node has NO children (leaf node) → Simply remove it
 * 2. Node has ONE child → Replace node with its child
 * 3. Node has TWO children → 
 *    - Find inorder successor (smallest node in right subtree)
 *    - Replace node's value with successor's value
 *    - Recursively delete the successor from right subtree
 * 
 * Key Properties Maintained:
 * - BST property preserved after deletion
 * - Tree height may change but structure remains valid
 * 
 * Time Complexity: O(h) where h is tree height
 * - O(log n) for balanced BST
 * - O(n) for skewed tree
 * 
 * Space Complexity: O(h) for recursion stack
 */
public class DeleteBST {
    static class TreeNode { 
        int val; 
        TreeNode left, right; 
        TreeNode(int v) {
            val = v;
        }
    }

    /**
     * Deletes node with given key from BST rooted at 'root'.
     * 
     * @param root Root of the BST (may change if deleting root)
     * @param key  Value to delete from the tree
     * @return     New root of the modified BST
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        // Base case: key not found
        if (root == null) return null;
        
        // Search for the node to delete
        if (key < root.val) {
            // Target is in left subtree
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            // Target is in right subtree
            root.right = deleteNode(root.right, key);
        } else {
            // Found the node to delete (root.val == key)
            
            // Case 1: Node has no left child
            if (root.left == null) {
                return root.right;  // Replace with right child (or null)
            }
            
            // Case 2: Node has no right child
            if (root.right == null) {
                return root.left;   // Replace with left child
            }
            
            // Case 3: Node has two children
            // Find inorder successor (smallest node in right subtree)
            TreeNode succ = root.right;
            while (succ.left != null) {
                succ = succ.left;  // Keep going left to find smallest
            }
            
            // Replace current node's value with successor's value
            root.val = succ.val;
            
            // Delete the successor from right subtree
            root.right = deleteNode(root.right, succ.val);
        }
        return root;
    }
    
    /**
     * Alternative implementation with detailed comments for each case
     */
    public TreeNode deleteNodeDetailed(TreeNode root, int key) {
        if (root == null) {
            return null;  // Key not found, nothing to delete
        }
        
        if (key < root.val) {
            root.left = deleteNode(root.left, key);
            return root;
        }
        
        if (key > root.val) {
            root.right = deleteNode(root.right, key);
            return root;
        }
        
        // At this point: root.val == key (node to delete found)
        
        // CASE 1: Leaf node (no children)
        if (root.left == null && root.right == null) {
            return null;  // Simply remove the node
        }
        
        // CASE 2A: Only left child exists
        if (root.right == null) {
            return root.left;  // Bypass current node
        }
        
        // CASE 2B: Only right child exists
        if (root.left == null) {
            return root.right;  // Bypass current node
        }
        
        // CASE 3: Both children exist
        // Find inorder successor (minimum value in right subtree)
        TreeNode successor = findMin(root.right);
        
        // Copy successor's value to current node
        root.val = successor.val;
        
        // Delete the successor from right subtree
        root.right = deleteNode(root.right, successor.val);
        
        return root;
    }
    
    /**
     * Finds minimum value node in a BST subtree
     */
    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    /**
     * Finds maximum value node in a BST subtree
     * (Alternative: could use inorder predecessor from left subtree)
     */
    private TreeNode findMax(TreeNode node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }
    
    /**
     * Alternative approach using inorder predecessor
     * Instead of successor from right subtree, use predecessor from left subtree
     */
    public TreeNode deleteNodeWithPredecessor(TreeNode root, int key) {
        if (root == null) return null;
        
        if (key < root.val) {
            root.left = deleteNodeWithPredecessor(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNodeWithPredecessor(root.right, key);
        } else {
            // Node found
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            
            // Use inorder predecessor (largest in left subtree)
            TreeNode pred = root.left;
            while (pred.right != null) {
                pred = pred.right;
            }
            
            root.val = pred.val;
            root.left = deleteNodeWithPredecessor(root.left, pred.val);
        }
        return root;
    }
    
    /**
     * Test cases and examples
     */
    public static void main(String[] args) {
        DeleteBST deleter = new DeleteBST();
        
        // Build test tree:
        //       5
        //      / \
        //     3   8
        //    / \   \
        //   2   4   9
        //  /
        // 1
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(9);
        root.left.left.left = new TreeNode(1);
        
        System.out.println("Original tree (inorder):");
        printInorder(root);
        System.out.println();
        
        // Test 1: Delete leaf node (4)
        System.out.println("\nDeleting leaf node 4:");
        root = deleter.deleteNode(root, 4);
        printInorder(root);
        System.out.println(" Expected: 1 2 3 5 8 9");
        
        // Test 2: Delete node with one child (2)
        System.out.println("\nDeleting node 2 (has left child 1):");
        root = deleter.deleteNode(root, 2);
        printInorder(root);
        System.out.println(" Expected: 1 3 5 8 9");
        
        // Test 3: Delete node with two children (3)
        System.out.println("\nDeleting node 3 (has two children):");
        root = deleter.deleteNode(root, 3);
        printInorder(root);
        System.out.println(" Expected: 1 5 8 9");
        
        // Test 4: Delete root node (5)
        System.out.println("\nDeleting root node 5:");
        root = deleter.deleteNode(root, 5);
        printInorder(root);
        System.out.println(" Expected: 1 8 9");
        
        // Test 5: Delete non-existent node
        System.out.println("\nDeleting non-existent node 100:");
        root = deleter.deleteNode(root, 100);
        printInorder(root);
        System.out.println(" Expected: 1 8 9 (no change)");
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
    
    /**
     * Helper to visualize tree structure
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
}