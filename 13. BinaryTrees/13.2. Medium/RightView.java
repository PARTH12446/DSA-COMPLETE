import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Right Side View of Binary Tree (LeetCode 199)
 * 
 * PROBLEM: Return the values of nodes you can see when looking at tree from right side
 * 
 * APPROACH: BFS Level Order Traversal, take last node at each level
 * TIME: O(n) - each node visited once
 * SPACE: O(w) - queue width (max nodes at any level)
 */
public class RightView {

    static class TreeNode {
        int val; 
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    /**
     * Right Side View using BFS (Level Order)
     * @param root - root of binary tree
     * @return List of rightmost nodes from top to bottom
     * 
     * STRATEGY:
     * 1. Use BFS to traverse level by level
     * 2. For each level, track the last node
     * 3. Add last node's value to result
     * 4. This gives rightmost visible node at each depth
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            int lastValue = 0;
            
            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                lastValue = node.val; // Track last node in this level
                
                // Add children for next level (left then right)
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            // Last node in level is rightmost visible node
            result.add(lastValue);
        }
        
        return result;
    }
    
    /**
     * Alternative: DFS approach (preorder with right-first)
     * More space efficient for deep trees
     */
    public List<Integer> rightSideViewDFS(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfs(root, 0, result);
        return result;
    }
    
    private void dfs(TreeNode node, int depth, List<Integer> result) {
        if (node == null) return;
        
        // If this is first node at this depth, add to result
        // Since we traverse right first, this will be rightmost node
        if (depth == result.size()) {
            result.add(node.val);
        }
        
        // Traverse right first, then left
        dfs(node.right, depth + 1, result);
        dfs(node.left, depth + 1, result);
    }
    
    /**
     * Left Side View (variation)
     * Same as right side view but take first node at each level
     */
    public List<Integer> leftSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            
            // First node in level is leftmost visible node
            result.add(queue.peek().val);
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                // Add children (left then right) for left side view
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        
        return result;
    }
    
    /**
     * Using BFS with level tracking (explicit level list)
     */
    public List<Integer> rightSideViewLevelList(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<TreeNode> level = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                level.add(node);
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            // Last node in level list is rightmost
            result.add(level.get(level.size() - 1).val);
        }
        
        return result;
    }

    /**
     * Visual Examples:
     * 
     * Example 1:
     *       1
     *      / \
     *     2   3
     *      \   \
     *       5   4
     * 
     * Right side view: [1, 3, 4]
     * Explanation:
     * Level 0: 1 (rightmost at depth 0)
     * Level 1: 3 (rightmost at depth 1)
     * Level 2: 4 (rightmost at depth 2)
     * 
     * BFS levels: [[1], [2,3], [5,4]]
     * Take last from each: 1, 3, 4
     * 
     * Example 2:
     *       1
     *        \
     *         3
     * 
     * Right side view: [1, 3]
     * 
     * Example 3:
     *       1
     *      / \
     *     2   3
     *    /
     *   4
     * 
     * Right side view: [1, 3, 4]
     * BFS levels: [[1], [2,3], [4]]
     */

    public static void main(String[] args) {
        RightView r = new RightView();
        
        // Example from problem
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(4);
        
        System.out.println("Right Side View (BFS): " + r.rightSideView(root)); // [1, 3, 4]
        System.out.println("Right Side View (DFS): " + r.rightSideViewDFS(root)); // [1, 3, 4]
        System.out.println("Left Side View: " + r.leftSideView(root)); // [1, 2, 5]
        
        // Test cases
        System.out.println("\n=== Test Cases ===");
        
        // Test 1: Empty tree
        System.out.println("Empty tree: " + r.rightSideView(null)); // []
        
        // Test 2: Single node
        TreeNode single = new TreeNode(1);
        System.out.println("Single node: " + r.rightSideView(single)); // [1]
        
        // Test 3: Left-skewed tree
        TreeNode leftSkewed = new TreeNode(1);
        leftSkewed.left = new TreeNode(2);
        leftSkewed.left.left = new TreeNode(3);
        System.out.println("Left-skewed: " + r.rightSideView(leftSkewed)); // [1, 2, 3]
        
        // Test 4: Right-skewed tree
        TreeNode rightSkewed = new TreeNode(1);
        rightSkewed.right = new TreeNode(2);
        rightSkewed.right.right = new TreeNode(3);
        System.out.println("Right-skewed: " + r.rightSideView(rightSkewed)); // [1, 2, 3]
        
        // Test 5: Perfect binary tree
        TreeNode perfect = new TreeNode(1);
        perfect.left = new TreeNode(2);
        perfect.right = new TreeNode(3);
        perfect.left.left = new TreeNode(4);
        perfect.left.right = new TreeNode(5);
        perfect.right.left = new TreeNode(6);
        perfect.right.right = new TreeNode(7);
        System.out.println("Perfect binary: " + r.rightSideView(perfect)); // [1, 3, 7]
        
        // Test 6: Complex tree
        TreeNode complex = new TreeNode(1);
        complex.left = new TreeNode(2);
        complex.right = new TreeNode(3);
        complex.left.left = new TreeNode(4);
        complex.left.right = new TreeNode(5);
        complex.right.left = new TreeNode(6);
        complex.right.right = new TreeNode(7);
        complex.left.right.left = new TreeNode(8);
        complex.right.left.right = new TreeNode(9);
        System.out.println("Complex tree: " + r.rightSideView(complex)); // [1, 3, 7, 9]
    }
}

