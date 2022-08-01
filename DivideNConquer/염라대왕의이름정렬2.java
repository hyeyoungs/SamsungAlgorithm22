package SamsungAlgorithm22.DivideNConquer;

import java.util.*;

public class 염라대왕의이름정렬2 {

    static Scanner sc = new Scanner(System.in);
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws Exception {
        int T = sc.nextInt();
        for (int testCase = 1; testCase <= T; testCase++) {
            sb.append("#" + testCase + "\n");
            simulate();
        }
        System.out.println(sb);
    }


    static void simulate() {
        int N = sc.nextInt();
        TreeSet<String> nameOrderedSet = new TreeSet<String>(new Comparator<String>() {
            public int compare(String pre, String next) {
                //글자의 길이 짧은 순
                if (pre.length() != next.length())  return pre.length() - next.length();
                //길이가 같으면, 사전순
                else    return pre.compareTo(next);
            }
        });

        for (int i = 0; i < N; i++) {
            nameOrderedSet.add(sc.next());
        }

        for (String string : nameOrderedSet) {
            sb.append(string + "\n");
        }
    }

}
