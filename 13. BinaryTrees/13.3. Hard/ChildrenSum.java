/** 
 * Children Sum Property Check
 * 
 * PROBLEM: Check if binary tree satisfies children sum property:
 * For every node, node's value = sum of its children's values.
 * Leaf nodes automatically satisfy (no children).
 * 
 * APPROACH: Recursive DFS checking property at each node
 * TIME: O(n) - each node visited once
 * SPACE: O(h) - recursion stack (h = height)
 */
public class ChildrenSum {
    static class Node { 
        int data; 
        Node left, right; 
        Node(int d) { data = d; } 
    }

    /**
     * Check if tree satisfies children sum property
     * @param root - root of binary tree
     * @return 1 if property holds, 0 otherwise
     * 
     * PROPERTY DEFINITION:
     * For each node: node.data = left.data + right.data
     * Where null children count as 0
     * Leaf nodes (no children) automatically satisfy
     * 
     * RECURSIVE APPROACH:
     * 1. Base case: null or leaf → return 1 (satisfies)
     * 2. Calculate sum of children's data
     * 3. Check if node.data == sum
     * 4. Recursively check left and right subtrees
     * 5. Return 1 only if current AND subtrees satisfy
     */
    public int isSumProperty(Node root) {
        // Base case 1: null node
        if (root == null) return 1;
        
        // Base case 2: leaf node (no children)
        if (root.left == null && root.right == null) return 1;
        
        // Calculate sum of children's values
        int childrenSum = 0;
        if (root.left != null) childrenSum += root.left.data;
        if (root.right != null) childrenSum += root.right.data;
        
        // Check current node property AND recursively check subtrees
        if (root.data == childrenSum && 
            isSumProperty(root.left) == 1 && 
            isSumProperty(root.right) == 1) {
            return 1;
        }
        
        return 0;
    }
    
    /**
     * Alternative: Convert tree to satisfy children sum property
     * Variation: If property not satisfied, update node values to satisfy it
     * Can only increment values (not decrement)
     */
    public void convertToChildSum(Node root) {
        if (root == null || (root.left == null && root.right == null)) return;
        
        // First convert children recursively (post-order)
        convertToChildSum(root.left);
        convertToChildSum(root.right);
        
        // Calculate children sum
        int leftData = (root.left != null) ? root.left.data : 0;
        int rightData = (root.right != null) ? root.right.data : 0;
        int childrenSum = leftData + rightData;
        
        // Update current node if needed
        if (root.data < childrenSum) {
            root.data = childrenSum;
        } else if (root.data > childrenSum) {
            // Need to propagate increment to a child
            if (root.left != null) {
                root.left.data += root.data - childrenSum;
                // Recursively update left subtree
                propagateIncrement(root.left);
            } else if (root.right != null) {
                root.right.data += root.data - childrenSum;
                propagateIncrement(root.right);
            }
        }
    }
    
    private void propagateIncrement(Node node) {
        if (node == null || (node.left == null && node.right == null)) return;
        
        int leftData = (node.left != null) ? node.left.data : 0;
        int rightData = (node.right != null) ? node.right.data : 0;
        int diff = node.data - (leftData + rightData);
        
        if (diff > 0) {
            if (node.left != null) {
                node.left.data += diff;
                propagateIncrement(node.left);
            } else if (node.right != null) {
                node.right.data += diff;
                propagateIncrement(node.right);
            }
        }
    }
    
    /**
     * Check property without recursion (iterative)
     * Using level order traversal
     */
    public int isSumPropertyIterative(Node root) {
        if (root == null) return 1;
        
        java.util.Queue<Node> queue = new java.util.LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            
            // Skip leaf nodes
            if (current.left == null && current.right == null) continue;
            
            // Calculate children sum
            int childrenSum = 0;
            if (current.left != null) {
                childrenSum += current.left.data;
                queue.offer(current.left);
            }
            if (current.right != null) {
                childrenSum += current.right.data;
                queue.offer(current.right);
            }
            
            // Check property
            if (current.data != childrenSum) {
                return 0;
            }
        }
        
