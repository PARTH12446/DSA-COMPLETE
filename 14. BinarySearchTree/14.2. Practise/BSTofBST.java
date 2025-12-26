/** Largest BST in Binary Tree. */
public class BSTofBST {
    static class Node { int data; Node left,right; Node(int d){data=d;} }

    static class NodeVal {
        int minNode, maxNode, maxSize;
        NodeVal(int minNode, int maxNode, int maxSize) {
            this.minNode = minNode; this.maxNode = maxNode; this.maxSize = maxSize;
        }
    }

    public int largestBst(Node root) {
        return helper(root).maxSize;
    }

    private NodeVal helper(Node root) {
        if (root == null) return new NodeVal((int)1e10, (int)-1e10, 0);
        NodeVal left = helper(root.left);
        NodeVal right = helper(root.right);
        if (left.maxNode < root.data && root.data < right.minNode) {
            return new NodeVal(Math.min(root.data, left.minNode),
                               Math.max(root.data, right.maxNode),
                               1 + left.maxSize + right.maxSize);
        }
        return new NodeVal((int)-1e10, (int)1e10, Math.max(left.maxSize, right.maxSize));
    }
    public static void main(String[] args) {

    }
}
