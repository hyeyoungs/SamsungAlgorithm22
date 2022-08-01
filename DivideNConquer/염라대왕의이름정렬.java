package SamsungAlgorithm22.DivideNConquer;

import java.util.*;

public class 염라대왕의이름정렬 {

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
        String[] name = new String[N];

        for (int i = 0; i < N; i++) {
            name[i] = sc.next();
        }

        //Sort
        //32개 이하 : 머지소트
        //32개 이상 : 팀소트(머지 + 선택)
        Arrays.sort(name, new Comparator<String>() {
            public int compare(String pre, String next) {
                //글자의 길이 짧은 순
                if (pre.length() != next.length())  return pre.length() - next.length();
                    //길이가 같으면, 사전순
                else    return pre.compareTo(next);
            }
        });

        //중복 제거
        String temp = null;
        for (int i = 0; i < name.length; i++) {
            if(!name[i].equals(temp)) {
                System.out.println(name[i]);
            }
            temp = name[i];
        }

        for (int i = 0; i < N; i++) {
            sb.append(name[i] + "\n");
        }
    }

}
