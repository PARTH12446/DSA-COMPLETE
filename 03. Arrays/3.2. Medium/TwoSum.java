// Problem: LeetCode <ID>. <Title>
// Problem: Reading (Two Sum check, Coding Ninjas)
// Converted from Python to Java
// Source: https://www.codingninjas.com/studio/problems/reading_6845742

import java.util.HashSet;
import java.util.Set;

public class TwoSum {

    // Returns "YES" if any pair sums to target, else "NO".
    public static String read(int n, int[] book, int target) {
        Set<Integer> seen = new HashSet<>();
        for (int num : book) {
            int rem = target - num;
            if (seen.contains(rem)) {
                return "YES";
            }
            seen.add(num);
        }
        return "NO";
    }
    public static void main(String[] args) {
        
    }
}

