import java.util.HashMap;
import java.util.Map;

/** 
 * Construct a binary tree from preorder and inorder traversal sequences
 * Problem: 105. Construct Binary Tree from Preorder and Inorder Traversal (LeetCode)
 * 
 * Key Insight: 
 * - The first element in preorder is always the root
 * - In inorder, elements left of root are in left subtree, right are in right subtree
 * - Use the root position in inorder to determine subtree sizes
 */
public class PreIn {
    static class TreeNode { 
        int val; 
        TreeNode left, right; 
        TreeNode(int v) { val = v; } 
    }

    /**
     * Main method: Construct tree from preorder and inorder arrays
     * 
     * @param preorder Preorder traversal array
     * @param inorder  Inorder traversal array
     * @return Root of the constructed binary tree
     * 
     * Time Complexity: O(n) where n is number of nodes
     * Space Complexity: O(n) for hashmap, O(h) for recursion stack
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // Edge cases validation
        if (preorder == null || inorder == null || 
            preorder.length != inorder.length || preorder.length == 0) {
            return null;
        }
        
        // Build hashmap for O(1) lookup of inorder indices
        Map<Integer, Integer> inorderIndexMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderIndexMap.put(inorder[i], i);
        }
        
        // Start recursive construction
        return buildTreeHelper(preorder, 0, preorder.length - 1,
                              inorder, 0, inorder.length - 1,
                              inorderIndexMap);
    }
    
    /**
     * Helper method: Recursively build tree from array segments
     * 
     * @param pre      Preorder array
     * @param preStart Start index in preorder array (inclusive)
     * @param preEnd   End index in preorder array (inclusive)
     * @param in       Inorder array
     * @param inStart  Start index in inorder array (inclusive)
     * @param inEnd    End index in inorder array (inclusive)
     * @param indexMap HashMap for O(1) inorder index lookup
     * @return Root node of subtree
     */
    private TreeNode buildTreeHelper(int[] pre, int preStart, int preEnd,
                                    int[] in, int inStart, int inEnd,
                                    Map<Integer, Integer> indexMap) {
        // Base case: empty segment
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }
        
        // Root is always the first element in preorder segment
        TreeNode root = new TreeNode(pre[preStart]);
        
        // Find root position in inorder array
        int rootIndexInInorder = indexMap.get(pre[preStart]);
        
        // Calculate size of left subtree
        int leftSubtreeSize = rootIndexInInorder - inStart;
        
        // Recursively build left subtree
        // Preorder left: elements immediately after root, size = leftSubtreeSize
        // Inorder left: elements before root in inorder
        root.left = buildTreeHelper(pre, preStart + 1, preStart + leftSubtreeSize,
                                   in, inStart, rootIndexInInorder - 1,
                                   indexMap);
        
        // Recursively build right subtree
        // Preorder right: elements after left subtree in preorder
        // Inorder right: elements after root in inorder
        root.right = buildTreeHelper(pre, preStart + leftSubtreeSize + 1, preEnd,
                                    in, rootIndexInInorder + 1, inEnd,
                                    indexMap);
        
