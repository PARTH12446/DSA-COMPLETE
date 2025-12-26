/**
 * Problem: Add Two Numbers
 * 
 * LeetCode 2: https://leetcode.com/problems/add-two-numbers/
 * 
 * Problem Statement:
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order, and each node contains a single digit.
 * Add the two numbers and return the sum as a linked list.
 * 
 * Key Points:
 * - Numbers are stored in reverse order (least significant digit first)
 * - Each node contains a single digit (0-9)
 * - The two numbers are non-negative
 * - Result should also be in reverse order
 * 
 * Example:
 * Input: l1 = [2,4,3], l2 = [5,6,4]
 * Explanation: 342 + 465 = 807
 * Output: [7,0,8]
 * 
 * Visual Representation:
 * Input: (2 → 4 → 3) represents 342
 *        (5 → 6 → 4) represents 465
 * Output: (7 → 0 → 8) represents 807
 */

public class AddNumbers {
    
    /**
     * Primary Solution: Add Two Numbers in Linked List Form
     * 
     * Algorithm:
     * 1. Create a dummy head node to simplify edge cases
     * 2. Initialize current pointer to dummy, carry to 0
     * 3. While either list has nodes OR carry is not zero:
     *    a. Sum = carry + l1.val (if exists) + l2.val (if exists)
     *    b. Carry = sum / 10 (integer division)
     *    c. Digit = sum % 10 (remainder)
     *    d. Create new node with digit, attach to result
     *    e. Move pointers forward
     * 4. Return dummy.next (actual head of result)
     * 
     * Time Complexity: O(max(m, n)) where m,n are lengths of lists
     * Space Complexity: O(max(m, n)) for result list
     * 
     * @param l1 First number as linked list (reverse order)
     * @param l2 Second number as linked list (reverse order)
     * @return Sum as linked list (reverse order)
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // Dummy node simplifies edge cases (empty list, carry at end)
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy; // Pointer to build result list
        int carry = 0;
        
        // Continue while either list has digits OR there's a carry
        while (l1 != null || l2 != null || carry != 0) {
            int sum = carry; // Start with carry from previous digit
            
            // Add digit from l1 if it exists
            if (l1 != null) { 
                sum += l1.val; 
                l1 = l1.next; 
            }
            
            // Add digit from l2 if it exists
            if (l2 != null) { 
                sum += l2.val; 
                l2 = l2.next; 
            }
            
            // Calculate carry for next digit
            carry = sum / 10;
            
            // Create new node with current digit
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
        }
        
        return dummy.next; // Skip dummy node
    }
    
    /**
     * Alternative: Recursive Solution
     * 
     * Advantages:
     * - More elegant code
     * - No explicit loop management
     * 
     * Disadvantages:
     * - Risk of stack overflow for very long numbers
     * - Less efficient for large inputs
     * 
     * @param l1 First number
     * @param l2 Second number
     * @param carry Carry from previous addition
     * @return Result list node
     */
    public static ListNode addTwoNumbersRecursive(ListNode l1, ListNode l2) {
        return addTwoNumbersRecursive(l1, l2, 0);
    }
    
    private static ListNode addTwoNumbersRecursive(ListNode l1, ListNode l2, int carry) {
        // Base case: both lists null and no carry
        if (l1 == null && l2 == null && carry == 0) {
            return null;
        }
        
        int sum = carry;
        if (l1 != null) {
            sum += l1.val;
            l1 = l1.next;
        }
        if (l2 != null) {
            sum += l2.val;
            l2 = l2.next;
        }
        
        ListNode result = new ListNode(sum % 10);
        result.next = addTwoNumbersRecursive(l1, l2, sum / 10);
        
        return result;
    }
    
    /**
     * Alternative: Using StringBuilder (for debugging/visualization)
     * 
     * Converts lists to strings, adds them, then converts back to list
     * NOT recommended for production (inefficient, doesn't handle large numbers well)
     * 
     * @param l1 First number
     * @param l2 Second number
     * @return Sum as linked list
     */
    public static ListNode addTwoNumbersString(ListNode l1, ListNode l2) {
        // Convert lists to strings (in correct order)
        String num1 = listToReverseString(l1);
        String num2 = listToReverseString(l2);
        
        // Parse to BigInteger to handle large numbers
        java.math.BigInteger big1 = new java.math.BigInteger(num1);
        java.math.BigInteger big2 = new java.math.BigInteger(num2);
        java.math.BigInteger sum = big1.add(big2);
        
        // Convert sum back to linked list (reverse order)
        return stringToListReverse(sum.toString());
    }
    
