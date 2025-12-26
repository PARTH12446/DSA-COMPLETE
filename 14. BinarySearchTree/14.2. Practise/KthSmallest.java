import java.util.Stack;

/**
 * Finds the k-th smallest element in a Binary Search Tree (BST).
 * 
 * Key Properties of BST:
 * - Inorder traversal of BST yields elements in ASCENDING order
 * - k-th smallest = k-th element in inorder traversal (1-indexed)
 * 
 * Approach: Iterative Inorder Traversal using Stack
 * - Simulates recursive inorder without recursion overhead
 * - Traverse left as far as possible (pushing nodes to stack)
 * - Process node (check if it's the k-th smallest)
 * - Traverse right subtree
 * 
 * Time Complexity: O(k) in best case, O(n) in worst case
 * - Actually visits nodes until k-th smallest is found
 * - Worst case (k = n or k-th is last): O(n)
 * 
 * Space Complexity: O(h) where h is tree height
 * - Stack stores at most h nodes (path from root to leaf)
 * - O(log n) for balanced BST, O(n) for skewed tree
 * 
 * Alternative Approaches:
 * 1. Recursive inorder (O(h) recursion stack)
 * 2. Morris traversal (O(1) extra space but modifies tree temporarily)
 * 3. Augmented BST with subtree counts (O(log n) query after O(n) preprocessing)
 */
class KthSmallestBST {
    static class TreeNode { 
        int val; 
        TreeNode left, right; 
        TreeNode(int v) {
            val = v;
        }
    }

    /**
     * Finds k-th smallest element using iterative inorder traversal.
     * 
     * @param root Root of the BST
     * @param k    Position (1-indexed) of element to find
     * @return     k-th smallest element, or -1 if k is invalid
     */
    int kthSmallest(TreeNode root, int k) {
        Stack<TreeNode> st = new Stack<>();
        TreeNode curr = root;
        
        while (curr != null || !st.isEmpty()) {
            // Go as far left as possible, pushing nodes to stack
            while (curr != null) { 
                st.push(curr); 
                curr = curr.left; 
            }
            
            // Process the most recent node (leftmost unprocessed)
            curr = st.pop();
            
            // Check if this is the k-th smallest
            if (--k == 0) {
                return curr.val;
            }
            
            // Move to right subtree
            curr = curr.right;
        }
        
        // k is larger than number of nodes in tree
        return -1;
    }
    
    /**
     * Recursive version of k-th smallest (simpler but uses recursion stack).
     */
    int kthSmallestRecursive(TreeNode root, int k) {
        int[] result = new int[1];  // Array to store result
        int[] count = new int[1];   // Array to maintain count across recursive calls
        count[0] = k;
        
        inorderRecursive(root, count, result);
        return result[0];
    }
    
    private void inorderRecursive(TreeNode node, int[] count, int[] result) {
        if (node == null) return;
        
        // Traverse left subtree
        inorderRecursive(node.left, count, result);
        
        // Process current node
        if (--count[0] == 0) {
            result[0] = node.val;
            return;  // Early termination if found
        }
        
        // Traverse right subtree
        inorderRecursive(node.right, count, result);
    }
    
    /**
     * Finds k-th LARGEST element in BST.
     * Reverse inorder traversal (right-root-left).
     */
    int kthLargest(TreeNode root, int k) {
        Stack<TreeNode> st = new Stack<>();
        TreeNode curr = root;
        
        while (curr != null || !st.isEmpty()) {
            // Go as far right as possible
            while (curr != null) { 
                st.push(curr); 
                curr = curr.right; 
            }
            
            curr = st.pop();
            if (--k == 0) return curr.val;
            curr = curr.left;
        }
        
        return -1;
    }
    
    /**
     * Morris Traversal - O(1) extra space (no stack)
     * Temporarily modifies tree by creating threads.
     */
    int kthSmallestMorris(TreeNode root, int k) {
        TreeNode curr = root;
        
        while (curr != null) {
            if (curr.left == null) {
                // No left child, process current node
                if (--k == 0) return curr.val;
                curr = curr.right;
            } else {
                // Find inorder predecessor
                TreeNode pred = curr.left;
                while (pred.right != null && pred.right != curr) {
                    pred = pred.right;
                }
                
                if (pred.right == null) {
                    // Create thread to come back to current node
                    pred.right = curr;
                    curr = curr.left;
                } else {
                    // Thread already exists, remove it
                    pred.right = null;
                    if (--k == 0) return curr.val;
                    curr = curr.right;
                }
            }
        }
        
        return -1;
    }
    
