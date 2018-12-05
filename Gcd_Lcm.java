import java.util.*;

/*求最大公约数和最小公倍数*/
public class Gcd_Lcm {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("请输入第一个整数：");
        int num1 = in.nextInt();

        System.out.print("请输入第二个整数：");
        int num2 = in.nextInt();

        System.out.println(Gcd(num1, num2));
        System.out.println(Lcm(num1, num2));
    }

    //求最大公约数
    public static int Gcd(int m, int n) {
        if (m < n) {
            int temp = m;
            m = n;
            n = temp;
        }
        if (m % n == 0) {
            return n;
        } else {
            return Gcd(n, m%n);
        }
    }

    // 求最小公倍数
    public static int Lcm(int m, int n) {
        return m * n / Gcd(m, n);
    }
}
