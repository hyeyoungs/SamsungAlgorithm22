package SamsungAlgorithm22.Tree;

import java.util.*;

public class 공통조상 {

    static class Node {
        int vertex, rightChild, leftChild;
        Node(int vertex) {
            this.vertex = vertex;
            this.leftChild = 0;
            this.rightChild = 0;
        }
    }

    static Scanner sc =  new Scanner(System.in);

    static int V, E;
    static int first, second;

    static int[] parent;
    static boolean[] check;
    static Node[] nodes;

    static int parentSize;

    public static void main(String[] args) throws Exception {
        int T = sc.nextInt();
        for (int testCase = 1; testCase <= T; testCase++) {
            init();
            int parent = getParent();
            getParentSize(nodes[parent]);
            System.out.println("#" + testCase + " " + parent + " " + parentSize + "\n");
        }
    }

    static void init() {
        V = sc.nextInt();
        E = sc.nextInt();
        first = sc.nextInt();
        second = sc.nextInt();

        parent = new int[V + 1];
        nodes = new Node[V + 1];
        for (int i = 1; i <= V; i++) {
            nodes[i] = new Node(i);
        }

        for (int i = 1; i <= E; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            parent[to] = from;
            if (nodes[from].leftChild == 0) nodes[from].leftChild = to;
            else nodes[from].rightChild = to;
        }

        check = new boolean[V + 1];

        parentSize = 0;
    }

    static int getParent() {
        //첫번째 vertex가 루트로 이동하며 체크
        while (first > 1) {
            check[first] = true;
            first = parent[first];
        }

        //두번째 vertex가 루트로 이동하면서 처음으로 만나는 체크된 정점을 찾음
        while (second > 1 && check[second] == false){
            second = parent[second];
        }
        return second;
    }

    static void getParentSize(Node node) {
        parentSize++;
        if (node.leftChild != 0) getParentSize(nodes[node.leftChild]);
        if (node.rightChild != 0) getParentSize(nodes[node.rightChild]);
    }

}
