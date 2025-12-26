import java.util.HashMap;
import java.util.Map;

/** 
 * Construct a binary tree from inorder and postorder traversal sequences
 * Problem: 106. Construct Binary Tree from Inorder and Postorder Traversal (LeetCode)
 * 
 * Key Insight: 
 * - The last element in postorder is always the root
 * - In inorder, elements left of root are in left subtree, right are in right subtree
 * - Use the root position in inorder to determine subtree sizes
 */
public class PostIn {
    static class TreeNode { 
        int val; 
        TreeNode left, right; 
        TreeNode(int v) { val = v; } 
    }

    /**
     * Main method: Construct tree from inorder and postorder arrays
     * 
     * @param inorder   Inorder traversal array
     * @param postorder Postorder traversal array
     * @return Root of the constructed binary tree
     * 
     * Time Complexity: O(n) where n is number of nodes
     * Space Complexity: O(n) for hashmap, O(h) for recursion stack
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        // Edge cases
        if (inorder == null || postorder == null || 
            inorder.length != postorder.length || inorder.length == 0) {
            return null;
        }
        
        // Build hashmap for O(1) lookup of inorder indices
        Map<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            indexMap.put(inorder[i], i);
        }
        
        // Start recursive construction
        return buildTreeHelper(inorder, 0, inorder.length - 1,
                              postorder, 0, postorder.length - 1,
                              indexMap);
    }
    
    /**
     * Helper method: Recursively build tree from array segments
     * 
     * @param in      Inorder array
     * @param inStart Start index in inorder array (inclusive)
     * @param inEnd   End index in inorder array (inclusive)
     * @param post    Postorder array
     * @param postStart Start index in postorder array (inclusive)
     * @param postEnd   End index in postorder array (inclusive)
     * @param indexMap HashMap for O(1) inorder index lookup
     * @return Root node of subtree
     */
    private TreeNode buildTreeHelper(int[] in, int inStart, int inEnd,
                                    int[] post, int postStart, int postEnd,
                                    Map<Integer, Integer> indexMap) {
        // Base case: empty segment
        if (inStart > inEnd || postStart > postEnd) {
            return null;
        }
        
        // Root is always the last element in postorder segment
        TreeNode root = new TreeNode(post[postEnd]);
        
        // Find root position in inorder array
        int rootIndexInInorder = indexMap.get(post[postEnd]);
        
        // Calculate size of left subtree
        int leftSubtreeSize = rootIndexInInorder - inStart;
        
        // Recursively build left subtree
        // Inorder left: [inStart, rootIndexInInorder - 1]
        // Postorder left: [postStart, postStart + leftSubtreeSize - 1]
        root.left = buildTreeHelper(in, inStart, rootIndexInInorder - 1,
                                   post, postStart, postStart + leftSubtreeSize - 1,
                                   indexMap);
        
        // Recursively build right subtree
        // Inorder right: [rootIndexInInorder + 1, inEnd]
        // Postorder right: [postStart + leftSubtreeSize, postEnd - 1]
        root.right = buildTreeHelper(in, rootIndexInInorder + 1, inEnd,
                                    post, postStart + leftSubtreeSize, postEnd - 1,
                                    indexMap);
        
        return root;
    }
    
    /**
     * Alternative approach: Without using index map
     * Less efficient: O(n²) time due to linear search in each recursion
     */
    public TreeNode buildTreeNoMap(int[] inorder, int[] postorder) {
        return buildTreeNoMapHelper(inorder, 0, inorder.length - 1,
                                   postorder, 0, postorder.length - 1);
    }
    
    private TreeNode buildTreeNoMapHelper(int[] in, int inStart, int inEnd,
                                         int[] post, int postStart, int postEnd) {
        if (inStart > inEnd || postStart > postEnd) return null;
        
        TreeNode root = new TreeNode(post[postEnd]);
        
        // Find root in inorder (linear search)
        int rootIndexInInorder = inStart;
        for (; rootIndexInInorder <= inEnd; rootIndexInInorder++) {
            if (in[rootIndexInInorder] == post[postEnd]) break;
        }
        
        int leftSize = rootIndexInInorder - inStart;
        
        root.left = buildTreeNoMapHelper(in, inStart, rootIndexInInorder - 1,
                                        post, postStart, postStart + leftSize - 1);
        
        root.right = buildTreeNoMapHelper(in, rootIndexInInorder + 1, inEnd,
                                         post, postStart + leftSize, postEnd - 1);
        
        return root;
    }
    
