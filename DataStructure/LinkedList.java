package SamsungAlgorithm22.DataStructure;

public class LinkedList {

    //자료구조 Node
    private class Node {
        //원소의 값을 저장
        private int data;
        //다음 노드의 주소 저장
        private Node next;

        private Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private final static int MAX_NODE = 10000;
    //Node 여러개로 구성된 LinkedList
    private Node[] node = new Node[MAX_NODE];
    //노드의 개수
    private int nodeCnt = 0;
    //Head
    private Node head;


    //초기화
    public void init() {
        head = null;
    }

    //Node 생성
    public Node getNode(int data) {
        //데이터 필드 작성
        node[nodeCnt] = new Node(data);
        //링크 필드 리턴
        return node[nodeCnt++];
    }

    //Node 맨 앞에 삽입
    public void addNode2Head(int data) {
        //LinkedList가 비어있다면,
        if (head == null) {
            //새로운 노드 생성하고 Head 노드가 새로운 노드를 가리킨다.
            //→ 맨 앞 노드가 된다.
            head = getNode(data);
            return;
        }

        //메모리 할당하여 새로운 노드 newNode 생성
        Node newNode = getNode(data);
        //new 노드의 링크 필드에 Head 노드의 주소를 넣는다.
        newNode.next = head;
        //Head 노드가 new 노드를 가리키게 한다.
        head = newNode;
    }

    //Node 맨 뒤에 삽입
    public void addNode2Tail(int data) {
        //LinkedList가 비어있다면,
        if(head == null) {
            //새로운 노드 생성하고 Head 노드가 새로운 노드를 가리킨다.
            //→ 맨 앞 노드가 된다.
            head = getNode(data);
            return;
        }

        //현재 노드를 헤더가 가리키는 노드(즉, 첫번째 노드)로 설정한다.
        Node currentNode = head;
        //현재 노드가 마지막 노드가 될 때까지(링크 필드가 Null이 될 때까지) 아래를 반복
        while (currentNode.next != null) {
            //현재 노드가 다음노드로 간다.
            currentNode = currentNode.next;
        }
        //새로운 노드 생성하고 Tail 노드가 새로운 노드를 가리킨다.
        //→ 맨 뒤 노드가 된다.
        Node tailNode = getNode(data);
        //마지막 노드의 링크 필드에 Tail 노드의 주소를 넣는다.
        currentNode.next = tailNode;
    }

    //Node를 인덱스 기준으로 삽입
    public void addNode2Idx(int data, int idx) {
        //LinkedList가 비어있다면,
        if (head == null) {
            //새로운 노드 생성하고 Head 노드가 새로운 노드를 가리킨다.
            //→ 맨 앞 노드가 된다
            head = getNode(data);
            return;
        }

        //index가 0이라면
        if(idx == 0) {
            //Node 맨 앞에 삽입
            addNode2Head(data);
            return;
        }

        //index가 노드의 갯수보다 같다면 맨 뒤에 삽입
        if (nodeCnt <= idx) {
            addNode2Tail(data);
            return;
        }

        //중간에 삽입
        //현재 노드를 head의 노드로 지정
        Node currentNode = head;
        //이전 노드는 null로 지정
        Node preNode = null;

        int cnt = 1;

        while (true) {
            //이동 횟수가 idx랑 같다면
            if (cnt == idx) {
                //이전 노드에 현재 노드를 저장
                preNode = currentNode;
                //현재 노드를 다음 노드에 저장
                currentNode = currentNode.next;
                break;
            }
            //현재 노드가 다음 노드로 계속 간다.
            currentNode = currentNode.next;
            cnt++;
        }

        // 새로운 Next 노드 생성
        Node nextNode = getNode(data);
        // Pre 노드 링크 필드에 Next node를 넣는다.
        preNode.next = nextNode;
        //Next Node의 링크 필드에 Current 노드의 주소를 넣는다.
        nextNode.next = currentNode;

    }

    //Node를 값 기준으로 삭제
    public void removeNode2Value(int data) {
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
                //삭제할 노드 탐색
                nextNode = currentNode.next;
                break;
            }
            //삭제할 노드의 앞 노드 탐색
            preNode = currentNode;
            currentNode = currentNode.next;
        }
        //삭제할 노드의 링크 필드를 선행 노드의 링크 필드에 복사
        preNode.next = nextNode;
    }

    //Node를 인덱스 기준으로 삭제
    public void removeNode2Idx(int idx) {
        if (head == null)
            return;

        //삭제할 앞 노드
        Node temp = head;

        if (idx == 0) {
            head = temp.next;
            return;
        }

        for (int i = 0; temp != null && i < idx - 1;  i++)
            //삭제할 노드의 앞 노드 탐색
            temp = temp.next;

        if (temp == null || temp.next == null)
            return;

        //삭제할 노드의 링크 필드를 선생 노드의 링크 필드에 복사
        temp.next = temp.next.next;
    }

    //노드의 값 정보를 차례대로 output[]에 저장하고 노드 갯수를 리턴
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
