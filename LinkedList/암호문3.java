package SamsungAlgorithm22.LinkedList;

import java.util.*;

public class 암호문3 {

    static class Node {
        public int data;
        public Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }


    final static Scanner sc = new Scanner(System.in);
    final static int MAX_NODE = 10000;
    static Node[] passwords;
    static int nodeCnt;
    static Node head;


    static Node getNode(int data) {
        passwords[nodeCnt] = new Node(data);
        return passwords[nodeCnt++];
    }

    static void addNode2Head(int data) {
        if (head == null) {
            head = getNode(data);
            return;
        }

        Node nodeHead = getNode(data);
        nodeHead.next = head;
        head = nodeHead;
    }

    static void addNode2Tail(int data) {
        if(head == null) {
            head = getNode(data);
            return;
        }

        Node currentNode = head;
        while (currentNode.next != null) {
            currentNode = currentNode.next;
        }

        Node nodeTail = getNode(data);
        currentNode.next = nodeTail;

    }

    static void addNode2Idx(int data, int idx) {
        if (head == null) {
            head = getNode(data);
            return;
        }

        if(idx == 0) {
            addNode2Head(data);
            return;
        }

        if (nodeCnt < idx) {
            addNode2Tail(data);
            return;
        }

        Node currentNode = head;
        Node preNode = null;
        int cnt = 1;

        while (true) {
            if (cnt == idx) {
                preNode = currentNode;
                currentNode = currentNode.next;
                break;
            }
            currentNode = currentNode.next;
            cnt++;
        }

        Node nextNode = getNode(data);
        preNode.next = nextNode;
        nextNode.next = currentNode;

    }

    static void removeNode2Idx(int idx) {
        if (head == null)
            return;

        Node temp = head;

        if (idx == 0) {
            head = temp.next;
            return;
        }
        
        for (int i = 0; temp != null && i < idx - 1;  i++)
            temp = temp.next;

        if (temp == null || temp.next == null)
            return;

        Node next = temp.next.next;
        temp.next = next;
    }

    static void init() {
        head = null;
        nodeCnt = 0;
        passwords = new Node[MAX_NODE];
    }

    static void inputPasswords() {
        int N = sc.nextInt();

        for (int i = 1; i <= N; i++) {
            int data = sc.nextInt();
            암호문3.addNode2Tail(data);
        }

    }

    static void simulateCommands() {
        int M = sc.nextInt();
        for (int i = 1; i <= M; i++) {
            char c = sc.next().charAt(0);

            if (c == 'I') {
                int X = sc.nextInt();
                int Y = sc.nextInt();
                int cnt = X + Y;
                for (int k = X; k < cnt; k++) {
                    int data = sc.nextInt();
                    암호문3.addNode2Idx(data, k);
                }
            } else if (c == 'D') {
                int X = sc.nextInt();
                int Y = sc.nextInt();
                int cnt = X + Y;
                for (int k = X; k < cnt; k++) {
                    암호문3.removeNode2Idx(X);
                }
            } else {
                int Y = sc.nextInt();
                for (int k = 0; k < Y; k++) {
                    int data = sc.nextInt();
                    암호문3.addNode2Tail(data);
                }
            }

        }

    }

    static void printPasswords(int testCase) {
        Node currentNode = head;

        String output = "#" + Integer.toString(testCase) + " " + Integer.toString(currentNode.data);
        for (int count = 2; count <= 10; count++) {
            currentNode = currentNode.next;
            output += (" " + Integer.toString(currentNode.data));
        }

        System.out.println(output);
    }


    public static void main(String[] args) {
        int T = 10;

        for (int testCase = 1; testCase <= T; testCase++) {
            init();
            inputPasswords();
            simulateCommands();
            printPasswords(testCase);
        }
    }

}
