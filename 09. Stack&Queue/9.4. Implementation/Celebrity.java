import java.util.*;

/**
 * The Celebrity Problem
 * 
 * Problem: In a party of n people, a celebrity is someone who:
 * 1. Knows nobody at the party
 * 2. Is known by everybody at the party
 * 
 * You're given a helper function: knows(a, b) returns true if a knows b
 * 
 * Approach: Two-pass elimination algorithm
 * 1. First pass: Find a candidate using elimination
 * 2. Second pass: Verify if candidate is actually a celebrity
 * 
 * Graph Representation:
 * - People are vertices (0 to n-1)
 * - Directed edge a→b means "a knows b"
 * - Celebrity: vertex with 0 outgoing edges, n-1 incoming edges
 * 
 * Time Complexity: O(n) - Each pair checked at most twice
 * Space Complexity: O(1) - No extra data structures
 * 
 * Variations:
 * 1. Find celebrity in O(n) time with O(1) space (this solution)
 * 2. Find all celebrities (multiple celebrities possible)
 * 3. Find celebrity with probability
 */
public class Celebrity {
    
    /**
     * Finds the celebrity in a party of n people.
     * 
     * Algorithm (Two-pass elimination):
     * Phase 1: Find a candidate
     *   - Start with candidate = 0
     *   - For each person i from 1 to n-1:
     *       If candidate knows i → candidate cannot be celebrity, make i candidate
     *       If candidate doesn't know i → i cannot be celebrity, keep candidate
     *   - At end, we have a candidate that might be celebrity
     * 
     * Phase 2: Verify candidate
     *   - Check that candidate knows nobody
     *   - Check that everyone knows candidate
     * 
     * @param n Number of people at party
     * @param knows Adjacency matrix where knows[a][b] = true if a knows b
     * @return Index of celebrity, or -1 if no celebrity exists
     */
    public static int findCelebrity(int n, boolean[][] knows) {
        // Phase 1: Find a candidate using elimination
        int candidate = 0;
        
        for (int i = 1; i < n; i++) {
            // If candidate knows i, candidate cannot be celebrity
            // If candidate doesn't know i, i cannot be celebrity
            if (knows[candidate][i]) {
                candidate = i;
            }
        }
        
        // Phase 2: Verify the candidate
        for (int i = 0; i < n; i++) {
            if (i == candidate) continue;
            
            // Celebrity conditions:
            // 1. Candidate should not know anyone
            // 2. Everyone should know candidate
            if (knows[candidate][i] || !knows[i][candidate]) {
                return -1; // Candidate is not a celebrity
            }
        }
        
        return candidate;
    }
    
    /**
     * Alternative: Stack-based solution.
     * Push all people to stack, pop two at a time, eliminate one.
     */
    public static int findCelebrityStack(int n, boolean[][] knows) {
        Stack<Integer> stack = new Stack<>();
        
        // Push all people to stack
        for (int i = 0; i < n; i++) {
            stack.push(i);
        }
        
        // Eliminate until one candidate remains
        while (stack.size() > 1) {
            int a = stack.pop();
            int b = stack.pop();
            
            if (knows[a][b]) {
                // a knows b → a cannot be celebrity
                stack.push(b);
            } else {
                // a doesn't know b → b cannot be celebrity
                stack.push(a);
            }
        }
        
        int candidate = stack.pop();
        
        // Verify the candidate
        for (int i = 0; i < n; i++) {
            if (i != candidate && (knows[candidate][i] || !knows[i][candidate])) {
                return -1;
            }
        }
        
        return candidate;
    }
    
    /**
     * Find all celebrities (if multiple might exist).
     * Returns list of all celebrity indices.
     */
    public static List<Integer> findAllCelebrities(int n, boolean[][] knows) {
        List<Integer> celebrities = new ArrayList<>();
        
        // Check each person
        for (int candidate = 0; candidate < n; candidate++) {
            boolean isCelebrity = true;
            
            // Check celebrity conditions
            for (int i = 0; i < n; i++) {
                if (i == candidate) continue;
                
                if (knows[candidate][i] || !knows[i][candidate]) {
                    isCelebrity = false;
                    break;
                }
            }
            
            if (isCelebrity) {
                celebrities.add(candidate);
            }
        }
        
        return celebrities;
    }
    
