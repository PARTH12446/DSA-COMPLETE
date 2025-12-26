import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Fractional Knapsack (GFG)
 *
 * Problem Statement:
 * Given weights and values of n items, we need to put these items in a knapsack
 * of capacity W to get the maximum total value in the knapsack.
 * In Fractional Knapsack, we can break items to maximize the total value.
 *
 * Example:
 * Input: 
 *   values[] = [60, 100, 120]
 *   weights[] = [10, 20, 30]
 *   capacity = 50
 * Output: 240
 * Explanation:
 *   Take 10kg of item 1 (value 60) → full
 *   Take 20kg of item 2 (value 100) → full  
 *   Take 20kg of item 3 (value 120 * 20/30 = 80) → partial
 *   Total = 60 + 100 + 80 = 240
 *
 * Greedy Approach:
 * 1. Calculate value/weight ratio for each item
 * 2. Sort items in decreasing order of value/weight ratio
 * 3. Take items greedily:
 *    - If item fits completely, take whole item
 *    - If item doesn't fit completely, take fractional part
 *
 * Time Complexity: O(n log n) for sorting
 * Space Complexity: O(1) excluding input storage
 */
public class FractionalKnapsack {

    static class Item {
        int value;
        int weight;
        Item(int v, int w) { 
            value = v; 
            weight = w; 
        }
    }

