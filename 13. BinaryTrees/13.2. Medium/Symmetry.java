/**
 * Symmetric Tree (LeetCode 101)
 * 
 * PROBLEM: Check if binary tree is symmetric around its center (mirror of itself)
 * 
 * APPROACH: Recursive mirror check of left and right subtrees
 * TIME: O(n) - each node visited once
 * SPACE: O(h) - recursion stack (h = height)
 */
public class Symmetry {

    static class TreeNode {
        int val; 
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    /**
     * Check if binary tree is symmetric (mirror of itself)
     * @param root - root of binary tree
     * @return true if tree is symmetric, false otherwise
     * 
     * A tree is symmetric if:
     * 1. Left subtree is mirror of right subtree
     * 2. Recursively, left.left mirrors right.right and left.right mirrors right.left
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true; // Empty tree is symmetric
        return isMirror(root.left, root.right);
    }
    
    /**
     * Helper: Check if two subtrees are mirror images
     * @param left - left subtree root
     * @param right - right subtree root
     * @return true if trees are mirrors, false otherwise
     * 
     * Two trees are mirrors if:
     * 1. Both roots are null → true
     * 2. One null, other not → false
     * 3. Root values equal
     * 4. left.left mirrors right.right AND left.right mirrors right.left
     */
    private boolean isMirror(TreeNode left, TreeNode right) {
        // Case 1: Both null
        if (left == null && right == null) return true;
        
        // Case 2: One null, other not
        if (left == null || right == null) return false;
        
        // Case 3: Values must match
        if (left.val != right.val) return false;
        
        // Case 4: Recursive mirror check
        return isMirror(left.left, right.right) && isMirror(left.right, right.left);
    }
    
    /**
     * Alternative: Iterative BFS using queue
     * Avoids recursion, uses level-order traversal
     */
    public boolean isSymmetricIterative(TreeNode root) {
        if (root == null) return true;
        
        Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.offer(root.left);
        queue.offer(root.right);
        
        while (!queue.isEmpty()) {
            TreeNode left = queue.poll();
            TreeNode right = queue.poll();
            
            // Both null → continue
            if (left == null && right == null) continue;
            
            // One null, other not → not symmetric
            if (left == null || right == null) return false;
            
            // Values must match
            if (left.val != right.val) return false;
            
            // Add children in mirror order
            queue.offer(left.left);
            queue.offer(right.right);
            queue.offer(left.right);
            queue.offer(right.left);
        }
        
        return true;
    }
    
    /**
     * Alternative: Iterative DFS using stack
     */
    public boolean isSymmetricDFS(TreeNode root) {
        if (root == null) return true;
        
        Stack<TreeNode> stack = new java.util.Stack<>();
        stack.push(root.left);
        stack.push(root.right);
        
        while (!stack.isEmpty()) {
            TreeNode right = stack.pop();
            TreeNode left = stack.pop();
            
            if (left == null && right == null) continue;
            if (left == null || right == null) return false;
            if (left.val != right.val) return false;
            
            // Push in mirror order
            stack.push(left.left);
            stack.push(right.right);
            stack.push(left.right);
            stack.push(right.left);
        }
        
        return true;
    }
    
    /**
     * Variation: Check if two trees are mirrors of each other
     * Same logic but for different trees
     */
    public boolean areMirrors(TreeNode tree1, TreeNode tree2) {
        return isMirror(tree1, tree2); // Same helper works
    }

    /**
     * Visual Examples:
     * 
     * Example 1: Symmetric tree
     *        1
     *       / \
     *      2   2
     *     / \ / \
     *    3  4 4  3
     * 
     * Mirror check:
     * isMirror(2,2):
     *   values match (2=2)
     *   left: isMirror(3,3) → true
     *   right: isMirror(4,4) → true
     *   return true
     * 
     * Example 2: Not symmetric
     *        1
     *       / \
     *      2   2
     *       \   \
     *        3   3
     * 
     * Mirror check:
     * isMirror(2,2):
     *   values match (2=2)
     *   left: isMirror(null,3) → false
     *   return false
     * 
     * Example 3: Single node (symmetric)
     *    1
     * 
     * isMirror(null,null) → true
     */

