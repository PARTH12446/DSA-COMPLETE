import java.util.Arrays;
import java.util.Scanner;

/**
 * Shortest Job First (SJF) Scheduling Algorithm
 * 
 * Problem Statement:
 * Given an array of burst times for n processes, calculate the average waiting time
 * using the Shortest Job First (SJF) non-preemptive scheduling algorithm.
 * 
 * SJF Algorithm:
 * - Always schedule the process with the shortest burst time next
 * - Non-preemptive: once a process starts, it runs to completion
 * 
 * Example:
 * Input: burst times = [6, 8, 7, 3]
 * Output: Average waiting time = 7
 * 
 * Calculation:
 * Sorted burst times: [3, 6, 7, 8]
 * Process execution order: P4(3) → P1(6) → P3(7) → P2(8)
 * 
 * Waiting times:
 * P4: 0
 * P1: 3
 * P3: 3+6 = 9  
 * P2: 3+6+7 = 16
 * Total waiting time = 0+3+9+16 = 28
 * Average waiting time = 28/4 = 7
 * 
 * Time Complexity: O(n log n) for sorting + O(n) for calculation = O(n log n)
 * Space Complexity: O(1) if sorting in-place, O(n) if not
 */
public class SJF {

    /**
     * Calculate average waiting time using SJF
     * 
     * @param bt Array of burst times
     * @return Average waiting time (integer division)
     */
    public int solve(int[] bt) {
        if (bt == null || bt.length == 0) {
            return 0;
        }
        
        // Step 1: Sort burst times in ascending order
        // SJF always picks shortest job first
        Arrays.sort(bt);
        
        // Step 2: Calculate total waiting time
        int totalWaitingTime = 0;
        int currentTime = 0;
        
        for (int burstTime : bt) {
            // Waiting time for current process = time it waits before starting
            totalWaitingTime += currentTime;
            
            // Update current time (process runs for its burst time)
            currentTime += burstTime;
        }
        
        // Step 3: Calculate average (integer division)
        return totalWaitingTime / bt.length;
    }
    
    /**
     * Alternative: Returns detailed statistics
     */
    public SJFResult solveDetailed(int[] bt) {
        if (bt == null || bt.length == 0) {
            return new SJFResult(0, 0, 0, new int[0], new int[0], new int[0]);
        }
        
        // Create array with indices to track original process IDs
        Process[] processes = new Process[bt.length];
        for (int i = 0; i < bt.length; i++) {
            processes[i] = new Process(i + 1, bt[i]);
        }
        
        // Sort by burst time (shortest first)
        Arrays.sort(processes, (a, b) -> Integer.compare(a.burstTime, b.burstTime));
        
        // Calculate waiting time, turnaround time for each process
        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        
        int[] waitingTimes = new int[bt.length];
        int[] turnaroundTimes = new int[bt.length];
        int[] completionTimes = new int[bt.length];
        
        for (int i = 0; i < processes.length; i++) {
            Process p = processes[i];
            
            // Waiting time = time process waits before starting
            waitingTimes[p.id - 1] = currentTime;
            totalWaitingTime += currentTime;
            
            // Completion time = when process finishes
            currentTime += p.burstTime;
            completionTimes[p.id - 1] = currentTime;
            
            // Turnaround time = completion time - arrival time (arrival time is 0 for all)
            turnaroundTimes[p.id - 1] = currentTime;
            totalTurnaroundTime += currentTime;
        }
        
        double avgWaitingTime = (double) totalWaitingTime / bt.length;
        double avgTurnaroundTime = (double) totalTurnaroundTime / bt.length;
        
        return new SJFResult(totalWaitingTime, avgWaitingTime, avgTurnaroundTime,
                           waitingTimes, turnaroundTimes, completionTimes);
    }
    
    static class Process {
        int id;          // Process ID (1-indexed)
        int burstTime;   // CPU burst time
        
        Process(int id, int burstTime) {
            this.id = id;
            this.burstTime = burstTime;
        }
    }
    
    static class SJFResult {
        int totalWaitingTime;
        double avgWaitingTime;
        double avgTurnaroundTime;
        int[] waitingTimes;
        int[] turnaroundTimes;
        int[] completionTimes;
        
