/** 
 * Lowest Common Ancestor in Binary Tree (LeetCode 236)
 * 
 * PROBLEM: Find the lowest common ancestor (LCA) of two nodes in binary tree.
 * LCA: Deepest node that is ancestor of both nodes.
 * 
 * APPROACH: Recursive DFS with three-state return
 * TIME: O(n) - each node visited once
 * SPACE: O(h) - recursion stack (h = height)
 */
public class LCA {
    static class TreeNode {
        int val; 
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    /**
     * Find lowest common ancestor of two nodes
     * @param root - root of binary tree
     * @param p - first node
     * @param q - second node
     * @return LCA node, or null if one/both nodes not found
     * 
     * RECURSIVE STRATEGY:
     * 1. Base case: root is null, p, or q → return root
     * 2. Recursively search left and right subtrees
     * 3. Four possible outcomes:
     *    a. Both left and right non-null → current root is LCA
     *    b. Left non-null, right null → LCA in left subtree
     *    c. Left null, right non-null → LCA in right subtree
     *    d. Both null → nodes not found in this subtree
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // Base cases
        if (root == null || root == p || root == q) {
            return root;
        }
        
        // Recursively search left and right subtrees
        TreeNode leftLCA = lowestCommonAncestor(root.left, p, q);
        TreeNode rightLCA = lowestCommonAncestor(root.right, p, q);
        
        // Result logic
        if (leftLCA == null) {
            // Both nodes in right subtree or right has LCA
            return rightLCA;
        } else if (rightLCA == null) {
            // Both nodes in left subtree or left has LCA
            return leftLCA;
        } else {
            // One node in left, one in right → current root is LCA
            return root;
        }
    }
    
    /**
     * Alternative: With explicit null handling
     */
    public TreeNode lowestCommonAncestorAlt(TreeNode root, TreeNode p, TreeNode q) {
        // Base cases
        if (root == null) return null;
        if (root == p || root == q) return root;
        
        // Search subtrees
        TreeNode left = lowestCommonAncestorAlt(root.left, p, q);
        TreeNode right = lowestCommonAncestorAlt(root.right, p, q);
        
        // Determine LCA
        if (left != null && right != null) {
            // p and q in different subtrees
            return root;
        } else if (left != null) {
            // Both in left subtree
            return left;
        } else {
            // Both in right subtree or not found
            return right;
        }
    }
    
    /**
     * Iterative approach using parent mapping and path tracing
     * Useful for very deep trees (avoid recursion stack overflow)
     */
    public TreeNode lowestCommonAncestorIterative(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        
        // Map each node to its parent
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        Queue<TreeNode> queue = new LinkedList<>();
        
        parent.put(root, null);
        queue.offer(root);
        
        // Build parent map until both p and q found
        while (!parent.containsKey(p) || !parent.containsKey(q)) {
            TreeNode node = queue.poll();
            
            if (node.left != null) {
                parent.put(node.left, node);
                queue.offer(node.left);
            }
            if (node.right != null) {
                parent.put(node.right, node);
                queue.offer(node.right);
            }
        }
        
        // Collect ancestors of p
        Set<TreeNode> ancestors = new HashSet<>();
        TreeNode current = p;
        while (current != null) {
            ancestors.add(current);
            current = parent.get(current);
        }
        
        // Find first common ancestor with q
        current = q;
        while (!ancestors.contains(current)) {
            current = parent.get(current);
        }
        
        return current;
    }
    
    /**
     * Check if both nodes exist in tree before finding LCA
     */
    public TreeNode lowestCommonAncestorWithValidation(TreeNode root, TreeNode p, TreeNode q) {
        // First check if both nodes exist in tree
        if (!nodeExists(root, p) || !nodeExists(root, q)) {
            return null;
        }
        return lowestCommonAncestor(root, p, q);
    }
    
    private boolean nodeExists(TreeNode root, TreeNode node) {
        if (root == null) return false;
        if (root == node) return true;
        return nodeExists(root.left, node) || nodeExists(root.right, node);
    }
    
    /**
     * Find LCA for Binary Search Tree (simpler)
     * Can use BST property: left < root < right
     */
    public TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        
        // Both nodes smaller → go left
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestorBST(root.left, p, q);
        }
        
