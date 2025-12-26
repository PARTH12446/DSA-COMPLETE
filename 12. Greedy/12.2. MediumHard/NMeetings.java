import java.util.Arrays;
import java.util.Scanner;

/**
 * N Meetings in One Room (GFG)
 * 
 * Problem Statement:
 * There is one meeting room in a firm. There are N meetings in the form of
 * (start[i], end[i]) where start[i] is start time and end[i] is finish time.
 * What is the maximum number of meetings that can be accommodated in the room?
 * 
 * Example:
 * Input:
 *   N = 6
 *   start[] = {1, 3, 0, 5, 8, 5}
 *   end[]   = {2, 4, 6, 7, 9, 9}
 * Output: 4
 * Explanation: Maximum four meetings can be held:
 *   Meeting 1: (1, 2)
 *   Meeting 2: (3, 4) 
 *   Meeting 3: (5, 7)
 *   Meeting 4: (8, 9)
 * 
 * Greedy Approach (Earliest Finishing Time):
 * 1. Sort meetings by finishing time (ascending)
 * 2. Always select the meeting that finishes earliest
 * 3. Skip meetings that overlap with selected meetings
 * 4. Count how many meetings can be scheduled
 * 
 * Time Complexity: O(n log n) for sorting + O(n) for selection = O(n log n)
 * Space Complexity: O(n) for Meeting objects (or O(1) with in-place sorting)
 */
public class NMeetings {

    private static class Meeting {
        int start;
        int end;
        int index; // To track original position
        
        Meeting(int start, int end, int index) {
            this.start = start;
            this.end = end;
            this.index = index;
        }
        
        @Override
        public String toString() {
            return "[" + start + "," + end + "]";
        }
    }

    /**
     * Greedy solution using earliest finishing time
     * 
     * @param n Number of meetings
     * @param start Array of start times
     * @param end Array of end times
     * @return Maximum number of non-overlapping meetings
     */
    public int maximumMeetings(int n, int[] start, int[] end) {
        // Edge cases
        if (n == 0) return 0;
        if (n == 1) return 1;
        
        // Create Meeting objects with original indices
        Meeting[] meetings = new Meeting[n];
        for (int i = 0; i < n; i++) {
            meetings[i] = new Meeting(start[i], end[i], i + 1);
        }
        
        // Step 1: Sort meetings by finishing time (ascending)
        // If finishing times are equal, sort by start time (optional)
        Arrays.sort(meetings, (a, b) -> {
            if (a.end != b.end) {
                return Integer.compare(a.end, b.end);
            }
            // Optional: if same end time, choose earlier start time
            return Integer.compare(a.start, b.start);
        });
        
        // Step 2: Greedy selection
        int count = 0;
        int lastEndTime = -1; // Time when last selected meeting ended
        
        for (Meeting meeting : meetings) {
            // A meeting can be scheduled if it starts after last meeting ends
            // Note: if meeting starts exactly when last ends, it's allowed
            if (meeting.start > lastEndTime) {
                count++;
                lastEndTime = meeting.end;
            }
            // If meeting.start == lastEndTime, it's also acceptable
            // (meeting can start right when previous ends)
        }
        
        return count;
    }
    
    /**
     * Alternative: Returns the actual meetings selected
     */
    public int[] maximumMeetingsWithSelection(int n, int[] start, int[] end) {
        if (n == 0) return new int[0];
        
        Meeting[] meetings = new Meeting[n];
        for (int i = 0; i < n; i++) {
            meetings[i] = new Meeting(start[i], end[i], i + 1);
        }
        
        Arrays.sort(meetings, (a, b) -> Integer.compare(a.end, b.end));
        
        int count = 0;
        int lastEndTime = -1;
        int[] selectedIndices = new int[n]; // Store selected meeting indices
        int selectedCount = 0;
        
        for (Meeting meeting : meetings) {
            if (meeting.start > lastEndTime) {
                selectedIndices[selectedCount++] = meeting.index;
                lastEndTime = meeting.end;
                count++;
            }
        }
        
        // Return only the selected indices
        return Arrays.copyOf(selectedIndices, selectedCount);
    }
    
