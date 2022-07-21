package SamsungAlgorithm22.BitwiseOperation;

import java.io.IOException;
import java.util.Scanner;

public class 새로운불면증치료법2 {
    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        for (int testCase = 1; testCase <= T; testCase++) {
            String strN = sc.next();
            int intN = simulate(strN);

            System.out.println("#" + testCase + " " + intN);
        }

    }

    static int simulate(String strN) {
        int intN = Integer.parseInt(strN);
        int tempIntN = intN;
        int cnt = 1, flag = 0;

        while (true) {
            while(tempIntN > 0){
                flag |= 1 << (tempIntN % 10);
                tempIntN /= 10;
            }

            if(flag == ((1 << 10) - 1)) break;
            cnt++;
            tempIntN = cnt * intN;
        }

        return  cnt * intN;
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
