import java.util.*;

/** 
 * Root-to-leaf paths in a binary tree - Multiple approaches
 * Problem: 257. Binary Tree Paths (LeetCode)
 * 
 * Find all root-to-leaf paths in a binary tree
 */
public class ThePaths {
    static class Node {
        int data;
        Node left, right;
        Node(int d) { data = d; }
    }

    // ============================
    // APPROACH 1: DFS with Backtracking (Original)
    // ============================
    
    /**
     * Find all root-to-leaf paths using DFS with backtracking
     * Time: O(n) where n is number of nodes
     * Space: O(h) for recursion stack, O(n) for result
     */
    public List<List<Integer>> paths(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        dfs(root, new ArrayList<>(), result);
        return result;
    }

    /**
     * DFS helper with backtracking
     * Key points:
     * 1. Add current node to path
     * 2. If leaf node, save path to result
     * 3. Recursively explore left and right
     * 4. Remove current node (backtrack)
     */
    private void dfs(Node node, List<Integer> path, List<List<Integer>> result) {
        if (node == null) return;
        
        // Add current node to path
        path.add(node.data);
        
        // Check if leaf node
        if (node.left == null && node.right == null) {
            // Create new list to avoid reference issues
            result.add(new ArrayList<>(path));
        } else {
            // Recursively explore children
            dfs(node.left, path, result);
            dfs(node.right, path, result);
        }
        
        // Backtrack: remove current node
        path.remove(path.size() - 1);
    }

    // ============================
    // APPROACH 2: DFS with StringBuilder
    // ============================
    
    /**
     * Return paths as strings (LeetCode format: "1->2->5")
     * Time: O(n), Space: O(h)
     */
    public List<String> binaryTreePaths(Node root) {
        List<String> result = new ArrayList<>();
        if (root == null) return result;
        
        dfsString(root, new StringBuilder(), result);
        return result;
    }
    
    private void dfsString(Node node, StringBuilder path, List<String> result) {
        if (node == null) return;
        
        // Save current length for backtracking
        int len = path.length();
        
        // Add current node to path
        if (len > 0) path.append("->");
        path.append(node.data);
        
        // Check if leaf node
        if (node.left == null && node.right == null) {
            result.add(path.toString());
        } else {
            dfsString(node.left, path, result);
            dfsString(node.right, path, result);
        }
        
        // Backtrack: restore original length
        path.setLength(len);
    }

    // ============================
    // APPROACH 3: BFS (Level-order) with Path Tracking
    // ============================
    
    /**
     * BFS approach using queue to store paths
     * Time: O(n), Space: O(n)
     */
    public List<List<Integer>> pathsBFS(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<Node> nodeQueue = new LinkedList<>();
        Queue<List<Integer>> pathQueue = new LinkedList<>();
        
        // Initialize with root
        nodeQueue.offer(root);
        List<Integer> initialPath = new ArrayList<>();
        initialPath.add(root.data);
        pathQueue.offer(initialPath);
        
        while (!nodeQueue.isEmpty()) {
            Node current = nodeQueue.poll();
            List<Integer> currentPath = pathQueue.poll();
            
            // Check if leaf node
            if (current.left == null && current.right == null) {
                result.add(currentPath);
            }
            
            // Process left child
            if (current.left != null) {
                List<Integer> newPath = new ArrayList<>(currentPath);
                newPath.add(current.left.data);
                nodeQueue.offer(current.left);
                pathQueue.offer(newPath);
            }
            
            // Process right child
            if (current.right != null) {
                List<Integer> newPath = new ArrayList<>(currentPath);
                newPath.add(current.right.data);
                nodeQueue.offer(current.right);
                pathQueue.offer(newPath);
            }
        }
        
        return result;
    }

    // ============================
    // APPROACH 4: DFS without Backtracking (Pass new list)
    // ============================
    
    /**
     * DFS creating new list at each recursion (simpler but more memory)
     * Time: O(n * h) for list copying, Space: O(h²)
     */
    public List<List<Integer>> pathsNoBacktrack(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        dfsNewList(root, new ArrayList<>(), result);
        return result;
    }
    
    private void dfsNewList(Node node, List<Integer> path, List<List<Integer>> result) {
        if (node == null) return;
        
        // Create new list with current path + current node
        List<Integer> newPath = new ArrayList<>(path);
        newPath.add(node.data);
        
        if (node.left == null && node.right == null) {
            result.add(newPath);
        } else {
            dfsNewList(node.left, newPath, result);
            dfsNewList(node.right, newPath, result);
        }
    }

    // ============================
    // APPROACH 5: Iterative DFS with Stack
    // ============================
    
