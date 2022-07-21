package SamsungAlgorithm22.BitwiseOperation;

import java.util.*;

public class 이진수표현 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        for(int testCase = 1; testCase <= T; testCase++) {
            int n = sc.nextInt();
            int m = sc.nextInt();

 			bitMasking(n, m, testCase);
        }
    }

    static void bitMasking(int n, int m, int testCase) {
        int standard = (1 << n) - 1;
        if((standard & m) != standard) {
            System.out.println("#"+testCase+" OFF");
            return;
        }
        System.out.println("#"+testCase+" ON");
    }

}
