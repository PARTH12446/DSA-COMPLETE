import java.util.HashMap;
import java.util.Map;

// Problem: Find elements with highest and lowest frequency
// Idea: Use HashMap to count, then scan to pick max/min frequency (tie-break by smaller value)
public class HighLowFreq {

    public static int[] highLowFreq(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int v : arr) map.put(v, map.getOrDefault(v, 0) + 1);
        int maxFreq = -1, minFreq = Integer.MAX_VALUE;
        int maxVal = -1, minVal = -1;
        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            int val = e.getKey(), freq = e.getValue();
            if (freq > maxFreq || (freq == maxFreq && val < maxVal)) {
                maxFreq = freq;
                maxVal = val;
            }
            if (freq < minFreq || (freq == minFreq && val < minVal)) {
                minFreq = freq;
                minVal = val;
            }
        }
        return new int[]{maxVal, minVal};
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 2, 3, 3, 3};
        int[] res = highLowFreq(arr);
        System.out.println("Highest freq value = " + res[0] + ", Lowest freq value = " + res[1]);
    }
}
