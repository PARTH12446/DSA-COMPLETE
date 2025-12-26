/**
 * Problem: Add One to a Number Represented as Linked List
 * 
 * Problem Statement:
 * Given a non-negative integer represented as a linked list where each node
 * contains a single digit (most significant digit first), add 1 to the number
 * and return the result as a linked list.
 * 
 * Key Points:
 * - Number is stored with most significant digit first (normal order)
 * - Each node contains a single digit (0-9)
 * - The number is non-negative
 * - Result should be in the same format
 * 
 * Example:
 * Input: 1 -> 2 -> 3  (represents 123)
 * Output: 1 -> 2 -> 4 (represents 124)
 * 
 * Example with carry:
 * Input: 9 -> 9 -> 9  (represents 999)
 * Output: 1 -> 0 -> 0 -> 0 (represents 1000)
 * 
 * Approaches:
 * 1. Reverse, add one, reverse back (implemented)
 * 2. Recursive approach
 * 3. Using a stack
 * 4. Two-pointer approach
 */

public class AddOne {
    
    /**
     * Primary Solution: Reverse, Add One, Reverse Back
     * 
     * Algorithm:
     * 1. Reverse the linked list (so least significant digit is first)
     * 2. Add 1 to the first digit, propagate carry if needed
     * 3. If carry reaches the end, add a new node
     * 4. Reverse the list back to original order
     * 
     * Time Complexity: O(n) - Three passes through the list
     * Space Complexity: O(1) - In-place modification
     * 
     * @param head Head of linked list representing the number (MSD first)
     * @return Head of linked list representing number + 1 (MSD first)
     */
    public static ListNode addOne(ListNode head) {
        // Edge case: empty list
        if (head == null) {
            return new ListNode(1);
        }
        
        // Step 1: Reverse list to make LSD first
        head = reverse(head);
        
        // Step 2: Add 1 and propagate carry
        int carry = 1;
        ListNode curr = head;
        
        while (curr != null && carry > 0) {
            int sum = curr.val + carry;
            curr.val = sum % 10;  // Update current digit
            carry = sum / 10;     // Calculate carry
            
            // If we're at the last node and still have carry
            if (curr.next == null && carry > 0) {
                curr.next = new ListNode(carry);
                carry = 0;  // Carry consumed
            }
            
            curr = curr.next;
        }
        
        // Step 3: Reverse back to original order
        return reverse(head);
    }
    
    /**
     * Alternative: Recursive Approach
     * 
     * Advantages:
     * - Single pass through list
     * - No list reversal needed
     * 
     * Disadvantages:
     * - Risk of stack overflow for very long numbers
     * - Requires O(n) recursion stack space
     * 
     * @param head Head of linked list
     * @return Head of result list
     */
    public static ListNode addOneRecursive(ListNode head) {
        // Base case: empty list
        if (head == null) {
            return new ListNode(1);
        }
        
        // Recursively process the rest of the list
        int carry = addOneHelper(head);
        
        // If there's a carry after processing all nodes
        if (carry > 0) {
            ListNode newHead = new ListNode(carry);
            newHead.next = head;
            return newHead;
        }
        
        return head;
    }
    
    /**
     * Helper method for recursive approach
     * 
     * @param node Current node being processed
     * @return Carry to propagate to previous digit
     */
    private static int addOneHelper(ListNode node) {
        // Base case: end of list, add 1
        if (node.next == null) {
            int sum = node.val + 1;
            node.val = sum % 10;
            return sum / 10;  // Return carry
        }
        
        // Recursive case: process next node first
        int carry = addOneHelper(node.next);
        
        if (carry > 0) {
            int sum = node.val + carry;
            node.val = sum % 10;
            return sum / 10;
        }
        
        return 0;  // No carry to propagate
    }
    
    /**
     * Alternative: Using Stack
     * 
     * Advantages:
     * - Clear logic for carry propagation
     * - No recursion stack overflow risk
     * 
     * Disadvantages:
     * - Extra O(n) space for stack
     * - Multiple passes
     */
    public static ListNode addOneStack(ListNode head) {
        if (head == null) {
            return new ListNode(1);
        }
        
        // Push all nodes to stack
        java.util.Stack<ListNode> stack = new java.util.Stack<>();
        ListNode curr = head;
        while (curr != null) {
            stack.push(curr);
            curr = curr.next;
        }
        
        // Add 1 and propagate carry
        int carry = 1;
        while (!stack.isEmpty() && carry > 0) {
            ListNode node = stack.pop();
            int sum = node.val + carry;
            node.val = sum % 10;
            carry = sum / 10;
        }
        
        // If carry remains after processing all nodes
        if (carry > 0) {
            ListNode newHead = new ListNode(carry);
            newHead.next = head;
            return newHead;
        }
        
        return head;
    }
    
