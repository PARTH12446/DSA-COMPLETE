import java.util.Arrays;
import java.util.Scanner;

/**
 * Minimum Platforms (GFG)
 * 
 * Problem Statement:
 * Given the arrival and departure times of all trains at a railway station,
 * find the minimum number of platforms required so that no train waits.
 * 
 * Example:
 * Input:
 *   n = 6
 *   arr[] = {0900, 0940, 0950, 1100, 1500, 1800}
 *   dep[] = {0910, 1200, 1120, 1130, 1900, 2000}
 * Output: 3
 * Explanation: Maximum 3 trains present simultaneously
 * 
 * Approach 1: Two-pointer greedy after sorting
 * 1. Sort both arrival and departure arrays
 * 2. Use two pointers to simulate timeline:
 *    - When a train arrives (arr[i] ≤ dep[j]): need new platform, count++
 *    - When a train departs (arr[i] > dep[j]): free platform, count--
 * 3. Track maximum platforms needed at any time
 * 
 * Time Complexity: O(n log n) for sorting + O(n) for two-pointer = O(n log n)
 * Space Complexity: O(1) excluding sorting space
 * 
 * Approach 2: Sweep line with time array (if time range is limited)
 */
public class MinPlatforms {

    /**
     * Two-pointer greedy solution
     * 
     * @param n Number of trains
     * @param arr Array of arrival times
     * @param dep Array of departure times
     * @return Minimum platforms required
     */
    public int minimumPlatform(int n, int[] arr, int[] dep) {
        // Edge cases
        if (n == 0) return 0;
        if (n == 1) return 1;
        
        // Step 1: Sort arrival and departure times separately
        Arrays.sort(arr);
        Arrays.sort(dep);
        
        // Step 2: Two-pointer simulation
        int platforms = 0;   // Current platforms in use
        int maxPlatforms = 0; // Maximum platforms needed
        int i = 0;           // Pointer for arrivals
        int j = 0;           // Pointer for departures
        
        while (i < n && j < n) {
            // If a train arrives before or when another departs
            if (arr[i] <= dep[j]) {
                platforms++;        // Need a new platform
                maxPlatforms = Math.max(maxPlatforms, platforms);
                i++;                // Move to next arrival
            } else {
                platforms--;        // A platform becomes free
                j++;                // Move to next departure
            }
        }
        
        return maxPlatforms;
    }
    
    /**
     * Alternative: Sweep line algorithm using time array
     * Works well when time range is limited (e.g., 0-2400)
     * Time: O(n + T) where T = time range
     */
    public int minimumPlatformSweep(int n, int[] arr, int[] dep) {
        if (n == 0) return 0;
        
        // Find maximum time (assuming times are in 24-hour format 0-2400)
        int maxTime = 2400;
        int[] timeline = new int[maxTime + 1];
        
        // Mark arrivals and departures on timeline
        for (int i = 0; i < n; i++) {
            timeline[arr[i]]++;      // Train arrives at this time
            timeline[dep[i]]--;      // Train departs at this time
            
            // If arrival and departure at same time, need separate handling
            // Actually, if a train departs at the same time another arrives,
            // they can use same platform, so we need to adjust
        }
        
        // Better approach: Use difference array for intervals
        int[] diff = new int[2361]; // Up to 23:60
        
        for (int i = 0; i < n; i++) {
            diff[arr[i]]++;
            // A train is considered to occupy platform from arrival to departure
            // So we decrement AFTER departure time
            if (dep[i] + 1 < diff.length) {
                diff[dep[i] + 1]--;
            }
        }
        
        // Compute prefix sum to find maximum concurrent trains
        int current = 0;
        int max = 0;
        for (int i = 0; i < diff.length; i++) {
            current += diff[i];
            max = Math.max(max, current);
        }
        
        return max;
    }
    
