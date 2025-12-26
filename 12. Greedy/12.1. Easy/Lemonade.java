import java.util.Scanner;

/**
 * Lemonade Change (LeetCode 860)
 * 
 * Problem Statement:
 * At a lemonade stand, each lemonade costs $5. Customers are standing in a queue
 * to buy lemonade and order one at a time. Each customer will only buy one 
 * lemonade and pay with either a $5, $10, or $20 bill.
 * You must provide correct change to each customer so that the net transaction
 * is that the customer pays $5.
 * 
 * Given an integer array bills where bills[i] is the bill the i-th customer pays,
 * return true if you can provide every customer with correct change, else false.
 * 
 * Note: You start with no money initially.
 * 
 * Example 1:
 * Input: bills = [5,5,5,10,20]
 * Output: true
 * Explanation: 
 *   Customer 1: $5 bill → no change needed
 *   Customer 2: $5 bill → no change needed  
 *   Customer 3: $5 bill → no change needed
 *   Customer 4: $10 bill → give $5 change
 *   Customer 5: $20 bill → give $10 + $5 change
 * 
 * Example 2:
 * Input: bills = [5,5,10,10,20]
 * Output: false
 * Explanation: 
 *   Last customer with $20 bill cannot get correct change
 * 
 * Greedy Approach:
 * - Maintain counts of $5 and $10 bills (don't need to track $20 since we never give them as change)
 * - For each customer:
 *   - If pays $5: keep it, increment five count
 *   - If pays $10: need to give $5 change, decrement five, increment ten
 *   - If pays $20: prefer to give $10+$5 change if possible, otherwise give 3×$5
 * - If at any point we can't give required change, return false
 * 
 * Time Complexity: O(n) where n = number of customers
 * Space Complexity: O(1) - only need two counters
 */
public class Lemonade {

