import java.util.*;

/**
 * Two Sum IV - Input is a Binary Search Tree.
 * 
 * Problem: Given a BST and a target value k, determine if there exist 
 * two different nodes such that their sum equals k.
 * 
 * Approaches:
 * 1. BFS + HashSet (current implementation) - O(n) time, O(n) space
 * 2. Inorder traversal + Two pointers - O(n) time, O(n) space
 * 3. DFS + HashSet - O(n) time, O(n) space
 * 4. BST property optimization - O(nh) time, O(h) space
 * 
 * Key Insight: 
 * - Similar to Two Sum in array, but data structure is a tree
 * - Can leverage BST properties for more efficient search
 * - Must ensure two DIFFERENT nodes (can't use same node twice)
 */
public class TwoSumBST {
    static class TreeNode { 
        int val; 
        TreeNode left, right; 
        TreeNode(int v) {
            val = v;
        }
    }

    /**
     * Solution 1: BFS (Level-order traversal) + HashSet
     * 
     * Traverse tree level by level, using HashSet to store visited values.
     * For each node, check if (k - node.val) exists in HashSet.
     * 
     * Time Complexity: O(n) - each node visited once
     * Space Complexity: O(n) - HashSet stores all values
     * 
     * Works for any binary tree, not just BST.
     */
    public boolean findTarget(TreeNode root, int k) {
        if (root == null) return false;
        
        List<TreeNode> bfs = new ArrayList<>();  // Queue for BFS
        bfs.add(root);
        Set<Integer> set = new HashSet<>();      // Store visited values
        
        for (int i = 0; i < bfs.size(); i++) {
            TreeNode node = bfs.get(i);
            
            // Check if complement exists
            if (set.contains(k - node.val)) {
                return true;
            }
            
            // Add current value to set
            set.add(node.val);
            
            // Add children to BFS queue
            if (node.left != null) bfs.add(node.left);
            if (node.right != null) bfs.add(node.right);
        }
        
        return false;
    }
    
    /**
     * Solution 2: Inorder Traversal + Two Pointers
     * 
     * 1. Get inorder traversal (sorted array) from BST
     * 2. Use two-pointer technique to find if sum exists
     * 
     * Time Complexity: O(n) for traversal + O(n) for two-pointer = O(n)
     * Space Complexity: O(n) for storing inorder traversal
     */
    public boolean findTargetInorder(TreeNode root, int k) {
        List<Integer> inorder = new ArrayList<>();
        inorderTraversal(root, inorder);
        
        // Two-pointer approach on sorted list
        int left = 0, right = inorder.size() - 1;
        while (left < right) {
            int sum = inorder.get(left) + inorder.get(right);
            if (sum == k) {
                return true;
            } else if (sum < k) {
                left++;
            } else {
                right--;
            }
        }
        return false;
    }
    
    private void inorderTraversal(TreeNode root, List<Integer> list) {
        if (root == null) return;
        inorderTraversal(root.left, list);
        list.add(root.val);
        inorderTraversal(root.right, list);
    }
    
    /**
     * Solution 3: DFS + HashSet
     * 
     * Similar to BFS approach but uses DFS (recursive) traversal.
     */
    public boolean findTargetDFS(TreeNode root, int k) {
        Set<Integer> set = new HashSet<>();
        return dfs(root, k, set);
    }
    
    private boolean dfs(TreeNode node, int k, Set<Integer> set) {
        if (node == null) return false;
        
        // Check complement before adding current node
        if (set.contains(k - node.val)) {
            return true;
        }
        
        set.add(node.val);
        
        // Search in both subtrees
        return dfs(node.left, k, set) || dfs(node.right, k, set);
    }
    
    /**
     * Solution 4: BST Property Optimization
     * 
     * For each node, search for complement (k - node.val) in the BST.
     * Leverages BST search property for O(h) search per node.
     * 
     * Time Complexity: O(nh) where h is tree height
     * - O(n log n) for balanced BST
     * - O(n²) for skewed BST
     * Space Complexity: O(h) for recursion stack
     */
    public boolean findTargetBST(TreeNode root, int k) {
        return findTargetBST(root, root, k);
    }
    
    private boolean findTargetBST(TreeNode current, TreeNode root, int k) {
        if (current == null) return false;
        
        // Search for complement (k - current.val) in the BST
        // Must ensure it's not the same node
        TreeNode complement = searchBST(root, k - current.val);
        if (complement != null && complement != current) {
            return true;
        }
        
        // Recursively check left and right subtrees
        return findTargetBST(current.left, root, k) || 
               findTargetBST(current.right, root, k);
    }
    
