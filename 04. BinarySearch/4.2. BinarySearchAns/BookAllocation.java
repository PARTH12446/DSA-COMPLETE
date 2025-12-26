/**
 * Problem: Allocate Books (Minimum Maximum Pages)
 * 
 * Problem Description:
 * Given an array 'arr' of integer numbers, where 'arr[i]' represents the number of pages
 * in the 'i-th' book. There are 'm' number of students, and the task is to allocate
 * all the books to the students.
 * 
 * Allocation Rules:
 * 1. Each student gets at least one book.
 * 2. Each book should be allocated to only one student.
 * 3. Books allocation should be in contiguous order.
 * 
 * Goal: Allocate books to 'm' students such that the maximum number of pages
 * assigned to a student is minimized.
 * 
 * Example:
 * Input: arr = [12, 34, 67, 90], m = 2
 * Output: 113
 * Explanation: Allocation [12, 34, 67] and [90] gives max = 113
 * 
 * Approach: Binary Search on Answer + Greedy Validation
 * 
 * Time Complexity: O(N * log(S)) where N = number of books, S = sum of pages
 * Space Complexity: O(1)
 */

import java.util.Arrays;

public class BookAllocation {

    /**
     * Validates if we can allocate books with given maximum pages per student
     * 
     * @param arr - Array of book pages
     * @param maxPages - Maximum pages allowed per student
     * @return Number of students needed with given constraint
     * 
     * Greedy Algorithm:
     * 1. Start with first student
     * 2. Keep giving books to current student until adding next book exceeds maxPages
     * 3. When limit exceeded, start new student with that book
     * 4. Count total students needed
     * 
     * Why greedy works?
     * - We want to minimize number of students for given maxPages
     * - Giving books contiguously to current student as much as possible
     *   minimizes student count
     */
    private static int countStudents(int[] arr, int maxPages) {
        int studentsRequired = 1;  // At least one student needed
        int currentPages = 0;      // Pages allocated to current student
        
        for (int pages : arr) {
            // Check if adding this book exceeds the maximum pages limit
            if (currentPages + pages > maxPages) {
                // Start new student with this book
                studentsRequired++;
                currentPages = pages;
                
                // Early optimization: If we already exceed some limit, we can break early
                // (Though not implemented here for clarity)
            } else {
                // Add book to current student
                currentPages += pages;
            }
        }
        
        return studentsRequired;
    }
    
    /**
     * Enhanced validation with detailed logging for understanding
     */
    private static int countStudentsVerbose(int[] arr, int maxPages, int maxStudentsAllowed) {
        System.out.println("\n  Checking maxPages = " + maxPages + ":");
        int studentsRequired = 1;
        int currentPages = 0;
        
        System.out.print("    Allocation: Student 1: [");
        for (int i = 0; i < arr.length; i++) {
            if (currentPages + arr[i] > maxPages) {
                System.out.print("] (" + currentPages + " pages)");
                studentsRequired++;
                currentPages = arr[i];
                System.out.print("\n    Student " + studentsRequired + ": [" + arr[i]);
                
                // Early exit if we already need more students than allowed
                if (studentsRequired > maxStudentsAllowed) {
                    System.out.println("...]");
                    System.out.println("    Result: Need " + studentsRequired + " students (exceeds " + maxStudentsAllowed + ")");
                    return studentsRequired;
                }
            } else {
                currentPages += arr[i];
                if (i > 0 && currentPages != arr[i]) {
                    System.out.print(", " + arr[i]);
                }
            }
        }
        System.out.println("] (" + currentPages + " pages)");
        System.out.println("    Result: Need " + studentsRequired + " students");
        
        return studentsRequired;
    }
    