    /**
     * Greedy solution for lemonade change
     * 
     * @param bills Array of bills from customers (each is 5, 10, or 20)
     * @return true if all customers can get correct change, false otherwise
     */
    public boolean lemonadeChange(int[] bills) {
        // Track number of $5 and $10 bills we have
        int fiveCount = 0;
        int tenCount = 0;
        
        for (int bill : bills) {
            if (bill == 5) {
                // Customer pays with $5: no change needed
                fiveCount++;
            } 
            else if (bill == 10) {
                // Customer pays with $10: need to give $5 change
                if (fiveCount == 0) {
                    return false; // Cannot give change
                }
                fiveCount--; // Give $5 change
                tenCount++;  // Receive $10
            } 
            else { // bill == 20
                // Customer pays with $20: need to give $15 change
                // Prefer to give $10 + $5 if possible (greedy choice)
                if (tenCount > 0 && fiveCount > 0) {
                    // Give one $10 and one $5
                    tenCount--;
                    fiveCount--;
                } 
                else if (fiveCount >= 3) {
                    // Give three $5 bills
                    fiveCount -= 3;
                } 
                else {
                    // Cannot give $15 change
                    return false;
                }
                // Note: We don't increment twentyCount because $20 bills are useless for change
            }
            
            // Check for negative counts (shouldn't happen with proper logic)
            if (fiveCount < 0 || tenCount < 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Alternative implementation with more detailed comments and validation
     */
    public boolean lemonadeChangeDetailed(int[] bills) {
        // Input validation
        if (bills == null) {
            return false;
        }
        
        int fives = 0, tens = 0;
        
        for (int i = 0; i < bills.length; i++) {
            int bill = bills[i];
            
            // Validate bill value
            if (bill != 5 && bill != 10 && bill != 20) {
                System.out.println("Invalid bill: " + bill + " at position " + i);
                return false;
            }
            
            System.out.println("\nCustomer " + (i+1) + " pays with $" + bill);
            System.out.println("  Before: fives=" + fives + ", tens=" + tens);
            
            if (bill == 5) {
                fives++;
                System.out.println("  No change needed. fives=" + fives);
            }
            else if (bill == 10) {
                if (fives == 0) {
                    System.out.println("  ERROR: Cannot give $5 change (no $5 bills)");
                    return false;
                }
                fives--;
                tens++;
                System.out.println("  Give $5 change. fives=" + fives + ", tens=" + tens);
            }
            else { // bill == 20
                // Greedy: prefer $10 + $5 over 3×$5 when possible
                if (tens > 0 && fives > 0) {
                    tens--;
                    fives--;
                    System.out.println("  Give $10 + $5 change. fives=" + fives + ", tens=" + tens);
                }
                else if (fives >= 3) {
                    fives -= 3;
                    System.out.println("  Give 3×$5 change. fives=" + fives);
                }
                else {
                    System.out.println("  ERROR: Cannot give $15 change");
                    return false;
                }
            }
            
            if (fives < 0 || tens < 0) {
                System.out.println("  ERROR: Negative bill count detected");
                return false;
            }
        }
        
        System.out.println("\nSuccess! All customers served with correct change.");
        return true;
    }
    
    /**
     * Explanation of why greedy works for $20 change:
     * 
     * For $15 change, we have two options:
     * 1. $10 + $5 (preferred)
     * 2. 3×$5 (fallback)
     * 
     * Why prefer $10+$5?
     * - $10 bills are less flexible than $5 bills
     * - $10 can only be used for $20 change (with $5)
     * - $5 bills are more versatile (can be used for $10 and $20 change)
     * - Saving $5 bills gives us more flexibility
     * 
     * Example where greedy matters:
     * bills = [5,5,10,20]
     * - Without greedy: use 3×$5 for $20 → fail for future $10 customers
     * - With greedy: use $10+$5 → success
     */
    
    /**
     * Visualization helper
     */
    public void visualizeLemonadeChange(int[] bills) {
        System.out.println("\n=== Visualizing Lemonade Change ===");
        System.out.print("Bills: [");
        for (int i = 0; i < bills.length; i++) {
            System.out.print(bills[i]);
            if (i < bills.length - 1) System.out.print(", ");
        }
        System.out.println("]");
        System.out.println("Lemonade price: $5");
        
        int fives = 0, tens = 0;
        boolean success = true;
        
        System.out.println("\nTransaction log:");
        for (int i = 0; i < bills.length; i++) {
            int bill = bills[i];
            System.out.println("\nCustomer " + (i+1) + ": pays $" + bill);
            
            switch (bill) {
                case 5:
                    fives++;
                    System.out.println("  Action: Keep $5 bill");
                    System.out.println("  Change: None needed");
                    break;
                    
                case 10:
                    if (fives > 0) {
                        fives--;
                        tens++;
                        System.out.println("  Action: Give $5 change, keep $10");
                        System.out.println("  Change: $5 bill");
                    } else {
                        System.out.println("  Action: FAIL - No $5 bill for change!");
                        success = false;
                    }
                    break;
                    
                case 20:
                    if (tens > 0 && fives > 0) {
                        tens--;
                        fives--;
                        System.out.println("  Action: Give $10 + $5 change, keep $20");
                        System.out.println("  Change: $10 + $5 bills");
                    } else if (fives >= 3) {
                        fives -= 3;
                        System.out.println("  Action: Give 3×$5 change, keep $20");
                        System.out.println("  Change: 3×$5 bills");
                    } else {
                        System.out.println("  Action: FAIL - Cannot make $15 change!");
                        System.out.println("  Available: " + fives + " $5 bills, " + tens + " $10 bills");
                        success = false;
                    }
                    break;
                    
                default:
                    System.out.println("  ERROR: Invalid bill amount");
                    success = false;
            }
            
            System.out.println("  Available after: " + fives + " $5 bills, " + tens + " $10 bills");
            
            if (!success) {
                System.out.println("\n✗ FAILED at customer " + (i+1));
                return;
            }
        }
        
        System.out.println("\n✓ SUCCESS! All customers served.");
        System.out.println("Final cash register: " + fives + " $5 bills, " + tens + " $10 bills");
        
        // Show total money (should be sum of bills minus $5 per customer)
        int totalCollected = 0;
        for (int bill : bills) totalCollected += bill;
        int totalChange = (bills.length * 5) - 5; // Each customer should net pay $5
        System.out.println("Total collected: $" + totalCollected);
        System.out.println("Total change given: $" + totalChange);
        System.out.println("Net profit: $" + (totalCollected - totalChange));
    }
    
    /**
     * Test helper for edge cases
     */
    public void runTestCases() {
        System.out.println("\n=== Test Cases ===");
        
        // Test 1: Simple case from LeetCode
        int[] test1 = {5,5,5,10,20};
        System.out.println("\nTest 1: " + java.util.Arrays.toString(test1));
        System.out.println("Expected: true, Got: " + lemonadeChange(test1));
        
        // Test 2: Case that fails
        int[] test2 = {5,5,10,10,20};
        System.out.println("\nTest 2: " + java.util.Arrays.toString(test2));
        System.out.println("Expected: false, Got: " + lemonadeChange(test2));
        
        // Test 3: Edge case - all $5
        int[] test3 = {5,5,5,5,5};
        System.out.println("\nTest 3: " + java.util.Arrays.toString(test3));
        System.out.println("Expected: true, Got: " + lemonadeChange(test3));
        
        // Test 4: Edge case - first customer $10 (should fail)
        int[] test4 = {10,5,5};
        System.out.println("\nTest 4: " + java.util.Arrays.toString(test4));
        System.out.println("Expected: false, Got: " + lemonadeChange(test4));
        
        // Test 5: Greedy choice matters
        int[] test5 = {5,5,10,10,5,20};
        System.out.println("\nTest 5: " + java.util.Arrays.toString(test5));
        System.out.println("Expected: true, Got: " + lemonadeChange(test5));
        
        // Test 6: Large $20 bill early
        int[] test6 = {20,5,5,10,5};
        System.out.println("\nTest 6: " + java.util.Arrays.toString(test6));
        System.out.println("Expected: false, Got: " + lemonadeChange(test6));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt(); // Number of test cases
        
        while (t-- > 0) {
            int n = sc.nextInt();
            int[] bills = new int[n];
            
            for (int i = 0; i < n; i++) {
                bills[i] = sc.nextInt();
            }
            
            Lemonade solver = new Lemonade();
            
            // Method 1: Basic solution
            boolean result = solver.lemonadeChange(bills);
            System.out.println(result ? "true" : "false");
            
            // Uncomment for visualization
            // solver.visualizeLemonadeChange(bills);
        }
        
        sc.close();
        
        // Uncomment to run test cases
        // solver.runTestCases();
    }
}