    /**
     * Augmented Tree Node with count of nodes in subtree.
     * Allows O(log n) queries after O(n) preprocessing.
     */
    static class AugTreeNode {
        int val;
        int leftCount;  // Number of nodes in left subtree
        AugTreeNode left, right;
        AugTreeNode(int v) {
            val = v;
            leftCount = 0;
        }
    }
    
    int kthSmallestAugmented(AugTreeNode root, int k) {
        AugTreeNode curr = root;
        
        while (curr != null) {
            if (k == curr.leftCount + 1) {
                return curr.val;
            } else if (k <= curr.leftCount) {
                // k-th smallest is in left subtree
                curr = curr.left;
            } else {
                // k-th smallest is in right subtree
                k -= (curr.leftCount + 1);
                curr = curr.right;
            }
        }
        
        return -1;
    }
    
    /**
     * Test cases and examples
     */
    public static void main(String[] args) {
        KthSmallestBST finder = new KthSmallestBST();
        
        // Build BST:
        //       7
        //      / \
        //     3   9
        //    / \   \
        //   2   5   10
        //      / \
        //     4   6
        TreeNode root = new TreeNode(7);
        root.left = new TreeNode(3);
        root.right = new TreeNode(9);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(10);
        root.left.right.left = new TreeNode(4);
        root.left.right.right = new TreeNode(6);
        
        System.out.println("BST structure:");
        System.out.println("       7");
        System.out.println("      / \\");
        System.out.println("     3   9");
        System.out.println("    / \\   \\");
        System.out.println("   2   5   10");
        System.out.println("      / \\");
        System.out.println("     4   6");
        System.out.println();
        
        // Test k-th smallest
        System.out.println("=== Testing k-th Smallest ===");
        
        int[] testCases = {1, 2, 3, 4, 5, 6, 7};
        int[] expected = {2, 3, 4, 5, 6, 7, 9};
        
        for (int i = 0; i < testCases.length; i++) {
            int k = testCases[i];
            int result = finder.kthSmallest(root, k);
            System.out.printf("k=%d: Expected %d, Got %d %s%n", 
                k, expected[i], result, 
                result == expected[i] ? "✓" : "✗");
        }
        
        // Test k-th largest
        System.out.println("\n=== Testing k-th Largest ===");
        
        int[] largestTests = {1, 2, 3, 4};
        int[] largestExpected = {10, 9, 7, 6};
        
        for (int i = 0; i < largestTests.length; i++) {
            int k = largestTests[i];
            int result = finder.kthLargest(root, k);
            System.out.printf("k=%d (largest): Expected %d, Got %d %s%n", 
                k, largestExpected[i], result,
                result == largestExpected[i] ? "✓" : "✗");
        }
        
        // Test edge cases
        System.out.println("\n=== Testing Edge Cases ===");
        
        // k larger than number of nodes
        System.out.println("k=10 (more than nodes): " + finder.kthSmallest(root, 10) + " (expected -1)");
        
        // k = 0 or negative
        System.out.println("k=0: " + finder.kthSmallest(root, 0) + " (expected -1)");
        
        // Single node tree
        TreeNode single = new TreeNode(42);
        System.out.println("Single node, k=1: " + finder.kthSmallest(single, 1) + " (expected 42)");
        
        // Empty tree
        System.out.println("Empty tree, k=1: " + finder.kthSmallest(null, 1) + " (expected -1)");
        
        // Compare different methods
        System.out.println("\n=== Comparing Methods ===");
        int k = 4;
        System.out.println("k=" + k + ":");
        System.out.println("  Iterative: " + finder.kthSmallest(root, k));
        System.out.println("  Recursive: " + finder.kthSmallestRecursive(root, k));
        System.out.println("  Morris:    " + finder.kthSmallestMorris(root, k));
        
        // Performance test idea
        System.out.println("\nNote: For frequent k-th queries on static BST,");
        System.out.println("consider augmenting nodes with subtree counts.");
    }
    
    /**
     * Utility to print inorder traversal for verification
     */
    private static void printInorder(TreeNode root) {
        if (root == null) return;
        printInorder(root.left);
        System.out.print(root.val + " ");
        printInorder(root.right);
    }
    
    /**
     * Utility to count total nodes in tree
     */
    private int countNodes(TreeNode root) {
        if (root == null) return 0;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }
}