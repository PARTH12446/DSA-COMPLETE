// Problem: Check if a number is an Armstrong number
// Idea: Sum of each digit raised to the number of digits equals the original number
public class CheckArmstrong {

    public static boolean isArmstrong(int n) {
        int temp = n;
        int digits = CountDigits.countDigits(n);
        int sum = 0;
        while (temp != 0) {
            int d = temp % 10;
            sum += Math.pow(d, digits);
            temp /= 10;
        }
        return sum == n;
    }

    public static void main(String[] args) {
        int n = 153;
        boolean ans = isArmstrong(n);
        System.out.println(n + " is Armstrong? " + ans);
    }
}