    /**
     * Construct tree from inorder and preorder (for comparison)
     * Problem: 105. Construct Binary Tree from Preorder and Inorder Traversal
     */
    public TreeNode buildTreeFromPreorderInorder(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || 
            preorder.length != inorder.length || preorder.length == 0) {
            return null;
        }
        
        Map<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            indexMap.put(inorder[i], i);
        }
        
        return buildTreePreInHelper(preorder, 0, preorder.length - 1,
                                   inorder, 0, inorder.length - 1,
                                   indexMap);
    }
    
    private TreeNode buildTreePreInHelper(int[] pre, int preStart, int preEnd,
                                         int[] in, int inStart, int inEnd,
                                         Map<Integer, Integer> indexMap) {
        if (preStart > preEnd || inStart > inEnd) return null;
        
        // Root is first element in preorder
        TreeNode root = new TreeNode(pre[preStart]);
        int rootIndexInInorder = indexMap.get(pre[preStart]);
        int leftSize = rootIndexInInorder - inStart;
        
        // Preorder: [root][left...][right...]
        root.left = buildTreePreInHelper(pre, preStart + 1, preStart + leftSize,
                                        in, inStart, rootIndexInInorder - 1,
                                        indexMap);
        
        root.right = buildTreePreInHelper(pre, preStart + leftSize + 1, preEnd,
                                         in, rootIndexInInorder + 1, inEnd,
                                         indexMap);
        
        return root;
    }
    
    /**
     * Test cases and examples
     */
    public static void main(String[] args) {
        PostIn constructor = new PostIn();
        
        // Test Case 1: Simple tree
        // Tree:
        //     3
        //    / \
        //   9  20
        //      / \
        //     15  7
        int[] inorder1 = {9, 3, 15, 20, 7};
        int[] postorder1 = {9, 15, 7, 20, 3};
        
        System.out.println("Test Case 1:");
        TreeNode root1 = constructor.buildTree(inorder1, postorder1);
        printTree(root1); // Should print tree in level order
        
        // Verify by doing traversals
        System.out.print("Inorder of constructed tree: ");
        printInorder(root1);
        System.out.println("\nExpected: 9 3 15 20 7");
        
        System.out.print("Postorder of constructed tree: ");
        printPostorder(root1);
        System.out.println("\nExpected: 9 15 7 20 3");
        
        // Test Case 2: Single node
        System.out.println("\nTest Case 2: Single node");
        int[] inorder2 = {1};
        int[] postorder2 = {1};
        TreeNode root2 = constructor.buildTree(inorder2, postorder2);
        printTree(root2);
        
        // Test Case 3: Left skewed tree
        // Tree:
        //     1
        //    /
        //   2
        //  /
        // 3
        System.out.println("\nTest Case 3: Left-skewed tree");
        int[] inorder3 = {3, 2, 1};
        int[] postorder3 = {3, 2, 1};
        TreeNode root3 = constructor.buildTree(inorder3, postorder3);
        printTree(root3);
        
        // Test Case 4: Right skewed tree
        // Tree:
        //   1
        //    \
        //     2
        //      \
        //       3
        System.out.println("\nTest Case 4: Right-skewed tree");
        int[] inorder4 = {1, 2, 3};
        int[] postorder4 = {3, 2, 1};
        TreeNode root4 = constructor.buildTree(inorder4, postorder4);
        printTree(root4);
        
        // Test Case 5: Complete tree
        // Tree:
        //       1
        //      / \
        //     2   3
        //    / \ / \
        //   4  5 6  7
        System.out.println("\nTest Case 5: Complete tree");
        int[] inorder5 = {4, 2, 5, 1, 6, 3, 7};
        int[] postorder5 = {4, 5, 2, 6, 7, 3, 1};
        TreeNode root5 = constructor.buildTree(inorder5, postorder5);
        printTree(root5);
        
        // Test Case 6: Tree with duplicates (problematic case)
        // Note: This implementation assumes unique values
        // For duplicates, we need more sophisticated approach
        System.out.println("\nTest Case 6: Tree with unique values is required");
    }
    
    /**
     * Utility methods for printing trees
     */
    private static void printTree(TreeNode root) {
        System.out.println("Level order traversal:");
        if (root == null) {
            System.out.println("null");
            return;
        }
        
        java.util.LinkedList<TreeNode> queue = new java.util.LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                if (node == null) {
                    System.out.print("null ");
                } else {
                    System.out.print(node.val + " ");
                    queue.add(node.left);
                    queue.add(node.right);
                }
            }
            System.out.println();
        }
    }
    
    private static void printInorder(TreeNode root) {
        if (root == null) return;
        printInorder(root.left);
        System.out.print(root.val + " ");
        printInorder(root.right);
    }
    
    private static void printPostorder(TreeNode root) {
        if (root == null) return;
        printPostorder(root.left);
        printPostorder(root.right);
        System.out.print(root.val + " ");
    }
    
    /**
     * Complexity Analysis:
     * 
     * With HashMap:
     * - Time: O(n) - Each node processed once
     * - Space: O(n) for hashmap + O(h) for recursion stack
     * 
     * Without HashMap (linear search):
     * - Time: O(n²) in worst case (skewed tree)
     * - Space: O(h) for recursion stack
     * 
     * Where:
     * - n = number of nodes
     * - h = height of tree (log n for balanced, n for skewed)
     */
    
    /**
     * Key Observations:
     * 
     * 1. Postorder: [left subtree][right subtree][root]
     * 2. Inorder: [left subtree][root][right subtree]
     * 3. The last element in postorder is always the root
     * 4. Once we find root in inorder, we know:
     *    - Elements left of root are in left subtree
     *    - Elements right of root are in right subtree
     * 5. Left subtree size = rootIndexInInorder - inStart
     * 
     * Example:
     *   Inorder:   [9, 3, 15, 20, 7]
     *   Postorder: [9, 15, 7, 20, 3]
     *   
     *   Steps:
     *   1. Root = 3 (last in postorder)
     *   2. Find 3 in inorder -> index 1
     *   3. Left subtree: inorder[0..0] = [9], postorder[0..0] = [9]
     *   4. Right subtree: inorder[2..4] = [15,20,7], postorder[1..3] = [15,7,20]
     *   5. Recursively build
     */
    
    /**
     * Important Edge Cases:
     * 1. Empty arrays (return null)
     * 2. Single element arrays
     * 3. Arrays with different lengths (invalid input)
     * 4. Duplicate values (this implementation doesn't handle)
     * 5. Very large trees (recursion depth may cause stack overflow)
     */
    
    /**
     * Handling Duplicate Values:
     * If tree has duplicate values, we need additional information.
     * Options:
     * 1. Modify TreeNode to include unique IDs
     * 2. Use array indices as unique identifiers
     * 3. Require that values are unique (common in these problems)
     */
    
    /**
     * Iterative Solution (Alternative approach)
     * Using stack to avoid recursion
     */
    public TreeNode buildTreeIterative(int[] inorder, int[] postorder) {
        if (inorder == null || postorder == null || 
            inorder.length == 0 || postorder.length == 0) {
            return null;
        }
        
        Map<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inMap.put(inorder[i], i);
        }
        
        TreeNode root = new TreeNode(postorder[postorder.length - 1]);
        java.util.Stack<TreeNode> stack = new java.util.Stack<>();
        stack.push(root);
        
        for (int i = postorder.length - 2; i >= 0; i--) {
            TreeNode node = new TreeNode(postorder[i]);
            
            if (inMap.get(node.val) > inMap.get(stack.peek().val)) {
                // Current node is right child of stack top
                stack.peek().right = node;
            } else {
                // Current node is in left subtree
                TreeNode parent = null;
                while (!stack.isEmpty() && inMap.get(node.val) < inMap.get(stack.peek().val)) {
                    parent = stack.pop();
                }
                parent.left = node;
            }
            stack.push(node);
        }
        
        return root;
    }
    
    /**
     * Validate constructed tree
     */
    public boolean validateTree(TreeNode root, int[] inorder, int[] postorder) {
        List<Integer> inorderResult = new ArrayList<>();
        List<Integer> postorderResult = new ArrayList<>();
        
        inorderTraversal(root, inorderResult);
        postorderTraversal(root, postorderResult);
        
        // Convert to arrays for comparison
        int[] inorderCheck = inorderResult.stream().mapToInt(i -> i).toArray();
        int[] postorderCheck = postorderResult.stream().mapToInt(i -> i).toArray();
        
        return Arrays.equals(inorder, inorderCheck) && 
               Arrays.equals(postorder, postorderCheck);
    }
    
    private void inorderTraversal(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inorderTraversal(node.left, result);
        result.add(node.val);
        inorderTraversal(node.right, result);
    }
    
    private void postorderTraversal(TreeNode node, List<Integer> result) {
        if (node == null) return;
        postorderTraversal(node.left, result);
        postorderTraversal(node.right, result);
        result.add(node.val);
    }
}