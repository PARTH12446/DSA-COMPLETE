import java.util.Scanner;

/**
 * Ceil in BST (Ceiling of a value in Binary Search Tree)
 * 
 * Problem Statement:
 * Given a BST and a value x, find the smallest value in BST that is greater than
 * or equal to x (ceiling). If such value doesn't exist, return -1.
 * 
 * Ceiling Definition:
 * - ceil(x) = smallest value in BST ≥ x
 * - If x exists in BST, ceil(x) = x
 * - If x doesn't exist, ceil(x) = next larger value
 * - If all values < x, ceil(x) = -1 (not found)
 * 
 * Example:
 * BST:       8
 *          /   \
 *         4     12
 *        / \    / \
 *       2   6  10  14
 * 
 * x = 5  → ceil = 6   (smallest value ≥ 5)
 * x = 6  → ceil = 6   (value exists)
 * x = 11 → ceil = 12  (next larger)
 * x = 15 → ceil = -1  (all values smaller)
 * 
 * Algorithm (Iterative):
 * 1. Initialize ceil = -1
 * 2. Traverse BST:
 *    - If node.key == x: ceil = x, found exact match
 *    - If x > node.key: search right subtree (ceiling must be greater)
 *    - If x < node.key: update ceil = node.key (potential ceil), search left subtree
 * 3. Return ceil
 * 
 * Time Complexity: O(h) where h = height of tree
 * Space Complexity: O(1) for iterative, O(h) for recursive
 */
public class CeilBST {
    static class Node { 
        int key; 
        Node left, right; 
        Node(int k) { key = k; }
    }

    /**
     * Iterative solution to find ceil in BST
     * 
     * @param root Root of BST
     * @param inp Value to find ceil for
     * @return Ceil value (smallest value ≥ inp), or -1 if not found
     */
    public int findCeil(Node root, int inp) {
        int ceil = -1;  // Initialize with "not found"
        
        while (root != null) {
            if (root.key == inp) {
                // Exact match found
                ceil = root.key;
                break;
            }
            
            if (inp > root.key) {
                // Current node is smaller than input
                // Ceil must be in right subtree
                root = root.right;
            } else {
                // Current node is greater than input
                // This is a potential ceil, but there might be
                // smaller values ≥ inp in left subtree
                ceil = root.key;
                root = root.left;
            }
        }
        
        return ceil;
    }
    
    /**
     * Recursive solution
     */
    public int findCeilRecursive(Node root, int inp) {
        return findCeilRecursiveHelper(root, inp, -1);
    }
    
    private int findCeilRecursiveHelper(Node node, int inp, int ceil) {
        if (node == null) {
            return ceil;
        }
        
        if (node.key == inp) {
            return inp;  // Exact match
        }
        
        if (inp > node.key) {
            // Search right subtree
            return findCeilRecursiveHelper(node.right, inp, ceil);
        } else {
            // Current node is potential ceil, search left for smaller ceil
            return findCeilRecursiveHelper(node.left, inp, node.key);
        }
    }
    
    /**
     * Find floor in BST (largest value ≤ inp)
     */
    public int findFloor(Node root, int inp) {
        int floor = -1;
        
        while (root != null) {
            if (root.key == inp) {
                floor = root.key;
                break;
            }
            
            if (inp < root.key) {
                // Current node is larger than input
                // Floor must be in left subtree
                root = root.left;
            } else {
                // Current node is smaller than input
                // This is a potential floor, but there might be
                // larger values ≤ inp in right subtree
                floor = root.key;
                root = root.right;
            }
        }
        
        return floor;
    }
    
    /**
     * Find both ceil and floor in single traversal
     */
    public int[] findCeilAndFloor(Node root, int inp) {
        int ceil = -1;
        int floor = -1;
        Node current = root;
        
        while (current != null) {
            if (current.key == inp) {
                // Exact match
                ceil = floor = current.key;
                break;
            }
            
            if (inp < current.key) {
                // Current node is larger than input
                // Current node is potential ceil
                ceil = current.key;
                // Search left for smaller values
                current = current.left;
            } else {
                // Current node is smaller than input
                // Current node is potential floor
                floor = current.key;
                // Search right for larger values
                current = current.right;
            }
        }
        
        return new int[]{ceil, floor};
    }
    