    /**
     * Iterative DFS using stack (avoids recursion)
     * Time: O(n), Space: O(h)
     */
    public List<List<Integer>> pathsIterative(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<Node> nodeStack = new Stack<>();
        Stack<List<Integer>> pathStack = new Stack<>();
        
        nodeStack.push(root);
        List<Integer> initialPath = new ArrayList<>();
        initialPath.add(root.data);
        pathStack.push(initialPath);
        
        while (!nodeStack.isEmpty()) {
            Node current = nodeStack.pop();
            List<Integer> currentPath = pathStack.pop();
            
            if (current.left == null && current.right == null) {
                result.add(currentPath);
            }
            
            // Push right first so left is processed first (stack is LIFO)
            if (current.right != null) {
                List<Integer> newPath = new ArrayList<>(currentPath);
                newPath.add(current.right.data);
                nodeStack.push(current.right);
                pathStack.push(newPath);
            }
            
            if (current.left != null) {
                List<Integer> newPath = new ArrayList<>(currentPath);
                newPath.add(current.left.data);
                nodeStack.push(current.left);
                pathStack.push(newPath);
            }
        }
        
        return result;
    }

    // ============================
    // APPROACH 6: Find if Path Sum Exists
    // ============================
    
    /**
     * Check if there exists a root-to-leaf path with given sum
     * Problem: 112. Path Sum (LeetCode)
     */
    public boolean hasPathSum(Node root, int targetSum) {
        if (root == null) return false;
        
        // Subtract current node value from target
        targetSum -= root.data;
        
        // Check if leaf node and sum matches
        if (root.left == null && root.right == null) {
            return targetSum == 0;
        }
        
        // Recursively check left and right subtrees
        return hasPathSum(root.left, targetSum) || 
               hasPathSum(root.right, targetSum);
    }

    // ============================
    // APPROACH 7: Find All Paths with Given Sum
    // ============================
    
    /**
     * Find all root-to-leaf paths with given sum
     * Problem: 113. Path Sum II (LeetCode)
     */
    public List<List<Integer>> pathSum(Node root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        pathSumDFS(root, targetSum, new ArrayList<>(), result);
        return result;
    }
    
    private void pathSumDFS(Node node, int remaining, 
                           List<Integer> path, List<List<Integer>> result) {
        if (node == null) return;
        
        path.add(node.data);
        remaining -= node.data;
        
        if (node.left == null && node.right == null && remaining == 0) {
            result.add(new ArrayList<>(path));
        } else {
            pathSumDFS(node.left, remaining, path, result);
            pathSumDFS(node.right, remaining, path, result);
        }
        
        path.remove(path.size() - 1);
    }

    // ============================
    // APPROACH 8: Find Maximum Path Sum
    // ============================
    
    /**
     * Find the maximum sum of any root-to-leaf path
     */
    public int maxPathSum(Node root) {
        if (root == null) return 0;
        return maxPathSumDFS(root);
    }
    
    private int maxPathSumDFS(Node node) {
        if (node == null) return Integer.MIN_VALUE;
        
        // Leaf node: return node value
        if (node.left == null && node.right == null) {
            return node.data;
        }
        
        int leftMax = maxPathSumDFS(node.left);
        int rightMax = maxPathSumDFS(node.right);
        
        // Return maximum of left and right plus current node
        return node.data + Math.max(leftMax, rightMax);
    }

    // ============================
    // TESTING AND EXAMPLES
    // ============================
    
