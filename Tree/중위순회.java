package SamsungAlgorithm22.Tree;

import java.io.*;

public class 중위순회 {

    static class Node {
        String data;
        String word;
        int left;
        int right;

        public Node(String data, String word, int left, int right) {
            this.data = data;
            this.word = word;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        for (int testCase = 1; testCase <= 10; testCase++) {
            int N = Integer.parseInt(br.readLine());
            Node[] node = new Node[N + 1];

            for (int vertex = 1; vertex <= N; vertex++) {
                String[] info = br.readLine().split(" ");
                init(vertex, info, node);
            }
            sb.append("#" + testCase + " ");
            inOrder(1, node, sb);
            sb.append("\n");
        }
        System.out.println(sb);
    }

    static void init(int vertex, String[] info, Node[] node) {
        int size = info.length;
        String data = "", word = "";
        int left = 0, right = 0;

        for (int i = 1; i <= size; i++) {
            data = info[0];
            word = info[1];
            if (size > 2)
                left = Integer.parseInt(info[2]);
            if (size > 3)
                right = Integer.parseInt(info[3]);

            node[vertex] = new Node(data, word, left, right);
        }
    }

    static void inOrder(int vertex, Node[] node, StringBuilder sb) {
        Node curNode = node[vertex];
        // 왼쪽 노드가 있다면 왼쪽 노드 순회
        if (curNode.left != 0)
            inOrder(curNode.left, node, sb);
        // 해당 노드 출력
        sb.append(curNode.word);
        // 오른쪽 노드가 있다면 오른쪽 노드 순회
        if (curNode.right != 0)
            inOrder(curNode.right, node, sb);

    }
}
