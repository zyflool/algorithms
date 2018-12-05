import java.util.*;

public class Array {
    public static void main (String[] args) {
        Scanner in = new Scanner(System.in);
        int x = in.nextInt();
        String[] strings = new String[x];
        for (int i = 0; i < x; i++) {
            strings[i] = in.next();
        }
        System.out.print("[");
        for (int i=0;i<x-1;i++)
            System.out.print(strings[i]+",");
        System.out.print(strings[x-1]+"]");
        System.out.println();
    }
}