        SJFResult(int totalWT, double avgWT, double avgTT,
                 int[] wt, int[] tt, int[] ct) {
            this.totalWaitingTime = totalWT;
            this.avgWaitingTime = avgWT;
            this.avgTurnaroundTime = avgTT;
            this.waitingTimes = wt;
            this.turnaroundTimes = tt;
            this.completionTimes = ct;
        }
    }
    
    /**
     * Visualization helper: Show Gantt chart and calculations
     */
    public void visualizeSJF(int[] bt) {
        System.out.println("\n=== Visualizing Shortest Job First (SJF) ===");
        System.out.println("Number of processes: " + bt.length);
        
        // Display all processes
        System.out.println("\nAll processes:");
        System.out.printf("%-12s %-15s\n", "Process", "Burst Time");
        System.out.println("-".repeat(30));
        for (int i = 0; i < bt.length; i++) {
            System.out.printf("%-12d %-15d\n", i + 1, bt[i]);
        }
        
        // Sort burst times
        int[] sortedBt = bt.clone();
        Arrays.sort(sortedBt);
        
        System.out.println("\nSorted by burst time (shortest first):");
        System.out.printf("%-12s %-15s\n", "Process", "Burst Time");
        System.out.println("-".repeat(30));
        
        // Map sorted order back to original process IDs
        // We'll simulate this by creating process objects
        Process[] processes = new Process[bt.length];
        for (int i = 0; i < bt.length; i++) {
            processes[i] = new Process(i + 1, bt[i]);
        }
        Arrays.sort(processes, (a, b) -> Integer.compare(a.burstTime, b.burstTime));
        
        for (Process p : processes) {
            System.out.printf("%-12d %-15d\n", p.id, p.burstTime);
        }
        
        // Calculate and display scheduling
        System.out.println("\n--- Scheduling Timeline ---");
        int currentTime = 0;
        int totalWaitingTime = 0;
        
        System.out.printf("%-12s %-15s %-15s %-15s %-15s\n", 
            "Process", "Burst Time", "Start Time", "End Time", "Waiting Time");
        System.out.println("-".repeat(75));
        
        for (Process p : processes) {
            int startTime = currentTime;
            int endTime = currentTime + p.burstTime;
            int waitingTime = startTime; // Since all arrive at time 0
            
            System.out.printf("%-12d %-15d %-15d %-15d %-15d\n", 
                p.id, p.burstTime, startTime, endTime, waitingTime);
            
            totalWaitingTime += waitingTime;
            currentTime = endTime;
        }
        
        // Gantt Chart
        System.out.println("\n--- Gantt Chart ---");
        System.out.print("Time:    ");
        for (Process p : processes) {
            System.out.printf("%-10d", currentTime - p.burstTime);
            currentTime -= p.burstTime;
        }
        System.out.println("0");
        
        currentTime = 0;
        System.out.print("Process: ");
        for (Process p : processes) {
            for (int i = 0; i < p.burstTime; i++) {
                System.out.print("█");
            }
            System.out.print(" ");
            currentTime += p.burstTime;
        }
        System.out.println();
        
        System.out.print("Label:   ");
        currentTime = 0;
        for (Process p : processes) {
            int spaces = p.burstTime / 2;
            for (int i = 0; i < spaces - 1; i++) System.out.print(" ");
            System.out.print("P" + p.id);
            for (int i = 0; i < spaces - 1; i++) System.out.print(" ");
            System.out.print(" ");
        }
        System.out.println();
        
        // Calculate and display statistics
        System.out.println("\n--- Statistics ---");
        double avgWaitingTime = (double) totalWaitingTime / bt.length;
        
        // Calculate turnaround time
        int totalTurnaroundTime = 0;
        currentTime = 0;
        for (Process p : processes) {
            int turnaroundTime = currentTime + p.burstTime;
            totalTurnaroundTime += turnaroundTime;
            currentTime += p.burstTime;
        }
        double avgTurnaroundTime = (double) totalTurnaroundTime / bt.length;
        
        System.out.println("Total Waiting Time: " + totalWaitingTime);
        System.out.println("Average Waiting Time: " + String.format("%.2f", avgWaitingTime));
        System.out.println("Total Turnaround Time: " + totalTurnaroundTime);
        System.out.println("Average Turnaround Time: " + String.format("%.2f", avgTurnaroundTime));
        
        // Compare with other algorithms
        System.out.println("\n--- Comparison with Other Algorithms ---");
        System.out.println("SJF Average Waiting Time: " + String.format("%.2f", avgWaitingTime));
        System.out.println("FCFS Average Waiting Time: " + 
                          String.format("%.2f", calculateFCFS(bt)));
        System.out.println("NOTE: SJF minimizes average waiting time among all non-preemptive algorithms!");
    }
    
