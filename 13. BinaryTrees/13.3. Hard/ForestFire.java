import java.util.*;

/** 
 * Burning Tree (Time to burn all nodes from target)
 * 
 * PROBLEM: Given binary tree and target node value, find minimum time to burn entire tree.
 * Fire spreads from burning node to its parent and children in one unit time.
 * 
 * APPROACH: BFS from target node with parent tracking
 * TIME: O(n) - each node visited twice (parent mapping + BFS)
 * SPACE: O(n) - parent map, visited set, queue
 */
public class ForestFire {
    static class Node { 
        int data; 
        Node left, right; 
        Node(int d) { data = d; } 
    }

    /**
     * Find minimum time to burn entire tree from target node
     * @param root - root of binary tree
     * @param targetVal - value of target node where fire starts
     * @return minimum time to burn all nodes
     * 
     * STRATEGY:
     * 1. Map each node to its parent (DFS traversal)
     * 2. Find target node
     * 3. BFS from target: burn parent, left child, right child each time unit
     * 4. Track visited nodes to avoid re-burning
     * 5. Count time levels where new nodes are burned
     */
    public int minTime(Node root, int targetVal) {
        if (root == null) return 0;
        
        // Map each node to its parent
        Map<Node, Node> parentMap = new HashMap<>();
        Node target = setParents(root, null, parentMap, targetVal);
        
        // If target not found, return 0
        if (target == null) return 0;
        
        // BFS to simulate fire spread
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        
        queue.offer(target);
        visited.add(target);
        int time = 0;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            boolean burnedThisLevel = false;
            
            // Process all nodes at current time unit
            for (int i = 0; i < levelSize; i++) {
                Node current = queue.poll();
                
                // Burn parent (if exists and not burned)
                Node parent = parentMap.get(current);
                if (parent != null && !visited.contains(parent)) {
                    visited.add(parent);
                    queue.offer(parent);
                    burnedThisLevel = true;
                }
                
                // Burn left child
                if (current.left != null && !visited.contains(current.left)) {
                    visited.add(current.left);
                    queue.offer(current.left);
                    burnedThisLevel = true;
                }
                
                // Burn right child
                if (current.right != null && !visited.contains(current.right)) {
                    visited.add(current.right);
                    queue.offer(current.right);
                    burnedThisLevel = true;
                }
            }
            
            // Increment time if any nodes burned this level
            if (burnedThisLevel) {
                time++;
            }
        }
        
