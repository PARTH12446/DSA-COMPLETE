// Problem: LeetCode <ID>. <Title>
// Problem: Longest Subarray with Sum K (can have negatives) (Coding Ninjas)
// Converted from Python to Java
// Source: https://www.codingninjas.com/studio/problems/longest-subarray-with-sum-k_5713505

// Statement:
// Given an integer array a (can contain positive, negative and zero values) and
// an integer k, find the length of the longest subarray whose elements sum to k.

// Approach (prefix sum + HashMap):
// - Maintain a running prefix sum "sum" while iterating from left to right.
// - If sum == k at index i, then subarray [0..i] has sum k (length i+1).
// - For current sum, we check if there exists an index j < i such that
//       prefixSum[j] == sum - k.
//   Then subarray (j+1..i) has sum k. We use a HashMap to remember the first
//   occurrence of each prefix sum to maximize subarray length.

import java.util.HashMap;
import java.util.Map;

public class LongestSubArrNeg {

    // Returns the length of the longest subarray with sum exactly k.
    public static int getLongestSubarray(int[] a, int k) {
        int n = a.length;

        // Map to store: prefixSum -> first index where this prefix sum occurred.
        Map<Integer, Integer> preSumMap = new HashMap<>();

        int sum = 0;      // running prefix sum
        int maxLen = 0;   // answer: maximum length found so far

        for (int i = 0; i < n; i++) {
            sum += a[i];

            // Case 1: subarray starting from index 0 to i has sum k
            if (sum == k) {
                maxLen = Math.max(maxLen, i + 1);
            }

            // Case 2: there exists some previous index j where prefixSum[j] == sum - k
            // Then subarray (j+1..i) has sum k.
            int rem = sum - k;
            if (preSumMap.containsKey(rem)) {
                int length = i - preSumMap.get(rem);
                maxLen = Math.max(maxLen, length);
            }

            // Store the first occurrence of this prefix sum.
            if (!preSumMap.containsKey(sum)) {
                preSumMap.put(sum, i);
            }
        }

        return maxLen;
    }

    public static void main(String[] args) {
        int[] a = {1, -1, 5, -2, 3};
        int k = 3;

        int len = getLongestSubarray(a, k);
        System.out.println("Length of longest subarray with sum " + k + " = " + len);
        // For the above example, one such longest subarray is [1, -1, 5, -2] with length 4.
    }
}

