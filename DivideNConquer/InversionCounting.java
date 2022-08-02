package SamsungAlgorithm22.DivideNConquer;

import java.util.*;

public class InversionCounting {

    static Scanner sc = new Scanner(System.in);
    static StringBuilder sb = new StringBuilder();

    static int[] arr, temp;
    static long answer;

    public static void main(String args[]) throws Exception {
        int T = sc.nextInt();
        for (int testCase = 1; testCase <= T; testCase++) {
            simulate();
            sb.append("#" + testCase + " " + answer + "\n");
        }
        System.out.print(sb);

    }

    static void simulate() {
        init();
        mergeSort(arr);
    }

    static void init() {
        answer = 0;
        int N = sc.nextInt();
        arr = new int[N];
        temp = new int[N];

        for(int i = 0; i < N; i++) {
            arr[i] = sc.nextInt();
        }
    }

    static void mergeSort(int[] arr) {
        int [] temp = new int [arr.length];
        mergeSort(arr, temp, 0, arr.length - 1);
    }

    static void mergeSort(int[] arr, int[] temp, int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            mergeSort(arr, temp, start, mid);
            mergeSort(arr, temp, mid + 1, end);
            merge(arr, temp, start, mid, end);
        }
    }

    static void merge(int[] arr, int[] temp, int start, int mid, int end) {
        for (int i = start; i <= end; i++)  temp[i] = arr[i];

        int part1 = start, part2 = mid + 1, index = start;

        while (part1 <= mid && part2 <= end) {
            if (temp[part1] <= temp[part2]) {
                arr[index] = temp[part1];
                part1++;
            }
            else {
                answer += (mid - part1 + 1);
                arr[index] = temp[part2];
                part2++;

            }
            index++;
        }

        for (int i = 0; i <= mid - part1; i++)  arr[index + i] = temp[part1 + i];
    }

}
