public class SetUnsetKth {

    public static int setBit(int n, int k) {
        return n | (1 << k);
    }

    public static int unsetBit(int n, int k) {
        return n & ~(1 << k);
    }
    public static void main(String[] args) {
        
    }
}