    /**
     * Visualization helper: Show ceil search process
     */
    public void visualizeCeilSearch(Node root, int inp) {
        System.out.println("\n=== Visualizing Ceil Search in BST ===");
        System.out.println("Finding ceil(" + inp + ") = smallest value ≥ " + inp);
        
        if (root == null) {
            System.out.println("Tree is empty. Ceil = -1");
            return;
        }
        
        System.out.println("\nBST structure:");
        printTree(root, "", true);
        
        System.out.println("\n--- Search Process ---");
        System.out.println("Rules:");
        System.out.println("1. If node.key == inp → found exact ceil");
        System.out.println("2. If inp > node.key → search right subtree");
        System.out.println("3. If inp < node.key → update ceil, search left subtree");
        
        int ceil = -1;
        Node current = root;
        int step = 1;
        
        while (current != null) {
            System.out.printf("\nStep %d: At node %d, current ceil = %s\n", 
                step++, current.key, ceil == -1 ? "-1 (not found)" : String.valueOf(ceil));
            
            if (current.key == inp) {
                System.out.println("  " + inp + " == " + current.key + " → found exact match!");
                ceil = current.key;
                break;
            }
            
            if (inp > current.key) {
                System.out.println("  " + inp + " > " + current.key + " → ceil must be in right subtree");
                System.out.println("  Go right");
                current = current.right;
            } else {
                System.out.println("  " + inp + " < " + current.key + " → " + current.key + " is potential ceil");
                System.out.println("  Update ceil = " + current.key);
                ceil = current.key;
                System.out.println("  Search left for possibly smaller ceil");
                current = current.left;
            }
        }
        
        System.out.println("\n=== Result ===");
        System.out.println("ceil(" + inp + ") = " + ceil);
        
        if (ceil == -1) {
            System.out.println("All values in BST are smaller than " + inp);
        } else if (ceil == inp) {
            System.out.println(inp + " exists in BST");
        } else {
            System.out.println("Smallest value ≥ " + inp + " is " + ceil);
        }
        
        // Show floor for comparison
        System.out.println("\n--- Floor for Comparison ---");
        int floor = findFloor(root, inp);
        System.out.println("floor(" + inp + ") = largest value ≤ " + inp + " = " + floor);
        
        // Show all values for context
        System.out.println("\n--- All BST Values (sorted) ---");
        System.out.print("Inorder traversal: ");
        inorderTraversal(root);
        System.out.println();
    }
    
    /**
     * Print tree in readable format
     */
    private void printTree(Node root, String prefix, boolean isLeft) {
        if (root == null) return;
        
        System.out.println(prefix + (isLeft ? "├── " : "└── ") + root.key);
        
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
        System.out.print(root.key + " ");
        inorderTraversal(root.right);
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
            if (val < current.key) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        
        if (val < parent.key) {
            parent.left = new Node(val);
        } else {
            parent.right = new Node(val);
        }
    }
    
    /**
     * Test different scenarios
     */
    public void testDifferentScenarios() {
        System.out.println("\n=== Testing Different Ceil Scenarios ===");
        
        // Build example BST
        Node root = new Node(8);
        root.left = new Node(4);
        root.right = new Node(12);
        root.left.left = new Node(2);
        root.left.right = new Node(6);
        root.right.left = new Node(10);
        root.right.right = new Node(14);
        
        System.out.println("BST with values: {2, 4, 6, 8, 10, 12, 14}");
        
        Object[][] testCases = {
            // {input, expected ceil, description}
            {5, 6, "Between 4 and 6"},
            {6, 6, "Exists in BST"},
            {1, 2, "Smaller than all"},
            {8, 8, "Root value"},
            {11, 12, "Between 10 and 12"},
            {14, 14, "Maximum value"},
            {15, -1, "Larger than all"},
            {0, 2, "Very small value"},
            {7, 8, "Between 6 and 8"},
            {10, 10, "Exists in BST"},
        };
        
        System.out.println("\nTest Results:");
        System.out.printf("%-25s %-10s %-10s %s\n", 
            "Description", "Input", "Expected", "Got");
        System.out.println("-".repeat(60));
        
        for (Object[] test : testCases) {
            int inp = (int) test[0];
            int expected = (int) test[1];
            String desc = (String) test[2];
            
            int result = findCeil(root, inp);
            boolean passed = (result == expected);
            
            System.out.printf("%-25s %-10d %-10d %-10d %s\n", 
                desc, inp, expected, result, passed ? "✓" : "✗");
        }
    }
    
    /**
     * Compare ceil with binary search on sorted array
     */
    public void compareWithBinarySearch(Node root, int inp) {
        System.out.println("\n=== Comparison with Binary Search ===");
        
        // Get sorted array from BST
        java.util.List<Integer> sorted = new java.util.ArrayList<>();
        getSortedList(root, sorted);
        
        System.out.println("Sorted array: " + sorted);
        System.out.println("Target: " + inp);
        
        // Binary search for ceil
        int left = 0, right = sorted.size() - 1;
        int ceil = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midVal = sorted.get(mid);
            
            if (midVal == inp) {
                ceil = midVal;
                break;
            } else if (midVal < inp) {
                left = mid + 1;
            } else {
                ceil = midVal;
                right = mid - 1;
            }
        }
        