    /**
     * Visualization helper: Show timeline and platform allocation
     */
    public void visualizePlatforms(int n, int[] arr, int[] dep) {
        System.out.println("\n=== Visualizing Minimum Platforms ===");
        System.out.println("Number of trains: " + n);
        
        System.out.println("\nTrain schedule:");
        System.out.printf("%-10s %-15s %-15s\n", "Train", "Arrival", "Departure");
        System.out.println("-".repeat(40));
        for (int i = 0; i < n; i++) {
            System.out.printf("%-10d %-15d %-15d\n", i+1, arr[i], dep[i]);
        }
        
        // Sort arrays for processing
        int[] sortedArr = arr.clone();
        int[] sortedDep = dep.clone();
        Arrays.sort(sortedArr);
        Arrays.sort(sortedDep);
        
        System.out.println("\n--- Timeline Simulation ---");
        System.out.println("Sorted arrivals:   " + Arrays.toString(sortedArr));
        System.out.println("Sorted departures: " + Arrays.toString(sortedDep));
        
        int platforms = 0;
        int maxPlatforms = 0;
        int i = 0, j = 0;
        int step = 1;
        
        System.out.println("\nStep-by-step simulation:");
        System.out.printf("%-10s %-15s %-20s %-15s %-20s\n", 
            "Step", "Time", "Event", "Platforms", "Max Platforms");
        System.out.println("-".repeat(80));
        
        while (i < n || j < n) {
            String time;
            String event;
            
            if (i < n && j < n && sortedArr[i] <= sortedDep[j]) {
                // Arrival event
                time = String.valueOf(sortedArr[i]);
                event = "Train arrives";
                platforms++;
                maxPlatforms = Math.max(maxPlatforms, platforms);
                System.out.printf("%-10d %-15s %-20s %-15d %-20d\n", 
                    step++, time, event, platforms, maxPlatforms);
                i++;
            } else if (j < n) {
                // Departure event (when no arrivals or arrival time > departure)
                time = String.valueOf(sortedDep[j]);
                event = "Train departs";
                platforms--;
                System.out.printf("%-10d %-15s %-20s %-15d %-20d\n", 
                    step++, time, event, platforms, maxPlatforms);
                j++;
            } else {
                break;
            }
        }
        
        // Handle remaining departures
        while (j < n) {
            String time = String.valueOf(sortedDep[j]);
            String event = "Train departs";
            platforms--;
            System.out.printf("%-10d %-15s %-20s %-15d %-20d\n", 
                step++, time, event, platforms, maxPlatforms);
            j++;
        }
        
        System.out.println("\n=== Result ===");
        System.out.println("Minimum platforms required: " + maxPlatforms);
        
        // Show alternative allocation visualization
        System.out.println("\n--- Alternative: Platform Allocation ---");
        showPlatformAllocation(arr, dep, maxPlatforms);
    }
    
