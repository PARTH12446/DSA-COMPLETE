import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Binary Tree Zigzag Level Order Traversal (LeetCode 103)
 * 
 * PROBLEM: Traverse binary tree level by level, alternating direction each level
 * Level 0: left to right
 * Level 1: right to left  
 * Level 2: left to right
 * etc.
 * 
 * APPROACH: BFS with direction flag and occasional reversal
 * TIME: O(n) - each node visited once
 * SPACE: O(w) - queue width (max nodes at any level)
 */
public class ZigZag {

    static class TreeNode {
        int val; 
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    /**
     * Zigzag level order traversal
     * @param root - root of binary tree
     * @return List of levels, each level's nodes in zigzag order
     * 
     * STRATEGY:
     * 1. Use standard BFS level order traversal
     * 2. Track direction with boolean flag
     * 3. For even levels (0,2,4...): left to right (don't reverse)
     * 4. For odd levels (1,3,5...): right to left (reverse level list)
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true; // Direction flag
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            
            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                currentLevel.add(node.val);
                
                // Add children for next level
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            // Reverse level if direction is right-to-left
            if (!leftToRight) {
                java.util.Collections.reverse(currentLevel);
            }
            
            result.add(currentLevel);
            // Toggle direction for next level
            leftToRight = !leftToRight;
        }
        
        return result;
    }
    
    /**
     * Alternative: Using LinkedList for efficient reversal
     * Add to front for right-to-left levels
     */
    public List<List<Integer>> zigzagLevelOrderLinkedList(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            LinkedList<Integer> currentLevel = new LinkedList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                // Add to list based on direction
                if (leftToRight) {
                    currentLevel.addLast(node.val); // Normal order
                } else {
                    currentLevel.addFirst(node.val); // Reverse order
                }
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            result.add(currentLevel);
            leftToRight = !leftToRight;
        }
        
        return result;
    }
    
    /**
     * Alternative: Two-stack approach (no queue)
     * Uses two stacks to alternate direction
     */
    public List<List<Integer>> zigzagLevelOrderTwoStacks(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        java.util.Stack<TreeNode> currentLevel = new java.util.Stack<>();
        java.util.Stack<TreeNode> nextLevel = new java.util.Stack<>();
        boolean leftToRight = true;
        
        currentLevel.push(root);
        List<Integer> level = new ArrayList<>();
        
        while (!currentLevel.isEmpty()) {
            TreeNode node = currentLevel.pop();
            level.add(node.val);
            
            // Push children based on direction
            if (leftToRight) {
                if (node.left != null) nextLevel.push(node.left);
                if (node.right != null) nextLevel.push(node.right);
            } else {
                if (node.right != null) nextLevel.push(node.right);
                if (node.left != null) nextLevel.push(node.left);
            }
            
            // Switch to next level when current level done
            if (currentLevel.isEmpty()) {
                result.add(new ArrayList<>(level));
                level.clear();
                leftToRight = !leftToRight;
                
                // Swap stacks
                java.util.Stack<TreeNode> temp = currentLevel;
                currentLevel = nextLevel;
                nextLevel = temp;
            }
        }
        
        return result;
    }
    
    /**
     * DFS approach (for comparison)
     * Pre-order traversal with level tracking
     */
    public List<List<Integer>> zigzagLevelOrderDFS(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(root, 0, result);
        return result;
    }
    
    private void dfs(TreeNode node, int level, List<List<Integer>> result) {
        if (node == null) return;
        
        // Create new level list if needed
        if (level >= result.size()) {
            result.add(new LinkedList<>());
        }
        
        // Add node to appropriate position based on level parity
        LinkedList<Integer> levelList = (LinkedList<Integer>) result.get(level);
        if (level % 2 == 0) {
            levelList.addLast(node.val); // Even level: left to right
        } else {
            levelList.addFirst(node.val); // Odd level: right to left
        }
        
        // Recursively process children
        dfs(node.left, level + 1, result);
        dfs(node.right, level + 1, result);
    }

    /**
     * Visual Examples:
     * 
     * Example tree:
     *       1
     *      / \
     *     2   3
     *    / \   \
     *   4   5   6
     * 
     * Normal level order: [[1], [2,3], [4,5,6]]
     * Zigzag level order: [[1], [3,2], [4,5,6]]
     * 
     * Level 0 (even): left to right → [1]
     * Level 1 (odd): reverse [2,3] → [3,2]
     * Level 2 (even): left to right → [4,5,6]
     * 
     * Execution:
     * Queue: [1], level=0, direction=true → [1]
     * Queue: [2,3], level=1, direction=false → reverse [2,3] to [3,2]
     * Queue: [4,5,6], level=2, direction=true → [4,5,6]
     */

    public static void main(String[] args) {
        ZigZag z = new ZigZag();
        
        // Example from problem
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);
        
        System.out.println("Zigzag (BFS with reverse): " + z.zigzagLevelOrder(root));
        // Expected: [[1], [3,2], [4,5,6]]
        
        System.out.println("Zigzag (LinkedList): " + z.zigzagLevelOrderLinkedList(root));
        // Expected: same
        
        System.out.println("Zigzag (Two Stacks): " + z.zigzagLevelOrderTwoStacks(root));
        // Expected: same
        
        System.out.println("Zigzag (DFS): " + z.zigzagLevelOrderDFS(root));
        // Expected: same
        