        // Both nodes larger → go right
        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestorBST(root.right, p, q);
        }
        
        // Nodes on different sides → current root is LCA
        return root;
    }

    /**
     * Visual Examples:
     * 
     * Example tree:
     *           3
     *          / \
     *         5   1
     *        / \ / \
     *       6  2 0  8
     *          / \
     *         7   4
     * 
     * Example 1: LCA(5, 1) = 3
     *   - 5 in left subtree, 1 in right subtree
     *   - Root 3 is LCA
     * 
     * Example 2: LCA(5, 4) = 5
     *   - Both 5 and 4 in left subtree
     *   - 5 is ancestor of 4
     * 
     * Example 3: LCA(6, 2) = 5
     *   - Both in left subtree, 5 is their ancestor
     * 
     * Recursive walkthrough for LCA(6, 4):
     * lowestCommonAncestor(3,6,4):
     *   left = lowestCommonAncestor(5,6,4)
     *     left = lowestCommonAncestor(6,6,4) → returns 6
     *     right = lowestCommonAncestor(2,6,4)
     *       left = lowestCommonAncestor(7,6,4) → null
     *       right = lowestCommonAncestor(4,6,4) → returns 4
     *       left=null, right=4 → return 4
     *     left=6, right=4 → return 5 (current root)
     *   right = lowestCommonAncestor(1,6,4) → null
     *   left=5, right=null → return 5
     */

    public static void main(String[] args) {
        LCA lca = new LCA();
        
        // Build example tree
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(5);
        root.right = new TreeNode(1);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);
        root.left.right.left = new TreeNode(7);
        root.left.right.right = new TreeNode(4);
        
        // Test cases
        System.out.println("=== LCA Tests ===");
        
        // Test 1: LCA(5, 1) = 3
        TreeNode p1 = root.left;  // 5
        TreeNode q1 = root.right; // 1
        TreeNode result1 = lca.lowestCommonAncestor(root, p1, q1);
        System.out.println("LCA(5, 1) = " + (result1 != null ? result1.val : "null")); // 3
        
        // Test 2: LCA(5, 4) = 5
        TreeNode p2 = root.left;           // 5
        TreeNode q2 = root.left.right.right; // 4
        TreeNode result2 = lca.lowestCommonAncestor(root, p2, q2);
        System.out.println("LCA(5, 4) = " + (result2 != null ? result2.val : "null")); // 5
        
        // Test 3: LCA(6, 2) = 5
        TreeNode p3 = root.left.left;      // 6
        TreeNode q3 = root.left.right;     // 2
        TreeNode result3 = lca.lowestCommonAncestor(root, p3, q3);
        System.out.println("LCA(6, 2) = " + (result3 != null ? result3.val : "null")); // 5
        
        // Test 4: LCA(6, 8) = 3
        TreeNode p4 = root.left.left;      // 6
        TreeNode q4 = root.right.right;    // 8
        TreeNode result4 = lca.lowestCommonAncestor(root, p4, q4);
        System.out.println("LCA(6, 8) = " + (result4 != null ? result4.val : "null")); // 3
        
        // Test 5: LCA(7, 4) = 2
        TreeNode p5 = root.left.right.left;   // 7
        TreeNode q5 = root.left.right.right;  // 4
        TreeNode result5 = lca.lowestCommonAncestor(root, p5, q5);
        System.out.println("LCA(7, 4) = " + (result5 != null ? result5.val : "null")); // 2
        
        // Test 6: Node with itself
        System.out.println("LCA(5, 5) = " + 
                          lca.lowestCommonAncestor(root, p2, p2).val); // 5
        
        // Test 7: Non-existent node (should handle gracefully)
        TreeNode fake = new TreeNode(100);
        TreeNode result7 = lca.lowestCommonAncestorWithValidation(root, p1, fake);
        System.out.println("LCA(5, fake) = " + (result7 != null ? result7.val : "null")); // null
        
        // Test iterative method
        System.out.println("\nIterative LCA(6, 4) = " + 
                          lca.lowestCommonAncestorIterative(root, p3, q2).val); // 5
    }
}

/**
 * KEY CONCEPTS:
 * 
 * 1. Lowest Common Ancestor (LCA):
 *    - Deepest node that is ancestor of both nodes
 *    - Can be one of the nodes itself (if one is ancestor of other)
 * 
 * 2. Recursive logic states:
 *    - null: node not found in subtree
 *    - p or q: found one of the target nodes
 *    - other node: found LCA candidate
 * 
 * 3. Four possible recursive outcomes:
 *    - Left null, Right null → return null
 *    - Left non-null, Right null → return left
 *    - Left null, Right non-null → return right
 *    - Left non-null, Right non-null → return root (LCA)
 */

/**
 * TIME & SPACE ANALYSIS:
 * 
 * Recursive:
 *   Time: O(n) - Each node visited once
 *   Space: O(h) - Recursion stack, where h = height
 *     - Best case (balanced): O(log n)
 *     - Worst case (skewed): O(n)
 * 
 * Iterative:
 *   Time: O(n) - Two passes (parent mapping + ancestor tracing)
 *   Space: O(n) - Parent map + ancestor set
 */

/**
 * ALGORITHM WALKTHROUGH:
 * 
 * Tree:       3
 *           /   \
 *          5     1
 *         / \   / \
 *        6   2 0   8
 *           / \
 *          7   4
 * 
 * Find LCA(6, 4):
 * 
 * lowestCommonAncestor(3,6,4):
 *   root=3, not null/p/q
 *   left = lowestCommonAncestor(5,6,4)
 *     root=5, not null/p/q
 *     left = lowestCommonAncestor(6,6,4) → returns 6 (found p)
 *     right = lowestCommonAncestor(2,6,4)
 *       root=2, not null/p/q
 *       left = lowestCommonAncestor(7,6,4) → null
 *       right = lowestCommonAncestor(4,6,4) → returns 4 (found q)
 *       left=null, right=4 → return 4
 *     left=6, right=4 → both non-null → return 5 (LCA)
 *   right = lowestCommonAncestor(1,6,4) → null
 *   left=5, right=null → return 5
 * 
 * Result: 5 ✓
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Not handling case where p is ancestor of q (or vice versa)
 * 2. Assuming both nodes exist in tree
 * 3. Forgetting null checks
 * 4. Wrong return logic (should return non-null when only one found)
 * 5. Confusing with BST LCA (which is simpler)
 */

/**
 * VARIATIONS:
 * 
 * 1. LCA in Binary Search Tree (simpler, use BST property)
 * 2. LCA with parent pointers (no need for recursion)
 * 3. Find distance between two nodes (distance = depth(p) + depth(q) - 2*depth(LCA))
 * 4. LCA for N-ary tree
 * 5. Find all common ancestors
 */

/**
 * PRACTICE EXERCISES:
 * 
 * 1. Implement iterative solution
 * 2. Find distance between two nodes
 * 3. Check if nodes are cousins (same depth, different parents)
 * 4. Find LCA for three nodes
 * 5. Find k-th ancestor of a node
 */