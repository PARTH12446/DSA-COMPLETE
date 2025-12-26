import java.util.ArrayList;
import java.util.List;

/**
 * Merges two Binary Search Trees (BSTs) into a single sorted list.
 * 
 * Approach: Use Morris Traversal to get inorder traversals of both BSTs in O(n) time and O(1) space,
 * then merge the two sorted lists.
 * 
 * Steps:
 * 1. Get inorder traversal of first BST (sorted list)
 * 2. Get inorder traversal of second BST (sorted list)  
 * 3. Merge the two sorted lists
 * 
 * Time Complexity: O(m + n) where m and n are sizes of the two trees
 * Space Complexity: O(m + n) for storing the merged list
 * 
 * Alternative approaches:
 * 1. Convert BSTs to DLLs (doubly linked lists), merge DLLs, convert back to BST
 * 2. Store inorder traversals in arrays, merge arrays, build balanced BST from sorted array
 */
public class Merge2BSTS {
    static class TreeNode { 
        int val; 
        TreeNode left, right; 
        TreeNode(int v) {
            val = v;
        }
    }

    /**
     * Morris Traversal based BST Iterator that provides inorder traversal
     * without recursion or explicit stack.
     * 
     * Morris Traversal Algorithm:
     * 1. Initialize current as root
     * 2. While current is not null:
     *    a. If current has no left child:
     *        - Visit current
     *        - Go to right child
     *    b. Else:
     *        - Find inorder predecessor (rightmost node in left subtree)
     *        - If predecessor's right is null:
     *            * Set predecessor's right to current (create thread)
     *            * Go to left child
     *        - Else (thread already exists):
     *            * Revert the thread (set predecessor's right to null)
     *            * Visit current
     *            * Go to right child
     * 
     * Space Complexity: O(1) - only uses a few pointers
     * Time Complexity: O(n) - each edge is traversed at most 3 times
     */
    static class BSTIterator {
        private final List<Integer> inorder = new ArrayList<>();
        private int ptr = 0;

        /**
         * Constructor that performs Morris inorder traversal and stores result.
         * @param root Root of BST to iterate over
         */
        BSTIterator(TreeNode root) {
            TreeNode curr = root;
            
            while (curr != null) {
                if (curr.left == null) {
                    // No left child, visit current node
                    inorder.add(curr.val);
                    curr = curr.right;
                } else {
                    // Find inorder predecessor
                    TreeNode prev = curr.left;
                    while (prev.right != null && prev.right != curr) {
                        prev = prev.right;
                    }
                    
                    if (prev.right == null) {
                        // Create thread to come back to current node
                        prev.right = curr;
                        curr = curr.left;
                    } else {
                        // Thread already exists, revert it
                        prev.right = null;
                        inorder.add(curr.val);
                        curr = curr.right;
                    }
                }
            }
        }

        public boolean hasNext() {
            return ptr < inorder.size();
        }

        public Integer next() {
            if (!hasNext()) return null;
            return inorder.get(ptr++);
        }
        
        /**
         * Resets iterator to beginning.
         */
        public void reset() {
            ptr = 0;
        }
    }
    
    /**
     * Alternative BST Iterator using explicit stack (more common approach).
     */
    static class BSTIteratorStack {
        private final Stack<TreeNode> stack = new Stack<>();
        
        BSTIteratorStack(TreeNode root) {
            pushLeft(root);
        }
        
        private void pushLeft(TreeNode node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
        
        public boolean hasNext() {
            return !stack.isEmpty();
        }
        
        public int next() {
            TreeNode node = stack.pop();
            pushLeft(node.right);
            return node.val;
        }
    }
    
    /**
     * Merges two BSTs by getting their inorder traversals and merging the sorted lists.
     * @param root1 First BST root
     * @param root2 Second BST root
     * @return List containing all elements from both BSTs in sorted order
     */
    public List<Integer> mergeBSTs(TreeNode root1, TreeNode root2) {
        // Get inorder traversals using Morris traversal
        BSTIterator it1 = new BSTIterator(root1);
        BSTIterator it2 = new BSTIterator(root2);
        
        List<Integer> merged = new ArrayList<>();
        
        // Merge two sorted lists
        Integer val1 = it1.hasNext() ? it1.next() : null;
        Integer val2 = it2.hasNext() ? it2.next() : null;
        
        while (val1 != null || val2 != null) {
            if (val1 == null) {
                merged.add(val2);
                val2 = it2.hasNext() ? it2.next() : null;
            } else if (val2 == null) {
                merged.add(val1);
                val1 = it1.hasNext() ? it1.next() : null;
            } else if (val1 < val2) {
                merged.add(val1);
                val1 = it1.hasNext() ? it1.next() : null;
            } else {
                merged.add(val2);
                val2 = it2.hasNext() ? it2.next() : null;
            }
        }
        
        return merged;
    }
    
