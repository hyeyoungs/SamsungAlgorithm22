package SamsungAlgorithm22.Heap;

import java.util.*;

public class 중간값구하기 {
    static Scanner sc = new Scanner(System.in);

    static int answer;
    static PriorityQueue<Integer> minHeap;
    static PriorityQueue<Integer> maxHeap;

    public static void main(String args[]) throws Exception {
        int T = sc.nextInt();

        for (int testCase = 1; testCase <= T; testCase++) {
            init();
            simulate();
            System.out.println("#" + testCase + " " + answer);
        }
    }

    static void init() {
        answer = 0;
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    }

    static void simulate() {
        int N = sc.nextInt();
        int center = sc.nextInt();

        for (int i = 0; i < N; i++) {
            int n1 = sc.nextInt();
            int n2 = sc.nextInt();

            if (n1 > center)    minHeap.offer(n1);
            else    maxHeap.offer(n1);

            if (n2 > center)    minHeap.offer(n2);
            else    maxHeap.offer(n2);

            if(minHeap.size() < maxHeap.size()) {
                minHeap.offer(center);
                center = maxHeap.poll();
            }   else if (minHeap.size() > maxHeap.size()) {
                maxHeap.offer(center);
                center = minHeap.poll();
            }

            answer = (answer + center) % 20171109;
        }
    }
}