        // Test cases
        System.out.println("\n=== Test Cases ===");
        
        // Test 1: Empty tree
        System.out.println("Empty tree: " + z.zigzagLevelOrder(null)); // []
        
        // Test 2: Single node
        TreeNode single = new TreeNode(1);
        System.out.println("Single node: " + z.zigzagLevelOrder(single)); // [[1]]
        
        // Test 3: Left-skewed tree
        TreeNode leftSkewed = new TreeNode(1);
        leftSkewed.left = new TreeNode(2);
        leftSkewed.left.left = new TreeNode(3);
        System.out.println("Left-skewed: " + z.zigzagLevelOrder(leftSkewed)); // [[1], [2], [3]]
        
        // Test 4: Right-skewed tree
        TreeNode rightSkewed = new TreeNode(1);
        rightSkewed.right = new TreeNode(2);
        rightSkewed.right.right = new TreeNode(3);
        System.out.println("Right-skewed: " + z.zigzagLevelOrder(rightSkewed)); // [[1], [2], [3]]
        
        // Test 5: Perfect binary tree
        TreeNode perfect = new TreeNode(1);
        perfect.left = new TreeNode(2);
        perfect.right = new TreeNode(3);
        perfect.left.left = new TreeNode(4);
        perfect.left.right = new TreeNode(5);
        perfect.right.left = new TreeNode(6);
        perfect.right.right = new TreeNode(7);
        System.out.println("Perfect binary: " + z.zigzagLevelOrder(perfect));
        // Expected: [[1], [3,2], [4,5,6,7]]
        
        // Test 6: More complex tree
        TreeNode complex = new TreeNode(1);
        complex.left = new TreeNode(2);
        complex.right = new TreeNode(3);
        complex.left.left = new TreeNode(4);
        complex.left.right = new TreeNode(5);
        complex.right.left = new TreeNode(6);
        complex.right.right = new TreeNode(7);
        complex.left.left.left = new TreeNode(8);
        complex.left.left.right = new TreeNode(9);
        System.out.println("Complex: " + z.zigzagLevelOrder(complex));
        // Expected: [[1], [3,2], [4,5,6,7], [9,8]]
    }
}

/**
 * KEY CONCEPTS:
 * 
 * 1. Zigzag pattern:
 *    - Even levels (0,2,4...): left to right
 *    - Odd levels (1,3,5...): right to left
 * 
 * 2. Direction handling approaches:
 *    - Reverse list after building (simplest)
 *    - Use LinkedList: addLast() or addFirst()
 *    - Two stacks: alternate push order
 * 
 * 3. Level tracking:
 *    - BFS: track level with queue size
 *    - DFS: pass level as parameter
 */

/**
 * TIME & SPACE ANALYSIS:
 * 
 * BFS with reverse:
 *   Time: O(n) - Each node visited once
 *         O(n) for reversal across all levels
 *   Space: O(w) where w = max width of tree
 * 
 * LinkedList approach:
 *   Time: O(n) - Each node visited once, O(1) add
 *   Space: O(w)
 * 
 * Two-stack approach:
 *   Time: O(n) - Each node pushed/popped once
 *   Space: O(w) - Two stacks
 * 
 * DFS approach:
 *   Time: O(n) - Each node visited once
 *   Space: O(h) - Recursion stack, plus O(n) for result
 */

/**
 * ALGORITHM WALKTHROUGH (BFS with reverse):
 * 
 * Tree:   1
 *        / \
 *       2   3
 *      / \   \
 *     4   5   6
 * 
 * Queue: [1]
 * Level 0: size=1, level=[1], leftToRight=true → don't reverse → result=[[1]]
 * Toggle: leftToRight=false
 * 
 * Queue: [2,3]  
 * Level 1: size=2
 *   i=0: node=2, level=[2], add children 4,5 → queue=[3,4,5]
 *   i=1: node=3, level=[2,3], add child 6 → queue=[4,5,6]
 *   leftToRight=false → reverse [2,3] to [3,2] → result=[[1],[3,2]]
 * Toggle: leftToRight=true
 * 
 * Queue: [4,5,6]
 * Level 2: size=3
 *   i=0: node=4, level=[4]
 *   i=1: node=5, level=[4,5]
 *   i=2: node=6, level=[4,5,6]
 *   leftToRight=true → don't reverse → result=[[1],[3,2],[4,5,6]]
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Not toggling direction correctly:
 *    - Should toggle after each level
 * 
 * 2. Reversing at wrong time:
 *    - Reverse after building level, not during
 * 
 * 3. Not handling empty tree
 * 
 * 4. Using wrong data structure:
 *    - ArrayList reverse is O(n), LinkedList addFirst is O(1)
 * 
 * 5. Confusing level indices:
 *    - Level 0 is root (even), not level 1
 */

/**
 * VARIATIONS:
 * 
 * 1. Spiral traversal (same as zigzag)
 * 2. Level order with different patterns
 * 3. Print tree in snake pattern
 * 4. N-ary tree zigzag
 * 5. Binary tree in diagonal order
 */

/**
 * PRACTICE EXERCISES:
 * 
 * 1. Implement using two stacks
 * 2. Implement using DFS
 * 3. Print zigzag with arrow indicators
 * 4. Find maximum zigzag path length
 * 5. Check if traversal is palindrome at each level
 */