/**
 * Finds the floor value of a given key in a Binary Search Tree (BST).
 * 
 * Floor Definition: The largest value in the BST that is less than or equal to the given key.
 * - If key exists in BST, floor = key
 * - If key doesn't exist, floor = largest value smaller than key
 * - If no such value exists (all values > key), return -1 (or appropriate sentinel)
 * 
 * Approach: Iterative traversal using BST properties
 * - Start with answer = -1 (sentinel for "not found")
 * - Traverse from root:
 *   1. If node.data == key → floor = key (exact match)
 *   2. If node.data < key → candidate found, go right to find larger candidate
 *   3. If node.data > key → go left to find smaller value
 * 
 * Time Complexity: O(h) where h is tree height
 * - O(log n) for balanced BST
 * - O(n) for skewed tree
 * 
 * Space Complexity: O(1) - iterative approach uses constant extra space
 * 
 * Example: BST = [8, 4, 12, 2, 6, 10, 14]
 *   floor(11) = 10  (largest ≤ 11)
 *   floor(6)  = 6   (exact match)
 *   floor(1)  = -1  (no value ≤ 1)
 *   floor(15) = 14  (largest ≤ 15)
 */
public class FloorBST {
    static class Node { 
        int data; 
        Node left, right; 
        Node(int d) {
            data = d;
        }
    }

    /**
     * Finds floor of given key in BST (largest value ≤ key).
     * 
     * @param root Root of the BST
     * @param x    Key to find floor for
     * @return     Floor value, or -1 if no floor exists
     */
    public int floor(Node root, int x) {
        int ans = -1;  // Sentinel value for "no floor found"
        
        // Iterative BST traversal
        while (root != null) {
            if (root.data == x) {
                // Exact match found, this is the floor
                ans = root.data;
                break;
            }
            
            if (root.data < x) {
                // Current node is a candidate (≤ x)
                // Update answer and search right for potentially larger candidate
                ans = root.data;
                root = root.right;
            } else {
                // Current node > x, so floor must be in left subtree
                root = root.left;
            }
        }
        return ans;
    }
    
    /**
     * Recursive version of floor function.
     * Alternative approach using recursion.
     */
    public int floorRecursive(Node root, int x) {
        return floorHelper(root, x, -1);
    }
    
    private int floorHelper(Node node, int x, int currentFloor) {
        if (node == null) {
            return currentFloor;  // Base case: return best floor found so far
        }
        
        if (node.data == x) {
            return x;  // Exact match
        }
        
        if (node.data < x) {
            // Current node is a better candidate than previous floor
            return floorHelper(node.right, x, node.data);
        } else {
            // Current node > x, floor must be in left subtree
            return floorHelper(node.left, x, currentFloor);
        }
    }
    
    /**
     * Finds ceiling of given key in BST (smallest value ≥ key).
     * Symmetric to floor operation.
     * 
     * @param root Root of the BST
     * @param x    Key to find ceiling for
     * @return     Ceiling value, or -1 if no ceiling exists
     */
    public int ceiling(Node root, int x) {
        int ans = -1;
        
        while (root != null) {
            if (root.data == x) {
                ans = root.data;
                break;
            }
            
            if (root.data > x) {
                // Current node is a candidate (≥ x)
                ans = root.data;
                root = root.left;  // Search left for potentially smaller candidate
            } else {
                // Current node < x, ceiling must be in right subtree
                root = root.right;
            }
        }
        return ans;
    }
    
    /**
     * Finds both floor and ceiling in single traversal.
     * More efficient than separate calls if both values are needed.
     */
    public int[] floorAndCeiling(Node root, int x) {
        int floor = -1;
        int ceiling = -1;
        
        while (root != null) {
            if (root.data == x) {
                // Exact match, both floor and ceiling are the same
                floor = root.data;
                ceiling = root.data;
                break;
            }
            
            if (root.data < x) {
                // Update floor and go right
                floor = root.data;
                root = root.right;
            } else {
                // Update ceiling and go left
                ceiling = root.data;
                root = root.left;
            }
        }
        
        return new int[]{floor, ceiling};
    }
    
    /**
     * Test cases and examples
     */
    public static void main(String[] args) {
        FloorBST finder = new FloorBST();
        
        // Build BST:
        //       8
        //      / \
        //     4   12
        //    / \  / \
        //   2  6 10 14
        Node root = new Node(8);
        root.left = new Node(4);
        root.right = new Node(12);
        root.left.left = new Node(2);
        root.left.right = new Node(6);
        root.right.left = new Node(10);
        root.right.right = new Node(14);
        
        System.out.println("BST structure:");
        System.out.println("       8");
        System.out.println("      / \\");
        System.out.println("     4   12");
        System.out.println("    / \\  / \\");
        System.out.println("   2  6 10 14");
        System.out.println();
        
        // Test cases
        System.out.println("Test 1: floor(11)");
        System.out.println("Expected: 10 (largest ≤ 11)");
        System.out.println("Result: " + finder.floor(root, 11));
        System.out.println();
        
        System.out.println("Test 2: floor(6)");
        System.out.println("Expected: 6 (exact match)");
        System.out.println("Result: " + finder.floor(root, 6));
        System.out.println();
        
        System.out.println("Test 3: floor(1)");
        System.out.println("Expected: -1 (no value ≤ 1)");
        System.out.println("Result: " + finder.floor(root, 1));
        System.out.println();
        
        System.out.println("Test 4: floor(15)");
        System.out.println("Expected: 14 (largest ≤ 15)");
        System.out.println("Result: " + finder.floor(root, 15));
        System.out.println();
        
        System.out.println("Test 5: floor(5)");
        System.out.println("Expected: 4 (largest ≤ 5)");
        System.out.println("Result: " + finder.floor(root, 5));
        System.out.println();
        
        // Test ceiling
        System.out.println("Test 6: ceiling(11)");
        System.out.println("Expected: 12 (smallest ≥ 11)");
        System.out.println("Result: " + finder.ceiling(root, 11));
        System.out.println();
        
        System.out.println("Test 7: ceiling(5)");
        System.out.println("Expected: 6 (smallest ≥ 5)");
        System.out.println("Result: " + finder.ceiling(root, 5));
        System.out.println();
        
        // Test floor and ceiling together
        int[] result = finder.floorAndCeiling(root, 9);
        System.out.println("Test 8: floorAndCeiling(9)");
        System.out.println("Expected: [8, 10]");
        System.out.println("Result: [" + result[0] + ", " + result[1] + "]");
        
        // Edge cases
        System.out.println("\nEdge Cases:");
        System.out.println("Empty tree floor(5): " + finder.floor(null, 5));
        
        // Single node tree
        Node single = new Node(10);
        System.out.println("Single node floor(10): " + finder.floor(single, 10));
        System.out.println("Single node floor(5): " + finder.floor(single, 5));
        System.out.println("Single node floor(15): " + finder.floor(single, 15));
    }
    
    /**
     * Additional utility: Finds k-th floor (k-th largest value ≤ x)
     * Not commonly required, but shows extension possibility
     */
    public int kthFloor(Node root, int x, int k) {
        // Would require in-order traversal up to x, tracking k values
        // Implementation would be more complex
        return -1; // Placeholder
    }
}