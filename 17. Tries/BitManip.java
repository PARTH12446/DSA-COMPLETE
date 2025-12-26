public class BitManip {

    // Bit manipulation notes for tries and XOR problems:
    // 1. Bits of a 32-bit number are indexed 0..31 (usually from right to left).
    // 2. XOR: 1 if bits differ, 0 if bits are same.
    // 3. AND: 1 only if both bits are 1.
    // 4. Right shift: divide by 2; left shift: multiply by 2.
    // 5. Turn on n-th bit: x | (1 << n).

    public static void main(String[] args) {
        int x = 5;          // 0101
        int y = 3;          // 0011
        System.out.println("x ^ y = " + (x ^ y));
        System.out.println("x & y = " + (x & y));
        System.out.println("x << 1 = " + (x << 1));
        System.out.println("x >> 1 = " + (x >> 1));
    }
}
