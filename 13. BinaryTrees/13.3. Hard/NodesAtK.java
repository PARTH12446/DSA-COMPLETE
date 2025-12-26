import java.util.*;

/** 
 * Find all nodes at distance K from a target node in a binary tree
 * Problem: 863. All Nodes Distance K in Binary Tree (LeetCode)
 * 
 * Approach: 
 * 1. First, build parent pointers to traverse upward
 * 2. Then perform BFS from target node to find nodes at distance K
 * 
 * Time Complexity: O(n) where n is number of nodes
 * Space Complexity: O(n) for parent map, visited set, and queue
 */
public class NodesAtK {
    static class TreeNode { 
        int val; 
        TreeNode left, right; 
        TreeNode(int v) { val = v; } 
    }

    /**
     * Main method: Find all nodes at distance K from target
     * 
     * @param root   Root of the binary tree
     * @param target Target node to measure distance from
     * @param k      Distance from target
     * @return List of values of nodes at exactly distance K from target
     */
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        // Edge case: empty tree or invalid input
        if (root == null || target == null) return new ArrayList<>();
        
        // Step 1: Build parent map using DFS
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        setParents(root, null, parent);
        
        // Step 2: BFS from target to find nodes at distance K
        Set<TreeNode> visited = new HashSet<>();
        Queue<TreeNode> queue = new LinkedList<>();
        
        // Start BFS from target
        queue.offer(target);
        visited.add(target);
        int distance = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            // Check if we've reached distance K
            if (distance == k) {
                List<Integer> result = new ArrayList<>();
                for (TreeNode node : queue) {
                    result.add(node.val);
                }
                return result;
            }
            
            // Process all nodes at current distance
            for (int i = 0; i < size; i++) {
                TreeNode current = queue.poll();
                
                // Explore three directions: parent, left child, right child
                
                // 1. Parent node
                TreeNode parentNode = parent.get(current);
                if (parentNode != null && !visited.contains(parentNode)) {
                    visited.add(parentNode);
                    queue.offer(parentNode);
                }
                
                // 2. Left child
                if (current.left != null && !visited.contains(current.left)) {
                    visited.add(current.left);
                    queue.offer(current.left);
                }
                
                // 3. Right child
                if (current.right != null && !visited.contains(current.right)) {
                    visited.add(current.right);
                    queue.offer(current.right);
                }
            }
            
