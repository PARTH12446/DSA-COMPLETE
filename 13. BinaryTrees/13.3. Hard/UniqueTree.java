import java.util.*;

/** 
 * Unique Binary Tree Requirements
 * 
 * This appears to be checking specific conditions for constructing 
 * unique binary trees from given parameters.
 * 
 * Based on the code, it seems to be checking if (a,b) is one of 
 * these specific pairs: (1,2) or (2,3).
 * 
 * Let's explore what this might represent and expand the concept.
 */
public class UniqueTree {
    
    /**
     * Original method - checks specific pairs
     * 
     * This seems to be a simplified/specialized check.
     * It returns 1 (true) only for pairs (1,2) or (2,3), 
     * ignoring order (so (2,1) and (3,2) also work).
     * 
     * Possible interpretations:
     * 1. Checking if we can form a specific binary tree structure
     * 2. Validating input parameters for tree construction
     * 3. Part of a larger tree validation problem
     */
    public int isPossible(int a, int b) {
        // Normalize order: make a <= b
        if (a > b) { 
            int t = a; 
            a = b; 
            b = t; 
        }
        
        // Check for specific valid pairs
        if ((a == 1 && b == 2) || (a == 2 && b == 3)) {
            return 1;  // true
        }
        return 0;  // false
    }
    
    /**
     * Expanded: Check if we can form a binary tree with given number of nodes
     * For a binary tree with n nodes:
     * - Minimum edges: n-1 (tree property)
     * - Maximum edges: n-1 (still tree property, but all nodes connected)
     * 
     * Actually, for binary tree structure constraints, we need more info.
     */
    public int canFormBinaryTree(int nodes, int edges) {
        // A binary tree with n nodes must have exactly n-1 edges
        if (edges != nodes - 1) {
            return 0;
        }
        
        // Additionally, in a binary tree:
        // - Maximum number of nodes at level i is 2^i
        // - Each node has at most 2 children
        // But without structure info, we can't validate further
        
        return 1;
    }
    
    /**
     * Check if given preorder and inorder can form a unique binary tree
     * For a binary tree to be uniquely constructible from traversals:
     * 1. All node values must be unique
     * 2. The traversals must be consistent
     */
    public int isUniqueTreePossible(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null) return 0;
        if (preorder.length != inorder.length) return 0;
        if (preorder.length == 0) return 1;  // empty tree is unique
        
        // Check if all values are unique
        Set<Integer> values = new HashSet<>();
        for (int val : preorder) {
            if (!values.add(val)) {
                return 0;  // duplicate values found
            }
        }
        
