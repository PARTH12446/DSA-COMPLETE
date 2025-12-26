// Problem: Insertion Sort (iterative)
// Idea: Build the sorted prefix by inserting each element into its correct position
// Time: O(n^2) worst/average, O(n) best (already sorted); Space: O(1); Stable: Yes
public class InsertionSort {

    public static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int j = i;
            while (j > 0 && arr[j - 1] > arr[j]) {
                int tmp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = tmp;
                j--;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {9, 5, 1, 4, 3};
        insertionSort(arr);
        System.out.print("Insertion sorted: ");
        for (int v : arr) System.out.print(v + " ");
        System.out.println();
    }
}
