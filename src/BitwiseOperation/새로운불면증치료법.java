package BitwiseOperation;

import java.util.*;
import java.io.*;

public class 새로운불면증치료법 {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        for (int testCase = 1; testCase <= T; testCase++) {
            String strN = sc.next();
            strN = simulate(strN);

            System.out.println("#" + testCase + " " + strN);
        }

    }

    static String simulate(String strN) {
        int intN = Integer.parseInt(strN);

        int k = 1;
        int cnt = 0;
        boolean[] check = new boolean[10];

        while (true) {
            int size = strN.length();
            for (int i = 0; i < size; i++) {
                int idx = Integer.parseInt(strN.substring(i, i + 1));
                if (!check[idx]) {
                    check[idx] = true;
                    cnt++;
                }
            }

            if (cnt == 10)
                break;

            k++;
            strN = Integer.toString(k * intN);
        }

        return strN;
    }

}

/*
5
1
2
11
1295
1692
*/
