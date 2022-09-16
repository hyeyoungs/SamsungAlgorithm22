package SamsungAlgorithm22.BinarySearch;

import java.util.*;

public class 수명이절반 {

    static final int MAX_VALUE = 200000;
    static int[] W, S;
    static int N, K, answer;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        W = new int[MAX_VALUE + 1];
        S = new int[MAX_VALUE + 1];

        int T = sc.nextInt();
        for (int testCase = 1; testCase <= T; testCase++) {
            N = sc.nextInt();
            K = sc.nextInt();

            for (int i = 1; i <= N; i++) {
                W[i] = sc.nextInt();
            }
            for (int i = 1; i <= K; i++) {
                S[i] = sc.nextInt();
            }

            answer = MAX_VALUE;
            parametricSearch();
            System.out.println("#" + testCase + " " + answer);
        }
    }

    static void parametricSearch() {
        int left = 1;
        int right = MAX_VALUE;

        while (left < right) {
            int mid = (left + right) / 2;

            if (isPossible(mid)) {
                right = mid;
                answer = right;
            } else {
                left = mid + 1;
            }
        }
    }

    static boolean isPossible(int mid) {
        int data = 1;
        int cnt = 0;

        for (int i = 1; i <= N; i ++) {
            if (W[i] <= mid)    cnt++;
            else    cnt = 0;

            if (cnt == S[data]) {
                data++;
                cnt = 0;
                if (data > K)   return true;
            }
        }
        return false;
    }

}
