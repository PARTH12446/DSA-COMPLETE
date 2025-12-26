import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Task Scheduler (LeetCode 621).
 * Given tasks represented by characters and a cooling interval n,
 * return the least number of time units that the CPU will take to finish all tasks.
 *
 * Problem Statement:
 * You are given an array of CPU tasks, each represented by letters A to Z, and a cooling time n.
 * Each task takes 1 unit of time to complete. After completing a task, you must wait at least
 * n units of time before performing the same task again. During the cooling period, you can
 * perform other tasks or stay idle.
 *
 * Example:
 * Input: tasks = ["A","A","A","B","B","B"], n = 2
 * Output: 8
 * Explanation: A -> B -> idle -> A -> B -> idle -> A -> B
 *
 * Approach: Max-Heap Simulation
 * 1. Count frequency of each task
 * 2. Use a max-heap to always pick the task with highest remaining count
 * 3. Process tasks in cycles of length (n + 1)
 * 4. In each cycle:
 *    - Extract up to (n + 1) tasks from heap
 *    - Execute each task (decrease count)
 *    - If task still has remaining count, store temporarily
 *    - Push back stored tasks after cycle ends
 * 5. Count idle time if heap empties before filling cycle
 *
 * Time Complexity: O(T log U) where T = total tasks, U = unique tasks
 * Space Complexity: O(U) for heap and frequency map
 */
public class TaskScheduler {

    /**
     * Method 1: Heap-based simulation (intuitive approach)
     */
    public int leastInterval(char[] tasks, int n) {
        // Edge case: if no tasks, time is 0
        if (tasks == null || tasks.length == 0) {
            return 0;
        }
        
        // Edge case: no cooling time, execute all tasks back-to-back
        if (n == 0) {
            return tasks.length;
        }

        // Step 1: Count frequency of each task
        Map<Character, Integer> freq = new HashMap<>();
        for (char task : tasks) {
            freq.put(task, freq.getOrDefault(task, 0) + 1);
        }

        // Step 2: Create max-heap (priority queue with reverse order)
        // Max-heap allows us to always pick task with highest remaining count
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.addAll(freq.values());

        int totalTime = 0;  // Total time units needed

        // Step 3: Process tasks in cycles of (n + 1) time units
        while (!maxHeap.isEmpty()) {
            int cycleLength = n + 1;  // Each cycle can contain at most (n+1) tasks
            
            // Temporary list to store tasks executed in this cycle that still have remaining work
            java.util.List<Integer> tempStorage = new java.util.ArrayList<>();
            
            // Execute tasks in current cycle
            while (cycleLength > 0 && !maxHeap.isEmpty()) {
                int remainingCount = maxHeap.poll() - 1;  // Execute task once
                
                if (remainingCount > 0) {
                    tempStorage.add(remainingCount);  // Task still has remaining executions
                }
                
                totalTime++;    // 1 unit of time for executing task
                cycleLength--;  // Reduce available slots in current cycle
            }
            
            // Step 4: Return tasks with remaining count back to heap
            maxHeap.addAll(tempStorage);
            
            // Step 5: Handle idle time if heap is not empty but cycle ended
            if (!maxHeap.isEmpty()) {
                // Remaining cycleLength represents idle time
                totalTime += cycleLength;
            }
        }
        
        return totalTime;
    }
    
    /**
     * Method 2: Mathematical formula (optimal approach)
     * Time: O(T), Space: O(1)
     * 
     * Formula: time = Math.max(tasks.length, (maxFreq - 1) * (n + 1) + countOfMaxFreq)
     * Where:
     * - maxFreq = highest frequency among tasks
     * - countOfMaxFreq = how many tasks have frequency = maxFreq
     */
    public int leastIntervalFormula(char[] tasks, int n) {
        if (tasks == null || tasks.length == 0) return 0;
        if (n == 0) return tasks.length;
        
        // Count frequencies
        int[] freq = new int[26];
        for (char task : tasks) {
            freq[task - 'A']++;
        }
        
        // Find max frequency and count of tasks with max frequency
        int maxFreq = 0;
        int countOfMaxFreq = 0;
        
        for (int count : freq) {
            if (count > maxFreq) {
                maxFreq = count;
                countOfMaxFreq = 1;
            } else if (count == maxFreq) {
                countOfMaxFreq++;
            }
        }
        
        // Calculate using formula
        int time = (maxFreq - 1) * (n + 1) + countOfMaxFreq;
        
        // If tasks can be scheduled without idle time, total time = tasks.length
        return Math.max(tasks.length, time);
    }
    
