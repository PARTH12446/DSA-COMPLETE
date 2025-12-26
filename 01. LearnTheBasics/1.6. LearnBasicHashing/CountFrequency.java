import java.util.HashMap;
import java.util.Map;

// Problem: Count frequency of each element in an array
// Idea: Use a HashMap to map value -> count
public class CountFrequency {

    public static Map<Integer, Integer> countFreq(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int v : arr) {
            map.put(v, map.getOrDefault(v, 0) + 1);
        }
        return map;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 2, 3, 3, 3};
        Map<Integer, Integer> freq = countFreq(arr);
        System.out.println("Frequencies: " + freq);
    }
}
