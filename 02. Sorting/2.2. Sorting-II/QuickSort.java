// Problem: Quick Sort
// Idea: Choose a pivot, partition array into < pivot and > pivot, then sort subarrays recursively
// Time: O(n log n) average, O(n^2) worst; Space: O(log n) recursion stack; Stable: No
public class QuickSort {

    public static void quickSort(int[] arr, int start, int end) {
        if (start >= end) return;
        int p = partition(arr, start, end);
        quickSort(arr, start, p - 1);
        quickSort(arr, p + 1, end);
    }

    private static int partition(int[] arr, int s, int e) {
        int count = 0;
        int pivot = arr[s];
        for (int i = s + 1; i <= e; i++) {
            if (arr[i] <= pivot) count++;
        }
        int pivotIndex = s + count;
        int tmp = arr[s];
        arr[s] = arr[pivotIndex];
        arr[pivotIndex] = tmp;

        int i = s, j = e;
        while (i < pivotIndex && j > pivotIndex) {
            while (arr[i] <= pivot) i++;
            while (arr[j] > pivot) j--;
            if (i < pivotIndex && j > pivotIndex) {
                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
                i++;
                j--;
            }
        }
        return pivotIndex;
    }

    public static void main(String[] args) {
        int[] arr = {10, 7, 8, 9, 1, 5};
        quickSort(arr, 0, arr.length - 1);
        System.out.print("Quick sorted: ");
        for (int v : arr) System.out.print(v + " ");
        System.out.println();
    }
}
