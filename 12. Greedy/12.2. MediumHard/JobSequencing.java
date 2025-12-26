import java.util.Arrays;
import java.util.Scanner;

/**
 * Job Sequencing with Deadlines (GFG)
 * 
 * Problem Statement:
 * Given a set of N jobs where each job i has:
 * - A unique ID
 * - A profit Pi (if job is completed by its deadline)
 * - A deadline Di (job must be completed by this time unit, 1-indexed)
 * 
 * Each job takes 1 unit of time to complete.
 * Only one job can be scheduled at a time.
 * 
 * Goal: Find the maximum profit and the number of jobs that can be scheduled.
 * 
 * Example:
 * Input:
 *   Jobs: (id, profit, deadline)
 *   (1, 100, 2)
 *   (2, 19, 1)  
 *   (3, 27, 2)
 *   (4, 25, 1)
 *   (5, 15, 3)
 * Output:
 *   Maximum profit = 142, Jobs scheduled = 3
 * Explanation: Schedule job1 (deadline 2), job3 (deadline 2), job5 (deadline 3)
 * 
 * Greedy Algorithm:
 * 1. Sort jobs in decreasing order of profit
 * 2. Initialize time slots array (size = max deadline)
 * 3. For each job (starting with highest profit):
 *    - Try to place it in the latest available slot ≤ its deadline
 *    - If found, mark slot occupied and add profit
 * 4. Return count of scheduled jobs and total profit
 * 
 * Time Complexity: O(n log n + n × d) where d = max deadline
 * Space Complexity: O(d) for slots array
 */
public class JobSequencing {

    static class Job {
        int id;
        int profit;
        int deadline;
        
        Job(int id, int profit, int deadline) {
            this.id = id;
            this.profit = profit;
            this.deadline = deadline;
        }
    }

    /**
     * Greedy job sequencing algorithm
     * 
     * @param jobs Array of jobs (id, profit, deadline)
     * @param n Number of jobs
     * @return int array [count, totalProfit]
     */
    public int[] jobScheduling(Job[] jobs, int n) {
        // Step 1: Sort jobs in descending order of profit
        // Higher profit jobs should be considered first
        Arrays.sort(jobs, (a, b) -> Integer.compare(b.profit, a.profit));
        
        // Step 2: Find maximum deadline to determine array size
        int maxDeadline = 0;
        for (Job job : jobs) {
            maxDeadline = Math.max(maxDeadline, job.deadline);
        }
        
        // Step 3: Initialize time slots (1-indexed for convenience)
        // slot[i] = job id scheduled at time i, -1 means empty
        int[] slot = new int[maxDeadline + 1];  // +1 for 1-indexed
        Arrays.fill(slot, -1);
        
        // Step 4: Schedule jobs greedily
        int scheduledJobs = 0;
        int totalProfit = 0;
        
        for (Job job : jobs) {
            // Try to schedule this job at the latest possible time ≤ deadline
            // We go from deadline down to 1 to find latest available slot
            for (int time = job.deadline; time > 0; time--) {
                // Check if this time slot is available
                // Also ensure we don't go beyond array bounds
                if (time <= maxDeadline && slot[time] == -1) {
                    // Schedule the job
                    slot[time] = job.id;
                    scheduledJobs++;
                    totalProfit += job.profit;
                    break; // Move to next job
                }
            }
        }
        
        return new int[]{scheduledJobs, totalProfit};
    }
    
    /**
     * Alternative implementation using TreeSet for faster slot finding
     * Time: O(n log n + n log d) where d = max deadline
     */
    public int[] jobSchedulingOptimized(Job[] jobs, int n) {
        // Sort by profit descending
        Arrays.sort(jobs, (a, b) -> Integer.compare(b.profit, a.profit));
        
        // Find max deadline
        int maxDeadline = 0;
        for (Job job : jobs) {
            maxDeadline = Math.max(maxDeadline, job.deadline);
        }
        
        // Initialize available slots (1 to maxDeadline)
        // Using boolean array for simplicity
        boolean[] occupied = new boolean[maxDeadline + 1];
        
        int scheduled = 0, profit = 0;
        
        for (Job job : jobs) {
            // Find latest available slot ≤ deadline
            for (int time = Math.min(job.deadline, maxDeadline); time > 0; time--) {
                if (!occupied[time]) {
                    occupied[time] = true;
                    scheduled++;
                    profit += job.profit;
                    break;
                }
            }
        }
        
        return new int[]{scheduled, profit};
    }
    
