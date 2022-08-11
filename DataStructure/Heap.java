package SamsungAlgorithm22.DataStructure;

import java.util.*;

public class Heap {
    static Scanner sc;

    static final int MAX_SIZE = 100;

    static int heap[] = new int[MAX_SIZE];
    static int heapSize = 0;

    /*** 초기화 ***/
    //루트 노드의 인덱스가 0으로 시작하는 경우
    //부모의 인덱스 = (자식의 인덱스 - 1) / 2
    //왼쪽 자식 인덱스 = (부모의 인덱스) * 2 + 1
    //오른쪽 자식 인덱스 = (부모의 인덱스) * 2 + 2
    static void heapInit() {
        heapSize = 0;
    }

    /*** 삽입 ***/
    static void heapPush(int value) {
        if (heapSize + 1 > MAX_SIZE)    return;

        ///배열의 맨 마지막에 데이터를 삽입하고
        heap[heapSize] = value;

        //삽입된 노드가 부모 노드보다 작으면 부모와 값을 바꾸면서 올라간다.
        int current = heapSize;

        while (current > 0 && heap[current] < heap[(current - 1) / 2]) {

            int temp = heap[(current - 1) / 2];
            heap[(current - 1) / 2] = heap[current];
            heap[current] = temp;
            current = (current - 1) / 2;
        }
        heapSize = heapSize + 1;
    }

    /*** 삭제 ***/
    static int heapPop() {
        if (heapSize <= 0)  return -1;

        int value = heap[0];
        heapSize = heapSize - 1;

        //맨 마지막 데이터를 루트 노드로 옮기고
        heap[0] = heap[heapSize];

        //힙 구조를 계속 유지하도록 자식과 값을 바꾸면서 내려간다.
        int current = 0;
        while (current < heapSize && current * 2 + 1 < heapSize) {
            //자식과 값을 비교할 때는 둘 중 더 큰 값(최소 힙일 경우 작은 값)과 비교한다.
            int child;
            if (current * 2 + 2 >= heapSize)    child = current * 2 + 1;
            else    child = heap[current * 2 + 1] < heap[current * 2 + 2] ? current * 2 + 1 : current * 2 + 2;

            if (heap[current] < heap[child])    break;

            int temp = heap[current];
            heap[current] = heap[child];
            heap[child] = temp;

            current = child;
        }
        return value;
    }

    /*** 출력 ***/
    static void heapPrint(int[] heap, int heap_size) {
        for (int i = 0 ; i < heap_size; i++)    System.out.print(heap[i] + " ");
        System.out.println();
    }

    public static void main(String arg[]) throws Exception {
        sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int test_case = 1; test_case <= T; test_case++) {
            int N = sc.nextInt();
            heapInit();

            for (int i = 0; i < N; i++) {
                int value = sc.nextInt();
                heapPush(value);
            }

            System.out.print("#" + test_case + " ");
            for (int i = 0; i < N; i++) System.out.print(heapPop() + " ");
            System.out.println();
        }
    }
}

/*
2
10
10 49 38 17 56 92 8 1 13 55
13
4 22 50 13 5 1 22 35 21 7 99 100 14
 */
