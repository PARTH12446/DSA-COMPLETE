// Problem: Selection Sort
// Idea: Repeatedly select the minimum element from the unsorted part and put it at the beginning
// Time: O(n^2) in all cases; Space: O(1); Stable: No (because of swaps)
public class SelectionSort {

    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int minimum = arr[i];
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < minimum) {
                    minimum = arr[j];
                    minIndex = j;
                }
            }
            int tmp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = tmp;
        }
    }

    public static void main(String[] args) {
        int[] arr = {64, 25, 12, 22, 11};
        selectionSort(arr);
        System.out.print("Selection sorted: ");
        for (int v : arr) System.out.print(v + " ");
        System.out.println();
    }
}
