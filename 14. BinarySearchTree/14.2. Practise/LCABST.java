/**
 * Finds the Lowest Common Ancestor (LCA) of two nodes in a Binary Search Tree (BST).
 * 
 * LCA Definition: The deepest node that is an ancestor of both nodes.
 * In BSTs, we can leverage the BST property for efficient LCA finding:
 * - All values in left subtree are LESS than parent
 * - All values in right subtree are GREATER than parent
 * 
 * Key Insight: 
 * For nodes p and q with values a and b (assuming a <= b):
 * 1. If both a and b are LESS than current node → LCA is in left subtree
 * 2. If both a and b are GREATER than current node → LCA is in right subtree
 * 3. Otherwise, current node is the LCA (nodes are on different sides or one is current node)
 * 
 * Approach: Iterative traversal using BST property
 * - Start from root
 * - Compare p and q values with current node
 * - Move left/right until LCA condition is met
 * 
 * Time Complexity: O(h) where h is tree height
 * - O(log n) for balanced BST
 * - O(n) for skewed tree
 * 
 * Space Complexity: O(1) - iterative approach uses constant extra space
 * 
 * Note: This algorithm works because BST properties guarantee:
 * - If p and q are on opposite sides of a node, that node must be LCA
 * - If one node is ancestor of the other, the ancestor is the LCA
 */
public class LCABST {
    static class TreeNode { 
        int val; 
        TreeNode left, right; 
        TreeNode(int v) {
            val = v;
        }
    }

    /**
     * Finds LCA of nodes p and q in BST rooted at root.
     * 
     * @param root Root of the BST
     * @param p    First node (must exist in tree)
     * @param q    Second node (must exist in tree)
     * @return     Lowest Common Ancestor of p and q, or null if not found
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // Ensure a <= b for simpler comparisons
        int a = p.val;
        int b = q.val;
        if (a > b) { 
            int t = a; 
            a = b; 
            b = t; 
        }
        
        // Iterative traversal using BST property
        while (root != null) {
            // Case 1: Current node is between p and q (LCA found)
            if (a < root.val && b > root.val) {
                return root;
            }
            
            // Case 2: Current node is p or q (current node is LCA)
            if (a == root.val || b == root.val) {
                return root;
            }
            
            // Case 3: Both nodes are in right subtree
            if (a > root.val && b > root.val) {
                root = root.right;
            } 
            // Case 4: Both nodes are in left subtree
            else {
                root = root.left;
            }
        }
        
        return null;  // Should not reach here if p and q exist in tree
    }
    
    /**
     * Simplified version without swapping values.
     * More readable, same logic.
     */
    public TreeNode lowestCommonAncestorSimplified(TreeNode root, TreeNode p, TreeNode q) {
        while (root != null) {
            // Both nodes in left subtree
            if (p.val < root.val && q.val < root.val) {
                root = root.left;
            }
            // Both nodes in right subtree
            else if (p.val > root.val && q.val > root.val) {
                root = root.right;
            }
            // Current node is LCA (nodes on different sides or one is current)
            else {
                return root;
            }
        }
        return null;
    }
    
    /**
     * Recursive version of LCA in BST.
     * Same logic, expressed recursively.
     */
    public TreeNode lowestCommonAncestorRecursive(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        
        // Both in left subtree
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestorRecursive(root.left, p, q);
        }
        
