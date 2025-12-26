import java.util.Scanner;

/**
 * Minimum value in BST (leftmost node)
 * 
 * Problem Statement:
 * Given a Binary Search Tree (BST), find the minimum value in the tree.
 * 
 * BST Property: For any node:
 * - All values in left subtree < node.data
 * - All values in right subtree > node.data
 * 
 * Therefore, the minimum value is always in the leftmost node.
 * 
 * Example:
 * Input BST:
 *        8
 *       / \
 *      3   10
 *     / \    \
 *    1   6    14
 *       / \   /
 *      4   7 13
 * Output: 1
 * 
 * Algorithm:
 * 1. Start at root
 * 2. Keep going left until left child is null
 * 3. Return data of that node
 * 
 * Time Complexity: O(h) where h = height of tree
 *   - Best/Average case (balanced tree): O(log n)
 *   - Worst case (skewed left tree): O(1) 
 *   - Worst case (skewed right tree): O(n)
 * Space Complexity: O(1) for iterative, O(h) for recursive
 */
public class MinInBST {
    static class Node { 
        int data; 
        Node left, right; 
        Node(int d) { data = d; }
    }

    /**
     * Iterative solution: Find leftmost node
     * 
     * @param root Root of BST
     * @return Minimum value in BST
     * @throws IllegalArgumentException if tree is empty
     */
    public int minValue(Node root) {
        // Handle empty tree
        if (root == null) {
            throw new IllegalArgumentException("Empty tree: cannot find minimum value");
        }
        
        // Iterate to leftmost node
        Node current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.data;
    }
    
    /**
     * Recursive solution
     */
    public int minValueRecursive(Node root) {
        if (root == null) {
            throw new IllegalArgumentException("Empty tree: cannot find minimum value");
        }
        
        // Base case: leftmost node found
        if (root.left == null) {
            return root.data;
        }
        
        // Recursive case: go left
        return minValueRecursive(root.left);
    }
    
    /**
     * Find maximum value in BST (rightmost node)
     */
    public int maxValue(Node root) {
        if (root == null) {
            throw new IllegalArgumentException("Empty tree: cannot find maximum value");
        }
        
        Node current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.data;
    }
    
    /**
     * Find both min and max in single traversal
     */
    public int[] findMinMax(Node root) {
        if (root == null) {
            throw new IllegalArgumentException("Empty tree");
        }
        
        int min = root.data;
        int max = root.data;
        
        // For min: traverse left
        Node current = root;
        while (current.left != null) {
            current = current.left;
            min = current.data;
        }
        
        // For max: traverse right
        current = root;
        while (current.right != null) {
            current = current.right;
            max = current.data;
        }
        
        return new int[]{min, max};
    }
    
