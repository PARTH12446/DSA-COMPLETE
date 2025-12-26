import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Basic Coin Change (minimum number of coins) using Indian denominations.
 * 
 * Problem Statement:
 * Given an amount N and a set of coin denominations (Indian currency notes),
 * find the minimum number of coins/notes needed to make the amount.
 * Assume we have unlimited supply of each denomination.
 * 
 * Indian Denominations: {1, 2, 5, 10, 20, 50, 100, 200, 500, 2000}
 * 
 * Greedy Approach:
 * - Always use the largest denomination that doesn't exceed the remaining amount
 * - This works for Indian currency because it's a canonical coin system
 * - Not all currency systems allow greedy approach (e.g., US coins {1, 5, 10, 25} work,
 *   but arbitrary systems may require dynamic programming)
 *
 * Example:
 * Input: N = 43
 * Output: [20, 20, 2, 1] → 4 notes
 * Explanation: 43 = 20 + 20 + 2 + 1
 *
 * Time Complexity: O(k) where k is number of denominations
 * Space Complexity: O(m) where m is number of coins in result
 */
public class BasicCoinChange {

    // Indian currency denominations in paise (but typically used as rupees)
    // Sorted in ascending order for easier processing
    private static final int[] NOTES = {1, 2, 5, 10, 20, 50, 100, 200, 500, 2000};
    
    /**
     * Greedy solution: Always use largest possible denomination
     * 
     * @param N The amount to make
     * @return List of coins/notes used (may not be minimum for non-canonical systems)
     */
    public List<Integer> minPartition(int N) {
        List<Integer> result = new ArrayList<>();
        int remaining = N;
        
        // Start from largest denomination and move downwards
        for (int i = NOTES.length - 1; i >= 0; i--) {
            int note = NOTES[i];
            
            // While we can use this denomination
            while (note <= remaining) {
                result.add(note);          // Use this note
                remaining -= note;         // Reduce remaining amount
            }
            
            // Alternative: Calculate count and use loop (more efficient for large amounts)
            // if (note <= remaining) {
            //     int count = remaining / note;
            //     for (int c = 0; c < count; c++) {
            //         result.add(note);
            //     }
            //     remaining %= note;
            // }
        }
        
        return result;
    }
    