    public static void main(String[] args) {
        Symmetry s = new Symmetry();
        
        // Example 1: Symmetric tree
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(2);
        root1.left.left = new TreeNode(3);
        root1.left.right = new TreeNode(4);
        root1.right.left = new TreeNode(4);
        root1.right.right = new TreeNode(3);
        
        System.out.println("Example 1 (Symmetric): " + s.isSymmetric(root1)); // true
        System.out.println("Iterative BFS: " + s.isSymmetricIterative(root1)); // true
        System.out.println("Iterative DFS: " + s.isSymmetricDFS(root1)); // true
        
        // Example 2: Not symmetric
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(2);
        root2.left.right = new TreeNode(3);
        root2.right.right = new TreeNode(3);
        
        System.out.println("\nExample 2 (Not symmetric): " + s.isSymmetric(root2)); // false
        
        // Example 3: Single node
        TreeNode root3 = new TreeNode(1);
        System.out.println("Example 3 (Single node): " + s.isSymmetric(root3)); // true
        
        // Example 4: Empty tree
        System.out.println("Example 4 (Empty): " + s.isSymmetric(null)); // true
        
        // Test cases
        System.out.println("\n=== Test Cases ===");
        
        // Test 1: Perfect symmetric tree
        TreeNode perfect = new TreeNode(1);
        perfect.left = new TreeNode(2);
        perfect.right = new TreeNode(2);
        perfect.left.left = new TreeNode(3);
        perfect.left.right = new TreeNode(4);
        perfect.right.left = new TreeNode(4);
        perfect.right.right = new TreeNode(3);
        System.out.println("Perfect symmetric: " + s.isSymmetric(perfect)); // true
        
        // Test 2: Values differ
        TreeNode diffValues = new TreeNode(1);
        diffValues.left = new TreeNode(2);
        diffValues.right = new TreeNode(3); // Different value
        System.out.println("Different values: " + s.isSymmetric(diffValues)); // false
        
        // Test 3: Structure differs
        TreeNode diffStruct = new TreeNode(1);
        diffStruct.left = new TreeNode(2);
        diffStruct.left.left = new TreeNode(3);
        diffStruct.right = new TreeNode(2);
        diffStruct.right.right = new TreeNode(3);
        System.out.println("Different structure: " + s.isSymmetric(diffStruct)); // false
        
        // Test 4: Large symmetric tree
        TreeNode large = new TreeNode(1);
        large.left = new TreeNode(2);
        large.right = new TreeNode(2);
        large.left.left = new TreeNode(3);
        large.left.right = new TreeNode(4);
        large.right.left = new TreeNode(4);
        large.right.right = new TreeNode(3);
        large.left.left.left = new TreeNode(5);
        large.left.left.right = new TreeNode(6);
        large.left.right.left = new TreeNode(7);
        large.left.right.right = new TreeNode(8);
        large.right.left.left = new TreeNode(8);
        large.right.left.right = new TreeNode(7);
        large.right.right.left = new TreeNode(6);
        large.right.right.right = new TreeNode(5);
        System.out.println("Large symmetric: " + s.isSymmetric(large)); // true
        
        // Test mirror of different trees
        TreeNode treeA = new TreeNode(1);
        treeA.left = new TreeNode(2);
        treeA.right = new TreeNode(3);
        
        TreeNode treeB = new TreeNode(1);
        treeB.left = new TreeNode(3);
        treeB.right = new TreeNode(2);
        
        System.out.println("\nMirror trees check: " + s.areMirrors(treeA, treeB)); // true
    }
}

/**
 * KEY CONCEPTS:
 * 
 * 1. Symmetric tree definition:
 *    - Mirror image of itself around center
 *    - Left subtree = mirror of right subtree
 * 
 * 2. Mirror check logic:
 *    - Compare left.left with right.right (outer pairs)
 *    - Compare left.right with right.left (inner pairs)
 * 
 * 3. Base cases:
 *    - Both null → symmetric
 *    - One null → not symmetric
 *    - Values differ → not symmetric
 * 
 * 4. Empty tree (null root) is symmetric
 */

/**
 * TIME & SPACE ANALYSIS:
 * 
 * Recursive:
 *   Time: O(n) - Each node visited once
 *   Space: O(h) where h = height of tree
 *     - Best case (balanced): O(log n)
 *     - Worst case (skewed but symmetric): O(n)
 * 
 * Iterative BFS:
 *   Time: O(n)
 *   Space: O(w) where w = max width of tree
 *     - Worst case: O(n) for complete tree
 * 
 * Iterative DFS:
 *   Time: O(n)
 *   Space: O(h) - Stack size
 */

/**
 * ALGORITHM WALKTHROUGH:
 * 
 * Tree:        1
 *            /   \
 *           2     2
 *          / \   / \
 *         3   4 4   3
 * 
 * isSymmetric(1):
 *   isMirror(2,2):
 *     left.val=2, right.val=2 → match
 *     outer: isMirror(3,3) → true
 *     inner: isMirror(4,4) → true
 *     return true
 * 
 * Tree:        1
 *            /   \
 *           2     2
 *            \     \
 *             3     3
 * 
 * isMirror(2,2):
 *   left.val=2, right.val=2 → match
 *   outer: isMirror(null,3) → false
 *   return false
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Not handling null root (empty tree is symmetric)
 * 2. Wrong mirror comparison:
 *    - Should compare left.left with right.right (not left.left with right.left)
 *    - Should compare left.right with right.left (not left.right with right.right)
 * 3. Forgetting to check values before structure
 * 4. Using OR instead of AND for recursive calls
 * 5. Not checking both children exist before accessing
 */

/**
 * VARIATIONS:
 * 
 * 1. Check if two trees are mirrors of each other
 * 2. Count number of symmetric subtrees
 * 3. Find longest symmetric path
 * 4. Check if tree is symmetric at all levels
 * 5. Mirror a binary tree
 */

/**
 * PRACTICE EXERCISES:
 * 
 * 1. Implement iterative solution
 * 2. Check if tree is symmetric using inorder traversal
 * 3. Find all symmetric pairs in tree
 * 4. Convert non-symmetric tree to symmetric
 * 5. Check if tree is symmetric after removing some nodes
 */

/**
 * RELATED PROBLEMS:
 * 
 * 1. LeetCode 101: Symmetric Tree
 * 2. LeetCode 100: Same Tree
 * 3. LeetCode 226: Invert Binary Tree (mirror)
 * 4. LeetCode 572: Subtree of Another Tree
 * 5. GeeksforGeeks: Mirror Tree
 */