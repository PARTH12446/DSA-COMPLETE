import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map;

/**
 * Hand of Straights (LeetCode 846):
 * Given an array of cards and a group size W, check if it can be rearranged
 * into groups of W consecutive cards.
 *
 * Problem Statement:
 * We are given an integer array hand where hand[i] is the value written on the 
 * ith card and an integer groupSize. We need to check if the cards can be 
 * rearranged into groups of size groupSize where each group consists of 
 * consecutive numbers.
 *
 * Example:
 * Input: hand = [1,2,3,6,2,3,4,7,8], groupSize = 3
 * Output: true
 * Explanation: We can form groups [1,2,3], [2,3,4], [6,7,8]
 *
 * Approach:
 * 1. First check if total cards can be divided into groups of size W
 * 2. Use TreeMap (sorted map) to store card frequencies
 * 3. Repeatedly take the smallest available card and try to build a 
 *    consecutive sequence of length W
 * 4. Decrease counts as we use cards
 * 5. If at any point a needed consecutive card is missing, return false
 *
 * Time Complexity: O(n log n) - TreeMap operations
 * Space Complexity: O(n) - for storing frequencies
 */
public class HandOfStraights {

    /**
     * Main solution method
     */
    public boolean isNStraightHand(int[] hand, int W) {
        // Basic validation: total cards must be divisible by group size
        if (hand.length % W != 0) {
            return false;
        }

        // Special case: if group size is 1, always true
        if (W == 1) {
            return true;
        }

        // Use TreeMap to maintain sorted order of cards with their frequencies
        // TreeMap sorts keys in natural order (ascending)
        TreeMap<Integer, Integer> count = new TreeMap<>();
        
        // Count frequency of each card
        for (int card : hand) {
            count.put(card, count.getOrDefault(card, 0) + 1);
        }

        // Process while we still have cards
        while (!count.isEmpty()) {
            // Get the smallest available card
            int startCard = count.firstKey();
            
            // Try to build a consecutive sequence of length W starting from startCard
            for (int currentCard = startCard; currentCard < startCard + W; currentCard++) {
                // If we don't have the current card in our map, sequence breaks
                if (!count.containsKey(currentCard)) {
                    return false;
                }
                
                // Get current count of this card
                int currentCount = count.get(currentCard);
                
                // If this is the last card of this value, remove from map
                if (currentCount == 1) {
                    count.remove(currentCard);
                } else {
                    // Otherwise decrement the count
                    count.put(currentCard, currentCount - 1);
                }
            }
        }
        
        return true;
    }

    /**
     * Alternative implementation with more detailed error messages
     * and additional validation
     */
    public boolean isNStraightHandDetailed(int[] hand, int W) {
        // Quick validations
        if (hand == null || hand.length == 0) {
            return W == 0; // Empty hand with group size 0 is valid
        }
        
        if (hand.length % W != 0) {
            System.out.println("Total cards (" + hand.length + ") not divisible by group size (" + W + ")");
            return false;
        }

        TreeMap<Integer, Integer> freqMap = new TreeMap<>();
        
        // Build frequency map
        for (int card : hand) {
            freqMap.put(card, freqMap.getOrDefault(card, 0) + 1);
        }
        
        System.out.println("Initial frequency map: " + freqMap);
        int groupCount = 0;
        
        // Continue until all cards are used
        while (!freqMap.isEmpty()) {
            // Get the smallest card available
            int firstCard = freqMap.firstKey();
            System.out.println("\nStarting new group #" + (groupCount + 1) + " with card: " + firstCard);
            
            // Build a sequence of W consecutive numbers
            for (int i = 0; i < W; i++) {
                int neededCard = firstCard + i;
                System.out.println("  Need card: " + neededCard);
                
                if (!freqMap.containsKey(neededCard)) {
                    System.out.println("  Missing card: " + neededCard);
                    return false;
                }
                
                // Use one occurrence of this card
                int remaining = freqMap.get(neededCard);
                if (remaining == 1) {
                    freqMap.remove(neededCard);
                    System.out.println("  Used last card " + neededCard + ", removed from map");
                } else {
                    freqMap.put(neededCard, remaining - 1);
                    System.out.println("  Used card " + neededCard + ", remaining: " + (remaining - 1));
                }
            }
            
            groupCount++;
            System.out.println("Completed group #" + groupCount);
        }
        
        System.out.println("Successfully formed " + groupCount + " groups");
        return true;
    }

    /**
     * Optimized approach using array when card values are bounded
     * This is faster when we know the range of card values
     */
    public boolean isNStraightHandOptimized(int[] hand, int W) {
        if (hand.length % W != 0) return false;
        if (W == 1) return true;
        
        // Find min and max card values
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int card : hand) {
            min = Math.min(min, card);
            max = Math.max(max, card);
        }
        
        // Create frequency array
        int[] freq = new int[max - min + 1];
        for (int card : hand) {
            freq[card - min]++;
        }
        
        // Try to form groups
        for (int i = 0; i < freq.length; i++) {
            if (freq[i] == 0) continue;
            
            int count = freq[i];
            // Need to form 'count' groups starting from this card
            for (int j = 0; j < W; j++) {
                int idx = i + j;
                if (idx >= freq.length || freq[idx] < count) {
                    return false;
                }
                freq[idx] -= count;
            }
        }
        
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // Number of test cases
        
        while (t-- > 0) {
            int n = sc.nextInt();  // Number of cards
            int[] hand = new int[n];
            
            for (int i = 0; i < n; i++) {
                hand[i] = sc.nextInt();
            }
            
            int W = sc.nextInt();  // Group size
            
            HandOfStraights solver = new HandOfStraights();
            boolean result = solver.isNStraightHand(hand, W);
            System.out.println(result ? "true" : "false");
            
            // For detailed output, uncomment:
            // System.out.println("\nDetailed execution:");
            // boolean detailedResult = solver.isNStraightHandDetailed(hand, W);
            // System.out.println("Result: " + detailedResult);
        }
        
        sc.close();
    }
}