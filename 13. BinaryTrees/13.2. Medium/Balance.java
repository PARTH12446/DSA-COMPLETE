/**
 * Balanced Binary Tree (LeetCode 110)
 * 
 * PROBLEM: Check if binary tree is height-balanced.
 * DEFINITION: A binary tree is height-balanced if for every node:
 *   - The heights of left and right subtrees differ by at most 1
 *   - Both left and right subtrees are also balanced
 * 
 * APPROACH: Bottom-up DFS with height checking
 * TIME: O(n) - each node visited once
 * SPACE: O(h) - recursion stack (h = height)
 */
public class Balance {

    static class TreeNode {
        int val; 
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    /**
     * DFS helper that returns height or -1 if unbalanced
     * @param node - current node
     * @return height of subtree if balanced, -1 if unbalanced
     * 
     * STRATEGY:
     * 1. Base case: null node has height 0
     * 2. Get height of left subtree
     * 3. If left unbalanced (-1), propagate -1 up
     * 4. Get height of right subtree
     * 5. If right unbalanced (-1), propagate -1 up
     * 6. Check if current node balanced: |left - right| ≤ 1
     * 7. If balanced, return height = max(left, right) + 1
     * 8. If unbalanced, return -1
     */
    private int dfs(TreeNode node) {
        // Base case: null node has height 0
        if (node == null) return 0;
        
        // Get height of left subtree
        int leftHeight = dfs(node.left);
        // If left subtree is unbalanced, propagate -1 up
        if (leftHeight == -1) return -1;
        
        // Get height of right subtree
        int rightHeight = dfs(node.right);
        // If right subtree is unbalanced, propagate -1 up
        if (rightHeight == -1) return -1;
        
        // Check if current node is balanced
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1; // Current node is unbalanced
        }
        
        // Current node is balanced, return its height
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Main method to check if tree is balanced
     * @param root - root of binary tree
     * @return true if balanced, false otherwise
     */
    public boolean isBalanced(TreeNode root) {
        // Tree is balanced if dfs doesn't return -1
        return dfs(root) != -1;
    }
    
    /**
     * Alternative approach: Separate height and balance check
     * Easier to understand but less efficient
     */
    public boolean isBalancedAlternative(TreeNode root) {
        if (root == null) return true;
        
        // Check if left and right subtrees are balanced
        boolean leftBalanced = isBalancedAlternative(root.left);
        boolean rightBalanced = isBalancedAlternative(root.right);
        
        // Check height difference at current node
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        
        return leftBalanced && rightBalanced && 
               Math.abs(leftHeight - rightHeight) <= 1;
    }
    
