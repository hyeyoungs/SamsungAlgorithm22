package SamsungAlgorithm22.LinkedList;

class Node {
    public int data;
    public Node prev;
    public Node next;

    public Node(int data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

public class DoubleLinkedList {

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

        Node nextNode = getNode(data);
        nextNode.next = head;
        head.prev = nextNode;
        head = nextNode;
    }

    public void addNode2Tail(int data) {
        if (head == null) {
            head = getNode(data);
            return;
        }

        Node currentNode = head;
        while (currentNode.next != null) {
            currentNode = currentNode.next;
        }

        Node nextNode = getNode(data);
        nextNode.prev = currentNode;
        currentNode.next = nextNode;
    }

    public void addNode2Num(int data, int num) {
        if (head == null) {
            head = getNode(data);
            return;
        }
        if (num == 1) {
            addNode2Head(data);
            return;
        }
        if (nodeCnt < num) {
            addNode2Tail(data);
            return;
        }

        int cnt = 1;
        Node currentNode = head;
        Node preNode = null;
        while (true) {
            if (num == cnt) {
                if (currentNode == null) {
                    addNode2Tail(data);
                    return;
                }
                preNode = currentNode.prev;
                break;
            }
            currentNode = currentNode.next;
            cnt++;
        }

        Node nextNode = getNode(data);
        preNode.next = nextNode;
        nextNode.prev = preNode;
        nextNode.next = currentNode;
        currentNode.prev = nextNode;
    }

    public int findNode(int data) {
        int cnt = 1;
        Node currentNode = head;
        while (true) {
            if (currentNode == null)
                return -1;
            if (currentNode.data == data)
                return cnt;

            currentNode = currentNode.next;
            cnt++;
        }
    }

    public void removeNode(int data) {
        Node currentNode = head;
        Node preNode = null;
        Node nextNode = null;

        if (head.data == data) {
            head = head.next;
            return;
        }

        while (true) {
            if (currentNode == null)
                return;
            if (currentNode.data == data) {
                preNode = currentNode.prev;
                nextNode = currentNode.next;
                break;
            }
            currentNode = currentNode.next;
        }

        if (nextNode == null) {
            preNode.next = null;
            return;
        }
        preNode.next = nextNode;
        nextNode.prev = preNode;
    }

    public int getList(int[] output) {
        Node currentNode = head;
        output[0] = currentNode.data;
        int idx = 1;
        while (currentNode.next != null) {
            currentNode = currentNode.next;
            output[idx] = currentNode.data;
            idx++;
        }
        return idx;
    }

    public int getReversedList(int[] output) {
        Node tailNode = head;
        while (tailNode.next != null) {
            tailNode = tailNode.next;
        }

        int idx = 1;
        output[0] = tailNode.data;
        while (tailNode.prev != null) {
            tailNode = tailNode.prev;
            output[idx] = tailNode.data;
            idx++;
        }
        return idx;
    }
}