    /**
     * Converts sorted list to balanced BST.
     * Useful for creating a new BST from merged lists.
     * @param sortedList Sorted list of values
     * @return Root of balanced BST
     */
    public TreeNode sortedListToBST(List<Integer> sortedList) {
        return buildBST(sortedList, 0, sortedList.size() - 1);
    }
    
    private TreeNode buildBST(List<Integer> list, int left, int right) {
        if (left > right) return null;
        
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(list.get(mid));
        
        root.left = buildBST(list, left, mid - 1);
        root.right = buildBST(list, mid + 1, right);
        
        return root;
    }
    
    /**
     * Merges two BSTs and returns a new balanced BST.
     * @param root1 First BST root
     * @param root2 Second BST root
     * @return Root of new balanced BST containing all elements
     */
    public TreeNode mergeBSTsToTree(TreeNode root1, TreeNode root2) {
        List<Integer> mergedList = mergeBSTs(root1, root2);
        return sortedListToBST(mergedList);
    }
    
    /**
     * Alternative: In-place merge by converting BSTs to DLLs, merging DLLs, and converting back.
     * More complex but doesn't create intermediate lists.
     */
    public TreeNode mergeBSTsInPlace(TreeNode root1, TreeNode root2) {
        // Step 1: Convert BSTs to sorted DLLs
        TreeNode head1 = bstToDLL(root1);
        TreeNode head2 = bstToDLL(root2);
        
        // Step 2: Merge two sorted DLLs
        TreeNode mergedHead = mergeDLLs(head1, head2);
        
        // Step 3: Convert merged DLL to balanced BST
        return dllToBST(mergedHead);
    }
    
    private TreeNode bstToDLL(TreeNode root) {
        if (root == null) return null;
        
        // Convert left subtree
        TreeNode leftHead = bstToDLL(root.left);
        
        // Find inorder predecessor (rightmost node in left subtree)
        TreeNode predecessor = null;
        if (leftHead != null) {
            predecessor = leftHead;
            while (predecessor.right != null) {
                predecessor = predecessor.right;
            }
            // Connect predecessor to current node
            predecessor.right = root;
            root.left = predecessor;
        }
        
        // Convert right subtree
        TreeNode rightHead = bstToDLL(root.right);
        if (rightHead != null) {
            rightHead.left = root;
            root.right = rightHead;
        }
        
        return leftHead != null ? leftHead : root;
    }
    
    private TreeNode mergeDLLs(TreeNode head1, TreeNode head2) {
        TreeNode dummy = new TreeNode(0);
        TreeNode current = dummy;
        
        while (head1 != null && head2 != null) {
            if (head1.val < head2.val) {
                current.right = head1;
                head1.left = current;
                head1 = head1.right;
            } else {
                current.right = head2;
                head2.left = current;
                head2 = head2.right;
            }
            current = current.right;
        }
        
        if (head1 != null) {
            current.right = head1;
            head1.left = current;
        }
        if (head2 != null) {
            current.right = head2;
            head2.left = current;
        }
        
        // Skip dummy node
        TreeNode result = dummy.right;
        if (result != null) {
            result.left = null;
        }
        return result;
    }
    
    private TreeNode dllToBST(TreeNode head) {
        if (head == null) return null;
        
        // Count nodes
        int count = 0;
        TreeNode temp = head;
        while (temp != null) {
            count++;
            temp = temp.right;
        }
        
        return dllToBSTRecursive(new TreeNode[]{head}, 0, count - 1);
    }
    