    /**
     * Find k-th smallest element in BST
     * Using inorder traversal
     */
    public int kthSmallest(Node root, int k) {
        if (root == null || k <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        
        int[] result = new int[2]; // [count, value]
        result[0] = 0; // Counter
        result[1] = -1; // Result value
        
        kthSmallestHelper(root, k, result);
        
        if (result[1] == -1) {
            throw new IllegalArgumentException("k is larger than tree size");
        }
        
        return result[1];
    }
    
    private void kthSmallestHelper(Node node, int k, int[] result) {
        if (node == null || result[0] >= k) {
            return;
        }
        
        // Inorder traversal: left → node → right
        kthSmallestHelper(node.left, k, result);
        
        result[0]++; // Visit current node
        if (result[0] == k) {
            result[1] = node.data;
            return;
        }
        
        kthSmallestHelper(node.right, k, result);
    }
    
    /**
     * Visualization helper: Show search for minimum
     */
    public void visualizeMinSearch(Node root) {
        System.out.println("\n=== Visualizing Minimum Value Search in BST ===");
        
        if (root == null) {
            System.out.println("Tree is empty. No minimum value.");
            return;
        }
        
        System.out.println("BST structure:");
        printTree(root, "", true);
        
        System.out.println("\n--- Finding Minimum Value ---");
        System.out.println("Strategy: Follow left children until null");
        
        Node current = root;
        int step = 1;
        
        System.out.printf("Step %d: Start at root (%d)\n", step++, current.data);
        
        while (current.left != null) {
            System.out.printf("Step %d: %d has left child %d → go left\n", 
                step++, current.data, current.left.data);
            current = current.left;
        }
        
        System.out.println("\n✓ Reached leftmost node: " + current.data);
        System.out.println("Minimum value in BST: " + current.data);
        
        // Verify with inorder traversal
        System.out.println("\n--- Verification via Inorder Traversal ---");
        System.out.print("Inorder traversal (sorted): ");
        inorderTraversal(root);
        System.out.println("\nFirst element in inorder: " + getFirstInorder(root));
        
        // Show maximum search for comparison
        System.out.println("\n--- Finding Maximum Value ---");
        System.out.println("Strategy: Follow right children until null");
        System.out.println("Maximum value: " + maxValue(root));
    }
    
    /**
     * Print tree in readable format
     */
    private void printTree(Node root, String prefix, boolean isLeft) {
        if (root == null) return;
        
        System.out.println(prefix + (isLeft ? "├── " : "└── ") + root.data);
        
        // Compute new prefix for children
        String childPrefix = prefix + (isLeft ? "│   " : "    ");
        
        // Print right child first (for better visualization)
        printTree(root.right, childPrefix, false);
        printTree(root.left, childPrefix, true);
    }
    
    /**
     * Inorder traversal
     */
    private void inorderTraversal(Node root) {
        if (root == null) return;
        inorderTraversal(root.left);
        System.out.print(root.data + " ");
        inorderTraversal(root.right);
    }
    
    /**
     * Get first element in inorder traversal (minimum)
     */
    private int getFirstInorder(Node root) {
        if (root == null) return -1;
        while (root.left != null) {
            root = root.left;
        }
        return root.data;
    }
    
    /**
     * Build BST from array for testing
     */
    public Node buildBST(int[] values) {
        if (values == null || values.length == 0) return null;
        
        Node root = new Node(values[0]);
        for (int i = 1; i < values.length; i++) {
            insertBST(root, values[i]);
        }
        return root;
    }
    
    private void insertBST(Node root, int val) {
        Node current = root;
        Node parent = null;
        
        while (current != null) {
            parent = current;
            if (val < current.data) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        
        if (val < parent.data) {
            parent.left = new Node(val);
        } else {
            parent.right = new Node(val);
        }
    }
    
    /**
     * Test different tree structures
     */
    public void testDifferentTrees() {
        System.out.println("\n=== Testing Different BST Structures ===");
        
        // Test 1: Balanced BST
        System.out.println("\n1. Balanced BST:");
        Node balanced = buildBST(new int[]{8, 3, 10, 1, 6, 14, 4, 7, 13});
        System.out.println("Min value: " + minValue(balanced));
        System.out.println("Max value: " + maxValue(balanced));
        
        // Test 2: Skewed right (all right children)
        System.out.println("\n2. Skewed Right BST:");
        Node skewedRight = new Node(1);
        skewedRight.right = new Node(2);
        skewedRight.right.right = new Node(3);
        skewedRight.right.right.right = new Node(4);
        System.out.println("Min value: " + minValue(skewedRight));
        System.out.println("Max value: " + maxValue(skewedRight));
        
        // Test 3: Skewed left (all left children)
        System.out.println("\n3. Skewed Left BST:");
        Node skewedLeft = new Node(4);
        skewedLeft.left = new Node(3);
        skewedLeft.left.left = new Node(2);
        skewedLeft.left.left.left = new Node(1);
        System.out.println("Min value: " + minValue(skewedLeft));
        System.out.println("Max value: " + maxValue(skewedLeft));
        
        // Test 4: Single node
        System.out.println("\n4. Single Node BST:");
        Node single = new Node(42);
        System.out.println("Min value: " + minValue(single));
        System.out.println("Max value: " + maxValue(single));
    }
    
    /**
     * Time complexity analysis helper
     */
    public void analyzeComplexity(Node root) {
        System.out.println("\n--- Time Complexity Analysis ---");
        
        int height = getHeight(root);
        int size = countNodes(root);
        
        System.out.println("Tree height: " + height);
        System.out.println("Number of nodes: " + size);
        
        System.out.println("\nMinimum search complexity:");
        System.out.println("- Always follows left children");
        System.out.println("- Path length = height of leftmost leaf from root");
        
        if (height == size) {
            System.out.println("- Tree is skewed right: O(n) worst case");
            System.out.println("- But min search still O(n) in worst case");
        } else if (isSkewedLeft(root)) {
            System.out.println("- Tree is skewed left: O(1) best case!");
            System.out.println("- Min is at root or very close");
        } else {
            System.out.println("- Balanced tree: O(log n) average case");
        }
        
        System.out.println("\nSpace complexity:");
        System.out.println("- Iterative: O(1) constant space");
        System.out.println("- Recursive: O(h) for call stack");
    }
    
    private int getHeight(Node root) {
        if (root == null) return 0;
        return 1 + Math.max(getHeight(root.left), getHeight(root.right));
    }
    
    private int countNodes(Node root) {
        if (root == null) return 0;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }
    
    private boolean isSkewedLeft(Node root) {
        while (root != null) {
            if (root.right != null) return false;
            root = root.left;
        }
        return true;
    }
    
    /**
     * Test cases and examples
     */
    public static void runTestCases() {
        MinInBST solver = new MinInBST();
        
        // Build example BST
        Node root = new Node(8);
        root.left = new Node(3);
        root.right = new Node(10);
        root.left.left = new Node(1);
        root.left.right = new Node(6);
        root.right.right = new Node(14);
        root.left.right.left = new Node(4);
        root.left.right.right = new Node(7);
        root.right.right.left = new Node(13);
        
        System.out.println("=== Test Cases for Minimum in BST ===");
        System.out.println("BST structure:");
        System.out.println("      8");
        System.out.println("     / \\");
        System.out.println("    3   10");
        System.out.println("   / \\    \\");
        System.out.println("  1   6    14");
        System.out.println("     / \\   /");
        System.out.println("    4   7 13");
        
        System.out.println("\nTest Results:");
        
        // Test 1: Minimum value
        try {
            int min = solver.minValue(root);
            System.out.println("Minimum value: " + min + " (expected: 1) " + 
                             (min == 1 ? "✓" : "✗"));
        } catch (IllegalArgumentException e) {
            System.out.println("Minimum value: ERROR - " + e.getMessage());
        }
        
        // Test 2: Maximum value
        try {
            int max = solver.maxValue(root);
            System.out.println("Maximum value: " + max + " (expected: 14) " + 
                             (max == 14 ? "✓" : "✗"));
        } catch (IllegalArgumentException e) {
            System.out.println("Maximum value: ERROR - " + e.getMessage());
        }
        
        // Test 3: Min and max together
        try {
            int[] minMax = solver.findMinMax(root);
            System.out.println("Min and max: [" + minMax[0] + ", " + minMax[1] + 
                             "] (expected: [1, 14]) " + 
                             (minMax[0] == 1 && minMax[1] == 14 ? "✓" : "✗"));
        } catch (IllegalArgumentException e) {
            System.out.println("Min and max: ERROR - " + e.getMessage());
        }
        
        // Test 4: k-th smallest
        try {
            int kth = solver.kthSmallest(root, 3);
            System.out.println("3rd smallest: " + kth + " (expected: 4) " + 
                             (kth == 4 ? "✓" : "✗"));
        } catch (IllegalArgumentException e) {
            System.out.println("3rd smallest: ERROR - " + e.getMessage());
        }
        
        // Test 5: Empty tree
        try {
            int min = solver.minValue(null);
            System.out.println("Empty tree min: " + min + " (should throw exception) ✗");
        } catch (IllegalArgumentException e) {
            System.out.println("Empty tree min: Correctly threw exception ✓");
        }
        
        // Test 6: Single node
        Node single = new Node(42);
        try {
            int min = solver.minValue(single);
            System.out.println("Single node min: " + min + " (expected: 42) " + 
                             (min == 42 ? "✓" : "✗"));
        } catch (IllegalArgumentException e) {
            System.out.println("Single node min: ERROR - " + e.getMessage());
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
                int n = sc.nextInt();
                int[] values = new int[n];
                
                for (int i = 0; i < n; i++) {
                    values[i] = sc.nextInt();
                }
                
                MinInBST solver = new MinInBST();
                Node root = solver.buildBST(values);
                
                try {
                    int min = solver.minValue(root);
                    System.out.println(min);
                } catch (IllegalArgumentException e) {
                    System.out.println("Empty tree");
                }
                
                // Uncomment for visualization
                // solver.visualizeMinSearch(root);
            }
        } else {
            // Interactive mode or default test
            System.out.println("No input detected. Running default test cases.");
            runTestCases();
            
            // Example with visualization
            System.out.println("\n\n=== Example with Visualization ===");
            MinInBST solver = new MinInBST();
            
            // Build example BST
            Node root = new Node(8);
            root.left = new Node(3);
            root.right = new Node(10);
            root.left.left = new Node(1);
            root.left.right = new Node(6);
            root.right.right = new Node(14);
            root.left.right.left = new Node(4);
            root.left.right.right = new Node(7);
            root.right.right.left = new Node(13);
            
            solver.visualizeMinSearch(root);
            
            // Test different tree structures
            solver.testDifferentTrees();
        }
        
        sc.close();
    }
}