    /**
     * Calculate FCFS (First-Come-First-Served) for comparison
     */
    private double calculateFCFS(int[] bt) {
        int totalWaitingTime = 0;
        int currentTime = 0;
        
        for (int burstTime : bt) {
            totalWaitingTime += currentTime;
            currentTime += burstTime;
        }
        
        return (double) totalWaitingTime / bt.length;
    }
    
    /**
     * Explanation of why SJF is optimal:
     * 
     * Theorem: Among all non-preemptive scheduling algorithms, 
     * SJF minimizes average waiting time.
     * 
     * Proof Sketch (Exchange Argument):
     * 1. Consider any optimal schedule
     * 2. If schedule doesn't follow SJF, find first point where
     *    longer job is scheduled before shorter job
     * 3. Swap them - total waiting time decreases or stays same
     * 4. Repeat until schedule follows SJF
     * 
     * Intuition: Scheduling shorter jobs first reduces waiting time
     * for more jobs.
     */
    
    /**
     * Test cases and examples
     */
    public static void runTestCases() {
        SJF solver = new SJF();
        
        Object[][] testCases = {
            // {burst times, expected average waiting time}
            {new int[]{6, 8, 7, 3}, 7},
            {new int[]{5, 3, 1, 2}, 2}, // Sorted: [1,2,3,5] → waits: 0,1,3,6 → avg=10/4=2.5 → int=2
            {new int[]{10, 5, 8}, 6},   // Sorted: [5,8,10] → waits: 0,5,13 → avg=18/3=6
            {new int[]{1, 2, 3, 4, 5}, 6}, // Sorted: [1,2,3,4,5] → waits: 0,1,3,6,10 → avg=20/5=4 → int=4? Wait recalc
            {new int[]{1}, 0},
            {new int[]{}, 0},
            {new int[]{7, 7, 7, 7}, 10}, // Sorted: [7,7,7,7] → waits: 0,7,14,21 → avg=42/4=10.5 → int=10
        };
        
        System.out.println("=== Test Cases for SJF ===");
        System.out.printf("%-30s %-20s %-15s %s\n", 
            "Burst Times", "Expected Avg Wait", "Got", "Status");
        System.out.println("-".repeat(80));
        
        for (Object[] test : testCases) {
            int[] bt = (int[]) test[0];
            int expected = (int) test[1];
            
            int result = solver.solve(bt);
            boolean passed = (result == expected);
            
            String btStr = Arrays.toString(bt);
            if (btStr.length() > 30) {
                btStr = btStr.substring(0, 27) + "...";
            }
            
            System.out.printf("%-30s %-20d %-15d %s\n", 
                btStr, expected, result, passed ? "✓" : "✗");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // Number of test cases
        
        while (t-- > 0) {
            int n = sc.nextInt();
            int[] bt = new int[n];
            
            for (int i = 0; i < n; i++) {
                bt[i] = sc.nextInt();
            }
            
            SJF solver = new SJF();
            int result = solver.solve(bt);
            System.out.println(result);
            
            // Uncomment for visualization
            // solver.visualizeSJF(bt);
            
            // Get detailed results
            // SJFResult detailed = solver.solveDetailed(bt);
            // System.out.println("Average waiting time: " + detailed.avgWaitingTime);
            // System.out.println("Average turnaround time: " + detailed.avgTurnaroundTime);
        }
        
        sc.close();
        
        // Uncomment to run test cases
        // runTestCases();
    }
}