    /**
     * Visualization helper: Show step-by-step selection process
     */
    public void visualizeMeetings(int n, int[] start, int[] end) {
        System.out.println("\n=== Visualizing Maximum Meetings ===");
        System.out.println("Number of meetings: " + n);
        
        // Display original meetings
        System.out.println("\nAll meetings:");
        System.out.printf("%-10s %-15s %-15s %s\n", 
            "Meeting", "Start", "End", "Duration");
        System.out.println("-".repeat(55));
        for (int i = 0; i < n; i++) {
            System.out.printf("%-10d %-15d %-15d %d\n", 
                i + 1, start[i], end[i], end[i] - start[i]);
        }
        
        // Create and sort meetings
        Meeting[] meetings = new Meeting[n];
        for (int i = 0; i < n; i++) {
            meetings[i] = new Meeting(start[i], end[i], i + 1);
        }
        
        Arrays.sort(meetings, (a, b) -> {
            if (a.end != b.end) return Integer.compare(a.end, b.end);
            return Integer.compare(a.start, b.start);
        });
        
        System.out.println("\nMeetings sorted by end time:");
        System.out.printf("%-10s %-15s %-15s %s\n", 
            "Meeting", "Start", "End", "Duration");
        System.out.println("-".repeat(55));
        for (Meeting m : meetings) {
            System.out.printf("%-10d %-15d %-15d %d\n", 
                m.index, m.start, m.end, m.end - m.start);
        }
        
        // Greedy selection process
        System.out.println("\n--- Greedy Selection Process ---");
        System.out.println("Rule: Always select meeting that finishes earliest");
        System.out.println("      Skip meetings that overlap with selected ones\n");
        
        int count = 0;
        int lastEndTime = -1;
        int step = 1;
        
        System.out.printf("%-10s %-15s %-20s %-20s %s\n", 
            "Step", "Consider", "Condition", "Decision", "Selected So Far");
        System.out.println("-".repeat(90));
        
        for (Meeting meeting : meetings) {
            System.out.printf("%-10d Meeting %d ", step++, meeting.index);
            System.out.printf("(%d,%d)".padRight(15), meeting.start, meeting.end);
            
            String condition = "start(" + meeting.start + ") > lastEnd(" + lastEndTime + ")?";
            System.out.printf("%-20s", condition);
            
            if (meeting.start > lastEndTime) {
                System.out.printf("%-20s", "✓ SELECT");
                count++;
                lastEndTime = meeting.end;
            } else {
                System.out.printf("%-20s", "✗ SKIP (overlap)");
            }
            
            System.out.printf("Count: %d, LastEnd: %d\n", count, lastEndTime);
        }
        
        System.out.println("\n=== Result ===");
        System.out.println("Maximum meetings that can be scheduled: " + count);
        
        // Show actual schedule
        System.out.println("\n--- Optimal Schedule ---");
        showOptimalSchedule(meetings);
        
        // Compare with other greedy strategies
        System.out.println("\n--- Comparison with Other Greedy Strategies ---");
        System.out.println("Earliest finishing time: " + count + " meetings");
        System.out.println("Shortest duration: " + 
                          maximumMeetingsByDuration(n, start, end) + " meetings");
        System.out.println("Earliest start time: " + 
                          maximumMeetingsByStartTime(n, start, end) + " meetings");
    }
    
    /**
     * Show the actual schedule of selected meetings
     */
    private void showOptimalSchedule(Meeting[] meetings) {
        int lastEndTime = -1;
        int meetingCount = 0;
        
        System.out.println("Timeline (0 to max end time):");
        int maxEnd = 0;
        for (Meeting m : meetings) {
            maxEnd = Math.max(maxEnd, m.end);
        }
        
        for (Meeting meeting : meetings) {
            if (meeting.start > lastEndTime) {
                meetingCount++;
                lastEndTime = meeting.end;
                
                // Print timeline
                System.out.printf("Meeting %d: ", meeting.index);
                for (int t = 0; t <= maxEnd; t++) {
                    if (t >= meeting.start && t <= meeting.end) {
                        System.out.print("█");
                    } else {
                        System.out.print(" ");
                    }
                }
                System.out.printf(" [%d-%d]\n", meeting.start, meeting.end);
            }
        }
        
        System.out.println("Total meetings scheduled: " + meetingCount);
    }
    
    /**
     * Greedy by shortest duration (for comparison - not optimal!)
     */
    private int maximumMeetingsByDuration(int n, int[] start, int[] end) {
        Meeting[] meetings = new Meeting[n];
        for (int i = 0; i < n; i++) {
            meetings[i] = new Meeting(start[i], end[i], i + 1);
        }
        
        Arrays.sort(meetings, (a, b) -> {
            int durA = a.end - a.start;
            int durB = b.end - b.start;
            if (durA != durB) return Integer.compare(durA, durB);
            return Integer.compare(a.end, b.end);
        });
        
        int count = 0;
        int lastEnd = -1;
        for (Meeting m : meetings) {
            if (m.start > lastEnd) {
                count++;
                lastEnd = m.end;
            }
        }
        return count;
    }
    