    private static String listToReverseString(ListNode head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val);
            head = head.next;
        }
        return sb.reverse().toString(); // Reverse to get actual number
    }
    
    private static ListNode stringToListReverse(String str) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        
        // Process from end to beginning (reverse order)
        for (int i = str.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(str.charAt(i));
            curr.next = new ListNode(digit);
            curr = curr.next;
        }
        
        return dummy.next;
    }
    
    /**
     * Helper: Convert array to linked list (for testing)
     */
    public static ListNode arrayToList(int[] arr) {
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
        long multiplier = 1;
        ListNode curr = head;
        
        while (curr != null) {
            num += curr.val * multiplier;
            multiplier *= 10;
            curr = curr.next;
        }
        
        return num;
    }
    
    /**
     * Extended: Add multiple numbers
     * 
     * @param lists Array of lists to add
     * @return Sum as linked list
     */
    public static ListNode addMultipleNumbers(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        ListNode result = lists[0];
        for (int i = 1; i < lists.length; i++) {
            result = addTwoNumbers(result, lists[i]);
        }
        
        return result;
    }
    
    /**
     * Extended: Add numbers with different digit storage (forward order)
     * 
     * For numbers stored in forward order (most significant digit first)
     * Requires reversing lists first, then adding, then reversing result
     */
    public static ListNode addTwoNumbersForward(ListNode l1, ListNode l2) {
        // Reverse both lists
        ListNode rev1 = reverseList(l1);
        ListNode rev2 = reverseList(l2);
        
        // Add in reverse order
        ListNode sumRev = addTwoNumbers(rev1, rev2);
        
        // Reverse result back to forward order
        return reverseList(sumRev);
    }
    
    private static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        
        return prev;
    }
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        System.out.println("=== Add Two Numbers (Linked Lists) ===\n");
        
        // Test Case 1: Basic example from LeetCode
        System.out.println("1. Basic Example (LeetCode Example):");
        ListNode l1 = arrayToList(new int[]{2, 4, 3});     // 342
        ListNode l2 = arrayToList(new int[]{5, 6, 4});     // 465
        System.out.print("l1 (342): ");
        printList(l1);
        System.out.print("l2 (465): ");
        printList(l2);
        
        ListNode result = addTwoNumbers(l1, l2);           // 807
        System.out.print("Sum (807): ");
        printList(result);
        System.out.println("Verification: " + listToNumber(l1) + " + " + 
                         listToNumber(l2) + " = " + listToNumber(result));
        
        // Test Case 2: Different lengths
        System.out.println("\n2. Different Lengths:");
        ListNode l3 = arrayToList(new int[]{9, 9, 9, 9});  // 9999
        ListNode l4 = arrayToList(new int[]{1});           // 1
        System.out.print("l3 (9999): ");
        printList(l3);
        System.out.print("l4 (1): ");
        printList(l4);
        
        ListNode result2 = addTwoNumbers(l3, l4);          // 10000
        System.out.print("Sum (10000): ");
        printList(result2);
        
        // Test Case 3: Carry propagation
        System.out.println("\n3. Carry Propagation:");
        ListNode l5 = arrayToList(new int[]{9, 9});        // 99
        ListNode l6 = arrayToList(new int[]{1});           // 1
        System.out.print("l5 (99): ");
        printList(l5);
        System.out.print("l6 (1): ");
        printList(l6);
        
        ListNode result3 = addTwoNumbers(l5, l6);          // 100
        System.out.print("Sum (100): ");
        printList(result3);
        
        // Test Case 4: Zero handling
        System.out.println("\n4. Zero Handling:");
        ListNode l7 = arrayToList(new int[]{0});           // 0
        ListNode l8 = arrayToList(new int[]{0});           // 0
        System.out.print("l7 (0): ");
        printList(l7);
        System.out.print("l8 (0): ");
        printList(l8);
        
        ListNode result4 = addTwoNumbers(l7, l8);          // 0
        System.out.print("Sum (0): ");
        printList(result4);
        
        // Test Case 5: Large numbers
        System.out.println("\n5. Large Numbers:");
        ListNode l9 = arrayToList(new int[]{9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9}); // 20 nines
        ListNode l10 = arrayToList(new int[]{1});                                        // 1
        System.out.println("l9 (20 nines): number with 20 digits of 9");
        System.out.println("l10 (1): 1");
        
        ListNode result5 = addTwoNumbers(l9, l10);
        System.out.print("Sum (1 followed by 20 zeros): first few digits: ");
        ListNode curr = result5;
        for (int i = 0; i < 5 && curr != null; i++) {
            System.out.print(curr.val + " -> ");
            curr = curr.next;
        }
        System.out.println("...");
        
        // Test Case 6: Compare different methods
        System.out.println("\n6. Method Comparison:");
        ListNode test1 = arrayToList(new int[]{7, 1, 6});  // 617
        ListNode test2 = arrayToList(new int[]{5, 9, 2});  // 295
        
        System.out.println("Testing iterative method:");
        ListNode iterResult = addTwoNumbers(test1, test2);
        printList(iterResult);
        
        System.out.println("Testing recursive method:");
        ListNode recResult = addTwoNumbersRecursive(test1, test2);
        printList(recResult);
        
        System.out.println("Testing string method (for verification):");
        ListNode strResult = addTwoNumbersString(test1, test2);
        printList(strResult);
        
        // Test Case 7: Multiple numbers addition
        System.out.println("\n7. Add Multiple Numbers:");
        ListNode[] lists = {
            arrayToList(new int[]{1, 2, 3}),    // 321
            arrayToList(new int[]{4, 5, 6}),    // 654
            arrayToList(new int[]{7, 8, 9})     // 987
        };
        System.out.println("Adding: 321 + 654 + 987 = 1962");
        
        ListNode multiResult = addMultipleNumbers(lists);
        System.out.print("Result (1962): ");
        printList(multiResult);
        
        // Test Case 8: Forward order (if needed)
        System.out.println("\n8. Forward Order Addition:");
        ListNode forward1 = arrayToList(new int[]{3, 2, 1});  // 321 (forward)
        ListNode forward2 = arrayToList(new int[]{9, 8, 7});  // 987 (forward)
        System.out.print("forward1 (321): ");
        printList(forward1);
        System.out.print("forward2 (987): ");
        printList(forward2);
        
        ListNode forwardResult = addTwoNumbersForward(forward1, forward2);
        System.out.print("Sum (1308 in forward): ");
        printList(forwardResult);
        
        // Test Case 9: Edge cases
        System.out.println("\n9. Edge Cases:");
        
        // Null lists
        try {
            ListNode nullResult = addTwoNumbers(null, null);
            System.out.println("Both null: " + (nullResult == null ? "null" : "not null"));
        } catch (Exception e) {
            System.out.println("Exception with null lists: " + e.getMessage());
        }
        
        // One null list
        ListNode oneNull = addTwoNumbers(arrayToList(new int[]{1, 2, 3}), null);
        System.out.print("One null list (123 + null = 123): ");
        printList(oneNull);
        
        // Test Case 10: Performance test
        System.out.println("\n10. Performance Test:");
        
        // Create two large numbers (1000 digits each)
        int[] largeArr1 = new int[1000];
        int[] largeArr2 = new int[1000];
        for (int i = 0; i < 1000; i++) {
            largeArr1[i] = 9;
            largeArr2[i] = 1;
        }
        
        ListNode large1 = arrayToList(largeArr1);
        ListNode large2 = arrayToList(largeArr2);
        
        long startTime = System.currentTimeMillis();
        ListNode largeResult = addTwoNumbers(large1, large2);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Added two 1000-digit numbers in " + (endTime - startTime) + " ms");
        
        // Verify first few digits
        System.out.print("First 5 digits of result: ");
        ListNode temp = largeResult;
        for (int i = 0; i < 5 && temp != null; i++) {
            System.out.print(temp.val + " ");
            temp = temp.next;
        }
        System.out.println("...");
    }
    
    /**
     * Common Mistakes to Avoid:
     * 
     * 1. FORGETTING THE FINAL CARRY:
     *    ❌ Stop when both lists are null (misses final carry)
     *    ✅ Continue while carry != 0
     *    
     * 2. INCORRECT DIGIT EXTRACTION:
     *    ❌ digit = sum / 10 (wrong - that's carry)
     *    ✅ digit = sum % 10 (correct)
     *    
     * 3. NOT USING DUMMY NODE:
     *    ❌ Handle empty result list specially
     *    ✅ Use dummy node for cleaner code
     *    
     * 4. MODIFYING INPUT LISTS:
     *    ❌ Modify l1/l2 if you need them later
     *    ✅ Use temporary pointers or clone lists
     *    
     * 5. INTEGER OVERFLOW:
     *    For very large numbers, don't convert to int/long
     *    Always use linked list arithmetic
     */
    
    /**
     * Algorithm Walkthrough Example:
     * 
     * Input: l1 = [2,4,3], l2 = [5,6,4]
     * Represent: 342 + 465 = 807
     * 
     * Iteration 1:
     *   sum = 0 + 2 + 5 = 7
     *   carry = 7/10 = 0
     *   digit = 7%10 = 7
     *   Result: [7]
     * 
     * Iteration 2:
     *   sum = 0 + 4 + 6 = 10
     *   carry = 10/10 = 1
     *   digit = 10%10 = 0
     *   Result: [7,0]
     * 
     * Iteration 3:
     *   sum = 1 + 3 + 4 = 8
     *   carry = 8/10 = 0
     *   digit = 8%10 = 8
     *   Result: [7,0,8]
     * 
     * Final: [7,0,8] represents 807 ✓
     */
    
    /**
     * Complexity Analysis:
     * 
     * Time Complexity: O(max(m, n))
     *   - We iterate at most max(m, n) + 1 times
     *   - Each iteration does O(1) work
     *   
     * Space Complexity: O(max(m, n))
     *   - We create a new list for the result
     *   - Maximum length is max(m, n) + 1
     *   
     * Memory Breakdown:
     *   - Input lists: O(m + n)
     *   - Result list: O(max(m, n))
     *   - Auxiliary variables: O(1)
     */
    
    /**
     * Variations and Extensions:
     * 
     * 1. SUBTRACT TWO NUMBERS: Similar logic with borrow instead of carry
     * 2. MULTIPLY TWO NUMBERS: More complex, requires nested loops
     * 3. ADD NUMBERS IN FORWARD ORDER: Reverse, add, reverse back
     * 4. ADD FLOATING POINT NUMBERS: Handle decimal point separately
     * 5. ADD NUMBERS WITH DIFFERENT BASES: Generalize from base 10 to any base
     */
}