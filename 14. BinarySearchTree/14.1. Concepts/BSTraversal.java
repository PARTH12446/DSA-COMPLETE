import java.util.Scanner;

/**
 * Check if an array can be inorder traversal of a BST (strictly increasing).
 * 
 * Problem Statement:
 * Given an array of integers, check if it could be the inorder traversal
 * of a binary search tree (BST). For a valid BST, its inorder traversal
 * must be in strictly increasing order.
 * 
 * Important: This checks if the array COULD BE an inorder traversal.
 * It does NOT check if the array IS a valid BST inorder traversal
 * (which would require additional constraints).
 * 
 * For a BST, inorder traversal gives sorted ascending order.
 * 
 * Example 1:
 * Input: [1, 2, 3, 4, 5]
 * Output: true (could be inorder of BST)
 * 
 * Example 2:
 * Input: [1, 3, 2, 4, 5]
 * Output: false (not sorted, cannot be BST inorder)
 * 
 * Edge Cases:
 * - Empty array: true (empty tree is valid BST)
 * - Single element: true
 * - Duplicate values: false (BST requires distinct values or consistent handling)
 * 
 * Time Complexity: O(n) - single pass through array
 * Space Complexity: O(1) - only constant extra space
 */
public class BSTraversal {

    /**
     * Check if array could be inorder traversal of BST
     * 
     * @param arr Array of integers
     * @return true if array is strictly increasing (could be BST inorder)
     */
    public boolean isBSTTraversal(int[] arr) {
        // Edge cases
        if (arr == null || arr.length <= 1) {
            return true;
        }
        
        // Check if array is strictly increasing
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] <= arr[i - 1]) {
                return false; // Not strictly increasing
            }
        }
        
        return true;
    }
    
    /**
     * Alternative: With explicit previous value tracking
     */
    public boolean isBSTTraversalAlternative(int[] arr) {
        if (arr == null) return true;
        
        int prev = Integer.MIN_VALUE;
        boolean firstElement = true;
        
        for (int num : arr) {
            // For first element, just store it
            if (firstElement) {
                prev = num;
                firstElement = false;
                continue;
            }
            
            // Check if current element > previous
            if (num <= prev) {
                return false;
            }
            
            prev = num;
        }
        
        return true;
    }
    
    /**
     * Enhanced version: Checks if array can represent a BST with duplicate values
     * according to different BST definitions:
     * 1. Standard BST: No duplicates allowed
     * 2. BST with duplicates allowed in left subtree (<) or right subtree (<=)
     */
    public BSTCheckResult checkBSTTraversalEnhanced(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new BSTCheckResult(true, true, true);
        }
        
        boolean strictIncreasing = true;      // Standard BST: no duplicates
        boolean nonDecreasing = true;         // BST with left < root ≤ right
        boolean nonIncreasing = false;        // BST with left ≤ root < right
        
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] <= arr[i - 1]) {
                strictIncreasing = false;
            }
            if (arr[i] < arr[i - 1]) {
                nonDecreasing = false;
            }
        }
        
        // Check non-increasing (for completeness)
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[i - 1]) {
                nonIncreasing = false;
                break;
            }
        }
        
        return new BSTCheckResult(strictIncreasing, nonDecreasing, nonIncreasing);
    }
    
    static class BSTCheckResult {
        boolean strictIncreasing;  // Standard BST (no duplicates)
        boolean nonDecreasing;     // BST with duplicates in right subtree
        boolean nonIncreasing;     // BST with duplicates in left subtree
        
        BSTCheckResult(boolean strict, boolean nonDec, boolean nonInc) {
            this.strictIncreasing = strict;
            this.nonDecreasing = nonDec;
            this.nonIncreasing = nonInc;
        }
        
        @Override
        public String toString() {
            return String.format(
                "Standard BST (strictly increasing): %s\n" +
                "BST with duplicates in right (non-decreasing): %s\n" +
                "BST with duplicates in left (non-increasing): %s",
                strictIncreasing, nonDecreasing, nonIncreasing
            );
        }
    }
    
    /**
     * Visualization helper: Show array and BST examples
     */
    public void visualizeBSTTraversal(int[] arr) {
        System.out.println("\n=== Visualizing BST Inorder Traversal Check ===");
        System.out.println("Array: " + java.util.Arrays.toString(arr));
        System.out.println("Length: " + arr.length);
        
        if (arr == null || arr.length == 0) {
            System.out.println("Empty array: can represent empty BST → true");
            return;
        }
        
        // Check if strictly increasing
        boolean isValid = true;
        System.out.println("\nChecking if strictly increasing:");
        for (int i = 1; i < arr.length; i++) {
            String comparison = arr[i] + " > " + arr[i-1] + "? ";
            if (arr[i] > arr[i - 1]) {
                System.out.println("  " + comparison + "✓ Yes");
            } else {
                System.out.println("  " + comparison + "✗ No");
                isValid = false;
                break;
            }
        }
        
        System.out.println("\nResult: Array " + (isValid ? "COULD BE" : "CANNOT BE") + 
                         " inorder traversal of a BST");
        
        // Show BST examples if valid
        if (isValid) {
            System.out.println("\n--- Example BSTs with this inorder traversal ---");
            
            // Example 1: Skewed right BST
            System.out.println("1. Skewed right BST:");
            System.out.println("   Each node has only right child");
            printSkewedRightBST(arr);
            
            // Example 2: Balanced BST (if possible)
            System.out.println("\n2. Balanced BST:");
            System.out.println("   (if length allows balanced structure)");
            printBalancedBSTExample(arr);
            
            // Example 3: Random BST
            System.out.println("\n3. General BST property:");
            System.out.println("   For any BST, inorder gives sorted array");
            System.out.println("   Conversely, any sorted array can be converted to BST");
        } else {
            System.out.println("\n--- Why it fails ---");
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] <= arr[i - 1]) {
                    if (arr[i] == arr[i - 1]) {
                        System.out.println("  Duplicate value at positions " + (i-1) + 
                                         " and " + i + ": " + arr[i]);
                        System.out.println("  Standard BSTs require distinct values");
                    } else {
                        System.out.println("  Decrease at position " + i + 
                                         ": " + arr[i-1] + " → " + arr[i]);
                        System.out.println("  Inorder traversal must be increasing");
                    }
                    break;
                }
            }
        }
        
        // Show mathematical proof
        System.out.println("\n--- Mathematical Proof ---");
        System.out.println("Property: Inorder traversal of BST = sorted ascending order");
        System.out.println("Proof by BST property:");
        System.out.println("  1. Left subtree < Root < Right subtree");
        System.out.println("  2. Inorder: left → root → right");
        System.out.println("  3. Therefore: sorted ascending order");
        System.out.println("Conversely, any sorted array can be BST inorder");
    }
    
    private void printSkewedRightBST(int[] arr) {
        if (arr.length == 0) return;
        
        System.out.println("   Tree structure:");
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < i; j++) System.out.print("    ");
            System.out.println("└── " + arr[i] + " (root)");
            if (i < arr.length - 1) {
                for (int j = 0; j <= i; j++) System.out.print("    ");
                System.out.println("    └── " + arr[i+1] + " (right child)");
            }
        }
        
        System.out.println("\n   Inorder traversal: " + java.util.Arrays.toString(arr));
    }
    
    private void printBalancedBSTExample(int[] arr) {
        if (arr.length == 0) return;
        
        // Build a balanced BST from sorted array
        System.out.println("   Balanced BST from sorted array:");
        printBalancedBST(arr, 0, arr.length - 1, 0);
        
        System.out.println("\n   Construction algorithm:");
        System.out.println("   1. Middle element becomes root");
        System.out.println("   2. Recursively build left from left half");
        System.out.println("   3. Recursively build right from right half");
    }
    
    private void printBalancedBST(int[] arr, int left, int right, int depth) {
        if (left > right) return;
        
        int mid = left + (right - left) / 2;
        
        // Print with indentation based on depth
        for (int i = 0; i < depth; i++) System.out.print("    ");
        System.out.println("└── " + arr[mid] + " (root)");
        
        // Recursively print left and right subtrees
        printBalancedBST(arr, left, mid - 1, depth + 1);
        printBalancedBST(arr, mid + 1, right, depth + 1);
    }
    
    /**
     * Build actual BST from sorted array and verify inorder
     */
    public void buildAndVerifyBST(int[] arr) {
        if (arr == null || arr.length == 0) return;
        
        System.out.println("\n--- Building BST from sorted array ---");
        
        // Check if array is sorted
        if (!isBSTTraversal(arr)) {
            System.out.println("Cannot build BST: array not sorted");
            return;
        }
        
        // Build balanced BST
        TreeNode root = buildBalancedBST(arr, 0, arr.length - 1);
        
        System.out.println("Built balanced BST structure:");
        printTree(root, "", true);
        
        // Verify inorder traversal
        System.out.println("\nVerifying inorder traversal:");
        java.util.List<Integer> inorder = new java.util.ArrayList<>();
        inorderTraversal(root, inorder);
        System.out.println("Inorder from BST: " + inorder);
        System.out.println("Original array:    " + java.util.Arrays.toString(arr));
        System.out.println("Match: " + inorder.equals(java.util.Arrays.asList(
            java.util.Arrays.stream(arr).boxed().toArray(Integer[]::new))));
    }
    
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int val) {
            this.val = val;
        }
    }
    
    private TreeNode buildBalancedBST(int[] arr, int left, int right) {
        if (left > right) return null;
        
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(arr[mid]);
        root.left = buildBalancedBST(arr, left, mid - 1);
        root.right = buildBalancedBST(arr, mid + 1, right);
        return root;
    }
    
    private void inorderTraversal(TreeNode root, java.util.List<Integer> result) {
        if (root == null) return;
        inorderTraversal(root.left, result);
        result.add(root.val);
        inorderTraversal(root.right, result);
    }
    
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
     * Test cases and examples
     */
    public static void runTestCases() {
        BSTraversal solver = new BSTraversal();
        
        Object[][] testCases = {
            // {array, expected result}
            {new int[]{1, 2, 3, 4, 5}, true},
            {new int[]{1, 3, 2, 4, 5}, false},
            {new int[]{}, true},
            {new int[]{1}, true},
            {new int[]{1, 1, 2, 3}, false},  // Duplicates
            {new int[]{5, 4, 3, 2, 1}, false}, // Decreasing
            {new int[]{-5, -3, 0, 2, 4}, true},
            {new int[]{1, 2, 2, 3}, false},  // Duplicates
            {new int[]{1, 2, 3, 3}, false},  // Duplicates
            {new int[]{10, 20, 30, 40, 50, 60}, true},
            {new int[]{10, 20, 15, 30, 25}, false}, // Not sorted
            {null, true},  // null array
        };
        
        System.out.println("=== Test Cases for BST Inorder Traversal Check ===");
        System.out.printf("%-30s %-15s %-15s %s\n", 
            "Array", "Expected", "Got", "Status");
        System.out.println("-".repeat(75));
        
        for (Object[] test : testCases) {
            int[] arr = (int[]) test[0];
            boolean expected = (boolean) test[1];
            
            boolean result = solver.isBSTTraversal(arr);
            boolean passed = (result == expected);
            
            String arrStr = arr == null ? "null" : java.util.Arrays.toString(arr);
            if (arrStr.length() > 30) {
                arrStr = arrStr.substring(0, 27) + "...";
            }
            
            System.out.printf("%-30s %-15s %-15s %s\n", 
                arrStr, expected, result, passed ? "✓" : "✗");
        }
        
        // Enhanced checks
        System.out.println("\n=== Enhanced BST Checks (with duplicates) ===");
        int[] arrWithDupes = {1, 2, 2, 3, 4};
        BSTCheckResult enhanced = solver.checkBSTTraversalEnhanced(arrWithDupes);
        System.out.println("Array: " + java.util.Arrays.toString(arrWithDupes));
        System.out.println(enhanced);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Check if there's input
        if (sc.hasNextInt()) {
            int t = sc.nextInt();  // Number of test cases
            
            while (t-- > 0) {
                int n = sc.nextInt();
                int[] arr = new int[n];
                
                for (int i = 0; i < n; i++) {
                    arr[i] = sc.nextInt();
                }
                
                BSTraversal solver = new BSTraversal();
                boolean result = solver.isBSTTraversal(arr);
                System.out.println(result ? "true" : "false");
                
                // Uncomment for visualization
                // solver.visualizeBSTTraversal(arr);
                
                // Uncomment to build and verify BST
                // solver.buildAndVerifyBST(arr);
                
                // Enhanced check
                // BSTCheckResult enhanced = solver.checkBSTTraversalEnhanced(arr);
                // System.out.println("Enhanced check: " + enhanced);
            }
        } else {
            // Interactive mode or default test
            System.out.println("No input detected. Running default test cases.");
            runTestCases();
            
            // Example with visualization
            System.out.println("\n\n=== Example with Visualization ===");
            BSTraversal solver = new BSTraversal();
            int[] example1 = {1, 2, 3, 4, 5};
            int[] example2 = {1, 3, 2, 4, 5};
            
            System.out.println("\nExample 1: " + java.util.Arrays.toString(example1));
            solver.visualizeBSTTraversal(example1);
            
            System.out.println("\n\nExample 2: " + java.util.Arrays.toString(example2));
            solver.visualizeBSTTraversal(example2);
        }
        
        sc.close();
    }
}