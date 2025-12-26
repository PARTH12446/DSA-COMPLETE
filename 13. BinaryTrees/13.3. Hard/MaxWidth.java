import java.util.LinkedList;
import java.util.List;

/** 
 * Solution for finding maximum/minimum width of a binary tree.
 * Problem: Given a binary tree, find the maximum width of the tree.
 * Width is defined as the maximum number of nodes in any level (including null nodes between end nodes).
 * Minimum width is the minimum number of nodes in any level.
 */
public class MaxWidth {
    
    // TreeNode definition for binary tree
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }
    
    // Pair class to store node along with its position index in the level
    // Using long to prevent integer overflow for deep trees
    static class Pair {
        TreeNode node;
        long idx; // Position index (like in heap representation)
        Pair(TreeNode n, long i) { node = n; idx = i; }
    }
    
    /**
     * Finds the maximum width of the binary tree.
     * Strategy: Use level-order traversal with position indexing.
     * For each node at position i:
     * - Left child gets position 2*i
     * - Right child gets position 2*i + 1
     * Width of a level = (last_idx - first_idx + 1)
     * 
     * @param root Root of the binary tree
     * @return Maximum width across all levels
     * Time Complexity: O(n) where n is number of nodes
     * Space Complexity: O(w) where w is maximum width
     */
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) return 0;
        
        // Current level being processed (stores node and its position)
        List<Pair> level = new LinkedList<>();
        level.add(new Pair(root, 1)); // Root at position 1
        
        int maxWidth = 0;
        
        while (!level.isEmpty()) {
            // Get first and last positions in current level
            long first = level.get(0).idx;
            long last = level.get(level.size() - 1).idx;
            
            // Calculate width: difference in positions + 1
            maxWidth = Math.max(maxWidth, (int)(last - first + 1));
            
            // Prepare next level
            List<Pair> next = new LinkedList<>();
            
            // Process all nodes in current level
            for (Pair p : level) {
                TreeNode node = p.node;
                long pos = p.idx;
                
                // Add left child with position 2*parent_position
                if (node.left != null) {
                    next.add(new Pair(node.left, pos * 2));
                }
                
                // Add right child with position 2*parent_position + 1
                if (node.right != null) {
                    next.add(new Pair(node.right, pos * 2 + 1));
                }
            }
            
            // Move to next level
            level = next;
        }
        
        return maxWidth;
    }
    
    /**
     * Finds the minimum width of the binary tree.
     * Minimum width is defined as the smallest number of nodes in any level
     * (excluding null nodes between end nodes, just counting actual nodes).
     * 
     * Note: This is DIFFERENT from the problem that uses position indexing.
     * This simply counts actual nodes at each level.
     * 
     * @param root Root of the binary tree
     * @return Minimum width (minimum number of nodes at any level)
     * Time Complexity: O(n)
     * Space Complexity: O(w)
     */
    public int minWidth(TreeNode root) {
        if (root == null) return 0;
        
        // Use LinkedList for queue operations (FIFO)
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        int minWidth = Integer.MAX_VALUE;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            
            // Update minimum width with current level's node count
            minWidth = Math.min(minWidth, levelSize);
            
            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                // Add children to queue for next level
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        
        return minWidth;
    }
    
    /**
     * Alternative: Minimum width using the same position-indexing approach as maxWidth.
     * This finds the minimum of (last_idx - first_idx + 1) across all levels.
     * This includes "null gaps" in the width calculation.
     * 
     * @param root Root of the binary tree
     * @return Minimum width including null gaps between nodes
     */
    public int minWidthWithNullGaps(TreeNode root) {
        if (root == null) return 0;
        
        List<Pair> level = new LinkedList<>();
        level.add(new Pair(root, 1));
        
        int minWidth = Integer.MAX_VALUE;
        
        while (!level.isEmpty()) {
            long first = level.get(0).idx;
            long last = level.get(level.size() - 1).idx;
            
            int currentWidth = (int)(last - first + 1);
            minWidth = Math.min(minWidth, currentWidth);
            
            List<Pair> next = new LinkedList<>();
            for (Pair p : level) {
                if (p.node.left != null) {
                    next.add(new Pair(p.node.left, p.idx * 2));
                }
                if (p.node.right != null) {
                    next.add(new Pair(p.node.right, p.idx * 2 + 1));
                }
            }
            level = next;
        }
        
        return minWidth;
    }
    
    // Helper method for testing
    public static void main(String[] args) {
        MaxWidth solution = new MaxWidth();
        
        // Example tree:
        //       1
        //      / \
        //     3   2
        //    /     \
        //   5       9
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(3);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(5);
        root.right.right = new TreeNode(9);
        
        System.out.println("Maximum width: " + solution.widthOfBinaryTree(root)); // 4
        System.out.println("Minimum width (actual nodes): " + solution.minWidth(root)); // 2
        System.out.println("Minimum width (with null gaps): " + solution.minWidthWithNullGaps(root)); // 2
    }
}