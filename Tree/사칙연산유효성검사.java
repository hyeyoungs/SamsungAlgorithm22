package SamsungAlgorithm22.Tree;

import java.io.*;
import java.util.*;

public class 사칙연산유효성검사 {

    static class Node {
        String data;
        Node rightChild;
        Node leftChild;
    }

    static int N;
    static int answer;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static Node[] treeNode;

    public static void main(String[] args) throws IOException {
        for (int testCase = 1; testCase <= 10; testCase++) {
            init();
            isPossible();
            sb.append("#" + testCase + " ");
            sb.append(answer + "\n");
        }

        System.out.println(sb);

    }

    static void init() throws IOException {
        N = Integer.parseInt(br.readLine());

        treeNode = new Node[N + 1];
        for (int i = 1; i <= N; i++) treeNode[i] = new Node();

        for (int i = 0; i < N; i ++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int vertex = Integer.parseInt(st.nextToken());
            String data = st.nextToken();

            treeNode[vertex].data = data;

            if (st.hasMoreTokens()) {
                int leftChild = Integer.parseInt(st.nextToken());
                treeNode[vertex].leftChild = treeNode[leftChild];
            }

            if (st.hasMoreTokens()) {
                int rightChild = Integer.parseInt(st.nextToken());
                treeNode[vertex].rightChild = treeNode[rightChild];
            }
        }

    }

    static void isPossible() {
        answer = 1;

        for(int vertex = 1; vertex <= N; vertex++) {
            if (vertex * 2 < N) {
                if(isNumeric(treeNode[vertex].data)) {
                    answer = 0;
                    return;
                }
            }
            else if (vertex * 2 == N) {
                answer = 0;
                return;
            }
            else {
                if(!isNumeric(treeNode[vertex].data)) {
                    answer = 0;
                    return;
                }
            }
        }
    }

    static boolean isNumeric(String s) {
        return s.chars().allMatch(Character::isDigit);
    }

}
