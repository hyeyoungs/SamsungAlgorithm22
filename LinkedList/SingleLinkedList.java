package SamsungAlgorithm22.LinkedList;

class Node {
    public int data;
    public Node next;

    public Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class SingleLinkedList {

    private final static int MAX_NODE = 10000;

    private Node[] node = new Node[MAX_NODE];
    private int nodeCnt = 0;
    private Node head;

    public Node getNode(int data) {
        node[nodeCnt] = new Node(data);
        return node[nodeCnt++];
    }

    public void init() {
        head = null;
    }

    public void addNode2Head(int data) {
        if (head == null) {
            head = getNode(data);
            return;
        }

        Node nodeHead = getNode(data);
        nodeHead.next = head;
        head = nodeHead;
    }

    public void addNode2Tail(int data) {
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

    public void addNode2Num(int data, int num) {
        if (head == null) {
            head = getNode(data);
            return;
        }

        if(num == 1) {
            addNode2Head(data);
            return;
        }

        if (nodeCnt < num) {
            addNode2Tail(data);
            return;
        }

        Node currentNode = head;
        Node preNode = null;
        int cnt = 1;

        while (true) {
            if (cnt == num - 1) {
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

    public void removeNode(int data) {
        if (head.data == data) {
            head = head.next;
            return;
        }

        Node currentNode = head;
        Node preNode = null;
        Node nextNode = null;

        while (true) {
            if (currentNode == null)
                return;

            if (currentNode.data == data) {
                nextNode = currentNode.next;
                break;
            }
            preNode = currentNode;
            currentNode = currentNode.next;
        }
        preNode.next = nextNode;
    }

    public int getList(int[] output) {
        int idx = 1;
        Node currentNode = head;
        output[0] = currentNode.data;
        while (currentNode.next != null) {
            currentNode = currentNode.next;
            output[idx] = currentNode.data;
            idx++;
        }
        return idx;
    }
}
