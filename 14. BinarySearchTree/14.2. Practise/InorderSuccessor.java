/**
 * Finds the inorder successor of a given node in a Binary Search Tree (BST).
 * 
 * Inorder Successor: The next node in the inorder traversal of the BST.
 * Two cases to consider:
 * 1. Node has a RIGHT subtree:
 *    - Successor is the MINIMUM node in the right subtree
 *    - Found by going right once, then left as far as possible
 * 
 * 2. Node has NO RIGHT subtree:
 *    - Successor is the nearest ancestor for which the node is in LEFT subtree
 *    - Found by traversing from root to node, tracking last "left turn"
 * 
 * Approach: Iterative BST traversal using the BST property
 * - Start from root, track potential successor
 * - Compare current node with target node (x)
 * - If x.data >= root.data: go right (successor not in left subtree)
 * - If x.data < root.data: current node is a potential successor, go left
 * 
 * Time Complexity: O(h) where h is tree height
 * - O(log n) for balanced BST
 * - O(n) for skewed tree
 * 
 * Space Complexity: O(1) - iterative approach uses constant extra space
 * 
 * Example: BST = [20, 8, 22, 4, 12, 10, 14]
 *   Successor of 8 is 10
 *   Successor of 10 is 12
 *   Successor of 14 is 20
 *   Successor of 22 is null (last node)
 */
public class InorderSuccessor {
    static class Node { 
        int data; 
        Node left, right; 
        Node(int d) {
            data = d;
        }
    }

    /**
     * Finds inorder successor of node x in BST rooted at root.
     * 
     * @param root Root of the BST
     * @param x    Node to find successor for
     * @return     Inorder successor node, or null if x is the last node
     */
    public Node inorderSuccessor(Node root, Node x) {
        Node ans = null;  // Tracks potential successor
        
        // Traverse from root to find successor
        while (root != null) {
            if (x.data >= root.data) {
                // Current node <= x, successor must be in right subtree
                root = root.right;
            } else {
                // Current node > x, so current node is a POTENTIAL successor
                // But we need the SMALLEST value > x, so continue left
                if (ans == null || root.data < ans.data) {
                    ans = root;  // Update if we found a better candidate
                }
                root = root.left;  // Look for even smaller values > x
            }
        }
        
        return ans;  // Returns null if no successor exists
    }
    
    /**
     * Alternative approach: Two-step method
     * 1. If node has right subtree: find min in right subtree
     * 2. Else: traverse from root tracking last left turn
     */
    public Node inorderSuccessorTwoStep(Node root, Node x) {
        // Case 1: Node has right subtree
        if (x.right != null) {
            return findMin(x.right);
        }
        
        // Case 2: Node has no right subtree
        // Successor is the ancestor where we last took a left turn
        Node successor = null;
        Node current = root;
        
        while (current != null) {
            if (x.data < current.data) {
                // Current node is greater than x, potential successor
                successor = current;
                current = current.left;  // Go left to find smaller values > x
            } else if (x.data > current.data) {
                // Current node is smaller than x, successor not here
                current = current.right;
            } else {
                // Found x, break
                break;
            }
        }
        
        return successor;
    }
    
    /**
     * Helper: Finds minimum node in a subtree
     */
    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    /**
     * Finds inorder predecessor of node x in BST
     * Symmetric operation to successor
     */
    public Node inorderPredecessor(Node root, Node x) {
        Node predecessor = null;
        
        while (root != null) {
            if (x.data <= root.data) {
                // Current node >= x, predecessor must be in left subtree
                root = root.left;
            } else {
                // Current node < x, potential predecessor
                if (predecessor == null || root.data > predecessor.data) {
                    predecessor = root;
                }
                root = root.right;  // Look for larger values < x
            }
        }
        
        return predecessor;
    }
    
    /**
     * Alternative: Find successor when given only value (not Node object)
     */
    public Node inorderSuccessorByValue(Node root, int x) {
        Node ans = null;
        Node current = root;
        
        // First, find the node with value x (or where it would be)
        while (current != null && current.data != x) {
            if (x < current.data) {
                ans = current;  // Potential successor (we're going left)
                current = current.left;
            } else {
                current = current.right;
            }
        }
        
        if (current == null) {
            return null;  // Node with value x not found
        }
        
        // If node has right subtree, find min in right subtree
        if (current.right != null) {
            return findMin(current.right);
        }
        
        // Otherwise return the ans we tracked
        return ans;
    }
    