    private int height(TreeNode node) {
        if (node == null) return 0;
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    /**
     * Visual Example:
     * 
     * Balanced tree:
     *        1
     *       / \
     *      2   3
     *     / 
     *    4   
     * 
     * Unbalanced tree:
     *        1
     *       / \
     *      2   3
     *     / 
     *    4   
     *   /
     *  5
     * 
     * For balanced tree:
     * dfs(1): left=2, right=1 → |2-1|=1 → return 3
     * 
     * For unbalanced tree:
     * dfs(1): left=3, right=1 → |3-1|=2>1 → return -1
     */

    public static void main(String[] args) {
        Balance b = new Balance();
        
        // Test 1: Balanced tree
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.left = new TreeNode(4);
        System.out.println("Test 1 (Balanced): " + b.isBalanced(root1)); // true
        
        // Test 2: Unbalanced tree
        root1.left.left.left = new TreeNode(5);
        System.out.println("Test 2 (Unbalanced): " + b.isBalanced(root1)); // false
        
        // Test 3: Empty tree
        System.out.println("Test 3 (Empty): " + b.isBalanced(null)); // true
        
        // Test 4: Single node
        TreeNode root2 = new TreeNode(1);
        System.out.println("Test 4 (Single): " + b.isBalanced(root2)); // true
        
        // Test 5: Left-skewed but balanced (height 2)
        TreeNode root3 = new TreeNode(1);
        root3.left = new TreeNode(2);
        root3.left.left = new TreeNode(3);
        System.out.println("Test 5 (Left-skewed height 2): " + b.isBalanced(root3)); // false
        
        // Test 6: Right-skewed but balanced (height 2)
        TreeNode root4 = new TreeNode(1);
        root4.right = new TreeNode(2);
        root4.right.right = new TreeNode(3);
        System.out.println("Test 6 (Right-skewed height 2): " + b.isBalanced(root4)); // false
        
        // Test 7: Perfectly balanced
        TreeNode root5 = new TreeNode(1);
        root5.left = new TreeNode(2);
        root5.right = new TreeNode(3);
        root5.left.left = new TreeNode(4);
        root5.left.right = new TreeNode(5);
        root5.right.left = new TreeNode(6);
        root5.right.right = new TreeNode(7);
        System.out.println("Test 7 (Perfect): " + b.isBalanced(root5)); // true
    }
}

/**
 * TIME & SPACE ANALYSIS:
 * 
 * Time Complexity: O(n)
 *   - Each node visited exactly once
 *   - Constant work per node (height calculation, balance check)
 *   - More efficient than naive O(n²) approach
 * 
 * Space Complexity: O(h) where h = height of tree
 *   - Best case (balanced): h = log n → O(log n)
 *   - Worst case (skewed): h = n → O(n)
 *   - Space used by recursion stack
 */

/**
 * WHY BOTTOM-UP APPROACH IS EFFICIENT:
 * 
 * Naive approach (top-down):
 *   - For each node: calculate height of left and right (O(n) each)
 *   - Total: O(n²) time
 * 
 * Optimized approach (bottom-up):
 *   - Calculate height and check balance simultaneously
 *   - Once subtree found unbalanced, propagate immediately
 *   - Total: O(n) time
 */

/**
 * ALGORITHM WALKTHROUGH:
 * 
 * Example tree (unbalanced):
 *        1
 *       / \
 *      2   3
 *     / 
 *    4   
 *   /
 *  5
 * 
 * Execution:
 * dfs(5): left=0, right=0 → |0-0|=0 → return 1
 * dfs(4): left=1, right=0 → |1-0|=1 → return 2
 * dfs(2): left=2, right=0 → |2-0|=2>1 → return -1
 * dfs(3): left=0, right=0 → |0-0|=0 → return 1
 * dfs(1): left=-1 → return -1 (propagate)
 * 
 * Result: false (unbalanced)
 */

/**
 * KEY INSIGHTS:
 * 
 * 1. Balanced ≠ Complete or Perfect:
 *    - Balanced only cares about height difference ≤ 1
 *    - Complete tree has all levels filled except last
 *    - Perfect tree has all levels completely filled
 * 
 * 2. -1 as sentinel value:
 *    - Represents "unbalanced" state
 *    - Propagates up immediately when found
 * 
 * 3. Height calculation:
 *    - Height of node = max(left height, right height) + 1
 *    - Height of null = 0
 * 
 * 4. Early termination:
 *    - Once unbalanced found, stop checking
 *    - Makes algorithm efficient
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Not checking subtree balance:
 *    - Must check both height difference AND subtree balance
 *    - Example: left subtree could be unbalanced even if heights equal
 * 
 * 2. Wrong height calculation:
 *    - Height should count edges, not nodes
 *    - Null height = 0, leaf height = 1
 * 
 * 3. Not using bottom-up approach:
 *    - Top-down is O(n²), bottom-up is O(n)
 * 
 * 4. Forgetting to propagate -1:
 *    - If subtree unbalanced, parent also unbalanced
 */

/**
 * VARIATIONS AND RELATED PROBLEMS:
 * 
 * 1. Check if tree is Complete (LeetCode 958)
 * 2. Check if tree is Perfect
 * 3. Check if tree is Symmetric (LeetCode 101)
 * 4. Maximum depth/height (LeetCode 104)
 * 5. Minimum depth (LeetCode 111)
 */

/**
 * PRACTICE EXERCISES:
 * 
 * 1. Modify to return height if balanced, -1 otherwise
 * 2. Count number of unbalanced nodes
 * 3. Find maximum height difference in tree
 * 4. Check if tree is balanced within tolerance k (height diff ≤ k)
 */