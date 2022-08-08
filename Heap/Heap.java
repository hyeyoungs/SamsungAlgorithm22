package SamsungAlgorithm22.Heap;

import java.util.*;

public class Heap {
    static Scanner sc = new Scanner(System.in);
    static StringBuilder sb = new StringBuilder();

    public static void main(String args[]) throws Exception {
        int T = sc.nextInt();

        for (int testCase = 1; testCase <= T; testCase++) {
            sb.append("#").append(testCase);
            simulate();
            sb.append("\n");
        }
        System.out.print(sb);
    }

    static void simulate() {
        int N = sc.nextInt();
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

        for (int i = 0; i < N; i++) {
            char command = sc.next().charAt(0);
            if (command == '1') {
                int value = Integer.parseInt(sc.next());
                pq.offer(value);
            } else {
                Integer removeValue = pq.poll();
                if (removeValue == null) sb.append(" ").append(-1);
                else    sb.append(" ").append(removeValue);
            }
        }
    }
}