        return 1;
    }

    /**
     * Visual Examples:
     * 
     * Example 1: Satisfies property
     *       10
     *      /  \
     *     8    2
     *    / \    \
     *   3   5    2
     * 
     * Check:
     * Node 10: 8+2=10 ✓
     * Node 8: 3+5=8 ✓
     * Node 2: 0+2=2 ✓ (right child only)
     * Result: 1 (satisfies)
     * 
     * Example 2: Doesn't satisfy
     *       10
     *      /  \
     *     8    2
     *    / \ 
     *   3   4
     * 
     * Node 10: 8+2=10 ✓
     * Node 8: 3+4=7 ≠ 8 ✗
     * Result: 0
     * 
     * Example 3: Single node (leaf)
     *       5
     * 
     * Leaf node → automatically satisfies
     * Result: 1
     */

    public static void main(String[] args) {
        ChildrenSum cs = new ChildrenSum();
        
        // Example 1: Tree satisfies property
        Node root1 = new Node(10);
        root1.left = new Node(8);
        root1.right = new Node(2);
        root1.left.left = new Node(3);
        root1.left.right = new Node(5);
        root1.right.right = new Node(2);
        
        System.out.println("Example 1 (satisfies): " + cs.isSumProperty(root1)); // 1
        System.out.println("Iterative check: " + cs.isSumPropertyIterative(root1)); // 1
        
        // Example 2: Tree doesn't satisfy
        Node root2 = new Node(10);
        root2.left = new Node(8);
        root2.right = new Node(2);
        root2.left.left = new Node(3);
        root2.left.right = new Node(4); // 3+4=7 ≠ 8
        
        System.out.println("\nExample 2 (doesn't satisfy): " + cs.isSumProperty(root2)); // 0
        
        // Example 3: Single node
        Node root3 = new Node(5);
        System.out.println("Example 3 (single node): " + cs.isSumProperty(root3)); // 1
        
        // Example 4: Null tree
        System.out.println("Example 4 (null): " + cs.isSumProperty(null)); // 1
        
        // Example 5: Tree with null children
        Node root5 = new Node(10);
        root5.left = new Node(10);
        root5.right = null;
        System.out.println("Example 5 (null right child): " + cs.isSumProperty(root5)); // 1
        // Node 10: left=10, right=null → sum=10 ✓
        
        // Test convertToChildSum
        System.out.println("\n=== Convert to Child Sum ===");
        Node convertRoot = new Node(50);
        convertRoot.left = new Node(7);
        convertRoot.right = new Node(2);
        convertRoot.left.left = new Node(3);
        convertRoot.left.right = new Node(5);
        convertRoot.right.left = new Node(1);
        convertRoot.right.right = new Node(30);
        
        System.out.println("Before conversion: " + cs.isSumProperty(convertRoot)); // 0
        cs.convertToChildSum(convertRoot);
        System.out.println("After conversion: " + cs.isSumProperty(convertRoot)); // 1
        
        // Print converted tree
        System.out.print("Converted tree (level order): ");
        java.util.Queue<Node> q = new java.util.LinkedList<>();
        q.offer(convertRoot);
        while (!q.isEmpty()) {
            Node node = q.poll();
            System.out.print(node.data + " ");
            if (node.left != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }
    }
}

/**
 * KEY CONCEPTS:
 * 
 * 1. Children Sum Property:
 *    - For each node: node.data = left.data + right.data
 *    - Null children count as 0
 *    - Leaf nodes (no children) automatically satisfy
 * 
 * 2. Base cases:
 *    - Null node: satisfies (empty tree)
 *    - Leaf node: satisfies (no children to check)
 * 
 * 3. Recursive check:
 *    - Check current node property
 *    - Recursively check left and right subtrees
 *    - All must satisfy for tree to satisfy
 */

/**
 * TIME & SPACE ANALYSIS:
 * 
 * Recursive:
 *   Time: O(n) - Each node visited once
 *   Space: O(h) where h = height of tree
 *     - Best case (balanced): O(log n)
 *     - Worst case (skewed): O(n)
 * 
 * Iterative (BFS):
 *   Time: O(n)
 *   Space: O(w) where w = max width of tree
 */

/**
 * ALGORITHM WALKTHROUGH:
 * 
 * Tree:     10
 *          /  \
 *         8    2
 *        / \    \
 *       3   5    2
 * 
 * isSumProperty(10):
 *   not leaf, childrenSum = 8+2=10
 *   10==10 ✓
 *   left = isSumProperty(8)
 *     not leaf, childrenSum = 3+5=8
 *     8==8 ✓
 *     left = isSumProperty(3) → leaf → 1
 *     right = isSumProperty(5) → leaf → 1
 *     return 1
 *   right = isSumProperty(2)
 *     not leaf, childrenSum = 0+2=2
 *     2==2 ✓
 *     left = isSumProperty(null) → 1
 *     right = isSumProperty(2) → leaf → 1
 *     return 1
 *   return 1
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Not handling null children correctly:
 *    - Null children should add 0 to sum
 * 
 * 2. Forgetting leaf node base case:
 *    - Leaf nodes automatically satisfy
 * 
 * 3. Not checking both subtrees recursively:
 *    - Must check left AND right, not left OR right
 * 
 * 4. Wrong operator precedence:
 *    - Should be && not & for logical AND
 * 
 * 5. Not handling empty tree (null root)
 */

/**
 * VARIATIONS:
 * 
 * 1. Convert tree to satisfy property (increment only)
 * 2. Check if property holds for specific node only
 * 3. Count nodes that violate property
 * 4. Find maximum difference between node and children sum
 * 5. Check if tree can be converted without changing leaf values
 */

/**
 * PRACTICE EXERCISES:
 * 
 * 1. Implement iterative version
 * 2. Convert tree to satisfy property
 * 3. Find node with maximum violation
 * 4. Check if property holds for all internal nodes only
 * 5. Print all violating nodes
 */

/**
 * RELATED PROBLEMS:
 * 
 * 1. Check for children sum property
 * 2. Convert binary tree to satisfy children sum
 * 3. Sum tree (different: node = sum of all descendants)
 * 4. Check if tree is sum tree
 * 5. Maximum sum of non-adjacent nodes
 */