/**
 * KEY CONCEPTS:
 * 
 * 1. Right Side View:
 *    - Rightmost node at each depth/level
 *    - Looking from right side, these nodes are visible
 *    - Each level contributes exactly one node
 * 
 * 2. Left Side View:
 *    - Leftmost node at each depth/level
 *    - Looking from left side, these nodes are visible
 * 
 * 3. BFS Approach:
 *    - Level order traversal
 *    - Take last node from each level for right view
 *    - Take first node from each level for left view
 * 
 * 4. DFS Approach (right side view):
 *    - Traverse right subtree first (right → left)
 *    - First node encountered at each depth is rightmost
 *    - More space efficient for deep trees
 */

/**
 * TIME & SPACE ANALYSIS:
 * 
 * BFS:
 *   Time: O(n) - Each node visited once
 *   Space: O(w) where w = max width of tree
 *     - Best case (skewed): O(1)
 *     - Worst case (complete): O(n/2) ≈ O(n)
 * 
 * DFS:
 *   Time: O(n) - Each node visited once
 *   Space: O(h) where h = height of tree
 *     - Best case (balanced): O(log n)
 *     - Worst case (skewed): O(n)
 *     - Recursion stack
 */

/**
 * ALGORITHM WALKTHROUGH (BFS):
 * 
 * Tree:       1
 *           /   \
 *          2     3
 *           \     \
 *            5     4
 * 
 * Queue: [1]
 * Level 0: size=1, last=1 → result=[1], add 2,3
 * Queue: [2,3]
 * Level 1: size=2
 *   i=0: node=2, last=2, add 5
 *   i=1: node=3, last=3, add 4
 *   result=[1,3]
 * Queue: [5,4]
 * Level 2: size=2
 *   i=0: node=5, last=5
 *   i=1: node=4, last=4
 *   result=[1,3,4]
 */

/**
 * DFS APPROACH EXPLANATION:
 * 
 * For right side view using DFS:
 * 1. Traverse right child first, then left child
 * 2. Keep track of current depth
 * 3. When depth == result.size(), this is first node seen at this depth
 * 4. Since we traverse right first, first node is rightmost
 * 
 * Example: Tree 1-2-3
 * dfs(1,0): depth=0, result.size=0 → add 1
 *   dfs(3,1): depth=1, result.size=1 → add 3
 *     dfs(null,2)
 *     dfs(null,2)
 *   dfs(2,1): depth=1, result.size=2 → skip
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Not handling empty tree
 * 2. Adding wrong node (first instead of last for right view)
 * 3. Not tracking level size correctly in BFS
 * 4. DFS: not traversing right child first
 * 5. DFS: wrong depth comparison (should be depth == result.size())
 * 6. Assuming tree is complete/perfect
 */

/**
 * VARIATIONS:
 * 
 * 1. Left Side View
 * 2. Top View
 * 3. Bottom View
 * 4. Diagonal View
 * 5. Print all side views
 */

/**
 * PRACTICE EXERCISES:
 * 
 * 1. Implement left side view
 * 2. Print both left and right side views
 * 3. Check if two trees have same right side view
 * 4. Find depth with maximum right side view value
 * 5. Right side view for N-ary tree
 */

/**
 * RELATED PROBLEMS:
 * 
 * 1. LeetCode 199: Binary Tree Right Side View
 * 2. LeetCode 102: Binary Tree Level Order Traversal
 * 3. LeetCode 103: Binary Tree Zigzag Level Order
 * 4. GeeksforGeeks: Left View of Binary Tree
 */