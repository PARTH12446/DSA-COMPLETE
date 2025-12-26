import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Binary Tree Level Order Traversal (BFS by levels).
 * 
 * LEVEL ORDER TRAVERSAL (Breadth-First Search):
 * Process nodes level by level from root to leaves
 * Returns: List of levels, each level is list of node values
 * 
 * ALGORITHM: Use queue, process each level separately
 * 1. Add root to queue
 * 2. While queue not empty:
 *    a. Get current level size = queue.size()
 *    b. Process all nodes at current level
 *    c. Add children to queue for next level
 * 
 * TIME: O(n) - each node visited once
 * SPACE: O(w) - queue width (max nodes at any level)
 */
public class LevelOrder {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    /**
     * Level Order Traversal (BFS)
     * @param root - Root of binary tree
     * @return List of levels, each level is list of node values
     * 
     * KEY POINTS:
     * 1. Use Queue (FIFO) for BFS
     * 2. Track level size to separate levels
     * 3. Add children to queue for next level
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            // Number of nodes at current level
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            
            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                currentLevel.add(node.val);
                
                // Add children to queue for next level
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            result.add(currentLevel);
        }
        return result;
    }
    
    /**
     * Alternative: Simple BFS without level separation
     * Returns flat list of nodes in level order
     */
    public List<Integer> levelOrderFlat(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            result.add(node.val);
            
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        return result;
    }
    
    /**
     * Reverse Level Order (bottom-up)
     * Returns levels from leaves to root
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            // Add level at beginning for reverse order
            result.add(0, level);
        }
        return result;
    }

    /**
     * Visual Walkthrough for tree:
     *        1
     *       / \
     *      2   3
     *     / \   \
     *    4   5   6
     * 
     * Execution trace:
     * 
     * Initial: queue=[1], result=[]
     * 
     * Level 0: size=1
     *   i=0: poll 1, add to level [1]
     *        add children: 2, 3 → queue=[2,3]
     *   result=[[1]]
     *   
     * Level 1: size=2, level=[]
     *   i=0: poll 2, add to level [2]
     *        add children: 4, 5 → queue=[3,4,5]
     *   i=1: poll 3, add to level [2,3]
     *        add children: 6 → queue=[4,5,6]
     *   result=[[1], [2,3]]
     *   
     * Level 2: size=3, level=[]
     *   i=0: poll 4, add to level [4]
     *        no children
     *   i=1: poll 5, add to level [4,5]
     *        no children
     *   i=2: poll 6, add to level [4,5,6]
     *        no children
     *   result=[[1], [2,3], [4,5,6]]
     */

    public static void main(String[] args) {
        // Build sample tree:
        //        1
        //       / \
        //      2   3
        //     / \   \
        //    4   5   6
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);

        LevelOrder solver = new LevelOrder();
        
        System.out.println("Level Order (by levels): " + solver.levelOrder(root));
        // Expected: [[1], [2, 3], [4, 5, 6]]
        
        System.out.println("Flat Level Order: " + solver.levelOrderFlat(root));
        // Expected: [1, 2, 3, 4, 5, 6]
        
        System.out.println("Bottom-up Level Order: " + solver.levelOrderBottom(root));
        // Expected: [[4, 5, 6], [2, 3], [1]]
        
        // Test edge cases
        System.out.println("\nEmpty tree: " + solver.levelOrder(null)); // []
        
        TreeNode single = new TreeNode(10);
        System.out.println("Single node: " + solver.levelOrder(single)); // [[10]]
        
        // Skewed tree (right)
        TreeNode rightSkewed = new TreeNode(1);
        rightSkewed.right = new TreeNode(2);
        rightSkewed.right.right = new TreeNode(3);
        System.out.println("Right-skewed: " + solver.levelOrder(rightSkewed));
        // Expected: [[1], [2], [3]]
        
        // Perfect binary tree
        TreeNode perfect = new TreeNode(1);
        perfect.left = new TreeNode(2);
        perfect.right = new TreeNode(3);
        perfect.left.left = new TreeNode(4);
        perfect.left.right = new TreeNode(5);
        perfect.right.left = new TreeNode(6);
        perfect.right.right = new TreeNode(7);
        System.out.println("Perfect binary: " + solver.levelOrder(perfect));
        // Expected: [[1], [2, 3], [4, 5, 6, 7]]
    }
}

/**
 * COMPARISON WITH DFS TRAVERSALS:
 * 
 * DFS (Pre/In/Post-order):
 *   - Uses Stack (LIFO) or recursion
 *   - Explores depth first
 *   - Space: O(h) where h = height
 *   
 * BFS (Level-order):
 *   - Uses Queue (FIFO)
 *   - Explores breadth first (level by level)
 *   - Space: O(w) where w = max width
 *   - Finds shortest path in unweighted trees
 */

/**
 * QUEUE CHOICE:
 * - LinkedList implements Queue interface
 * - offer() = add to end (returns false if fails)
 * - poll() = remove from front (returns null if empty)
 * - peek() = view front without removing
 * 
 * Alternative: ArrayDeque (faster for many operations)
 */

/**
 * TIME & SPACE ANALYSIS:
 * 
 * Time Complexity: O(n) - Each node visited exactly once
 * Space Complexity: O(w) where w = maximum width of tree
 *   - Worst case: O(n) for complete binary tree (last level has ~n/2 nodes)
 *   - Best case: O(1) for skewed tree (width = 1)
 *   
 * Queue operations per node: offer once, poll once → O(1) per node
 */

/**
 * VARIATIONS OF LEVEL ORDER:
 * 
 * 1. Zigzag Level Order (LeetCode 103):
 *    - Alternate left-to-right and right-to-left
 *    - Use boolean flag and Collections.reverse()
 * 
 * 2. Level Average (LeetCode 637):
 *    - Calculate average of each level
 * 
 * 3. Right Side View (LeetCode 199):
 *    - Only take last element of each level
 * 
 * 4. Find Largest Value in Each Level (LeetCode 515)
 * 
 * 5. Minimum Depth of Binary Tree (LeetCode 111)
 *    - BFS stops at first leaf found
 */

/**
 * APPLICATIONS:
 * 
 * 1. Shortest path in unweighted tree/graph
 * 2. Printing tree structure
 * 3. Serialization/deserialization
 * 4. Finding level of a node
 * 5. Checking if tree is complete
 * 6. Finding cousins in binary tree
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Not tracking level size (mixes levels)
 * 2. Using stack instead of queue (DFS)
 * 3. Forgetting to check null children
 * 4. Not handling empty tree
 * 5. Using add() instead of offer() (throws exception)
 * 6. Not using size variable (infinite loop if queue changes)
 */

/**
 * OPTIMIZATIONS:
 * 
 * 1. Pre-allocate level list with known size
 * 2. Use ArrayDeque instead of LinkedList
 * 3. For reverse level order, use stack or add to front
 * 4. For memory, process nodes without storing all levels
 */

/**
 * RELATED PROBLEMS:
 * 
 * 1. N-ary Tree Level Order (LeetCode 429)
 * 2. Binary Tree Zigzag (LeetCode 103)  
 * 3. Average of Levels (LeetCode 637)
 * 4. Right Side View (LeetCode 199)
 * 5. Minimum Depth (LeetCode 111)
 */