// Problem: Reverse an array in-place using recursion
// Idea: Swap elements at l and r, then recurse on inner subarray
public class ReverseArrayRecursion {

    public static void reverse(int[] arr, int l, int r) {
        if (l >= r) return;
        int tmp = arr[l];
        arr[l] = arr[r];
        arr[r] = tmp;
        reverse(arr, l + 1, r - 1);
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        reverse(arr, 0, arr.length - 1);
        System.out.print("Reversed array: ");
        for (int v : arr) System.out.print(v + " ");
        System.out.println();
    }
}