    /**
     * Greedy solution for fractional knapsack
     * 
     * @param capacity Maximum weight capacity of knapsack
     * @param items Array of items with value and weight
     * @return Maximum achievable value (can be fractional)
     */
    public double fractionalKnapsack(int capacity, Item[] items) {
        // Edge cases
        if (capacity <= 0 || items == null || items.length == 0) {
            return 0.0;
        }

        // Step 1: Sort items by value/weight ratio in descending order
        Arrays.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item a, Item b) {
                // Calculate value per unit weight
                double ratioA = (double) a.value / a.weight;
                double ratioB = (double) b.value / b.weight;
                
                // Sort in descending order (highest ratio first)
                // Using Double.compare(b, a) for descending
                return Double.compare(ratioB, ratioA);
            }
        });

        // Step 2: Greedy selection
        double totalValue = 0.0;
        int remainingCapacity = capacity;

        for (Item item : items) {
            if (remainingCapacity == 0) {
                break; // Knapsack is full
            }

            if (item.weight <= remainingCapacity) {
                // Take whole item
                totalValue += item.value;
                remainingCapacity -= item.weight;
            } else {
                // Take fractional part of item
                double fraction = (double) remainingCapacity / item.weight;
                totalValue += fraction * item.value;
                remainingCapacity = 0; // Knapsack is now full
                break;
            }
        }

        return totalValue;
    }
    
    /**
     * Alternative implementation using Java 8 lambda for cleaner code
     */
    public double fractionalKnapsackLambda(int capacity, Item[] items) {
        if (capacity <= 0 || items == null || items.length == 0) {
            return 0.0;
        }

        // Sort by value/weight ratio descending
        Arrays.sort(items, (a, b) -> {
            double ratioA = (double) a.value / a.weight;
            double ratioB = (double) b.value / b.weight;
            return Double.compare(ratioB, ratioA); // Descending
        });

        double totalValue = 0.0;
        int remaining = capacity;

        for (Item item : items) {
            if (remaining == 0) break;
            
            if (item.weight <= remaining) {
                totalValue += item.value;
                remaining -= item.weight;
            } else {
                totalValue += ((double) remaining / item.weight) * item.value;
                remaining = 0;
                break;
            }
        }

        return totalValue;
    }
    
    /**
     * Implementation that also returns which items were taken (for visualization)
     */
    public static class KnapsackResult {
        double totalValue;
        String[] selections; // "Full", "Partial (x/y)", or "None"
        
        KnapsackResult(double value, String[] selections) {
            this.totalValue = value;
            this.selections = selections;
        }
    }
    
    public KnapsackResult fractionalKnapsackDetailed(int capacity, Item[] items) {
        if (capacity <= 0 || items == null || items.length == 0) {
            return new KnapsackResult(0.0, new String[0]);
        }
        
        // Create array with indices to track original positions
        ItemWithIndex[] indexedItems = new ItemWithIndex[items.length];
        for (int i = 0; i < items.length; i++) {
            indexedItems[i] = new ItemWithIndex(items[i].value, items[i].weight, i);
        }
        
        // Sort by ratio descending
        Arrays.sort(indexedItems, (a, b) -> {
            double ratioA = (double) a.value / a.weight;
            double ratioB = (double) b.value / b.weight;
            return Double.compare(ratioB, ratioA);
        });
        
        double totalValue = 0.0;
        int remaining = capacity;
        String[] selections = new String[items.length];
        
        for (ItemWithIndex item : indexedItems) {
            if (remaining == 0) {
                selections[item.originalIndex] = "None (knapsack full)";
                continue;
            }
            
            if (item.weight <= remaining) {
                // Take whole item
                totalValue += item.value;
                remaining -= item.weight;
                selections[item.originalIndex] = String.format("Full (value=%d, weight=%d)", 
                    item.value, item.weight);
            } else {
                // Take fractional part
                double fraction = (double) remaining / item.weight;
                double fractionalValue = fraction * item.value;
                totalValue += fractionalValue;
                selections[item.originalIndex] = String.format(
                    "Partial %.2f/%d (value=%.2f/%d)", 
                    remaining, item.weight, fractionalValue, item.value);
                remaining = 0;
            }
        }
        
        return new KnapsackResult(totalValue, selections);
    }
    
    static class ItemWithIndex extends Item {
        int originalIndex;
        
        ItemWithIndex(int v, int w, int idx) {
            super(v, w);
            this.originalIndex = idx;
        }
    }
    
    /**
     * Visualization helper
     */
    public void visualizeFractionalKnapsack(int capacity, Item[] items) {
        System.out.println("\n=== Visualizing Fractional Knapsack ===");
        System.out.println("Knapsack capacity: " + capacity);
        System.out.println("\nAvailable items:");
        
        for (int i = 0; i < items.length; i++) {
            double ratio = (double) items[i].value / items[i].weight;
            System.out.printf("  Item %d: value=%d, weight=%d, ratio=%.2f\n",
                i, items[i].value, items[i].weight, ratio);
        }
        
        // Sort items by ratio
        Item[] sortedItems = items.clone();
        Arrays.sort(sortedItems, (a, b) -> {
            double ratioA = (double) a.value / a.weight;
            double ratioB = (double) b.value / b.weight;
            return Double.compare(ratioB, ratioA);
        });
        
        System.out.println("\nSorted by value/weight ratio (descending):");
        for (int i = 0; i < sortedItems.length; i++) {
            double ratio = (double) sortedItems[i].value / sortedItems[i].weight;
            System.out.printf("  %d. value=%d, weight=%d, ratio=%.2f\n",
                i+1, sortedItems[i].value, sortedItems[i].weight, ratio);
        }
        
        double totalValue = 0.0;
        int remaining = capacity;
        int step = 1;
        
        System.out.println("\nGreedy selection process:");
        for (Item item : sortedItems) {
            System.out.println("\nStep " + step++ + ":");
            System.out.printf("  Considering item: value=%d, weight=%d, ratio=%.2f\n",
                item.value, item.weight, (double)item.value/item.weight);
            System.out.println("  Remaining capacity: " + remaining);
            
            if (remaining == 0) {
                System.out.println("  ✗ Knapsack is full, skipping item");
                continue;
            }
            
            if (item.weight <= remaining) {
                System.out.printf("  ✓ Taking full item (weight %d ≤ remaining %d)\n",
                    item.weight, remaining);
                totalValue += item.value;
                remaining -= item.weight;
                System.out.printf("  Added value: %d, New remaining: %d\n",
                    item.value, remaining);
            } else {
                double fraction = (double) remaining / item.weight;
                double fractionalValue = fraction * item.value;
                System.out.printf("  ⚠ Taking %.2f of item (weight %d > remaining %d)\n",
                    fraction, item.weight, remaining);
                totalValue += fractionalValue;
                System.out.printf("  Added value: %.2f, New remaining: 0\n", fractionalValue);
                remaining = 0;
            }
            
            System.out.printf("  Cumulative value: %.2f\n", totalValue);
        }
        
        System.out.println("\n=== Final Result ===");
        System.out.printf("Maximum achievable value: %.2f\n", totalValue);
        System.out.println("Remaining capacity: " + remaining);
        
        // Show optimality proof intuition
        System.out.println("\n=== Why Greedy Works (Proof Sketch) ===");
        System.out.println("1. Sorting by value/weight ratio ensures we consider");
        System.out.println("   most valuable items per unit weight first");
        System.out.println("2. If we have capacity for full item, taking it is optimal");
        System.out.println("3. If we can't take full item, taking fractional part");
        System.out.println("   maximizes value for that capacity");
        System.out.println("4. Any alternative arrangement would give ≤ value");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt(); // Number of test cases
        
        while (t-- > 0) {
            int n = sc.nextInt(); // Number of items
            int W = sc.nextInt(); // Knapsack capacity
            
            Item[] items = new Item[n];
            for (int i = 0; i < n; i++) {
                int value = sc.nextInt();
                int weight = sc.nextInt();
                items[i] = new Item(value, weight);
            }
            
            FractionalKnapsack solver = new FractionalKnapsack();
            
            // Method 1: Basic greedy
            double result = solver.fractionalKnapsack(W, items);
            System.out.printf("%.2f\n", result); // Print with 2 decimal places
            
            // Uncomment for visualization
            // solver.visualizeFractionalKnapsack(W, items);
            
            // Method 2: Detailed result
            // KnapsackResult detailed = solver.fractionalKnapsackDetailed(W, items);
            // System.out.printf("Total value: %.2f\n", detailed.totalValue);
            // for (int i = 0; i < detailed.selections.length; i++) {
            //     System.out.println("Item " + i + ": " + detailed.selections[i]);
            // }
        }
        
        sc.close();
    }
}