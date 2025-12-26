// Problem: LeetCode <ID>. <Title>
// Problem: Superior Elements (Coding Ninjas)
// Converted from Python to Java
// Source: https://www.codingninjas.com/studio/problems/superior-elements_6783446

import java.util.ArrayList;
import java.util.List;

public class Superiors {

    public static List<Integer> superiorElements(int[] a) {
        int n = a.length;
        List<Integer> s = new ArrayList<>();
        int maxi = a[n - 1];
        s.add(maxi);
        for (int i = n - 2; i >= 0; i--) {
            if (a[i] > maxi) {
                s.add(a[i]);
                if (a[i] > maxi) maxi = a[i];
            }
        }
        return s;
    }
    public static void main(String[] args) {
        
    }
}

