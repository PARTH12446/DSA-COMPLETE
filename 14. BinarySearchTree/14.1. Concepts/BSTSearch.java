import java.util.Scanner;

/**
 * Search in a Binary Search Tree (LeetCode 700)
 * 
 * Problem Statement:
 * Given the root of a binary search tree (BST) and an integer value,
 * find the node in the BST with value equal to the given value.
 * Return the subtree rooted with that node. If such node does not exist,
 * return null.
 * 
 * Example 1:
 * Input: root = [4,2,7,1,3], val = 2
 * Output: [2,1,3]
 * 
 * Example 2:
 * Input: root = [4,2,7,1,3], val = 5
 * Output: []
 * 
 * BST Property: For any node:
 * - All values in left subtree < node.val
 * - All values in right subtree > node.val
 * 
 * Search Algorithm:
 * Recursive approach:
 * 1. If root is null or root.val == val, return root
 * 2. If val > root.val, search in right subtree
 * 3. If val < root.val, search in left subtree
 * 
 * Time Complexity: O(h) where h = height of tree
 *   - Best/Average case (balanced tree): O(log n)
 *   - Worst case (skewed tree): O(n)
 * Space Complexity: O(h) for recursion stack (or O(1) for iterative)
 */
public class BSTSearch {
    static class TreeNode { 
        int val; 
        TreeNode left, right; 
        TreeNode(int v) { val = v; }
    }

    /**
     * Recursive search in BST
     * 
     * @param root Root of BST
     * @param val Value to search for
     * @return TreeNode with value = val, or null if not found
     */
    public TreeNode searchBST(TreeNode root, int val) {
        // Base case: reached null or found value
        if (root == null || root.val == val) {
            return root;
        }
        
        // BST property: go right if value > current node
        if (val > root.val) {
            return searchBST(root.right, val);
        }
        // Otherwise go left
        else {
            return searchBST(root.left, val);
        }
    }
    
    /**
     * Iterative search in BST
     * Avoids recursion overhead, O(1) space
     */
    public TreeNode searchBSTIterative(TreeNode root, int val) {
        TreeNode current = root;
        
        while (current != null) {
            if (current.val == val) {
                return current;  // Found
            }
            else if (val > current.val) {
                current = current.right;  // Search right subtree
            }
            else {
                current = current.left;   // Search left subtree
            }
        }
        
        return null;  // Not found
    }
    
    /**
     * Search and return subtree (same as searchBST, but named for clarity)
     */
    public TreeNode searchAndReturnSubtree(TreeNode root, int val) {
        TreeNode node = searchBST(root, val);
        return node;  // Returns entire subtree rooted at found node
    }
    
    /**
     * Visualization helper: Show search path
     */
    public void visualizeSearch(TreeNode root, int val) {
        System.out.println("\n=== Visualizing BST Search ===");
        System.out.println("Searching for value: " + val);
        
        if (root == null) {
            System.out.println("Tree is empty. Result: null");
            return;
        }
        
        System.out.println("\n--- Search Path ---");
        TreeNode current = root;
        int step = 1;
        boolean found = false;
        
        while (current != null) {
            System.out.printf("Step %d: At node %d", step++, current.val);
            
            if (current.val == val) {
                System.out.println(" ✓ FOUND!");
                found = true;
                break;
            }
            else if (val > current.val) {
                System.out.println(" → " + val + " > " + current.val + " → go RIGHT");
                current = current.right;
            }
            else {
                System.out.println(" → " + val + " < " + current.val + " → go LEFT");
                current = current.left;
            }
        }
        
        if (!found) {
            System.out.println("Reached null. Value " + val + " not found.");
        }
        
        // Show tree structure
        System.out.println("\n--- Tree Structure ---");
        printTree(root, "", true);
        
        // Show result
        TreeNode result = searchBST(root, val);
        System.out.println("\n=== Result ===");
        if (result != null) {
            System.out.println("Found node with value: " + result.val);
            System.out.println("Subtree rooted at " + val + ":");
            printTree(result, "", true);
        } else {
            System.out.println("Value " + val + " not found in BST.");
        }
    }
    
    /**
     * Print tree in readable format
     */
    private void printTree(TreeNode root, String prefix, boolean isLeft) {
        if (root == null) return;
        
        System.out.println(prefix + (isLeft ? "├── " : "└── ") + root.val);
        
        // Compute new prefix for children
        String childPrefix = prefix + (isLeft ? "│   " : "    ");
        
        // Print right child first (for better visualization)
        printTree(root.right, childPrefix, false);
        printTree(root.left, childPrefix, true);
    }
    
    /**
     * Build BST from array for testing
     */
    public TreeNode buildBST(int[] values) {
        if (values == null || values.length == 0) return null;
        
        TreeNode root = new TreeNode(values[0]);
        for (int i = 1; i < values.length; i++) {
            insertBST(root, values[i]);
        }
        return root;
    }
    