    /**
     * Visualization helper to show the elimination process.
     */
    public static void visualizeElimination(int n, boolean[][] knows) {
        System.out.println("\n=== Celebrity Problem Visualization ===");
        System.out.println("Number of people: " + n);
        System.out.println("\nKnowledge Matrix (knows[a][b] = true if a knows b):");
        
        // Print header
        System.out.print("     ");
        for (int j = 0; j < n; j++) {
            System.out.print("P" + j + " ");
        }
        System.out.println();
        
        // Print matrix
        for (int i = 0; i < n; i++) {
            System.out.print("P" + i + " | ");
            for (int j = 0; j < n; j++) {
                System.out.print(knows[i][j] ? " T " : " . ");
            }
            System.out.println();
        }
        
        System.out.println("\nPhase 1: Finding candidate through elimination");
        System.out.println("Start with candidate = P0");
        
        int candidate = 0;
        for (int i = 1; i < n; i++) {
            System.out.printf("Compare candidate P%d with P%d: ", candidate, i);
            if (knows[candidate][i]) {
                System.out.printf("P%d knows P%d → P%d cannot be celebrity, new candidate = P%d%n", 
                                candidate, i, candidate, i);
                candidate = i;
            } else {
                System.out.printf("P%d doesn't know P%d → P%d cannot be celebrity%n", 
                                candidate, i, i);
            }
        }
        
        System.out.println("\nCandidate after elimination: P" + candidate);
        
        System.out.println("\nPhase 2: Verifying candidate");
        boolean isValid = true;
        for (int i = 0; i < n; i++) {
            if (i == candidate) continue;
            
            System.out.printf("Check P%d → P%d: %s", candidate, i, 
                            knows[candidate][i] ? "Knows ✗" : "Doesn't know ✓");
            System.out.printf(" | P%d → P%d: %s%n", i, candidate, 
                            knows[i][candidate] ? "Knows ✓" : "Doesn't know ✗");
            
            if (knows[candidate][i] || !knows[i][candidate]) {
                isValid = false;
            }
        }
        
        if (isValid) {
            System.out.println("\n✓ P" + candidate + " is the celebrity!");
        } else {
            System.out.println("\n✗ No celebrity exists in this party");
        }
    }
    