    /**
     * Show how trains can be allocated to platforms
     */
    private void showPlatformAllocation(int[] arr, int[] dep, int platforms) {
        // Create array of platforms
        int[] platformEndTime = new int[platforms];
        Arrays.fill(platformEndTime, -1);
        
        // Pair trains with their original indices
        int[][] trains = new int[arr.length][3];
        for (int i = 0; i < arr.length; i++) {
            trains[i][0] = arr[i];
            trains[i][1] = dep[i];
            trains[i][2] = i; // Original index
        }
        
        // Sort by arrival time
        Arrays.sort(trains, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Allocate trains to platforms
        int[] platformAssignment = new int[arr.length];
        
        for (int[] train : trains) {
            int arrival = train[0];
            int departure = train[1];
            int trainIdx = train[2];
            
            // Find first available platform
            for (int p = 0; p < platforms; p++) {
                if (platformEndTime[p] < arrival) {
                    platformEndTime[p] = departure;
                    platformAssignment[trainIdx] = p + 1; // 1-indexed
                    break;
                }
            }
        }
        
        // Display allocation
        System.out.println("Train allocation to platforms:");
        System.out.printf("%-10s %-15s %-15s %-10s\n", 
            "Train", "Arrival", "Departure", "Platform");
        System.out.println("-".repeat(50));
        
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("%-10d %-15d %-15d %-10d\n", 
                i+1, arr[i], dep[i], platformAssignment[i]);
        }
        
        // Visual timeline
        System.out.println("\n--- Platform Timeline ---");
        int startTime = Arrays.stream(arr).min().orElse(0);
        int endTime = Arrays.stream(dep).max().orElse(2400);
        
        for (int p = 1; p <= platforms; p++) {
            System.out.printf("Platform %d: ", p);
            for (int t = startTime; t <= endTime; t += 100) {
                boolean occupied = false;
                for (int i = 0; i < arr.length; i++) {
                    if (platformAssignment[i] == p && t >= arr[i] && t <= dep[i]) {
                        occupied = true;
                        break;
                    }
                }
                System.out.print(occupied ? "█" : " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Explanation of algorithm correctness:
     * 
     * Why sorting works:
     * 1. We need to find maximum number of overlapping intervals
     * 2. Sorting arrivals and departures allows us to process events in order
     * 3. When arrival ≤ departure, a new train arrives before one leaves
     * 4. When arrival > departure, a train leaves before new one arrives
     * 5. Maximum concurrent count gives minimum platforms needed
     * 
     * Example:
     * Arrivals:   [900, 940, 950, 1100, 1500, 1800]
     * Departures: [910, 1200, 1120, 1130, 1900, 2000]
     * Sorted: same order
     * 
     * Timeline:
     * 900: Train 1 arrives → platforms=1, max=1
     * 910: Train 1 departs → platforms=0
     * 940: Train 2 arrives → platforms=1, max=1
     * 950: Train 3 arrives → platforms=2, max=2
     * 1100: Train 4 arrives → platforms=3, max=3
     * 1120: Train 3 departs → platforms=2
     * 1130: Train 4 departs → platforms=1
     * 1200: Train 2 departs → platforms=0
     * 1500: Train 5 arrives → platforms=1
     * 1800: Train 6 arrives → platforms=2, max=3
     * 1900: Train 5 departs → platforms=1
     * 2000: Train 6 departs → platforms=0
     */
    
    /**
     * Test cases and examples
     */
    public static void runTestCases() {
        MinPlatforms solver = new MinPlatforms();
        
        Object[][] testCases = {
            // {n, arr, dep, expected}
            {6, 
             new int[]{900, 940, 950, 1100, 1500, 1800},
             new int[]{910, 1200, 1120, 1130, 1900, 2000},
             3},
            {3,
             new int[]{900, 1000, 1100},
             new int[]{930, 1200, 1130},
             2},
            {3,
             new int[]{900, 1100, 1235},
             new int[]{1000, 1200, 1240},
             1},
            {4,
             new int[]{100, 200, 300, 400},
             new int[]{200, 300, 400, 500},
             1},
            {4,
             new int[]{100, 200, 300, 400},
             new int[]{500, 400, 500, 500},
             2},
            {1,
             new int[]{100},
             new int[]{200},
             1},
            {0,
             new int[]{},
             new int[]{},
             0},
            {6,
             new int[]{900, 940, 950, 1100, 1500, 1800},
             new int[]{910, 1200, 1120, 1130, 1900, 2000},
             3},
            // Edge case: Same arrival and departure times
            {3,
             new int[]{1000, 1000, 1000},
             new int[]{1000, 1000, 1000},
             3},
        };
        
        System.out.println("=== Test Cases for Minimum Platforms ===");
        System.out.printf("%-50s %-15s %-15s %s\n", 
            "Test Case", "Expected", "Got", "Status");
        System.out.println("-".repeat(100));
        
        for (Object[] test : testCases) {
            int n = (int) test[0];
            int[] arr = (int[]) test[1];
            int[] dep = (int[]) test[2];
            int expected = (int) test[3];
            
            int result = solver.minimumPlatform(n, arr, dep);
            boolean passed = (result == expected);
            
            String testCaseStr = String.format("n=%d, arr=%s, dep=%s", 
                n, Arrays.toString(arr), Arrays.toString(dep));
            if (testCaseStr.length() > 50) {
                testCaseStr = testCaseStr.substring(0, 47) + "...";
            }
            
            System.out.printf("%-50s %-15d %-15d %s\n", 
                testCaseStr, expected, result, passed ? "✓" : "✗");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // Number of test cases
        
        while (t-- > 0) {
            int n = sc.nextInt();
            int[] arr = new int[n];
            int[] dep = new int[n];
            
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }
            
            for (int i = 0; i < n; i++) {
                dep[i] = sc.nextInt();
            }
            
            MinPlatforms solver = new MinPlatforms();
            int result = solver.minimumPlatform(n, arr, dep);
            System.out.println(result);
            
            // Uncomment for visualization
            // solver.visualizePlatforms(n, arr, dep);
            
            // Alternative solution
            // int result2 = solver.minimumPlatformSweep(n, arr, dep);
            // System.out.println("Sweep line: " + result2);
        }
        
        sc.close();
        
        // Uncomment to run test cases
        // runTestCases();
    }
}