        return time;
    }
    
    /**
     * DFS to map each node to its parent and find target node
     * @param node - current node
     * @param parent - parent of current node
     * @param parentMap - map to store node → parent mapping
     * @param targetVal - value to search for
     * @return target node if found in this subtree, null otherwise
     */
    private Node setParents(Node node, Node parent, Map<Node, Node> parentMap, int targetVal) {
        if (node == null) return null;
        
        // Store parent mapping
        parentMap.put(node, parent);
        
        Node target = null;
        
        // Check if current node is target
        if (node.data == targetVal) {
            target = node;
        }
        
        // Recursively process children
        Node leftTarget = setParents(node.left, node, parentMap, targetVal);
        Node rightTarget = setParents(node.right, node, parentMap, targetVal);
        
        // Return found target (prioritize current, then left, then right)
        if (target != null) return target;
        if (leftTarget != null) return leftTarget;
        return rightTarget;
    }
    
    /**
     * Alternative: Two-pass BFS approach
     * 1. First BFS to map parents and find target
     * 2. Second BFS to burn tree
     */
    public int minTimeTwoPass(Node root, int targetVal) {
        if (root == null) return 0;
        
        // Map parents and find target using BFS
        Map<Node, Node> parentMap = new HashMap<>();
        Node target = null;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        parentMap.put(root, null);
        
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            
            if (current.data == targetVal) {
                target = current;
            }
            
            if (current.left != null) {
                parentMap.put(current.left, current);
                queue.offer(current.left);
            }
            
            if (current.right != null) {
                parentMap.put(current.right, current);
                queue.offer(current.right);
            }
        }
        
        if (target == null) return 0;
        
        // BFS to burn tree
        return burnTree(target, parentMap);
    }
    
    private int burnTree(Node target, Map<Node, Node> parentMap) {
        Set<Node> burned = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(target);
        burned.add(target);
        int time = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            boolean spread = false;
            
            for (int i = 0; i < size; i++) {
                Node current = queue.poll();
                
                // Spread to parent
                Node parent = parentMap.get(current);
                if (parent != null && !burned.contains(parent)) {
                    burned.add(parent);
                    queue.offer(parent);
                    spread = true;
                }
                
                // Spread to children
                if (current.left != null && !burned.contains(current.left)) {
                    burned.add(current.left);
                    queue.offer(current.left);
                    spread = true;
                }
                
                if (current.right != null && !burned.contains(current.right)) {
                    burned.add(current.right);
                    queue.offer(current.right);
                    spread = true;
                }
            }
            
            if (spread) time++;
        }
        
        return time;
    }
    
    /**
     * Variation: Return nodes burned at each time unit
     */
    public List<List<Integer>> burningSequence(Node root, int targetVal) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Map<Node, Node> parentMap = new HashMap<>();
        Node target = setParents(root, null, parentMap, targetVal);
        
        if (target == null) return result;
        
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        
        queue.offer(target);
        visited.add(target);
        result.add(Arrays.asList(target.data));
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            
            for (int i = 0; i < size; i++) {
                Node current = queue.poll();
                
                // Check parent
                Node parent = parentMap.get(current);
                if (parent != null && !visited.contains(parent)) {
                    visited.add(parent);
                    queue.offer(parent);
                    level.add(parent.data);
                }
                
                // Check children
                if (current.left != null && !visited.contains(current.left)) {
                    visited.add(current.left);
                    queue.offer(current.left);
                    level.add(current.left.data);
                }
                
                if (current.right != null && !visited.contains(current.right)) {
                    visited.add(current.right);
                    queue.offer(current.right);
                    level.add(current.right.data);
                }
            }
            
            if (!level.isEmpty()) {
                result.add(level);
            }
        }
        
        return result;
    }

    /**
     * Visual Examples:
     * 
     * Example tree:
     *           1
     *          / \
     *         2   3
     *        /   / \
     *       4   5   6
     *          /
     *         7
     * 
     * Target: 5
     * 
     * Parent mapping:
     * 1.parent=null, 2.parent=1, 3.parent=1, 4.parent=2, 
     * 5.parent=3, 6.parent=3, 7.parent=5
     * 
     * Burning sequence:
     * Time 0: [5]
     * Time 1: [3,7]     (parent and left child)
     * Time 2: [1,6]     (3's parent and right child)
     * Time 3: [2]       (1's left child)
     * Time 4: [4]       (2's left child)
     * 
     * Total time = 4 units
     * 
     * Note: At time 1, fire spreads from 5 to parent (3) and child (7)
     * At time 2, from 3 to its parent (1) and right child (6)
     * From 7: no unburned neighbors
     */

    public static void main(String[] args) {
        ForestFire ff = new ForestFire();
        
        // Example tree
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.right.left = new Node(5);
        root.right.right = new Node(6);
        root.right.left.left = new Node(7);
        
        int target = 5;
        System.out.println("Min time to burn tree from node " + target + ": " + 
                          ff.minTime(root, target)); // Expected: 4
        
        System.out.println("Two-pass approach: " + ff.minTimeTwoPass(root, target)); // Expected: 4
        
        System.out.println("\nBurning sequence:");
        List<List<Integer>> sequence = ff.burningSequence(root, target);
        for (int i = 0; i < sequence.size(); i++) {
            System.out.println("Time " + i + ": " + sequence.get(i));
        }
        
        // Test cases
        System.out.println("\n=== Test Cases ===");
        
        // Test 1: Empty tree
        System.out.println("Empty tree: " + ff.minTime(null, 5)); // 0
        
        // Test 2: Single node (target)
        Node single = new Node(1);
        System.out.println("Single node (target): " + ff.minTime(single, 1)); // 0 (only target node)
        
        // Test 3: Single node (non-target)
        System.out.println("Single node (non-target): " + ff.minTime(single, 5)); // 0 (target not found)
        
        // Test 4: Left-skewed tree
        Node leftSkewed = new Node(1);
        leftSkewed.left = new Node(2);
        leftSkewed.left.left = new Node(3);
        leftSkewed.left.left.left = new Node(4);
        System.out.println("Left-skewed from root: " + ff.minTime(leftSkewed, 1)); // 3
        System.out.println("Left-skewed from leaf: " + ff.minTime(leftSkewed, 4)); // 3
        
        // Test 5: Right-skewed tree
        Node rightSkewed = new Node(1);
        rightSkewed.right = new Node(2);
        rightSkewed.right.right = new Node(3);
        rightSkewed.right.right.right = new Node(4);
        System.out.println("Right-skewed from root: " + ff.minTime(rightSkewed, 1)); // 3
        
        // Test 6: Complete binary tree
        Node complete = new Node(1);
        complete.left = new Node(2);
        complete.right = new Node(3);
        complete.left.left = new Node(4);
        complete.left.right = new Node(5);
        complete.right.left = new Node(6);
        complete.right.right = new Node(7);
        System.out.println("Complete tree from root: " + ff.minTime(complete, 1)); // 2
        System.out.println("Complete tree from leaf 4: " + ff.minTime(complete, 4)); // 3
    }
}

