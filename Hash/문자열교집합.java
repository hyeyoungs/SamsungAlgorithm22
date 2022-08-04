package SamsungAlgorithm22.Hash;

import java.util.*;

public class 문자열교집합 {
    static Scanner sc = new Scanner(System.in);
    static Set<String> stringSet;
    static int answer;

    public static void main(String args[]) throws Exception {
        int T = sc.nextInt();
        for(int testCase = 1; testCase <= T; testCase++) {
            simulate();
            System.out.println("#" + testCase + " " + answer);
        }
    }

    static void simulate() {
        stringSet = new HashSet<>();
        answer = 0;
        int N = sc.nextInt(), M = sc.nextInt();

        for (int i = 0; i < N; i++) {
            String string = sc.next();
            stringSet.add(string);
        }

        for (int i = 0; i < M; i++) {
            String string = sc.next();
            if (stringSet.contains(string)) answer++;
            else stringSet.add(string);
        }
    }

}