    /**
     * Greedy by earliest start time (for comparison - not optimal!)
     */
    private int maximumMeetingsByStartTime(int n, int[] start, int[] end) {
        Meeting[] meetings = new Meeting[n];
        for (int i = 0; i < n; i++) {
            meetings[i] = new Meeting(start[i], end[i], i + 1);
        }
        
        Arrays.sort(meetings, (a, b) -> Integer.compare(a.start, b.end));
        
        int count = 0;
        int lastEnd = -1;
        for (Meeting m : meetings) {
            if (m.start > lastEnd) {
                count++;
                lastEnd = m.end;
            }
        }
        return count;
    }
    
    /**
     * Proof of optimality for earliest finishing time:
     * 
     * Exchange Argument:
     * 1. Suppose we have optimal solution O and greedy solution G
     * 2. Let k be first position where they differ
     * 3. Greedy chose meeting m (earliest finish), optimal chose meeting m'
     * 4. Since m finishes earlier than m', we can replace m' with m in optimal
     * 5. This doesn't affect feasibility and maintains optimality
     * 6. Therefore greedy is optimal
     * 
     * Counterexample for other strategies:
     * Meetings: [0,10], [2,3], [4,5], [6,7], [8,9]
     * - Earliest finish: selects [2,3], [4,5], [6,7], [8,9] = 4 meetings ✓
     * - Shortest duration: same in this case
     * - Earliest start: selects [0,10] = 1 meeting ✗
     */
    
    /**
     * Test cases and examples
     */
    public static void runTestCases() {
        NMeetings solver = new NMeetings();
        
        Object[][] testCases = {
            // {n, start, end, expected}
            {6,
             new int[]{1, 3, 0, 5, 8, 5},
             new int[]{2, 4, 6, 7, 9, 9},
             4},
            {3,
             new int[]{10, 12, 20},
             new int[]{20, 25, 30},
             2},
            {1,
             new int[]{1},
             new int[]{2},
             1},
            {0,
             new int[]{},
             new int[]{},
             0},
            {4,
             new int[]{1, 2, 3, 4},
             new int[]{2, 3, 4, 5},
             4},
            {4,
             new int[]{1, 2, 3, 4},
             new int[]{5, 5, 5, 5},
             1},
            {5,
             new int[]{0, 1, 3, 5, 8},
             new int[]{6, 2, 4, 7, 9},
             4},
            {3,
             new int[]{1, 1, 1},
             new int[]{2, 3, 4},
             1},
            {3,
             new int[]{1, 2, 3},
             new int[]{4, 3, 4},
             2},
            // Example from GFG
            {8,
             new int[]{75250, 50074, 43659, 8931, 11273, 27545, 50879, 77924},
             new int[]{112960, 114515, 81825, 93424, 54316, 35533, 73383, 160252},
             3},
        };
        
        System.out.println("=== Test Cases for Maximum Meetings ===");
        System.out.printf("%-60s %-15s %-15s %s\n", 
            "Test Case", "Expected", "Got", "Status");
        System.out.println("-".repeat(100));
        
        for (Object[] test : testCases) {
            int n = (int) test[0];
            int[] start = (int[]) test[1];
            int[] end = (int[]) test[2];
            int expected = (int) test[3];
            
            int result = solver.maximumMeetings(n, start, end);
            boolean passed = (result == expected);
            
            String testCaseStr = String.format("n=%d, start=%s, end=%s", 
                n, Arrays.toString(start), Arrays.toString(end));
            if (testCaseStr.length() > 60) {
                testCaseStr = testCaseStr.substring(0, 57) + "...";
            }
            
            System.out.printf("%-60s %-15d %-15d %s\n", 
                testCaseStr, expected, result, passed ? "✓" : "✗");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // Number of test cases
        
        while (t-- > 0) {
            int n = sc.nextInt();
            int[] start = new int[n];
            int[] end = new int[n];
            
            for (int i = 0; i < n; i++) {
                start[i] = sc.nextInt();
            }
            
            for (int i = 0; i < n; i++) {
                end[i] = sc.nextInt();
            }
            
            NMeetings solver = new NMeetings();
            int result = solver.maximumMeetings(n, start, end);
            System.out.println(result);
            
            // Uncomment for visualization
            // solver.visualizeMeetings(n, start, end);
            
            // Get actual selected meetings
            // int[] selected = solver.maximumMeetingsWithSelection(n, start, end);
            // System.out.println("Selected meetings: " + Arrays.toString(selected));
        }
        
        sc.close();
        
        // Uncomment to run test cases
        // runTestCases();
    }
}