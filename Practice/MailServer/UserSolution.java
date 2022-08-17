package SamsungAlgorithm22.Practice.MailServer;

import java.util.*;

public class UserSolution {

    /** Trie 자료구조 ***/
    static class Trie {
        //노드 개수
        static int nodeNum = 0;
        //알파벳 길이
        static int ALPHABET_LEN = 26;
        //루트 노드 표시
        Node root;

        //트라이 만들 때, 루트 노드 생성
        public Trie() {
            root = new Node();
            root.c = ' ';
        }

        //노드 자료구조
        static class Node {
            //숫자 (노드에 대한 숫자)
            int num;
            //문자
            char c;
            //종료 여부
            boolean isTerminal;
            //자식 노드
            Node[] child = new Node[ALPHABET_LEN];

            //노드 만들 때, 노드 개수 증가
            public Node() {
                this.num = nodeNum++;
            }
        }

        //트라이 초기화
        void clear() {
            for (int i = 0; i < ALPHABET_LEN; i++) {
                root.child[i] = null;
            }
        }

        //문자열 삽입
        int insert(String str) {
            //문자열 길이
            int len = str.length();
            //루트 노드 가져오기
            Node cur = root;

            //문자열의 각 문자마다
            for (int i = 0; i < len; i++) {
                //문자를 숫자로
                int key = str.charAt(i) - 'a';
                //cur 노드의 자식노드 중 해당 문자가 없다면,
                if(cur.child[key] == null) {
                    //cur 노드의 자식노드에 해당 문자에 관한 노드 생성
                    cur.child[key] = new Node();
                    cur.child[key].c = str.charAt(i);
                }
                //cur 노드는 새로 추가된 노드로 설정
                cur = cur.child[key];
            }

            //cur 노드 종료여부 표시
            cur.isTerminal = true;
            //cur 노드에 대한 숫자 리턴
            return cur.num;
        }

        //문자열 검색
        int find(String str) {
            //문자열 길이
            int len = str.length();
            //루트 노드 가져오기
            Node cur = root;

            //문자열의 각 문자마다
            for (int i = 0; i < len; i++) {
                //문자를 숫자로
                int key = str.charAt(i) - 'a';
                //cur 노드의 자식 노드 중 해당 문자가 없다면,
                if (cur.child[key] == null) {
                    //-1 리턴
                    return -1;
                }
                //cur 노드는 찾은 문자 노드로 설정
                cur = cur.child[key];
            }

            //cur 노드(찾은 마지막 문자 노드가)가 null이 아니면서 cur 노드가 Terminal 표시 되어 있다면, cur 노드에 대한 숫자 리턴
            if (cur != null && cur.isTerminal)  return cur.num;
            //아니면, -1 리턴
            else    return -1;
        }
    }

    /*** mail Node 자료구조 ***/
    static class Node {
        int mailNum;
        Node next;
        Node prev;

        //메일 노드 만들 때, 메일 Id 받아서 생성
        public Node(int mailNum) {
            this.mailNum = mailNum;
        }
    }

    static int MAX_USER = 1000;
    static int MAX_MAIL = 10000;

    //mId2wId
    int[][] mails = new int[MAX_MAIL][10];
    //mId2wordCnt
    int[] mailWordCount = new int[MAX_MAIL];
    //uId2MailBox
    Node[] userMailBox = new Node[MAX_USER];
    //맨 마지막을 가리키는 노드
    Node[] tails = new Node[MAX_USER];
    //uId2mailCnt
    int[] userMailCount = new int[MAX_USER];

    //트라이
    Trie trie;
    int k, mailNum;

    public UserSolution() {
        // 테스트 케이스 마다 trie 생성
        trie = new Trie();
        // userMailBox, tails 초기화
        for (int i = 0; i < MAX_USER; i++) {
            userMailBox[i] = new Node(-1);
            tails[i] = new Node(-1);
        }
    }


    public void init(int N, int K) {
        k = K;
        mailNum = 0;

        Arrays.fill(mailWordCount, 0);
        Arrays.fill(userMailCount, 0);

        for (int i = 0; i < MAX_USER; i++) {
            userMailBox[i].next = tails[i];
            tails[i].prev = userMailBox[i];
        }
        trie.clear();
    }

    String arrToString(char[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; arr[i] != '\0'; i++) {
            sb.append(arr[i]);
        }
        return sb.toString();
    }


    void addNode(Node head, int mailNum) {
        Node newNode = new Node(mailNum);
        newNode.next = head.next;
        newNode.next.prev = newNode;
        head.next = newNode;
        newNode.prev = head;
    }

    void removeTail(int uID) {
        Node tail = tails[uID];
        Node remove = tail.prev;
        remove.prev.next = tail;
        tail.prev = remove.prev;
    }

    void removeNode(Node remove) {
        remove.prev.next = remove.next;
        remove.next.prev = remove.prev;
    }

    void sendMail(char subject[], int uID, int cnt, int rIDs[]) {
        String[] words = arrToString(subject).split(" ");
        for (int i = 0; i < words.length; i++) {
            mails[mailNum][i] = trie.insert(words[i]);
        }
        mailWordCount[mailNum] = words.length;

        for (int i = 0; i < cnt; i++) {
            int userId = rIDs[i];
            addNode(userMailBox[userId], mailNum);
            userMailCount[userId]++;

            if (userMailCount[userId] > k) {
                removeTail(userId);
                userMailCount[userId]--;
            }
        }

        mailNum++;
    }

    int deleteMail(int uID, char subject[]) {
        int answer = 0;
        String[] subjects = arrToString(subject).split(" ");
        Node cur = this.userMailBox[uID];

        while (cur.next != tails[uID]) {
            cur = cur.next;

            int len = mailWordCount[cur.mailNum];

            if (len != subjects.length) {
                continue;
            }

            boolean flag = true;
            int[] mail = mails[cur.mailNum];

            for (int i = 0; i < len; i++) {
                if (mail[i] != trie.find(subjects[i])) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                removeNode(cur);
                answer++;
            }
        }

        userMailCount[uID] -= answer;

        return answer;
    }

    int searchMail(int uID, char text[]) {
        int answer = 0;
        Node cur = userMailBox[uID];
        Node tail = tails[uID];

        String target = arrToString(text);
        int num = trie.find(target);

        while (cur.next != tail) {
            cur = cur.next;

            int len = mailWordCount[cur.mailNum];

            for (int i = 0; i < len; i++) {
                if (mails[cur.mailNum][i] == num) {
                    answer++;
                    break;
                }
            }
        }

        return answer;
    }

    int getCount(int uID) {
        return userMailCount[uID];
    }

}