        System.out.println("Binary search ceil: " + ceil);
        System.out.println("BST traversal ceil: " + findCeil(root, inp));
        System.out.println("Both methods should give same result!");
    }
    
    private void getSortedList(Node root, java.util.List<Integer> result) {
        if (root == null) return;
        getSortedList(root.left, result);
        result.add(root.key);
        getSortedList(root.right, result);
    }
    
    /**
     * Time complexity analysis
     */
    public void analyzeComplexity(Node root) {
        System.out.println("\n--- Time Complexity Analysis ---");
        
        int height = getHeight(root);
        int size = countNodes(root);
        
        System.out.println("Tree height: " + height);
        System.out.println("Number of nodes: " + size);
        
        System.out.println("\nCeil search complexity:");
        System.out.println("- Follows a path from root to leaf");
        System.out.println("- At each node: O(1) comparison");
        System.out.println("- Path length ≤ tree height");
        
        if (height <= Math.log(size) / Math.log(2) + 1) {
            System.out.println("- Balanced tree: O(log n) ≈ O(" + 
                             String.format("%.0f", Math.log(size) / Math.log(2)) + ")");
        } else if (height == size) {
            System.out.println("- Skewed tree: O(n) worst case");
        } else {
            System.out.println("- General tree: O(h) where h = " + height);
        }
        
        System.out.println("\nSpace complexity:");
        System.out.println("- Iterative: O(1) constant space");
        System.out.println("- Recursive: O(h) for call stack");
        
        System.out.println("\nComparison with binary search:");
        System.out.println("- BST search: O(h), no need to store array");
        System.out.println("- Binary search on array: O(log n), needs sorted array");
    }
    
    private int getHeight(Node root) {
        if (root == null) return 0;
        return 1 + Math.max(getHeight(root.left), getHeight(root.right));
    }
    
    private int countNodes(Node root) {
        if (root == null) return 0;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }
    
    /**
     * Test cases and examples
     */
    public static void runTestCases() {
        CeilBST solver = new CeilBST();
        
        // Build example BST
        Node root = new Node(8);
        root.left = new Node(4);
        root.right = new Node(12);
        root.left.left = new Node(2);
        root.left.right = new Node(6);
        root.right.left = new Node(10);
        root.right.right = new Node(14);
        
        System.out.println("=== Test Cases for Ceil in BST ===");
        System.out.println("BST structure:");
        System.out.println("       8");
        System.out.println("     /   \\");
        System.out.println("    4     12");
        System.out.println("   / \\    / \\");
        System.out.println("  2   6  10  14");
        
        System.out.println("\nTest Results:");
        
        // Test various inputs
        int[] testInputs = {1, 2, 3, 5, 6, 7, 8, 9, 11, 13, 14, 15};
        int[] expected = {2, 2, 4, 6, 6, 8, 8, 10, 12, 14, 14, -1};
        
        System.out.printf("%-15s %-15s %-15s %s\n", 
            "Input", "Expected", "Got", "Status");
        System.out.println("-".repeat(50));
        
        for (int i = 0; i < testInputs.length; i++) {
            int inp = testInputs[i];
            int exp = expected[i];
            int result = solver.findCeil(root, inp);
            boolean passed = (result == exp);
            
            System.out.printf("%-15d %-15d %-15d %s\n", 
                inp, exp, result, passed ? "✓" : "✗");
        }
        
        // Test floor function
        System.out.println("\n=== Testing Floor Function ===");
        int[] floorInputs = {1, 2, 3, 5, 6, 7, 8, 9, 11, 13, 14, 15};
        int[] floorExpected = {-1, 2, 2, 4, 6, 6, 8, 8, 10, 12, 14, 14};
        
        System.out.printf("%-15s %-15s %-15s %s\n", 
            "Input", "Expected", "Got", "Status");
        System.out.println("-".repeat(50));
        
        for (int i = 0; i < floorInputs.length; i++) {
            int inp = floorInputs[i];
            int exp = floorExpected[i];
            int result = solver.findFloor(root, inp);
            boolean passed = (result == exp);
            
            System.out.printf("%-15d %-15d %-15d %s\n", 
                inp, exp, result, passed ? "✓" : "✗");
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
                
                int inp = sc.nextInt();
                
                CeilBST solver = new CeilBST();
                Node root = solver.buildBST(values);
                
                int result = solver.findCeil(root, inp);
                System.out.println(result);
                
                // Uncomment for visualization
                // solver.visualizeCeilSearch(root, inp);
            }
        } else {
            // Interactive mode or default test
            System.out.println("No input detected. Running default test cases.");
            runTestCases();
            
            // Example with visualization
            System.out.println("\n\n=== Example with Visualization ===");
            CeilBST solver = new CeilBST();
            
            // Build example BST
            Node root = new Node(8);
            root.left = new Node(4);
            root.right = new Node(12);
            root.left.left = new Node(2);
            root.left.right = new Node(6);
            root.right.left = new Node(10);
            root.right.right = new Node(14);
            
            solver.visualizeCeilSearch(root, 5);
            
            // Test different scenarios
            solver.testDifferentScenarios();
            
            // Compare with binary search
            solver.compareWithBinarySearch(root, 11);
        }
        
        sc.close();
    }
}