        // Both in right subtree
        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestorRecursive(root.right, p, q);
        }
        
        // LCA found
        return root;
    }
    
    /**
     * Finds LCA for general binary tree (not BST).
     * Works for any binary tree, not just BST.
     * More complex: O(n) time, O(h) space.
     */
    public TreeNode lowestCommonAncestorBinaryTree(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }
        
        TreeNode left = lowestCommonAncestorBinaryTree(root.left, p, q);
        TreeNode right = lowestCommonAncestorBinaryTree(root.right, p, q);
        
        if (left != null && right != null) {
            return root;  // Current node is LCA
        }
        
        return left != null ? left : right;
    }
    
    /**
     * Finds LCA for BST when given values instead of nodes.
     * Useful when we don't have node references.
     */
    public TreeNode lowestCommonAncestorByValue(TreeNode root, int pVal, int qVal) {
        while (root != null) {
            if (pVal < root.val && qVal < root.val) {
                root = root.left;
            } else if (pVal > root.val && qVal > root.val) {
                root = root.right;
            } else {
                return root;
            }
        }
        return null;
    }
    
    /**
     * Finds LCA for N nodes in BST.
     * Generalization of the two-node case.
     */
    public TreeNode lowestCommonAncestorN(TreeNode root, TreeNode[] nodes) {
        if (nodes.length == 0) return null;
        if (nodes.length == 1) return nodes[0];
        
        // Find min and max values among all nodes
        int min = nodes[0].val;
        int max = nodes[0].val;
        for (TreeNode node : nodes) {
            min = Math.min(min, node.val);
            max = Math.max(max, node.val);
        }
        
        // LCA is the node where min is in left and max is in right
        return lowestCommonAncestorByValue(root, min, max);
    }
    
    /**
     * Test cases and examples
     */
    public static void main(String[] args) {
        LCABST lcaFinder = new LCABST();
        
        // Build BST:
        //       20
        //      /  \
        //     10   30
        //    /  \    \
        //   5   15    35
        //  / \   \    /
        // 3   7   18 32
        TreeNode root = new TreeNode(20);
        root.left = new TreeNode(10);
        root.right = new TreeNode(30);
        root.left.left = new TreeNode(5);
        root.left.right = new TreeNode(15);
        root.right.right = new TreeNode(35);
        root.left.left.left = new TreeNode(3);
        root.left.left.right = new TreeNode(7);
        root.left.right.right = new TreeNode(18);
        root.right.right.left = new TreeNode(32);
        
        System.out.println("BST structure:");
        System.out.println("         20");
        System.out.println("        /  \\");
        System.out.println("      10    30");
        System.out.println("     /  \\    \\");
        System.out.println("    5    15    35");
        System.out.println("   / \\    \\    /");
        System.out.println("  3   7    18 32");
        System.out.println();
        
        // Get references to test nodes
        TreeNode node3 = root.left.left.left;      // 3
        TreeNode node7 = root.left.left.right;     // 7
        TreeNode node18 = root.left.right.right;   // 18
        TreeNode node32 = root.right.right.left;   // 32
        
        // Test Case 1: Nodes in same subtree
        System.out.println("=== Test Case 1: Nodes in same left subtree ===");
        TreeNode lca1 = lcaFinder.lowestCommonAncestor(root, node3, node7);
        System.out.println("LCA of 3 and 7:");
        System.out.println("  Expected: 5 (their direct parent)");
        System.out.println("  Got: " + (lca1 != null ? lca1.val : "null"));
        
        // Test Case 2: Nodes in different subtrees
        System.out.println("\n=== Test Case 2: Nodes in different subtrees ===");
        TreeNode lca2 = lcaFinder.lowestCommonAncestor(root, node7, node18);
        System.out.println("LCA of 7 and 18:");
        System.out.println("  Expected: 10 (first common ancestor)");
        System.out.println("  Got: " + (lca2 != null ? lca2.val : "null"));
        
        // Test Case 3: One node is ancestor of the other
        System.out.println("\n=== Test Case 3: One node is ancestor of the other ===");
        TreeNode lca3 = lcaFinder.lowestCommonAncestor(root, root, node32);
        System.out.println("LCA of 20 (root) and 32:");
        System.out.println("  Expected: 20 (root is ancestor of 32)");
        System.out.println("  Got: " + (lca3 != null ? lca3.val : "null"));
        
        // Test Case 4: Nodes far apart
        System.out.println("\n=== Test Case 4: Nodes far apart ===");
        TreeNode lca4 = lcaFinder.lowestCommonAncestor(root, node3, node32);
        System.out.println("LCA of 3 and 32:");
        System.out.println("  Expected: 20 (root)");
        System.out.println("  Got: " + (lca4 != null ? lca4.val : "null"));
        
        // Test Case 5: Same node
        System.out.println("\n=== Test Case 5: Same node ===");
        TreeNode lca5 = lcaFinder.lowestCommonAncestor(root, node7, node7);
        System.out.println("LCA of 7 and 7:");
        System.out.println("  Expected: 7 (the node itself)");
        System.out.println("  Got: " + (lca5 != null ? lca5.val : "null"));
        
        // Test Case 6: Using values instead of nodes
        System.out.println("\n=== Test Case 6: Using values ===");
        TreeNode lca6 = lcaFinder.lowestCommonAncestorByValue(root, 3, 18);
        System.out.println("LCA of values 3 and 18:");
        System.out.println("  Expected: 10");
        System.out.println("  Got: " + (lca6 != null ? lca6.val : "null"));
        
        // Test Case 7: Simplified version
        System.out.println("\n=== Test Case 7: Simplified version ===");
        TreeNode lca7 = lcaFinder.lowestCommonAncestorSimplified(root, node3, node18);
        System.out.println("LCA of 3 and 18 (simplified):");
        System.out.println("  Expected: 10");
        System.out.println("  Got: " + (lca7 != null ? lca7.val : "null"));
        
        // Compare with binary tree LCA (should give same result)
        System.out.println("\n=== Comparison with general binary tree LCA ===");
        TreeNode lcaBinary = lcaFinder.lowestCommonAncestorBinaryTree(root, node3, node18);
        System.out.println("General binary tree LCA of 3 and 18:");
        System.out.println("  Expected: 10");
        System.out.println("  Got: " + (lcaBinary != null ? lcaBinary.val : "null"));
        
        // Edge Cases
        System.out.println("\n=== Edge Cases ===");
        System.out.println("Empty tree: " + lcaFinder.lowestCommonAncestor(null, node3, node7));
        
        // Single node tree
        TreeNode single = new TreeNode(42);
        System.out.println("Single node tree, LCA with itself: " + 
            lcaFinder.lowestCommonAncestor(single, single, single).val);
    }
    
    /**
     * Utility: Find distance between two nodes in BST
     * Distance = edges between nodes
     */
    public int distanceBetweenNodes(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode lca = lowestCommonAncestor(root, p, q);
        return distanceFromNode(lca, p) + distanceFromNode(lca, q);
    }
    
    private int distanceFromNode(TreeNode from, TreeNode to) {
        int distance = 0;
        TreeNode current = from;
        
        while (current != to) {
            if (to.val < current.val) {
                current = current.left;
            } else {
                current = current.right;
            }
            distance++;
        }
        return distance;
    }
}