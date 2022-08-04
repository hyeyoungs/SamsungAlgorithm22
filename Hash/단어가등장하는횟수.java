package SamsungAlgorithm22.Hash;

import java.util.*;

public class 단어가등장하는횟수 {
    static Scanner sc = new Scanner(System.in);

    public static void main(String args[]) throws Exception {
        int T = sc.nextInt();
        for (int testCase = 1; testCase <= T; testCase++) {
            String book = sc.next();
            String query = sc.next();
            System.out.println("#" + testCase + " " + kmp(book, query));
        }
    }

    static int[] makeTable(String pattern) {
        int patternSize = pattern.length();
        int[] table = new int[patternSize];

        int j = 0;
        for (int i = 1; i < patternSize; i++) {
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = table[j - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(j)) {
                table[i] = ++j;
            }
        }

        return table;
    }

    static int kmp(String str, String pattern) {
        int count = 0;
        int[] table = makeTable(pattern);
        int j = 0;

        for (int i = 0; i < str.length(); i++) {
            while (j > 0 && str.charAt(i) != pattern.charAt(j)) j = table[j - 1];

            if (str.charAt(i) == pattern.charAt(j)) {
                if (j == pattern.length() - 1) {
                    count++;
                    j = table[j];
                } else  j++;
            }
        }

        return count;
    }

}
