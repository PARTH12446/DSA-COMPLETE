public class SetRightUnset {

    // sets the rightmost unset bit and returns result
    public static int setRightMostUnsetBit(int n) {
        // if all bits up to msb are 1, just return n (or n+1 depending on original problem)
        int mask = 1;
        while ((n & mask) != 0) {
            mask <<= 1;
        }
        return n | mask;
    }
    public static void main(String[] args) {
        
    }
}
