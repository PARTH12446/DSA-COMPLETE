import java.util.Scanner;

/**
 * Problem: Implement a priority queue using a binary max-heap.
 *
 * Idea:
 * - Store elements in an array H[] where:
 *   - For index i, parent = (i - 1) / 2
 *   - Left child = 2 * i + 1
 *   - Right child = 2 * i + 2
 * - On insert:
 *   - Place element at the end (index s), then "shift up" while it is
 *     greater than its parent to restore max-heap property.
 * - On extractMax:
 *   - The max element is at index 0.
 *   - Replace H[0] with the last element, decrease size,
 *     then "shift down" to restore heap property.
 *
 * Time Complexity:
 * - Insertion: O(log n)
 * - Extract max: O(log n)
 * Space Complexity: O(n) for the heap array.
 */
public class IntroToHeaps {

    private int[] H;
    private int s;

    public IntroToHeaps(int capacity) {
        H = new int[capacity];
        s = -1;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    private void shiftUp(int i) {
        while (i > 0 && H[parent(i)] < H[i]) {
            int temp = H[parent(i)];
            H[parent(i)] = H[i];
            H[i] = temp;
            i = parent(i);
        }
    }

    private void shiftDown(int i) {
        int maxIndex = i;
        int l = leftChild(i);

        if (l <= s && H[l] > H[maxIndex]) {
            maxIndex = l;
        }

        int r = rightChild(i);
        if (r <= s && H[r] > H[maxIndex]) {
            maxIndex = r;
        }

        if (i != maxIndex) {
            int temp = H[i];
            H[i] = H[maxIndex];
            H[maxIndex] = temp;
            shiftDown(maxIndex);
        }
    }

    public void insert(int value) {
        s++;
        H[s] = value;
        shiftUp(s);
    }

    public int extractMax() {
        if (s < 0) {
            throw new IllegalStateException("Heap is empty");
        }
        int res = H[0];
        H[0] = H[s];
        s--;
        if (s >= 0) {
            shiftDown(0);
        }
        return res;
    }

    public void printHeap() {
        for (int i = 0; i <= s; i++) {
            System.out.print(H[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t-- > 0) {
            int n = sc.nextInt();
            IntroToHeaps heap = new IntroToHeaps(10009);
            for (int i = 0; i < n; i++) {
                int val = sc.nextInt();
                heap.insert(val);
            }
            int max = heap.extractMax();
            System.out.println("Node with maximum priority : " + max);
            System.out.print("Priority queue after extracting maximum : ");
            heap.printHeap();
        }
        sc.close();
    }
}
