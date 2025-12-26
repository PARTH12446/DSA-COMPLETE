/**
 * Constructs a Binary Search Tree (BST) from its preorder traversal.
 * 
 * Preorder traversal: Root, Left subtree, Right subtree
 * Key property: In BST preorder, first element is always root.
 * Subsequent elements: 
 *   - All elements less than root form left subtree
 *   - All elements greater than root form right subtree
 * 
 * Approach: Stack-based iterative construction
 * - First element becomes root, push to stack
 * - For each subsequent value:
 *   1. If value < top of stack: 
 *      - Becomes left child of top
 *      - Push to stack (as it could be parent of future nodes)
 *   2. If value > top of stack:
 *      - Pop from stack until we find appropriate parent
 *      - Value becomes right child of last popped node
 *      - Push value to stack
 * 
 * Intuition: Stack maintains the "current ancestor chain"
 * Last popped node is the largest ancestor smaller than current value
 * 
 * Time Complexity: O(n) - each node pushed/popped at most once
 * Space Complexity: O(h) - stack size, where h is tree height
 * 
 * Example: preorder = [8,5,1,7,10,12]
 * Process:
 * 1. Root: 8, stack = [8]
 * 2. 5 < 8: left child of 8, stack = [8,5]
 * 3. 1 < 5: left child of 5, stack = [8,5,1]
 * 4. 7 > 1: pop 1, 7 > 5: pop 5, 7 < 8: right child of 5, stack = [8,7]
 * 5. 10 > 7: pop 7, 10 > 8: pop 8, right child of 8, stack = [10]
 * 6. 12 > 10: right child of 10, stack = [10,12]
 */
public class ConstructFromPreorder {
    /**
     * TreeNode structure for binary tree.
     */
    static class TreeNode { 
        int val; 
        TreeNode left, right; 
        TreeNode(int v) {
            val = v;
        }
    }

    /**
     * Constructs BST from preorder traversal array.
     * 
     * @param preorder Preorder traversal of BST (guaranteed valid)
     * @return Root node of reconstructed BST
     */
    public TreeNode bstFromPreorder(int[] preorder) {
        // First element is always root in preorder traversal
        TreeNode root = new TreeNode(preorder[0]);
        java.util.Stack<TreeNode> st = new java.util.Stack<>();
        st.push(root);
        
        // Process remaining elements
        for (int i = 1; i < preorder.length; i++) {
            int val = preorder[i];
            
            if (val < st.peek().val) {
                // Current value is less than top of stack
                // It becomes left child of top node
                TreeNode parent = st.peek();
                parent.left = new TreeNode(val);
                st.push(parent.left);  // New node could be parent of future nodes
            } else {
                // Current value is greater than top of stack
                // Find correct parent by popping until we find node < val
                TreeNode last = null;
                
                // Pop nodes while current value > node value
                // Last popped node is the largest ancestor smaller than current value
                while (!st.isEmpty() && st.peek().val < val) {
                    last = st.pop();
                }
                
                // 'last' is now the parent for current value
                // Current value becomes right child of 'last'
                last.right = new TreeNode(val);
                st.push(last.right);  // New node could be parent of future nodes
            }
        }
        return root;
    }
    
    /**
     * Alternative: Recursive solution with bounds
     * Time: O(n), Space: O(h) for recursion stack
     */
    public TreeNode bstFromPreorderRecursive(int[] preorder) {
        // Use array to maintain current index across recursive calls
        return build(preorder, new int[]{0}, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    private TreeNode build(int[] preorder, int[] idx, int lower, int upper) {
        if (idx[0] >= preorder.length) return null;
        
        int val = preorder[idx[0]];
        // Check if current value fits in current subtree bounds
        if (val < lower || val > upper) return null;
        
        TreeNode node = new TreeNode(val);
        idx[0]++;  // Move to next element
        
        // Left subtree: values must be between current lower bound and node value
        node.left = build(preorder, idx, lower, val);
        // Right subtree: values must be between node value and current upper bound
        node.right = build(preorder, idx, val, upper);
        
        return node;
    }
    
    /**
     * Test cases
     */
    public static void main(String[] args) {
        ConstructFromPreorder constructor = new ConstructFromPreorder();
        
        // Test case 1: Normal BST
        int[] preorder1 = {8, 5, 1, 7, 10, 12};
        TreeNode root1 = constructor.bstFromPreorder(preorder1);
        System.out.println("Test 1 - Tree constructed from preorder [8,5,1,7,10,12]");
        System.out.println("Expected structure:");
        System.out.println("        8");
        System.out.println("       / \\");
        System.out.println("      5   10");
        System.out.println("     / \\   \\");
        System.out.println("    1   7   12");
        
        // Test case 2: Right-skewed tree
        int[] preorder2 = {1, 2, 3, 4, 5};
        TreeNode root2 = constructor.bstFromPreorder(preorder2);
        System.out.println("\nTest 2 - Right-skewed tree [1,2,3,4,5]");
        
        // Test case 3: Left-skewed tree
        int[] preorder3 = {5, 4, 3, 2, 1};
        TreeNode root3 = constructor.bstFromPreorder(preorder3);
        System.out.println("Test 3 - Left-skewed tree [5,4,3,2,1]");
        
        // Test case 4: Single node
        int[] preorder4 = {10};
        TreeNode root4 = constructor.bstFromPreorder(preorder4);
        System.out.println("Test 4 - Single node [10]");
        
        // Verify by printing preorder traversal of constructed tree
        System.out.println("\nVerification - Preorder traversal of constructed trees:");
        System.out.print("Tree 1: ");
        printPreorder(root1);
        System.out.println("\nOriginal: [8,5,1,7,10,12]");
    }
    
    /**
     * Helper to print preorder traversal for verification
     */
    private static void printPreorder(TreeNode root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        printPreorder(root.left);
        printPreorder(root.right);
    }
}