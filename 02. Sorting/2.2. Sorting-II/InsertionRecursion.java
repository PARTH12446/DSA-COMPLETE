// Problem: Insertion Sort (recursive)
// Idea: Recursively sort prefix [0..i-1], then insert element at i into its correct position
// Time: O(n^2); Space: O(n) recursion stack; Stable: Yes
public class InsertionRecursion {

    public static void insertionSort(int[] arr, int i) {
        if (i == arr.length) return;
        int j = i;
        while (j > 0 && arr[j - 1] > arr[j]) {
            int tmp = arr[j];
            arr[j] = arr[j - 1];
            arr[j - 1] = tmp;
            j--;
        }
        insertionSort(arr, i + 1);
    }

    public static void main(String[] args) {
        int[] arr = {9, 5, 1, 4, 3};
        insertionSort(arr, 0);
        System.out.print("Recursive insertion sorted: ");
        for (int v : arr) System.out.print(v + " ");
        System.out.println();
    }
}
