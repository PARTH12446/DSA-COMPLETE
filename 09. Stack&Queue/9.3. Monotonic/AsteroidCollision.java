import java.util.*;

/**
 * Asteroid Collision Problem
 * 
 * Problem: Asteroids are moving in a line. Positive values move right,
 * negative values move left. When two asteroids meet, the smaller one
 * explodes. If equal size, both explode.
 * 
 * Rules:
 * 1. Positive asteroids move → (right)
 * 2. Negative asteroids move ← (left)
 * 3. Collisions only occur when right-moving asteroid meets left-moving asteroid
 * 4. Larger asteroid survives, smaller explodes
 * 5. Equal size: both explode
 * 
 * Example: [5, 10, -5] → [5, 10] (10 destroys -5)
 * Example: [8, -8] → [] (both explode)
 * 
 * Time Complexity: O(n) where n = number of asteroids
 * Space Complexity: O(n) for stack storage
 */
public class AsteroidCollision {

    /**
     * Standard solution using stack
     * 
     * Algorithm:
     * 1. Iterate through asteroids
     * 2. For each asteroid:
     *    - If moving right (positive), push to stack
     *    - If moving left (negative), check collisions with right-moving asteroids on stack
     * 3. Handle collisions based on size comparison
     * 
     * @param asteroids Array of asteroid sizes with signs indicating direction
     * @return Array of surviving asteroids
     */
    public static int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> st = new Stack<>();
        
        for (int ast : asteroids) {
            boolean currentSurvives = true;
            
            // Collision scenario: current asteroid moves left, stack top moves right
            while (!st.isEmpty() && ast < 0 && st.peek() > 0) {
                int stackTop = st.peek();
                int currentSize = Math.abs(ast);
                
                if (currentSize > stackTop) {
                    // Current asteroid destroys stack top
                    st.pop();
                    continue; // Check next asteroid in stack
                } 
                else if (currentSize == stackTop) {
                    // Both asteroids destroy each other
                    st.pop();
                    currentSurvives = false;
                    break;
                } 
                else {
                    // Stack top destroys current asteroid
                    currentSurvives = false;
                    break;
                }
            }
            
            // If current asteroid survived all collisions, add to stack
            if (currentSurvives) {
                st.push(ast);
            }
        }
        
        // Convert stack to array (reversed order)
        int[] result = new int[st.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = st.pop();
        }
        
