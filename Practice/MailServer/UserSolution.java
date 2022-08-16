package SamsungAlgorithm22.Practice.MailServer;

import java.util.*;

public class UserSolution {

    static class Trie {
        static int nodeNum = 0;
        static int ALPHABET_LEN = 26;
        Node root;

        public Trie() {
            root = new Node();
            root.c = ' ';
        }

        static class Node {
            int num;
            char c;
            boolean isTerminal;
            Node[] child = new Node[ALPHABET_LEN];

            public Node() {
                this.num = nodeNum++;
            }
        }

        void clear() {
            for (int i = 0; i < ALPHABET_LEN; i++) {
                root.child[i] = null;
            }
        }

        int insert(String str) {
            int len = str.length();
            Node cur = root;

            for (int i = 0; i < len; i++) {
                int key = str.charAt(i) - 'a';

                if(cur.child[key] == null) {
                    cur.child[key] = new Node();
                    cur.child[key].c = str.charAt(i);
                }

                cur = cur.child[key];
            }
            cur.isTerminal = true;
            return cur.num;
        }

        int find(String str) {
            int len = str.length();
            Node cur = root;

            for (int i = 0; i < len; i++) {
                int key = str.charAt(i) - 'a';
                if (cur.child[key] == null) {
                    return -1;
                }
                cur = cur.child[key];
            }

            if (cur != null && cur.isTerminal)  return cur.num;
            else    return -1;
        }
    }

    static class Node {
        int mailNum;
        Node next;
        Node prev;

        public Node(int mailNum) {
            this.mailNum = mailNum;
        }
    }

    static int MAX_USER = 1000;
    static int MAX_MAIL = 10000;

    int[][] mails = new int[MAX_MAIL][10];
    int[] mailWordCount = new int[MAX_MAIL];
    Node[] userMailBox = new Node[MAX_USER];
    Node[] tails = new Node[MAX_USER];
    int[] userMailCount = new int[MAX_USER];

    Trie trie;
    int k, mailNum;

    public UserSolution() {
        trie = new Trie();

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