    /**
     * Alternative: Two-pointer approach (find last non-9 digit)
     * 
     * Algorithm:
     * 1. Find the rightmost digit that is not 9
     * 2. Increment that digit by 1
     * 3. Set all following digits (which are 9s) to 0
     * 4. If all digits are 9, add a new head node with 1
     * 
     * Advantages:
     * - Single pass
     * - No extra space
     * - No list reversal
     */
    public static ListNode addOneTwoPointer(ListNode head) {
        if (head == null) {
            return new ListNode(1);
        }
        
        // Step 1: Find the rightmost non-9 digit
        ListNode lastNonNine = null;
        ListNode curr = head;
        
        while (curr != null) {
            if (curr.val != 9) {
                lastNonNine = curr;
            }
            curr = curr.next;
        }
        
        // Case 1: All digits are 9
        if (lastNonNine == null) {
            ListNode newHead = new ListNode(1);
            newHead.next = head;
            
            // Set all digits to 0
            curr = head;
            while (curr != null) {
                curr.val = 0;
                curr = curr.next;
            }
            
            return newHead;
        }
        
        // Case 2: Found a non-9 digit
        // Increment the last non-9 digit
        lastNonNine.val++;
        
        // Set all following digits to 0
        curr = lastNonNine.next;
        while (curr != null) {
            curr.val = 0;
            curr = curr.next;
        }
        
        return head;
    }
    