        // Reverse to maintain original relative order
        reverseArray(result);
        return result;
    }

    /**
     * Alternative approach using ArrayList as stack
     * More efficient for array conversion
     */
    public static int[] asteroidCollisionArrayList(int[] asteroids) {
        List<Integer> list = new ArrayList<>();
        
        for (int ast : asteroids) {
            boolean addCurrent = true;
            
            while (!list.isEmpty() && ast < 0 && list.get(list.size() - 1) > 0) {
                int last = list.get(list.size() - 1);
                
                if (Math.abs(ast) > last) {
                    list.remove(list.size() - 1);
                } 
                else if (Math.abs(ast) == last) {
                    list.remove(list.size() - 1);
                    addCurrent = false;
                    break;
                } 
                else {
                    addCurrent = false;
                    break;
                }
            }
            
            if (addCurrent) {
                list.add(ast);
            }
        }
        
        // Convert List to array
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * Simulation version - shows step-by-step collisions
     * Useful for understanding the process
     */
    public static int[] asteroidCollisionWithSteps(int[] asteroids) {
        Stack<Integer> st = new Stack<>();
        System.out.println("Initial asteroids: " + Arrays.toString(asteroids));
        
        for (int i = 0; i < asteroids.length; i++) {
            int ast = asteroids[i];
            System.out.println("\nProcessing asteroid " + ast + " at position " + i);
            System.out.println("Current stack: " + st);
            
            boolean destroyed = false;
            
            while (!st.isEmpty() && ast < 0 && st.peek() > 0) {
                int top = st.peek();
                System.out.println("  Collision between " + top + " (right) and " + ast + " (left)");
                
                if (Math.abs(ast) > top) {
                    System.out.println("  " + ast + " destroys " + top);
                    st.pop();
                } 
                else if (Math.abs(ast) == top) {
                    System.out.println("  Both " + ast + " and " + top + " destroy each other");
                    st.pop();
                    destroyed = true;
                    break;
                } 
                else {
                    System.out.println("  " + top + " destroys " + ast);
                    destroyed = true;
                    break;
                }
            }
            
            if (!destroyed) {
                st.push(ast);
                System.out.println("  Added " + ast + " to stack");
            }
        }
        
        int[] result = new int[st.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = st.pop();
        }
        reverseArray(result);
        
        System.out.println("\nFinal result: " + Arrays.toString(result));
        return result;
    }

    /**
     * Reverse array in-place
     */
    private static void reverseArray(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }

    /**
     * Visualization helper - shows asteroid directions
     */
    public static void visualizeAsteroids(int[] asteroids) {
        System.out.println("\nVisualization:");
        for (int ast : asteroids) {
            String direction = ast > 0 ? "→" : "←";
            System.out.printf("%3d %s ", ast, direction);
        }
        System.out.println();
    }

    /**
     * Test cases
     */
    public static void main(String[] args) {
        System.out.println("=== ASTEROID COLLISION DEMO ===\n");
        
        // Test case 1: Basic collision
        System.out.println("Test 1: [5, 10, -5]");
        int[] test1 = {5, 10, -5};
        int[] result1 = asteroidCollision(test1);
        System.out.println("Result: " + Arrays.toString(result1) + " (Expected: [5, 10])");
        visualizeAsteroids(result1);
        
        // Test case 2: Mutual destruction
        System.out.println("\nTest 2: [8, -8]");
        int[] test2 = {8, -8};
        int[] result2 = asteroidCollision(test2);
        System.out.println("Result: " + Arrays.toString(result2) + " (Expected: [])");
        
        // Test case 3: Multiple collisions
        System.out.println("\nTest 3: [10, 2, -5]");
        int[] test3 = {10, 2, -5};
        int[] result3 = asteroidCollision(test3);
        System.out.println("Result: " + Arrays.toString(result3) + " (Expected: [10])");
        visualizeAsteroids(result3);
        
        // Test case 4: Complex scenario
        System.out.println("\nTest 4: [-2, -1, 1, 2]");
        int[] test4 = {-2, -1, 1, 2};
        int[] result4 = asteroidCollision(test4);
        System.out.println("Result: " + Arrays.toString(result4) + " (Expected: [-2, -1, 1, 2])");
        visualizeAsteroids(result4);
        
        // Test case 5: Large example
        System.out.println("\nTest 5: [5, 10, -5, -10, 8, -8, 3, -3]");
        int[] test5 = {5, 10, -5, -10, 8, -8, 3, -3};
        int[] result5 = asteroidCollisionWithSteps(test5);
        System.out.println("Final Result: " + Arrays.toString(result5));
        
        // Test case 6: Edge cases
        System.out.println("\nTest 6: [] (empty array)");
        int[] test6 = {};
        int[] result6 = asteroidCollision(test6);
        System.out.println("Result: " + Arrays.toString(result6) + " (Expected: [])");
        
        System.out.println("\nTest 7: [1, 2, 3] (all positive)");
        int[] test7 = {1, 2, 3};
        int[] result7 = asteroidCollision(test7);
        System.out.println("Result: " + Arrays.toString(result7) + " (Expected: [1, 2, 3])");
        
        System.out.println("\nTest 8: [-1, -2, -3] (all negative)");
        int[] test8 = {-1, -2, -3};
        int[] result8 = asteroidCollision(test8);
        System.out.println("Result: " + Arrays.toString(result8) + " (Expected: [-1, -2, -3])");
    }
    
    /**
     * Performance test for large input
     */
    public static void performanceTest() {
        System.out.println("\n=== PERFORMANCE TEST ===");
        
        // Generate large test case
        int size = 100000;
        int[] largeTest = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            largeTest[i] = rand.nextInt(100) - 50; // Values between -50 and 50
        }
        
        long start = System.nanoTime();
        asteroidCollision(largeTest);
        long end = System.nanoTime();
        
        System.out.printf("Processed %,d asteroids in %.2f ms\n", 
            size, (end - start) / 1_000_000.0);
    }
    
    /**
     * Additional: Find survivors without using stack (array manipulation)
     * Alternative approach for interview discussions
     */
    public static int[] asteroidCollisionNoStack(int[] asteroids) {
        List<Integer> result = new ArrayList<>();
        int i = 0;
        
        while (i < asteroids.length) {
            int current = asteroids[i];
            
            if (result.isEmpty() || current > 0 || result.get(result.size() - 1) < 0) {
                // No collision possible
                result.add(current);
                i++;
            } 
            else {
                // Potential collision
                int last = result.get(result.size() - 1);
                
                if (Math.abs(current) > last) {
                    // Current destroys last
                    result.remove(result.size() - 1);
                } 
                else if (Math.abs(current) == last) {
                    // Both destroyed
                    result.remove(result.size() - 1);
                    i++;
                } 
                else {
                    // Last destroys current
                    i++;
                }
            }
        }
        
        return result.stream().mapToInt(Integer::intValue).toArray();
    }
}