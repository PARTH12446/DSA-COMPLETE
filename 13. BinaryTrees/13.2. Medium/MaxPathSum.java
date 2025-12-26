/**
 * Binary Tree Maximum Path Sum (LeetCode 124)
 * 
 * PROBLEM: Find the maximum path sum in binary tree.
 * A path is any sequence of nodes where each pair is connected by an edge.
 * Path must contain at least one node and can start/end at any node.
 * 
 * KEY INSIGHT: For each node, compute:
 * 1. Maximum path sum THROUGH this node (left + node + right)
 * 2. Maximum path sum RETURNED to parent (node + max(left, right))
 * 
 * APPROACH: Post-order DFS with global maximum tracking
 * TIME: O(n) - each node visited once
 * SPACE: O(h) - recursion stack (h = height)
 */
public class MaxPathSum {

    static class TreeNode {
        int val; 
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    private int maxSum; // Global maximum path sum
    
    /**
     * Main method to find maximum path sum
     * @param root - root of binary tree
     * @return maximum path sum
     */
    public int maxPathSum(TreeNode root) {
        maxSum = Integer.MIN_VALUE; // Initialize with minimum value
        dfs(root);                 // Start DFS
        return maxSum;
    }
    
    /**
     * DFS helper that returns maximum single path sum ending at node
     * (Path that can be extended to parent)
     * 
     * @param node - current node
     * @return maximum path sum that ends at current node and goes upward
     * 
     * For each node, we compute:
     * 1. leftGain = max(0, dfs(node.left))   // Ignore negative contributions
     * 2. rightGain = max(0, dfs(node.right)) // Ignore negative contributions
     * 3. currentPath = leftGain + node.val + rightGain
     * 4. Update global maxSum with currentPath
     * 5. Return node.val + max(leftGain, rightGain) to parent
     */
    private int dfs(TreeNode node) {
        // Base case: null node contributes 0
        if (node == null) return 0;
        
        // Get maximum gain from left and right subtrees
        // If gain is negative, we ignore it (take 0 instead)
        int leftGain = Math.max(0, dfs(node.left));
        int rightGain = Math.max(0, dfs(node.right));
        
        // Maximum path sum THROUGH current node
        // This path can't be extended to parent (it's arch-shaped)
        int currentPathSum = leftGain + node.val + rightGain;
        
        // Update global maximum
        maxSum = Math.max(maxSum, currentPathSum);
        
        // Return maximum single path sum ending at current node
        // This path can be extended to parent (it's V-shaped)
        return node.val + Math.max(leftGain, rightGain);
    }
    
    /**
     * Alternative: Return both values in an array
     * Avoids global variable
     */
    public int maxPathSumAlternative(TreeNode root) {
        int[] result = dfsAlt(root);
        return result[1]; // Global maximum is at index 1
    }
    
    private int[] dfsAlt(TreeNode node) {
        // Returns [maxSinglePath, maxPathSum]
        if (node == null) return new int[]{0, Integer.MIN_VALUE};
        
        int[] left = dfsAlt(node.left);
        int[] right = dfsAlt(node.right);
        
        // Max single path ending at current node (can be extended upward)
        int leftGain = Math.max(0, left[0]);
        int rightGain = Math.max(0, right[0]);
        int maxSinglePath = node.val + Math.max(leftGain, rightGain);
        
        // Max path sum through current node (arch-shaped)
        int maxPathThroughNode = node.val + leftGain + rightGain;
        
        // Global maximum path sum
        int maxPathSum = Math.max(Math.max(left[1], right[1]), maxPathThroughNode);
        
        return new int[]{maxSinglePath, maxPathSum};
    }

    /**
     * Visual Examples:
     * 
     * Example 1 (from problem):
     *       -10
     *       /  \
     *      9   20
     *         /  \
     *        15   7
     * 
     * Calculation:
     * Node 9: left=0, right=0 → current=9, return 9
     * Node 15: left=0, right=0 → current=15, return 15
     * Node 7: left=0, right=0 → current=7, return 7
     * Node 20: left=15, right=7 → current=15+20+7=42, return 20+max(15,7)=35
     * Node -10: left=9, right=35 → current=9+(-10)+35=34, return -10+35=25
     * 
     * Max path sum = 42 ✓ (path 15 → 20 → 7)
     * 
     * Example 2 (all negative):
     *       -3
     *       / \
     *     -2  -1
     * 
     * Node -2: current=-2, return -2
     * Node -1: current=-1, return -1
     * Node -3: left=-2, right=-1 → current=-2+(-3)+(-1)=-6, return -3+max(-2,-1)=-4
     * Max path sum = -1 ✓ (single node -1)
     * 
     * Example 3 (path doesn't go through root):
     *       10
     *      /  \
     *     2   10
     *    / \    \
     *   20  1   -25
     *          /  \
     *         3    4
     * 
     * Max path: 20 → 2 → 10 → 10 = 42
     * Note: Path can't include both 20 and 10 (right child) since they're on different sides
     * Actually correct path: 20 → 2 → 10 = 32
     * Or: 10 → 10 = 20
     * Let's compute...
     * Actually the 20-2-10 path is correct, sum = 32
     */
    