/**
 * KEY CONCEPTS:
 * 
 * 1. Fire spread rules:
 *    - From burning node to its parent and children
 *    - One time unit per level of spread
 *    - Fire doesn't re-burn already burned nodes
 * 
 * 2. Parent mapping:
 *    - Essential to traverse upward
 *    - Can be done with DFS or BFS
 * 
 * 3. BFS for level-wise spread:
 *    - Each BFS level = one time unit
 *    - Track visited nodes to prevent cycles
 * 
 * 4. Time calculation:
 *    - Start time = 0 (target node burning)
 *    - Increment when new nodes burn
 *    - Final time = max distance from target
 */

/**
 * TIME & SPACE ANALYSIS:
 * 
 * Time Complexity: O(n)
 *   - Parent mapping: O(n)
 *   - BFS burning: O(n)
 *   - Total: O(2n) ≈ O(n)
 * 
 * Space Complexity: O(n)
 *   - Parent map: O(n)
 *   - Visited set: O(n)
 *   - Queue: O(w) where w = max width
 *   - Total: O(n)
 */

/**
 * ALGORITHM WALKTHROUGH:
 * 
 * Tree:     1
 *          / \
 *         2   3
 *            / \
 *           5   6
 *          /
 *         7
 * Target: 5
 * 
 * 1. Parent mapping:
 *   1→null, 2→1, 3→1, 5→3, 6→3, 7→5
 * 
 * 2. BFS from target 5:
 *   Level 0: [5] → time=0
 *   Level 1: burn 3(parent) and 7(child) → time=1
 *   Level 2: from 3: burn 1(parent) and 6(child) → time=2
 *            from 7: nothing new
 *   Level 3: from 1: burn 2(child) → time=3
 *            from 6: nothing
 *   Level 4: from 2: burn 4(child) → time=4
 * 
 * Total time = 4
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Not mapping parents (can't traverse upward)
 * 2. Forgetting to mark target as visited initially
 * 3. Not checking all three directions (parent, left, right)
 * 4. Counting target node as time unit (should be time 0)
 * 5. Not handling target not found case
 */

/**
 * VARIATIONS:
 * 
 * 1. Print burning sequence (nodes at each time)
 * 2. Find maximum burning time for any target
 * 3. Burn tree from multiple starting points
 * 4. Different spread rules (only children, etc.)
 * 5. Weighted edges (different burn times)
 */

/**
 * PRACTICE EXERCISES:
 * 
 * 1. Implement two-pass BFS approach
 * 2. Return burning sequence
 * 3. Find node that maximizes burning time
 * 4. Check if tree can be burned within k time
 * 5. Burn tree with fire starting at leaves only
 */