    /**
     * Visualization helper: Show step-by-step scheduling
     */
    public void visualizeJobScheduling(Job[] jobs, int n) {
        System.out.println("\n=== Visualizing Job Sequencing ===");
        System.out.println("Original jobs:");
        System.out.printf("%-6s %-10s %-10s\n", "Job", "Profit", "Deadline");
        System.out.println("-".repeat(30));
        for (Job job : jobs) {
            System.out.printf("%-6d %-10d %-10d\n", job.id, job.profit, job.deadline);
        }
        
        // Sort jobs by profit descending
        Job[] sortedJobs = jobs.clone();
        Arrays.sort(sortedJobs, (a, b) -> Integer.compare(b.profit, a.profit));
        
        System.out.println("\nSorted by profit (descending):");
        System.out.printf("%-6s %-10s %-10s\n", "Job", "Profit", "Deadline");
        System.out.println("-".repeat(30));
        for (Job job : sortedJobs) {
            System.out.printf("%-6d %-10d %-10d\n", job.id, job.profit, job.deadline);
        }
        
        // Find max deadline
        int maxDeadline = 0;
        for (Job job : jobs) {
            maxDeadline = Math.max(maxDeadline, job.deadline);
        }
        
        System.out.println("\nMaximum deadline: " + maxDeadline);
        System.out.println("Time slots: 1 to " + maxDeadline);
        
        // Initialize slots
        int[] slot = new int[maxDeadline + 1];
        Arrays.fill(slot, -1);
        
        System.out.println("\n--- Greedy Scheduling Process ---");
        int step = 1;
        int totalProfit = 0;
        
        for (Job job : sortedJobs) {
            System.out.println("\nStep " + step++ + ": Considering Job " + job.id + 
                             " (profit=" + job.profit + ", deadline=" + job.deadline + ")");
            
            System.out.print("  Trying to schedule at time ≤ " + job.deadline + ": ");
            
            boolean scheduled = false;
            for (int time = Math.min(job.deadline, maxDeadline); time > 0; time--) {
                System.out.print(time + " ");
                
                if (slot[time] == -1) {
                    slot[time] = job.id;
                    totalProfit += job.profit;
                    scheduled = true;
                    System.out.println("\n  ✓ Scheduled at time " + time);
                    break;
                }
            }
            
            if (!scheduled) {
                System.out.println("\n  ✗ No available slot ≤ " + job.deadline);
            }
            
            // Display current schedule
            System.out.println("  Current schedule:");
            for (int t = 1; t <= maxDeadline; t++) {
                if (slot[t] != -1) {
                    System.out.printf("    Time %d: Job %d\n", t, slot[t]);
                }
            }
        }
        
        System.out.println("\n=== Final Schedule ===");
        int scheduledJobs = 0;
        for (int t = 1; t <= maxDeadline; t++) {
            if (slot[t] != -1) {
                System.out.printf("Time %d: Job %d\n", t, slot[t]);
                scheduledJobs++;
            }
        }
        
        System.out.println("\nTotal jobs scheduled: " + scheduledJobs);
        System.out.println("Total profit: " + totalProfit);
        
        // Show unscheduled jobs
        System.out.println("\nUnscheduled jobs:");
        boolean[] scheduledIds = new boolean[n + 1];
        for (int t = 1; t <= maxDeadline; t++) {
            if (slot[t] != -1) {
                scheduledIds[slot[t]] = true;
            }
        }
        
        for (Job job : jobs) {
            if (!scheduledIds[job.id]) {
                System.out.printf("Job %d (profit=%d, deadline=%d)\n", 
                    job.id, job.profit, job.deadline);
            }
        }
    }
    
    /**
     * Explanation of why greedy works:
     * 
     * 1. Sorting by profit: Higher profit jobs should be prioritized
     * 2. Latest slot: Scheduling at latest possible time leaves earlier
     *    slots available for jobs with tighter deadlines
     * 3. Proof sketch: Exchange argument
     *    - Suppose we have optimal solution O and greedy solution G
     *    - Find first point where they differ
     *    - Greedy scheduled higher-profit job, optimal scheduled lower-profit
     *    - Can swap without affecting feasibility
     *    - Therefore greedy is optimal
     */
    
    /**
     * Test cases and examples
     */
    public static void runTestCases() {
        JobSequencing solver = new JobSequencing();
        
        // Test case from GFG example
        Job[] test1 = {
            new Job(1, 100, 2),
            new Job(2, 19, 1),
            new Job(3, 27, 2),
            new Job(4, 25, 1),
            new Job(5, 15, 3)
        };
        
        // Test case: All jobs same deadline
        Job[] test2 = {
            new Job(1, 50, 1),
            new Job(2, 40, 1),
            new Job(3, 30, 1),
            new Job(4, 20, 1)
        };
        
        // Test case: Sequential deadlines
        Job[] test3 = {
            new Job(1, 20, 1),
            new Job(2, 15, 2),
            new Job(3, 10, 3),
            new Job(4, 5, 4)
        };
        
        // Test case: One job
        Job[] test4 = {
            new Job(1, 100, 1)
        };
        
        // Test case: Empty
        Job[] test5 = {};
        
        Object[][] testCases = {
            {test1, "GFG Example", 3, 142},
            {test2, "Same Deadline", 1, 50},
            {test3, "Sequential", 4, 50},
            {test4, "Single Job", 1, 100},
            {test5, "Empty", 0, 0}
        };
        
        System.out.println("=== Test Cases ===");
        System.out.printf("%-20s %-15s %-15s %-15s %s\n", 
            "Test Case", "Expected Jobs", "Expected Profit", "Got", "Status");
        System.out.println("-".repeat(80));
        
        for (Object[] test : testCases) {
            Job[] jobs = (Job[]) test[0];
            String name = (String) test[1];
            int expectedJobs = (int) test[2];
            int expectedProfit = (int) test[3];
            
            int[] result = solver.jobScheduling(jobs, jobs.length);
            
            boolean passed = (result[0] == expectedJobs && result[1] == expectedProfit);
            System.out.printf("%-20s %-15d %-15d %-15s %s\n", 
                name, expectedJobs, expectedProfit, 
                result[0] + "/" + result[1], 
                passed ? "✓" : "✗");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // Number of test cases
        
        while (t-- > 0) {
            int n = sc.nextInt();
            Job[] jobs = new Job[n];
            
            for (int i = 0; i < n; i++) {
                // Note: Input format is profit then deadline
                int profit = sc.nextInt();
                int deadline = sc.nextInt();
                // IDs are 1-indexed
                jobs[i] = new Job(i + 1, profit, deadline);
            }
            
            JobSequencing solver = new JobSequencing();
            int[] result = solver.jobScheduling(jobs, n);
            System.out.println(result[0] + " " + result[1]);
            
            // Uncomment for visualization
            // solver.visualizeJobScheduling(jobs, n);
        }
        
        sc.close();
        
        // Uncomment to run test cases
        // runTestCases();
    }
}