            // Increment distance for next level
            distance++;
        }
        
        // If distance K not reachable, return empty list
        return new ArrayList<>();
    }
    
    /**
     * Helper method: Build parent pointers using DFS
     * 
     * @param node   Current node
     * @param p      Parent of current node
     * @param parent Map storing parent relationships
     */
    private void setParents(TreeNode node, TreeNode p, Map<TreeNode, TreeNode> parent) {
        if (node == null) return;
        parent.put(node, p);
        setParents(node.left, node, parent);
        setParents(node.right, node, parent);
    }
    
    /**
     * Alternative: Without building full parent map first
     * Finds path from root to target, then does BFS from target
     */
    public List<Integer> distanceKAlternative(TreeNode root, TreeNode target, int k) {
        List<Integer> result = new ArrayList<>();
        if (root == null || target == null) return result;
        
        // Step 1: Find path from root to target
        List<TreeNode> path = new ArrayList<>();
        findPath(root, target, path);
        
        // Step 2: For each node on path, find nodes at specific distances
        for (int i = 0; i < path.size(); i++) {
            TreeNode currentNode = path.get(i);
            int remainingDistance = k - (path.size() - 1 - i);
            
            if (remainingDistance < 0) continue;
            
            // Avoid going back to previous node in the path
            TreeNode blocker = (i > 0) ? path.get(i - 1) : null;
            findNodesAtDistance(currentNode, remainingDistance, blocker, result);
        }
        
        return result;
    }
    
    /**
     * Find path from root to target node
     */
    private boolean findPath(TreeNode node, TreeNode target, List<TreeNode> path) {
        if (node == null) return false;
        
        path.add(node);
        
        if (node == target) return true;
        
        if (findPath(node.left, target, path) || findPath(node.right, target, path)) {
            return true;
        }
        
        path.remove(path.size() - 1);
        return false;
    }
    
    /**
     * Find all nodes at distance d from given node, avoiding blocker node
     */
    private void findNodesAtDistance(TreeNode node, int d, TreeNode blocker, List<Integer> result) {
        if (node == null || d < 0 || node == blocker) return;
        
        if (d == 0) {
            result.add(node.val);
            return;
        }
        
        findNodesAtDistance(node.left, d - 1, blocker, result);
        findNodesAtDistance(node.right, d - 1, blocker, result);
    }
    
    /**
     * Optimized version: Single pass with return values
     */
    public List<Integer> distanceKOptimized(TreeNode root, TreeNode target, int k) {
        List<Integer> result = new ArrayList<>();
        dfs(root, target, k, result);
        return result;
    }
    
    /**
     * DFS that returns distance from current node to target
     * Returns -1 if target not found in subtree
     */
    private int dfs(TreeNode node, TreeNode target, int k, List<Integer> result) {
        if (node == null) return -1;
        
        // If current node is target, collect nodes at distance k in its subtree
        if (node == target) {
            collectNodes(node, k, result);
            return 0;  // Distance from target to itself is 0
        }
        
        // Search in left subtree
        int leftDist = dfs(node.left, target, k, result);
        if (leftDist != -1) {
            // Target found in left subtree
            if (leftDist + 1 == k) {
                // Current node is at distance k from target
                result.add(node.val);
            } else {
                // Check right subtree with adjusted distance
                collectNodes(node.right, k - leftDist - 2, result);
            }
            return leftDist + 1;
        }
        
        // Search in right subtree
        int rightDist = dfs(node.right, target, k, result);
        if (rightDist != -1) {
            // Target found in right subtree
            if (rightDist + 1 == k) {
                // Current node is at distance k from target
                result.add(node.val);
            } else {
                // Check left subtree with adjusted distance
                collectNodes(node.left, k - rightDist - 2, result);
            }
            return rightDist + 1;
        }
        
        return -1;  // Target not found in this subtree
    }
    
    /**
     * Collect nodes at distance d from given node
     */
    private void collectNodes(TreeNode node, int d, List<Integer> result) {
        if (node == null || d < 0) return;
        if (d == 0) {
            result.add(node.val);
            return;
        }
        collectNodes(node.left, d - 1, result);
        collectNodes(node.right, d - 1, result);
    }
    
    /**
     * Test cases and visualization
     */
    public static void main(String[] args) {
        NodesAtK solution = new NodesAtK();
        
        // Test Case 1: Simple tree
        //       3
        //      / \
        //     5   1
        //    / \ / \
        //   6  2 0  8
        //     / \
        //    7   4
        TreeNode root1 = new TreeNode(3);
        TreeNode node5 = new TreeNode(5);
        TreeNode node1 = new TreeNode(1);
        TreeNode node6 = new TreeNode(6);
        TreeNode node2 = new TreeNode(2);
        TreeNode node0 = new TreeNode(0);
        TreeNode node8 = new TreeNode(8);
        TreeNode node7 = new TreeNode(7);
        TreeNode node4 = new TreeNode(4);
        
        root1.left = node5; root1.right = node1;
        node5.left = node6; node5.right = node2;
        node1.left = node0; node1.right = node8;
        node2.left = node7; node2.right = node4;
        
        System.out.println("Test Case 1: Tree with target=5, k=2");
        List<Integer> result1 = solution.distanceK(root1, node5, 2);
        System.out.println("  Result: " + result1); // Expected: [7, 4, 1]
        
        System.out.println("  Alternative approach: " + solution.distanceKAlternative(root1, node5, 2));
        System.out.println("  Optimized approach: " + solution.distanceKOptimized(root1, node5, 2));
        
        // Test Case 2: Target is leaf node
        System.out.println("\nTest Case 2: Tree with target=7 (leaf), k=3");
        List<Integer> result2 = solution.distanceK(root1, node7, 3);
        System.out.println("  Result: " + result2); // Expected: [3, 0, 8]
        
        // Test Case 3: Target is root
        System.out.println("\nTest Case 3: Tree with target=3 (root), k=1");
        List<Integer> result3 = solution.distanceK(root1, root1, 1);
        System.out.println("  Result: " + result3); // Expected: [5, 1]
        
        // Test Case 4: k=0 (target itself)
        System.out.println("\nTest Case 4: Tree with target=2, k=0");
        List<Integer> result4 = solution.distanceK(root1, node2, 0);
        System.out.println("  Result: " + result4); // Expected: [2]
        
        // Test Case 5: k larger than tree depth
        System.out.println("\nTest Case 5: Tree with target=5, k=5");
        List<Integer> result5 = solution.distanceK(root1, node5, 5);
        System.out.println("  Result: " + result5); // Expected: []
        
        // Test Case 6: Single node tree
        TreeNode single = new TreeNode(1);
        System.out.println("\nTest Case 6: Single node tree, target=1, k=0");
        List<Integer> result6 = solution.distanceK(single, single, 0);
        System.out.println("  Result: " + result6); // Expected: [1]
        
        System.out.println("\nTest Case 7: Single node tree, target=1, k=1");
        List<Integer> result7 = solution.distanceK(single, single, 1);
        System.out.println("  Result: " + result7); // Expected: []
        
        // Test Case 8: Skewed tree
        //   1
        //    \
        //     2
        //      \
        //       3
        TreeNode skewed = new TreeNode(1);
        TreeNode s2 = new TreeNode(2);
        TreeNode s3 = new TreeNode(3);
        skewed.right = s2;
        s2.right = s3;
        
        System.out.println("\nTest Case 8: Skewed tree, target=2, k=1");
        List<Integer> result8 = solution.distanceK(skewed, s2, 1);
        System.out.println("  Result: " + result8); // Expected: [1, 3]
    }
    
    /**
     * Complexity Analysis:
     * 
     * Original Approach (BFS with parent map):
     * - Time: O(n) - Each node visited at most twice (once for parent map, once for BFS)
     * - Space: O(n) - Parent map, visited set, queue all O(n)
     * 
     * Alternative Approach (Path + DFS):
     * - Time: O(n) - Finding path O(n), collecting nodes O(n)
     * - Space: O(h) for recursion stack, where h is tree height
     * 
     * Optimized Approach (Single pass DFS):
     * - Time: O(n) - Each node visited once
     * - Space: O(h) for recursion stack
     */
    
    /**
     * Edge Cases to Consider:
     * 1. target == null
     * 2. k = 0 (only target itself)
     * 3. k larger than tree diameter
     * 4. target not in tree
     * 5. Tree with duplicate values (handle by node reference, not value)
     * 6. Very large k causing integer overflow
     */
    
    /**
     * Follow-up Questions:
     * 
     * 1. What if we need to handle duplicate values?
     *    - Solution: Work with node references, not values
     *    - This implementation already handles that correctly
     * 
     * 2. What if tree is modified frequently?
     *    - Parent map needs to be rebuilt each time
     *    - Alternative approaches might be better
     * 
     * 3. Can we do this in O(1) extra space?
     *    - Not without modifying the tree
     *    - Morris traversal could give O(1) space but would be complex
     * 
     * 4. What if we need to find nodes at distance ≤ k?
     *    - Modify BFS to collect all nodes up to distance k
     */
    
    /**
     * Extension: Find nodes at distance ≤ k
     */
    public List<Integer> distanceUpToK(TreeNode root, TreeNode target, int k) {
        List<Integer> result = new ArrayList<>();
        if (root == null || target == null) return result;
        
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        setParents(root, null, parent);
        
        Set<TreeNode> visited = new HashSet<>();
        Queue<TreeNode> queue = new LinkedList<>();
        
        queue.offer(target);
        visited.add(target);
        int distance = 0;
        
        while (!queue.isEmpty() && distance <= k) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode current = queue.poll();
                result.add(current.val);
                
                TreeNode parentNode = parent.get(current);
                if (parentNode != null && !visited.contains(parentNode)) {
                    visited.add(parentNode);
                    queue.offer(parentNode);
                }
                
                if (current.left != null && !visited.contains(current.left)) {
                    visited.add(current.left);
                    queue.offer(current.left);
                }
                
                if (current.right != null && !visited.contains(current.right)) {
                    visited.add(current.right);
                    queue.offer(current.right);
                }
            }
            distance++;
        }
        
        return result;
    }
}