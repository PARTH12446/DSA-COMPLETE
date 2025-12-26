public class RabinCarp {

    // Repeated String Match using Rabin-Karp style hashing
    public static int repeatedStringMatch(String a, String b) {
        final int MOD = 1_000_000_007;
        final int PRIME = 101;

        int lenB = b.length();
        int lenA = a.length();

        int minRepeats = (lenB + lenA - 1) / lenA;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < minRepeats; i++) sb.append(a);
        String repeatedA = sb.toString();

        if (!repeatedA.contains(b)) {
            repeatedA += a;
        }

        long hashB = hashFn(b, lenB, PRIME, MOD);
        long hashWindow = hashFn(repeatedA.substring(0, lenB), lenB, PRIME, MOD);
        long highestPow = 1;
        for (int i = 0; i < lenB - 1; i++) {
            highestPow = (highestPow * PRIME) % MOD;
        }

        for (int i = 0; i <= repeatedA.length() - lenB; i++) {
            if (hashWindow == hashB && repeatedA.substring(i, i + lenB).equals(b)) {
                int baseRepeats = minRepeats;
                if (i + lenB > lenA * minRepeats) baseRepeats++;
                return baseRepeats;
            }
            if (i + lenB < repeatedA.length()) {
                hashWindow = updateHash(hashWindow, repeatedA.charAt(i), repeatedA.charAt(i + lenB),
                        PRIME, MOD, highestPow);
            }
        }
        return -1;
    }

    private static long hashFn(String s, int length, int PRIME, int MOD) {
        long hash = 0;
        for (int i = 0; i < length; i++) {
            hash = (hash * PRIME + s.charAt(i)) % MOD;
        }
        return hash;
    }

    private static long updateHash(long hash, char oldChar, char newChar, int PRIME, int MOD, long highestPow) {
        hash = (hash - oldChar * highestPow) % MOD;
        if (hash < 0) hash += MOD;
        hash = (hash * PRIME + newChar) % MOD;
        return hash;
    }

    public static void main(String[] args) {
        String a = "abcd";
        String b = "cdabcdab";
        System.out.println("Repeated string match: " + repeatedStringMatch(a, b));
    }
}