    public static void main(String[] args) {
        ThePaths solution = new ThePaths();
        
        // Create test tree:
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        
        System.out.println("=== Test Case 1: All Root-to-Leaf Paths ===");
        
        // Test Approach 1: DFS with Backtracking
        List<List<Integer>> paths1 = solution.paths(root);
        System.out.println("DFS with Backtracking:");
        for (List<Integer> path : paths1) {
            System.out.println(path);
        }
        // Expected: [[1,2,4], [1,2,5], [1,3]]
        
        // Test Approach 2: String paths
        List<String> stringPaths = solution.binaryTreePaths(root);
        System.out.println("\nString paths:");
        for (String path : stringPaths) {
            System.out.println(path);
        }
        // Expected: ["1->2->4", "1->2->5", "1->3"]
        
        // Test Approach 3: BFS
        List<List<Integer>> paths2 = solution.pathsBFS(root);
        System.out.println("\nBFS approach:");
        for (List<Integer> path : paths2) {
            System.out.println(path);
        }
        
        // Test Approach 4: Without backtracking
        List<List<Integer>> paths3 = solution.pathsNoBacktrack(root);
        System.out.println("\nDFS without backtracking:");
        for (List<Integer> path : paths3) {
            System.out.println(path);
        }
        
        // Test Approach 5: Iterative DFS
        List<List<Integer>> paths4 = solution.pathsIterative(root);
        System.out.println("\nIterative DFS:");
        for (List<Integer> path : paths4) {
            System.out.println(path);
        }
        
        // Test Path Sum
        System.out.println("\n=== Test Case 2: Path Sum ===");
        System.out.println("Has path sum 8? " + solution.hasPathSum(root, 8)); // true: 1+2+5
        System.out.println("Has path sum 7? " + solution.hasPathSum(root, 7)); // true: 1+2+4
        System.out.println("Has path sum 10? " + solution.hasPathSum(root, 10)); // false
        
        // Test Path Sum II
        System.out.println("\n=== Test Case 3: All Paths with Sum 8 ===");
        List<List<Integer>> sumPaths = solution.pathSum(root, 8);
        for (List<Integer> path : sumPaths) {
            System.out.println(path); // Expected: [1, 2, 5]
        }
        
        // Test Max Path Sum
        System.out.println("\n=== Test Case 4: Maximum Path Sum ===");
        System.out.println("Maximum path sum: " + solution.maxPathSum(root)); // Expected: 9 (1+2+5)
        
        // Edge Cases
        System.out.println("\n=== Test Case 5: Edge Cases ===");
        
        // Single node
        Node single = new Node(42);
        System.out.println("Single node paths: " + solution.paths(single));
        
        // Empty tree
        System.out.println("Empty tree paths: " + solution.paths(null));
        
        // Left-skewed tree
        Node leftSkewed = new Node(1);
        leftSkewed.left = new Node(2);
        leftSkewed.left.left = new Node(3);
        System.out.println("Left-skewed paths: " + solution.paths(leftSkewed));
        
        // Tree with negative values
        Node negativeTree = new Node(-1);
        negativeTree.left = new Node(2);
        negativeTree.right = new Node(-3);
        negativeTree.left.left = new Node(4);
        System.out.println("Tree with negatives: " + solution.paths(negativeTree));
        System.out.println("Max path sum with negatives: " + solution.maxPathSum(negativeTree));
    }
    
    // ============================
    // ADDITIONAL UTILITIES
    // ============================
    
    /**
     * Find shortest root-to-leaf path length
     */
    public int minDepth(Node root) {
        if (root == null) return 0;
        
        // If one child is null, only consider the other child
        if (root.left == null) return 1 + minDepth(root.right);
        if (root.right == null) return 1 + minDepth(root.left);
        
        return 1 + Math.min(minDepth(root.left), minDepth(root.right));
    }
    
    /**
     * Find longest root-to-leaf path length (tree height)
     */
    public int maxDepth(Node root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }
    
    /**
     * Check if two trees have same root-to-leaf paths
     */
    public boolean samePaths(Node root1, Node root2) {
        List<List<Integer>> paths1 = paths(root1);
        List<List<Integer>> paths2 = paths(root2);
        
        if (paths1.size() != paths2.size()) return false;
        
        // Sort paths for comparison
        paths1.sort((a, b) -> {
            for (int i = 0; i < Math.min(a.size(), b.size()); i++) {
                if (!a.get(i).equals(b.get(i))) {
                    return a.get(i) - b.get(i);
                }
            }
            return a.size() - b.size();
        });
        
        paths2.sort((a, b) -> {
            for (int i = 0; i < Math.min(a.size(), b.size()); i++) {
                if (!a.get(i).equals(b.get(i))) {
                    return a.get(i) - b.get(i);
                }
            }
            return a.size() - b.size();
        });
        
        return paths1.equals(paths2);
    }
    
    /**
     * Find all root-to-leaf paths where each node's value is prime
     */
    public List<List<Integer>> primeValuePaths(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        primePathsDFS(root, new ArrayList<>(), result);
        return result;
    }
    
    private void primePathsDFS(Node node, List<Integer> path, List<List<Integer>> result) {
        if (node == null) return;
        
        // Check if current node value is prime
        if (!isPrime(node.data)) return;
        
        path.add(node.data);
        
        if (node.left == null && node.right == null) {
            result.add(new ArrayList<>(path));
        } else {
            primePathsDFS(node.left, path, result);
            primePathsDFS(node.right, path, result);
        }
        
        path.remove(path.size() - 1);
    }
    
    private boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }
    
    // ============================
    // COMPLEXITY ANALYSIS
    // ============================
    
    /**
     * Complexity Comparison:
     * 
     * | Approach                | Time Complexity | Space Complexity | Pros                          | Cons                          |
     * |-------------------------|-----------------|------------------|-------------------------------|-------------------------------|
     * | DFS with Backtracking   | O(n)            | O(h)             | Memory efficient, clean       | Recursion depth limit         |
     * | BFS with Path Queue     | O(n)            | O(n)             | Level-order, avoids recursion | More memory for queue         |
     * | DFS without Backtracking| O(n * h)        | O(h²)            | Simpler logic                 | Creates many list copies      |
     * | Iterative DFS           | O(n)            | O(h)             | Avoids recursion              | More complex implementation   |
     * 
     * Where:
     * - n = number of nodes
     * - h = tree height (log n for balanced, n for skewed)
     */
}