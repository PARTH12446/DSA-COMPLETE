/**
 * Diameter of Binary Tree (LeetCode 543)
 * 
 * PROBLEM: Find the length of the longest path between any two nodes in a tree.
 * The path may or may not pass through the root.
 * 
 * DEFINITION: Diameter = maximum number of edges between any two nodes
 * 
 * APPROACH: Modified height calculation with diameter tracking
 * TIME: O(n) - each node visited once
 * SPACE: O(h) - recursion stack (h = height)
 */
public class Diameter {

    static class TreeNode {
        int val; 
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    private int maxDiameter; // Global variable to track maximum diameter
    
    /**
     * Main method to calculate diameter
     * @param root - root of binary tree
     * @return diameter (longest path length in edges)
     */
    public int diameterOfBinaryTree(TreeNode root) {
        maxDiameter = 0; // Reset for each call
        height(root);    // This updates maxDiameter
        return maxDiameter;
    }
    
    /**
     * Calculate height of subtree and update max diameter
     * @param node - current node
     * @return height of subtree rooted at node
     * 
     * KEY INSIGHT: 
     * - Diameter through a node = height(left) + height(right)
     * - Global maxDiameter tracks maximum across all nodes
     * - Height of node = max(height(left), height(right)) + 1
     */
    private int height(TreeNode node) {
        // Base case: null node has height 0
        if (node == null) return 0;
        
        // Recursively get heights of left and right subtrees
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);
        
        // Update maximum diameter: path through current node
        // Diameter at this node = leftHeight + rightHeight (edges)
        maxDiameter = Math.max(maxDiameter, leftHeight + rightHeight);
        
        // Return height of current node
        // Height = max(leftHeight, rightHeight) + 1 (for current node)
        return Math.max(leftHeight, rightHeight) + 1;
    }
    
    /**
     * Alternative: Return both height and diameter in an array
     * Avoids global variable, uses return object
     */
    public int diameterOfBinaryTreeAlternative(TreeNode root) {
        return dfs(root)[0]; // First element is diameter
    }
    
    private int[] dfs(TreeNode node) {
        // Return [diameter, height]
        if (node == null) return new int[]{0, 0};
        
        int[] left = dfs(node.left);
        int[] right = dfs(node.right);
        
        int diameter = Math.max(Math.max(left[0], right[0]), left[1] + right[1]);
        int height = Math.max(left[1], right[1]) + 1;
        
        return new int[]{diameter, height};
    }
    
    /**
     * Brute force approach (for comparison)
     * O(n²) time - calculates height for each node
     */
    public int diameterBruteForce(TreeNode root) {
        if (root == null) return 0;
        
        // Diameter through root
        int throughRoot = heightCalc(root.left) + heightCalc(root.right);
        
        // Diameter in left subtree
        int leftDiameter = diameterBruteForce(root.left);
        
        // Diameter in right subtree
        int rightDiameter = diameterBruteForce(root.right);
        
        return Math.max(throughRoot, Math.max(leftDiameter, rightDiameter));
    }
    
    private int heightCalc(TreeNode node) {
        if (node == null) return 0;
        return Math.max(heightCalc(node.left), heightCalc(node.right)) + 1;
    }

    /**
     * Visual Examples:
     * 
     * Example 1:
     *       1
     *      / \
     *     2   3
     *    / \
     *   4   5
     * 
     * Heights: 4(0), 5(0), 2(1), 3(0), 1(2)
     * Diameters: 
     *   - Through node 2: leftHeight(4)=0 + rightHeight(5)=0 = 0
     *   - Through node 1: leftHeight(2)=1 + rightHeight(3)=0 = 1
     *   Actually longest path: 4-2-5 (2 edges) or 4-2-1-3 (3 edges)
     *   Let's calculate properly:
     *   Node 4: height 0, diameter 0
     *   Node 5: height 0, diameter 0  
     *   Node 2: height 1, diameter through 2 = height(4)+height(5) = 0+0 = 0
     *   Node 3: height 0, diameter 0
     *   Node 1: height 2, diameter through 1 = height(2)+height(3) = 1+0 = 1
     *   Wait, something's wrong...
     *   
     *   Actually diameter through node 2 = height(4) + height(5) = 1+1 = 2?
     *   No, height of leaf is 0...
     *   Let me think: height of null = 0, height of leaf = 1
     *   So leaf nodes have height 1, not 0
     *   
     *   Correct calculation:
     *   Node 4: left=0, right=0 → height=1, diameter=0
     *   Node 5: left=0, right=0 → height=1, diameter=0
     *   Node 2: left=1, right=1 → height=2, diameter=2 (4-2-5)
     *   Node 3: left=0, right=0 → height=1, diameter=0
     *   Node 1: left=2, right=1 → height=3, diameter=3 (4-2-1-3)
     *   
     *   Max diameter = 3 ✓
     * 
     * Example 2 (Longest path doesn't pass through root):
     *       1
     *      / \
     *     2   3
     *        / \
     *       4   5
     *      /     \
     *     6       7
     *    /         \
     *   8           9
     * 
     * Longest path: 8-6-4-3-5-7-9 (6 edges)
     * Diameter = 6
     */