    /**
     * Generate random knowledge matrix for testing.
     */
    public static boolean[][] generateKnowledgeMatrix(int n, int celebrityIndex) {
        boolean[][] knows = new boolean[n][n];
        Random rand = new Random();
        
        // Initialize all to false
        for (int i = 0; i < n; i++) {
            Arrays.fill(knows[i], false);
        }
        
        // Randomly fill the matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    // 50% chance of knowing someone
                    knows[i][j] = rand.nextBoolean();
                }
            }
        }
        
        // Ensure the celebrity exists if requested
        if (celebrityIndex >= 0 && celebrityIndex < n) {
            // Celebrity knows nobody
            Arrays.fill(knows[celebrityIndex], false);
            
            // Everyone knows celebrity
            for (int i = 0; i < n; i++) {
                if (i != celebrityIndex) {
                    knows[i][celebrityIndex] = true;
                }
            }
        }
        
        return knows;
    }
    
    /**
     * Test cases for the celebrity problem.
     */
    public static void runTestCases() {
        System.out.println("=== Celebrity Problem Test Cases ===\n");
        
        // Test 1: Simple case with celebrity at index 2
        System.out.println("Test 1: Celebrity at index 2");
        boolean[][] test1 = {
            {false, true, true},   // P0 knows P1 and P2
            {true, false, true},   // P1 knows P0 and P2
            {false, false, false}  // P2 knows nobody
        };
        visualizeElimination(3, test1);
        System.out.println("Result: " + findCelebrity(3, test1));
        System.out.println();
        
        // Test 2: No celebrity
        System.out.println("Test 2: No celebrity");
        boolean[][] test2 = {
            {false, true, false},
            {false, false, true},
            {true, false, false}  // Everyone knows someone
        };
        visualizeElimination(3, test2);
        System.out.println("Result: " + findCelebrity(3, test2));
        System.out.println();
        
        // Test 3: Everyone knows everyone
        System.out.println("Test 3: Everyone knows everyone");
        boolean[][] test3 = {
            {false, true, true},
            {true, false, true},
            {true, true, false}
        };
        System.out.println("Result: " + findCelebrity(3, test3) + " (should be -1)");
        System.out.println();
        
        // Test 4: Larger party
        System.out.println("Test 4: Party of 5 with celebrity at index 3");
        boolean[][] test4 = generateKnowledgeMatrix(5, 3);
        System.out.println("Result: " + findCelebrity(5, test4));
        System.out.println();
        
        // Test 5: Edge cases
        System.out.println("Test 5: Single person party");
        boolean[][] test5 = {{false}};
        System.out.println("Result: " + findCelebrity(1, test5) + " (should be 0)");
        System.out.println();
        
        System.out.println("Test 6: Two people, no celebrity");
        boolean[][] test6 = {{false, true}, {true, false}};
        System.out.println("Result: " + findCelebrity(2, test6) + " (should be -1)");
        System.out.println();
        
        // Test stack solution
        System.out.println("Test 7: Stack solution comparison");
        boolean[][] test7 = generateKnowledgeMatrix(6, 2);
        int result1 = findCelebrity(6, test7);
        int result2 = findCelebrityStack(6, test7);
        System.out.println("Elimination result: " + result1);
        System.out.println("Stack result: " + result2);
        System.out.println("Match: " + (result1 == result2));
    }
    
    /**
     * Performance comparison between different approaches.
     */
    public static void benchmark() {
        System.out.println("\n=== Performance Comparison ===");
        
        int n = 10000;
        System.out.println("Party size: " + n + " people");
        
        // Generate test matrix with celebrity at middle
        boolean[][] knows = generateKnowledgeMatrix(n, n/2);
        
        // Two-pass elimination
        long start = System.currentTimeMillis();
        int result1 = findCelebrity(n, knows);
        long time1 = System.currentTimeMillis() - start;
        System.out.println("Two-pass elimination: " + time1 + " ms, result: " + result1);
        
        // Stack solution
        start = System.currentTimeMillis();
        int result2 = findCelebrityStack(n, knows);
        long time2 = System.currentTimeMillis() - start;
        System.out.println("Stack solution: " + time2 + " ms, result: " + result2);
        
        // Brute force (for comparison)
        if (n <= 1000) { // Too slow for large n
            start = System.currentTimeMillis();
            List<Integer> result3 = findAllCelebrities(n, knows);
            long time3 = System.currentTimeMillis() - start;
            System.out.println("Brute force: " + time3 + " ms, result: " + result3);
        }
    }
    
    /**
     * Explain the algorithm with proof of correctness.
     */
    public static void explainAlgorithmProof() {
        System.out.println("\n=== Algorithm Proof ===");
        System.out.println("Why the two-pass elimination algorithm works:");
        System.out.println();
        System.out.println("1. Elimination Phase:");
        System.out.println("   At each step (candidate, i):");
        System.out.println("   - If candidate knows i: candidate cannot be celebrity");
        System.out.println("     (celebrity knows nobody)");
        System.out.println("   - If candidate doesn't know i: i cannot be celebrity");
        System.out.println("     (everyone knows celebrity)");
        System.out.println("   This eliminates n-1 people, leaving one candidate");
        System.out.println();
        System.out.println("2. Verification Phase:");
        System.out.println("   Must verify the candidate because:");
        System.out.println("   - We only eliminated based on candidate's knowledge");
        System.out.println("   - Need to check everyone knows candidate");
        System.out.println("   - Need to check candidate knows nobody");
        System.out.println();
        System.out.println("Proof of optimality:");
        System.out.println("- Must ask at least n-1 'knows' questions to eliminate n-1 people");
        System.out.println("- Our algorithm uses exactly n-1 questions in elimination phase");
        System.out.println("- Plus 2(n-1) questions in verification phase");
        System.out.println("- Total: 3n-3 questions, optimal for worst case");
    }
    
    public static void main(String[] args) {
        // Run test cases
        runTestCases();
        
        // Explain algorithm proof
        explainAlgorithmProof();
        
        // Run performance benchmark (optional)
        // benchmark();
        
        // Additional visualization
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Additional Example: Party of 4 with Celebrity");
        System.out.println("=".repeat(60));
        
        boolean[][] example = {
            {false, true, false, true},   // P0 knows P1 and P3
            {true, false, true, true},    // P1 knows P0, P2, P3
            {false, false, false, false}, // P2 knows nobody (Celebrity!)
            {true, true, false, false}    // P3 knows P0 and P1
        };
        
        visualizeElimination(4, example);
    }
}