    /**
     * Main function to find minimum maximum pages
     * 
     * @param arr - Array of book pages
     * @param n - Number of books (should be arr.length)
     * @param m - Number of students
     * @return Minimum possible maximum pages, or -1 if allocation impossible
     * 
     * Binary Search Explanation:
     * 
     * Search Space:
     * - Lower bound (low) = Maximum book pages
     *   Reason: A student must get at least one complete book
     * - Upper bound (high) = Sum of all pages
     *   Reason: If only one student, they get all books
     * 
     * Monotonic Property:
     * Let f(x) = number of students needed if maximum pages per student = x
     * - If x increases → f(x) decreases or stays same (monotonically decreasing)
     * - If x decreases → f(x) increases or stays same
     * 
     * Binary Search Logic:
     * For candidate mid:
     * - If f(mid) > m: Need more students than available → increase pages (low = mid + 1)
     * - If f(mid) ≤ m: Can allocate with given students → try smaller pages (high = mid - 1)
     */
    public static int findPages(int[] arr, int n, int m) {
        // Edge cases
        if (arr == null || n == 0) {
            return -1;
        }
        
        // If more students than books, allocation is impossible
        if (m > n) {
            return -1;
        }
        
        // If only one student, they get all books
        if (m == 1) {
            int sum = 0;
            for (int pages : arr) sum += pages;
            return sum;
        }
        
        // If students equal books, each gets one book
        if (m == n) {
            int max = 0;
            for (int pages : arr) max = Math.max(max, pages);
            return max;
        }
        
        // Calculate search bounds
        int low = 0;      // Will be maximum book pages
        int high = 0;     // Will be sum of all pages
        
        for (int pages : arr) {
            low = Math.max(low, pages);  // At least the largest book
            high += pages;               // At most all books
        }
        
        System.out.println("Initial bounds:");
        System.out.println("  Low (max book): " + low);
        System.out.println("  High (sum): " + high);
        System.out.println("  Students: " + m);
        
        int result = -1;
        
        // Binary search for minimum maximum pages
        while (low <= high) {
            int mid = low + (high - low) / 2;  // Candidate maximum pages
            
            int studentsNeeded = countStudents(arr, mid);
            System.out.printf("  mid = %d → students needed = %d", mid, studentsNeeded);
            
            if (studentsNeeded > m) {
                System.out.println(" (Too many students, increase pages)");
                // Need more students than available
                // Current maxPages is too small, increase it
                low = mid + 1;
            } else {
                System.out.println(" (Feasible, try smaller pages)");
                // Can allocate with current or fewer students
                result = mid;  // Valid solution
                high = mid - 1;  // Try to minimize further
            }
        }
        
        // At the end, 'low' is the minimum feasible maxPages
        return low;  // Or return result
    }
    
    /**
     * Alternative implementation with clear variable names
     */
    public static int allocateBooks(int[] books, int students) {
        // Edge cases
        if (books == null || books.length == 0 || students <= 0) {
            return -1;
        }
        
        int n = books.length;
        if (students > n) {
            return -1;  // More students than books
        }
        
        // Calculate search bounds
        int maxBook = 0;
        int totalPages = 0;
        
        for (int pages : books) {
            maxBook = Math.max(maxBook, pages);
            totalPages += pages;
        }
        
        // Binary search
        int left = maxBook;
        int right = totalPages;
        int answer = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (isAllocationPossible(books, students, mid)) {
                // Allocation possible with current max pages
                answer = mid;      // Store valid answer
                right = mid - 1;   // Try for smaller max pages
            } else {
                // Allocation not possible, need larger max pages
                left = mid + 1;
            }
        }
        
        return answer;
    }
    
    /**
     * Helper method for alternative implementation
     */
    private static boolean isAllocationPossible(int[] books, int maxStudents, int maxPages) {
        int studentsUsed = 1;
        int pagesForCurrentStudent = 0;
        
        for (int pages : books) {
            if (pagesForCurrentStudent + pages <= maxPages) {
                // Add to current student
                pagesForCurrentStudent += pages;
            } else {
                // Start new student
                studentsUsed++;
                pagesForCurrentStudent = pages;
                
                // Early exit if already exceeded max students
                if (studentsUsed > maxStudents) {
                    return false;
                }
            }
        }
        
        return studentsUsed <= maxStudents;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Book Allocation Problem ===\n");
        
        // Test Case 1: Example from problem statement
        System.out.println("Test Case 1: Standard example");
        int[] books1 = {12, 34, 67, 90};
        int students1 = 2;
        System.out.println("Books: " + Arrays.toString(books1));
        System.out.println("Students: " + students1);
        
        int result1 = findPages(books1, books1.length, students1);
        System.out.println("\nMinimum maximum pages: " + result1);
        System.out.println("Expected: 113\n");
        
        // Show allocation for result
        System.out.println("Optimal allocation for " + result1 + " pages per student:");
        countStudentsVerbose(books1, result1, students1);
        
        // Test Case 2: Equal books
        System.out.println("\n\nTest Case 2: Equal books");
        int[] books2 = {20, 20, 20, 20, 20};
        int students2 = 3;
        System.out.println("Books: " + Arrays.toString(books2));
        System.out.println("Students: " + students2);
        
        int result2 = findPages(books2, books2.length, students2);
        System.out.println("\nMinimum maximum pages: " + result2);
        System.out.println("Expected: 40\n");
        
        // Test Case 3: Many students
        System.out.println("\n\nTest Case 3: Many students");
        int[] books3 = {10, 20, 30, 40};
        int students3 = 4;
        System.out.println("Books: " + Arrays.toString(books3));
        System.out.println("Students: " + students3);
        
        int result3 = findPages(books3, books3.length, students3);
        System.out.println("\nMinimum maximum pages: " + result3);
        System.out.println("Expected: 40\n");
        
        // Test Case 4: Large book at end
        System.out.println("\n\nTest Case 4: Large book at end");
        int[] books4 = {5, 5, 5, 100};
        int students4 = 2;
        System.out.println("Books: " + Arrays.toString(books4));
        System.out.println("Students: " + students4);
        
        int result4 = findPages(books4, books4.length, students4);
        System.out.println("\nMinimum maximum pages: " + result4);
        System.out.println("Expected: 100\n");
        
        // Test Case 5: Impossible case
        System.out.println("\n\nTest Case 5: More students than books");
        int[] books5 = {10, 20, 30};
        int students5 = 5;
        System.out.println("Books: " + Arrays.toString(books5));
        System.out.println("Students: " + students5);
        
        int result5 = findPages(books5, books5.length, students5);
        System.out.println("\nMinimum maximum pages: " + result5);
        System.out.println("Expected: -1\n");
        
        // Test Case 6: Single student
        System.out.println("\n\nTest Case 6: Single student");
        int[] books6 = {15, 17, 20};
        int students6 = 1;
        System.out.println("Books: " + Arrays.toString(books6));
        System.out.println("Students: " + students6);
        
        int result6 = findPages(books6, books6.length, students6);
        System.out.println("\nMinimum maximum pages: " + result6);
        System.out.println("Expected: 52\n");
        
        // Test Case 7: Complex case
        System.out.println("\n\nTest Case 7: Complex allocation");
        int[] books7 = {25, 46, 28, 49, 24};
        int students7 = 4;
        System.out.println("Books: " + Arrays.toString(books7));
        System.out.println("Students: " + students7);
        
        int result7 = findPages(books7, books7.length, students7);
        System.out.println("\nMinimum maximum pages: " + result7);
        System.out.println("Expected: 71 (or similar)\n");
        
        // Performance comparison
        System.out.println("=== Performance Test ===");
        int[] largeBooks = new int[10000];
        for (int i = 0; i < largeBooks.length; i++) {
            largeBooks[i] = (int)(Math.random() * 100) + 1;
        }
        int largeStudents = 100;
        
        long startTime = System.currentTimeMillis();
        int largeResult = findPages(largeBooks, largeBooks.length, largeStudents);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Large test: " + largeBooks.length + " books, " + largeStudents + " students");
        System.out.println("Result: " + largeResult);
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
    }
}

