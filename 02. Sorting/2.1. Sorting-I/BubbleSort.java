// Problem: Bubble Sort (iterative)
// Idea: Repeatedly swap adjacent out-of-order elements; largest elements "bubble" to the end
// Time: O(n^2) in worst/average, O(n) in best case with optimization; Space: O(1); Stable: Yes
public class BubbleSort {

    public static void bubbleSort(int[] arr, int n) {
        for (int x = 0; x < n; x++) {
            for (int i = 0; i < n - x - 1; i++) {
                int j = i + 1;
                if (arr[i] > arr[j]) {
                    int tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {5, 1, 4, 2, 8};
        bubbleSort(arr, arr.length);
        System.out.print("Bubble sorted: ");
        for (int v : arr) System.out.print(v + " ");
        System.out.println();
    }
}