    private TreeNode dllToBSTRecursive(TreeNode[] current, int left, int right) {
        if (left > right) return null;
        
        int mid = left + (right - left) / 2;
        
        // Build left subtree
        TreeNode leftChild = dllToBSTRecursive(current, left, mid - 1);
        
        // Current node is at the front of the list
        TreeNode root = current[0];
        current[0] = current[0].right;
        
        // Build right subtree
        TreeNode rightChild = dllToBSTRecursive(current, mid + 1, right);
        
        root.left = leftChild;
        root.right = rightChild;
        
        return root;
    }
    
    /**
     * Test cases and examples
     */
    public static void main(String[] args) {
        Merge2BSTS merger = new Merge2BSTS();
        
        // Build first BST:
        //       3
        //      / \
        //     1   5
        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(1);
        root1.right = new TreeNode(5);
        
        // Build second BST:
        //       4
        //      / \
        //     2   6
        TreeNode root2 = new TreeNode(4);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(6);
        
        System.out.println("BST 1 structure:");
        printTree(root1, "", true);
        System.out.println("\nBST 2 structure:");
        printTree(root2, "", true);
        
        // Test BSTIterator
        System.out.println("\n=== Testing BSTIterator (Morris Traversal) ===");
        BSTIterator it1 = new BSTIterator(root1);
        System.out.print("BST 1 inorder traversal: ");
        while (it1.hasNext()) {
            System.out.print(it1.next() + " ");
        }
        System.out.println();
        
        // Test merging BSTs
        System.out.println("\n=== Merging Two BSTs ===");
        List<Integer> mergedList = merger.mergeBSTs(root1, root2);
        System.out.println("Merged sorted list: " + mergedList);
        System.out.println("Expected: [1, 2, 3, 4, 5, 6]");
        
        // Test creating balanced BST from merged list
        System.out.println("\n=== Creating Balanced BST from Merged List ===");
        TreeNode mergedBST = merger.sortedListToBST(mergedList);
        System.out.println("Balanced BST structure:");
        printTree(mergedBST, "", true);
        
        // Test merge directly to tree
        System.out.println("\n=== Merge BSTs to Tree (one step) ===");
        TreeNode mergedTree = merger.mergeBSTsToTree(root1, root2);
        System.out.println("Merged tree structure:");
        printTree(mergedTree, "", true);
        
        // Verify it's a valid BST
        System.out.println("\nIs merged tree a valid BST? " + isValidBST(mergedTree));
        
        // Test with empty trees
        System.out.println("\n=== Edge Cases ===");
        System.out.println("Merge empty tree with BST:");
        List<Integer> mergedEmpty = merger.mergeBSTs(null, root1);
        System.out.println("Result: " + mergedEmpty);
        
        // Test both empty
        System.out.println("Merge two empty trees:");
        List<Integer> bothEmpty = merger.mergeBSTs(null, null);
        System.out.println("Result: " + bothEmpty);
        
        // Test with duplicates
        TreeNode dup1 = new TreeNode(2);
        dup1.left = new TreeNode(2);
        dup1.right = new TreeNode(3);
        
        TreeNode dup2 = new TreeNode(2);
        dup2.right = new TreeNode(4);
        
        System.out.println("\n=== Merging BSTs with duplicates ===");
        List<Integer> mergedWithDups = merger.mergeBSTs(dup1, dup2);
        System.out.println("Merged list with duplicates: " + mergedWithDups);
    }
    
    /**
     * Helper to print tree structure visually
     */
    private static void printTree(TreeNode root, String indent, boolean last) {
        if (root == null) return;
        
        System.out.print(indent);
        if (last) {
            System.out.print("└─");
            indent += "  ";
        } else {
            System.out.print("├─");
            indent += "│ ";
        }
        System.out.println(root.val);
        
        printTree(root.left, indent, false);
        printTree(root.right, indent, true);
    }
    
    /**
     * Helper to validate BST
     */
    private static boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    private static boolean isValidBST(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return isValidBST(node.left, min, node.val) && 
               isValidBST(node.right, node.val, max);
    }
    
    /**
     * Stack implementation for BSTIteratorStack
     */
    static class Stack<T> {
        private java.util.ArrayList<T> list = new java.util.ArrayList<>();
        
        public void push(T item) {
            list.add(item);
        }
        
        public T pop() {
            return list.remove(list.size() - 1);
        }
        
        public boolean isEmpty() {
            return list.isEmpty();
        }
        
        public T peek() {
            return list.get(list.size() - 1);
        }
    }
}