        return root;
    }
    
    /**
     * Alternative: Iterative solution using stack
     * Time: O(n), Space: O(n)
     */
    public TreeNode buildTreeIterative(int[] preorder, int[] inorder) {
        if (preorder == null || preorder.length == 0) return null;
        
        // Build inorder index map
        Map<Integer, Integer> inorderIndex = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderIndex.put(inorder[i], i);
        }
        
        TreeNode root = new TreeNode(preorder[0]);
        java.util.Stack<TreeNode> stack = new java.util.Stack<>();
        stack.push(root);
        
        for (int i = 1; i < preorder.length; i++) {
            TreeNode node = new TreeNode(preorder[i]);
            
            if (inorderIndex.get(node.val) < inorderIndex.get(stack.peek().val)) {
                // Current node is left child of stack top
                stack.peek().left = node;
            } else {
                // Current node is right child of some node
                TreeNode parent = null;
                while (!stack.isEmpty() && 
                       inorderIndex.get(node.val) > inorderIndex.get(stack.peek().val)) {
                    parent = stack.pop();
                }
                parent.right = node;
            }
            stack.push(node);
        }
        
        return root;
    }
    
    /**
     * Alternative: Without using HashMap (less efficient)
     * Time: O(n²) in worst case due to linear search
     */
    public TreeNode buildTreeNoMap(int[] preorder, int[] inorder) {
        return buildTreeNoMapHelper(preorder, 0, preorder.length - 1,
                                   inorder, 0, inorder.length - 1);
    }
    
    private TreeNode buildTreeNoMapHelper(int[] pre, int preStart, int preEnd,
                                         int[] in, int inStart, int inEnd) {
        if (preStart > preEnd || inStart > inEnd) return null;
        
        TreeNode root = new TreeNode(pre[preStart]);
        
        // Find root in inorder (linear search - O(n) per node)
        int rootIndexInInorder = inStart;
        for (; rootIndexInInorder <= inEnd; rootIndexInInorder++) {
            if (in[rootIndexInInorder] == pre[preStart]) break;
        }
        
        int leftSize = rootIndexInInorder - inStart;
        
        root.left = buildTreeNoMapHelper(pre, preStart + 1, preStart + leftSize,
                                        in, inStart, rootIndexInInorder - 1);
        
        root.right = buildTreeNoMapHelper(pre, preStart + leftSize + 1, preEnd,
                                         in, rootIndexInInorder + 1, inEnd);
        
        return root;
    }
    
    /**
     * Test cases and examples
     */
    public static void main(String[] args) {
        PreIn constructor = new PreIn();
        
        // Test Case 1: Standard tree
        // Tree:
        //     3
        //    / \
        //   9  20
        //      / \
        //     15  7
        int[] preorder1 = {3, 9, 20, 15, 7};
        int[] inorder1 = {9, 3, 15, 20, 7};
        
        System.out.println("Test Case 1: Standard tree");
        TreeNode root1 = constructor.buildTree(preorder1, inorder1);
        printTree(root1);
        
        // Verify traversals
        System.out.print("Preorder of constructed tree: ");
        printPreorder(root1);
        System.out.println("\nExpected: 3 9 20 15 7");
        
        System.out.print("Inorder of constructed tree: ");
        printInorder(root1);
        System.out.println("\nExpected: 9 3 15 20 7");
        
        // Test Case 2: Single node
        System.out.println("\nTest Case 2: Single node");
        int[] preorder2 = {1};
        int[] inorder2 = {1};
        TreeNode root2 = constructor.buildTree(preorder2, inorder2);
        printTree(root2);
        
        // Test Case 3: Left-skewed tree
        // Tree:
        //     1
        //    /
        //   2
        //  /
        // 3
        System.out.println("\nTest Case 3: Left-skewed tree");
        int[] preorder3 = {1, 2, 3};
        int[] inorder3 = {3, 2, 1};
        TreeNode root3 = constructor.buildTree(preorder3, inorder3);
        printTree(root3);
        
        // Test Case 4: Right-skewed tree
        // Tree:
        //   1
        //    \
        //     2
        //      \
        //       3
        System.out.println("\nTest Case 4: Right-skewed tree");
        int[] preorder4 = {1, 2, 3};
        int[] inorder4 = {1, 2, 3};
        TreeNode root4 = constructor.buildTree(preorder4, inorder4);
        printTree(root4);
        
        // Test Case 5: Complete binary tree
        // Tree:
        //       1
        //      / \
        //     2   3
        //    / \ / \
        //   4  5 6  7
        System.out.println("\nTest Case 5: Complete tree");
        int[] preorder5 = {1, 2, 4, 5, 3, 6, 7};
        int[] inorder5 = {4, 2, 5, 1, 6, 3, 7};
        TreeNode root5 = constructor.buildTree(preorder5, inorder5);
        printTree(root5);
        
        // Test Case 6: Compare different methods
        System.out.println("\nTest Case 6: Comparing methods");
        TreeNode rootRecursive = constructor.buildTree(preorder1, inorder1);
        TreeNode rootIterative = constructor.buildTreeIterative(preorder1, inorder1);
        TreeNode rootNoMap = constructor.buildTreeNoMap(preorder1, inorder1);
        
        System.out.println("Recursive and iterative results are equal: " + 
                          treesEqual(rootRecursive, rootIterative));
        System.out.println("Recursive and no-map results are equal: " + 
                          treesEqual(rootRecursive, rootNoMap));
    }
    
    /**
     * Utility methods for printing and comparing trees
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
    
    private static void printPreorder(TreeNode root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        printPreorder(root.left);
        printPreorder(root.right);
    }
    
    private static void printInorder(TreeNode root) {
        if (root == null) return;
        printInorder(root.left);
        System.out.print(root.val + " ");
        printInorder(root.right);
    }
    
    private static boolean treesEqual(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        if (t1.val != t2.val) return false;
        return treesEqual(t1.left, t2.left) && treesEqual(t1.right, t2.right);
    }
    
    /**
     * Visualization helper for understanding the algorithm
     */
    public void visualizeConstruction(int[] preorder, int[] inorder) {
        System.out.println("\nVisualizing construction process:");
        System.out.println("Preorder: " + java.util.Arrays.toString(preorder));
        System.out.println("Inorder:  " + java.util.Arrays.toString(inorder));
        
        Map<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            indexMap.put(inorder[i], i);
        }
        
        visualizeHelper(preorder, 0, preorder.length - 1,
                       inorder, 0, inorder.length - 1,
                       indexMap, 0);
    }
    
    private TreeNode visualizeHelper(int[] pre, int preStart, int preEnd,
                                    int[] in, int inStart, int inEnd,
                                    Map<Integer, Integer> indexMap, int depth) {
        if (preStart > preEnd || inStart > inEnd) return null;
        
        TreeNode root = new TreeNode(pre[preStart]);
        int rootIndex = indexMap.get(pre[preStart]);
        int leftSize = rootIndex - inStart;
        
        // Print current step
        String indent = "  ".repeat(depth);
        System.out.println(indent + "Building root: " + root.val);
        System.out.println(indent + "  Preorder segment: " + 
                          java.util.Arrays.toString(java.util.Arrays.copyOfRange(pre, preStart, preEnd + 1)));
        System.out.println(indent + "  Inorder segment: " + 
                          java.util.Arrays.toString(java.util.Arrays.copyOfRange(in, inStart, inEnd + 1)));
        System.out.println(indent + "  Root index in inorder: " + rootIndex);
        System.out.println(indent + "  Left subtree size: " + leftSize);
        
        root.left = visualizeHelper(pre, preStart + 1, preStart + leftSize,
                                   in, inStart, rootIndex - 1,
                                   indexMap, depth + 1);
        
        root.right = visualizeHelper(pre, preStart + leftSize + 1, preEnd,
                                    in, rootIndex + 1, inEnd,
                                    indexMap, depth + 1);
        
        return root;
    }
    
    /**
     * Extension: Construct BST from preorder traversal
     * Simpler because BST property gives us ordering
     */
    public TreeNode bstFromPreorder(int[] preorder) {
        return bstFromPreorderHelper(preorder, 0, preorder.length - 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    private TreeNode bstFromPreorderHelper(int[] preorder, int index, int end, int min, int max) {
        if (index > end) return null;
        
        int val = preorder[index];
        if (val < min || val > max) return null;
        
        TreeNode root = new TreeNode(val);
        
        // Find the boundary between left and right subtrees
        int rightStart = index + 1;
        while (rightStart <= end && preorder[rightStart] < val) {
            rightStart++;
        }
        
        root.left = bstFromPreorderHelper(preorder, index + 1, rightStart - 1, min, val);
        root.right = bstFromPreorderHelper(preorder, rightStart, end, val, max);
        
        return root;
    }
    
    /**
     * Validation method to check if constructed tree produces correct traversals
     */
    public boolean validateConstruction(int[] preorder, int[] inorder, TreeNode root) {
        List<Integer> preResult = new ArrayList<>();
        List<Integer> inResult = new ArrayList<>();
        
        getPreorder(root, preResult);
        getInorder(root, inResult);
        
        int[] preCheck = preResult.stream().mapToInt(i -> i).toArray();
        int[] inCheck = inResult.stream().mapToInt(i -> i).toArray();
        
        return Arrays.equals(preorder, preCheck) && Arrays.equals(inorder, inCheck);
    }
    
    private void getPreorder(TreeNode node, List<Integer> result) {
        if (node == null) return;
        result.add(node.val);
        getPreorder(node.left, result);
        getPreorder(node.right, result);
    }
    
    private void getInorder(TreeNode node, List<Integer> result) {
        if (node == null) return;
        getInorder(node.left, result);
        result.add(node.val);
        getInorder(node.right, result);
    }
}