    /**
     * Test cases and examples
     */
    public static void main(String[] args) {
        InorderSuccessor finder = new InorderSuccessor();
        
        // Build BST:
        //       20
        //      /  \
        //     8    22
        //    / \
        //   4   12
        //      /  \
        //     10   14
        Node root = new Node(20);
        root.left = new Node(8);
        root.right = new Node(22);
        root.left.left = new Node(4);
        root.left.right = new Node(12);
        root.left.right.left = new Node(10);
        root.left.right.right = new Node(14);
        
        System.out.println("BST structure:");
        System.out.println("       20");
        System.out.println("      /  \\");
        System.out.println("     8    22");
        System.out.println("    / \\");
        System.out.println("   4   12");
        System.out.println("      /  \\");
        System.out.println("     10   14");
        System.out.println();
        
        // Test cases
        System.out.println("Test 1: Successor of 8 (has right subtree)");
        System.out.println("Expected: 10 (min in right subtree)");
        Node node8 = root.left;
        Node succ1 = finder.inorderSuccessor(root, node8);
        System.out.println("Result: " + (succ1 != null ? succ1.data : "null"));
        System.out.println();
        
        System.out.println("Test 2: Successor of 10 (no right subtree)");
        System.out.println("Expected: 12 (parent)");
        Node node10 = root.left.right.left;
        Node succ2 = finder.inorderSuccessor(root, node10);
        System.out.println("Result: " + (succ2 != null ? succ2.data : "null"));
        System.out.println();
        
        System.out.println("Test 3: Successor of 14 (no right subtree, deep in tree)");
        System.out.println("Expected: 20 (ancestor where we last turned left)");
        Node node14 = root.left.right.right;
        Node succ3 = finder.inorderSuccessor(root, node14);
        System.out.println("Result: " + (succ3 != null ? succ3.data : "null"));
        System.out.println();
        
        System.out.println("Test 4: Successor of 22 (largest node)");
        System.out.println("Expected: null (no successor)");
        Node node22 = root.right;
        Node succ4 = finder.inorderSuccessor(root, node22);
        System.out.println("Result: " + (succ4 != null ? succ4.data : "null"));
        System.out.println();
        
        System.out.println("Test 5: Successor of 4 (leaf with no right subtree)");
        System.out.println("Expected: 8 (parent)");
        Node node4 = root.left.left;
        Node succ5 = finder.inorderSuccessor(root, node4);
        System.out.println("Result: " + (succ5 != null ? succ5.data : "null"));
        System.out.println();
        
        // Test predecessor
        System.out.println("Test 6: Predecessor of 8");
        System.out.println("Expected: 4");
        Node pred1 = finder.inorderPredecessor(root, node8);
        System.out.println("Result: " + (pred1 != null ? pred1.data : "null"));
        System.out.println();
        
        System.out.println("Test 7: Predecessor of 10");
        System.out.println("Expected: 8");
        Node pred2 = finder.inorderPredecessor(root, node10);
        System.out.println("Result: " + (pred2 != null ? pred2.data : "null"));
        System.out.println();
        
        // Test by value (when we don't have Node reference)
        System.out.println("Test 8: Successor of value 12 (using value search)");
        Node succ8 = finder.inorderSuccessorByValue(root, 12);
        System.out.println("Expected: 14 (min in right subtree)");
        System.out.println("Result: " + (succ8 != null ? succ8.data : "null"));
        
        // Edge cases
        System.out.println("\nEdge Cases:");
        System.out.println("Empty tree successor: " + finder.inorderSuccessor(null, node8));
        
        // Single node tree
        Node single = new Node(10);
        System.out.println("Single node successor: " + finder.inorderSuccessor(single, single));
    }
    
    /**
     * Utility: Print inorder traversal to verify BST structure
     */
    private static void printInorder(Node root) {
        if (root == null) return;
        printInorder(root.left);
        System.out.print(root.data + " ");
        printInorder(root.right);
    }
}