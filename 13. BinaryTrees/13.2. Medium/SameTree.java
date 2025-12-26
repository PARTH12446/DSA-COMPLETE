/**
 * Same Tree (LeetCode 100)
 * 
 * PROBLEM: Check if two binary trees are structurally identical and have same node values
 * 
 * APPROACH: Recursive DFS comparing nodes pairwise
 * TIME: O(n) - each node visited once
 * SPACE: O(h) - recursion stack (h = height of smaller tree)
 */
public class SameTree {

    static class TreeNode {
        int val; 
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    /**
     * Check if two binary trees are identical
     * @param p - root of first tree
     * @param q - root of second tree
     * @return true if trees are identical, false otherwise
     * 
     * RECURSIVE DEFINITION:
     * 1. Both null → identical ✓
     * 2. One null, other not → not identical ✗
     * 3. Values differ → not identical ✗
     * 4. Recursively check left and right subtrees
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // Case 1: Both nodes are null
        if (p == null && q == null) return true;
        
        // Case 2: One is null, other is not (XOR condition)
        if (p == null || q == null) return false;
        
        // Case 3: Values differ
        if (p.val != q.val) return false;
        
        // Case 4: Recursively check subtrees
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
    
    /**
     * Alternative: Iterative BFS using queues
     * Avoids recursion limit for very deep trees
     */
    public boolean isSameTreeBFS(TreeNode p, TreeNode q) {
        Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.offer(p);
        queue.offer(q);
        
        while (!queue.isEmpty()) {
            TreeNode first = queue.poll();
            TreeNode second = queue.poll();
            
            // Both null → continue
            if (first == null && second == null) continue;
            
            // One null, other not → not same
            if (first == null || second == null) return false;
            
            // Values differ → not same
            if (first.val != second.val) return false;
            
            // Add children pairs to queue
            queue.offer(first.left);
            queue.offer(second.left);
            queue.offer(first.right);
            queue.offer(second.right);
        }
        
        return true;
    }
    
    /**
     * Alternative: Iterative DFS using stacks
     */
    public boolean isSameTreeDFS(TreeNode p, TreeNode q) {
        Stack<TreeNode> stack = new java.util.Stack<>();
        stack.push(p);
        stack.push(q);
        
        while (!stack.isEmpty()) {
            TreeNode node2 = stack.pop();
            TreeNode node1 = stack.pop();
            
            if (node1 == null && node2 == null) continue;
            if (node1 == null || node2 == null) return false;
            if (node1.val != node2.val) return false;
            
            // Push right children first (for preorder)
            stack.push(node1.right);
            stack.push(node2.right);
            stack.push(node1.left);
            stack.push(node2.left);
        }
        
        return true;
    }
    
    /**
     * Check if trees are mirror of each other
     * Variation: Symmetric check across two trees
     */
    public boolean areMirror(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        
        return p.val == q.val && 
               areMirror(p.left, q.right) && 
               areMirror(p.right, q.left);
    }
    
    /**
     * Check if tree is symmetric (mirror of itself)
     * LeetCode 101 variation
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return areMirror(root.left, root.right);
    }

    /**
     * Visual Examples:
     * 
     * Example 1: Same trees
     * Tree p:    1     Tree q:    1
     *           / \             / \
     *          2   3           2   3
     * 
     * isSameTree(1,1):
     *   p.val=1, q.val=1 → same
     *   left: isSameTree(2,2) → true
     *   right: isSameTree(3,3) → true
     *   return true
     * 
     * Example 2: Different values
     * Tree p:    1     Tree q:    1
     *           / \             / \
     *          2   3           2   4
     * 
     * isSameTree(1,1):
     *   p.val=1, q.val=1 → same
     *   left: isSameTree(2,2) → true
     *   right: isSameTree(3,4) → false (values differ)
     *   return false
     * 
     * Example 3: Different structure
     * Tree p:    1     Tree q:    1
     *           /                 \
     *          2                   2
     * 
     * isSameTree(1,1):
     *   p.val=1, q.val=1 → same
     *   left: isSameTree(2,null) → false
     *   return false
     */