    public static void main(String[] args) {
        Diameter d = new Diameter();
        
        // Example from problem
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.left = new TreeNode(4);
        root1.left.right = new TreeNode(5);
        
        System.out.println("Example 1: " + d.diameterOfBinaryTree(root1)); // 3
        
        // Test with alternative method
        System.out.println("Alternative method: " + d.diameterOfBinaryTreeAlternative(root1)); // 3
        
        // Test brute force (should be same)
        System.out.println("Brute force: " + d.diameterBruteForce(root1)); // 3
        
        // Test cases
        System.out.println("\n=== Test Cases ===");
        
        // Test 1: Empty tree
        System.out.println("Empty tree: " + d.diameterOfBinaryTree(null)); // 0
        
        // Test 2: Single node
        TreeNode single = new TreeNode(1);
        System.out.println("Single node: " + d.diameterOfBinaryTree(single)); // 0
        
        // Test 3: Left-skewed tree (chain)
        TreeNode leftSkewed = new TreeNode(1);
        leftSkewed.left = new TreeNode(2);
        leftSkewed.left.left = new TreeNode(3);
        System.out.println("Left-skewed (height 2): " + d.diameterOfBinaryTree(leftSkewed)); // 2
        
        // Test 4: Right-skewed tree
        TreeNode rightSkewed = new TreeNode(1);
        rightSkewed.right = new TreeNode(2);
        rightSkewed.right.right = new TreeNode(3);
        System.out.println("Right-skewed: " + d.diameterOfBinaryTree(rightSkewed)); // 2
        
        // Test 5: Perfect binary tree
        TreeNode perfect = new TreeNode(1);
        perfect.left = new TreeNode(2);
        perfect.right = new TreeNode(3);
        perfect.left.left = new TreeNode(4);
        perfect.left.right = new TreeNode(5);
        perfect.right.left = new TreeNode(6);
        perfect.right.right = new TreeNode(7);
        System.out.println("Perfect binary: " + d.diameterOfBinaryTree(perfect)); // 4
        
        // Test 6: Complex tree (longest path not through root)
        TreeNode complex = new TreeNode(1);
        complex.left = new TreeNode(2);
        complex.right = new TreeNode(3);
        complex.right.left = new TreeNode(4);
        complex.right.right = new TreeNode(5);
        complex.right.left.left = new TreeNode(6);
        complex.right.right.right = new TreeNode(7);
        complex.right.left.left.left = new TreeNode(8);
        complex.right.right.right.right = new TreeNode(9);
        System.out.println("Complex (longest not through root): " + 
                          d.diameterOfBinaryTree(complex)); // 6
    }
}

/**
 * KEY CONCEPTS:
 * 
 * 1. Diameter definition:
 *    - Length of longest path between any two nodes
 *    - Measured in number of EDGES (not nodes)
 *    - Path may or may not pass through root
 * 
 * 2. Height vs Diameter:
 *    - Height: longest path from node to leaf (downward)
 *    - Diameter: longest path between any two nodes (can be sideways)
 * 
 * 3. Critical observation:
 *    - Diameter through a node = height(left) + height(right)
 *    - Global diameter = max(diameter through any node)
 */

/**
 * TIME & SPACE ANALYSIS:
 * 
 * Time Complexity: O(n)
 *   - Each node visited exactly once
 *   - Constant work per node (max, addition)
 *   - Brute force: O(n²) (recalculates heights)
 * 
 * Space Complexity: O(h) where h = height of tree
 *   - Best case (balanced): O(log n)
 *   - Worst case (skewed): O(n)
 *   - Recursion stack depth
 */

/**
 * ALGORITHM WALKTHROUGH:
 * 
 * Tree:       1
 *           /   \
 *          2     3
 *         / \
 *        4   5
 * 
 * Execution:
 * height(4): left=0, right=0 → diameter=0, return 1
 * height(5): left=0, right=0 → diameter=0, return 1
 * height(2): left=1, right=1 → diameter=2, maxDiameter=2, return 2
 * height(3): left=0, right=0 → diameter=0, return 1
 * height(1): left=2, right=1 → diameter=3, maxDiameter=3, return 3
 * 
 * Final maxDiameter = 3
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Counting nodes instead of edges:
 *    - Diameter is edges, not nodes
 *    - Path with k nodes has k-1 edges
 * 
 * 2. Only checking path through root:
 *    - Longest path may not pass through root
 *    - Must check all nodes
 * 
 * 3. Using O(n²) brute force:
 *    - Recalculating heights for each node
 * 
 * 4. Not resetting global variable:
 *    - If multiple calls, reset maxDiameter
 * 
 * 5. Wrong height calculation:
 *    - Height of null = 0
 *    - Height of leaf = 1
 */

/**
 * VARIATIONS:
 * 
 * 1. Return nodes on diameter path (not just length)
 * 2. Diameter in N-ary tree
 * 3. Diameter with weighted edges
 * 4. Find if diameter is odd/even
 * 5. Count number of diameter paths
 */

/**
 * PRACTICE EXERCISES:
 * 
 * 1. Modify to return both diameter and height as array
 * 2. Find if tree has diameter greater than k
 * 3. Find all nodes that lie on diameter path
 * 4. Calculate diameter without global variable
 * 5. Diameter for tree stored as array
 */