/**
 * ALGORITHM WALKTHROUGH:
 * 
 * For arr = [12, 34, 67, 90], m = 2
 * 
 * Step 1: Calculate bounds
 *   low = max(12, 34, 67, 90) = 90
 *   high = 12 + 34 + 67 + 90 = 203
 * 
 * Step 2: Binary search iterations
 * 
 * Iteration 1: mid = (90 + 203) / 2 = 146
 *   Check: Can allocate with max 146 pages?
 *     Student 1: 12 + 34 + 67 = 113 ≤ 146
 *     Student 2: 90 ≤ 146
 *     Students needed = 2 ≤ m=2 → SUCCESS
 *     Update: high = 146 - 1 = 145, try smaller
 * 
 * Iteration 2: mid = (90 + 145) / 2 = 117
 *   Check: Can allocate with max 117 pages?
 *     Student 1: 12 + 34 + 67 = 113 ≤ 117
 *     Student 2: 90 ≤ 117
 *     Students needed = 2 ≤ 2 → SUCCESS
 *     Update: high = 117 - 1 = 116
 * 
 * Iteration 3: mid = (90 + 116) / 2 = 103
 *   Check: Can allocate with max 103 pages?
 *     Student 1: 12 + 34 + 67 = 113 > 103 → split
 *     Student 1: 12 + 34 = 46 ≤ 103
 *     Student 2: 67 ≤ 103
 *     Student 3: 90 ≤ 103
 *     Students needed = 3 > 2 → FAIL
 *     Update: low = 103 + 1 = 104
 * 
 * Continue until low > high...
 * Final answer: low = 113
 */

/**
 * WHY THIS ALGORITHM WORKS:
 * 
 * 1. Feasibility Function:
 *    The function f(x) = "can allocate with max x pages" is monotonic
 *    If f(x) is true, then f(y) is true for all y ≥ x
 *    If f(x) is false, then f(y) is false for all y ≤ x
 * 
 * 2. Binary Search Validity:
 *    Because of monotonicity, we can binary search for the minimum x
 *    where f(x) is true
 * 
 * 3. Greedy Allocation:
 *    Given a maximum x, the greedy algorithm minimizes student count
 *    If greedy needs ≤ m students, allocation is possible
 *    If greedy needs > m students, no allocation exists for that x
 */

/**
 * PRACTICAL APPLICATIONS:
 * 
 * 1. Load Balancing: Distribute tasks among workers minimizing max load
 * 2. Disk Storage: Allocate files to disks minimizing max disk usage
 * 3. Network Routing: Distribute data packets minimizing max link utilization
 * 4. Exam Scheduling: Assign questions to students minimizing max difficulty
 */