    public static void main(String[] args) {
        SameTree s = new SameTree();
        
        // Example 1: Same trees
        TreeNode a1 = new TreeNode(1);
        a1.left = new TreeNode(2);
        a1.right = new TreeNode(3);
        
        TreeNode b1 = new TreeNode(1);
        b1.left = new TreeNode(2);
        b1.right = new TreeNode(3);
        
        System.out.println("Example 1 (Same): " + s.isSameTree(a1, b1)); // true
        System.out.println("BFS: " + s.isSameTreeBFS(a1, b1)); // true
        System.out.println("DFS: " + s.isSameTreeDFS(a1, b1)); // true
        
        // Example 2: Different value
        TreeNode b2 = new TreeNode(1);
        b2.left = new TreeNode(2);
        b2.right = new TreeNode(4); // Different value
        
        System.out.println("\nExample 2 (Different value): " + s.isSameTree(a1, b2)); // false
        
        // Example 3: Different structure
        TreeNode a3 = new TreeNode(1);
        a3.left = new TreeNode(2);
        // No right child
        
        TreeNode b3 = new TreeNode(1);
        b3.right = new TreeNode(2);
        // No left child
        
        System.out.println("Example 3 (Different structure): " + s.isSameTree(a3, b3)); // false
        
        // Test cases
        System.out.println("\n=== Test Cases ===");
        
        // Test 1: Both null
        System.out.println("Both null: " + s.isSameTree(null, null)); // true
        
        // Test 2: One null
        System.out.println("One null: " + s.isSameTree(new TreeNode(1), null)); // false
        
        // Test 3: Single node same
        System.out.println("Single node same: " + 
                          s.isSameTree(new TreeNode(5), new TreeNode(5))); // true
        
        // Test 4: Single node different
        System.out.println("Single node different: " + 
                          s.isSameTree(new TreeNode(5), new TreeNode(6))); // false
        
        // Test 5: Deep identical trees
        TreeNode deep1 = new TreeNode(1);
        deep1.left = new TreeNode(2);
        deep1.right = new TreeNode(3);
        deep1.left.left = new TreeNode(4);
        deep1.left.right = new TreeNode(5);
        deep1.right.left = new TreeNode(6);
        deep1.right.right = new TreeNode(7);
        
        TreeNode deep2 = new TreeNode(1);
        deep2.left = new TreeNode(2);
        deep2.right = new TreeNode(3);
        deep2.left.left = new TreeNode(4);
        deep2.left.right = new TreeNode(5);
        deep2.right.left = new TreeNode(6);
        deep2.right.right = new TreeNode(7);
        
        System.out.println("Deep identical: " + s.isSameTree(deep1, deep2)); // true
        
        // Test mirror check
        System.out.println("\n=== Mirror Tests ===");
        TreeNode mirror1 = new TreeNode(1);
        mirror1.left = new TreeNode(2);
        mirror1.right = new TreeNode(3);
        
        TreeNode mirror2 = new TreeNode(1);
        mirror2.left = new TreeNode(3);
        mirror2.right = new TreeNode(2);
        
        System.out.println("Mirror trees: " + s.areMirror(mirror1, mirror2)); // true
        System.out.println("Symmetric tree: " + s.isSymmetric(mirror1)); // false
        
        TreeNode symmetric = new TreeNode(1);
        symmetric.left = new TreeNode(2);
        symmetric.right = new TreeNode(2);
        symmetric.left.left = new TreeNode(3);
        symmetric.left.right = new TreeNode(4);
        symmetric.right.left = new TreeNode(4);
        symmetric.right.right = new TreeNode(3);
        
        System.out.println("Symmetric tree check: " + s.isSymmetric(symmetric)); // true
    }
}

/**
 * KEY CONCEPTS:
 * 
 * 1. Tree equality definition:
 *    - Same structure (identical null positions)
 *    - Same values at corresponding positions
 * 
 * 2. Base cases order matters:
 *    - Check both null first
 *    - Check one null second
 *    - Check values third
 *    - This order prevents NullPointerException
 * 
 * 3. Four possible cases:
 *    Case 1: p=null, q=null → true
 *    Case 2: p=null, q≠null → false
 *    Case 3: p≠null, q=null → false
 *    Case 4: p≠null, q≠null → check values and subtrees
 */

/**
 * TIME & SPACE ANALYSIS:
 * 
 * Recursive:
 *   Time: O(n) where n = number of nodes in smaller tree
 *   Space: O(h) where h = height of smaller tree
 *     - Best case (balanced): O(log n)
 *     - Worst case (skewed): O(n)
 * 
 * Iterative BFS:
 *   Time: O(n)
 *   Space: O(w) where w = max width of smaller tree
 * 
 * Iterative DFS:
 *   Time: O(n)
 *   Space: O(h) like recursive but with explicit stack
 */

/**
 * RECURSION WALKTHROUGH:
 * 
 * Trees:   1         1
 *         / \       / \
 *        2   3     2   3
 * 
 * isSameTree(1,1):
 *   p≠null, q≠null, p.val=1, q.val=1 → match
 *   left = isSameTree(2,2)
 *     p≠null, q≠null, p.val=2, q.val=2 → match
 *     left = isSameTree(null,null) → true
 *     right = isSameTree(null,null) → true
 *     return true
 *   right = isSameTree(3,3) → similar, returns true
 *   return true
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Wrong order of null checks:
 *    - Must check both null before checking one null
 *    - Otherwise NullPointerException
 * 
 * 2. Using == instead of .equals for values:
 *    - For integers == works, but for objects use .equals()
 *    - Here TreeNode.val is int, so == works
 * 
 * 3. Not checking both subtrees:
 *    - Must check left AND right, not left OR right
 * 
 * 4. Assuming trees have same number of nodes:
 *    - Structure might differ even with same node count
 * 
 * 5. Not handling empty trees (null roots)
 */

/**
 * VARIATIONS:
 * 
 * 1. Check if trees are mirror of each other
 * 2. Check if tree is symmetric (mirror of itself)
 * 3. Check if trees are isomorphic (same shape, values may differ)
 * 4. Check if one tree is subtree of another
 * 5. Check if trees are flip equivalent
 */

/**
 * PRACTICE EXERCISES:
 * 
 * 1. Implement iterative version
 * 2. Check if trees are mirror images
 * 3. Find maximum depth difference between trees
 * 4. Check if trees have same inorder traversal
 * 5. Count number of differing nodes
 */