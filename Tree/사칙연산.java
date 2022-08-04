package SamsungAlgorithm22.Tree;

import java.io.*;
import java.util.*;

public class 사칙연산 {
    static class Node {
        String data;
        Node leftChild;
        Node rightChild;
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static Node [] treeNode;
    public static void main(String[] args) throws Exception {
        for (int testCase = 1; testCase <= 10; testCase++) {
            init();
            int answer = (int) postOrderTraversal(treeNode[1]);
            System.out.println("#" + testCase + " " + answer);
        }
    }

    static void init() throws Exception {
        int N = Integer.parseInt(br.readLine());
        treeNode = new Node[N + 1];
        for (int i = 1; i <= N; i++) treeNode[i] = new Node();

        StringTokenizer st = null;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            int nodeNum = Integer.parseInt(st.nextToken());
            String data = st.nextToken();

            treeNode[nodeNum].data = data;

            if (st.hasMoreTokens()) {
                int leftChild = Integer.parseInt(st.nextToken());
                treeNode[nodeNum].leftChild = treeNode[leftChild];
            }

            if (st.hasMoreTokens()) {
                int rightChild = Integer.parseInt(st.nextToken());
                treeNode[nodeNum].rightChild = treeNode[rightChild];
            }
        }
    }

    static double postOrderTraversal(Node node) {
        if (node.leftChild == null && node.rightChild == null)  return Double.parseDouble(node.data);

        double n1 = postOrderTraversal(node.leftChild);
        double n2 = postOrderTraversal(node.rightChild);
        String operation = node.data;

        if (operation.equals("+")) return n1 + n2;
        else if (operation.equals("-")) return n1 - n2;
        else if (operation.equals("*")) return n1 * n2;
        else return n1 / n2;
    }
}