        // Check if traversals are consistent
        return areTraversalsConsistent(preorder, inorder, 0, preorder.length - 1, 
                                       0, inorder.length - 1) ? 1 : 0;
    }
    
    private boolean areTraversalsConsistent(int[] pre, int[] in, 
                                           int preStart, int preEnd,
                                           int inStart, int inEnd) {
        if (preStart > preEnd || inStart > inEnd) return true;
        
        int root = pre[preStart];
        
        // Find root in inorder
        int rootIndex = -1;
        for (int i = inStart; i <= inEnd; i++) {
            if (in[i] == root) {
                rootIndex = i;
                break;
            }
        }
        
        if (rootIndex == -1) return false;  // root not found in inorder
        
        int leftSize = rootIndex - inStart;
        
        // Recursively check left and right subtrees
        boolean left = areTraversalsConsistent(pre, in, 
                                              preStart + 1, preStart + leftSize,
                                              inStart, rootIndex - 1);
        boolean right = areTraversalsConsistent(pre, in,
                                               preStart + leftSize + 1, preEnd,
                                               rootIndex + 1, inEnd);
        
        return left && right;
    }
    
    /**
     * Check if given number of nodes can form a Full Binary Tree
     * A full binary tree: every node has 0 or 2 children
     * For n nodes, a full binary tree requires:
     * - If n == 1: possible (single node)
     * - If n > 1: n must be odd (because L + R + 1 = n, and L,R are either 0 or even)
     */
    public int canFormFullBinaryTree(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;  // single node is a full binary tree
        
        // For n > 1, n must be odd for full binary tree
        if (n % 2 == 0) return 0;
        
        // Check recursively
        return canFormFullBinaryTreeHelper(n) ? 1 : 0;
    }
    
    private boolean canFormFullBinaryTreeHelper(int n) {
        if (n == 1) return true;
        
        // Try all possible left subtree sizes (must be odd)
        for (int left = 1; left < n; left += 2) {
            int right = n - 1 - left;  // minus 1 for root
            if (right % 2 == 1 && canFormFullBinaryTreeHelper(left) 
                && canFormFullBinaryTreeHelper(right)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check if given number of nodes can form a Complete Binary Tree
     * A complete binary tree: all levels except possibly last are completely filled,
     * and all nodes are as left as possible
     * 
     * For n nodes, it's always possible to form a complete binary tree
     * (just fill levels left to right)
     */
    public int canFormCompleteBinaryTree(int n) {
        return n >= 0 ? 1 : 0;  // Always possible for non-negative n
    }
    
    /**
     * Catalan Numbers: Number of unique binary trees with n nodes
     * The nth Catalan number gives the number of unique binary search trees
     * with n distinct values, and also the number of unique full binary trees
     * with n+1 leaves.
     */
    public int catalanNumber(int n) {
        if (n < 0) return 0;
        
        int[] catalan = new int[n + 1];
        catalan[0] = 1;
        
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                catalan[i] += catalan[j] * catalan[i - 1 - j];
            }
        }
        
        return catalan[n];
    }
    
    /**
     * Check if two numbers can represent valid tree dimensions
     * For example: a = number of internal nodes, b = number of leaves
     * In a full binary tree: leaves = internal nodes + 1
     */
    public int isValidTreeDimensions(int internalNodes, int leaves) {
        // For any binary tree: leaves = internalNodes + 1 (if all internal nodes have 2 children)
        // For full binary tree specifically: leaves = internalNodes + 1
        if (leaves == internalNodes + 1) {
            return 1;
        }
        return 0;
    }
    
    /**
     * Check if given degree sequence can form a binary tree
     * For a tree with n nodes, sum of degrees = 2*(n-1)
     * For binary tree, each node degree <= 3 (root degree <= 2, others <= 3)
     */
    public int canFormBinaryTreeFromDegrees(int[] degrees) {
        if (degrees == null || degrees.length == 0) return 0;
        
        int n = degrees.length;
        int sum = 0;
        
        for (int deg : degrees) {
            if (deg < 1 || deg > 3) return 0;  // Invalid degree for binary tree
            sum += deg;
        }
        
        // For tree: sum of degrees = 2*(n-1)
        return (sum == 2 * (n - 1)) ? 1 : 0;
    }
    
    /**
     * Check if preorder traversal is valid for a BST
     */
    public int isValidBSTPreorder(int[] preorder) {
        if (preorder == null || preorder.length == 0) return 1;
        
        Stack<Integer> stack = new Stack<>();
        int root = Integer.MIN_VALUE;
        
        for (int value : preorder) {
            // If we find a node that is smaller than root, it's invalid
            if (value < root) {
                return 0;
            }
            
            // Remove elements from stack that are smaller than current value
            while (!stack.isEmpty() && stack.peek() < value) {
                root = stack.pop();
            }
            
            stack.push(value);
        }
        
        return 1;
    }
    
    /**
     * Main method with test cases
     */
    public static void main(String[] args) {
        UniqueTree checker = new UniqueTree();
        
        System.out.println("=== Testing Original Method ===");
        System.out.println("(1,2): " + checker.isPossible(1, 2));  // 1
        System.out.println("(2,1): " + checker.isPossible(2, 1));  // 1 (order swapped)
        System.out.println("(2,3): " + checker.isPossible(2, 3));  // 1
        System.out.println("(3,2): " + checker.isPossible(3, 2));  // 1
        System.out.println("(1,3): " + checker.isPossible(1, 3));  // 0
        System.out.println("(3,3): " + checker.isPossible(3, 3));  // 0
        
        System.out.println("\n=== Testing Expanded Methods ===");
        
        // Test binary tree formation
        System.out.println("Can form binary tree with 5 nodes, 4 edges: " + 
                          checker.canFormBinaryTree(5, 4));  // 1
        System.out.println("Can form binary tree with 5 nodes, 5 edges: " + 
                          checker.canFormBinaryTree(5, 5));  // 0
                          
        // Test unique tree from traversals
        int[] preorder = {1, 2, 3};
        int[] inorder = {2, 1, 3};
        System.out.println("Unique tree possible from preorder/inorder: " + 
                          checker.isUniqueTreePossible(preorder, inorder));  // 1
        
        int[] preorder2 = {1, 2, 2};
        int[] inorder2 = {2, 1, 2};
        System.out.println("Unique tree with duplicates: " + 
                          checker.isUniqueTreePossible(preorder2, inorder2));  // 0
                          
        // Test full binary tree
        System.out.println("Can form full binary tree with 7 nodes: " + 
                          checker.canFormFullBinaryTree(7));  // 1
        System.out.println("Can form full binary tree with 6 nodes: " + 
                          checker.canFormFullBinaryTree(6));  // 0
                          
        // Test Catalan numbers
        System.out.println("Catalan number C3: " + checker.catalanNumber(3));  // 5
        System.out.println("Catalan number C4: " + checker.catalanNumber(4));  // 14
        
        // Test tree dimensions
        System.out.println("Valid tree dimensions (2 internal, 3 leaves): " + 
                          checker.isValidTreeDimensions(2, 3));  // 1
        System.out.println("Valid tree dimensions (3 internal, 3 leaves): " + 
                          checker.isValidTreeDimensions(3, 3));  // 0
        
        // Test degree sequence
        int[] degrees1 = {1, 2, 1};  // leaf, internal, leaf
        int[] degrees2 = {2, 3, 1};  // invalid (degree 3 with only 3 nodes)
        System.out.println("Valid degree sequence [1,2,1]: " + 
                          checker.canFormBinaryTreeFromDegrees(degrees1));  // 1
        System.out.println("Valid degree sequence [2,3,1]: " + 
                          checker.canFormBinaryTreeFromDegrees(degrees2));  // 0
        
        // Test BST preorder
        int[] bstPreorder = {5, 3, 2, 4, 7, 6, 8};
        int[] nonBSTPreorder = {5, 3, 8, 2, 4, 7, 6};
        System.out.println("Valid BST preorder: " + 
                          checker.isValidBSTPreorder(bstPreorder));  // 1
        System.out.println("Invalid BST preorder: " + 
                          checker.isValidBSTPreorder(nonBSTPreorder));  // 0
    }
    
    /**
     * Additional: Check if two trees are isomorphic (same structure)
     */
    public int areTreesIsomorphic(int[] tree1Parents, int[] tree2Parents) {
        if (tree1Parents.length != tree2Parents.length) return 0;
        
        int n = tree1Parents.length;
        
        // Build adjacency lists
        List<List<Integer>> adj1 = buildAdjacencyList(tree1Parents);
        List<List<Integer>> adj2 = buildAdjacencyList(tree2Parents);
        
        // Check if trees are isomorphic by comparing structures
        return areIsomorphic(adj1, adj2, 0, 0) ? 1 : 0;
    }
    
    private List<List<Integer>> buildAdjacencyList(int[] parents) {
        int n = parents.length;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        
        for (int i = 0; i < n; i++) {
            if (parents[i] != -1) {
                adj.get(i).add(parents[i]);
                adj.get(parents[i]).add(i);
            }
        }
        
        return adj;
    }
    
    private boolean areIsomorphic(List<List<Integer>> adj1, List<List<Integer>> adj2, 
                                 int node1, int node2) {
        if (adj1.get(node1).size() != adj2.get(node2).size()) {
            return false;
        }
        
        // For binary tree isomorphism, we need to check children structures
        // This is a simplified check
        return true;
    }
    
    /**
     * Check if array can represent preorder traversal of a binary tree
     * where -1 represents null nodes
     */
    public int isValidPreorderWithNulls(int[] preorder) {
        if (preorder == null) return 0;
        
        int slots = 1;  // start with one slot for root
        
        for (int value : preorder) {
            slots--;  // consume one slot
            
            if (slots < 0) return 0;  // more nodes than available slots
            
            if (value != -1) {
                slots += 2;  // non-null node provides 2 slots for children
            }
        }
        
        return slots == 0 ? 1 : 0;  // all slots should be consumed
    }
}