    /**
     * Alternative: Dynamic Programming solution (works for any coin system)
     * Returns minimum number of coins, not the actual coins
     */
    public int minCoinsDP(int N) {
        if (N <= 0) return 0;
        
        int[] dp = new int[N + 1];
        
        // Initialize: 0 coins needed for amount 0
        dp[0] = 0;
        
        // For all amounts from 1 to N
        for (int i = 1; i <= N; i++) {
            dp[i] = Integer.MAX_VALUE;
            
            // Try every coin that's <= current amount
            for (int note : NOTES) {
                if (note <= i && dp[i - note] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - note] + 1);
                }
            }
        }
        
        return dp[N] == Integer.MAX_VALUE ? -1 : dp[N];
    }
    
    /**
     * Dynamic Programming solution that also returns the coins used
     */
    public List<Integer> minPartitionDP(int N) {
        if (N <= 0) return new ArrayList<>();
        
        int[] dp = new int[N + 1];
        int[] lastCoin = new int[N + 1];  // Track which coin was used
        
        dp[0] = 0;
        for (int i = 1; i <= N; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int note : NOTES) {
                if (note <= i && dp[i - note] != Integer.MAX_VALUE && dp[i - note] + 1 < dp[i]) {
                    dp[i] = dp[i - note] + 1;
                    lastCoin[i] = note;
                }
            }
        }
        
        // Reconstruct the coins used
        List<Integer> result = new ArrayList<>();
        int amount = N;
        while (amount > 0) {
            int coin = lastCoin[amount];
            result.add(coin);
            amount -= coin;
        }
        
        return result;
    }
    
    /**
     * Check if greedy works for a given coin system
     * Canonical coin system: greedy always gives optimal solution
     */
    public boolean isCanonical(int[] coins) {
        // For a coin system to be canonical, greedy must work for all amounts
        // up to the sum of the two largest coins - 2
        int maxCheck = coins[coins.length - 1] + coins[coins.length - 2] - 2;
        
        for (int amount = 1; amount <= maxCheck; amount++) {
            int greedyCount = greedyCount(amount, coins);
            int dpCount = dpCount(amount, coins);
            
            if (greedyCount > dpCount) {
                System.out.println("Counterexample found at amount " + amount + 
                                 ": greedy=" + greedyCount + ", dp=" + dpCount);
                return false;
            }
        }
        return true;
    }
    
    private int greedyCount(int amount, int[] coins) {
        int count = 0;
        for (int i = coins.length - 1; i >= 0; i--) {
            if (coins[i] <= amount) {
                count += amount / coins[i];
                amount %= coins[i];
            }
        }
        return count;
    }
    
    private int dpCount(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int coin : coins) {
                if (coin <= i && dp[i - coin] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount];
    }
    
    /**
     * Visualization helper: Show step-by-step greedy process
     */
    public void visualizeGreedy(int N) {
        System.out.println("\n=== Visualizing Greedy Coin Change ===");
        System.out.println("Amount: " + N);
        System.out.println("Available denominations: ");
        for (int note : NOTES) {
            System.out.print(note + " ");
        }
        System.out.println("\n");
        
        List<Integer> result = new ArrayList<>();
        int remaining = N;
        int step = 1;
        
        for (int i = NOTES.length - 1; i >= 0; i--) {
            int note = NOTES[i];
            if (note <= remaining) {
                int count = remaining / note;
                System.out.println("Step " + step++ + ":");
                System.out.println("  Consider note " + note + ", remaining amount: " + remaining);
                System.out.println("  Can use " + count + " of note " + note);
                
                for (int c = 0; c < count; c++) {
                    result.add(note);
                }
                remaining %= note;
                System.out.println("  Added " + count + " × " + note + " = " + (count * note));
                System.out.println("  New remaining: " + remaining);
                
                if (remaining == 0) {
                    System.out.println("  Amount fully made!");
                    break;
                }
            }
        }
        
        System.out.println("\nTotal coins/notes: " + result.size());
        System.out.print("Solution: ");
        for (int note : result) {
            System.out.print(note + " ");
        }
        
        // Verify sum
        int sum = 0;
        for (int note : result) sum += note;
        System.out.println("\nVerification sum: " + sum + " (original: " + N + ")");
        
        // Compare with DP for educational purposes
        System.out.println("\n=== For comparison (educational) ===");
        int greedyCount = result.size();
        int dpCount = minCoinsDP(N);
        System.out.println("Greedy count: " + greedyCount);
        System.out.println("DP optimal count: " + dpCount);
        
        if (greedyCount == dpCount) {
            System.out.println("✓ Greedy gave optimal solution for this amount");
        } else {
            System.out.println("✗ Greedy did NOT give optimal solution!");
            System.out.println("DP solution: " + minPartitionDP(N));
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // Number of test cases
        
        while (t-- > 0) {
            int N = sc.nextInt();  // Amount to make
            
            BasicCoinChange solver = new BasicCoinChange();
            
            // Method 1: Greedy approach (works for Indian currency)
            List<Integer> greedyResult = solver.minPartition(N);
            System.out.print("Greedy solution (" + greedyResult.size() + " coins): ");
            for (int val : greedyResult) {
                System.out.print(val + " ");
            }
            System.out.println();
            
            // Uncomment for visualization
            // solver.visualizeGreedy(N);
            
            // Method 2: DP approach (always optimal)
            // List<Integer> dpResult = solver.minPartitionDP(N);
            // System.out.print("DP solution (" + dpResult.size() + " coins): ");
            // for (int val : dpResult) {
            //     System.out.print(val + " ");
            // }
            // System.out.println();
            
            // Check if Indian currency is canonical
            // System.out.println("Is Indian currency canonical? " + 
            //                    solver.isCanonical(NOTES));
        }
        
        sc.close();
    }
}