    private TreeNode searchBST(TreeNode root, int target) {
        while (root != null) {
            if (root.val == target) {
                return root;
            } else if (target < root.val) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return null;
    }
    
    /**
     * Solution 5: Two Stacks (Forward and Backward BST Iterators)
     * 
     * Use two BST iterators: one forward (inorder), one backward (reverse inorder)
     * Similar to two-pointer but doesn't require storing entire traversal.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(h)
     */
    public boolean findTargetTwoStacks(TreeNode root, int k) {
        Stack<TreeNode> leftStack = new Stack<>();
        Stack<TreeNode> rightStack = new Stack<>();
        
        // Initialize left stack with leftmost nodes
        TreeNode curr = root;
        while (curr != null) {
            leftStack.push(curr);
            curr = curr.left;
        }
        
        // Initialize right stack with rightmost nodes
        curr = root;
        while (curr != null) {
            rightStack.push(curr);
            curr = curr.right;
        }
        
        while (!leftStack.isEmpty() && !rightStack.isEmpty()) {
            TreeNode left = leftStack.peek();
            TreeNode right = rightStack.peek();
            
            // Can't use same node
            if (left == right) break;
            
            int sum = left.val + right.val;
            if (sum == k) {
                return true;
            } else if (sum < k) {
                // Move left pointer forward
                leftStack.pop();
                curr = left.right;
                while (curr != null) {
                    leftStack.push(curr);
                    curr = curr.left;
                }
            } else {
                // Move right pointer backward
                rightStack.pop();
                curr = right.left;
                while (curr != null) {
                    rightStack.push(curr);
                    curr = curr.right;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Solution 6: BFS with Early Exit Optimization
     * 
     * Stop BFS early if we find a complement.
     * Uses Queue instead of ArrayList for cleaner BFS.
     */
    public boolean findTargetBFSQueue(TreeNode root, int k) {
        if (root == null) return false;
        
        Queue<TreeNode> queue = new LinkedList<>();
        Set<Integer> set = new HashSet<>();
        
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            
            if (set.contains(k - node.val)) {
                return true;
            }
            
            set.add(node.val);
            
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        
        return false;
    }
    
    /**
     * Test cases and examples
     */
    public static void main(String[] args) {
        TwoSumBST twoSum = new TwoSumBST();
        
        // Build BST:
        //       5
        //      / \
        //     3   6
        //    / \   \
        //   2   4   7
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(7);
        
        System.out.println("BST structure:");
        System.out.println("       5");
        System.out.println("      / \\");
        System.out.println("     3   6");
        System.out.println("    / \\   \\");
        System.out.println("   2   4   7");
        
        // Test cases
        System.out.println("\n=== Testing Two Sum in BST ===");
        
        int[] targets = {9, 12, 5, 10, 13};
        boolean[] expected = {true, true, false, true, false};
        
        for (int i = 0; i < targets.length; i++) {
            int target = targets[i];
            boolean result = twoSum.findTarget(root, target);
            System.out.printf("Target %d: Expected %b, Got %b %s%n",
                target, expected[i], result,
                result == expected[i] ? "✓" : "✗");
        }
        
        // Compare different approaches
        System.out.println("\n=== Comparing Different Approaches ===");
        int testTarget = 9;
        
        System.out.println("Target = " + testTarget + ":");
        System.out.println("1. BFS + HashSet:     " + twoSum.findTarget(root, testTarget));
        System.out.println("2. Inorder + 2ptr:    " + twoSum.findTargetInorder(root, testTarget));
        System.out.println("3. DFS + HashSet:     " + twoSum.findTargetDFS(root, testTarget));
        System.out.println("4. BST Search:        " + twoSum.findTargetBST(root, testTarget));
        System.out.println("5. Two Stacks:        " + twoSum.findTargetTwoStacks(root, testTarget));
        System.out.println("6. BFS Queue:         " + twoSum.findTargetBFSQueue(root, testTarget));
        
        // Edge cases
        System.out.println("\n=== Testing Edge Cases ===");
        
        // Empty tree
        System.out.println("Empty tree, target 0: " + twoSum.findTarget(null, 0));
        
        // Single node
        TreeNode single = new TreeNode(1);
        System.out.println("Single node 1, target 2: " + twoSum.findTarget(single, 2));
        System.out.println("Single node 1, target 1: " + twoSum.findTarget(single, 1));
        
        // Negative values
        TreeNode negRoot = new TreeNode(-1);
        negRoot.left = new TreeNode(-2);
        negRoot.right = new TreeNode(-3);
        System.out.println("Negative values [-1,-2,-3], target -4: " + 
            twoSum.findTarget(negRoot, -4));
        
        // Same value nodes (valid if different nodes)
        TreeNode sameRoot = new TreeNode(2);
        sameRoot.left = new TreeNode(1);
        sameRoot.right = new TreeNode(3);
        sameRoot.left.right = new TreeNode(2);  // Another 2
        System.out.println("Duplicate values, target 4: " + 
            twoSum.findTarget(sameRoot, 4));
        
        // Large tree test
        System.out.println("\n=== Performance Test Idea ===");
        System.out.println("For large BSTs:");
        System.out.println("- Two-pointer approach after inorder is O(n) time, O(n) space");
        System.out.println("- Two-stacks approach is O(n) time, O(h) space (better for memory)");
        System.out.println("- BFS+HashSet is simplest but uses O(n) space");
        
        // Real-world example
        System.out.println("\n=== Real-world Example ===");
        System.out.println("In a price database stored as BST, find if two products");
        System.out.println("exist with combined price equal to a gift card amount.");
    }
    
    /**
     * Utility: Generate random BST for testing
     */
    private static TreeNode generateRandomBST(int size, Random random) {
        if (size == 0) return null;
        
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            values.add(random.nextInt(100));
        }
        
        TreeNode root = new TreeNode(values.get(0));
        for (int i = 1; i < values.size(); i++) {
            insertBST(root, values.get(i));
        }
        
        return root;
    }
    
    private static void insertBST(TreeNode root, int val) {
        TreeNode parent = null;
        TreeNode current = root;
        
        while (current != null) {
            parent = current;
            if (val < current.val) {
                current = current.left;
            } else if (val > current.val) {
                current = current.right;
            } else {
                return;  // No duplicates
            }
        }
        
        if (val < parent.val) {
            parent.left = new TreeNode(val);
        } else {
            parent.right = new TreeNode(val);
        }
    }
}