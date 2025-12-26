// Problem: Bubble Sort (recursive)
// Idea: Each recursive call bubbles the largest element of the remaining part to the end
// Time: O(n^2); Space: O(n) recursion stack; Stable: Yes
public class BubbleRecursive {

    public static void bubbleSort(int[] arr, int n) {
        if (n == 1) return;
        for (int i = 0; i < n - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                int tmp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = tmp;
            }
        }
        bubbleSort(arr, n - 1);
    }

    public static void main(String[] args) {
        int[] arr = {5, 1, 4, 2, 8};
        bubbleSort(arr, arr.length);
        System.out.print("Recursive bubble sorted: ");
        for (int v : arr) System.out.print(v + " ");
        System.out.println();
    }
}
