import java.util.*;

/** 
 * Serialize and Deserialize Binary Tree
 * Problem: 297. Serialize and Deserialize Binary Tree (LeetCode)
 * 
 * Approaches: Preorder DFS, Level-order BFS, JSON format, etc.
 * This implementation uses Preorder DFS with null markers.
 */
public class SerialDeserial {
    static class TreeNode { 
        int val; 
        TreeNode left, right; 
        TreeNode(int v) { val = v; } 
    }

    // ============================
    // APPROACH 1: Preorder DFS (Recursive)
    // ============================
    
    /**
     * Serialize binary tree to string using preorder DFS
     * Format: "1,2,N,N,3,4,N,N,5,N,N,"
     * 
     * Time: O(n), Space: O(n) for recursion stack
     */
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeDfs(root, sb);
        return sb.toString();
    }

    /**
     * Helper for DFS serialization
     */
    private void serializeDfs(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("N,");  // 'N' represents null
            return;
        }
        sb.append(node.val).append(',');
        serializeDfs(node.left, sb);
        serializeDfs(node.right, sb);
    }

    // Index for deserialization (needs to be shared across recursive calls)
    private int idx;

    /**
     * Deserialize string to binary tree using preorder DFS
     */
    public TreeNode deserialize(String data) {
        // Split by comma and filter empty strings
        String[] vals = data.split(",");
        // Handle trailing comma if present
        if (vals.length > 0 && vals[vals.length - 1].isEmpty()) {
            vals = Arrays.copyOfRange(vals, 0, vals.length - 1);
        }
        idx = 0;
        return deserializeDfs(vals);
    }

    /**
     * Helper for DFS deserialization
     */
    private TreeNode deserializeDfs(String[] vals) {
        if (idx >= vals.length || vals[idx].equals("N")) {
            idx++;
            return null;
        }
        TreeNode node = new TreeNode(Integer.parseInt(vals[idx++]));
        node.left = deserializeDfs(vals);
        node.right = deserializeDfs(vals);
        return node;
    }

    // ============================
    // APPROACH 2: Level-order BFS
    // ============================
    
    /**
     * Serialize using level-order BFS
     * Format: "1,2,3,N,N,4,5,N,N,N,N,"
     * More intuitive for human reading
     */
    public String serializeBFS(TreeNode root) {
        if (root == null) return "N,";
        
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                sb.append("N,");
                continue;
            }
            sb.append(node.val).append(",");
            queue.offer(node.left);
            queue.offer(node.right);
        }
        
        return sb.toString();
    }
    
    /**
     * Deserialize level-order BFS string
     */
    public TreeNode deserializeBFS(String data) {
        String[] vals = data.split(",");
        if (vals.length == 0 || vals[0].equals("N")) return null;
        
        TreeNode root = new TreeNode(Integer.parseInt(vals[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        int i = 1;
        while (!queue.isEmpty() && i < vals.length) {
            TreeNode node = queue.poll();
            
            // Left child
            if (!vals[i].equals("N")) {
                node.left = new TreeNode(Integer.parseInt(vals[i]));
                queue.offer(node.left);
            }
            i++;
            
            // Right child
            if (i < vals.length && !vals[i].equals("N")) {
                node.right = new TreeNode(Integer.parseInt(vals[i]));
                queue.offer(node.right);
            }
            i++;
        }
        
        return root;
    }

    // ============================
    // APPROACH 3: Compact format (without trailing nulls)
    // ============================
    
    /**
     * Compact serialization - omits trailing nulls
     * More space-efficient for sparse trees
     */
    public String serializeCompact(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                sb.append("N,");
                continue;
            }
            sb.append(node.val).append(",");
            queue.offer(node.left);
            queue.offer(node.right);
        }
        
        // Remove trailing nulls
        String result = sb.toString();
        while (result.endsWith("N,")) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    // ============================
    // APPROACH 4: JSON-like format
    // ============================
    
    /**
     * JSON-like format: {"val":1,"left":{"val":2},"right":{"val":3}}
     * Human-readable but more verbose
     */
    public String serializeJson(TreeNode root) {
        if (root == null) return "null";
        return String.format("{\"val\":%d,\"left\":%s,\"right\":%s}",
                           root.val,
                           serializeJson(root.left),
                           serializeJson(root.right));
    }
    
    // For JSON deserialization, we'd need a proper parser
    // This is simplified and assumes valid format
    private int jsonIdx;
    
    public TreeNode deserializeJson(String json) {
        jsonIdx = 0;
        return parseJsonNode(json);
    }
    
    private TreeNode parseJsonNode(String json) {
        if (json.startsWith("null", jsonIdx)) {
            jsonIdx += 4;
            return null;
        }
        
        // Skip "{"
        jsonIdx++;
        
        // Parse "val":X
        skip(json, "\"val\":");
        int val = parseInt(json);
        
        // Skip comma and parse left
        skip(json, ",\"left\":");
        TreeNode left = parseJsonNode(json);
        
        // Skip comma and parse right
        skip(json, ",\"right\":");
        TreeNode right = parseJsonNode(json);
        
        // Skip "}"
        jsonIdx++;
        
        TreeNode node = new TreeNode(val);
        node.left = left;
        node.right = right;
        return node;
    }
    
    private void skip(String json, String pattern) {
        while (jsonIdx < json.length() && json.charAt(jsonIdx) != pattern.charAt(0)) {
            jsonIdx++;
        }
        jsonIdx += pattern.length();
    }
    
    private int parseInt(String json) {
        int start = jsonIdx;
        while (jsonIdx < json.length() && Character.isDigit(json.charAt(jsonIdx))) {
            jsonIdx++;
        }
        return Integer.parseInt(json.substring(start, jsonIdx));
    }

    // ============================
    // TESTING AND EXAMPLES
    // ============================
    
    public static void main(String[] args) {
        SerialDeserial codec = new SerialDeserial();
        
        // Test Case 1: Standard tree
        //     1
        //    / \
        //   2   3
        //      / \
        //     4   5
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.right.left = new TreeNode(4);
        root1.right.right = new TreeNode(5);
        
        System.out.println("=== Test Case 1: Standard Tree ===");
        String serialized1 = codec.serialize(root1);
        System.out.println("Preorder DFS serialized: " + serialized1);
        
        TreeNode deserialized1 = codec.deserialize(serialized1);
        System.out.println("Trees equal: " + areTreesEqual(root1, deserialized1));
        
        String bfsSerialized1 = codec.serializeBFS(root1);
        System.out.println("BFS serialized: " + bfsSerialized1);
        
        // Test Case 2: Single node
        System.out.println("\n=== Test Case 2: Single Node ===");
        TreeNode root2 = new TreeNode(42);
        String serialized2 = codec.serialize(root2);
        System.out.println("Serialized: " + serialized2);
        System.out.println("Deserialized correctly: " + 
                          areTreesEqual(root2, codec.deserialize(serialized2)));
        
        // Test Case 3: Empty tree
        System.out.println("\n=== Test Case 3: Empty Tree ===");
        String serialized3 = codec.serialize(null);
        System.out.println("Serialized: " + serialized3);
        System.out.println("Deserialized is null: " + (codec.deserialize(serialized3) == null));
        
        // Test Case 4: Left-skewed tree
        //   1
        //  /
        // 2
        ///
        //3
        System.out.println("\n=== Test Case 4: Left-skewed Tree ===");
        TreeNode root4 = new TreeNode(1);
        root4.left = new TreeNode(2);
        root4.left.left = new TreeNode(3);
        String serialized4 = codec.serialize(root4);
        System.out.println("Serialized: " + serialized4);
        System.out.println("Deserialized correctly: " + 
                          areTreesEqual(root4, codec.deserialize(serialized4)));
        
        // Test Case 5: Tree with negative values
        System.out.println("\n=== Test Case 5: Tree with Negative Values ===");
        TreeNode root5 = new TreeNode(-1);
        root5.left = new TreeNode(2);
        root5.right = new TreeNode(-3);
        String serialized5 = codec.serialize(root5);
        System.out.println("Serialized: " + serialized5);
        System.out.println("Deserialized correctly: " + 
                          areTreesEqual(root5, codec.deserialize(serialized5)));
        
        // Test Case 6: Performance test with large tree
        System.out.println("\n=== Test Case 6: Large Tree ===");
        TreeNode root6 = createLargeTree(5); // 31 nodes
        String serialized6 = codec.serialize(root6);
        System.out.println("Serialization successful, length: " + serialized6.length());
        TreeNode deserialized6 = codec.deserialize(serialized6);
        System.out.println("Deserialization successful, trees equal: " + 
                          areTreesEqual(root6, deserialized6));
        
        // Test different serialization methods
        System.out.println("\n=== Comparison of Serialization Methods ===");
        System.out.println("Preorder DFS: " + codec.serialize(root1).length() + " chars");
        System.out.println("Level-order BFS: " + codec.serializeBFS(root1).length() + " chars");
        System.out.println("Compact BFS: " + codec.serializeCompact(root1).length() + " chars");
        
        // Test JSON-like serialization
        System.out.println("\n=== JSON-like Serialization ===");
        String json = codec.serializeJson(root1);
        System.out.println("JSON: " + json);
    }
    
    // Helper to create a complete binary tree of given depth
    private static TreeNode createLargeTree(int depth) {
        return createTreeHelper(1, depth);
    }
    
    private static TreeNode createTreeHelper(int val, int depth) {
        if (depth == 0) return null;
        TreeNode node = new TreeNode(val);
        node.left = createTreeHelper(val * 2, depth - 1);
        node.right = createTreeHelper(val * 2 + 1, depth - 1);
        return node;
    }
    
    // Helper to compare two trees
    private static boolean areTreesEqual(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        if (t1.val != t2.val) return false;
        return areTreesEqual(t1.left, t2.left) && areTreesEqual(t1.right, t2.right);
    }
    
    // ============================
    // ENHANCED VERSION WITH ERROR HANDLING
    // ============================
    
    public static class EnhancedCodec {
        
        /**
         * Enhanced serialize with error checking
         */
        public String serialize(TreeNode root) {
            try {
                StringBuilder sb = new StringBuilder();
                serializeHelper(root, sb);
                // Remove trailing comma if present
                if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ',') {
                    sb.setLength(sb.length() - 1);
                }
                return sb.toString();
            } catch (Exception e) {
                throw new RuntimeException("Serialization failed", e);
            }
        }
        
        private void serializeHelper(TreeNode node, StringBuilder sb) {
            if (node == null) {
                sb.append("#,");  // Using # instead of N for null
                return;
            }
            // Validate node value (could be any integer)
            sb.append(node.val).append(",");
            serializeHelper(node.left, sb);
            serializeHelper(node.right, sb);
        }
        
        /**
         * Enhanced deserialize with better error handling
         */
        public TreeNode deserialize(String data) {
            if (data == null || data.isEmpty()) {
                return null;
            }
            
            try {
                String[] tokens = data.split(",");
                if (tokens.length == 0) return null;
                
                int[] index = new int[]{0};  // Using array to simulate pointer
                return deserializeHelper(tokens, index);
            } catch (Exception e) {
                throw new RuntimeException("Deserialization failed for data: " + 
                                          (data.length() > 50 ? data.substring(0, 50) + "..." : data), e);
            }
        }
        
        private TreeNode deserializeHelper(String[] tokens, int[] index) {
            if (index[0] >= tokens.length) {
                throw new IllegalArgumentException("Unexpected end of input");
            }
            
            String token = tokens[index[0]++];
            if (token.equals("#")) {
                return null;
            }
            
            try {
                int val = Integer.parseInt(token);
                TreeNode node = new TreeNode(val);
                node.left = deserializeHelper(tokens, index);
                node.right = deserializeHelper(tokens, index);
                return node;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }
        
        /**
         * Binary serialization (more space-efficient)
         */
        public byte[] serializeBinary(TreeNode root) {
            List<Byte> bytes = new ArrayList<>();
            serializeBinaryHelper(root, bytes);
            byte[] result = new byte[bytes.size()];
            for (int i = 0; i < bytes.size(); i++) {
                result[i] = bytes.get(i);
            }
            return result;
        }
        
        private void serializeBinaryHelper(TreeNode node, List<Byte> bytes) {
            if (node == null) {
                bytes.add((byte) 0xFF);  // Null marker
                return;
            }
            
            // Store value as 4 bytes (int)
            int val = node.val;
            bytes.add((byte) (val >> 24));
            bytes.add((byte) (val >> 16));
            bytes.add((byte) (val >> 8));
            bytes.add((byte) val);
            
            serializeBinaryHelper(node.left, bytes);
            serializeBinaryHelper(node.right, bytes);
        }
    }
    
    // ============================
    // COMPARISON OF APPROACHES
    // ============================
    
    /**
     * Comparison table of different serialization approaches:
     * 
     * | Approach       | Format                      | Space | Readability | Edge Cases | Best For          |
     * |----------------|-----------------------------|-------|-------------|------------|-------------------|
     * | Preorder DFS   | "1,2,N,N,3,N,N,"            | Good  | Medium      | Handles all| General purpose   |
     * | Level-order BFS| "1,2,3,N,N,4,5,N,N,N,N,"    | Fair  | Good        | Handles all| Debugging, display|
     * | JSON           | {"val":1,"left":{...}}      | Poor  | Excellent   | Handles all| Web APIs, configs|
     * | Binary         | 0x00 0x00 0x00 0x01 ...     | Best  | None        | Complex    | Network transfer  |
     * | Compact        | "1,2,3,4,5" (no trailing N) | Varies| Medium      | Some       | Sparse trees      |
     */
}