    /**
     * Method 3: Alternative heap approach with task tracking
     * Tracks actual task characters, not just counts
     */
    public int leastIntervalDetailed(char[] tasks, int n) {
        if (tasks == null || tasks.length == 0) return 0;
        if (n == 0) return tasks.length;
        
        // Count frequencies
        Map<Character, Integer> freq = new HashMap<>();
        for (char task : tasks) {
            freq.put(task, freq.getOrDefault(task, 0) + 1);
        }
        
        // Max-heap based on frequency
        PriorityQueue<Map.Entry<Character, Integer>> maxHeap = 
            new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        maxHeap.addAll(freq.entrySet());
        
        int time = 0;
        java.util.List<Map.Entry<Character, Integer>> executed = new java.util.ArrayList<>();
        
        while (!maxHeap.isEmpty()) {
            int cycle = n + 1;
            executed.clear();
            
            // Execute tasks in current cycle
            while (cycle > 0 && !maxHeap.isEmpty()) {
                Map.Entry<Character, Integer> current = maxHeap.poll();
                
                // Execute task
                System.out.println("Time " + time + ": Execute task " + current.getKey());
                
                // Reduce count
                current.setValue(current.getValue() - 1);
                
                if (current.getValue() > 0) {
                    executed.add(current);
                }
                
                time++;
                cycle--;
            }
            
            // Return tasks with remaining count to heap
            for (Map.Entry<Character, Integer> task : executed) {
                maxHeap.offer(task);
            }
            
            // Add idle time if needed
            if (!maxHeap.isEmpty() && cycle > 0) {
                System.out.println("Time " + time + " to " + (time + cycle - 1) + ": IDLE");
                time += cycle;
            }
        }
        
        return time;
    }
    
    /**
     * Visualization helper
     */
    public void visualizeTaskScheduler(char[] tasks, int n) {
        System.out.println("\n=== Visualizing Task Scheduler ===");
        System.out.println("Tasks: " + java.util.Arrays.toString(tasks));
        System.out.println("Cooling interval: " + n);
        
        // Count frequencies
        Map<Character, Integer> freq = new HashMap<>();
        for (char task : tasks) {
            freq.put(task, freq.getOrDefault(task, 0) + 1);
        }
        
        System.out.println("\nTask frequencies: " + freq);
        
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.addAll(freq.values());
        
        int time = 0;
        int cycleNumber = 1;
        
        while (!maxHeap.isEmpty()) {
            System.out.println("\n--- Cycle " + cycleNumber++ + " (up to " + (n + 1) + " tasks) ---");
            
            int cycle = n + 1;
            java.util.List<Integer> temp = new java.util.ArrayList<>();
            
            // Execute tasks
            while (cycle > 0 && !maxHeap.isEmpty()) {
                int count = maxHeap.poll();
                System.out.println("  Time " + time + ": Execute task (remaining count was " + count + ")");
                count--;
                time++;
                cycle--;
                
                if (count > 0) {
                    temp.add(count);
                    System.out.println("    Task still has " + count + " remaining executions");
                }
            }
            
            // Add tasks back to heap
            maxHeap.addAll(temp);
            
            // Add idle time if needed
            if (!maxHeap.isEmpty() && cycle > 0) {
                System.out.println("  Time " + time + " to " + (time + cycle - 1) + ": IDLE (" + cycle + " units)");
                time += cycle;
            }
        }
        
        System.out.println("\nTotal time units: " + time);
        
        // Show formula calculation
        System.out.println("\n=== Formula Calculation ===");
        int[] freqArray = new int[26];
        for (char task : tasks) {
            freqArray[task - 'A']++;
        }
        
        int maxFreq = 0;
        int countMax = 0;
        for (int f : freqArray) {
            if (f > maxFreq) {
                maxFreq = f;
                countMax = 1;
            } else if (f == maxFreq) {
                countMax++;
            }
        }
        
        int formulaTime = (maxFreq - 1) * (n + 1) + countMax;
        System.out.println("Max frequency: " + maxFreq);
        System.out.println("Tasks with max frequency: " + countMax);
        System.out.println("Formula: (" + maxFreq + " - 1) * (" + n + " + 1) + " + countMax + " = " + formulaTime);
        System.out.println("Max with tasks.length: max(" + formulaTime + ", " + tasks.length + ") = " + 
                          Math.max(formulaTime, tasks.length));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // Number of test cases
        
        while (t-- > 0) {
            int len = sc.nextInt();
            char[] tasks = new char[len];
            
            for (int i = 0; i < len; i++) {
                String s = sc.next();
                tasks[i] = s.charAt(0);
            }
            
            int n = sc.nextInt();  // Cooling interval
            
            TaskScheduler solver = new TaskScheduler();
            
            // Method 1: Heap-based simulation
            int ans1 = solver.leastInterval(tasks, n);
            System.out.println("Heap simulation result: " + ans1);
            
            // Method 2: Formula approach
            int ans2 = solver.leastIntervalFormula(tasks, n);
            System.out.println("Formula result: " + ans2);
            
            // Uncomment for visualization
            // solver.visualizeTaskScheduler(tasks, n);
        }
        
        sc.close();
    }
}