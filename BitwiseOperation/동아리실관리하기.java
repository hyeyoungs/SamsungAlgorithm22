package SamsungAlgorithm22.BitwiseOperation;

import java.util.*;

public class 동아리실관리하기 {
    static int mod = 1000000007;
    static int[][] map;
    static String text;
    static int size = 0;
    static int result = 0;

    static void simulate() {
        for(int idx = 1; idx <= size; idx++) {
            int key = 1 << (text.charAt(idx - 1) - 'A');

            for(int i = 1; i < 16; i++) {
                if(map[idx - 1][i] == 0) continue;

                for(int j = 1; j < 16; j++) {
                    if((j & key) == 0 || (i & j) == 0) continue;
                    map[idx][j] = (map[idx][j] + map[idx - 1][i]) % mod;
                }
            }
        }

    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        for(int testCase = 1; testCase <= T; testCase++) {
            text = sc.next();
            size = text.length();
            map = new int[size + 1][16];
            map[0][1] = 1;

            simulate();

            result = 0;
            for(int i = 1; i < 16; i++) result = (result + map[size][i]) % mod;
            System.out.println("#" + testCase + " " + result);

        }
    }
}

/*
2
BC
ADCBBACDCBCBACBDCABDCBA
 */