    public static void main(String[] args) {
        MaxPathSum m = new MaxPathSum();
        
        // Example from problem
        TreeNode root1 = new TreeNode(-10);
        root1.left = new TreeNode(9);
        root1.right = new TreeNode(20);
        root1.right.left = new TreeNode(15);
        root1.right.right = new TreeNode(7);
        
        System.out.println("Example 1: " + m.maxPathSum(root1)); // 42
        System.out.println("Alternative: " + m.maxPathSumAlternative(root1)); // 42
        
        // Test cases
        System.out.println("\n=== Test Cases ===");
        
        // Test 1: Single node
        TreeNode single = new TreeNode(5);
        System.out.println("Single positive: " + m.maxPathSum(single)); // 5
        
        TreeNode singleNeg = new TreeNode(-5);
        System.out.println("Single negative: " + m.maxPathSum(singleNeg)); // -5
        
        // Test 2: All negative
        TreeNode allNeg = new TreeNode(-3);
        allNeg.left = new TreeNode(-2);
        allNeg.right = new TreeNode(-1);
        System.out.println("All negative: " + m.maxPathSum(allNeg)); // -1
        
        // Test 3: Simple tree
        TreeNode simple = new TreeNode(1);
        simple.left = new TreeNode(2);
        simple.right = new TreeNode(3);
        System.out.println("Simple 1-2-3: " + m.maxPathSum(simple)); // 6
        
        // Test 4: Path doesn't go through root
        TreeNode complex = new TreeNode(10);
        complex.left = new TreeNode(2);
        complex.right = new TreeNode(10);
        complex.left.left = new TreeNode(20);
        complex.left.right = new TreeNode(1);
        complex.right.right = new TreeNode(-25);
        complex.right.right.left = new TreeNode(3);
        complex.right.right.right = new TreeNode(4);
        System.out.println("Complex tree: " + m.maxPathSum(complex)); // 42
        
        // Test 5: Left skewed
        TreeNode leftSkewed = new TreeNode(1);
        leftSkewed.left = new TreeNode(2);
        leftSkewed.left.left = new TreeNode(3);
        System.out.println("Left skewed: " + m.maxPathSum(leftSkewed)); // 6
        
        // Test 6: Right skewed
        TreeNode rightSkewed = new TreeNode(1);
        rightSkewed.right = new TreeNode(2);
        rightSkewed.right.right = new TreeNode(3);
        System.out.println("Right skewed: " + m.maxPathSum(rightSkewed)); // 6
        
        // Test 7: Tree with zeros
        TreeNode withZeros = new TreeNode(1);
        withZeros.left = new TreeNode(0);
        withZeros.right = new TreeNode(0);
        withZeros.left.left = new TreeNode(5);
        withZeros.left.right = new TreeNode(-2);
        System.out.println("With zeros: " + m.maxPathSum(withZeros)); // 6 (5-0-1)
    }
}

/**
 * KEY CONCEPTS:
 * 
 * 1. Two types of paths:
 *    - Arch-shaped: Path goes through node and uses both children (left → node → right)
 *      * Can't be extended to parent
 *      * Used to update global maximum
 *    - V-shaped: Path ends at node and uses max of left/right child (node → max(left, right))
 *      * Can be extended to parent
 *      * Returned to parent
 * 
 * 2. Negative contributions:
 *    - If subtree contributes negative sum, we ignore it (take 0)
 *    - This is why we use Math.max(0, dfs(...))
 * 
 * 3. At least one node:
 *    - Path must contain at least one node
 *    - This handles all-negative trees correctly
 */

/**
 * TIME & SPACE ANALYSIS:
 * 
 * Time Complexity: O(n)
 *   - Each node visited exactly once
 *   - Constant work per node (max, addition)
 * 
 * Space Complexity: O(h) where h = height of tree
 *   - Best case (balanced): O(log n)
 *   - Worst case (skewed): O(n)
 *   - Recursion stack depth
 */

/**
 * ALGORITHM WALKTHROUGH:
 * 
 * Tree:       -10
 *            /    \
 *           9     20
 *                /  \
 *               15   7
 * 
 * dfs(9): left=0, right=0 → current=9, maxSum=9, return 9
 * dfs(15): left=0, right=0 → current=15, maxSum=15, return 15
 * dfs(7): left=0, right=0 → current=7, maxSum=15, return 7
 * dfs(20): left=15, right=7 → current=42, maxSum=42, return 20+15=35
 * dfs(-10): left=9, right=35 → current=34, maxSum=42, return -10+35=25
 * 
 * Final maxSum = 42
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Not handling negative values correctly:
 *    - Must use Math.max(0, dfs(...)) to ignore negative contributions
 * 
 * 2. Confusing return value:
 *    - Should return node.val + max(left, right), not the arch path
 * 
 * 3. Forgetting to update global maximum:
 *    - Need to check path through current node (left + node + right)
 * 
 * 4. Not initializing maxSum to Integer.MIN_VALUE:
 *    - For all-negative trees, max could be negative
 * 
 * 5. Assuming path must go through root:
 *    - Maximum path may be entirely in a subtree
 */

/**
 * VARIATIONS:
 * 
 * 1. Maximum path sum with at most k nodes
 * 2. Maximum path sum where nodes have specific values
 * 3. Find the actual path (nodes) not just sum
 * 4. Maximum path sum in N-ary tree
 * 5. Maximum path sum with constraints (e.g., can't skip more than 1 node)
 */

/**
 * PRACTICE EXERCISES:
 * 
 * 1. Modify to return the actual path nodes
 * 2. Find maximum path sum where all nodes have same sign
 * 3. Count number of paths with sum = k
 * 4. Maximum path sum where path length is limited
 * 5. Check if path exists with sum = target
 */