    /**
     * Helper: Reverse a linked list
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    private static ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        
        while (curr != null) {
            ListNode next = curr.next;  // Save next node
            curr.next = prev;           // Reverse current node's pointer
            prev = curr;                // Move prev forward
            curr = next;                // Move curr forward
        }
        
        return prev;  // New head
    }
    
    /**
     * Helper: Create linked list from array
     */
    public static ListNode arrayToList(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int num : arr) {
            curr.next = new ListNode(num);
            curr = curr.next;
        }
        return dummy.next;
    }
    
    /**
     * Helper: Print linked list
     */
    public static void printList(ListNode head) {
        ListNode curr = head;
        while (curr != null) {
            System.out.print(curr.val);
            if (curr.next != null) {
                System.out.print(" -> ");
            }
            curr = curr.next;
        }
        System.out.println();
    }
    
    /**
     * Helper: Convert list to number (for verification)
     */
    public static long listToNumber(ListNode head) {
        long num = 0;
        ListNode curr = head;
        
        while (curr != null) {
            num = num * 10 + curr.val;
            curr = curr.next;
        }
        
        return num;
    }
    
    /**
     * Extended: Add any number (not just 1)
     * 
     * @param head Head of linked list
     * @param number Number to add (0-9)
     * @return Result list
     */
    public static ListNode addNumber(ListNode head, int number) {
        if (number == 0) {
            return head;
        }
        
        head = reverse(head);
        int carry = number;
        ListNode curr = head;
        
        while (curr != null && carry > 0) {
            int sum = curr.val + carry;
            curr.val = sum % 10;
            carry = sum / 10;
            
            if (curr.next == null && carry > 0) {
                curr.next = new ListNode(carry);
                carry = 0;
            }
            
            curr = curr.next;
        }
        
        return reverse(head);
    }
    
    /**
     * Extended: Add one to a number stored in reverse order (LSD first)
     * 
     * @param head Head of list (LSD first)
     * @return Result list (LSD first)
     */
    public static ListNode addOneReverseOrder(ListNode head) {
        if (head == null) {
            return new ListNode(1);
        }
        
        int carry = 1;
        ListNode curr = head;
        ListNode prev = null;
        
        while (curr != null && carry > 0) {
            int sum = curr.val + carry;
            curr.val = sum % 10;
            carry = sum / 10;
            
            prev = curr;
            curr = curr.next;
        }
        
        if (carry > 0) {
            prev.next = new ListNode(carry);
        }
        
        return head;
    }
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        System.out.println("=== Add One to Number Represented as Linked List ===\n");
        
        // Test Case 1: Basic addition (no carry)
        System.out.println("1. Basic Addition (No Carry):");
        ListNode test1 = arrayToList(new int[]{1, 2, 3});  // 123
        System.out.print("Input:  ");
        printList(test1);
        System.out.println("Number: " + listToNumber(test1));
        
        ListNode result1 = addOne(test1);
        System.out.print("Output: ");
        printList(result1);
        System.out.println("Result: " + listToNumber(result1));
        System.out.println("Expected: 124");
        
        // Test Case 2: Addition with carry
        System.out.println("\n2. Addition with Single Carry:");
        ListNode test2 = arrayToList(new int[]{1, 2, 9});  // 129
        System.out.print("Input:  ");
        printList(test2);
        
        ListNode result2 = addOne(test2);
        System.out.print("Output: ");
        printList(result2);
        System.out.println("Result: " + listToNumber(result2));
        System.out.println("Expected: 130");
        
        // Test Case 3: Multiple carries (all 9s)
        System.out.println("\n3. All 9s (Multiple Carries):");
        ListNode test3 = arrayToList(new int[]{9, 9, 9});  // 999
        System.out.print("Input:  ");
        printList(test3);
        
        ListNode result3 = addOne(test3);
        System.out.print("Output: ");
        printList(result3);
        System.out.println("Result: " + listToNumber(result3));
        System.out.println("Expected: 1000");
        
        // Test Case 4: Single digit
        System.out.println("\n4. Single Digit:");
        ListNode test4 = arrayToList(new int[]{9});  // 9
        System.out.print("Input:  ");
        printList(test4);
        
        ListNode result4 = addOne(test4);
        System.out.print("Output: ");
        printList(result4);
        System.out.println("Result: " + listToNumber(result4));
        System.out.println("Expected: 10");
        
        // Test Case 5: Zero
        System.out.println("\n5. Zero:");
        ListNode test5 = arrayToList(new int[]{0});  // 0
        System.out.print("Input:  ");
        printList(test5);
        
        ListNode result5 = addOne(test5);
        System.out.print("Output: ");
        printList(result5);
        System.out.println("Result: " + listToNumber(result5));
        System.out.println("Expected: 1");
        
        // Test Case 6: Large number with random 9s
        System.out.println("\n6. Large Number with Random 9s:");
        ListNode test6 = arrayToList(new int[]{4, 5, 9, 9, 3, 9, 8});  // 4599398
        System.out.print("Input:  ");
        printList(test6);
        System.out.println("Number: " + listToNumber(test6));
        
        ListNode result6 = addOne(test6);
        System.out.print("Output: ");
        printList(result6);
        System.out.println("Result: " + listToNumber(result6));
        System.out.println("Expected: 4599399");
        
        // Test Case 7: Compare different methods
        System.out.println("\n7. Method Comparison:");
        ListNode test7 = arrayToList(new int[]{8, 9, 9, 9});  // 8999
        System.out.print("Input: ");
        printList(test7);
        
        System.out.println("\nReverse Method:");
        ListNode revResult = addOne(test7);
        printList(revResult);
        
        System.out.println("Recursive Method:");
        ListNode recResult = addOneRecursive(arrayToList(new int[]{8, 9, 9, 9}));
        printList(recResult);
        
        System.out.println("Stack Method:");
        ListNode stackResult = addOneStack(arrayToList(new int[]{8, 9, 9, 9}));
        printList(stackResult);
        
        System.out.println("Two-Pointer Method:");
        ListNode twoPtrResult = addOneTwoPointer(arrayToList(new int[]{8, 9, 9, 9}));
        printList(twoPtrResult);
        
        // Test Case 8: Extended functionality - add any number
        System.out.println("\n8. Add Any Number (Not Just 1):");
        ListNode test8 = arrayToList(new int[]{1, 2, 3});  // 123
        System.out.print("Input: 123 + 7 = ");
        
        ListNode result8 = addNumber(test8, 7);  // Add 7
        printList(result8);
        System.out.println("Result: " + listToNumber(result8));
        System.out.println("Expected: 130");
        
        // Test Case 9: Reverse order storage
        System.out.println("\n9. Reverse Order Storage (LSD First):");
        ListNode test9 = arrayToList(new int[]{3, 2, 1});  // 123 in reverse (LSD first)
        System.out.print("Input (LSD first): ");
        printList(test9);
        
        ListNode result9 = addOneReverseOrder(test9);
        System.out.print("Output (LSD first): ");
        printList(result9);
        System.out.println("Represents: 124 (4->2->1 in LSD first)");
        
        // Test Case 10: Edge cases
        System.out.println("\n10. Edge Cases:");
        
        // Null input
        System.out.print("Null input: ");
        ListNode nullResult = addOne(null);
        printList(nullResult);
        
        // Very large number (performance test)
        System.out.println("\nPerformance Test (Large Number):");
        int[] largeArr = new int[1000];
        for (int i = 0; i < 1000; i++) {
            largeArr[i] = 9;  // All 9s - worst case
        }
        
        ListNode largeList = arrayToList(largeArr);
        long startTime = System.currentTimeMillis();
        ListNode largeResult = addOne(largeList);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Added 1 to 1000-digit number (all 9s) in " + 
                          (endTime - startTime) + " ms");
        
        // Verify result has correct length
        int length = 0;
        ListNode temp = largeResult;
        while (temp != null) {
            length++;
            temp = temp.next;
        }
        System.out.println("Result length: " + length + " (expected: 1001)");
        
        // Test Case 11: Special patterns
        System.out.println("\n11. Special Patterns:");
        
        // Alternating 9s and non-9s
        ListNode pattern1 = arrayToList(new int[]{1, 9, 2, 9, 3, 9});  // 192939
        System.out.print("Pattern 192939 + 1 = ");
        printList(addOne(pattern1));
        
        // Number ending with many 9s
        ListNode pattern2 = arrayToList(new int[]{7, 8, 9, 9, 9, 9});  // 789999
        System.out.print("Pattern 789999 + 1 = ");
        printList(addOne(pattern2));
        
        // Test Case 12: Visualization of algorithm steps
        System.out.println("\n12. Algorithm Step-by-Step Visualization:");
        ListNode demo = arrayToList(new int[]{9, 9, 8});  // 998
        System.out.println("Input: 9 -> 9 -> 8 (998)");
        
        System.out.println("\nStep 1: Reverse list");
        ListNode reversed = reverse(demo);
        System.out.print("Reversed: ");
        printList(reversed);  // 8 -> 9 -> 9
        
        System.out.println("\nStep 2: Add 1 and propagate carry");
        System.out.println("Start: carry = 1");
        System.out.println("Node 1: 8 + 1 = 9, no carry");
        System.out.println("Node 2: 9 + 0 = 9, no carry");
        System.out.println("Node 3: 9 + 0 = 9, no carry");
        
        System.out.println("\nStep 3: Reverse back");
        ListNode finalResult = reverse(reversed);
        System.out.print("Final: ");
        printList(finalResult);  // 9 -> 9 -> 9
        System.out.println("Result: 999");
    }
    
    /**
     * Common Mistakes to Avoid:
     * 
     * 1. FORGETTING TO REVERSE BACK:
     *    ❌ Only reverse once, forget to reverse back
     *    ✅ Always reverse back to original order
     *    
     * 2. INCORRECT CARRY PROPAGATION:
     *    ❌ Stop when curr becomes null (misses final carry)
     *    ✅ Continue while carry > 0
     *    
     * 3. NOT HANDLING ALL 9s CASE:
     *    ❌ For 999, result should be 1000 (extra digit)
     *    ✅ Check if carry remains after last node
     *    
     * 4. MODIFYING ORIGINAL LIST:
     *    ❌ Modify input if you need to preserve it
     *    ✅ Create copy or document that input is modified
     *    
     * 5. EDGE CASES:
     *    - Empty list
     *    - Single digit
     *    - Zero
     *    - All 9s
     */
    
    /**
     * Complexity Analysis:
     * 
     * Reverse Method:
     * Time: O(3n) = O(n) - Three passes (reverse, add, reverse)
     * Space: O(1) - In-place modification
     * 
     * Recursive Method:
     * Time: O(n) - Single pass
     * Space: O(n) - Recursion stack
     * 
     * Stack Method:
     * Time: O(2n) = O(n) - Two passes
     * Space: O(n) - Stack storage
     * 
     * Two-pointer Method:
     * Time: O(n) - Single pass
     * Space: O(1) - In-place
     * 
     * Recommendation: Two-pointer method is optimal (O(n) time, O(1) space)
     */
    
    /**
     * Algorithm Walkthrough (Reverse Method):
     * 
     * Example: 199 + 1 = 200
     * 
     * Step 1: Reverse list
     *   1 -> 9 -> 9 becomes 9 -> 9 -> 1
     *   
     * Step 2: Add 1 with carry propagation
     *   Node 1: 9 + 1 = 10, digit=0, carry=1
     *   Node 2: 9 + 1 = 10, digit=0, carry=1  
     *   Node 3: 1 + 1 = 2, digit=2, carry=0
     *   Result: 0 -> 0 -> 2
     *   
     * Step 3: Reverse back
     *   0 -> 0 -> 2 becomes 2 -> 0 -> 0
     *   
     * Final: 200 ✓
     */
    
    /**
     * Variations and Extensions:
     * 
     * 1. ADD TWO NUMBERS: Similar to LeetCode #2
     * 2. ADD ONE TO BINARY NUMBER: For binary linked list
     * 3. SUBTRACT ONE: Handle borrow instead of carry
     * 4. ADD K: Add any integer k (not just 1)
     * 5. CIRCULAR NUMBER: Handle numbers in circular linked lists
     * 6. DOUBLY LINKED LIST: Account for prev pointers
     */
}