    private void insertBST(TreeNode root, int val) {
        TreeNode current = root;
        TreeNode parent = null;
        
        while (current != null) {
            parent = current;
            if (val < current.val) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        
        if (val < parent.val) {
            parent.left = new TreeNode(val);
        } else {
            parent.right = new TreeNode(val);
        }
    }
    
    /**
     * Inorder traversal to verify BST property
     */
    public void inorderTraversal(TreeNode root) {
        if (root == null) return;
        inorderTraversal(root.left);
        System.out.print(root.val + " ");
        inorderTraversal(root.right);
    }
    
    /**
     * Time complexity analysis helper
     */
    public void analyzeComplexity(TreeNode root) {
        System.out.println("\n--- Time Complexity Analysis ---");
        
        int height = getHeight(root);
        int size = countNodes(root);
        
        System.out.println("Tree height: " + height);
        System.out.println("Number of nodes: " + size);
        
        if (height == size) {
            System.out.println("Tree is skewed (worst case)");
            System.out.println("Search complexity: O(n)");
        } else if (height <= Math.log(size) / Math.log(2) + 1) {
            System.out.println("Tree is balanced (best case)");
            System.out.println("Search complexity: O(log n) ≈ " + 
                             String.format("%.2f", Math.log(size) / Math.log(2)));
        } else {
            System.out.println("Tree is reasonably balanced (average case)");
            System.out.println("Search complexity: O(h) where h = " + height);
        }
    }
    
    private int getHeight(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(getHeight(root.left), getHeight(root.right));
    }
    
    private int countNodes(TreeNode root) {
        if (root == null) return 0;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }
    
    /**
     * Test cases and examples
     */
    public static void runTestCases() {
        BSTSearch solver = new BSTSearch();
        
        // Build example BST: [4,2,7,1,3]
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        
        System.out.println("=== Test Cases for BST Search ===");
        System.out.println("BST structure:");
        System.out.println("      4");
        System.out.println("     / \\");
        System.out.println("    2   7");
        System.out.println("   / \\");
        System.out.println("  1   3");
        
        System.out.println("\nInorder traversal (should be sorted):");
        solver.inorderTraversal(root);
        System.out.println();
        
        Object[][] testCases = {
            // {value to search, expected result value, description}
            {2, 2, "Search for root's left child"},
            {7, 7, "Search for root's right child"},
            {1, 1, "Search for leaf node"},
            {5, null, "Search for non-existent value"},
            {4, 4, "Search for root"},
            {3, 3, "Search for another leaf"},
            {10, null, "Search for value larger than all"},
            {0, null, "Search for value smaller than all"},
        };
        
        System.out.println("\nTest Results:");
        System.out.printf("%-40s %-15s %-15s %s\n", 
            "Description", "Search Value", "Expected", "Got");
        System.out.println("-".repeat(90));
        
        for (Object[] test : testCases) {
            int searchVal = (int) test[0];
            Integer expectedVal = (Integer) test[1];
            String description = (String) test[2];
            
            TreeNode result = solver.searchBST(root, searchVal);
            Integer resultVal = result == null ? null : result.val;
            
            boolean passed = (expectedVal == null && resultVal == null) || 
                           (expectedVal != null && expectedVal.equals(resultVal));
            
            System.out.printf("%-40s %-15d %-15s %-15s %s\n", 
                description, searchVal, 
                expectedVal == null ? "null" : expectedVal,
                resultVal == null ? "null" : resultVal,
                passed ? "✓" : "✗");
        }
        
        // Complexity analysis
        System.out.println("\n");
        solver.analyzeComplexity(root);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Check if there's input
        if (sc.hasNextInt()) {
            int t = sc.nextInt();  // Number of test cases
            
            while (t-- > 0) {
                // For simplicity, we'll build a fixed BST
                // In a real scenario, you'd read the tree structure
                
                // Example: Read n nodes and build BST
                int n = sc.nextInt();
                int[] values = new int[n];
                
                for (int i = 0; i < n; i++) {
                    values[i] = sc.nextInt();
                }
                
                int searchVal = sc.nextInt();
                
                BSTSearch solver = new BSTSearch();
                TreeNode root = solver.buildBST(values);
                
                TreeNode result = solver.searchBST(root, searchVal);
                
                if (result != null) {
                    System.out.println("Found: " + result.val);
                    // Print subtree in preorder or other format if needed
                } else {
                    System.out.println("Not found");
                }
                
                // Uncomment for visualization
                // solver.visualizeSearch(root, searchVal);
            }
        } else {
            // Interactive mode or default test
            System.out.println("No input detected. Running default test cases.");
            runTestCases();
            
            // Example with visualization
            System.out.println("\n\n=== Example with Visualization ===");
            BSTSearch solver = new BSTSearch();
            
            // Build BST: [8,3,10,1,6,14,4,7,13]
            TreeNode root = new TreeNode(8);
            root.left = new TreeNode(3);
            root.right = new TreeNode(10);
            root.left.left = new TreeNode(1);
            root.left.right = new TreeNode(6);
            root.right.right = new TreeNode(14);
            root.left.right.left = new TreeNode(4);
            root.left.right.right = new TreeNode(7);
            root.right.right.left = new TreeNode(13);
            
            System.out.println("Searching in BST:");
            solver.visualizeSearch(root, 6);
            System.out.println("\n");
            solver.visualizeSearch(root, 12);
        }
        
        sc.close();
    }
}