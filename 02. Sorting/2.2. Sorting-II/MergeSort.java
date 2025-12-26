// Problem: Merge Sort
// Idea: Divide array into halves, sort each half, then merge sorted halves
// Time: O(n log n) in all cases; Space: O(n) for temporary arrays; Stable: Yes
public class MergeSort {

    public static void mergeSort(int[] arr, int l, int r) {
        if (l < r) {
            int mid = (l + r) / 2;
            mergeSort(arr, l, mid);
            mergeSort(arr, mid + 1, r);
            merge(arr, l, mid, r);
        }
    }

    private static void merge(int[] arr, int l, int mid, int r) {
        int len1 = mid - l + 1;
        int len2 = r - mid;
        int[] first = new int[len1];
        int[] second = new int[len2];
        for (int i = 0; i < len1; i++) first[i] = arr[l + i];
        for (int j = 0; j < len2; j++) second[j] = arr[mid + 1 + j];
        int i = 0, j = 0, k = l;
        while (i < len1 && j < len2) {
            if (first[i] < second[j]) {
                arr[k++] = first[i++];
            } else {
                arr[k++] = second[j++];
            }
        }
        while (i < len1) arr[k++] = first[i++];
        while (j < len2) arr[k++] = second[j++];
    }

    public static void main(String[] args) {
        int[] arr = {38, 27, 43, 3, 9, 82, 10};
        mergeSort(arr, 0, arr.length - 1);
        System.out.print("Merge sorted: ");
        for (int v : arr) System.out.print(v + " ");
        System.out.println();
    }
}
